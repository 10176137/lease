package com.atguigu.lease.common.interceptor;

import com.atguigu.lease.common.jjwtutil.JjwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class Interceptor implements HandlerInterceptor {
	@Autowired
	JjwtUtil jjwtUtil;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		return false;
	}
}