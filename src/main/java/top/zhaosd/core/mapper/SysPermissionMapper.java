package top.zhaosd.core.mapper;

import top.zhaosd.core.domain.SysPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/13.
 */
@org.apache.ibatis.annotations.Mapper
public interface SysPermissionMapper extends Mapper<SysPermission> {

    /**
     * 查询用户所有权限的字符串列表
     * @param uid
     * @return
     */
    @Select("SELECT p.permission FROM sys_permission p left join sys_user_permission up on p.id = up.pid WHERE up.uid=#{uid} " +
            "  union " +
            "  select p.permission from sys_permission p left join sys_role_permission rp on p.id = rp.pid left join sys_user_role ur on rp.rid = ur.rid where ur.uid = #{uid} ")
    public List<String> findByUid(@Param("uid") Integer uid);

    /**
     * 查询用户所有权限对象
     * @param uid
     * @return
     */
    @Select("SELECT p.* FROM sys_permission p left join sys_user_permission up on p.id = up.pid WHERE up.uid=#{uid} " +
            "  union " +
            "  select p.* from sys_permission p left join sys_role_permission rp on p.id = rp.pid left join sys_user_role ur on rp.rid = ur.rid where ur.uid = #{uid} ")
    public List<SysPermission> findPermissionByUid(@Param("uid") Integer uid);

    /**
     * 查询某个角色所有权限对象
     * @param rid
     * @return
     */
    @Select("select p.* from sys_permission p left join sys_role_permission rp on p.id = rp.pid where rp.rid = #{rid} ")
    public List<SysPermission> findByRid(@Param("rid") Integer rid);

    /**
     * 清空角色权限
     * @param rid   角色id
     * @return
     */
    @Insert({"delete from sys_role_permission where rid = #{rid}"})
    Integer clearRolePermissions(@Param("rid") Integer rid);

    /**
     * 保存角色权限
     * @param rid   角色id
     * @param pids  权限id列表
     * @return
     */
    @Insert({"<script> insert into sys_role_permission(rid,pid) " +
            "values " +
            "<foreach collection=\"pids\" item=\"pid\" index=\"index\"  separator=\",\"> "+
            "(#{rid},#{pid})"+
            "</foreach> </script>"})
    Integer saveRolePermissions(@Param("rid") Integer rid, @Param("pids") List<Integer> pids);

}
