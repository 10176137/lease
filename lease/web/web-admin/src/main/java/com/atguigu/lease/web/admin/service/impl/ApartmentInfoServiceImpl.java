package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.service.ApartmentInfoService;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
		implements ApartmentInfoService {
	@Autowired
	private ApartmentInfoMapper apartmentInfoMapper;

	@Override
	public IPage<ApartmentItemVo> pageItem(long current, long size, ApartmentQueryVo queryVo) {
		Page<ApartmentInfo> pageInfo = new Page<>(current, size);
		IPage<ApartmentItemVo>  page = apartmentInfoMapper.pageItem(pageInfo, new LambdaQueryWrapper<ApartmentInfo>()
				.eq(queryVo.getCityId()!=null, ApartmentInfo::getCityId, queryVo.getCityId())
				.eq(queryVo.getDistrictId()!=null, ApartmentInfo::getDistrictId, queryVo.getDistrictId())
		);
		return page;
	}

	@Override
	public ApartmentDetailVo getDetailById(Long id) {
		ApartmentDetailVo apartmentDetailVo = apartmentInfoMapper.getDetailById(id);
		return apartmentDetailVo;
	}
}




