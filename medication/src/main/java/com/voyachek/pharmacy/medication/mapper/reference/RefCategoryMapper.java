package com.voyachek.pharmacy.medication.mapper.reference;

import com.voyachek.pharmacy.medication.entity.reference.RefCategory;
import com.voyachek.pharmacy.medication.mapper.base.reference.DefaultReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Маппер для справочника "Категория препарата"
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefCategoryMapper extends DefaultReferenceMapper<RefCategory> {}
