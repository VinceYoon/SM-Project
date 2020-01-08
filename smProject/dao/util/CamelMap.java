package com.smProject.dao.util;

import java.math.BigDecimal;

import org.apache.commons.collections.map.ListOrderedMap;

public class CamelMap extends ListOrderedMap {

	private static final long serialVersionUID = 6723434363565852261L;

	/**
	 * key 에 대하여 Camel Case 변환하여 super.put (ListOrderedMap) 을 호출한다.
	 *
	 * @param key
	 *            - '_' 가 포함된 변수명
	 * @param value
	 *            - 명시된 key 에 대한 값 (변경 없음)
	 * @return previous value associated with specified key, or null if there was no
	 *         mapping for key
	 */
	@Override
	public Object put(Object key, Object value) {
		return super.put(CamelUtil.convert2CamelCase((String) key), value);
	}

	public String getString(String key) {
		Object object = super.get(key);
		if (object == null) {
			return "";
		}

		String objectType = object.getClass().getSimpleName();

		if ("BigDecimal".equals(objectType)) {
			BigDecimal b = (BigDecimal) object;
			return b.toString();
		}

		return (String) object;
	}

	public BigDecimal getBigDecimal(String key) {
		return (BigDecimal) super.get(key);
	}

}
