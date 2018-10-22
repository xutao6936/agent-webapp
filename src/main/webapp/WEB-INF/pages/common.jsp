<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%--定义常用页面变量 --%>
<c:set var="request" scope="page" value="${pageContext.request}" />
<c:set var="scheme" scope="page" value="${request.scheme}" />
<c:set var="server" scope="page" value="${request.serverName}" />
<c:set var="port" scope="page" value="${request.serverPort}" />
<c:set var="ctx" scope="page" value="${request.contextPath}" />




