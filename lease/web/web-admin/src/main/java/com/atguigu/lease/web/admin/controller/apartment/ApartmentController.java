package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ReleaseStatus;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "公寓信息管理")
@RestController
@RequestMapping("/admin/apartment")
public class ApartmentController {

	@Autowired
	ApartmentFacilityService apartmentFacilityService;
	@Autowired
	ApartmentInfoService apartmentInfoService;
	@Autowired
	ApartmentFeeValueService apartmentFeeValueService;
	@Autowired
	ApartmentLabelService apartmentLabelService;

	@Autowired
	GraphInfoService graphInfoService;

	@Operation(summary = "保存或更新公寓信息")
	@PostMapping("saveOrUpdate")
	public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
		apartmentInfoService.saveOrUpdate(apartmentSubmitVo);

		ApartmentLabel apartmentLabel = ApartmentLabel.builder().build();
		apartmentLabel.setIsDeleted(Byte.valueOf("1"));

		ApartmentFacility apartmentFacility = ApartmentFacility.builder().build();
		apartmentFacility.setIsDeleted(Byte.valueOf("1"));

		ApartmentFeeValue apartmentFeeValue = ApartmentFeeValue.builder().build();
		apartmentFeeValue.setIsDeleted(Byte.valueOf("1"));

		apartmentLabelService.update(apartmentLabel, new LambdaUpdateWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId()));

		apartmentFacilityService.update(apartmentFacility, new LambdaUpdateWrapper<ApartmentFacility>().eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId()));

		apartmentFeeValueService.update(apartmentFeeValue, new LambdaUpdateWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId()));

		apartmentSubmitVo.getFacilityInfoIds().stream().forEach(apartmentInfoId -> {
			apartmentFacilityService.saveOrUpdate(apartmentInfoId, apartmentSubmitVo.getId());
		});

		apartmentSubmitVo.getFeeValueIds().stream().forEach(fi -> {
			apartmentFeeValueService.saveOrUpdate(fi, apartmentSubmitVo.getId());
		});

		apartmentSubmitVo.getLabelIds().stream().forEach(al -> {
			apartmentLabelService.saveOrUpdate(al, apartmentSubmitVo.getId());
		});
		return Result.ok();
	}

	@Operation(summary = "根据条件分页查询公寓列表")
	@GetMapping("pageItem")
	public Result<IPage<ApartmentItemVo>> pageItem(@RequestParam long current, @RequestParam long size, ApartmentQueryVo queryVo) {
		IPage<ApartmentItemVo> apartmentItemVoIPage = apartmentInfoService.pageItem(current, size, queryVo);
		return Result.ok(apartmentItemVoIPage);
	}

	@Operation(summary = "根据ID获取公寓详细信息")
	@GetMapping("getDetailById")
	public Result<ApartmentDetailVo> getDetailById(@RequestParam Long id) {
		ApartmentDetailVo detailById = apartmentInfoService.getDetailById(id);
		return Result.ok(detailById);
	}

	@Operation(summary = "根据id删除公寓信息")
	@DeleteMapping("removeById")
	public Result removeById(@RequestParam Long id) {
		apartmentInfoService.removeById(id);
		return Result.ok();
	}

	@Operation(summary = "根据id修改公寓发布状态")
	@PostMapping("updateReleaseStatusById")
	public Result updateReleaseStatusById(@RequestParam Long id, @RequestParam ReleaseStatus status) {
		ApartmentInfo apartmentInfo = new ApartmentInfo();
		apartmentInfoService.update(apartmentInfo, new LambdaUpdateWrapper<ApartmentInfo>().set(status != null, ApartmentInfo::getIsRelease, status).eq(ApartmentInfo::getId, id));
		return Result.ok();
	}

	@Operation(summary = "根据区县id查询公寓信息列表")
	@GetMapping("listInfoByDistrictId")
	public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
		List<ApartmentInfo> list = apartmentInfoService.list(new LambdaQueryWrapper<ApartmentInfo>().eq(ApartmentInfo::getDistrictId, id));
		return Result.ok(list);
	}
}














