package top.zhaosd.core.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/17.
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
