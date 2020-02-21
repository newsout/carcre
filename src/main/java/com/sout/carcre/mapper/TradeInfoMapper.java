package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.TradeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lzw on 2020/2/14.
 */
@Repository
@Mapper
public interface TradeInfoMapper {

    //查找商品信息
    public List<TradeInfo> selectTradeInfo();

    //查找商品对应积分数
    public int selectGradeById(int tradeId);
}
