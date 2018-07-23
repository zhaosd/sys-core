package com.fw121.core.rest;

import com.fw121.core.domain.R;
import com.fw121.core.domain.SysModule;
import com.fw121.core.domain.SysPermission;
import com.fw121.core.mapper.SysPermissionMapper;
import com.fw121.core.service.LocaleMessageService;
import com.fw121.core.service.SysPermissionService;
import com.fw121.core.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Api(tags ="权限管理")
@RequiresPermissions("sys.permission.admin")
@RestController
@RequestMapping(value="/permission")
public class SysPermissionController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

	private static final String model = "permission";

	@Autowired
	SysPermissionMapper permissionMapper;

	@Autowired
	SysPermissionService permissionService;

    @Autowired
    LocaleMessageService messageService;

	@Override
	public void init() {
		super.setMapper(permissionMapper);
		super.setModel(model);
	}

	@ApiOperation(value="获取列表")
	@RequestMapping(value="list",method = RequestMethod.GET)
    public R list(SysPermission permission) {
		init();
		Condition condition=new Condition(SysPermission.class);
		if (permission.getPermission() != null) {
			condition.createCriteria().andCondition("permission like '%" + permission.getPermission() + "%'");
		}
		if (permission.getName() != null) {
			condition.createCriteria().andCondition("name like '%" + permission.getName() + "%'");
		}
		return super.list(permission, condition);
    }

	@ApiOperation(value="添加权限")
	@RequestMapping(value="create",method = RequestMethod.POST)
	public R create(@RequestBody SysPermission permission) {
		init();
		return super.create(permission);
	}

	@ApiOperation(value="编辑权限")
	@RequestMapping(value="update",method = RequestMethod.POST)
	public R update(@RequestBody SysPermission permission) {
		init();
		return super.update(permission);
	}

	@ApiOperation(value="删除权限")
	@GetMapping("/remove/{id}")
	public R remove(@PathVariable Object id) {
		init();
		return super.remove(id);
	}

	@ApiOperation(value="通过模块获取权限列表")
	@GetMapping("/all")
	public R all() {
		List<SysModule> modules = permissionService.getPermissionsByModule();
		return R.ok(modules);
	}

	@ApiOperation(value="获取当前用户权限对象列表")
	@GetMapping("/my")
	public R my() {
		Integer userid = JwtUtils.getCurrentUserid();
		List<SysPermission> permissions = permissionMapper.findPermissionByUid(userid);
		return R.ok(permissions);
	}

}
