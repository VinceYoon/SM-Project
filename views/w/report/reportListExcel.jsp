<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smProject.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%
	String currentDate = DateUtil.getDate();
	response.setContentType("application/vnd.msexcel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"REPORT_"+currentDate+".xls\";");
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
			<td bgcolor="ccf4cc">일정 시작 일시</td>
			<td bgcolor="ccf4cc">일정 종료 일시</td>
			<td bgcolor="ccf4cc">담당자</td>
			<td bgcolor="ccf4cc">점검 상태</td>
			<td bgcolor="ccf4cc">보고서 상태</td>
			<td bgcolor="ccf4cc">보고서 확인 일시</td>
			<td bgcolor="ccf4cc">보고서 만기 일시</td>
		</tr>

		<c:if test="${fn:length(list) <= 0 }">
			<tr>
				<td colspan="10">검색 결과 없음.</td>
			</tr>
		</c:if>

		<c:if test="${fn:length(list) > 0 }">
			<c:forEach items="${list }" var="item" varStatus="vst">
				<tr>
					<td>${item.reportSeq }</td>
					<td>${item.companyNm }</td>
					<td>${fn:substring(item.startDt,0,4)}-${fn:substring(item.startDt,4,6 )}-${fn:substring(item.startDt,6,8)}&nbsp;${fn:substring(item.startTime,0,2)}:${fn:substring(item.startTime,2,4)}</td>
					<td>${fn:substring(item.endDt,0,4)}-${fn:substring(item.endDt,4,6)}-${fn:substring(item.endDt,6,8)}&nbsp;${fn:substring(item.endTime,0,2)}:${fn:substring(item.endTime,2,4)}</td>
					<td>${item.mngNm }</td>
					<td>
						<c:choose>
							<c:when test="${item.confirmYn eq 'Y' }">
								 완료 (${item.confirmNm }&nbsp;${item.confirmPositionNm })
							</c:when>
							<c:otherwise>
								 미완료
							</c:otherwise>											
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${item.mngYn eq 'Y' }">
								완료
							</c:when>
							<c:otherwise>
								미완료
							</c:otherwise>	
						</c:choose>
					</td>
					<td>${item.mngDt }</td>
					<td>${item.limitDt }</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>

</html>
