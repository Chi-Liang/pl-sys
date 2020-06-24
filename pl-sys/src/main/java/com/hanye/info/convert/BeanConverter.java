package com.hanye.info.convert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.cglib.core.Converter;

public class BeanConverter implements Converter {

	@Override
	public Object convert(Object value, Class target, Object context) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		if (value instanceof Date) {
			return sdf.format(value);
		} 
		if (value instanceof Set<?>) {
			return null;
		}
		
		return value;
	}

}
