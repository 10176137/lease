package com.atguigu.lease.web.admin.controller.lease;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.ViewAppointment;
import com.atguigu.lease.model.enums.AppointmentStatus;
import com.atguigu.lease.web.admin.service.ViewAppointmentService;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {

	@Autowired
	ViewAppointmentService viewAppointmentService;

	@Operation(summary = "分页查询预约信息")
	@GetMapping("page")
	public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
		IPage<AppointmentVo> appointmentVo = viewAppointmentService.pageQuery(current, size, queryVo);
		return Result.ok(appointmentVo);
	}

	@Operation(summary = "根据id更新预约状态")
	@PostMapping("updateStatusById")
	public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
		ViewAppointment viewAppointment = new ViewAppointment();
		viewAppointment.setId(id);
		viewAppointmentService.update(viewAppointment,new LambdaUpdateWrapper<ViewAppointment>().eq(ViewAppointment::getId, id).set(ViewAppointment::getAppointmentStatus, status));
		return Result.ok();
	}

}
