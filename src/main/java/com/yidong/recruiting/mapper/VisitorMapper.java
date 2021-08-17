package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.Recruit;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface VisitorMapper{
    //查询所有招聘
    @Select("select * from recruit where id=1")
    @Results({
            @Result(column = "interviewer",property = "interviewer"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "region",property = "region"),
            @Result(column = "education",property = "education"),
            @Result(column = "salary",property = "salary"),
            @Result(column = "scale_company",property = "scaleCompany"),
            @Result(column = "position_type",property = "positionType"),
            @Result(column = "position",property = "position"),
            @Result(column = "desc_po",property = "descPo"),
//            @Result(column = "interview_time",property = "interviewTime"),
            @Result(column = "num",property = "num")
    })
    List<Recruit> findAllRecruit();


    //搜索查询
    @Select("<script> " +
            "SELECT * " +
            "from recruit " +
            " <where> " +
            "  id=1" +
            " <if test=\"region != '全部'\">and region=#{region}</if> " +
            " <if test=\"education != '全部'\">and education=#{education}</if> " +
            " <if test=\"salary != '全部'\">and salary=#{salary}</if> " +
            " <if test=\"scaleCompany != '全部'\">and scale_company=#{scaleCompany}</if> " +
            " <if test=\"positionType != '全部'\">and position_type=#{positionType}</if> " +
            " </where> " +
            " </script> ")
    @Results({
            @Result(column = "interviewer",property = "interviewer"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "region",property = "region"),
            @Result(column = "education",property = "education"),
            @Result(column = "salary",property = "salary"),
            @Result(column = "scale_company",property = "scaleCompany"),
            @Result(column = "position_type",property = "positionType"),
            @Result(column = "position",property = "position"),
            @Result(column = "desc_po",property = "descPo"),
//            @Result(column = "interview_time",property = "interviewTime"),
            @Result(column = "num",property = "num")
    })
    List<Recruit> search(@Param("region") String region, @Param("education") String education, @Param("salary") String salary, @Param("scaleCompany") String scaleCompany, @Param("positionType") String positionType);
}
