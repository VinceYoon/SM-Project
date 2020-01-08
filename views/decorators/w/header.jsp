<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<nav>
		<div class="main_nav">
			<h1><a href="/schMng/schMngList"><img src="/resources/images/logo_eng.png" alt="ENG" style="width: 121px; height: auto; margin-top: 3px;"></a></h1>
			<ul class="main_menu">
				<li><a href="/schMng/schMngList">일정관리</a></li>
				<li><a href="/report/reportList">보고서관리</a></li>
				<li><a href="/fire/fireCtrList">방화관리</a></li>
				<c:if test="${AUTH.auth eq '02'}">
					<li><a href="/user/userList">사용자관리</a></li>
				</c:if>
				<li><a href="/company/companyList">업체관리</a></li>
				<!-- 
				<li>
					<a href="#">서브메뉴샘플</a>
					<ul>
						<li><a href="#">하위메뉴1</a></li>
						<li><a href="#">하위메뉴2</a></li>
					</ul>
				</li>
				 -->
			 	<li style="float:right;right:30px;padding-top: 13px;">			
			 		<span style="font-family:'맑은고딕',Malgun Gothic; font-size:15px; text-decoration:none; color:#a9a9a9;cursor:pointer;font-weight: bold;vertical-align: middle;" onclick="location.href='/login/userEditForm'">
			 			${AUTH.userNm}&nbsp;${AUTH.positionNm}
			 		</span>
			 		<img style="padding-left:7px;border:0;cursor:pointer;" onclick="location.href='/login/logout'" src="${STATIC_COMMON_URL}/images/btn_top_logout.png" alt="로그아웃" title="로그아웃">
				 </li>
			</ul>
		</div>
	</nav>
</header>
		
