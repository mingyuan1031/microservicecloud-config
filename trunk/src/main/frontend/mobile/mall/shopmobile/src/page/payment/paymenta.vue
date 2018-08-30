<template>
	<div id="paymenta" class="paddingBox ">
		<!--客户地址-s-->
		<div class="bobyback">

			<div class="notsite" v-show="siteList.length<=0||siteid==''">
				<router-link to="/user/site">
					<span class="icon">
					  	<img alt="../../assets/img/shop/tianjia.png" src="../../assets/img/shop/tianjia.png"/>
					  </span> 添加新地址
				</router-link>
			</div>

			<div class="paymentaSite" v-for="(item , index) in siteList" v-show="item.defaulted==1">
				<div class="item icon">
					<i class="layui-icon ">&#xe715;</i>
				</div>

				<div class="item site">
					<p>收货人：{{item.receiver}} <span>{{item.receiverPhone}}</span></p>
					<h3>收货地址： {{item.receiverAddress}} </h3>
				</div>
				<!--  @click="falseiframe" -->
				<div class="item iconBack">
					<router-link to="/user/site">

						<i class="layui-icon ">&#xe602;</i>

					</router-link>
				</div>

				<h1></h1>

			</div>

			<!--商家店铺-->
			<div class="store-center">
				<ul>
					<li>
						<div class="store-name">
							<span><img src="../../assets/img/index/shop.png"/></span>红田家居生活馆
						</div>

						<div class="store-item" v-for="(item, index) in shoplist" :key="index">
							<div class="item shopimg">
								<img :src="item.bigimage" />
							</div>

							<div class="item shopnav">
								<p>{{item.name}}</p>
								<span> 
							{{item.optiondatas}}
           			    	{{item.group}}
           			    </span>
								<div class="calculator">
									<b>￥{{item.price}}</b><small>x{{item.count}}</small>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>

			<!--选项-e-->

			<div class="goodMessage">
				<ul>
					<li class="goodMlist" style="margin-top: 0.5rem;"><small>配送方式：</small> <span>商家自选</span> </li>
					<li class="goodMlist" style="margin-top: 0.5rem;" v-show="pinkageshow==false"><small>配送运费：</small> <span>￥{{freightdatas}}</span> 不足{{pinkage}}元需支付邮费 </li>
					<li class="goodMlist" style="margin-top: 0.5rem;" v-show="pinkageshow==true"><small>配送运费：</small> <span>￥0</span> 满{{pinkage}}元包邮</li>
					<li class="goodMlist" style="margin-top: 0.5rem;"><small>买家留言：</small> <span>
							<input type="text" style="border: none;" v-model="leaveword" name="" id="" value="" placeholder="留言内容">
						</span> </li>
				</ul>
			</div>

			<!--选项-e-->

		</div>

		<!--订单支付底部-->
		<div class="order-pay">
			<div class="item total">
				<span><small>合计：</small>￥{{allmoney}}</span>
			</div>
			<div class="item payment" @click="sub">
				<a>
					去付款
				</a>
			</div>
		</div>

		<!--订单支付底部-e-->
		<!--alert-s-->
		<alertopen :text=tip></alertopen>

		<!--alert-end-->

		<!--site-->
		<div class="ordersite" v-show="andiframe==true">

			<div class="andiframe_center">
				<div class="andiframe_box">

					<i class="layui-icon backsite" @click="falseiframe">&#x1006;</i>
					<div class="andiframe_box_cebter">
						<ordersiteopen></ordersiteopen>
					</div>

				</div>

			</div>
			<!--<iframe name="andiframe" src="/user/site">
					
				</iframe>-->
		</div>
		<!--site-end-->

	</div>
</template>
<!-- <script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>-->
<script>
	import alert from './../../components/alert.vue'
	import ordersite from './../user/ordersite.vue'

	var wx = require('weixin-js-sdk');

	export default {
		name: 'paymenta',

		data() {

			return {
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				leaveword: "",
				pinkage:0,//包邮信息
				pinkageshow:false,
				siteid: "", //默认地址id
				allmoney: 0,
				andiframe: false,
				/*地址*/
				siteList: [],

				shoplist: [],

				Datas: [],
				tagscache: {}, //规格缓存
				orderdata: "", //付款时生成的订单
				wxmsgdatas: "", //微信支付信息
				freightdatas: 0, //运费
				falsemsg: "",
			}

		},

		/**/

		components: {
			alertopen: alert,
			ordersiteopen: ordersite
		},

		methods: {

			alertn: function(index, tex, type, ind, word) {
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

			timeout: function() {
				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert = false

				}, 2000)
			},

			falseiframe: function() {

				this.andiframe = !this.andiframe

			},

			allm: function() {
				var totalmoney = 0;
				for(var i = 0; i < this.shoplist.length; i++) {

					totalmoney += this.shoplist[i].price * this.shoplist[i].count
				}

				this.allmoney = totalmoney
			},

			sub: function() {
				var _this = this
				var idsdata = []
				var tridarry = []
				//提交

				var trid = this.$route.query.shopid

				tridarry = trid.split("&")

				for(var i = 0; i < tridarry.length; i++) {
					idsdata.push(tridarry[i].split("id=")[1])
				}

				if(this.siteid == "" || this.siteid.trim().length == 0 || this.siteid == null) {
					this.alertn(2, "请选择地址")
					this.timeout()
					return false;

				} else {
					this.alertn(5)

					_this.axios({
						method: 'post',
						
						url: '/api/orders',

						data: {
							freight: _this.freightdatas,
							desrc: _this.leaveword.trim(), //留言
							ids: idsdata, //商品id
							receiptId: _this.siteid, //地址
						}

					}).then(function(res) {
						if(res.data.error) {

							_this.alertn(2, res.data.error.message)
							_this.back()
							_this.timeout()
							return false

						}

						_this.orderdata = res.data.data

						console.log(_this.orderdata)
						//微信支付信息

						_this.wxmsg()

					}).catch(function(err) {
						console.log(err)
						_this.alertn(2, "出现错误")
						_this.timeout()
					})

				}

				/*  this.$router.push({
					path: "/succeed",
					
              })*/
			},

			wxmsg: function() {
				var _this = this

				var orid = _this.orderdata.id
				console.log(orid)
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

				_this.axios({
					method: 'PUT',
					url: '/api/orders/' + _this.orderdata.id + '/close'
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

				var orid = _this.orderdata.id
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
						path: "/",

					})

				}, 2000)

			},

			goodsDatas: function() {

				/*接受传值*/

				var trid = this.$route.query.shopid

				var _this = this

				this.alertn(5)

				_this.axios({
					method: 'get',

					url: '/api/users/0/carts?' + trid + '',

				}).then(function(res) {
	
					var datas = res.data.data
					_this.Datas = res.data.data
console.log(datas)
					if(_this.Datas.length == 0) {
						_this.back()
					}

					var toGetTypeIds = [];
					var toGetType = [];
					var temgood;
					var n = 0
					var len = datas.length
					var temtypeid;
					var goodsDtas;
					var atr = []
					for(; n < len; n++) {

						atr.push(datas[n].freight)

						/*for(var m=0;m<yfsz.length; m++){
						
						if(yfsz[m+1]>yfsz[m]){
						var n=yfsz[n+1]
					     yfsz[n+1]=yfsz[n]
					     yfsz[n]=n
	
					}
						
						console.log(yfsz)
						
					}*/

						var toGetTypeIds = []
						temgood = datas[n] //每一条购物车商品信息
						goodsDtas = datas[n].options
						var OptionsDatas = goodsDtas.split(",")
						var typeoptionsId;
						_this.allmoney += temgood.price * temgood.count
						datas[n].optiondatas = ""
						for(var i = 0; i < OptionsDatas.length; i++) {
							typeoptionsId = OptionsDatas[i]

							toGetTypeIds.push(_this.tagscache[typeoptionsId].name)
							datas[n].optiondatas = toGetTypeIds.join(",")

						}

						/*	_this.toGetTypeIdsaxios(toGetTypeIds,n)*/

					}

					var atrleng = atr.length
					for(var u = 0; u < atrleng; u++) { //循环的次数等于数组的长度（即位数）

						for(var f = 0; f < atrleng - 1 - u; f++) {

							if(atr[f] < atr[f + 1]) { //如果前一个数大于后一个数 就让这个数和后一个数换下位置
								// if判断语句中‘>’是从小到大， ‘<’则是从大到小
								var n = atr[f + 1]
								atr[f + 1] = atr[f] //让后面数的值等于前一个数的值

								atr[f] = n //让前一个数的值等于后一个数的值

								//提供改变值 来排大小位置

							}

						}

					}

					_this.freightdatas = atr[0]
					
					if(_this.allmoney>=_this.pinkage){
						_this.pinkageshow=true
					}else{
							_this.allmoney += _this.freightdatas
							_this.pinkageshow=false
					}
					
				
					_this.allmoney = _this.allmoney.toFixed(2)
					_this.shoplist = datas

					_this.setalert()

				}).catch(function(err) {
					console.log(err)
					_this.alertn(2, "请稍后重试")
					_this.timeout()

				})

			},

		},
		mounted: function() {

			/* var trid=this.$route.params.shopid*/
			var _this = this

			//查询全部规格
			_this.axios({
				method: 'get',

				url: '/api/goodstypes/all'

			}).then(function(res) {
				var tagsCache = _this.tagscache
				var tagesdata = res.data.data.specOption
				/*console.log(tagesdata)*/
				var thisTages = []

				var tageshow;
				var tagesid;
				for(var i = 0; i < tagesdata.length; i++) {

					tagesid = tagesdata[i].id

					tageshow = tagsCache[tagesid];

					if(!tageshow) {
						tageshow = {
							color: tagesdata[i].id,
							id: tagesdata[i].id,
							disabled: tagesdata[i].disabled,
							name: tagesdata[i].name,
						}
					}

					tagsCache[tagesid] = tageshow;

				}

				//console.log(_this.tagscache["A10"])

				_this.goodsDatas()

			}).catch(function(err) {
				console.log(err)

			})

			//请求地址信息

			_this.axios({
				method: 'get',

				url: '/api/users/0/address'

			}).then(function(res) {
				//console.log(res.data.data)

				_this.siteList = res.data.data

				for(var i = 0; i < _this.siteList.length; i++) {

					if(_this.siteList[i].defaulted == true) {
						_this.siteid = _this.siteList[i].id
					}

				}

			}).catch(function(err) {
				console.log(err)

			})
			
			//查询包邮信息 api/cfgdatas
      		  _this.axios({
				method: 'get', 

				url: '/api/cfgdatas'

			}).then(function(res) {
				
				
				_this.pinkage=res.data.data.storecfg.pinkage
				
 
			}).catch(function(err) { 
				console.log(err)
				
			})

		}
		/**/

	}
</script>

<style src='./../../assets/css/payment.css'></style>
<style>
	.ordersite {
		position: fixed;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		z-index: 99;
		overflow: auto;
	}
	
	.ordersite .andiframe_center {
		position: absolute;
		top: 0px;
		left: 0px;
		bottom: 0px;
		right: 0px;
		display: block;
		width: 15rem;
		margin: auto;
		padding: 0.5rem 0rem;
		border: none;
		border-radius: 0.3rem;
		height: 17rem;
		box-shadow: 1px 1px 50px rgba(0, 0, 0, .3);
	}
	
	.ordersite .andiframe_box {
		position: relative;
	}
	
	.ordersite .andiframe_box i.backsite {
		position: absolute;
		top: 0px;
		right: 0px;
		z-index: 6;
		font-size: 1rem;
		padding: 0.2rem;
		margin-top: -1.1rem;
		margin-right: -0.7rem;
		background: white;
		border-radius: 50%;
		overflow: hidden;
		box-shadow: 1px 1px 50px rgba(0, 0, 0, .3);
	}
	
	.ordersite .andiframe_box .andiframe_box_cebter {
		width: 100%;
		height: 100%;
		background: white;
		overflow: auto;
	}
</style>