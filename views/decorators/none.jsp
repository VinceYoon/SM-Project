<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="serverId"><spring:eval expression="@config['server.id']"/></c:set>
<!DOCTYPE HTML>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>SM 관리시스템</title>
	<link href="${STATIC_COMMON_URL}/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${STATIC_COMMON_URL}/css/board.css" rel="stylesheet" type="text/css" />
	<link href="${STATIC_COMMON_URL}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${STATIC_COMMON_URL}/css/layout.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery.selectboxes.js"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/common.js"></script>
	<script type="text/javascript" src="${STATIC_COMMON_URL}/js/smProject.js"></script>
</head>	
<body>
	<sitemesh:write property="body"/>
</body>
</html>



