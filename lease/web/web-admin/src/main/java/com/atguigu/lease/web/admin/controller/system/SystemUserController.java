package com.atguigu.lease.web.admin.controller.system;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import static com.atguigu.lease.common.result.ResultCodeEnum.PARAM_ERROR;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {

	@Autowired
	private SystemUserService systemUserService;

	@Operation(summary = "根据条件分页查询后台用户列表")
	@GetMapping("page")
	public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
		IPage<SystemUserItemVo> systemUserItemVoIPage = systemUserService.queryPage(current, size, queryVo);
		return Result.ok(systemUserItemVoIPage);
	}

	@Operation(summary = "根据ID查询后台用户信息")
	@GetMapping("getById")
	public Result<SystemUserItemVo> getById(@RequestParam Long id) {
		SystemUserItemVo byId = systemUserService.getById(id);
		return Result.ok(byId);
	}

	@Operation(summary = "保存或更新后台用户信息")
	@PostMapping("saveOrUpdate")
	public Result saveOrUpdate(@RequestBody SystemUser systemUser) {
		String passMd5 = DigestUtils.md5DigestAsHex(systemUser.getPassword().getBytes());
		systemUser.setPassword(passMd5);
		try{
			systemUserService.saveOrUpdate(systemUser);
			return Result.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return Result.fail();
		}
	}

	@Operation(summary = "判断后台用户名是否可用")
	@GetMapping("isUserNameAvailable")
	public Result<Boolean> isUsernameExists(@RequestParam String username) {
		if(username == null || "".equals(username)){
			return Result.fail(PARAM_ERROR);
		}
		boolean usernameExists = systemUserService.isUsernameExists(username);
		return Result.ok(usernameExists);
	}

	@DeleteMapping("deleteById")
	@Operation(summary = "根据ID删除后台用户信息")
	public Result removeById(@RequestParam Long id) {
		systemUserService.removeById(id);
		return Result.ok();
	}

	@Operation(summary = "根据ID修改后台用户状态")
	@PostMapping("updateStatusByUserId")
	public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status) {
		if (id == null) {
			return Result.fail();
		}
		SystemUser systemUser = new SystemUser();
		systemUserService.update(systemUser, new LambdaUpdateWrapper<SystemUser>().set(SystemUser::getStatus, status).eq(SystemUser::getId, id));
		return Result.ok();
	}
}
