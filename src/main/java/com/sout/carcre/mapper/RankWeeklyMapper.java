package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.mapper.bean.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/21.
 */
@Repository
@Mapper
public interface RankWeeklyMapper {

    public int insertRankWeeklyData(UserInfo userInfo);

    public RankWeekly selectDataByMobilPhone(String mobilPhone);

    /*更新周排行榜的碎片数量*/
    public int updateChipNumByUserId(@Param("userId")int userId,@Param("chipNum")int chipNum);

    /*查询周排行榜用户现有的碎片数量*/
    public int seleteChipNumByUserId(int userId);

    /*更新用户周排行积分数据*/
    public int updateGradeNumByUserID(@Param("userId")int userId,@Param("gradeNum") int gradeNum);

    /*查询周排行榜用户现有积分数目*/
    public int selectGradeNumByUserId(@Param("userId") int userId);
}
