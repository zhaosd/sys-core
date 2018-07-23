package com.fw121.core.service;

import com.fw121.core.domain.SysModule;
import com.fw121.core.domain.SysPermission;
import com.fw121.core.domain.SysRole;
import com.fw121.core.mapper.SysModuleMapper;
import com.fw121.core.mapper.SysPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/21.
 */
@Service
public class SysPermissionService {

    @Autowired
    SysModuleMapper moduleMapper;

    @Autowired
    SysPermissionMapper permissionMapper;

    /**
     * 按模块获取权限列表
     * @return
     */
    public List<SysModule> getPermissionsByModule() {
        List<SysModule> modules = moduleMapper.selectAll(); // 从数据库读取所有模块
        List<SysPermission> permissions = permissionMapper.selectAll(); // 从数据库读取所有权限
        List<SysModule> result = new ArrayList<>(); // 初始化结果集容器
        for(SysModule module : modules) {       // 循环模块
            for(SysPermission permission : permissions) {   // 循环权限
                if (permission.getMid().equals(module.getId())) {   // 如果权限所属模块等于当前模块
                    List<SysPermission> modulePermissions = module.getPermissions();  // 获取模块的权限列表
                    if (modulePermissions == null) {      // 如果模块权限列表为空
                        modulePermissions = new ArrayList<>();  // 初始化模块权限列表
                    }
                    modulePermissions.add(permission);  // 添加权限到该模块
//                    permissions.remove(permission); // 已处理过的权限从原权限列表中删除
                    module.setPermissions(modulePermissions);   // 设置模块的权限列表
                }
            }
            if(module.getPermissions() != null && module.getPermissions().size() > 0) { // 循环处理完模块包含的权限,如果有则添加到结果容器
                result.add(module);
                for(SysPermission p : module.getPermissions()) {
                    permissions.remove(p);
                }
            }
        }
        return result;
    }

    /**
     * 保存角色权限
     * @param role
     * @return
     */
    @Transactional
    public Integer saveRolePermissions(SysRole role) {
        // 先清空当前角色所有权限
        permissionMapper.clearRolePermissions(role.getId());
        Integer result = 0;
        if (role.getPids() != null && role.getPids().size() > 0) {  // 如果有权限,则保存
            result = permissionMapper.saveRolePermissions(role.getId(), role.getPids());
        }
        return result;
    }

}
