package com.sout.carcre.mapstruct.Bo2Vo;

import com.sout.carcre.controller.bean.QueryChip;
import com.sout.carcre.service.bean.QueryChipService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/31 11:07
 */
@Mapper
public interface QueryChipService2Chip {
    QueryChipService2Chip INSTANCE= Mappers.getMapper(QueryChipService2Chip.class);

    QueryChip queryChipService2Chip(QueryChipService queryChipService2Chip);
}
