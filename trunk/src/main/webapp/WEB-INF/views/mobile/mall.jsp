<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 这个是商城首页的页面，如果是商品检索可以是 mall_search.jsp --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>新零售</title>
    <%@ include file="/WEB-INF/views/common/common_header.jsp"%>
    <%@ include file="/WEB-INF/views/common/mall/common_css.jsp"%>
    <link rel="stylesheet" href="/mall.7d7fa6d4.css">
</head>
<body class="white">
<%@ include file="/WEB-INF/views/common/mall/header.jsp"%>
<!-- main -->
<div class="global-main">
    我是商城首頁 - 移动端
</div>

<%@ include file="/WEB-INF/views/common/mall/footer.jsp"%>
<%@ include file="/WEB-INF/views/common/mall/common_js.jsp"%>
<%-- 打包后的mall商城首页js --%>
<script src="/mall.7459e6c3.js"></script>
</body>
</html>


