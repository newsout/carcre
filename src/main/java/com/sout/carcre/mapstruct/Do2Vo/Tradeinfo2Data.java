package com.sout.carcre.mapstruct.Do2Vo;

import com.sout.carcre.controller.bean.beanson.TradeData;
import com.sout.carcre.mapper.bean.TradeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/20 20:30
 */
@Mapper
public interface Tradeinfo2Data {

    Tradeinfo2Data INSTANCE= Mappers.getMapper(Tradeinfo2Data.class);

//   @Mappings({
//           @Mapping(source = "tradeGrade",target = "tradeHeight")
//   })
    /*转换函数*/
    TradeData tradeInfo2Data(TradeInfo tradeInfo);
}
