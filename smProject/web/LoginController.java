package com.smProject.web;

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smProject.auth.Auth;
import com.smProject.auth.AuthUtil;
import com.smProject.dao.util.CamelMap;
import com.smProject.service.UserService;
import com.smProject.sitemesh.SiteMeshDecoratorSelector;
import com.smProject.sitemesh.SiteMeshFilter;
import com.smProject.util.FileUtil;
import com.smProject.util.MessageUtil;
import com.smProject.util.StringUtil;
import com.smProject.util.exception.BizException;
import com.smProject.vo.ResultDataVO;
import com.smProject.vo.ResultVO;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	private Properties config;

	@Autowired
	private MessageSourceAccessor mr;

	/**
	 * 로그인 폼
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (AuthUtil.hasAuth(request)) {
			return "redirect:" + config.getProperty("site.main");
		}

		request.setAttribute(SiteMeshFilter.SITE_MESH_DECORATOR_KEY, SiteMeshDecoratorSelector.NONE);

		return request.getAttribute("VIEW_PATH") + "/login/login";
	}

	/**
	 * 로그인 처리
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginAjax")
	@ResponseBody
	public ResultVO loginProc(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("param :::>>" + param.toString());
		
		// 유효성 체크
		if (!StringUtil.isValidationCheck(param, "userId", "pwd")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		CamelMap map = new CamelMap();
		try {
			map = userService.loginProc(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		Auth auth = new Auth();
		BeanUtils.copyProperties(auth, map);
		AuthUtil.setAuth(request, auth);

		return new ResultDataVO<Auth>(ResultVO.SUCCESS, auth);
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
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AuthUtil.removeAuth(request);

		return "redirect:/login/login";
	}
	
	/**
	 * 본인 정보 수정 폼
	 * @param param
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userEditForm", method = RequestMethod.GET)
	public String userEditForm(@RequestParam Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {

		if (!AuthUtil.hasAuth(request)) {

			String returnUrl = request.getRequestURI();
			String queryString = request.getQueryString();
			if (StringUtil.isNotEmpty(queryString)) {
				returnUrl += "?" + queryString;
			}
			MessageUtil.showAlert(request, response, mr.getMessage("msg.common.need.login"),
					"/login/login?returnUrl=" + returnUrl);
			return null;
		}
		
		// 사용자 정보 조회
		param.put("userId", AuthUtil.getAuth(request).getUserId());
		model.addAllAttributes(userService.getUser(param));
				
		// 직급 목록 조회
		model.addAllAttributes(userService.getPositionList(param));
		
		// 사용자 구분 목록 조회
		model.addAllAttributes(userService.getAuthList(param));
		
		model.addAttribute("fileList", FileUtil.getFileList(config.getProperty("profile.file.physical.path")));
		model.addAttribute("path", config.getProperty("profile.file.path"));
		
		return request.getAttribute("VIEW_PATH") + "/login/userEditForm";
	}
	
	
	/**
	 * 본인 회원 정보 수정
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
		if (!StringUtil.isValidationCheck(param, "userId", "userNm", "position", "phone")) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.validation"));
		}

		try {
			
			param.put("auth", AuthUtil.getAuth(request).getAuth());
			userService.updateUser(param);
		} catch (BizException e) {
			return new ResultVO(ResultVO.FAIL, e.getMessage());
		} catch (Exception e) {
			return new ResultVO(ResultVO.FAIL, mr.getMessage("msg.common.exception"));
		}

		return new ResultVO();
	}
	

}
