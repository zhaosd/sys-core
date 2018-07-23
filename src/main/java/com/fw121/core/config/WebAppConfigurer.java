package com.fw121.core.config;

import com.fw121.core.interceptor.LogInterceptor;
import com.fw121.core.interceptor.TokenInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 拦截配置--调用链
 * 创建者  科帮网 
 * 创建时间  2017年11月24日
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurationSupport {

	private static final Logger logger = LoggerFactory.getLogger(WebAppConfigurer.class);

	//关键，将拦截器作为bean写入配置中
	@Bean
	public TokenInterceptor tokenInterceptor(){
		return new TokenInterceptor();
	}

	@Bean
	public LogInterceptor logInterceptor(){
		return new LogInterceptor();
	}

	/**
	 * 在配置文件中配置的文件保存路径
	 */
	@Value("${file.location}")	// 文件保存路径
	private String location;
	@Value("${file.path}")	// 文件url根路径
	private String filePath;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(logInterceptor())
				.addPathPatterns("/**");

		String[] patterns = new String[] { "/login/**","/error","/*.html","/swagger-resources/**"};
		// token判断拦截器
		registry.addInterceptor(tokenInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns(patterns);

		super.addInterceptors(registry);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.debug("converters length: " + converters.size());
		for(int i = 0; i < converters.size(); i++) {
			HttpMessageConverter c = converters.get(i);
			logger.debug(c.toString());
			if(c instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter) c).setDefaultCharset(Charset.forName("UTF-8"));
			}
		}
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//文件最大KB,MB
		factory.setMaxFileSize("20MB");
		//设置总上传数据总大小
		factory.setMaxRequestSize("50MB");
		return factory.createMultipartConfig();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(filePath + "**").addResourceLocations("file:" + location);
		super.addResourceHandlers(registry);
	}

}