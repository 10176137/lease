package com.atguigu.lease.web.admin.controller.login;


import com.atguigu.lease.common.interceptor.Interceptor;
import com.atguigu.lease.common.jjwtutil.JjwtUtil;
import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JjwtUtil jjwtUtil;
    @Operation(summary = "获取图形验证码")
    @GetMapping("/login/captcha")
    public Result<CaptchaVo> getCaptcha() {

        return Result.ok();
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginVo loginVo) throws Exception {
        Map<String,String> claims = new HashMap();
        String token = jjwtUtil.createJWT(null, null, "admin", claims);
        return Result.ok(token);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        return Result.ok();
    }
}