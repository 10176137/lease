package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.CityInfo;
import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.web.admin.mapper.LeaseAgreementMapper;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

	@Autowired
	private LeaseAgreementMapper leaseAgreementMapper;
	@Override
	public IPage<AgreementVo> queryPage(long current, long size, AgreementQueryVo queryVo) {
		Page<LeaseAgreement> page = new Page<>(current, size);
		Page<AgreementVo> agreementVoPage = leaseAgreementMapper.queryPage(page, new LambdaQueryWrapper<LeaseAgreement>().
				eq(queryVo.getName()!=null?true:false, LeaseAgreement::getName, queryVo.getName()).
				eq(queryVo.getPhone()!=null?true:false,LeaseAgreement::getPhone, queryVo.getPhone()).
				eq(queryVo.getApartmentId()!=null?true:false,LeaseAgreement::getApartmentId,queryVo.getApartmentId()), queryVo);

		return agreementVoPage;
	}

	@Override
	public AgreementVo getAgreementVoById(Long id) {
		AgreementVo agreementVoById = leaseAgreementMapper.getAgreementVoById(id);
		return agreementVoById;
	}
}




