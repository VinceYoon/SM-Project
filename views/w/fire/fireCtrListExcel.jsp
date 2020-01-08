<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smProject.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%
	String currentDate = DateUtil.getDate();
	response.setContentType("application/vnd.msexcel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"FIRECTR_"+currentDate+".xls\";");
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
			<td colspan="14">* 방화 관리가 "완료"된 월에만 "방화 일자" 및 "방화 관리자"가 표시됩니다.</td>
		</tr>
		<tr>
			<td colspan="14" bgcolor="ccf4cc">${year}</td>
		</tr>
		<tr>
			<td bgcolor="ccf4cc">업체 명</td>
			<td bgcolor="ccf4cc">업체 주소</td>
			<td bgcolor="ccf4cc">1월</td>
			<td bgcolor="ccf4cc">2월</td>
			<td bgcolor="ccf4cc">3월</td>
			<td bgcolor="ccf4cc">4월</td>
			<td bgcolor="ccf4cc">5월</td>
			<td bgcolor="ccf4cc">6월</td>
			<td bgcolor="ccf4cc">7월</td>
			<td bgcolor="ccf4cc">8월</td>
			<td bgcolor="ccf4cc">9월</td>
			<td bgcolor="ccf4cc">10월</td>
			<td bgcolor="ccf4cc">11월</td>
			<td bgcolor="ccf4cc">12월</td>
		</tr>

		<c:if test="${fn:length(list) <= 0 }">
			<tr>
				<td colspan="14">검색 결과 없음.</td>
			</tr>
		</c:if>

		<c:if test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="vst">
				<tr>
					<td>${item.companyNm }</td>
					<td>${item.companyAddr }</td>
					<td>${item.jan }</td>
					<td>${item.feb }</td>
					<td>${item.mar }</td>
					<td>${item.apr }</td>
					<td>${item.may }</td>
					<td>${item.jun }</td>
					<td>${item.jul }</td>
					<td>${item.aug }</td>
					<td>${item.sep }</td>
					<td>${item.oct }</td>
					<td>${item.nov }</td>
					<td>${item.dec }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>

</html>
