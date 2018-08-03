package top.zhaosd.core.service;

import top.zhaosd.core.domain.SysUser;
import top.zhaosd.core.mapper.SysUserMapper;
import top.zhaosd.core.util.Md5SaltTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/21.
 */
@Service
public class SysUserService {

    @Autowired
    SysUserMapper userMapper;

    /**
     * 保存用户信息,包含用户的角色列表
     * @param user
     * @return
     */
    @Transactional
    public Integer create(SysUser user) {
        // 保存用户信息
        String passwordHash = Md5SaltTool.getEncryptedPwd(user.getPassword());
        user.setPassword(passwordHash);
        Integer result = userMapper.insert(user);
        // 保存用户角色列表
        if(user.getRids() != null && user.getRids().size() > 0) {
            result += userMapper.saveRoles(user.getId(), user.getRids());
        }
        return result;
    }

    /**
     * 修改用户信息,包含用户的角色列表
     * @param user
     * @return
     */
    @Transactional
    public Integer update(SysUser user) {
        // 保存用户信息
        Integer result = userMapper.updateByPrimaryKey(user);
        // 清空现有角色
        result += userMapper.clearRoles(user.getId());
        // 保存用户新角色列表
        if(user.getRids() != null && user.getRids().size() > 0) {
            result += userMapper.saveRoles(user.getId(), user.getRids());
        }
        return result;
    }

    /**
     * 删除用户信息,包含用户的角色列表
     * @param id 用户id
     * @return
     */
    @Transactional
    public Integer delete(Integer id) {
        // 删除用户角色列表
        Integer result = userMapper.clearRoles(id);
        // 删除用户信息
        result += userMapper.deleteByPrimaryKey(id);
        return result;
    }

}
