package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.BaseEntity;
import com.atguigu.lease.model.entity.GraphInfo;
import com.atguigu.lease.model.entity.RoomAttrValue;
import com.atguigu.lease.model.entity.RoomInfo;
import com.atguigu.lease.model.enums.ReleaseStatus;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.atguigu.lease.web.admin.vo.room.RoomDetailVo;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.atguigu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "房间信息管理")
@RestController
@RequestMapping("/admin/room")
public class RoomController {

	@Autowired
	RoomInfoService roomInfoService;

	@Autowired
	RoomAttrValueService roomAttrValueService;

	@Autowired
	RoomFacilityService facilityService;

	@Autowired
	RoomLabelService roomLabelService;

	@Autowired
	RoomLeaseTermService roomLeaseTermService;

	@Autowired
	GraphInfoService graphInfoService;

	@Autowired
	RoomPaymentTypeService roomPaymentTypeService;

	@Operation(summary = "保存或更新房间信息")
	@PostMapping("saveOrUpdate")
	public Result saveOrUpdate(@RequestBody RoomSubmitVo roomSubmitVo) {
		RoomInfo roomInfo = new RoomInfo();
		BeanUtils.copyProperties(roomSubmitVo, roomInfo);
		roomInfoService.saveOrUpdate(roomInfo);

		// old
		List<RoomAttrValue> attrValuelist = roomAttrValueService.list(new LambdaQueryWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, roomInfo.getId()));
		// new
		List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();

		// old - new 删除
		List<RoomAttrValue> deleteRoomAttValueList = attrValuelist.stream().filter(alist -> attrValueIds.stream().noneMatch(id -> id.equals(alist.getAttrValueId()))).toList();
		deleteRoomAttValueList.forEach(alist->alist.setIsDeleted(Byte.valueOf("1")));
		roomAttrValueService.updateBatchById(deleteRoomAttValueList);

		// new-old 添加
		List<RoomAttrValue> AddRoomAttValueList = attrValueIds.stream().filter(id -> attrValuelist.stream().map(RoomAttrValue::getRoomId).noneMatch(atid -> atid.equals(id))).map(id->RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(id).build()).toList();
		AddRoomAttValueList.forEach(av->roomAttrValueService.save(av));


		List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
		List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
		List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
		List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
		List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();


		return Result.ok();
	}

	@Operation(summary = "根据条件分页查询房间列表")
	@GetMapping("pageItem")
	public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
		IPage<RoomItemVo> page = roomInfoService.pageItem(current,size,queryVo);
		return Result.ok();
	}

	@Operation(summary = "根据id获取房间详细信息")
	@GetMapping("getDetailById")
	public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
		return Result.ok();
	}

	@Operation(summary = "根据id删除房间信息")
	@DeleteMapping("removeById")
	public Result removeById(@RequestParam Long id) {
		return Result.ok();
	}

	@Operation(summary = "根据id修改房间发布状态")
	@PostMapping("updateReleaseStatusById")
	public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
		return Result.ok();
	}

	@GetMapping("listBasicByApartmentId")
	@Operation(summary = "根据公寓id查询房间列表")
	public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
		return Result.ok();
	}

}


















