<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
	<title>Error-新零售</title>
	<%@ include file="/WEB-INF/views/common/common_header.jsp"%>
	<style>
		*{
			margin:0;
			padding:0;
			border:0;
			font-family:"Microsoft YaHei", "微软雅黑", SimSun, "宋体";
		}
		a{text-decoration:none;}
		.clearfix{ clear: both;}
		li{ list-style:none;}
		.error_404{width:100%; margin: auto; margin-top: 40%;}
		.error_son{width:32%; margin: auto; display: block;padding-right: 5%;}
		.error_son img{width: 100%; }
		.error_son1{text-align: center; font-size: 0.8rem; margin-top: 1rem; color: #838383; letter-spacing: 0.05rem;}
		.error_son2{ text-align: center;  margin-top: 1.8rem;}
		.error_son2 .back_404{ width:22%;margin:auto; font-size: 0.9rem;padding: 0.3rem 0.8rem; border-radius: 0.3rem; color: #ff4444; border: solid #ff4444 0.05rem;}
	</style>
</head>
<body >
<div class="error_404">
	<ul>
		<li class="error_son"><img src="${ctx}/images/error.png" /></li>
		<li class="error_son1">很抱歉，发生了错误！！！</li>
		<li class="error_son2"> <div class="back_404"><a href="${ctx}/mall">返回首页</a></div></li>
	</ul>
</div>

</body >
<script>
    var size = (document.documentElement.clientWidth / 360) * 20 + 'px';
    document.documentElement.style.fontSize = size
</script>
</html>

