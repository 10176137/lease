package com.atguigu.lease.common.exceptionhandle;

import com.atguigu.lease.common.baseexeption.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(value = BaseException.class)
	public String exceptionHandler(Exception e){
		System.out.println("全局异常捕获>>>:"+e);
		return "全局异常捕获,错误原因>>>"+e.getMessage();
	}
}
