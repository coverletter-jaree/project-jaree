package org.jaree.api.core.interfaces;

import java.util.List;

import org.jaree.api.core.dto.DTO;

/**
 * @category DTO Converter
 * @apiNote Entity와 DTO 간의 변환을 위한 인터페이스
 * @version 1.0
 * @since 2025-12-24
 */
public interface DTOConverter {

  /**
   * @apiNote Entity를 DTO로 변환
   * @param record
   * @return
   */
  <EntityRecord, T extends DTO<EntityRecord>> T convert(EntityRecord record);

  /**
   * @apiNote DTO를 Entity로 변환
   * @param dto
   * @return
   */
  <EntityRecord, T extends DTO<EntityRecord>> EntityRecord convert(T dto);

  /**
   * @apiNote List<EntityRecord>를 List<DTO>로 변환
   * @param records
   * @return
   */
  <EntityRecord, T extends DTO<EntityRecord>> List<T> convert(List<EntityRecord> records);
}
