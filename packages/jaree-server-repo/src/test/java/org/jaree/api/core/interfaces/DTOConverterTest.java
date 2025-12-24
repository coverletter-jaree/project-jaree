package org.jaree.api.core.interfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.jaree.api.core.dto.DTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * DTOConverter 인터페이스 테스트
 */
class DTOConverterTest {

    // ---------------------- 테스트용 클래스 선언 ----------------------
    /**
     * 테스트용 1차 엔티티
     */
    static class Test1stEntity {
        private final String name;

        public Test1stEntity(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 테스트용 2차 엔티티
     */
    static class Test2ndEntity {
        private final int age;

        public Test2ndEntity(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }

    /**
     * 테스트용 DTO
     */
    static class TestDTO extends DTO<TestDTO.EntityRecord> {
        private final String name;
        private final int age;

        public TestDTO(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        record EntityRecord(Test1stEntity test1stEntity, Test2ndEntity test2ndEntity) {
        }
    }

    /**
     * DTOConverter 인터페이스 테스트용 구현체
     */
    static class TestConverter implements DTOConverter {

        @Override
        @SuppressWarnings("unchecked")
        public <EntityRecord, T extends DTO<EntityRecord>> T convert(EntityRecord record) {
            if (record instanceof TestDTO.EntityRecord r) {
                return (T) new TestDTO(r.test1stEntity().getName(), r.test2ndEntity().getAge());
            }
            throw new IllegalArgumentException("지원하지 않는 EntityRecord 타입입니다: " + record.getClass());
        }

        @Override
        @SuppressWarnings("unchecked")
        public <EntityRecord, T extends DTO<EntityRecord>> EntityRecord convert(T dto) {
            if (dto instanceof TestDTO d) {
                return (EntityRecord) new TestDTO.EntityRecord(new Test1stEntity(d.getName()), new Test2ndEntity(d.getAge()));
            }
            throw new IllegalArgumentException("지원하지 않는 DTO 타입입니다: " + dto.getClass());
        }

        @Override
        public <EntityRecord, T extends DTO<EntityRecord>> List<T> convert(List<EntityRecord> records) {
            return records.stream()
                .map(this::<EntityRecord, T>convert)
                .toList();
        }
    }

    // ---------------------- 테스트용 클래스 선언 끝 ----------------------

    // ---------------------- 테스트용 변수 선언 ----------------------

    private static DTOConverter converter;
    
    // ---------------------- 테스트용 변수 선언 끝 ----------------------

    @BeforeAll
    static void setup() {
        converter = new TestConverter();
    }

    @Test
    @DisplayName("Entity -> DTO 변환 테스트")
    void testConvertEntityToDTO() {
        // Given
        Test1stEntity test1stEntity = new Test1stEntity("Jaree");
        Test2ndEntity test2ndEntity = new Test2ndEntity(20);

        // When
        TestDTO.EntityRecord record = new TestDTO.EntityRecord(test1stEntity, test2ndEntity);
        TestDTO result = converter.convert(record);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Jaree");
        assertThat(result.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("DTO -> Entity 변환 테스트")
    void testConvertDTOToEntity() {
        // Given
        TestDTO dto = new TestDTO("Jaree", 20);

        // When
        TestDTO.EntityRecord result = converter.convert(dto);
        Test1stEntity test1stEntity = result.test1stEntity();
        Test2ndEntity test2ndEntity = result.test2ndEntity();

        // Then
        assertThat(result).isNotNull();
        assertThat(test1stEntity.getName()).isEqualTo("Jaree");
        assertThat(test2ndEntity.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("List<Entity> -> List<DTO> 변환 테스트")
    void testConvertEntityListToDTOList() {
        // Given
        List<Test1stEntity> entities = List.of(
            new Test1stEntity("User1"),
            new Test1stEntity("User2")
        );

        List<Test2ndEntity> entities2 = List.of(
            new Test2ndEntity(10),
            new Test2ndEntity(20)
        );
        
        // When
        List<TestDTO.EntityRecord> records = List.of(
            new TestDTO.EntityRecord(entities.get(0), entities2.get(0)),
            new TestDTO.EntityRecord(entities.get(1), entities2.get(1))
        );
        List<TestDTO> results = converter.convert(records);

        // Then
        assertThat(results).hasSize(2)
            .usingRecursiveFieldByFieldElementComparator() // 필드 값 비교
            .containsExactly(
                new TestDTO("User1", 10),
                new TestDTO("User2", 20)
            );
    }
}
