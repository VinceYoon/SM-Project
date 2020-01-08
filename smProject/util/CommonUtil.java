package com.smProject.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.smProject.dao.util.CamelMap;
import com.smProject.vo.SearchVO;

public class CommonUtil {

	/**
	 * 리스트를 map 으로 변경 key, value
	 *
	 * @param list
	 * @param keyFiledNm
	 * @param valFiledNm
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, String> cvtMap(Collection<T> list, String keyFiledNm, String valFiledNm)
			throws Exception {
		if (list == null || list.isEmpty()) {
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();
		for (T el : list) {
			String key = BeanUtils.getProperty(el, keyFiledNm);
			String value = BeanUtils.getProperty(el, valFiledNm);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 리스트를 map 으로 컨버트
	 *
	 * @param list
	 * @param keyFiledNm
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, T> cvtMap(Collection<T> list, String keyFiledNm) throws Exception {
		if (list == null || list.isEmpty()) {
			return null;
		}

		Map<String, T> map = new HashMap<String, T>();
		for (T el : list) {
			String value = BeanUtils.getProperty(el, keyFiledNm);
			map.put(value, el);
		}

		return map;
	}

	/**
	 * 키값으로 리스트 그룹핑된 맵 return
	 *
	 * @param list
	 * @param keyFiledNm
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, List<T>> cvtMapListGrouping(List<T> list, String keyFiledNm) throws Exception {
		if (list == null || list.isEmpty()) {
			return null;
		}

		Map<String, List<T>> map = new HashMap<String, List<T>>();
		String temp = "";
		String key = "";

		for (T el : list) {
			key = BeanUtils.getProperty(el, keyFiledNm);

			if (!temp.equals(key) && !map.containsKey(key)) {
				map.put(key, new ArrayList<T>());
			}

			map.get(key).add(el);
			temp = key;
		}

		return map;
	}

	/**
	 * 배열 null 체크
	 *
	 * @param arr
	 * @return
	 */
	public static boolean isEmpty(Object[] arr) {
		return (arr == null || arr.length == 0);
	}

	/**
	 * 배열 null 체크
	 *
	 * @param arr
	 * @return
	 */
	public static boolean isNotEmpty(Object[] arr) {
		return !isEmpty(arr);
	}

	/**
	 * 리스트 empty 체크
	 *
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> list) {
		return (list == null || list.isEmpty());
	}

	/**
	 * 리스트 not empty 체크
	 *
	 * @param list
	 * @return
	 */
	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}
	
	/**
	 * 페이징 처리
	 * @param param
	 * @param searchVO
	 */
	public static void pageSetting(Map<String, Object> param, SearchVO searchVO) {

		if (!StringUtil.isEmpty(param.get("pageNo"))) {
			searchVO.setPageNo(Integer.parseInt(param.get("pageNo").toString()));
		}

		param.put("beginUnitPage", searchVO.getStartRow() - 1);
		param.put("rowSize", searchVO.getRowSize());

	}

}
