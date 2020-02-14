package com.sout.carcre.mapper.bean;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

/**
 * Created by lzw on 2020/2/13.
 */
@Data
public class CardInfo {
    private long id;
    private Integer cardId;
    private Integer cardIsLimit;
    private Integer cardHeight;
    private Integer cardGrade;
    private String cardContent;
    private String cardPath;
    private Integer cardIsUsable;
    private String crateTime;
}
