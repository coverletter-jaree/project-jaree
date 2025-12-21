package org.jaree.api.core.interfaces;

import java.util.List;

import org.jaree.api.core.dto.DTO;

public interface DTOConverter {
  <EntityRecord, T extends DTO<EntityRecord>> T convert(EntityRecord record);
  <EntityRecord, T extends DTO<EntityRecord>> List<T> convert(List<EntityRecord> records);
}
