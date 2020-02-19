package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/13.
 */
@Repository
@Mapper
public interface UserInfoMapper {
    //插入用户数据
    public int insertUserInfo(UserInfo user);

    //通过userid查询是否存在用户
    // 存在返回0,不存在返回1！！！！！！！！！！！！
    public Integer userIsSaveByUserId(Integer userId);
    public Integer userIsSaveByMobilPhone(String mobilPhone);


    //通过userId查询用户
    public UserInfo selectUserInfoByUserId(Integer userId);

    //通过手机号码查找用户
    public UserInfo selectUserInfoByMobilPhone(String mobilPhone);

    //通过userId查询碎片信息
    public String selectChipInfoByUserId(Integer userId);

    //更新用户对应的碎片信息
    public int updateChipInfoByuserId(int userId,String chipinfo);

    //更新用户对应的卡片信息
    public int updateCardInfoByUserId(int userId,String cardinfo);
}
