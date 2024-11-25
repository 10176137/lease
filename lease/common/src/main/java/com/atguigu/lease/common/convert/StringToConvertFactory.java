package com.atguigu.lease.common.convert;

import com.atguigu.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
@Component
public class StringToConvertFactory implements ConverterFactory<String, BaseEnum> {
	@Override
	public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		return source -> Arrays
				.stream(targetType.getEnumConstants())
				.filter(con -> con.getCode().equals(source))
				.findFirst()
				.orElseThrow();
	}
}
