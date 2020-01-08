package com.smProject.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smProject.auth.AuthUtil;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.SchMngService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.DateUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/schMng")
public class SchMngController {

	@Autowired
	SchMngService schMngService;
	
	@Autowired
	private MessageSourceAccessor mr;
	
	/**
	 * 일정관리 목록
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngList", method = RequestMethod.GET)
	public String userList(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {
		
		model.addAttribute("currYear", DateUtil.getDateFormat(DateUtil.getDate(), "Y"));

		return request.getAttribute("VIEW_PATH") + "/schMng/schMngList";

	}
	
	/**
	 * 일정 등록 폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngRegForm", method = RequestMethod.GET)
	public String schMngRegForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		model.addAllAttributes(schMngService.getSchGubunList(param));
		
		return request.getAttribute("VIEW_PATH") + "/schMng/schMngRegForm";

	}
	
	/**
	 * 일정 조회
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngFormAjax")
	@ResponseBody
	public ResultVO schMngFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<CamelMap> map = new ArrayList<CamelMap>();
		
		try {
			param.put("userId", AuthUtil.getAuth(request).getUserId());
			param.put("auth", AuthUtil.getAuth(request).getAuth());
			map = schMngService.getSchMngList(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<List<CamelMap>>(ResultVO.SUCCESS, map);
	}
	
	/**
	 * 일정 수정 폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngEditForm", method = RequestMethod.GET)
	public String schMngEditForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {
		
		model.addAllAttributes(schMngService.getSchMng(param));

		return request.getAttribute("VIEW_PATH") + "/schMng/schMngEditForm";

	}
	
	/**
	 * 일정 상세
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngView", method = RequestMethod.GET)
	public String schMngView(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {
		
		model.addAllAttributes(schMngService.getSchMng(param));

		return request.getAttribute("VIEW_PATH") + "/schMng/schMngView";

	}

	/**
	 * 일정 등록 처리
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngRegFormAjax")
	@ResponseBody
	public ResultVO schMngRegProc(@RequestParam Map<String, Object> param,
			@RequestParam (value = "staffId", required = false) String[] partnerIds, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "scheduleGubun")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		if(param.get("scheduleGubun").equals("03")){
			if (!StringUtil.isValidationCheck(param, "scheduleGubun", "mngId", "startDt", "endDt")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
			}
		}else{
			if (!StringUtil.isValidationCheck(param, "scheduleGubun", "companySeq","mngId", "startDt", "endDt","startTime", "endTime")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
			}
		}

		//동행자 존재할 경우 추가
		if(partnerIds != null && partnerIds.length != 0){
			param.put("partnerIds", partnerIds);
		}
		try {
			schMngService.insertSchMng(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();

	}
	
	/**
	 * 일정 수정 처리
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngEditFormAjax")
	@ResponseBody
	public ResultVO schMngEditProc(@RequestParam Map<String, Object> param,
			@RequestParam (value = "staffId", required = false) String[] partnerIds, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "scheduleGubun")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		if(param.get("scheduleGubun").equals("03")){
			if (!StringUtil.isValidationCheck(param, "scheduleGubun", "mngId", "startDt", "endDt")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
			}
		}else{
			if (!StringUtil.isValidationCheck(param, "scheduleGubun", "companySeq","mngId", "startDt", "endDt","startTime", "endTime")) {
				return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
			}
		}

		//동행자 존재할 경우 추가
		if(partnerIds.length != 0){
			param.put("partnerIds", partnerIds);
		}
		try {
			schMngService.updateSchMng(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();

	}
	
	/**
	 * 일정 삭제 처리
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngDelFormAjax")
	@ResponseBody
	public ResultVO schMngDelProc(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "scheduleMngSeq")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			schMngService.deleteSchMng(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 일정 관리 목록 엑셀 다운로드
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngListExcelDownload", method = RequestMethod.GET)
	public String schMngListExcelDownload(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAllAttributes(schMngService.getSchMngListAll(param));

		return request.getAttribute("VIEW_PATH") + "/schMng/schMngListExcel";
	}
	
	/**
	 * 일정 관리 목록 엑셀 다운로드 (휴가자)
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schMngListExcelDownload2", method = RequestMethod.GET)
	public String schMngListExcelDownload2(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAllAttributes(schMngService.getSchMngVacationListAll(param));

		return request.getAttribute("VIEW_PATH") + "/schMng/schMngListExcel2";
	}
	
	/**
	 * 일정 완료/완료 취소 처리
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeConfirmFormAjax")
	@ResponseBody
	public ResultVO changeConfirmFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "scheduleMngSeq", "confirmYn")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			param.put("userId", AuthUtil.getAuth(request).getUserId());
			param.put("auth", AuthUtil.getAuth(request).getAuth());
			schMngService.changeConfSchMng(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
}
