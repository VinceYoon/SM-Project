<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.smProject.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="strUtil" uri="/WEB-INF/tld/strUtil.tld"%>
<%
	String currentDate = DateUtil.getDate();
	response.setContentType("application/vnd.msexcel;charset=UTF-8");
	response.setHeader("Content-Disposition", "attachment; filename=\"SCHEDULE_"+currentDate+".xls\";");
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
			<td bgcolor="ccf4cc">일정 구분</td>
			<td bgcolor="ccf4cc">업체 명</td>
			<td bgcolor="ccf4cc">담당자</td>
			<td bgcolor="ccf4cc">일정 시작 일시</td>
			<td bgcolor="ccf4cc">일정 종료 일시</td>
			<td bgcolor="ccf4cc">동행자</td>
			<td bgcolor="ccf4cc">특이사항</td>
			<td bgcolor="ccf4cc">완료 여부</td>
		</tr>

		<c:if test="${fn:length(schList) <= 0 }">
			<tr>
				<td colspan="8">검색 결과 없음.</td>
			</tr>
		</c:if>

		<c:if test="${fn:length(schList) > 0 }">
			<c:forEach items="${schList }" var="item" varStatus="vst">
				<c:set var="scheduleMngSeq" value="${item.scheduleMngSeq }" />
				<tr>
					<td>
						<c:if test="${item.scheduleGubun eq '01'}"><font style="color:#${item.colorCd }">●&nbsp;&nbsp;</font>공사</c:if>
						<c:if test="${item.scheduleGubun eq '02'}"><font style="color:#${item.colorCd }">●&nbsp;&nbsp;</font>점검</c:if>
						<c:if test="${item.scheduleGubun eq '03'}"><font style="color:#${item.colorCd }">●&nbsp;&nbsp;</font>휴가</c:if>
						<c:if test="${item.scheduleGubun eq '04'}"><font style="color:#${item.colorCd }">●&nbsp;&nbsp;</font>방화관리</c:if>
					</td>
					<td>
						<c:if test="${item.scheduleGubun eq '01'}">${item.companyNm }</c:if>
						<c:if test="${item.scheduleGubun eq '02'}">${item.companyNm }</c:if>
						<c:if test="${item.scheduleGubun eq '03'}"></c:if>
						<c:if test="${item.scheduleGubun eq '04'}">${item.companyNm }</c:if>
					</td>
					<td>${item.mngNm }</td>
					<td>${fn:substring(item.startDt,0,4)}-${fn:substring(item.startDt,4,6 )}-${fn:substring(item.startDt,6,8)}&nbsp;${fn:substring(item.startTime,0,2)}:${fn:substring(item.startTime,2,4)}</td>
					<td>${fn:substring(item.endDt,0,4)}-${fn:substring(item.endDt,4,6)}-${fn:substring(item.endDt,6,8)}&nbsp;${fn:substring(item.endTime,0,2)}:${fn:substring(item.endTime,2,4)}</td>
					<td>
						<c:if test="${fn:length(schMngUser) > 0 }">
							<c:forEach items="${schMngUser }" var="item1" varStatus="vst1">
								<c:if test="${item1.scheduleMngSeq eq scheduleMngSeq }">
									${item1.userNm }&nbsp;${item1.positionNm }
									<c:if test="${!vst1.last }">,&nbsp;</c:if>
								</c:if>
							</c:forEach>
						</c:if>
					</td>
					<td>
						<% pageContext.setAttribute("newLineChar", "\n"); %>
						${fn:replace( item.scheduleMemo , newLineChar, '<br/>')}
					</td>
					<td>
						<c:choose>
							<c:when test="${item.confirmYn eq 'Y' }">
								완료
							</c:when>
							<c:otherwise>
								미완료
							</c:otherwise>	
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>

</html>
