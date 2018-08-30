<template>

	<div id="orderdetails" class="paddingBox ">
		<!--客户地址-s-->
		<div class="bobyback">

			<div class="paymentaSite" v-show="!!Datas.logisticsAddress">
				<div class="item icon">
					<i class="layui-icon ">&#xe715;</i>
				</div>

				<div class="item site">
					<p>收货人：{{Datas.logisticsAddress.receiver}} <span>{{Datas.logisticsAddress.receiverPhone}}</span></p>
					<h3>收货地址：{{Datas.logisticsAddress.receiverAddress}} </h3>
				</div>

				<h1></h1>

			</div>

			<!--商家店铺-->
			<div class="store-center">
				<ul>
					<li>

						<!---->
						<div style="padding: 0rem 0.5rem;background: white;">
							<div class="order-good">
								<router-link :to="{name:'gooditem',params:{gooditemid:GooDdatas.goodsId}}">
									<div class="item imges"><img :src="GooDdatas.bigimage"></div>
									<div class="item nametype" style="width: 9rem;">
										<p>{{GooDdatas.name}}</p>
										<span>{{GooDdatas.optiondatas}}</span>
										<b>￥{{GooDdatas.paidPrice}}</b>
									</div>
									<div class="item stnav" style="float: right;">
										<small v-show="GooDdatas.status==0&&Datas.order.status==0">未付款</small>
										<small v-show="GooDdatas.status==0&&Datas.order.status==1">未发货</small>
										<small v-show="GooDdatas.status==1&&Datas.order.status==1">已发货</small>
										<small v-show="GooDdatas.status==2">已收货</small>
										<small v-show="GooDdatas.status==3">已评论</small>

										<span>x{{GooDdatas.goodsAmount}}</span>

									</div>
								</router-link>
							</div>
						</div>

					</li>
				</ul>
			</div>

			<!--选项-s-->

			<div class="goodMessage">

				<ul>
					<li class="goodMlistNav"><small>订单编号：</small> <span>{{Datas.order.orderNumber}}</span> </li>
					<li class="goodMlistNav"><small>下单时间：</small> <span>{{Datas.order.created}}</span</li>
						<li class="goodMlistNav"  v-show="!!Datas.logisticsAddress.logisticsNumber"><small>物流单号：</small> <span>{{Datas.logisticsAddress.logisticsNumber}}</span> <span>[{{Datas.logisticsAddress.logisticsName}}]</span> </li>
				</ul>

				<ul>

					<li class="goodMlistNav"><small>留言内容：</small> <span>{{Datas.order.descr}}</span> </li>
					<li class="goodMlistNav"><small>商品合计：</small> <span>￥{{GooDdatas.goodspaidPrice}}</span</li>
						<li class="goodMlistNav" ><small>配送运费：</small> <span>￥{{Datas.order.freight}}</span> </li>
				</ul>

				<ul>

					<li class="goodMlistNav  all"><small>应付：</small> <span>￥{{allmoney}}</span> </li>
				</ul>

			</div>

			<!--选项-e-->

			<!--<div class="order-kf">
				<p>
					服务时间: 9:00-22:00
				</p>

				<div class="order-kfbut">
					<div class="li" style="float: left;">
						<a>
							<span><img src="../../assets/img/shop/kf.jpg"/></span>
							<small>在线客服</small>
						</a>
					</div>

					<div class="li" style="float: right;">
						<a>
							<span><img src="../../assets/img/shop/phone.jpg"/></span>
							<small>电话客服</small>
						</a>

					</div>
				</div>

			</div>-->

		</div>

		<!--订单详情底部-->
		<!--订单支付底部-->
		<div class="order-pay" style="padding: 0px;">
			
			<div class="item total"><span><small>合计：</small>￥{{allmoney}}元</span></div>
			<div class="item payment" @click="wxmsg">
				去付款
			</div>
		</div>

		<!--订单支付底部-e-->
		<!--订单支付底部-e-->

		<!--alert-s-->
		<alertopen :text=tip></alertopen>

		<!--alert-end-->

	</div>

</template>

<script>
	import alert from './../../components/alert.vue'
	
	var wx = require('weixin-js-sdk');
	
	export default {

		name: 'orderdetails',

		data() {
			return {
				leaveword: "",
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				allmoney: 0,
				/*地址*/
				siteList: [{
					"checked": false,
					"usernmae": "苏打水",
					"userphone": "15888828888",
					"usercity": "河南省郑州市经济开发区",
					"userparticularcity": "河南省郑州市经济开发区新华大厦010101老屋新房网络科技有限公司",

				}, {
					"checked": true,
					"usernmae": "荔枝",
					"userphone": "15888826666",
					"usercity": "河南省郑州市经济开发区",
					"userparticularcity": "河南省郑州市经济开发区新华大厦010101老屋新房网络科技有限公司",

				}, ],

				shoplist: [{
					"checked": true,
					"shopname": "红田家居丨实木沙发茶几",
					"title": "圣马力诺",
					"type": 1,
					"num": 1,
					"maxNum": "9",
					"money": 1699,
					"totalM": 1699,
					"img": require('./../../assets/img/index/banner2.jpg')
				}, ],

				GooDdatas: {
					logistics: {

						logisticsName: "",
						logisticsNumber: "",
						orderGoodsId: "",
						orderId: "",
						receiptId: "",
						receiver: "",
						receiverAddress: "",
						receiverPhone: "",
					}

				},

				Datas: {
					logisticsAddress: {

						logisticsName: "",
						logisticsNumber: "",
						orderGoodsId: "",
						orderId: "",
						receiptId: "",
						receiver: "",
						receiverAddress: "",
						receiverPhone: "",
					},

					order: {

						orderNumber: "",
						logisticsNumber: "",

					}

				},
				addressDatas: "",
				wxmsgdatas: "",
			}
		},

		methods: {

			timeout: function() {
				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert = false

				}, 2000)
			},
			alertn: function(index, tex, type, ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
				this.tip.alwarning = tex

			},

			alertdb: function(index, tex) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
				this.tip.alwarning = tex

			},

			setalert: function() {

				this.tip.alertopacity = 0
				this.tip.alwarning = ""
				this.tip.alertshow = 0
				this.tip.Alert = false

			},

			delorder: function() {
				this.alertn(5, "正在删除", true, 1, "删除成功")
			},

			//支付

			wxmsg: function() {
				var _this = this

				var orid = _this.Datas.order.id
				/*console.log(orid)*/
				/* _this.alertn(4,_this.orderdata.id)*/

				_this.axios({
					method: 'post',
					url: '/api/orders/' + orid + '/mppay'
				}).then(function(res) {

					if(res.data.error) {

						console.log(res.data.error.message)
						_this.alertn(2, res.data.error.message)
						/* _this.falsemsg=res.data.error.message*/
						_this.timeout()

						return false
					}

					_this.wxmsgdatas = res.data.data
					/*	_this.falsemsg=res.data.data*/
					_this.wxapi()

					_this.setalert()

				}).catch(function(err) {
					console.log(err)
					/*_this.falsemsg=err*/
					_this.alertn(2, "出现错误")
					_this.removeorid()
					_this.timeout()
				})

			},
			//撤销微信订单
			removeorid: function() {
				var _this = this

				var orid = _this.Datas.order.id

				_this.axios({
					method: 'PUT',
					url: '/api/orders/' + orid + '/close'
				}).then(function(res) {
					if(res.data.error) {
						console.log(res.data.error.message)
						_this.alertn(2, res.data.error.message)
						_this.timeout()
						return false
					}

					

					_this.setalert()

				}).catch(function(err) {
					console.log(err)
					/* _this.alertn(2,"出现错误")
					 _this.removeorid()
					 _this.timeout()*/
				})
			},

			wxapi: function() {
				var _this = this
				var appId = this.wxmsgdatas.appId;
				var timeStamp = this.wxmsgdatas.timeStamp;
				var nonceStr = this.wxmsgdatas.nonceStr;
				var pg = this.wxmsgdatas.pg;
				var signType = this.wxmsgdatas.signType;
				var paySign = this.wxmsgdatas.paySign;

				wx.config({
					debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId: appId, // 必填，公众号的唯一标识
					timestamp: timeStamp, // 必填，生成签名的时间戳
					nonceStr: nonceStr, // 必填，生成签名的随机串
					signature: paySign, // 必填，签名
					jsApiList: [
						"chooseWXPay"
					] // 必填，需要使用的JS接口列表
				});
				// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				wx.ready(function() {
					/*var $consoleOutContent = $("#consoleOutContent");
					$consoleOutContent.append("<p style=\"font-size: 18px\">开始支付</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">appId = "+appId+"</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">timeStamp = "+timeStamp+"</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">nonceStr = "+nonceStr+"</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">pg = "+pg+"</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">signType = "+signType+"</p>");
					$consoleOutContent.append("<p style=\"font-size: 18px\">paySign = "+paySign+"</p>");*/
					wx.chooseWXPay({
						appId: appId,
						timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
						nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
						'package': pg, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
						signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
						paySign: paySign, // 支付签名
						success: function(res) {
							/*$consoleOutContent.append("<p style=\"font-size: 18px\">支付成功："+JSON.stringify(res)+"</p>");*/

							_this.alertn(1, "支付成功")
							_this.appendwx()
							_this.back()

						},
						fail: function(err) {
							/* $consoleOutContent.append("<p style=\"font-size: 18px\">支付失败："+JSON.stringify(err)+"</p>");*/
							_this.alertn(2, "支付失败")
							_this.removeorid()
							_this.timeout()
							setTimeout(() => {
								this.$router.push({
									path: "/",

								})

							}, 2000)
						},
						cancel: function(res) {
							//支付取消
							/* $consoleOutContent.append("<p style=\"font-size: 18px\">支付取消："+JSON.stringify(res)+"</p>");*/
							_this.removeorid()
							_this.alertn(2, '支付取消');
							_this.timeout()
							_this.back()

						}
					});
				});

			},

			//创建支付记录
			appendwx: function() {
				var _this = this
				/*	var orid=_this.msgdatas.id*/
				var orid = _this.Datas.order.id

				_this.axios({
					method: 'post',

					url: '/api/orders/' + orid + '/paidrecords'

				}).then(function(res) {

				}).catch(function(err) {
					console.log(err)

				})

			},

			back: function() {

				setTimeout(() => {
					this.$router.push({
						name: 'order',
						params: {
							orderid: 0
						}
					})

				}, 2000)

			},

		},

		components: {

			alertopen: alert,
		},

		mounted: function() {

			var _this = this
			_this.axios({
				method: 'get',
				//orders/4269jdkenpq9/details
				//url: '/api/orders/'
				url: '/api/users/0/orderGoods/' + this.$route.query.ordergoodsid + '/details'

			}).then(function(res) {

				var datas = res.data.data
				_this.Datas = datas
				console.log(datas)

				var gooDdatas = datas.goods

				/*_this.addressDatas=datas*/

				var typeoptionsId = "";
				var toGetTypeIds = [];
				gooDdatas.optiondatas = ""
				var OptionsDatas = gooDdatas.options.split(",")

				for(var i = 0; i < OptionsDatas.length; i++) {
					typeoptionsId = OptionsDatas[i]
					toGetTypeIds.push("options=" + typeoptionsId + "");

				}

				if(toGetTypeIds.length > 0) {
					var ids = toGetTypeIds.join("&")
					_this.axios({
						method: 'get',

						url: '/api/goodstypes/options?' + ids + ''

					}).then(function(res) {
						var datas = res.data.data
						gooDdatas.optiondatas = datas.join(",")

						_this.GooDdatas = gooDdatas

                       _this.allmoney=_this.GooDdatas.goodspaidPrice+_this.Datas.order.freight


					}).catch(function(err) {
						console.log(err)

					})
				}

				gooDdatas.goodspaidPrice = gooDdatas.goodsAmount * gooDdatas.paidPrice

			}).catch(function(err) {
				console.log(err)

			})

			/*合计money*/
			/*var totalmoney = 0;
					for(var i = 0; i < this.shoplist.length; i++) {

						totalmoney += this.shoplist[i].money
					}

					this.allmoney = totalmoney
*/
			/*console.log(this.$route.params.ordergoodsid)*/

		}
	}
</script>

<style src='./../../assets/css/orderdetails.css'></style>
<style>
	.order-pay span {
		border: none;
	}
	
	.order-good .stnav {
		width: 2rem;
		padding-right: 0.5rem;
		float: right;
		text-align: right;
	}
	
	.order-good .stnav span {
		display: block;
		width: 100%;
		height: 2rem;
		line-height: 2rem;
		color: #666;
		font-size: 0.7rem;
	}
	
	.order-good .stnav small {
		color: #D20000;
		font-size: 0.6rem;
	}
	
	.goodMessage ul li.goodMlistNav {
		line-height: 1.6rem;
		height: 1.6rem;
		font-size: 0.8rem;
		word-break: break-word;
		word-break: break-all;
	}
	
	.order-pay {
		border-top: solid 1px #EEEEEE;
		position: fixed;
		height: 50px;
		width: 100%;
		line-height: 50px;
		z-index: 9;
		background: white;
		left: 0px;
		bottom: 0px;
	}
	
	.order-pay .item {
		float: left;
	}
	
	.order-pay .total {
		width: 60%;
		height: 50px;
		text-align: right;
	}
	
	.order-pay .total span {
		color: #ff4444;
		font-size: 16px;
		font-weight: 100;
		margin-right: 20px;
	}
	
	.order-pay .total small {
		color: #000;
		font-size: 16px;
		font-weight: 100;
	}
	
	.order-pay .payment {
		width: 40%;
		height: 50px;
		background: #FF4444;
		color: white;
		font-size: 16px;
		text-align: center;
	}
	
	.order-pay .payment a {
		color: white;
	}
</style>