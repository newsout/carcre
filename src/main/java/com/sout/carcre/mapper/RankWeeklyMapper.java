package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.RankWeekly;
import com.sout.carcre.mapper.bean.UserInfo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/21.
 */
@Repository
@Mapper
public interface RankWeeklyMapper {

    public int insertRankWeeklyData(UserInfo userInfo);

    public RankWeekly selectDataByMobilPhone(String mobilPhone);
}
