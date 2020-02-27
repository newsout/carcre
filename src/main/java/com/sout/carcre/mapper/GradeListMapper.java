package com.sout.carcre.mapper;

import com.sout.carcre.mapper.bean.GradeList;
import com.sout.carcre.service.bean.GradeListInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lzw on 2020/2/14.
 */
@Repository
@Mapper
public interface GradeListMapper {

    /*插入积分交易记录*/
    public int insertGradeListByRemark(@Param("gradeListInfo") GradeListInfo gradeListInfo);
}
