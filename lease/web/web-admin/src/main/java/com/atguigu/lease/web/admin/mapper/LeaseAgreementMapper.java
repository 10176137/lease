package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;



/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.atguigu.lease.model.LeaseAgreement
*/
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

  Page<AgreementVo> queryPage(Page<LeaseAgreement> page, @Param("ew") Wrapper<LeaseAgreement> updateWrapper, AgreementQueryVo queryVo);

  AgreementVo getAgreementVoById(Long agreementId);
}




