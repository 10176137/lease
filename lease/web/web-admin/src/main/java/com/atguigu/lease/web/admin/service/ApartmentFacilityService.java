package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.ApartmentFacility;
import com.atguigu.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentFacilityService extends IService<ApartmentFacility> {
	boolean saveOrUpdate(Long apartmentFacilityId, long apartmentId);
}
