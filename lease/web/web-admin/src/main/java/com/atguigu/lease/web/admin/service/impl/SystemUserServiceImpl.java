package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
		implements SystemUserService {

	@Autowired
	private SystemUserMapper systemUserMapper;

	@Override
	public IPage<SystemUserItemVo> queryPage(long current, long size, SystemUserQueryVo queryVo) {
		Page<SystemUser> page = new Page<>(current, size);
		Page<SystemUserItemVo> systemUserItemVoPage = systemUserMapper.selectQueryPage(page, new LambdaQueryWrapper<SystemUser>().
				eq(queryVo.getPhone() != null ? true : false, SystemUser::getPhone, queryVo.getPhone()).
				eq(queryVo.getName() != null ? true : false, SystemUser::getName, queryVo.getName()));
		return systemUserItemVoPage;
	}

	@Override
	public SystemUserItemVo getById(Long id) {
		SystemUserItemVo systemUser = systemUserMapper.getById(id);
		return systemUser;
	}

	@Override
	public boolean isUsernameExists(String username) {
		if (systemUserMapper.selectByUsername(username) != null) {
			return false;
		}
		return true;
	}
}




