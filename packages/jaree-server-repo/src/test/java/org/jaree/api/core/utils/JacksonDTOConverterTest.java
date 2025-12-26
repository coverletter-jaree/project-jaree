package org.jaree.api.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jaree.api.core.dto.DTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTOConverter 구현체 테스트
 */
class JacksonDTOConverterTest {

    // ---------------------- 테스트용 클래스 선언 ----------------------
    /**
     * 테스트용 1차 엔티티
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test1stEntity {
        private String prop1;
        private int prop2;
        private boolean prop3;
    }

    /**
     * 테스트용 2차 엔티티
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test2ndEntity {
        private List<String> listProp1;
        private Map<String, String> mapProp2;
        private Set<String> setProp3;
    }

    /**
     * 테스트용 1차 DTO (DTO Root에 바로 엔티티를 포함)
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test1stDTO extends DTO<Test1stDTO.EntityRecord> {
        private String prop1;
        private int prop2;
        private boolean prop3;
        private List<String> listProp1;
        private Map<String, String> mapProp2;
        private Set<String> setProp3;

        record EntityRecord(Test1stEntity test1stEntity, Test2ndEntity test2ndEntity) {
        }
    }

    /**
     * 테스트용 2차 DTO (DTO Field 1 depth * 2)
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test2ndDTO extends DTO<Test2ndDTO.EntityRecord> {
        private Test1stEntityDTO test1stEntityDTO;
        private Test2ndEntityDTO test2ndEntityDTO;
        
        record EntityRecord(Test1stEntity test1stEntity, Test2ndEntity test2ndEntity) {
        }
        
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        static class Test1stEntityDTO {
          private String prop1;
          private int prop2;
          private boolean prop3;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        static class Test2ndEntityDTO {
          private List<String> listProp1;
          private Map<String, String> mapProp2;
          private Set<String> setProp3;
        }
    }

    /**
     * 테스트용 3차 DTO (DTO Root + Field 1 depth)
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Test3rdDTO extends DTO<Test3rdDTO.EntityRecord> {
        private String prop1;
        private int prop2;
        private boolean prop3;
        private Test2ndEntityDTO test2ndEntityDTO;

        record EntityRecord(Test1stEntity test1stEntity, Test2ndEntity test2ndEntity) {
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        static class Test2ndEntityDTO {
          private List<String> listProp1;
          private Map<String, String> mapProp2;
          private Set<String> setProp3;
        }
    }

    /**
     * 필드 충돌 테스트용 1차 엔티티
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestCollisionEntity1 {
        private String commonProp;
        private String uniqueProp1;
    }

    /**
     * 필드 충돌 테스트용 2차 엔티티
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestCollisionEntity2 {
        private String commonProp;
        private String uniqueProp2;
    }

    /**
     * 필드 충돌 테스트용 DTO
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestCollisionDTO extends DTO<TestCollisionDTO.EntityRecord> {
        private String commonProp;
        private String uniqueProp1;
        private String uniqueProp2;
        private TestCollisionEntity1 testCollisionEntity1;
        private TestCollisionEntity2 testCollisionEntity2;

        record EntityRecord(TestCollisionEntity1 testCollisionEntity1, TestCollisionEntity2 testCollisionEntity2) {
        }
    }

    // ---------------------- 테스트용 클래스 선언 끝 ----------------------

    // ---------------------- 테스트용 변수 선언 ----------------------
    private static JacksonDTOConverter converter;
    // ---------------------- 테스트용 변수 선언 끝 ----------------------

    @BeforeAll
    public static void setup() {
        converter = new JacksonDTOConverter();
    }

    @Nested
    @DisplayName("Entity to Output DTO")
    class EntityToDTOTest {
      @Test
      @DisplayName("Entity의 Field가 둘 다 Root에 존재하는 경우")
      void testAllRootField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test1stDTO.EntityRecord entityRecord = new Test1stDTO.EntityRecord(entity, entity2);

        // When
        Test1stDTO dto = converter.convert(entityRecord);

        // Then
        assertEquals(entity.getProp1(), dto.getProp1());
        assertEquals(entity.getProp2(), dto.getProp2());
        assertEquals(entity.isProp3(), dto.isProp3());
        assertEquals(entity2.getListProp1(), dto.getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root에 존재하지 않는 경우")
      void testNoneRootField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test2ndDTO.EntityRecord entityRecord = new Test2ndDTO.EntityRecord(entity, entity2);

        // When
        Test2ndDTO dto = converter.convert(entityRecord);

        // Then
        assertEquals(entity.getProp1(), dto.getTest1stEntityDTO().getProp1());
        assertEquals(entity.getProp2(), dto.getTest1stEntityDTO().getProp2());
        assertEquals(entity.isProp3(), dto.getTest1stEntityDTO().isProp3());
        assertEquals(entity2.getListProp1(), dto.getTest2ndEntityDTO().getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getTest2ndEntityDTO().getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getTest2ndEntityDTO().getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root와 DTO Field에 복합적으로 존재하는 경우")
      void testMixField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test3rdDTO.EntityRecord entityRecord = new Test3rdDTO.EntityRecord(entity, entity2);

        // When
        Test3rdDTO dto = converter.convert(entityRecord);

        // Then
        assertEquals(entity.getProp1(), dto.getProp1());
        assertEquals(entity.getProp2(), dto.getProp2());
        assertEquals(entity.isProp3(), dto.isProp3());
        assertEquals(entity2.getListProp1(), dto.getTest2ndEntityDTO().getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getTest2ndEntityDTO().getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getTest2ndEntityDTO().getSetProp3());
      }

      @Test
      @DisplayName("서로 다른 Nested 클래스의 필드명이 중복될 때 충돌 테스트")
      void testFieldCollision() {
        // Given
        TestCollisionEntity1 entity1 = new TestCollisionEntity1("entity1", "unique1");
        TestCollisionEntity2 entity2 = new TestCollisionEntity2("entity2", "unique2");
        TestCollisionDTO.EntityRecord entityRecord = new TestCollisionDTO.EntityRecord(entity1, entity2);

        // When
        TestCollisionDTO dto = converter.convert(entityRecord);

        // Then
        // 1. Flattening: Collision occurs, last one wins (Entity2)
        assertEquals(entity2.getCommonProp(), dto.getCommonProp());
        assertEquals(entity1.getUniqueProp1(), dto.getUniqueProp1());
        assertEquals(entity2.getUniqueProp2(), dto.getUniqueProp2());

        // 2. Nesting: Preserved correctly
        assertEquals(entity1.getCommonProp(), dto.getTestCollisionEntity1().getCommonProp());
        assertEquals(entity2.getCommonProp(), dto.getTestCollisionEntity2().getCommonProp());
      }
    }

    @Nested
    @DisplayName("Entity List to Output DTO List")
    class EntityListToDTOListTest {
      @Test
      @DisplayName("Entity의 Field가 둘 다 Root에 존재하는 경우")
      void testAllRootField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test1stDTO.EntityRecord entityRecord = new Test1stDTO.EntityRecord(entity, entity2);

        // When
        List<Test1stDTO> dtoList = converter.convert(List.of(entityRecord));

        // Then
        assertEquals(1, dtoList.size());
        Test1stDTO dto = dtoList.get(0);
        assertEquals(entity.getProp1(), dto.getProp1());
        assertEquals(entity.getProp2(), dto.getProp2());
        assertEquals(entity.isProp3(), dto.isProp3());
        assertEquals(entity2.getListProp1(), dto.getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root에 존재하지 않는 경우")
      void testNoneRootField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test2ndDTO.EntityRecord entityRecord = new Test2ndDTO.EntityRecord(entity, entity2);

        // When
        List<Test2ndDTO> dtoList = converter.convert(List.of(entityRecord));

        // Then
        assertEquals(1, dtoList.size());
        Test2ndDTO dto = dtoList.get(0);
        assertEquals(entity.getProp1(), dto.getTest1stEntityDTO().getProp1());
        assertEquals(entity.getProp2(), dto.getTest1stEntityDTO().getProp2());
        assertEquals(entity.isProp3(), dto.getTest1stEntityDTO().isProp3());
        assertEquals(entity2.getListProp1(), dto.getTest2ndEntityDTO().getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getTest2ndEntityDTO().getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getTest2ndEntityDTO().getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root와 DTO Field에 복합적으로 존재하는 경우")
      void testMixField() {
        // Given
        Test1stEntity entity = new Test1stEntity("test1", 1, true);
        Test2ndEntity entity2 = new Test2ndEntity(List.of("test2"), Map.of("test2", "test2"), Set.of("test2"));
        Test3rdDTO.EntityRecord entityRecord = new Test3rdDTO.EntityRecord(entity, entity2);

        // When
        List<Test3rdDTO> dtoList = converter.convert(List.of(entityRecord));

        // Then
        assertEquals(1, dtoList.size());
        Test3rdDTO dto = dtoList.get(0);
        assertEquals(entity.getProp1(), dto.getProp1());
        assertEquals(entity.getProp2(), dto.getProp2());
        assertEquals(entity.isProp3(), dto.isProp3());
        assertEquals(entity2.getListProp1(), dto.getTest2ndEntityDTO().getListProp1());
        assertEquals(entity2.getMapProp2(), dto.getTest2ndEntityDTO().getMapProp2());
        assertEquals(entity2.getSetProp3(), dto.getTest2ndEntityDTO().getSetProp3());
      }
    }

    @Nested
    @DisplayName("Input DTO to Entity")
    class DTOToEntityTest {
      @Test
      @DisplayName("Entity의 Field가 둘 다 Root에 존재하는 경우")
      void testAllRootField() {
        // Given
        Test1stDTO dto = new Test1stDTO(
            "test1", 1, true, 
            List.of("test2"), Map.of("test2", "test2"), Set.of("test2")
        );

        // When
        Test1stDTO.EntityRecord entityRecord = converter.convert(dto);
        Test1stEntity entity = entityRecord.test1stEntity();
        Test2ndEntity entity2 = entityRecord.test2ndEntity();

        // Then
        assertEquals(dto.getProp1(), entity.getProp1());
        assertEquals(dto.getProp2(), entity.getProp2());
        assertEquals(dto.isProp3(), entity.isProp3());
        assertEquals(dto.getListProp1(), entity2.getListProp1());
        assertEquals(dto.getMapProp2(), entity2.getMapProp2());
        assertEquals(dto.getSetProp3(), entity2.getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root에 존재하지 않는 경우")
      void testNoneRootField() {
        // Given
        Test2ndDTO.Test1stEntityDTO entityDto1 = new Test2ndDTO.Test1stEntityDTO("test1", 1, true);
        Test2ndDTO.Test2ndEntityDTO entityDto2 = new Test2ndDTO.Test2ndEntityDTO(
            List.of("test2"), Map.of("test2", "test2"), Set.of("test2")
        );
        Test2ndDTO dto = new Test2ndDTO(entityDto1, entityDto2);

        // When
        Test2ndDTO.EntityRecord entityRecord = converter.convert(dto);
        Test1stEntity entity = entityRecord.test1stEntity();
        Test2ndEntity entity2 = entityRecord.test2ndEntity();

        // Then
        assertEquals(dto.getTest1stEntityDTO().getProp1(), entity.getProp1());
        assertEquals(dto.getTest1stEntityDTO().getProp2(), entity.getProp2());
        assertEquals(dto.getTest1stEntityDTO().isProp3(), entity.isProp3());
        assertEquals(dto.getTest2ndEntityDTO().getListProp1(), entity2.getListProp1());
        assertEquals(dto.getTest2ndEntityDTO().getMapProp2(), entity2.getMapProp2());
        assertEquals(dto.getTest2ndEntityDTO().getSetProp3(), entity2.getSetProp3());
      }

      @Test
      @DisplayName("Entity의 Field가 Root와 DTO Field에 복합적으로 존재하는 경우")
      void testMixField() {
        // Given
        Test3rdDTO.Test2ndEntityDTO entityDto2 = new Test3rdDTO.Test2ndEntityDTO(
            List.of("test2"), Map.of("test2", "test2"), Set.of("test2")
        );
        Test3rdDTO dto = new Test3rdDTO(
            "test1", 1, true, entityDto2
        );

        // When
        Test3rdDTO.EntityRecord entityRecord = converter.convert(dto);
        Test1stEntity entity = entityRecord.test1stEntity();
        Test2ndEntity entity2 = entityRecord.test2ndEntity();

        // Then
        assertEquals(dto.getProp1(), entity.getProp1());
        assertEquals(dto.getProp2(), entity.getProp2());
        assertEquals(dto.isProp3(), entity.isProp3());
        assertEquals(dto.getTest2ndEntityDTO().getListProp1(), entity2.getListProp1());
        assertEquals(dto.getTest2ndEntityDTO().getMapProp2(), entity2.getMapProp2());
        assertEquals(dto.getTest2ndEntityDTO().getSetProp3(), entity2.getSetProp3());
      }
    }
}
