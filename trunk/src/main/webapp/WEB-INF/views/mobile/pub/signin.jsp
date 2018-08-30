<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html><html><head><meta charset=utf-8><meta name=viewport content="width=device-width,height=device-height,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=0"><meta content="telephone=no" name=format-detection><meta name=mobile-web-app-capable content=yes><title>新零售 - 登录</title>
<%@ include file="/WEB-INF/views/common/common_header.jsp" %>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        $( "#gologin" ).change(function(e,a) {
            var user = $( "#gologin option:selected").first().text();
            var password = "e6e061838856bf47e1de730719fb2609";
            if(!user){
                return;
            }
            $.post({
                url: "/api/users/login",
                dataType:"json",
                data: JSON.stringify({
                    loginName: user,
                    password: password
                }),
                contentType:"application/json;charset=UTF-8"
            }).done(function(data){
                alert("登陆成功 >>> "+data.go);
            })
        });
    });
</script>
</head>
<body>
用户登陆：
<select id="gologin">
    <option value=""></option>
    <option value="admin">admin</option>
</select>
</body>
</html>