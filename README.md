**controller：**
    bean:controller层需要返回给前端的标准数据bean结构
    conroller其他类
    
**service：**
    bean:service层标准结构，可利用mapstruct将mapper层的数据转化为service，并完成service层转化为controller层标准数据返回
    service其他类
    
**mapper：**
    bean:mapper层标准结构（数据库表结构）
    mapper其他类
    
**ntegration：**
    handler：拦截器
    config：相关配置类，例如redis配置等
    redis:redis操作类
    component:规定返回给前端数据返回类型（Result实体类）
    
**mapstruct：**
    各层数据转换类
    
**scheduling：**
    定时任务

