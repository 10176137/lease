package com.atguigu.lease.common.webmvc;

import com.atguigu.lease.common.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcconfiguration implements WebMvcConfigurer {
	@Autowired
	Interceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
		registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/admin/login/**");
	}
}
