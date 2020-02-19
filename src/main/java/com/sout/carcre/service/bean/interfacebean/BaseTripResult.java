package com.sout.carcre.service.bean.interfacebean;

import lombok.Data;

/**
 * 获取用户行程列表bean
 */
@Data
public class BaseTripResult {
    /*行程单号*/
    private String tripNo;

    /*城市id*/
    private int cityId;

    /*服务id*/
    private String serviceId;
    private String inStationId;
    private String inStationName;
    private String inLineId;
    private String inLineName;
    private String inTime;
    private String outStationId;
    private String outStationName;
    private String outLineId;
    private String outLineName;
    private String outTime;

    /*里程*/
    private int mileage;

    /*行程状态*/
    private String status;
}
