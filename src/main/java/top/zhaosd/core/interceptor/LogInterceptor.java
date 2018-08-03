package top.zhaosd.core.interceptor;

import top.zhaosd.core.domain.SysLog;
import top.zhaosd.core.mapper.SysLogMapper;
import top.zhaosd.core.util.CusAccessObjectUtil;
import top.zhaosd.core.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问日志拦截器
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {   // 写入日志
            SysLog sysLog = new SysLog();
            sysLog.setAction(request.getRequestURI());
            sysLog.setParameter(request.getQueryString());
            sysLog.setAgent(request.getHeader("user-Agent"));
            sysLog.setHost(request.getHeader("host"));
            sysLog.setIp(CusAccessObjectUtil.getIpAddress(request));
            sysLog.setLanguage(request.getHeader("accept-language"));
            sysLog.setReferer(request.getHeader("referer"));
            sysLog.setOrigin(request.getHeader("origin"));
            try {
                sysLog.setUid(JwtUtils.getCurrentUserid());
            } catch (Exception e) {
                sysLog.setUid(null);
            }
            sysLogMapper.insert(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {  
    }  
}  