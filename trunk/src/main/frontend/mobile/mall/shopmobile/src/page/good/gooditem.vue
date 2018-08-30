<template>

	<div id="good" class="good paddingBox" style="background: #EEEEEE;">
		<div class="goodBanner">
			<!--移动端轮播-s-->
			<!--<banner :text=bannerlistnav></banner>-->

			<div id="carousel">
				<div class="swiper-container">
					<div class="swiper-wrapper" style="height: auto;">

						<div class="swiper-slide"  v-for="(item,index) in bannerlistnav"><img class="img"   :alt="item.path" :src="item" /></div>
						<!--<div class="swiper-slide" ><img  style="width: 100%;" src="./../assets/img/index/ximi2s.png"/></div>
            
            <div class="swiper-slide" ><img style="width: 100%;" src="./../assets/img/index/redminote5.png"/></div>-->

					</div>

					<div class="swiper-pagination"></div>
				</div>

			</div>

			<!--移动端轮播-e-->
		</div>

		<!--商品信息-->
		<div class="goodItem">
			<h3>{{shopmsg.name}}</h3>

			<div class="shopM">
				<!--<span>￥{{.price}}</span><del v-show="!!gsedltshow.originalprice">￥{{gsedltshow.originalprice}}</del>-->
				<small>￥{{gsedltshow.price}}</small> <del v-show="gsedltshow.originalprice!=''">￥{{gsedltshow.originalprice}}</del><span>销量：{{gsedltshow.sales}}</span>
			    <p>库存：{{gsedltshow.stock}}</p>
			   <!-- {{goodstock}}-->
			</div>
		</div>

		<!--购买信息--> 
		<div class="goodMessage">
			<ul>
				<li @click="pop(1)" class="goodMlistgood" style="margin-top: 0.5rem;">
					<small>已选择：</small>
					<span  v-show="gsedltshow.stock>0">{{gsedltshow.optionsdatas}}  x{{gsedltshow.count}}</span>
					<span  v-show="gsedltshow.stock==0">{{gsedltshow.optionsdatas}}  x0</span>
					<i class="layui-icon ">&#xe602;</i>
				</li>

				<li @click="pop(2)" class="goodMlistgood" style="margin-bottom: 0.5rem;" v-for="(item,index) in siteList" v-show="item.defaulted==true">
					<small>配送至：</small>
					<span>{{item.receiverAddress}}</span>
				
					<i class="layui-icon ">	<small class="none">运费￥{{shopmsg.freight}}元</small>&#xe602;</i>
				</li>

               <li  class="goodMlistgood" style="margin-bottom: 0.5rem;"  v-show="siteList.length==0||siteid==''">
					<small>配送至：</small>
					<span>
						<router-link to="/user/site">
							请添加/选择地址
					   </router-link >	 
					</span>
				</li>

				<!--<li class="goodensure">
						<span><i class="layui-icon ">&#xe756;</i>7天无理由退货</span>
						<span><i class="layui-icon ">&#xe756;</i>15天质量问题换货</span>
						<span><i class="layui-icon ">&#xe756;</i>365天保修</span>
					</li>-->

			</ul>

		</div>

		<!--用户评论-->
		<div class="goodUser" style="width: 17rem; padding: 0rem 0.5rem;">
			<div class="userTitle">

				<router-link :to="{name:'usertalk',params:{goodid:goodid}}">
					<small>用户评价({{allpagesize}})</small>
					<div class="Umore">
						<span></span>全部评价<i class="layui-icon ">&#xe602;</i>
					</div>
				</router-link>
			</div>
			<!--客户评价-->
			<div class="userTalk" v-show="allpagesize>0">
				<div class="userMeg">
					<div class="userMeg-img" :style="{backgroundImage: 'url(' + datasone.creator.avatar + ')' }">
					
					</div>
					<div class="userMeg-Nmae">
						<p>{{datasone.creator.name}}</p>
						<!--<span>{{datasone.created}}</span>-->
					</div>
					<!--<div class="userMeg-evaluate">
						<span v-show="datasone.score==0">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

						<span v-show="datasone.score==1">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

						<span v-show="datasone.score==2">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

					</div>-->

				</div>

				<div class="userTalk-content">
					<p>
						{{datasone.content}}
					</p>
					<!--<div class="take-imgs">
						<ul class="two">
							<li v-for="(item,index) in filesDates[0]">
								<img :src="item.path" />
							</li>
						</ul>
					</div>-->
				</div>

				<!--回复-->
				<div class="userTalk-revert" v-show="!!datasone.reply">
					<ul>
						<li>

							<span class="Uname">店家回复：</span>
							<span>{{datasone.reply}}</span>

						</li>

					</ul>
				</div>

			</div>
		</div>

		<!--客户评价-e-->

		<!--商品概述-s-->
		<div class="goodsummarize">
			<div class="goodsummarizeTitle">
				<ul>
					<li :class="{active:sumTanclass=='0'}" @click="sumTan(0)">概述</li>
					
				</ul>
			</div>

			<div class="goodsummarizeConcent" id="divhtml">
				
			</div>

		</div>

		<!--弹框-s-->
		<div class="goodUp " :class="{show:upshow==true}">

			<div class="goodUpmoreItem" v-bind:style="{'bottom':-up+'rem'}">

				<!--商品类型选择-s-->
				<div v-show="showIndex==1" class="pickst">
					<div class="pickstNmae">
						<div class="pickstNmae-img">
							<img :src="gsedltshow.bigimage" />
						</div>

						<div class="pickstNmae-name">

							<p>{{gsedltshow.optionsdatas}} </p>
							<div class="pickstNmae-price">
								<span>￥{{gsedltshow.price}}</span><del v-show="!!gsedltshow.originalprice">￥{{gsedltshow.originalprice}}</del>
							</div>
							<span>库存：{{gsedltshow.stock}}</span>
							
						</div>

						<div class="back"><i @click="upnone()" class="layui-icon ">&#x1006;</i></div>

					</div>

					<div class="pickstcontent">

						<!--<div class="pickstcontentV it">
							<h3>版本</h3>
							<ul>
								<li :class="{active:typeindex==index}" v-for="(item,index) in shoptypelist" @click="typeactive(index)">
									<p>{{item.type}}<small>￥{{item.money}}</small></p>

								</li>
							</ul>
						</div>-->
						<div class="pickst-type it" v-for="(item,index) in gsoldatas">
							<h3>{{item.specname}}</h3>
							<div class="pickst-ul">
								<ul>
									<!--"  @click="colortab(indexdb)"-->
									<li   v-for="(itemdb,indexdb) in gsoldatas[index].optionsdatas" :class="{active:itemdb.disabled==true}" @click="activeget(index,indexdb)">{{itemdb.name}}</li>

								</ul>
							</div>

						</div>

						<div class="pickst-num it">
							<h3>数量</h3>
							<div class="pickst-numan" v-show="gsedltshow.stock>0">
								<span @click="pickstnumdoen">-</span>
								<input readonly="readonly" type="text" name="" id="" :value="pickstnum" />
								<span @click="pickstnumapp">+</span>
							</div>
							
							<div class="pickst-numan" v-show="gsedltshow.stock==0">
								<span>-</span>
								<input readonly="readonly" type="text" name="" id="" :value="0" />
								<span >+</span>
							</div>

						</div>

					</div>

					<!--购买-s-->
					<div class="appenDp">

						<a v-if="shopmsg.customization==true" class="wait">
							立即预约
						</a>
						<a v-else="" class="new" @click="appTrollet">
							加入购物车
						</a>

					</div>

					<!--购买-e-->
				</div>
				<!--商品类型选择-e-->

				<!--地址管理-s-->
				<div v-show="showIndex==2" class="pickst">
					<div class="shopSite-title" style="padding: 0.5rem 0rem;">
						选择收货地址
						<span>
								<i @click="upnone()" class="layui-icon ">&#x1006;</i>
							</span>
					</div>

					<div class="pickstcontent">

						<div class="site-item">
							<ul>

								<div class="notsite" v-show="siteList.length<=0">
									还没有地址，赶紧去去添加吧~
								</div>

								<li v-for="(item,index) in siteList">
									<div class="item pack">
										<span :class="{siteCk:item.defaulted==true}" @click="siteCk(index)">
						  					<i class="layui-icon">&#xe605;</i>
						  				</span>
									</div>

									<div class="item siteNav">
										<p>收货人：{{item.receiver}} <span>{{item.receiverPhone}}</span></p>
										<h3>收货地址： {{item.receiverAddress}}</h3>
									</div>

									<!--<div class="siteAmend">
						  			  <i class="layui-icon ">&#xe642;</i>
						  			</div>-->

								</li>

							</ul>
						</div>

					</div>

					<div class="appenDp">
						<router-link to="/user/siet/addsite">
							<a>
								<i class="layui-icon ">&#xe608;</i>添加新地址
							</a>
						</router-link>
					</div>

				</div>
				<!--地址选择-end-->
				
				<!--留言内容-s-->
				<div  v-show="showIndex==3" class="goodcontent">
					<i @click="upnone()" class="layui-icon ">&#x1006;</i>
					<p>订单信息</p>
					<p>商品名称：{{shopmsg.name}}</p>
					<p>商品规格：{{gsedltshow.optionsdatas}}</p>
					<p>商品数量：{{gsedltshow.count}}</p>
					<p>商品价格：{{gsedltshow.price}}(单价)</p>
					<p  v-show="pinkageshow==false">商品运费：{{shopmsg.freight}}元 不足{{pinkage}}元需支付邮费 </p>
					<p  v-show="pinkageshow==true">商品运费：0元 满{{pinkage}}元包邮</p>
					<p>买家留言(可以为空)</p>
					<input type="text" v-model="goodcontent"  name="" id="" value="" />
					<div class="goodcontent_but">
						<span @click="buynow">确认</span>
					</div>
				</div>
				
				<!--留言内容-end-->
				
			</div>

		</div>

		<!--弹框-e-->

		<!--底部-s-->
		<div class="goodfloor">
			<ul>
				<li class="two">
					<router-link to="/">
						<i class="layui-icon ">&#xe68e;</i>
					
					</router-link>
				</li>
				
				<li class="two" @click="tel">
					
						<i class="layui-icon ">&#xe606;</i>
					
				</li>
				
				<li class="two" style="position: relative;">
					<router-link to="/trolley">
					<span class="twopo">{{trolleylen}}</span>
					<i class="layui-icon ">&#xe698;</i> 
				
					</router-link>
				</li>

				<li class="all" v-if="gsedltshow.stock==0">

					<span class="yuyue" style="width: 100%;">库存不足</span>
				</li>

				<li class="all" v-else="">

					<span @click="appTrollet" style="color: #666;" >加入购物车</span>
					<span @click="pop(3)" class="xianzai">立即购买</span>

				</li>
			</ul>
		</div>

		<!--底部-e-->

		<!--alert-->
		<alertopen :text=tip></alertopen>

	</div>

</template>

<script>
	
		import axios from 'axios'
	
	var cfg=window.lwxfPreload

	
	axios.defaults.headers['X-SID']=cfg.appcfg['X-SID'];
	axios.defaults.headers['PHPSESSIONID']=cfg.appcfg['PHPSESSIONID'];
	axios.defaults.headers['X-PHPSESSID']=cfg.appcfg['X-PHPSESSID'];
	axios.defaults.headers['X-C-ID']=cfg.preload['X-C-ID'];
	axios.defaults.headers['X-Requested-With']='XMLHttpRequest';
	axios.defaults.headers['ContentType']='application/json;charset=UTF-8';

	
	import Swiper from 'swiper'
	import carousel from './../../components/carousel.vue'
	import alert from './../../components/alert.vue'
	export default {
		name: 'good',

		data() {

			return {
				datas: [],
				gsedltDatas:{},//报价信息
				gsedltindex:0,//报价信息的位置
				gsedltshow:"",
				dataIndex: 0,
				shopTypeCache: {}, //该商品规格缓存
				typeIdCache: {}, //类型缓存
                 gsoldatas:[],//规格数据
				goodid: "",
				shoptypelist: [], //商品类型
				goodstype: [], //全部规格
				typedatacache: {}, //规格缓存
				tagscache: {}, //全部规格缓存
				typecache:{},//规格父名称缓存
				trolleylen:0,//购物车商品数量
				goodstock:0,//商品总库存
				showstockdata:"",//选择的商品报价类型
				siteid:"",//默认地址id
				goodcontent:"",//立即购买留言内容
				typecache: {},
				pinkage:0,//包邮信息
				pinkageshow:false,
				alwarning: "不能为空",
				alertshow: 0,
				alertopacity: 0,
				Alert: false,
				sumTanclass: 0,
				goodall: false,
				goodIMGmore: true,
				upshow: false,
				up: 10,
				showIndex: 0,
				pickstnum: 1,
				typeindex: 0,
				shopindex: 0,
				safeguard: false,
				allshow: false,
				now: true,
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				shopmsg: {
                "num":0
				},
				shopitem: [

					{
						"color": "白色",
						"checked": true,
						"customization": false,
						"shopname": "小米min2s",
						"title": "小米min2s搭载高通骁龙845 年度旗舰处理器 / AI超感光双摄，DxO百分相机 / 艺术品般陶瓷机身",
						"titlemore": "小米min2s搭载高通骁龙845 年度旗舰处理器 / AI超感光双摄，DxO百分相机 / 艺术品般陶瓷机身",
						"num": 1,
						"maxNum": "9",
						"money": 3299,
						"del": false,
						"oldMoney": 3299,
						"img": require('./../../assets/img/index/banner2.jpg'),
						"imglist": [],
						"typelist": [{
							"type": "6G+64GB",
							"money": 3299,
						}, {
							"type": "6G+128GB",
							"money": 3599,
						}, {
							"type": "8GB+256GB 尊享版",
							"money": 3999,
						}, {
							"type": "8GB+256GB 艺术版",
							"money": 3999,
						}],
					},

					{
						"color": "黑色",
						"checked": false,
						"shopname": "小米min2s",
						"customization": false,
						"title": "「今日早10点开售，享小米分期免息」",
						"titlemore": "搭载高通骁龙845 年度旗舰处理器 / AI超感光双摄，DxO百分相机 / 艺术品般陶瓷机身",
						"num": 1,
						"maxNum": "9",
						"money": 3299,
						"del": false,
						"oldMoney": 3299,
						"img": require('./../../assets/img/index/banner1.jpg'),
						"typelist": [{
							"type": "6G+64GB",
							"money": 3399,
						}, {
							"type": "6G+128GB",
							"money": 3699,
						}, {
							"type": "8GB+256GB 尊享版",
							"money": 4099,
						}, {
							"type": "8GB+256GB 艺术版",
							"money": 4199,
						}],
					}
				],

				/*地址*/
				siteList: [],

				bannerlistnav: [],
				usersCache: {},
				allpagesize: 0,
				dataspl: [],
				datasone: {
					"id": "",
					"memberId": "",
					"goodsId": "",
					"content": "",
					"disabled": "",
					"created": "",
					"goodsExtendId": "",
					"score": "",
					"reply": "",
					"replyCreated": "",
					"goodsName": "",
					"replyId": "",
					"creator": {
						"id": "",
						"isLoad": "",
						"name": "",
						"avatar": ""
					}

				},

				filesDates: [],
				shareDataCache: {}, //

			}

		},

		/**/

		components: {

			banner: carousel,
			alertopen: alert,
		},

		methods: {
			sumTan: function(index) {
				this.sumTanclass = index
			},

			imgmore: function() {
				this.goodall = true;
				this.goodIMGmore = false;
			},
			pop: function(index) {

				this.upshow = !this.upshow
				this.showIndex = index
				this.up = 0
				
				var allmoney=this.gsedltshow.count*this.gsedltshow.price
				console.log(allmoney)
				
				if(allmoney>=this.pinkage){
						this.pinkageshow=true
						this.shopmsg.freight=0
					}else{
						this.pinkageshow=false
					}
				
				
			},

			upnone: function(index) {

				this.upshow = !this.upshow

				this.up = 10
			},

			/*数量--*/
			pickstnumdoen: function() {
				this.pickstnum--
				
					if(this.pickstnum < 1) {
						this.pickstnum = 0
					}
				this.gsedltshow.count = this.pickstnum
				
				
				
			},

			pickstnumapp: function() {
				
				this.pickstnum++
				
					if(this.pickstnum > this.goodstock) {
						this.pickstnum = this.goodstock
					}

				this.gsedltshow.count = this.pickstnum
				
				
			},
			/*保障*/
			safTab: function() {
				this.safeguard = !this.safeguard
			},

			/*规格选择*/
            activeget:function(index,indexdb){
            	
            	this.pickstnum=1
            	var gsolshow;
            	var typeshow=[]
            	
            	
            	
            	
            	for(var i=0; i<this.gsoldatas.length;i++){
            		gsolshow=this.gsoldatas[i].optionsdatas
            		for(var n=0; n<gsolshow.length; n++){
            			this.gsoldatas[index].optionsdatas[n].disabled=false
            			this.gsoldatas[index].optionsdatas[indexdb].disabled=true
            			if(gsolshow[n].disabled==true){
            				
            				
            			typeshow.push(gsolshow[n].id)
            				
            			}
            			
            			
            		}
            		
            	}
            	
            	var showdata=typeshow.join(',')            	
            
            	this.showstockdata=showdata
            	
            	for(var n=0 ;n<this.gsedltDatas.length ; n++){
            		 
            		
            		if(showdata==this.gsedltDatas[n].options){
            			this.gsedltshow=this.gsedltDatas[n]
            			this.goodstock=this.gsedltshow.stock
            		}
            		
            	}
            	
         
            	
            		/*console.log(this.gsedltshow)*/
            
            	//选项选项展示
            	
            	/**/
            	
            },
			tel:function  () {
			
			var cfg=window.lwxfPreload
	
	         this.call=cfg.preload.storecfg.reservationCall
			
			window.location.href = "tel:"+this.call+""
			
		},

			/*地址切换*/
			siteCk: function(index) {
				var _this = this

				_this.axios({
					method: 'put',
					url: '/api/users/0/address/'+this.siteList[index].id+'/defaulted',

				}).then(function(res) {
					console.log(res.data)
					_this.alertn(5)
					_this.ax()

				}).catch(function(err) {
					console.log(err)

				})

			},

			noalert: function() {
				alert(12)
				this.Alert = false
				this.alertshow = 0
			},

			/*弹框*/

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

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert = false

				}, 2000)

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

			/*加入购物车*/
			appTrollet: function() {
                
                var stock=this.gsedltshow.stock
                var count=this.gsedltshow.count
                 
                 
                 
                 if(stock-count<0){
                 	this.alertn(2,"库存不足")
                 	this.timeout()
                 	return false
                 }


              

				this.alertn(5)
				var _this = this

				_this.axios({
					method: 'post',
					headers: {

						'Content-Type': 'application/json'
					},
					url: '/api/users/0/carts',

					data: {

						goodsId: this.goodid,
						count: this.gsedltshow.count,
						extendId:this.gsedltshow.id

					}
				}).then(function(res) {
					
						if(res.data.error){

						 _this.alertn(2,res.data.error.message)
		                _this.timeout()			
						return false
					   }
					
					console.log(res.data)
					_this.alertn(3, "添加成功")
					/*_this.trolleylen+=_this.gsedltshow.count*/
					_this.gwc()
					setTimeout(() => {
					_this.upshow = _this.upshow=false

				}, 2000)
					
					
					for(var n=0 ;n<_this.gsedltDatas.length ; n++){
            		 
            		
            		/*if(_this.showstockdata==_this.gsedltDatas[n].options){
            			_this.gsedltshow.stock-=_this.gsedltshow.count
            			_this.gsoldatas[n].stock-=_this.gsedltshow.count
            			
            		}*/
            		
            	}
					
					_this.timeout()

				}).catch(function(err) {
					console.log(err)

					_this.alertn(2, "稍后重试")
					_this.timeout()

				})

			},
			
			//立即购买
			buynow:function(){ 
				
				var _this=this
			
			
				if(this.siteid==""||this.siteid.trim().length==0||this.siteid==null){
              	this.alertn(2,"请选择地址")
              	 this.timeout()
              	return false;
              	
              }else if(this.goodcontent.length>20){
              	this.alertn(2,"留言内容不能超过20个字")
              	 this.timeout()
                	return false;
              	
              }else{
              	var shopid=[]
              	var shipgoodid=_this.shopmsg.id
              	    shopid.push(shipgoodid)
              		this.alertn(5)
              	 	var goodfreight=_this.shopmsg.freight.toFixed(2)     
              	_this.axios({
				method: 'post',
              
				url: '/api/orders/purchase',
				
		    	
		    	//this.shopmsg.freight
				data:{
					id:_this.gsedltshow.id, //商品报价id
					receiptId:_this.siteid,
					desrc:_this.goodcontent,//留言
					freight:goodfreight,
					amount:_this.gsedltshow.count,
					
				}

			}).then(function(res) {
				//console.log(res.data.data)
				var ordata=res.data.data
				if(res.data.error){
                     _this.alertn(2,res.data.error.message)
		                _this.timeout()			
						return false
					   }
			    /* _this.alertn(3,"加入购物车成功")
			     
			     setTimeout(() => {
					_this.upshow=false
				}, 2000)*/
			     
            _this.timeout()	
              
              _this.$router.push({
					path: "/goodpaymenta",
					query:{
						ordergoodsid:ordata.orderGoods.id
					}
				})
              
              
              

			}).catch(function(err) {
				console.log(err)
                _this.alertn(2,"出现错误")
                _this.timeout()
			})
			
              	
              }
              
				
				
				
				
				
				
				
				
				
				
				
			},
			

			//通过规格重置shopmsg
			all: function() {
					this.shopmsg = this.shopitem[this.shopindex]

				}

				,
			apptrolley: function() {
				console.log(123)
			},

			ax: function() {
				this.alertn(5)
				var _this = this

				_this.axios({
					method: 'get',

					url: '/api/users/0/address'

				}).then(function(res) {
					if(res.data.error){
                     _this.alertn(2,res.data.error.message)
		                _this.timeout()			
						return false
					   }
				

					_this.siteList = res.data.data

					_this.setalert()

				}).catch(function(err) {
					console.log(err)

				})
			},

			//请求轮播图

			bannerhq: function() {

			},
			//轮播图
			swiperid: function() {
				var _this = this
				_this.axios({
					method: 'get',

					url: '/api/goods/' + this.goodid + '/goodsshows'

				}).then(function(res) {
                   if(res.data.error){
                     _this.alertn(2,res.data.error.message)
		                _this.timeout()			
						return false
					   }
					var datas = res.data.data;
					for(var i = 0; i < datas.length; i++) {

						_this.bannerlistnav.push(datas[i].path)

					}

					//_this.bannerlistnav=res.data.data
					//_this.swiperid()

				}).catch(function(err) {
					console.log(err)

				})

			},

            //请求商品详情信息
            shopDatas:function(){
            	var _this=this
            	
            	//查找商品信息
			_this.axios({
				method: 'get',

				url: '/api/goods/' + this.goodid

			}).then(function(res) {
				
				if(res.data.error){
                     _this.alertn(2,res.data.error.message)
		                _this.timeout()			
						return false
					   }
				
                 

				_this.shopmsg = res.data.data.goods
				var goodslist = res.data.data.goodsExtendList
				_this.gsedltDatas = res.data.data.goodsExtendList
				
			
				
				
				if(_this.shopmsg.content){
						document.getElementById("divhtml").innerHTML=_this.shopmsg.content
				}else{
						document.getElementById("divhtml").innerHTML=""
				}
				
			    	
			var divname = document.querySelector("#divhtml")
              var getli = divname.querySelectorAll('img')
              
              for(var i=0; i<getli.length; i++){
              	getli[i].style.width=100+'%'
              }
			
			
		
			
			
				 
				//构建报价数据结构
				
				
				for(var m=0 ; m<_this.gsedltDatas.length ;m++){
					var gsdata=[]
					var getgs=[]
					gsdata=_this.gsedltDatas[m].options.split(",")
					_this.gsedltDatas[m].count=1
					for(var q=0; q<gsdata.length ; q++){
						var osid=gsdata[q]
						  getgs.push(_this.tagscache[osid].name)	
					}
					_this.gsedltDatas[m].optionsdatas=getgs.join(',')
					
					
					if(_this.gsedltDatas[m].defaults==true){
						_this.gsedltshow=_this.gsedltDatas[m]
				_this.goodstock=_this.gsedltDatas[m].stock
					}
					
				}
				
				console.log()
				
				//默认显示第一个
				/*_*/
				
				//处理规格标签
				var gsol=res.data.data.goodsSpecsOptionsList;
			
				
				var gsolshowid;
				var gsolshow;
				for(var i=0 ;i<gsol.length; i++){
					gsolshow=gsol[i]
					gsolshowid=gsolshow.spec
					
					gsol[i].specname=_this.typecache[gsolshowid].name
					/*gsol[i].disabled=false
					gsol[0].disabled=true*/
				var geTtages=[]	
				
				  for(var n=0 ;n<gsol[i].options.length; n++){
				  	  
				  	  var optionsshow=gsol[i].options
				  	    
				  	  var optionsid=optionsshow[n]
				  	  
				  	  geTtages.push(_this.tagscache[optionsid])
                    
				  }
					
					  
			     gsol[i].optionsdatas=geTtages
			  
			 
			      

					
				}
				
					var showoptions=_this.gsedltshow.options.split(",")//默认显示第一个的
				 for(var q=0; q<gsol.length ;q++){
			     	
			     	var getshowoptions=gsol[q]
			     	var getshowoparry=gsol[q].options
			     
			     
			     	
			     	for(var u=0 ;u<getshowoparry.length ;u++){
			     		
			     		console.log()
			     		var qu=getshowoptions.options[u]
			     		
			     		
			     		
			     		for(var m=0; m<showoptions.length ;m++){
			     			
			     			if(qu==showoptions[m]){

			     			 getshowoptions.optionsdatas[u].disabled=true
			     		}
			     			
			     			
			     		}
			     		
			     		
			     		
			     		
			     	}
			     	
                    
			     }
			
			
			/*
			 */
				  _this.gsoldatas=gsol
				
					
					
					
				
				/*var toGetTypeIds = [];
				var toGetType = [];
				var temgood;
				var n = 0
				var len = goodslist.length
				var temtypeid;*/
				
				
				
				

				_this.swiperid()

			}).catch(function(err) {
				console.log(err)

			})
            	
            },
             
			/*加载数据*/
			dataNow: function() {
				/**/
				this.alertn(5)
				var usersCache = this.usersCache
				var shareDataCache = this.shareDataCache //

				var _this = this

				_this.axios({
					method: 'get',

					url: '/api/goods/' + this.$route.params.gooditemid + '/comments?pageNum=0&pageSize=5'

				}).then(function(res) {
					_this.islongo = false
					_this.setalert()

					_this.allpagesize = res.data.pagination.total
					var toGetuser = []
					var datas = res.data.data.goodsComments

					var datasdfiles = res.data.data.files
					for(var i = 0; i < datasdfiles.length; i++) {
						_this.filesDates.push(datasdfiles[i])
					}

				

					var m = 0
					var len = datas.length
					var tempBlog;
					var microblogId;
					var showBlog;
					var userId;
					var shouDatas = [];
					var showUser;
					for(; m < len; m++) {

						tempBlog = datas[m]; //每条评论信息
						microblogId = tempBlog.id;
						showBlog = {
							id: microblogId,
							content: tempBlog.content,
							created: tempBlog.created,
							score: tempBlog.score,
							reply: tempBlog.reply,
							replyCreated: tempBlog.reply,
							memberId: tempBlog.memberId,
							goodsExtendId: tempBlog.goodsExtendId,
							goodsId: tempBlog.goodsId,
							goodsName: tempBlog.goodsName,
							disabled: tempBlog.disabled,
						};

						// 处理用户缓存数据
						userId = tempBlog.memberId;
						showUser = usersCache[userId];

						if(!showUser) {
							showUser = {
								id: userId,
								isLoad: false,
								name: "",
								avatar: ""
							}
							usersCache[userId] = showUser;

							toGetuser.push(userId);
						}

						showBlog.creator = showUser;

						shouDatas.push(showBlog);

						_this.dataspl.push(showBlog)

					}

					if(_this.dataspl.length > 0) {
						_this.datasone = _this.dataspl[0]
					}

					if(toGetuser.length > 0) {
						//请求：;
						/*	var toGetUserIds=["1","2","3"]*/
						var ids = JSON.stringify(toGetuser);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {

							var userInfos = res.data.data;
							m = 0, len = userInfos.length;
							var tempUser;
							var fromCacheUser;
							for(; m < len; m++) {
								var tempUser = userInfos[m];
								fromCacheUser = usersCache[tempUser.id];
								if(!fromCacheUser.isLoad) {
									fromCacheUser.name = tempUser.name;
									fromCacheUser.avatar = tempUser.avatar; //:""
									fromCacheUser.isLoad = true
								}
							}

						}).catch(function(err) {
							console.log(err)

						})

						/**/

						//

					}

				}).catch(function(err) {
					console.log(err)

				})

			},

			toGetTypeIdsaxios: function(toGetTypeIds, n) {
				var _this = this
				var ids = toGetTypeIds.join("&")

				_this.axios({
					method: 'get',

					url: '/api/goodstypes/options?' + ids + ''

				}).then(function(res) {

					_this.data[n].optionS = res.data.data
					//console.log(_this.data)
				}).catch(function(err) {
					console.log(err)

				})
			},
			
			gwc:function  () {
				var _this=this
				_this.axios({
				method: 'get',

				
					url: '/api/users/0/carts'
 
			}).then(function(res) {
			//console.log(res.data.data)
			var datatrolly=res.data.data
			_this.trolleylen=datatrolly.length
			/*for(var i=0; i<datatrolly.length ; i++){
				
				var len=datatrolly[i].count
				_this.trolleylen+=len
			}*/
			
		

			}).catch(function(err) {
				console.log(err)
				
              
			}) 
			}

		},
		mounted: function() {
		
			
            var _this = this
			
			this.dataNow()//初始评论信息
	
			this.shopmsg = this.shopitem[0]
			this.shopmsg.type = this.shopitem[0].typelist[0].type
            this.goodid = this.$route.params.gooditemid

			//请求地址
			_this.axios({
				method: 'get',

				url: '/api/users/0/address'

			}).then(function(res) {
          /*	console.log(_this.siteList)*/
				_this.siteList = res.data.data
				
				for(var i=0 ; i<_this.siteList.length ; i++){
					if (_this.siteList[i].defaulted==true) {
              		  _this.siteid=_this.siteList[i].id
              	}
				}
				
				

			}).catch(function(err) {
				console.log(err)

			})

			//查询全部规格
			

			_this.axios({
				method: 'get',

				url: '/api/goodstypes/all'

			}).then(function(res) {
				/*console.log(res.data)*/
				var typeCache=_this.typecache
				var typedata=res.data.data.typeSpec
				
				var tagsCache = _this.tagscache 
				var tagesdata = res.data.data.specOption
				
				//处理规格标签
				var typeshow;
				var typeid
                
                for(var i = 0; i < typedata.length; i++) {

					typeid = typedata[i].id

					typeshow = typeCache[typeid];

					if(!typeshow) {
						typeshow = {
							color: typedata[i].id,
							id: typedata[i].id,
							disabled: typedata[i].disabled,
							name: typedata[i].name,
						}
					}

					typeCache[typeid] = typeshow;

				

				}
                				

                	//console.log(_this.typecache["4205mxey5ts0"])

				//处理规格
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

			
			_this.shopDatas()

			}).catch(function(err) {
				console.log(err)

			})


    
			
      	
			//请求购物车信息
			
			this.gwc()
		
		 //查询包邮信息 api/cfgdatas
      		  _this.axios({
				method: 'get', 

				url: '/api/cfgdatas'

			}).then(function(res) {
				
				
				_this.pinkage=res.data.data.storecfg.pinkage
				
 
			}).catch(function(err) { 
				console.log(err)
				
			})

			//轮播图

			new Swiper('#carousel .swiper-container', {
				pagination: {
					el: '.swiper-pagination',

				},

				observer: true,
				loop: true,
				autoplay: {
					delay: 3000,
					stopOnLastSlide: false,
					disableOnInteraction: false,
				},

			})

		}
		/**/

	}
</script>
<style src='./../../assets/css/good.css'></style>

<style>
	.appenDp a.new {
		background: #D20000;
		width: 100%;
		color: white;
		display: block;
		text-align: center;
		line-height: 1.6rem;
	}
	
	.appenDp a.wait {
		background: #ff6700;
	}
	
	.twopo {
		position: absolute;
		top: 0.2rem;
		right: 0.5rem;
		display: block;
		width: 0.8rem;
		height: 0.8rem;
		line-height: 0.8rem;
		border-radius: 50% ;
		
		color: #FF4444;
		background: white;
		text-align: center;
		
		font-size: 0.5rem;
		border: solid 1px #FF4444;
	}
	
	.shopSite-title {
		
		padding-bottom: 0.5rem;
	}
	
	.shopSite-title span {
		top: 0.5rem;
	}
	
	.site-item ul li {
		padding: 1rem 0rem;
	}
	
	.swiper-slide   img.img{
		width: 100%;
	
	}
	
	.goodUser .userTitle{
		padding: 0px;
	}
	
	.swiper-wrapper{
	
}

.goodcontent{
	padding:0.5rem;
	position: relative;
}

.goodcontent i{
	position: absolute;
	top: 0.5rem;
	right: 0.5rem;
	font-size: 1rem;
}

.goodcontent p{
	width: 100%;
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	font-size: 0.6rem;
	color: #333333;
	padding-bottom: 0.6rem;
}

.goodcontent input{
	display: block;
	width: 100%;
	height: 1.5rem;
	font-size: 0.7rem;
	border-radius: 5px;
    border: solid 1px #AAAAAA;
}

.goodcontent_but{
	width: 100%;
	padding-top: 0.6rem;
	padding-bottom: 0.1rem;
	
	
}

.goodcontent_but span{
	display: block;
	width: 5rem;
	height: 1.3rem;
	text-align: center;
	line-height: 1.3rem;
	color: white;
	
	margin: 0rem auto;
	border-radius: 0.2rem;

	background: #FF4444;
}

.goodsummarizeConcent img{
	width: 100%;
}

</style>