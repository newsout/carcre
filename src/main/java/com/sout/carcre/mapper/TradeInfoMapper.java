package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.TradeInfo;
import com.sout.carcre.service.bean.TradeSell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    //查找商品库存信息以及销售量
    public TradeSell selectTradeStoAndNum(int tradeId);

    //更新商品库存信息以及销售量
    public int updateTradeStoAndNum(@Param("tradeId") int tradeId, @Param("tradeNum") int tradeNum, @Param("tradeSto")int tradeSto);
}
