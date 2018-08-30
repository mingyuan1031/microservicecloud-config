<%@ page isELIgnored="false" import="com.lwxf.newstore.webapp.common.utils.WebUtils"
		 language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
		 trimDirectiveWhitespaces="true" %>
<%@ page import="com.lwxf.newstore.webapp.facade.AppBeanInjector" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="<%=WebUtils.getDomainUrl()%>" scope="request" />
<c:set var="isOnProd" value="<%=AppBeanInjector.configuration.isOnProd() %>" scope="request" />
<c:set var="res" value="${ctx}/resources" scope="request" />
<c:set var="logo" value="${ctx}/logo.png" scope="request" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" type="image/x-icon"/>
<link rel="bookmark" href="${ctx}/favicon.ico" type="image/x-icon"/>
<link rel="apple-touch-icon" href="${ctx}/apple-touch-icon-iphone.png" />
<link rel="apple-touch-icon" sizes="72x72" href="${ctx}/apple-touch-icon-ipad.png" />
<link rel="apple-touch-icon" sizes="114x114" href="${ctx}/apple-touch-icon-iphone4.png" />
<!-- releaseVer: releaseVer  -->
<meta name="keywords" content="红田,全屋定制,橱柜,衣柜,厨具">
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<script type="text/plain" id="lwxf_preload">
	${preload}
</script>
