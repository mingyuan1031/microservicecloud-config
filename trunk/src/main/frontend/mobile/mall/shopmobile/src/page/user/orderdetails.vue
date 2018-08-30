<template>

	<div id="orderdetails" class="paddingBox ">
		<!--客户地址-s-->
		<div class="bobyback">

		

			<div class="paymentaSite"  v-show="!!Datas.logisticsAddress">
				<div class="item icon">
					<i class="layui-icon ">&#xe715;</i>
				</div>

				<div class="item site" >
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
							<div class="order-good" >
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
										<small v-show="GooDdatas.status==1">待收货</small>
										<small v-show="GooDdatas.status==2">已收货</small>
										<small v-show="GooDdatas.status==3">已评论</small>
										
										<span>x{{GooDdatas.goodsAmount}}</span>

									</div>
								 </router-link >
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
					<li class="goodMlistNav"><small>支付方式：</small> <span>未选择</span> </li>
					<li class="goodMlistNav"><small>留言内容：</small> <span>{{Datas.order.descr}}</span> </li>
					<li class="goodMlistNav"><small>商品合计：</small> <span>￥{{GooDdatas.goodspaidPrice}}</span</li>
						<li class="goodMlistNav" ><small>运费：</small> <span>运费</span> </li>
				</ul>

				<ul>

					<li class="goodMlistNav  all"><small>应付：</small> <span>￥{{GooDdatas.goodspaidPrice}}</span> </li>
				</ul>

			</div>

			<!--选项-e-->

			<div class="order-kf">
				<p>
					服务时间: 9:00-22:00
				</p>

				<div class="order-kfbut">
					<div class="li" style="float: left;"  @click="tel">
						
							<span><img src="../../assets/img/shop/kf.jpg"/></span>
							<small>在线客服</small>
						
					</div>

					<div class="li" style="float: right;"  @click="tel">
						
							<span><img src="../../assets/img/shop/phone.jpg"/></span>
							<small>电话客服</small>
						

					</div>
				</div>

			</div>

		</div>

		<!--订单详情底部-->
		<!--<div class="order-pay">
            <span @click="delorder">
            	删除订单
            </span>	
	      </div>-->

		<!--订单支付底部-e-->

		<!--alert-s-->
		<alertopen :text=tip></alertopen>

		<!--alert-end-->

	</div>

</template>

<script>
	import alert from './../../components/alert.vue'
	export default {

		name: 'orderdetails',

		data() {
			return {
				call:"",
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
				
				Datas:{
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
				addressDatas:"",
				}
			},

			methods: {
					alertn: function(index, tex, type, ind, word) {

						this.tip.alertopacity = 3
						this.tip.alertshow = index
						this.tip.Alert = true
						this.tip.alwarning = tex

						setTimeout(() => {
							this.tip.alertopacity = 0
							this.tip.alwarning = ""
							this.tip.alertshow = 0
							this.tip.Alert = false
							if(type == true) {
								this.alertdb(ind, word)
							}
						}, 2000)

					},

					alertdb: function(index, tex) {

						this.tip.alertopacity = 3
						this.tip.alertshow = index
						this.tip.Alert = true
						this.tip.alwarning = tex

						setTimeout(() => {
							this.tip.alertopacity = 0
							this.tip.alwarning = ""
							this.tip.alertshow = 0
							this.tip.Alert = false

						}, 2000)

					},

					delorder: function() {
						this.alertn(5, "正在删除", true, 1, "删除成功")
					},
					
					
					tel:function  () {
			
			var cfg=window.lwxfPreload
	
	         this.call=cfg.preload.storecfg.reservationCall
			
			window.location.href = "tel:"+this.call+""
			
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
						url: '/api/users/0/orderGoods/' +this.$route.params.ordergoodsid+ '/details'

					}).then(function(res) {

						var datas = res.data.data
						_this.Datas = datas
						console.log(datas)
					    
						var gooDdatas = datas.goods
						
					/*_this.addressDatas=datas*/
					
						var typeoptionsId="";
						var toGetTypeIds=[];
						 gooDdatas.optiondatas = ""
						 	var OptionsDatas=gooDdatas.options.split(",")
						 	
						 	for(var i=0 ; i<OptionsDatas.length ; i++){
						 		typeoptionsId = OptionsDatas[i]
							   toGetTypeIds.push("options=" + typeoptionsId + "");
						 		
						 	}
						 	
						 	if(toGetTypeIds.length>0){
						 		var ids = toGetTypeIds.join("&")
								_this.axios({
									method: 'get',
			
									url: '/api/goodstypes/options?' + ids + ''
			
								}).then(function(res) {
								var datas=res.data.data
								  gooDdatas.optiondatas=datas.join(",")
								 
							     _this.GooDdatas = gooDdatas
							
								}).catch(function(err) {
									console.log(err)
			
								})
						 	}
						 	
						 	
						gooDdatas.goodspaidPrice = gooDdatas.goodsAmount*gooDdatas.paidPrice
						

						
					}).catch(function(err) {
						console.log(err)

					})

					/*合计money*/
					var totalmoney = 0;
					for(var i = 0; i < this.shoplist.length; i++) {

						totalmoney += this.shoplist[i].money
					}

					this.allmoney = totalmoney

					/*console.log(this.$route.params.ordergoodsid)*/

				}
		}
</script>

<style src='./../../assets/css/orderdetails.css'></style>
<style>
	.order-pay span {
		border: none;
	}
	
	
	
	
.order-good  .stnav{
	width: 2rem;
	padding-right: 0.5rem;
	float: right;
	text-align: right;
}
.order-good  .stnav span{
	display: block;
	width: 100%;
	height: 2rem;
	line-height: 2rem;
	color: #666;
	font-size: 0.7rem;
}

.order-good  .stnav small{

	color: #D20000;
	
	font-size: 0.6rem;
}

.goodMessage ul li.goodMlistNav{
	line-height: 1.6rem;
	height: 1.6rem;
	font-size: 0.8rem;
	
	word-break: break-word;
	word-break: break-all;
	
	
	
}
	
	
	
	
</style>