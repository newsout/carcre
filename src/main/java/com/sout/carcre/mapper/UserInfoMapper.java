package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/13.
 */
@Repository
@Mapper
public interface UserInfoMapper {
    //插入用户数据
    public int insertUserInfo(UserInfo user);
}
