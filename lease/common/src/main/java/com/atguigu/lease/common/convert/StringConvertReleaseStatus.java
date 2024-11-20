package com.atguigu.lease.common.convert;

import com.atguigu.lease.model.enums.ReleaseStatus;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringConvertReleaseStatus implements Converter<String, ReleaseStatus> {
	@Override
	public ReleaseStatus convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			return (Integer.parseInt(source) == 1) ? ReleaseStatus.RELEASED : ReleaseStatus.NOT_RELEASED;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
