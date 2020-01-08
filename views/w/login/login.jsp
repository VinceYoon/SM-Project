<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="serverId"><spring:eval expression="@config['server.id']"/></c:set>
<div class="login_wrap">
	<form id="frmLogin" name="frmLogin" method ="post" action="/login/loginAjax">
		<div class="login_box login_ani">
			<div class="login_txt">
				<p class="pb10"><img src="/resources/images/img_logo_login_eng.png" style="width: 144px; height: auto;"></p>
				<p><span></span></p>
			</div>
	 		<div class="login_input">
	 			<input type="text" name="userId" class="inputbg01" placeholder="아이디"  maxlength="20" value=""/>
				<input type="password" name="pwd" class="inputbg02" placeholder="비밀번호" maxlength="20" value=""/>
				<!-- auto_save_off / auto_save_on -->
				<div>
					<button type="button" class="btn_login" onclick="javascript:loginProc();">Login to your account</button>
				</div>
	 		</div>
	 		<div class="login_info" style="text-align: center" >
<!-- 	 			<a href="/resources/app/inspector.apk">앱다운로드</a> -->
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		<c:if test="${serverId eq 'local'}">
			$('[name="userId"]').val('admin');
			$('[name="pwd"]').val('1Q2W3E4R');
		</c:if>
	});
	
	$("input").keydown(function(key) {
		if (key.keyCode == 13) {
			loginProc();
		}
	});
	
	function loginProc(){
		$('#frmLogin').ajaxSubmit({
			dataType: 'json',
			beforeSubmit: function(arr, $form, options){
			    var form = $form[0];
			    if (!form.userId.value) {
			        alert('아이디를 입력하세요.');
			        form.userId.focus();
			        return false;
			    }
			    if (!form.pwd.value) {
			        alert('비밀번호를 입력하세요.');
			        form.userPwd.focus();
			        return false;
			    }

			    return true;
			},
			success: function(data) {
		        if(data.status == 'success'){
		        	<c:if test="${empty param.returnUrl}">
					location.href = '${SITE_MAIN}';
		        	</c:if>
		        	<c:if test="${not empty param.returnUrl}">
					location.href = '${param.returnUrl}';
		        	</c:if>
		        }else{
		        	alert(data.msg);
		        }
		    }
		});
	}
</script>