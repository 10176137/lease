package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

	SystemUser login(String id);
}
