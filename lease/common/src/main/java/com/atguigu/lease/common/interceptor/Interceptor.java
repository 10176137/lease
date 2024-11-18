package com.atguigu.lease.common.interceptor;

import com.atguigu.lease.common.jjwtutil.JjwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Configuration
public class Interceptor implements HandlerInterceptor {
	@Autowired
	JjwtUtil jjwtUtil;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("access-token");
		try{
			Claims claims = JjwtUtil.parseJWT(token);
			String id = claims.getSubject();
			log.info("id:{}", id);
		}catch (Exception e){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		return true;
	}
}