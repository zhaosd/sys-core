package com.fw121.core.mapper;

import com.fw121.core.domain.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/13.
 */
@org.apache.ibatis.annotations.Mapper
public interface SysRoleMapper extends Mapper<SysRole> {

    /**
     * 通过用户id查找role
     * @param uid
     * @return
     */
    @Select("SELECT r.name FROM sys_role r left join sys_user_role ur on r.id = ur.rid WHERE ur.uid=#{uid}")
    public List<String> findByUid(@Param("uid") Integer uid);

}
