package com.fw121.core.mapper;

import com.fw121.core.domain.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/13.
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserMapper extends Mapper<SysUser> {

    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    public SysUser findByUserName(@Param("username") String username);

    /**
     * 清空用户角色
     * @param uid   用户id
     * @return
     */
    @Insert({"delete from sys_user_role where uid = #{uid}"})
    Integer clearRoles(@Param("uid") Integer uid);

    /**
     * 保存用户角色
     * @param uid   用户id
     * @param rids  角色id列表
     * @return
     */
    @Insert({"<script> insert into sys_user_role(uid,rid) " +
            "values " +
            "<foreach collection=\"rids\" item=\"rid\" index=\"index\"  separator=\",\"> "+
            "(#{uid},#{rid})"+
            "</foreach> </script>"})
    Integer saveRoles(@Param("uid") Integer uid, @Param("rids") List<Integer> rids);

    /**
     * 通过用户权限id列表
     * @param uid
     * @return
     */
    @Select("SELECT rid FROM sys_user_role WHERE uid=#{uid}")
    public List<Integer> getRids(@Param("uid") Integer uid);

    /**
     * 修改密码
     * @param id 用户id
     * @param password   用户密码
     * @return
     */
    @Insert({"update sys_user set password = #{password} where id = #{id}"})
    Integer changePassword(@Param("id") Integer id, @Param("password") String password);

    /**
     * 个人设置
     * @param user 用户
     * @return
     */
    @Insert({"update sys_user set first_name = #{user.firstName}, last_name = #{user.lastName}, " +
            " avatar = #{user.avatar}, email = #{user.email}, mobile = #{user.mobile}, description = #{user.description} " +
            " where id = #{user.id}"})
    Integer setting(@Param("user") SysUser user);

}
