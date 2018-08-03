package top.zhaosd.core.rest;

import top.zhaosd.core.domain.R;
import top.zhaosd.core.domain.SysPermission;
import top.zhaosd.core.domain.SysRole;
import top.zhaosd.core.mapper.SysPermissionMapper;
import top.zhaosd.core.mapper.SysRoleMapper;
import top.zhaosd.core.service.LocaleMessageService;
import top.zhaosd.core.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Api(tags ="角色管理")
@RequiresPermissions("sys.role.admin")
@RestController
@RequestMapping(value="/role")
public class SysRoleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	private static final String model = "role";

	@Autowired
	SysRoleMapper roleMapper;

	@Autowired
	SysPermissionMapper permissionMapper;

	@Autowired
	SysPermissionService permissionService;

    @Autowired
	LocaleMessageService messageService;

	@Override
	public void init() {
		super.setMapper(roleMapper);
		super.setModel(model);
	}

	@ApiOperation(value="获取列表")
	@RequestMapping(value="list",method = RequestMethod.GET)
    public R list(SysRole role) {
		init();
		Condition condition=new Condition(SysRole.class);
		if (role.getName() != null) {
			condition.createCriteria().andCondition("name like '%" + role.getName() + "%'");
		}
		return super.list(role, condition);
    }

	@ApiOperation(value="添加角色")
	@RequestMapping(value="create",method = RequestMethod.POST)
	public R create(@RequestBody SysRole role) {
		init();
		return super.create(role);
	}

	@ApiOperation(value="编辑角色")
	@RequestMapping(value="update",method = RequestMethod.POST)
	public R update(@RequestBody SysRole role) {
		init();
		return super.update(role);
	}

	@ApiOperation(value="删除角色")
	@GetMapping("/remove/{id}")
	public R remove(@PathVariable Object id) {
		init();
		return super.remove(id);
	}

	@ApiOperation(value="获取某个角色所有权限对象列表")
	@GetMapping("/{id}/permissions")
	public R permissions(@PathVariable Integer id) {
		List<SysPermission> permissions = permissionMapper.findByRid(id);
		return R.ok(permissions);
	}

	@ApiOperation(value="保存角色权限")
	@RequestMapping(value="savePermissions",method = RequestMethod.POST)
	public R savePermissions(@RequestBody SysRole role) {
		Integer result = permissionService.saveRolePermissions(role);
		return R.ok(result);
	}

	@ApiOperation(value="获取所有角色列表")
	@RequestMapping(value="all",method = RequestMethod.GET)
	public R all() {
		List<SysRole> roles = roleMapper.selectAll();
		return R.ok(roles);
	}

}
