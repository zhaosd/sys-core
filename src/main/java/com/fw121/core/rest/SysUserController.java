package com.fw121.core.rest;

import com.fw121.core.domain.R;
import com.fw121.core.domain.SysUser;
import com.fw121.core.mapper.SysPermissionMapper;
import com.fw121.core.mapper.SysUserMapper;
import com.fw121.core.service.LocaleMessageService;
import com.fw121.core.service.SysUserService;
import com.fw121.core.util.JwtUtils;
import com.fw121.core.util.Md5SaltTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

@Api(tags ="用户管理")
@RequiresPermissions("sys.user.admin")
@RestController
@RequestMapping(value="/user")
public class SysUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	private static final String model = "user";

	@Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserService userService;

	@Autowired
	SysPermissionMapper permissionMapper;

    @Autowired
    LocaleMessageService messageService;

	@ApiOperation(value="获取用户信息")
	@RequestMapping(value="info",method = RequestMethod.GET)
    public R info() {
		Integer userid = JwtUtils.getCurrentUserid();
		SysUser user = userMapper.selectByPrimaryKey(userid);
		user.setPassword(null);
		List<String> permissions = permissionMapper.findByUid(userid);
		user.setRoles(permissions);
		return R.ok(user);
    }

	@Override
	public void init() {
		super.setMapper(userMapper);
		super.setModel(model);
	}

	@ApiOperation(value="获取列表")
	@RequestMapping(value="list",method = RequestMethod.GET)
	public R list(SysUser user) {
		init();
		Condition condition=new Condition(SysUser.class);
		if (user.getUsername() != null) {
			condition.createCriteria().andCondition("username like '%" + user.getUsername() + "%'");
		}
		return super.list(user, condition);
	}

	@ApiOperation(value="添加用户")
	@RequestMapping(value="create",method = RequestMethod.POST)
	public R create(@RequestBody SysUser user) {
        try {
            int result = userService.create(user);
            if (result > 0) {
                return R.ok(user);
            }
        } catch (DuplicateKeyException e) {
            return R.error(messageService.getMessage(model + ".exist"));
        }
        return R.error(messageService.getMessage(model + ".create.failed"));
	}

	@ApiOperation(value="编辑用户")
	@RequestMapping(value="update",method = RequestMethod.POST)
	public R update(@RequestBody SysUser user) {
        try {
            int result = userService.update(user);
            if (result > 0) {
                return R.ok(user);
            }
        } catch (DuplicateKeyException e) {
            return R.error(messageService.getMessage(model + ".exist"));
        }
        return R.error(messageService.getMessage(model + ".update.failed"));
	}

	@ApiOperation(value="删除用户")
	@GetMapping("/remove/{id}")
	public R remove(@PathVariable Integer id) {
        int result = userService.delete(id);
        if (result > 0) {
            return R.ok(messageService.getMessage(model + ".delete.success"));
        }
        return R.error(messageService.getMessage(model + ".delete.failed"));
	}

    @ApiOperation(value="获取用户权限id列表")
    @GetMapping("/{id}/rids")
    public R getRids(@PathVariable Integer id) {
        List<Integer> rids = userMapper.getRids(id);
        return R.ok(rids);
    }

    @ApiOperation(value="修改密码")
    @RequestMapping(value="changePassword",method = RequestMethod.POST)
    public R changePassword(@RequestBody SysUser user) {
        Integer currentUid = JwtUtils.getCurrentUserid();
        SysUser currentUserInDB = userMapper.selectByPrimaryKey(currentUid);
        boolean oldPasswordValid = Md5SaltTool.validPassword(user.getPassword(), currentUserInDB.getPassword());
        if (oldPasswordValid) {
            String newPassword = Md5SaltTool.getEncryptedPwd(user.getNewPassword());
            userMapper.changePassword(currentUid, newPassword);
            return R.ok("success");
        } else {
            return R.error(messageService.getMessage(model + ".oldPasswordError"));
        }
    }

    @ApiOperation(value="个人信息设置")
    @RequestMapping(value="setting",method = RequestMethod.POST)
    public R setting(@RequestBody SysUser user) {
        Integer result = userMapper.setting(user);
        if (result > 0) {
            return R.ok("success");
        } else {
            return R.error(messageService.getMessage(model + ".setting.failed"));
        }
    }

}
