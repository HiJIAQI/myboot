package com.itcast.dao;

import com.itcast.vo.HrVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/6/1 - 14:00
 */
@Repository
public interface HrMapper {

    @Select("select * from hr group by id desc")
    List<HrVo> list();

    @Select("select * from hr where id = #{id}")
    HrVo get(@Param("id") Long id);

    @Insert("insert into hr (username,name,phone,telephone,address) values (#{hr.userName},#{hr.name},#{hr.phone},#{hr.telephone},#{hr.address})")
    int insert(@Param("hr") HrVo hrVo);

    @Delete("delete from hr where id = #{id}")
    int delete(@Param("id") Long id);

    @Update("update hr set username = #{hr.userName} where id = #{hr.id}")
    int update(@Param("hr") HrVo hrVo);
}
