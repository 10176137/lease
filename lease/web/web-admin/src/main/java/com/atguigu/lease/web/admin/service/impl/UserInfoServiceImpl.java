package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.admin.service.UserInfoService;
import com.atguigu.lease.web.admin.mapper.UserInfoMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
		implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public IPage<UserInfo> pageUserInfo(Long current, Long size, UserInfoQueryVo queryVo) {
		if (current == null || size == null) {
			return null;
		}
		IPage<UserInfo> userInfoIPage = new Page<>(current, size);

		userInfoIPage = userInfoMapper.
				selectPage(userInfoIPage, new LambdaQueryWrapper<UserInfo>().
						eq(queryVo.getPhone() != null, UserInfo::getPhone, queryVo.getPhone()).
						eq(queryVo.getStatus() != null, UserInfo::getStatus, queryVo.getStatus()));
		return userInfoIPage;
	}

	@Override
	public void updateStatusById(Long id, BaseStatus status) {
		userInfoMapper.update(new LambdaUpdateWrapper<UserInfo>().
				set(status != null ? true : false, UserInfo::getStatus, status).
				eq(id != null ? true : false, UserInfo::getId, id));
	}
}




