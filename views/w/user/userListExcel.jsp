<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smProject.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%
	String currentDate = DateUtil.getDate();
	response.setContentType("application/vnd.msexcel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"USER_"+currentDate+".xls\";");
	response.setHeader("Content-Description", "JSP Generated Data");
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
%>

<!doctype html>
<html lang="ko" style="overflow:hidden">

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title></title>
</head>

<body text="black" link="blue" vlink="purple" alink="red" leftmargin="1" marginwidth="1" topmargin="0" marginheight="0" bgcolor="#FFFFFF">
	<table border="1" cellspacing="1" cellpadding="0">
		<tr>
			<td bgcolor="ccf4cc">이름</td>
			<td bgcolor="ccf4cc">아이디</td>
			<td bgcolor="ccf4cc">직급</td>
			<td bgcolor="ccf4cc">연락처</td>
			<td bgcolor="ccf4cc">권한</td>
			<td bgcolor="ccf4cc">프로필 파일 이름</td>
		</tr>

		<c:if test="${fn:length(list) <= 0 }">
			<tr>
				<td colspan="6">검색 결과 없음.</td>
			</tr>
		</c:if>

		<c:if test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="vst">
				<tr>
					<td>${item.userNm }</td>
					<td>${item.userId }</td>
					<td>${item.positionNm }</td>
					<td>${item.phone }</td>
					<td>${item.authNm }</td>
					<td>${item.prFile }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>

</html>
