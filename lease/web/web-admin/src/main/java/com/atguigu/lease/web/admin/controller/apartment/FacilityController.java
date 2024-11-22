package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.FacilityInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.service.FacilityInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "配套管理")
@RestController
@RequestMapping("/admin/facility")
public class FacilityController {

    @Autowired
    FacilityInfoService facilityInfoService;
    @Operation(summary = "[根据类型]查询配套信息列表")
    @GetMapping("list")
    public Result<List<FacilityInfo>> listFacility(@RequestParam(required = false) ItemType type) {
        List<FacilityInfo> list = facilityInfoService.list(new LambdaQueryWrapper<FacilityInfo>().eq(type!=null ,FacilityInfo::getType, type).eq(FacilityInfo::getIsDeleted, 0));
        return Result.ok(list);
    }

    @Operation(summary = "新增或修改配套信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FacilityInfo facilityInfo) {
        facilityInfoService.saveOrUpdate(facilityInfo);
        return Result.ok();
    }

    @Operation(summary = "根据id删除配套信息")
    @DeleteMapping("deleteById")
    public Result removeFacilityById(@RequestParam Long id) {
        FacilityInfo facilityInfo = new FacilityInfo();
        facilityInfo.setId(id);
        facilityInfoService.update(facilityInfo, new LambdaUpdateWrapper<FacilityInfo>().set(FacilityInfo::getIsDeleted, 1).eq(FacilityInfo::getId, id));
        return Result.ok();
    }
}
