package top.zhaosd.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import top.zhaosd.core.constant.SystemConstant;
import top.zhaosd.core.domain.CheckResult;
import io.jsonwebtoken.*;
import org.apache.shiro.SecurityUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * jwt加密和解密的工具类 
 * 创建者 科帮网 
 * 创建时间 2017年11月24日
 */
public class JwtUtils {

	public static final String REFRESH_TOKEN_KEY = "refresh";

	/**
	 * 签发JWT
	 * @Author  科帮网 
	 * @param id
	 * @param subject 可以是JSON数据 尽可能少
	 * @param ttlMillis
	 * @return  String
	 * @Date	2017年11月24日
	 * 2017年11月24日  张志朋  首次创建
	 *
	 */
	public static String createJWT(String id, String subject, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setId(id)
//				.claim("username", subject)
//				.claim("userid", id)
				.setSubject(subject)   // 主题
				.setIssuer("富景天策")     // 签发者
				.setIssuedAt(now)      // 签发时间
				.signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis);
			builder.setExpiration(expDate); // 过期时间
		}
		return builder.compact();
	}
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(SystemConstant.JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(SystemConstant.JWT_SECERT);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}
	
	/**
	 * 
	 * 解析JWT字符串
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getSubject();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 获取系统当前用户名
	 * @return
     */
	public static String getCurrentUsername() {
		return getUsername(SecurityUtils.getSubject().getPrincipal().toString());
	}

	/**
	 * 获得token中的用户id
	 * @return token中包含的用户名
	 */
	public static Integer getUserid(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return Integer.parseInt(jwt.getId());
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	public static Integer getCurrentUserid() {
		if (SecurityUtils.getSubject().getPrincipal() != null) {
			return getUserid(SecurityUtils.getSubject().getPrincipal().toString());
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		//小明失效 10s
		String sc = createJWT("1","小明", 3000);
		System.out.println(sc);
		System.out.println(validateJWT(sc).getErrCode());
		System.out.println(validateJWT(sc).getClaims().getId());
		//Thread.sleep(3000);
		System.out.println(validateJWT(sc).getClaims());
	}
}
