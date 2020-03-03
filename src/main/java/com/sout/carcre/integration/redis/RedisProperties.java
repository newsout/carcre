package com.sout.carcre.integration.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Data

public class RedisProperties {

    private static final String PROPERTIES_FILE = "application.properties";

    /**
     * spring.redis.database=0
     * spring.redis.host=192.168.33.200
     * spring.redis.port=6379
     * spring.redis.ssl=false
     * spring.redis.password=123456
     * spring.redis.connTimeout=5000ms
     * spring.redis.maxActive=500
     * spring.redis.maxIdle=10
     * spring.redis.minIdle=0
     * spring.redis.maxWait=5000ms
     * */
    private Integer database=0;
    private String host="jnlzw.top";
    private Integer port=6379;
    private Boolean ssl=false;
    private String password="123";
    private Integer connTimeout=5000;
    private Integer maxActive=500;
    private Integer maxIdle=10;
    private Integer minIdle=0;
    private Integer maxWait=5000;
}

