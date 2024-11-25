package com.atguigu.lease.common.convert;

import com.atguigu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringConvertItemType implements Converter<String , ItemType> {
	@Override
	public ItemType convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		try {
			return (Integer.parseInt(source) == 1) ? ItemType.APARTMENT : ItemType.ROOM;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
