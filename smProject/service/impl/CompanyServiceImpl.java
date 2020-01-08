package com.smProject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.smProject.auth.AuthUtil;
import com.smProject.dao.CompanyDao;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.CompanyService;
import com.smProject.util.CommonUtil;
import com.smProject.util.PageNavigator;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.SearchVO;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	private MessageSourceAccessor mr;

	@Override
	public Map<String, Object> getCityList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityList", companyDao.selectCityList(param));
		
		return map;
	}
	
	@Override
	public List<CamelMap> getTownList(Map<String, Object> param) throws Exception {
		
		List<CamelMap> map = companyDao.selectTownList(param);
		
		//지역 상세 확인
		if (map == null || StringUtil.isEmpty(map.get(0).get("codeNm"))) {
			throw new BizException(mr.getMessage("msg.company.town.empty"));
		}
		return map;
	}
	
	@Override
	public Map<String, Object> getCompanyList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 건수 조회
		int totalCount = companyDao.selectCompanyCnt(param);
		
		// 페이징 처리를 위한 세팅
		SearchVO search = new SearchVO();
		CommonUtil.pageSetting(param, search);

		map.put("companyList", companyDao.selectCompanyList(param));
		map.put("totalCount", totalCount);
		map.put("navi", new PageNavigator(search, totalCount));
		
		// 지역 구분 (서울경기, 서울시, 경기도)
		map.put("cityList", companyDao.selectCityList(param));
		
		return map;
	}

	@Override
	public Map<String, Object> getCompanyTypeList(Map<String, Object> param) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyTypeList", companyDao.selectCompanyTypeList(param));
		
		return map;
	}

	@Override
	public void insertCompany(Map<String, Object> param) throws Exception {
		
		companyDao.insertCompany(param);
	}

	@Override
	public CamelMap getCompany(Map<String, Object> param) throws Exception {
		
		CamelMap map = companyDao.selectCompanyView(param);
		
		return map;
	}

	@Override
	public void updateCompany(Map<String, Object> param) throws Exception {
		
		companyDao.updateCompany(param);
	}

	@Override
	public void deleteCompany(Map<String, Object> params) throws Exception {
		
		companyDao.deleteCompany(params);
	}

	@Override
	public Map<String, Object> getSchCompanyList(Map<String, Object> param) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("companySchList", companyDao.selectSchCompanyList(param));

		return map;
	}

	@Override
	public List<CamelMap> getCompanyListAll(Map<String, Object> param) throws Exception {
		
		return companyDao.selectCompanyListAll(param);
	}
}
