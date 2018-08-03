package top.zhaosd.core.interceptor;

import top.zhaosd.core.constant.SystemConstant;
import top.zhaosd.core.domain.CheckResult;
import top.zhaosd.core.domain.R;
import top.zhaosd.core.service.LocaleMessageService;
import top.zhaosd.core.util.JwtUtils;
import top.zhaosd.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 用户权限校验
 * 创建者   科帮网 
 * 创建时间  2017年11月24日
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    
	private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    LocaleMessageService messageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	if (handler instanceof HandlerMethod){
    		String authHeader = request.getHeader(SystemConstant.JWT_TOKEN_KEY);
        	if (StringUtils.isEmpty(authHeader)) {
                String msg = messageService.getMessage("token.not.exist");
                logger.info(msg);
                ResponseUtil.print(response, R.error(SystemConstant.JWT_ERRCODE_NULL, msg));
                return false;
            }else{
            	//验证JWT的签名，返回CheckResult对象
                CheckResult checkResult = JwtUtils.validateJWT(authHeader);
                if (checkResult.isSuccess()) {
                	return true;
                } else {
                    switch (checkResult.getErrCode()) {
                    // 签名验证不通过
                    case SystemConstant.JWT_ERRCODE_FAIL:
                        String msg = messageService.getMessage("token.failed");
                        logger.info(msg);
                        ResponseUtil.print(response,R.error(checkResult.getErrCode(), msg));
                    	break;
                     // 签名过期，返回过期提示码
                    case SystemConstant.JWT_ERRCODE_EXPIRE:
                        msg = messageService.getMessage("token.expired");
                        logger.info(msg);
                        ResponseUtil.print(response,R.error(checkResult.getErrCode(), msg));
                    	break;
                    default:
                        break;
                    }
                    return false;
                }
            }
		}else{
			return true;
		}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(response.getStatus()==500){
//            print(response,R.error(500,"error"));
        }else if(response.getStatus()==404){
//            print(response,R.error(404,"Not found"));
        }  
    }  
  
    /**  
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，  
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。  
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {  
    }  
}  