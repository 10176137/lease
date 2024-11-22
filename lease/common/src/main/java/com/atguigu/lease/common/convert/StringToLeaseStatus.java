package com.atguigu.lease.common.convert;

import com.atguigu.lease.model.enums.AppointmentStatus;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLeaseStatus implements Converter<String, LeaseStatus> {
	@Override
	public LeaseStatus convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			switch (source) {
				case "1": return LeaseStatus.SIGNING;
				case "2": return LeaseStatus.SIGNED;
				case "3": return LeaseStatus.CANCELED;
				case "4": return LeaseStatus.EXPIRED;
				case "5": return LeaseStatus.WITHDRAWING;
				case "6": return LeaseStatus.WITHDRAWN ;
				case "7": return LeaseStatus.RENEWING;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
