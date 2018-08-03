package top.zhaosd.core.constant;
/**
 * 系统级静态变量
 * 创建者 科帮网 
 * 创建时间	2017年11月20日
 */
public class SystemConstant {
    /**
	 * token
	 */
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	public static final int USER_LOGIN_ERROR = 4011;			// 用户登陆失败

	/**
	 * JWT
	 */
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
	public static final long JWT_TTL = 120 * 60 * 1000;									//token有效时间, 单位: 毫秒

	public static final String JWT_TOKEN_KEY = "token";			// 默认header中的token名称
}
