<template>
	<div id="paymenta" class="paddingBox ">
		<!--客户地址-s-->
		<div class="bobyback">

			

			<div class="paymentaSite">
				<div class="item icon">
					<i class="layui-icon ">&#xe715;</i>
				</div>

				<div class="item site">
					<p>收货人：{{msgdatas.address.receiver}} <span>{{msgdatas.address.receiverPhone}}</span></p>
					<h3>收货地址：{{msgdatas.address.receiverAddress}} </h3>
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
           			    </span>
								<div class="calculator">
									<b>￥{{item.paidPrice}}</b><small>x{{item.goodsAmount}}</small>
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
					<li class="goodMlist" style="margin-top: 0.5rem;"><small>配送运费：</small> <span>￥{{msgdatas.freight}}</span> </li>
					<li class="goodMlist" style="margin-top: 0.5rem;"><small>买家留言：</small> <span>
							<input type="text" class="leave"  v-model="leaveword" name="" id="" value="" placeholder="留言内容">
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
			<div class="item payment" @click="wxmsg">
				<a>
					去付款
				</a>
			</div>
		</div>

		<!--订单支付底部-e-->
		
		<!--alert-s-->
			<alertopen   :text=tip  ></alertopen>
			
			<!--alert-end-->
		
	</div>
</template>

<script>
	
	import  alert from './../../components/alert.vue'
	var wx = require('weixin-js-sdk')
	export default {
		name: 'paymenta',

		data() {

			return {
				tagscache:{},//全部规格缓存
				msgdatas:{
					address: {

						logisticsName: "",
						logisticsNumber: "",
						orderGoodsId: "",
						orderId: "",
						receiptId: "",
						receiver: "",
						receiverAddress: "",
						receiverPhone: "",
					},
				},
              tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
				leaveword: "",
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

				shoplist: [],
				Datas:[],
				wxmsgdatas:"",
				pinkage:0,//包邮信息
				pinkageshow:false,

			}

		},

		/**/

		components: {

		},

		methods: {
			
			alertn: function(index, tex,type,ind, word) {
				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex
			},
		
		   setalert:function  () {
			
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
				
				
			},
			
			timeout:function  () {
				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				}, 2000)
			},
			
			
			
			/*sub: function() {
 
             
               this.wxmsg()
			},
			*/
			
			
			
			wxmsg:function(){
				var _this=this
				
				var orid=_this.msgdatas.id
			
				/* _this.alertn(4,_this.orderdata.id)*/
		
				_this.axios({
					method: 'post',
					url: '/api/orders/'+orid+'/mppay'
					}).then(function(res) {
					
						if(res.data.error){
								
		              		
						 _this.alertn(2,res.data.error.message)
						/* _this.falsemsg=res.data.error.message*/
		                _this.timeout()
		                
						return false
					   }
						
						_this.wxmsgdatas=res.data.data
					/*	_this.falsemsg=res.data.data*/
						_this.wxapi()

		             _this.setalert()
		              
		              
		
					}).catch(function(err) {
						console.log(err)
							/*_this.falsemsg=err*/
		                _this.alertn(2,"出现错误")
		                _this.removeorid()
		                _this.timeout()
					})
			
			},
			//撤销微信订单
			removeorid:function(){
				var _this=this
				
				
			
				_this.axios({
					method: 'PUT',
					url: '/api/orders/'+_this.msgdatas.id+'/close'
					}).then(function(res) {
						if(res.data.error){
		              	
						 _this.alertn(2,res.data.error.message)
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
			
			wxapi:function(){
					var _this=this
				var appId =  this.wxmsgdatas.appId;
		        var timeStamp =  this.wxmsgdatas.timeStamp;
		        var nonceStr =  this.wxmsgdatas.nonceStr;
		        var pg =  this.wxmsgdatas.pg;
		        var signType =  this.wxmsgdatas.signType;
		        var paySign =  this.wxmsgdatas.paySign;
				
				 wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: appId, // 必填，公众号的唯一标识
                timestamp:timeStamp , // 必填，生成签名的时间戳
                nonceStr: nonceStr, // 必填，生成签名的随机串
                signature: paySign,// 必填，签名
                jsApiList: [
                    "chooseWXPay"
                ] // 必填，需要使用的JS接口列表
            });
            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
            wx.ready(function(){
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
                       
                        _this.alertn(1,"支付成功")
                        _this.appendwx()
                       _this.back()
                        
                       
                    },
                    fail: function(err){
                       /* $consoleOutContent.append("<p style=\"font-size: 18px\">支付失败："+JSON.stringify(err)+"</p>");*/
                        _this.alertn(2,"支付失败")
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
                         _this.alertn(2,'支付取消');
                        _this.timeout()
                        _this.back()
                          
                    }
                });
            });
				
			},
			
			//创建支付记录
			appendwx:function(){
				var _this=this

				var orid=_this.msgdatas.id
								_this.axios({
				method: 'post',

				url: '/api/orders/'+orid+'/paidrecords'

			}).then(function(res) {
				

			}).catch(function(err) {
				console.log(err)

			})
			
			},
			
			
			
			
			
			
			
			
			
			
			
			
			
			back:function  () {
				
				setTimeout(() => {
					this.$router.push({ name: 'order', params: { orderid: 0 }})
					
				}, 2000)
				
				 
			},
			
			/*//请求规格数据
			toGetTypeIdsaxios:function  (toGetTypeIds,n) {
				var _this=this
				var ids = toGetTypeIds.join("&")
                       	
					_this.axios({
						method: 'get',

						url: '/api/goodstypes/options?' + ids + ''

					}).then(function(res) {
			
					var datas=res.data.data
				 
					 _this.Datas[n].optiondatas =datas.join(",")
				
			         _this.shoplist=_this.Datas
				 
			console.log(  _this.shoplist)
	
				
					}).catch(function(err) {
						console.log(err)

					})
			},*/
			
			goodsDatas:function(){
				var _this=this
			   this.alertn(5) 
      	
             var oid=this.$route.query.orid
           
			_this.axios({
				method: 'get',

				
				url: '/api/orders/'+oid+''

			}).then(function(res) {
            //console.log(res.data.data)
			      _this.msgdatas=res.data.data
				  _this.setalert()
				  var datas=res.data.data.goodsDtos
				/**/
			   var temgood;
			    var goodsDtas;
			   
				for(var n=0 ;n<datas.length ; n++){
							
					var toGetTypeIds=[]
					temgood = datas[n] //每一条购物车商品信息
				    goodsDtas= datas[n].options
				    var OptionsDatas=goodsDtas.split(",")
				     var typeoptionsId;
				  
				   	datas[n].optiondatas=""
						for(var i = 0; i < OptionsDatas.length; i++) {
						
							typeoptionsId = OptionsDatas[i]
							toGetTypeIds.push(_this.tagscache[typeoptionsId].name)
                            datas[n].optiondatas=toGetTypeIds.join(",")
							
							
						}
 
					
					
				}
				
				
				 _this.shoplist=datas
				
				
				_this.allmoney=res.data.data.paidPrice
				
			}).catch(function(err) {
				console.log(err)
		  _this.alertn(2,"请稍后重试") 
        _this.timeout()
      /*  _this.back()*/
			})
				
			},

		},
		
		components: { 
           
			alertopen:alert,
		},

		
		mounted: function() {
			
			
			
			
			 var _this=this
			//查询全部规格
	       _this.axios({
				method: 'get', 

				url: '/api/goodstypes/all'

			}).then(function(res) {
				 var tagsCache=_this.tagscache
				var tagesdata=res.data.data.specOption
				/*console.log(tagesdata)*/
				var thisTages=[]
				
				var tageshow;
				var tagesid; 
				for(var i=0 ; i<tagesdata.length ;i++){
					
					 tagesid=tagesdata[i].id
					
						tageshow = tagsCache[tagesid];
						
						if(!tageshow){
							tageshow={
						color:tagesdata[i].id,
						id:tagesdata[i].id,
						disabled:tagesdata[i].disabled,
						name:tagesdata[i].name,
					}
						}
					
					
					 tagsCache[tagesid] = tageshow;
					 
					thisTages.push( tageshow)

				}
				
				//console.log(_this.tagscache["A10"])
			
			_this.goodsDatas()
 
			}).catch(function(err) { 
				console.log(err)
				
			})
			

			//请求地址信息
			/* _this.axios({
				method: 'get',

				url: '/api/users/0/address'

			}).then(function(res) {
				//console.log(res.data.data)
				
			_this.siteList=res.data.data
            
              

			}).catch(function(err) {
				console.log(err)

			})*/
			
		
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
.order-pay{
	padding: 0px;
}

.order-pay span{
	padding: 0px;
}
</style>