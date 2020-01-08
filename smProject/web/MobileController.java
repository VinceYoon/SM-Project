package com.smProject.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smProject.auth.Auth;
import com.smProject.auth.AuthUtil;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.CompanyService;
import com.smProject.service.FireCtrService;
import com.smProject.service.ReportService;
import com.smProject.service.SchMngService;
import com.smProject.service.UserService;
import com.smProject.util.DateUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/mobile")
public class MobileController {

	@Autowired
	UserService userService;

	@Autowired
	CompanyService companyService;
	
	@Autowired
	FireCtrService fireCtrService;
	
	@Autowired
	SchMngService schMngService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	private Properties config;

	@Autowired
	private MessageSourceAccessor mr;
	
	/**
	 * 로그인 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public ResultVO loginProc(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "userId", "pwd")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		CamelMap map = new CamelMap();
		try {
			map = userService.loginProc(param);
			map.put("allUserList", userService.getUserAllList());
			
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		Auth auth = new Auth();
		BeanUtils.copyProperties(auth, map);
		AuthUtil.setAuth(request, auth);

		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}
	

	/**
	 * 로그 아웃
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logoutAjax")
	@ResponseBody
	public ResultVO logout(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AuthUtil.removeAuth(request);
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 앱 버전 전달
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppVersion")
	@ResponseBody
	public ResultVO getAppVersion(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("appVersionUrl", config.getProperty("site.domain") + config.getProperty("app.version.file"));
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}

	/**
	 * 스케줄 목록
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/schListAjax")
	@ResponseBody
	public ResultVO schListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (!StringUtil.isValidationCheck(param, "schDt")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation") + "[요청날짜]");
			}

			map = schMngService.getMobileSchMngList(param);
		
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}

	/**
	 * 스케줄 확인 처리
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/schMngConfProcAjax")
	@ResponseBody
	public ResultVO schMngConfProcAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {

			if (!StringUtil.isValidationCheck(param, "scheduleMngSeq", "userId")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation") + "[스케줄 번호],[사용자]");
			}

			schMngService.schMngConfProc(param);

		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO(ResultVO.SUCCESS, mr.getMessage("msg.common.suc.proc"));
	}
	
	
	/**
	 * 업체점검 이력 조회
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/schCompanyListAjax")
	@ResponseBody
	public ResultVO schCompanyListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (!StringUtil.isValidationCheck(param, "companySeq")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation") + "[업체코드]");
			}

			map = companyService.getSchCompanyList(param);
		
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}

	
	/**
	 * 방화 관리 목록 조회
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrListAjax")
	@ResponseBody
	public ResultVO fireCtrListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			if (StringUtil.isEmpty(param.get("schDt")) || param.get("schDt").toString().length() != 6) {
				System.out.println("DATE 없음");
				param.put("schDt", DateUtil.getDate("yyyyMM"));
			}
			
			map = fireCtrService.getMobileCtrList(param);
			
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}
		
		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}

	
	/**
	 * 방화관리 확인 처리
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fireCtrRegProcAjax")
	@ResponseBody
	public ResultVO fireCtrRegProcAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtil.isValidationCheck(param, "companySeq", "userId")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation") + "[방화관리키, 사용자 아이디]" );
		}

		try {
			fireCtrService.insertFireCtr(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}

	/**
	 * 보고서 목록 조회
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportListAjax")
	@ResponseBody
	public ResultVO reportListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (StringUtil.isEmpty(param.get("schDt")) || param.get("schDt").toString().length() != 6) {
				System.out.println("DATE 없음");
				param.put("schDt", DateUtil.getDate("yyyyMM"));
			}

			map = reportService.getMobileReportList(param);

		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<Map>(ResultVO.SUCCESS, map);
	}
	
	/**
	 * 보고서 확인 처리
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportConfProcAjax")
	@ResponseBody
	public ResultVO reportRegProcAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtil.isValidationCheck(param, "reportSeq", "userId", "auth")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation") + "[보고서정보, 사용자아이디, 권한]");
		}

		try {
			reportService.reportConfProcAjax(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}

}
