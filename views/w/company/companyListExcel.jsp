<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smProject.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%
	String currentDate = DateUtil.getDate();
	response.setContentType("application/vnd.msexcel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"COMPANY_"+currentDate+".xls\";");
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
			<td bgcolor="ccf4cc">No.</td>
			<td bgcolor="ccf4cc">업체 명</td>
			<td bgcolor="ccf4cc">업체 구분</td>
			<td bgcolor="ccf4cc">시</td>
			<td bgcolor="ccf4cc">구/군</td>
			<td bgcolor="ccf4cc">업체 주소</td>
			<td bgcolor="ccf4cc">담당자 명</td>
			<td bgcolor="ccf4cc">연락처</td>
			<td bgcolor="ccf4cc">특이사항</td>
		</tr>

		<c:if test="${fn:length(list) <= 0 }">
			<tr>
				<td colspan="9">검색 결과 없음.</td>
			</tr>
		</c:if>

		<c:if test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="vst">
				<tr>
					<td>${item.companySeq }</td>
					<td>${item.companyNm }</td>
					<td>${item.companyTypeNm }</td>
					<td>${item.codeSiNm }</td>
					<td>${item.codeGuNm }</td>
					<td>${item.companyAddr }</td>
					<td>${item.MngNm }</td>
					<td>${item.phone }</td>
					<td>${item.etc }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>

</html>
