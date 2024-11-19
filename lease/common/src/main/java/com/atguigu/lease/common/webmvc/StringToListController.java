package com.atguigu.lease.common.webmvc;

import com.atguigu.lease.model.enums.BaseStatus;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringToListController implements Converter<String, BaseStatus> {

	@Override
	public BaseStatus convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			return (Integer.parseInt(source) == 1) ? BaseStatus.ENABLE : BaseStatus.DISABLE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
