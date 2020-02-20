package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.TradeList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/14.
 */
@Repository
@Mapper
public interface TradeListMapper {
    /*插入用户交易商品记录*/
    public int insertTradeBytradeList(TradeList tradeList);
}
