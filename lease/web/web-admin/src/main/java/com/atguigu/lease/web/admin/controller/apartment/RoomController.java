package com.atguigu.lease.web.admin.controller.apartment;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.*;
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
	@Autowired
	private LeaseTermService leaseTermService;
	@Autowired
	private RoomFacilityService roomFacilityService;

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
		deleteRoomAttValueList.forEach(alist -> alist.setIsDeleted(Byte.valueOf("1")));
		roomAttrValueService.updateBatchById(deleteRoomAttValueList);

		// new-old 添加
		List<RoomAttrValue> AddRoomAttValueList = attrValueIds.stream().filter(id -> attrValuelist.stream().map(RoomAttrValue::getRoomId).noneMatch(atid -> atid.equals(id))).map(id -> RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(id).build()).toList();
		AddRoomAttValueList.forEach(av -> roomAttrValueService.save(av));


		List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();


		List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
		roomLabelService.update(new LambdaUpdateWrapper<RoomLabel>().set(RoomLabel::getIsDeleted,1).eq(RoomLabel::getRoomId, roomInfo.getId()));
		labelInfoIds.stream().map(li->RoomLabel.builder().labelId(li).roomId(roomSubmitVo.getId()).build()).
				filter(li->!roomLabelService.update(li, new LambdaUpdateWrapper<RoomLabel>().set(RoomLabel::getIsDeleted, 0).eq(RoomLabel::getLabelId, li.getLabelId()).eq(RoomLabel::getRoomId, roomSubmitVo.getId())))
				.forEach(li->roomLabelService.save(li));

		List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
		roomLeaseTermService.update(new LambdaUpdateWrapper<RoomLeaseTerm>().set(RoomLeaseTerm::getIsDeleted,1).eq(RoomLeaseTerm::getRoomId, roomInfo.getId()));
		leaseTermIds.stream().map(li->RoomLeaseTerm.builder().leaseTermId(li) .roomId(roomSubmitVo.getId()).build())
				.filter(li->!roomLeaseTermService
						.update(li, new LambdaUpdateWrapper<RoomLeaseTerm>()
								.set(RoomLeaseTerm::getIsDeleted, 0)
								.eq(RoomLeaseTerm::getLeaseTermId, li.getLeaseTermId())
								.eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId()))).forEach(li->roomLeaseTermService.save(li));

		List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
		roomPaymentTypeService
				.update(new LambdaUpdateWrapper<RoomPaymentType>()
						.set(RoomPaymentType::getIsDeleted,1)
						.eq(RoomPaymentType::getRoomId, roomSubmitVo.getId()));
		paymentTypeIds.stream().map(pti->RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(pti).build())
				.filter(rpi->!roomPaymentTypeService.update(rpi ,new LambdaUpdateWrapper<RoomPaymentType>().
						set(RoomPaymentType::getIsDeleted,0).
						eq(RoomPaymentType::getPaymentTypeId, rpi.getPaymentTypeId()).
						eq(RoomPaymentType::getRoomId, roomSubmitVo.getId()))).forEach(li->roomPaymentTypeService.save(li));

		List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
		roomFacilityService.update(new LambdaUpdateWrapper<RoomFacility>().set(RoomFacility::getIsDeleted,1).eq(RoomFacility::getRoomId, roomSubmitVo.getId()));
		facilityInfoIds.stream().
				map(fii->RoomFacility.builder().facilityId(fii).roomId(roomSubmitVo.getId()).build()).
				filter(fii->!roomFacilityService.update(fii, new LambdaUpdateWrapper<RoomFacility>().set(RoomFacility::getIsDeleted, 0).eq(RoomFacility::getFacilityId, fii.getFacilityId()).eq(RoomFacility::getRoomId, roomSubmitVo.getId()))).forEach(li->roomFacilityService.save(li));
		return Result.ok();
	}

	@Operation(summary = "根据条件分页查询房间列表")
	@GetMapping("pageItem")
	public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
		IPage<RoomItemVo> page = roomInfoService.pageItem(current, size, queryVo);
		return Result.ok(page);
	}

	@Operation(summary = "根据id获取房间详细信息")
	@GetMapping("getDetailById")
	public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
		RoomDetailVo roomDetailVo = roomInfoService.getDetailById(id);
		return Result.ok(roomDetailVo);
	}

	@Operation(summary = "根据id删除房间信息")
	@DeleteMapping("removeById")
	public Result removeById(@RequestParam Long id) {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setId(id);
		roomInfoService.update(roomInfo, new LambdaUpdateWrapper<RoomInfo>().set(RoomInfo::getIsRelease, 1).eq(RoomInfo::getId, id));
		return Result.ok();
	}

	@Operation(summary = "根据id修改房间发布状态")
	@PostMapping("updateReleaseStatusById")
	public Result updateReleaseStatusById(Long id, ReleaseStatus status) {
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setId(id);
		roomInfoService.update(roomInfo, new LambdaUpdateWrapper<RoomInfo>().set(status!=null, RoomInfo::getIsRelease, status).eq(RoomInfo::getId, id));
		return Result.ok();
	}

	@GetMapping("listBasicByApartmentId")
	@Operation(summary = "根据公寓id查询房间列表")
	public Result<List<RoomInfo>> listBasicByApartmentId(Long id) {
		List<RoomInfo> list = roomInfoService.list(new LambdaQueryWrapper<RoomInfo>().eq(RoomInfo::getId, id));
		return Result.ok(list);
	}

}


















