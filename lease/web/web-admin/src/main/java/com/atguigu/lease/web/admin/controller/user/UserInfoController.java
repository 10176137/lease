package com.atguigu.lease.web.admin.controller.user;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.service.UserInfoService;
import com.atguigu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, @Valid UserInfoQueryVo queryVo) {
      IPage<UserInfo> iPages = userInfoService.pageUserInfo(current, size, queryVo);
        return Result.ok(iPages);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        return Result.ok();
    }
}
