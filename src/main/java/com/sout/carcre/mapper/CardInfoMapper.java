package com.sout.carcre.mapper;

import com.sout.carcre.controller.bean.SynCard;
import com.sout.carcre.service.bean.ChipFromCard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lzw on 2020/2/13.
 */
@Repository
@Mapper
public interface CardInfoMapper {

    //查找卡片路经以及等级信息
    public SynCard seleteCardBycardId(int cardId);

    //根据卡片等级获得卡片ID
    public List<ChipFromCard> seleteChipByheight(int height);
}
