package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.SystemUser
*/

public interface SystemUserMapper extends BaseMapper<SystemUser> {
   @Select("select * from system_user where username = #{username}")
	SystemUser selectByUsername(String username);

	Page<SystemUserItemVo> selectQueryPage(Page<SystemUser> page, @Param("ew") Wrapper<SystemUser> queryWrapper);

	SystemUserItemVo getById(Long id);


}




