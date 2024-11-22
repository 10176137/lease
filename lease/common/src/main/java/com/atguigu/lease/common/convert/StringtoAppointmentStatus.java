package com.atguigu.lease.common.convert;

import com.atguigu.lease.model.enums.AppointmentStatus;
import com.atguigu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringtoAppointmentStatus implements Converter<String, AppointmentStatus> {
	@Override
	public AppointmentStatus convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			switch (source) {
				case "1": return AppointmentStatus.WAITING;
				case "2": return AppointmentStatus.CANCELED;
				case "3": return AppointmentStatus.VIEWED;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
