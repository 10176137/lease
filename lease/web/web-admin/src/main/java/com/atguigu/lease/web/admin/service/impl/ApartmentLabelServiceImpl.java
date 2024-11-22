package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.ApartmentFeeValue;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.ApartmentLabel;
import com.atguigu.lease.web.admin.service.ApartmentLabelService;
import com.atguigu.lease.web.admin.mapper.ApartmentLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_label(公寓标签关联表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentLabelServiceImpl extends ServiceImpl<ApartmentLabelMapper, ApartmentLabel>
		implements ApartmentLabelService {

	@Autowired
	private ApartmentLabelMapper apartmentLabelMapper;

	@Override
	public boolean  saveOrUpdate(Long apartmentLabelId, long apartmentId){
		List<ApartmentLabel> apartmentLabels = apartmentLabelMapper.selectList(new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getLabelId, apartmentLabelId).eq(ApartmentLabel::getApartmentId, apartmentId));

		if(apartmentLabels.size() == 0){
			apartmentLabelMapper.insert(ApartmentLabel.builder().apartmentId(apartmentId).labelId(apartmentLabelId).build());
			return true;
		}
		apartmentLabels.get(0).setIsDeleted(Byte.valueOf("0"));
		apartmentLabelMapper.update(apartmentLabels.get(0), new LambdaUpdateWrapper<ApartmentLabel>().eq(ApartmentLabel::getId, apartmentLabels.get(0).getId()));

		return true;
	}
}




