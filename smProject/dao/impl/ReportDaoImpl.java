package com.smProject.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.smProject.dao.ReportDao;
import com.smProject.dao.util.CamelMap;

@Repository
public class ReportDaoImpl implements ReportDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int selectReportCnt(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("report.selectReportCnt",param);
	}

	@Override
	public List<CamelMap> selectReportList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("report.selectReportList",param);
	}

	@Override
	public void cancelReport(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("report.cancelReport", param);
	}

	@Override
	public void saveLimitDt(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("report.saveLimitDt", param);
	}

	@Override
	public List<CamelMap> selectReportListAll(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("report.selectReportListAll", param);
	}
	
	@Override
	public void insertReport(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("report.insertReport", param);
	}

	@Override
	public CamelMap selectReportStat(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("report.selectReportStat", param);
	}

	@Override
	public void updateReportConf(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("report.updateReportConf", param);
	}

	@Override
	public List<CamelMap> selectMobileReportList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("report.selectMobileReportList", param);
	}

	@Override
	public void updateReport(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.update("report.updateReport", param);
	}
}
