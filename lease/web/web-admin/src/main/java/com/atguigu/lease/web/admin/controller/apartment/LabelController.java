package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.LabelInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.service.LabelInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "标签管理")
@RestController
@RequestMapping("/admin/label")
public class LabelController {

	@Autowired
	LabelInfoService labelInfoService;

	@Operation(summary = "（根据类型）查询标签列表")
	@GetMapping("list")
	public Result<List<LabelInfo>> labelList(@RequestParam(required = false) ItemType type) {
		List<LabelInfo> list = labelInfoService.list(new LambdaQueryWrapper<LabelInfo>().eq(type != null, LabelInfo::getType, type).eq(LabelInfo::getIsDeleted, 0));
		return Result.ok(list);
	}

	@Operation(summary = "新增或修改标签信息")
	@PostMapping("saveOrUpdate")
	public Result saveOrUpdateLabel(@RequestBody LabelInfo labelInfo) {

		labelInfoService.saveOrUpdate(labelInfo);
		return Result.ok();
	}

	@Operation(summary = "根据id删除标签信息")
	@DeleteMapping("deleteById")
	public Result deleteLabelById(@RequestParam Long id) {
		LabelInfo labelInfo = new LabelInfo();
		labelInfo.setId(id);
		labelInfoService.update(labelInfo, new LambdaUpdateWrapper<LabelInfo>().set(LabelInfo::getIsDeleted, 1).eq(LabelInfo::getId, id));
		return Result.ok();
	}
}
