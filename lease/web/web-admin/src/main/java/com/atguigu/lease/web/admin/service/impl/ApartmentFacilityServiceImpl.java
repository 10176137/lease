package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.ApartmentFacility;
import com.atguigu.lease.web.admin.service.ApartmentFacilityService;
import com.atguigu.lease.web.admin.mapper.ApartmentFacilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class ApartmentFacilityServiceImpl extends ServiceImpl<ApartmentFacilityMapper, ApartmentFacility>
    implements ApartmentFacilityService{

	@Autowired
	private ApartmentFacilityMapper apartmentFacilityMapper;

	@Override
	public boolean  saveOrUpdate(Long apartmentFacilityId, long apartmentId){
		List<ApartmentFacility> apartmentFacilities = apartmentFacilityMapper.selectList(new LambdaQueryWrapper<ApartmentFacility>().eq(ApartmentFacility::getFacilityId, apartmentFacilityId).eq(ApartmentFacility::getApartmentId, apartmentId));
        if(apartmentFacilities.size() == 0){
	        apartmentFacilityMapper.insert(ApartmentFacility.builder().apartmentId(apartmentId).facilityId(apartmentFacilityId).build());
			return true;
        }
		apartmentFacilities.get(0).setIsDeleted(Byte.valueOf("0"));
		apartmentFacilityMapper.update(apartmentFacilities.get(0), new LambdaUpdateWrapper<ApartmentFacility>().eq(ApartmentFacility::getId, apartmentFacilities.get(0).getId()));

		return true;
	}
}




