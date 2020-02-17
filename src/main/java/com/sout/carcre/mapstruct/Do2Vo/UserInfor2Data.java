package com.sout.carcre.mapstruct.Do2Vo;

import com.sout.carcre.controller.bean.beanson.UserData;
import com.sout.carcre.mapper.bean.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserInfor2Data {
    UserInfor2Data INSTANCE= Mappers.getMapper(UserInfor2Data.class);

    @Mappings({
            @Mapping(source = "userInfo.userId",target = "userId"),
            @Mapping(source = "userInfo.nickname",target = "nickname"),
    })
    /*转换函数*/
    UserData userinfor2data(UserInfo userInfo);
}
