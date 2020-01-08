package com.smProject.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smProject.dao.util.CamelMap;
import com.smProject.service.UserService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.FileUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("config")
	private Properties config;
	
	@Autowired
	private MessageSourceAccessor mr;

	/**
	 * 사용자 관리 목록
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userList(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		
		model.addAllAttributes(userService.getUserList(param));

		return request.getAttribute("VIEW_PATH") + "/user/userList";

	}
	
	/**
	 * 사용자 등록 폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userRegForm", method = RequestMethod.GET)
	public String userRegForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		// 직급 목록 조회
		model.addAllAttributes(userService.getPositionList(param));
		
		// 사용자 구분 목록 조회
		model.addAllAttributes(userService.getAuthList(param));
		model.addAttribute("fileList", FileUtil.getFileList(config.getProperty("profile.file.physical.path")));
		model.addAttribute("path", config.getProperty("profile.file.path"));
		
		return request.getAttribute("VIEW_PATH") + "/user/userRegForm";

	}
	
	/**
	 * 아이디 중복 확인
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userIdChkProcAjax")
	@ResponseBody
	public ResultVO userIdChkProcAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "userId")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		CamelMap map = new CamelMap();
		try {
			map = userService.getUserIdChk(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultDataVO<CamelMap>(ResultVO.SUCCESS, map);
	}
	
	/**
	 * 계정 등록 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userRegFormAjax")
	@ResponseBody
	public ResultVO userRegFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "userId", "pwd", "userNm", "position", "phone", "auth")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		try {
			userService.insertUser(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}

	/**
	 * 사용자 수정 폼
	 * @param param
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userEditForm", method = RequestMethod.GET)
	public String userEditForm(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws Exception {

		// 사용자 정보 조회
		model.addAllAttributes(userService.getUser(param));
				
		// 직급 목록 조회
		model.addAllAttributes(userService.getPositionList(param));
		
		// 사용자 구분 목록 조회
		model.addAllAttributes(userService.getAuthList(param));
		
		model.addAttribute("fileList", FileUtil.getFileList(config.getProperty("profile.file.physical.path")));
		model.addAttribute("path", config.getProperty("profile.file.path"));
		
		return request.getAttribute("VIEW_PATH") + "/user/userEditForm";
	}
	
	/**
	 * 계정 수정 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userEditFormAjax")
	@ResponseBody
	public ResultVO userEditFormAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "userId", "userNm", "position", "phone", "auth")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		try {
			userService.updateUser(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 계정 삭제 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userDelFormAjax")
	@ResponseBody
	public ResultVO userDelFormAjax(@RequestParam List<String> userIds, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 유효성 체크
		if(userIds == null && userIds.size() == 0){
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}
		
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("params", userIds);
			
			userService.deleteUser(params);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	
	/**
	 * 사용자 전체 목록 조회
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userListAjax", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO userListAjax(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ResultDataVO<List<CamelMap>>(ResultVO.SUCCESS, userService.getUserListAll(param));
	}
	
	/**
	 * 사용자 목록 엑셀 다운로드
	 *
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userListExcelDownload", method = RequestMethod.GET)
	public String userListExcelDownload(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		model.addAttribute("list", userService.getUserListAll(param));

		return request.getAttribute("VIEW_PATH") + "/user/userListExcel";
	}
}
