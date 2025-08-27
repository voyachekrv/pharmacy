package com.voyachek.pharmacy.medication.mapper.reference;

import com.voyachek.pharmacy.medication.entity.reference.RefSubstance;
import com.voyachek.pharmacy.medication.mapper.base.reference.DefaultReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер для справочника "Действующее вещество"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefSubstanceMapper extends DefaultReferenceMapper<RefSubstance> {}
