<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/30
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>

<head>
	<meta charset="UTF-8">
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript" src="/common.js"></script>

	<script type="text/javascript">
        $(function() {
            $('a').on('click', function() {

                onBridgeReady();
            });
        });
        var appId = "${data.appId}";
        var timeStamp = "${data.timeStamp}";
        var nonceStr = "${data.nonceStr}";
        var pg = "prepay_id=" + "${data.pg}";
        var signType = "${data.signType}";
        var paySign = "${data.paySign}";
        function onBridgeReady() {
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId": appId, //公众号名称，由商户传入
                    "timeStamp": timeStamp, //时间戳，自1970年以来的秒数
                    "nonceStr": nonceStr, //随机串
                    "package": pg,
                    "signType":signType, //微信签名方式：
                    "paySign": paySign //微信签名
                },
                function(res) {
                    if (res.err_msg == "get_brand_wcpay_request：ok") {} // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
                }
            );
        }
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
	</script>
</head>

<body>
<a style="font-size: 20px;">贡献一下</a>
</body>

</html>