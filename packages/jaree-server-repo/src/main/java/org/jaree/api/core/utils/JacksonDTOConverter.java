package org.jaree.api.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jaree.api.core.dto.DTO;
import org.jaree.api.core.interfaces.DTOConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonDTOConverter implements DTOConverter {

    private final ObjectMapper mapper;

    public JacksonDTOConverter() {
        this.mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <EntityRecord, T extends DTO<EntityRecord>> T convert(EntityRecord record) {
        if (record == null) {
            return null;
        }

        // 1. DTO 타입 추론
        // EntityRecord는 DTO 내부의 static record로 정의되므로, EnclosingClass가 DTO Class가 됨
        Class<?> targetClass = record.getClass().getEnclosingClass();
        if (targetClass == null || !DTO.class.isAssignableFrom(targetClass)) {
            throw new IllegalArgumentException("EntityRecord must be a static inner class of a DTO class.");
        }

        // 2. Map 생성 및 병합
        Map<String, Object> rootMap = new HashMap<>();

        try {
            // Record Component 순회
            RecordComponent[] components = record.getClass().asSubclass(Record.class).getRecordComponents();
            for (RecordComponent component : components) {
                Object value = component.getAccessor().invoke(record);
                if (value == null) continue;

                Map<String, Object> entityMap = mapper.convertValue(value, Map.class);

                // Strategy 1: Flattening (Root에 병합)
                // 충돌 시 뒤에 오는 컴포넌트 값이 덮어씀 (Last-write-wins)
                rootMap.putAll(entityMap);

                // Strategy 2: Nesting (계층 구조 보존)
                // - Component Name
                rootMap.put(component.getName(), value);
                // - Component Name + "DTO" Suffix
                rootMap.put(component.getName() + "DTO", value);
            }

            // 3. 최종 변환
            T result = mapper.convertValue(rootMap, (Class<T>) targetClass);
            return result;

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to access record component", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <EntityRecord, T extends DTO<EntityRecord>> EntityRecord convert(T dto) {
        if (dto == null) {
            return null;
        }

        // 1. EntityRecord 타입 추론
        Class<EntityRecord> recordClass = (Class<EntityRecord>) getRecordClass(dto.getClass());
        
        // 2. DTO -> Map 변환
        Map<String, Object> dtoMap = mapper.convertValue(dto, Map.class);
        
        // 3. Component 데이터 추출 및 복원
        RecordComponent[] components = recordClass.getRecordComponents();
        Object[] args = new Object[components.length];
        Class<?>[] argTypes = new Class<?>[components.length];

        for (int i = 0; i < components.length; i++) {
            RecordComponent component = components[i];
            argTypes[i] = component.getType();
            String name = component.getName();
            
            Object sourceValue = null;

            // Priority 1: Nested Key (name or name + "DTO")
            if (dtoMap.containsKey(name)) {
                sourceValue = dtoMap.get(name);
            } else if (dtoMap.containsKey(name + "DTO")) {
                sourceValue = dtoMap.get(name + "DTO");
            } 
            // Priority 2: Flattened Root
            else {
                // dtoMap 전체를 소스로 사용 (Flattened 된 경우)
                // 단, 이 경우 dtoMap에 해당 Entity의 필드들이 포함되어 있어야 함
                sourceValue = dtoMap;
            }

            if (sourceValue != null) {
                args[i] = mapper.convertValue(sourceValue, component.getType());
            }
        }

        // 4. EntityRecord 생성
        try {
            Constructor<EntityRecord> constructor = recordClass.getDeclaredConstructor(argTypes);
            constructor.setAccessible(true);
            EntityRecord result = constructor.newInstance(args);
            return result;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to instantiate EntityRecord: " + recordClass.getName(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <EntityRecord, T extends DTO<EntityRecord>> List<T> convert(List<EntityRecord> records) {
        if (records == null) {
            return List.of();
        }
        return records.stream()
                .map(record -> (T) this.convert(record))
                .collect(Collectors.toList());
    }

    private Class<?> getRecordClass(Class<?> dtoClass) {
        Type superClass = dtoClass.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0 && typeArguments[0] instanceof Class) {
                return (Class<?>) typeArguments[0];
            }
        }
        // DTO를 상속받은 경우 재귀적으로 탐색 필요할 수 있으나, 현재 구조상 바로 상위 확인.
        // 만약 DTO<R>을 직접 상속하지 않고 중간 클래스가 있다면 로직 보완 필요.
        throw new IllegalArgumentException("Could not determine EntityRecord type from DTO class: " + dtoClass.getName());
    }
}
