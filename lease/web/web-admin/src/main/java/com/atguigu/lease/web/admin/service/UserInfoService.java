package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【user_info(用户信息表)】的数据库操作Service
 * @createDate 2023-07-24 15:48:00
 */
public interface UserInfoService extends IService<UserInfo> {
	IPage<UserInfo> pageUserInfo(Long current, Long size, UserInfoQueryVo queryVo);

	void updateStatusById(Long id, BaseStatus status);
}
