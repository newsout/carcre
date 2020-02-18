package com.sout.carcre.mapper.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lzw on 2020/2/14.
 */
@Data
/*
记录用户收集碎片信息
 */
public class CollList {
    private long id;
    private Integer userId;
    private Integer collId;
    private Timestamp createTime;
}
