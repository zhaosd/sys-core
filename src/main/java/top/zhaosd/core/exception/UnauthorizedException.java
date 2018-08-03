package top.zhaosd.core.exception;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/17.
 */
public class UnauthorizedException extends RuntimeException  {

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }

}
