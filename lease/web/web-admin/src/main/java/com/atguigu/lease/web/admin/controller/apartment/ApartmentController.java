package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.ApartmentFacility;
import com.atguigu.lease.model.entity.ApartmentFeeValue;
import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.model.entity.ApartmentLabel;
import com.atguigu.lease.model.enums.ReleaseStatus;
import com.atguigu.lease.web.admin.service.ApartmentFacilityService;
import com.atguigu.lease.web.admin.service.ApartmentFeeValueService;
import com.atguigu.lease.web.admin.service.ApartmentInfoService;
import com.atguigu.lease.web.admin.service.ApartmentLabelService;
import com.atguigu.lease.web.admin.service.impl.ApartmentFacilityServiceImpl;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
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

    @Operation(summary = "保存或更新公寓信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
        apartmentInfoService.saveOrUpdate(apartmentSubmitVo);
        apartmentSubmitVo.getFacilityInfoIds().stream().map(fi->ApartmentFacility.
                builder().
                apartmentId(apartmentSubmitVo.getId()).
                facilityId(fi).build()).forEach(apartmentFacilityService::saveOrUpdate);
//        apartmentSubmitVo.getGraphVoList().stream().map(fi->ApartmentFacility.
//                builder().
//                apartmentId(apartmentSubmitVo.getId()).
//                facilityId(fi).build()).forEach(apartmentFacilityService::saveOrUpdate);
        apartmentSubmitVo.getFeeValueIds().stream().map(fv-> ApartmentFeeValue.
                builder().
                apartmentId(apartmentSubmitVo.getId()).
                feeValueId(fv).build()).
                forEach(apartmentFeeValueService::saveOrUpdate);
        apartmentSubmitVo.getLabelIds().stream().map(ll-> ApartmentLabel.builder().apartmentId(apartmentSubmitVo.getId()).labelId(ll).build()).forEach(apartmentLabelService::saveOrUpdate);
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
        apartmentInfoService.update(apartmentInfo, new LambdaUpdateWrapper<ApartmentInfo>().set(status!=null,ApartmentInfo::getIsRelease,status).eq(ApartmentInfo::getId, id));
        return Result.ok();
    }

    @Operation(summary = "根据区县id查询公寓信息列表")
    @GetMapping("listInfoByDistrictId")
    public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
        return Result.ok();
    }
}














