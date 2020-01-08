package com.smProject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.smProject.dao.CompanyDao;
import com.smProject.dao.FireCtrDao;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.FireCtrService;
import com.smProject.util.CommonUtil;
import com.smProject.util.DateUtil;
import com.smProject.util.PageNavigator;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.SearchVO;

@Service
public class FireCtrServiceImpl implements FireCtrService {

	@Autowired
	FireCtrDao fireCtrDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	private MessageSourceAccessor mr;

	@Override
	public Map<String, Object> getFireCtrList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 건수 조회
		int totalCount = companyDao.selectCompanyCnt(param);
		
		// 페이징 처리를 위한 세팅
		SearchVO search = new SearchVO();
		CommonUtil.pageSetting(param, search);
		
		String year = (String) param.get("year");
		String currYear = DateUtil.getDateFormat(DateUtil.getDate(), "Y");
		if (StringUtil.isEmpty(year)) {
			param.put("year", currYear);
		}
		String currMonth = DateUtil.getDateFormat(DateUtil.getDate(), "M");

		map.put("fireCtrList", fireCtrDao.selectFireCtrList(param));
		// 지역 구분 (서울경기, 서울시, 경기도)
		map.put("cityList", companyDao.selectCityList(param));
		// 방화 관리 목록
		map.put("totalCount", totalCount);
		map.put("navi", new PageNavigator(search, totalCount));
		map.put("currYear", currYear);
		map.put("currMonth", currMonth);
		map.put("paramVO", param);
		
		return map;
	}

	@Override
	public void deleteFireCtr(Map<String, Object> param) throws Exception {
		
		fireCtrDao.deleteFireCtr(param);
	}

	@Override
	public void insertFireCtr(Map<String, Object> param) throws Exception {

		String confirmDate = fireCtrDao.selectFireCtrDupChk(param);
		if (!StringUtil.isEmpty(confirmDate)) {
			throw new BizException(mr.getMessage("msg.fireCtr.dupChk") + "[" + confirmDate + "]");
		}

		fireCtrDao.insertFireCtr(param);
	}

	@Override
	public List<CamelMap> getFireCtrListAll(Map<String, Object> param) throws Exception {
		return fireCtrDao.getFireCtrListAll(param);
	}

	@Override
	public Map<String, Object> getMobileCtrList(Map<String, Object> param) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cityList", companyDao.selectCityList(param));
		map.put("guSiList", companyDao.selectGuSiAllList(param));
		map.put("fireCtrList", fireCtrDao.selectFireCtrMobileListAll(param));
		
		return map;
	}

}
