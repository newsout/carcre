package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.CollList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/14.
 */
@Repository
@Mapper
public interface CollListMapper {

    /*插入用户获得随机碎片记录*/
    public int insertChipLogByCollList(@Param("userId") int userId, @Param("chipId")String chipId);

}
