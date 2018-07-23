package com.fw121.core.rest;

import com.fw121.core.constant.SystemConstant;
import com.fw121.core.domain.CheckResult;
import com.fw121.core.domain.R;
import com.fw121.core.domain.SysUser;
import com.fw121.core.mapper.SysUserMapper;
import com.fw121.core.service.LocaleMessageService;
import com.fw121.core.shiro.JWTToken;
import com.fw121.core.util.JwtUtils;
import com.fw121.core.util.Md5SaltTool;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags ="用户登陆退出等系统功能")
@RestController
@RequestMapping(value="/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
    SysUserMapper userMapper;

    @Autowired
    LocaleMessageService messageService;
	
	@ApiOperation(value="用户登陆")
	@RequestMapping(value="login",method = RequestMethod.POST)
    public R login(@RequestBody SysUser user) {
        logger.debug("username:" + user.getUsername() + ", " + user.getPassword());
		SysUser userInDB = userMapper.findByUserName(user.getUsername());
		logger.debug("userInDB: " + userInDB);
		if(userInDB!=null && Md5SaltTool.validPassword(user.getPassword(), userInDB.getPassword())){
            //把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
            String accessToken = JwtUtils.createJWT(userInDB.getId().toString(), userInDB.getUsername(), SystemConstant.JWT_TTL);
            String refreshToken = JwtUtils.createJWT(userInDB.getId().toString(), JwtUtils.REFRESH_TOKEN_KEY, SystemConstant.JWT_TTL);
            logger.debug("return token: " + accessToken);
            return makeTokenResult(accessToken, refreshToken);
		}else{
            return R.error(SystemConstant.USER_LOGIN_ERROR, messageService.getMessage("login.error"));
		}
    }

	@ApiOperation(value="刷新token")
	@RequestMapping(value="refresh",method = RequestMethod.POST)
	public R refreshToken(String refreshToken) {
		logger.debug("refreshToken: " + refreshToken);
		if(refreshToken!=null){
			CheckResult checkResult = JwtUtils.validateJWT(refreshToken);
			if (checkResult.isSuccess()) {
				Claims c = checkResult.getClaims();
				if(c.getSubject().equals(JwtUtils.REFRESH_TOKEN_KEY)) {
					SysUser u = userMapper.selectByPrimaryKey(Integer.parseInt(c.getId()));
					String accessToken = JwtUtils.createJWT(u.getId().toString(), u.getUsername(), SystemConstant.JWT_TTL);
					refreshToken = JwtUtils.createJWT(u.getId().toString(), JwtUtils.REFRESH_TOKEN_KEY, SystemConstant.JWT_TTL);
					return makeTokenResult(accessToken, refreshToken);
				}
			} else {
				switch (checkResult.getErrCode()) {
					case SystemConstant.JWT_ERRCODE_FAIL: // 签名验证不通过
						return R.error(checkResult.getErrCode(),"签名验证不通过");
					case SystemConstant.JWT_ERRCODE_EXPIRE: // 签名过期，返回过期提示码
						return R.error(checkResult.getErrCode(),"签名过期");
					default:
						break;
				}
				return R.error(checkResult.getErrCode(),"签名错误");
			}
		}
		return R.error("刷新token错误");
	}

    @ApiOperation(value="用户退出")
    @RequestMapping(value="logout",method = RequestMethod.POST)
    public R logout() {
        SecurityUtils.getSubject().logout();
        return R.ok("logout success");
    }

	/**
	 * 构造token返回结果对象
	 * @param accessToken
	 * @param refreshToken
     * @return
     */
	private R makeTokenResult(String accessToken, String refreshToken) {
		Map result = new HashMap<String, String>();
		result.put("access_token", accessToken);
		result.put("refresh_token", refreshToken);
		result.put("expired", (SystemConstant.JWT_TTL / 1000));	// 时效时长, 单位: 秒
        JWTToken jwtToken = new JWTToken(accessToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        SecurityUtils.getSubject().login(jwtToken);
		return R.ok(result);
	}

}
