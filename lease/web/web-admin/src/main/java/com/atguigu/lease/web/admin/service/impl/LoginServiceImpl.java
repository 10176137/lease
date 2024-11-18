package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	SystemUserMapper systemUserMapper;

	@Override
	public SystemUser login(String id){
		if(id == null || "".equals(id)){
			throw new RuntimeException();
		}
		return systemUserMapper.selectByUsername(id);
	}
}
