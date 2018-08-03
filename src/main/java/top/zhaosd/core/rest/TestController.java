package top.zhaosd.core.rest;

import top.zhaosd.core.domain.R;
import top.zhaosd.core.service.LocaleMessageService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags ="测试")
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private LocaleMessageService messageService;

	@RequestMapping("/hello")
	public String hello(){
		String msg = messageService.getMessage("welcome");
        logger.info(msg);
		return  msg;
	}

	@GetMapping("/article")
	public R article() {
		Subject subject = SecurityUtils.getSubject();
		System.out.println("principal: " + subject.getPrincipal());
		if (subject.isAuthenticated()) {
			return R.ok("You are already logged in");
		} else {
			return R.ok("You are guest");
		}
	}

	@GetMapping("/require_auth")
	@RequiresAuthentication
	public R requireAuth() {
		return R.ok("You are authenticated");
	}

	@GetMapping("/require_role")
	@RequiresRoles("admin")
	public R requireRole() {
		return R.ok("You are visiting require_role");
	}

	@GetMapping("/require_permission")
	@RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
	public R requirePermission() {
		return R.ok("You are visiting permission require edit,view");
	}

	@RequestMapping(path = "/401")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public R unauthorized() {
		return R.error(401, "Unauthorized");
	}

}
