package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.UserInfo;
import com.sout.carcre.service.bean.UserGrade;
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
    public UserInfo selectUserInfoByUserId(@Param("userId")int userId);

    //通过手机号码查找用户
    public UserInfo selectUserInfoByMobilPhone(String mobilPhone);

    //通过userId查询碎片信息
    public String selectChipInfoByUserId(@Param("userId")int userId);

    //更新用户对应的碎片信息
    public int updateChipInfoByuserId(@Param("userId") int userId,@Param("chipinfo")String chipinfo);

    //更新用户对应的卡片信息
    public int updateCardInfoByUserId(@Param("userId")int userId,@Param("cardinfo")String cardinfo);

    //取出用户现有的碳积分数量
    public int selectExistGradebyUserId(@Param("userId")int userId);

    //更新用户所拥有的现积分数
    public int updateGradeByUserId(@Param("userId")int userId,@Param("grade")int grade);

    //查询用户现阶段拥有的碳积分数
    public UserGrade selectGradeByUserId(@Param("userId")int userId);

    //更新用户拥有得碳积分
    public int updateGradeByNew(@Param("userId")int userId,@Param("gradeAll") int gradeAll,@Param("grade")int grade);

    //查询用户领取碎片状态
    public int selectChipStatusByUserId(@Param("userId")int userId);

    //更新用户领取碎片信息
    public int updateChipStatusByUserId(@Param("userId")int userId);

    //每日更新用户获取碎片的状态
    public int updateChipStatusByDay();

    //设置数据库中用户获取碎片状态为0（方便前端测试，实际没有）
    public int updateChipStatus0ByuserId(@Param("userId")int userId);
}
