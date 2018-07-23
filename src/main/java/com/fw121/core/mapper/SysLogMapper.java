package com.fw121.core.mapper;

import com.fw121.core.domain.SysLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/13.
 */
@org.apache.ibatis.annotations.Mapper
public interface SysLogMapper extends Mapper<SysLog> {

    @Select("SELECT l.*, u.username as user FROM sys_log l left join sys_user u on l.uid = u.id order by l.id desc limit #{limit} offset #{offset}")
    public List<SysLog> selectAllPageData(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT l.*, u.username as user FROM sys_log l left join sys_user u on l.uid = u.id where l.action like #{action} order by l.id desc limit #{limit} offset #{offset}")
    public List<SysLog> selectPageDataByAction(@Param("action") String action, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("SELECT count(1) FROM sys_log where action like #{action}")
    public Integer selectCountByAction(@Param("action") String action);

}
