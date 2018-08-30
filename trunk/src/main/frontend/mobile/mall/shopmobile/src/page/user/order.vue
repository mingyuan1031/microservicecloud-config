<template>

	<div id="order" class="order ">
		<div class="order-head">
			<ul>
				<li :class="{active:tabclass==0}" @click="TabClass(0)"><span>全部{{orderlist.length}}</span></li>
				<!--daifu:0,
			yifu:0,
			sohuo:0,
			pinjia:0,-->
				<li :class="{active:tabclass==1}" @click="TabClass(1)"><span>待付款{{daifu}}</span></li>
				<li :class="{active:tabclass==2}" @click="TabClass(2)"><span>待发货{{yifu}}</span></li>
				<li :class="{active:tabclass==3}" @click="TabClass(3)"><span>待收货{{sohuo}}</span></li>
				<li :class="{active:tabclass==4}" @click="TabClass(4)"><span>待评价{{pinjia}}</span></li>
			</ul>
		</div>

		<div class="order-nav">
			<!--全部订单-->
			<div class="order-class" v-show="tabclass==0">
				<!--没有货-->
				<div class="nonal" v-show="orderlist.length==0">
					<p>空空如也~</p>
					<span><router-link to="/">去逛逛</router-link></span>
				</div>

				<div class="order-item" v-for="(item, index) in orderlist" :key="index">

					<div class="order-itemNav">

						<div class="ordernum">
							订单编号：{{item.orderNumber}}<span @click="delorder(index)" v-show="item.status==0">取消订单</span>
							<span v-show="item.status==1">待发货</span>
							
							<span v-show="item.status==3">待收货</span>
							<span v-show="item.status==4">已收货</span>
							<span v-show="item.status==5">已撤销</span>
							<span v-show="item.status==6">已退款</span>
						</div>
						<!---->
						<div class="order-good" v-for="(itemdb, indexdb) in orderlist[index].goodsDtos">
							<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<div class="item imges">
									<img :src="itemdb.bigimage" />
								</div>
							</router-link>
							<div class="item nametype">
								<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<p style="line-height: 1rem;">{{itemdb.name}}
								</p>
							    </router-link >

								<span>
										{{itemdb.optiondatas}}
										
									    </span>
								<span class="paidPrice">￥{{itemdb.paidPrice}} <small style="float: right; font-size: 0.5rem; color: #000000;">	x{{itemdb.goodsAmount}}</small></span>
							</div>

							<div class="item status">
								<small v-show="itemdb.status==0&&item.status==0">未付款</small>
								<small v-show="itemdb.status==0&&item.status==1">待发货</small>
								<small v-show="itemdb.status==1">待收货</small>
								<small v-show="itemdb.status==2">已收货</small>
								<small v-show="itemdb.status==3">已评论</small>
								<small v-show="itemdb.status==4">已追评</small>
								<!---->

								<small class="ac" style="max-width: 3rem;" v-show="itemdb.status==1"  @click="qrsh(index,indexdb)">
											         确认收货
										</small>
								<small @click="vrouter(index,indexdb,itemdb.id,itemdb.goodsId,itemdb.goodsExtendId)" class="ac" style="max-width: 2rem;" v-show="itemdb.status==2">
											         待评价
								</small> 
								
								<!--<small @click="vrouter(itemdb.id,itemdb.goodsId)" class="ac" style="max-width: 2rem;" v-show="itemdb.status==3">
											         去追评
								</small>-->

							</div>

						</div>

						<div class="order-payment">
							应付：￥ {{item.paidPrice}}(含运费：{{item.freight}})
							<span v-show="item.status==0" @click="goM(item.id)">去付款</span>

							<!--<span  v-show="item.status==2"  @click="affirm(index)">已收货</span>-->
						</div>

					</div>
				</div>

			</div>

			<!--全部订单-->

			<!--待付款-->

			<div class="order-class" v-show="tabclass==1">
				<!--没有货-->
				<div class="nonal" v-show="orderlist.length==0">
					<p>空空如也~</p>
					<span><router-link to="/">去逛逛</router-link></span>
				</div>

				<div class="order-item" v-for="(item, index) in orderlist" :key="index" v-show="item.status==0">

					<div class="order-itemNav">

						<div class="ordernum">
							订单编号：{{item.orderNumber}}<span @click="delorder(index)" v-show="item.status==0">取消订单</span>
						</div>
						<!---->
						<div class="order-good" v-for="(itemdb, indexdb) in orderlist[index].goodsDtos">

							<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<div class="item imges">
									<img :src="itemdb.bigimage" />
								</div> 
							</router-link>
                                 
								<div class="item nametype">
									<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
									<p>{{itemdb.name}}
									</p>
									</router-link>

									<span>
										{{itemdb.optiondatas}}
									    </span>
									<span class="paidPrice">￥{{itemdb.paidPrice}}<small style="float: right; font-size: 0.5rem; color: #000000;">	x{{itemdb.goodsAmount}}</small></span>
								</div>

								<div class="item status">
									<small v-show="itemdb.status==0&&item.status==0">未付款</small>
								<small v-show="itemdb.status==0&&item.status==1">未发货</small>
									<small v-show="itemdb.status==2">已收货</small>
									<small v-show="itemdb.status==3">已评论</small>
									<small v-show="itemdb.status==4">已追评</small>

								</div>
						

						</div>

						<div class="order-payment">
							应付：￥ {{item.paidPrice}}(含运费：{{item.freight}})
							<span v-show="item.status==0" @click="goM(item.id)">去付款</span>
							<!--<span  v-show="item.status==1" >已付款</span>
								<span  v-show="item.status==2"  @click="affirm(index)">已收货</span>-->
						</div>

					</div>
				</div>

			</div>

			<!--待付款-end-->

			<!--待发货-->

			<div class="order-class" v-show="tabclass==2">
				<!--没有货-->
				<div class="nonal" v-show="orderlist.length==0">
					<p>空空如也~</p>
					<span><router-link to="/">去逛逛</router-link></span>
				</div>

				<div class="order-item" v-for="(item, index) in orderlist" :key="index" v-show="item.status==1||item.status==2">

					<div class="order-itemNav">

						<div class="ordernum">
							订单编号：{{item.orderNumber}}<span v-show="item.status==1">待发货</span>
							<!--<span v-show="item.status==2">部分发货</span>-->

						</div>
						<!--v-show="itemdb.status==0"-->
						<div class="order-good" v-for="(itemdb, indexdb) in orderlist[index].goodsDtos" >

							<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<div class="item imges">
									<img :src="itemdb.bigimage" />
								</div>
							</router-link>

								<div class="item nametype">
									<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
									<p>{{itemdb.name}}
									</p>
                                     </router-link>
									<span>
										{{itemdb.optiondatas}}
									    </span>
									<span class="paidPrice">￥{{itemdb.paidPrice}}<small style="float: right; font-size: 0.5rem; color: #000000;">	x{{itemdb.goodsAmount}}</small></span>
								</div>

								<div class="item status">
									<small v-show="itemdb.status==0">待发货</small>
									<small v-show="itemdb.status==1">已发货</small>
									<small v-show="itemdb.status==2">已收货</small>
									<small v-show="itemdb.status==3">已评论</small>
									<small v-show="itemdb.status==4">已追评</small>

								</div>
							

						</div>

						<div class="order-payment">
							应付：￥ {{item.paidPrice}}(含运费：{{item.freight}})
							<!--<span  v-show="item.status==0">去付款</span>
							
								<span  v-show="item.status==2"  @click="affirm(index)">已收货</span>-->
						</div>

					</div>
				</div>

			</div>

			<!--待发货-end-->

			<!--待收货-->

			<div class="order-class" v-show="tabclass==3">
				<!--没有货-->
				<div class="nonal" v-show="orderlist.length==0">
					<p>空空如也~</p>
					<span><router-link to="/">去逛逛</router-link></span>
				</div>

				<div class="order-item" v-for="(item, index) in orderlist" :key="index" v-show="item.status==3">

					<div class="order-itemNav">

						<div class="ordernum">
							订单编号：{{item.orderNumber}}
							<!--<span @click="delorder(index)" v-show="item.status==0">取消订单</span>
							<span v-show="item.status==2">部分发货</span>-->
							<span v-show="item.status==3">待收货</span>
						</div>
						<!---->
						<div class="order-good" v-for="(itemdb, indexdb) in orderlist[index].goodsDtos" >

								<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<div class="item imges">
									<img :src="itemdb.bigimage" />
								</div>
                            </router-link>
								<div class="item nametype">
										<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
									<p>{{itemdb.name}}
									</p>
									 </router-link>

									<span>
										{{itemdb.optiondatas}}
									    </span>
									<span class="paidPrice">￥{{itemdb.paidPrice}}<small style="float: right; font-size: 0.5rem; color: #000000;">	x{{itemdb.goodsAmount}}</small></span>
								</div>

								<div class="item status">
									<small v-show="itemdb.status==0">未发货</small>
									<small v-show="itemdb.status==1">待收货</small>
									<small v-show="itemdb.status==2">已收货</small>
									<small v-show="itemdb.status==3">已评论</small>
									<small v-show="itemdb.status==4">已追评</small>
									<small class="ac" v-show="itemdb.status==1" @click="qrsh(index,indexdb)">确认收货</small>
									<!--<small @click="vrouter(index,indexdb,itemdb.id,itemdb.goodsId,itemdb.goodsExtendId)" class="ac" style="max-width: 2rem;" v-show="itemdb.status==2">
											         待评价
								</small>-->
									<!--<small class="ac" v-show="itemdb.status==3" >已评论</small>-->

								</div>
						

						</div>

						<div class="order-payment">
							应付：￥ {{item.paidPrice}}(含运费：{{item.freight}})
							<!--<span  v-show="item.status==0">去付款</span>-->

							
						</div>

					</div>
				</div>

			</div>

			<!--待收货-end-->

			<!--待评价-->

			<div class="order-class" v-show="tabclass==4">
				<!--没有货-->
				<div class="nonal" v-show="orderlist.length==0">
					<p>空空如也~</p>
					<span><router-link to="/">去逛逛</router-link></span>
				</div>

				<div class="order-item" v-for="(item, index) in orderlist" :key="index" v-show="item.status==4" >

					<div class="order-itemNav">

						<div class="ordernum">
							订单编号：{{item.orderNumber}}<span @click="delorder(index)" v-show="item.status==0">取消订单</span>
						</div>
						<!---->
						<div class="order-good" v-for="(itemdb, indexdb) in orderlist[index].goodsDtos" >

							<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
								<div class="item imges">
									<img :src="itemdb.bigimage" />
								</div>
                            </router-link>
								<div class="item nametype">
									<router-link :to="{name:'orderdetails',params:{ordergoodsid:itemdb.id}}">
									<p>{{itemdb.name}}
									</p>
									</router-link>

									<span>
										{{itemdb.optiondatas}}
									    </span>
									<span class="paidPrice">￥{{itemdb.paidPrice}}<small style="float: right; font-size: 0.5rem; color: #000000;">	x{{itemdb.goodsAmount}}</small></span>
								</div>

								<div class="item status">
									<small v-show="itemdb.status==0">未发货</small>
									<small v-show="itemdb.status==1">已发货</small>

									<small v-show="itemdb.status==2">待评价</small>
									<small v-show="itemdb.status==3">已评论</small>
									<small v-show="itemdb.status==4">已追评</small>
									<!--已收货去评价-->
									<small @click="vrouter(index,indexdb,itemdb.id,itemdb.goodsId,itemdb.goodsExtendId)" class="ac" style="max-width: 2rem;" v-show="itemdb.status==2">
											         待评价
								</small>
								
								<!--<small @click="vrouter(itemdb.id,itemdb.goodsId,itemdb.goodsExtendId)" class="ac" style="max-width: 2rem;" v-show="itemdb.status==3">
											         去追评
								</small>-->
								</div>
							

						</div>

						<div class="order-payment">
							应付：￥ {{item.paidPrice}}(含运费：{{item.freight}})
							<!--<span v-show="item.status==0">去付款</span>
							<span v-show="item.status==1">已付款</span>
							<span v-show="item.status==2" @click="affirm(index)">已收货</span>-->
						</div>

					</div>
				</div>

			</div>

			<!--待-end-->
			<!--取消订单-s-->
				<div class="delclass" v-show="delclass==true">
							<div class="nav">
								<p class="tip">
									提示
								</p>
								<p class="center">
									是否删除这条订单
								</p>
								<div class="but">
									<span @click="delorderid">确认</span>
									<span @click="delorderidno">取消</span>
			
								</div>
							</div>
						</div>
			<!--取消订单-end-->
		
			<!--确认收货-s-->
				<div class="delclass" v-show="delsh==true">
							<div class="nav">
								<p class="tip">
									提示
								</p>
								<p class="center">
									你是否确认已收到货物
								</p>
								<div class="but">
									<span @click="delordergoodid">确认</span>
									<span @click="delordergoodidno">取消</span>
			
								</div>
							</div>
						</div>
			<!--确认收货-end-->
						

		</div>

		<!--alert-s-->
		<alertopen :text=tip @child-say="openoff" @child-none="openone"></alertopen>

		<!--alert-end-->

		

		<!--发表评论-->
         
         <div class="take" :style="{'left':Allleft+'rem'}">
         	
         	<div class="top_left">
         	<i class="layui-icon layui-icon-face-smile" @click="black">&#xe603;</i>
         		评论
         	</div>
         	
			<form >

				<textarea placeholder="写些什么吧" @keyup="numsiez" v-model="msg.center"></textarea>
				<small class="num">{{msg.center.trim().length}}/{{maxnumsize}}</small>

				<div class="uliketype">
                    		<p>选择满意度</p>
							<span :class="{active:msg.liketype==0}" @click="liketab(0)">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span :class="{active:msg.liketype==1}" @click="liketab(1)">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span :class="{active:msg.liketype==2}" @click="liketab(2)">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>
 
							</div>
 
				<ul id="imgid">

					<p>点击下面按钮选择图片(图片最多只能上传6张)<small id="tip"></small></p>

					<li v-for="(item,index) in msg.imgUrls">
						<span class="moreimg">
	    				<img :src="item.file"/>
	    			    <small @click="removeimg(index)">X</small>
	    			</span>

					</li>

					<li>
						<span class="moreimg"></span>
						<input @change="upimg" type="file" multiple="multiple" name="" id="" value="" />
					</li>

				</ul>

				<ul></ul>

				<span @click="sub" class="take-but">
						提交
					</span>

			</form>
		</div>
           

		<!--发表评论-end-->

	</div>

</template>

<script>
	import alert from './../../components/alert.vue'
	export default {
		name: 'order',

		data() {

			return {
				delclass: false,
				delsh:false,
				Allleft:20,
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				tabclass: 0,
				daifu: 0,
				yifu: 0,
				sohuo: 0,
				pinjia: 0,
				shopind: 0, //订单位置
				orgoodidind:0,//订单商品位置
				
				
				
				orderlist: [

				],

				orderList: [],
				tagscache:{},
				//
				queryData:[],
				maxnumsize: 200, //最大字符个数
				imgUrls: [],
				morimg: [],
				imgData: {
					accept: 'image/gif, image/jpeg, image/png, image/jpg',
				},
tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
				msg: {
					"center": "",
                    "liketype":0,
					"imgUrls": [],
				},
				
				plmsg:{
					"id":"",
					"goodsId":"",
					"goodsExtendId":"",
				} //评论需要的信息
			}
				
		

		},

		/**/

		components: {

			alertopen: alert,
		},

		methods: {
			
			 
			//弹框
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
			
			goM:function(id){
				this.$router.push({ name: 'orderpaymenta', query: { orid: id }})
			},
			
			
          //评论跳转
			vrouter: function(index,indexdb,id, goodid,goodsex) {
            
             this.shopind=index
             this.orgoodidind=indexdb
             this.Allleft=0
              
             this.plmsg.id=id
             this.plmsg.goodsId=goodid
             this.plmsg.goodsExtendId=goodsex
            
            
			},
           
           black:function(){
           	this.Allleft=20
           	this.msg.center=""
            this.msg.imgUrls=[]
           	
           },
          
			open: function(index, tex, type, ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
				this.tip.alwarning = tex

			},

			openoff: function() {

				this.tip.alertopacity = 0
				this.tip.alwarning = ""
				this.tip.alertshow = 0
				this.tip.Alert = false

				this.orderlist.splice(this.shopind, 1)

			},

			openone: function(somedata) {

				this.tip.alertopacity = 0
				this.tip.alwarning = ""
				this.tip.alertshow = 0
				this.tip.Alert = false

			},

			/*更新订单状态数量*/

			/*type: function() {
			

				this.daifu = 0
				this.yifu = 0
				this.sohuo = 0
				this.pinjia = 0
				for(var i = 0; i < this.orderlist.length; i++) {
					
					var goodslist=this.orderlist[i].goodsDtos
					
					for (var n=0; n<goodslist; n++) {
						
						
						
					}
					
					
					if(this.orderlist[i].type == 1) {
						this.daifu++
					}

					if(this.orderlist[i].type == 2) {
						this.yifu++
					}

					if(this.orderlist[i].type == 3) {
						this.sohuo++
					}

					if(this.orderlist[i].type == 4) {
						this.sohuo++
					}

					

					if(this.orderlist[i].type == 6) {
						this.pinjia++
					}
				}
			},*/

			TabClass: function(index) {
				this.tabclass = index

			},
			delorder: function(index) {
				this.shopind = index //要取消的订单的位置

				this.delclass = !this.delclass

				//this.orderlist.splice(index, 1)

			},
			
			
			delorderidno: function() {
				this.delclass = false
			},
           
            //取消订单
           
			delorderid: function() {
				
					this.alertn(5)
				
				var oid = this.orderlist[this.shopind].id
				console.log(oid)
				
this.delclass = false
				var _this = this

				_this.axios({
					method: 'put',
					url: '/api/orders/'+oid+'/status/5',

				}).then(function(res) {
					console.log(res.data)
                     _this.setalert()
				 _this.orderlist[_this.shopind].status=5

				}).catch(function(err) {
					console.log(err)
					_this.alertn(2,"出现错误")
					_this.timeout()
					this.delclass = false

				})

			},
			
			//确认收货弹框
			qrsh:function(index,indexdb){
				    this.delsh=true
					this.shopind = index //要确认的订单的位置
					this.orgoodidind=indexdb//要确认的收货商品的位置
					console.log(index,indexdb)
				//this.orderlist[index].goodsDtos[indexdb].status=2
			},

			

			/*确认收货*/

        delordergoodid:function(){
        		this.alertn(5)
				var ogid=this.orderlist[this.shopind].goodsDtos[this.orgoodidind].id
		         console.log(ogid)
		/*	this.orderlist[this.shopind].goodsDtos[this.orgoodidind].status=2*/
			

				var _this = this

				_this.axios({
					method: 'put',
					url: '/api/orderGoods/'+ogid+'/status/2', 

				}).then(function(res) {
					console.log(res.data)
					var ortype=res.data.data
					if(res.data.error){
		              	
						 _this.alertn(2,res.data.error.message)
		                _this.timeout()
		               
						return false
					   }
					
                  _this.delsh=false
                /*   _this.orderlist[_this.shopind].status=4*/
				 _this.orderlist[_this.shopind].goodsDtos[_this.orgoodidind].status=2
				
				 _this.setalert()

				}).catch(function(err) {
					console.log(err)
					_this.alertn(2,"出现错误")
					_this.timeout()
					 _this.delsh=false

				})
			
				
			},
        
        delordergoodidno:function(){
				
				this.delsh=false
			},



			/*affirm: function(index) {
				this.orderlist[index].type = 5
				this.type()
			},
*/
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
			
			//查询订单
			
			orderData:function  () {
				//查询订单
				
				
			this.alertn(5)

			var _this = this
				
			_this.axios({
				method: 'get',

				url: '/api/users/0/orders'

			}).then(function(res) {
				console.log(res.data.data)

				_this.setalert()

				var datas = res.data.data;

				/**/

				var toGetTypeIds = [];
				var toGetType = [];
				var temgood;
				var n = 0
				var len = datas.length
				var temtypeid;
				var goodsDtas;
				for(; n < len; n++) {
					//待付款
					if(datas[n].status == 0) {
						_this.daifu++
					}
					
					if(datas[n].status == 1||datas[n].status==2) {
						_this.yifu++
					}
					
					if(datas[n].status==3) {
						_this.sohuo++
					}
					
				   if(datas[n].status==4) {
							_this.pinjia++
					}
					
					var toGetTypeIds = []
					temgood = datas[n] //每一条订单信息
					goodsDtas = datas[n].goodsDtos

					for(var m = 0; m < goodsDtas.length; m++) {
                     
						/*//订单类型统计
						if(goodsDtas[m].status == 0 || datas[n].status == 1) {
							_this.yifu++
						}

						if(goodsDtas[m].status == 1 || datas[n].status == 1) {
							_this.sohuo++
						}
                        */
					

						var toGetTypeIds = [];
						var OptionsDatas = goodsDtas[m].options.split(",")
						var typeoptionsId;
						datas[n].goodsDtos[m].optiondatas = ""

						for(var i = 0; i < OptionsDatas.length; i++) {
							typeoptionsId = OptionsDatas[i]
 
                             toGetTypeIds.push(_this.tagscache[typeoptionsId].name)
                             datas[n].goodsDtos[m].optiondatas=toGetTypeIds.join(",")
							/*toGetTypeIds.push("options=" + typeoptionsId + "");*/

						}

						

					}

					_this.orderlist = datas

				}

			}).catch(function(err) {
				console.log(err)
				_this.alertn(2, "请稍后重试")
				_this.timeout()

			})

			},
			
			//
			
			
			liketab:function(index){
				this.msg.liketype=index
			},
			
			
			
			upimg: function(e) {

				var _this = this
                
				this.morimg = []
				this.morimg = e.target.files
			
			     var imgfiles=e.target.files
				 var imgdata = e.target.files
                 var formData = new FormData();
				for (var i=0 ; i<imgdata.length; i++) {
							
				var imgsize = imgdata[i].size
				var imgstyle = imgdata[i].type
			if(this.imgData.accept.indexOf(imgstyle) == -1) {

					document.getElementById("tip").innerHTML = "请选择正确的图片格式"
					 _this.setalert()
					return false;
				} else if(imgsize > 5242880) {

					document.getElementById("tip").innerHTML = "图片大小不能超过5M"
					 _this.setalert()
					return false;
				} else if(this.msg.imgUrls.length+imgdata.length> 6) {

					document.getElementById("tip").innerHTML = "图片数量不能超过6张"
					  _this.setalert()
					return false;
				} else {
					       document.getElementById("tip").innerHTML = ""
					
					        var datimg=imgdata[i]
                            console.log(datimg)
						    formData.append('fileList', datimg);
								
					  
					
						}
							
							
							
						}
				
					
				
					
                     this.alertn(5)
					
					var _this = this
			
					_this.axios({
						method: 'post',//
//						//
						
						url: 'api/goods/'+_this.plmsg.goodsId+'/comments/files',
						
                        
						data: formData

					}).then(function(res) {
						 var igdatas=res.data.data
						console.log(res.data.data)
                      
                      for(var i=0; i<igdatas.length;i++){
                      	_this.msg.imgUrls.push(igdatas[i])
                      	
                      }
 
						   _this.setalert()
							 
					}).catch(function(err) {
						console.log(err)
						 _this.alertn(2,"图片上传失败")
                         _this.timeout()
						
					})

				

		
		},

		/*清除图片*/

		removeimg: function(index) {
			
			this.msg.imgUrls.splice(index,1)
			console.log(	this.msg.imgUrls)
		/*	var imgid=this.msg.imgUrls[index].id
			console.log(imgid)
			 var _this = this
						
				_this.axios({
				method: 'delete',
				headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    
		    	},
				url: '/api/microblogs/files/'+imgid+'',
				
				
			}).then(function(res) {
				console.log(res.data)
                _this.msg.imgUrls=res.data.data
			}).catch(function(err) {
				console.log(err)
				
				

			})*/
		},

		/*字符长度*/

		numsiez: function() {

			if(this.msg.center.length >= this.maxnumsize) {

				this.msg.center = this.msg.center.substr(0, this.maxnumsize)
			}
		},

		/*提交*/

		sub: function() {
			if(this.msg.center.trim().length==0&&this.msg.imgUrls.length==0) {
				document.getElementById("tip").innerHTML = "评论字数不能字为空";
				return false;
			} else if(this.msg.center.trim().length>200) {
				document.getElementById("tip").innerHTML = "帖子内容不能超过200个字符";
				return false;
			}else{
				//console.log(this.msg)
				document.getElementById("tip").innerHTML = ""
                this.alertn(5)
				var _this = this

				var nav = this.msg.center
				var imgur=[]; 
				
				
				for (var i=0;i<this.msg.imgUrls.length;i++) {
					
					imgur.push(this.msg.imgUrls[i].id)
					
 
				}
				/*console.log(_this.plmsg.id)
				console.log(_this.plmsg.goodsId)
				console.log(_this.plmsg.goodsExtendId)*/
				
				/* var imagrs=imgur.join("&")*/
				 console.log( this.plmsg)
				  console.log(_this.plmsg.goodsId)
				   console.log(_this.plmsg.id)
				_this.axios({
					method: 'post',
					///
					
					url: '/api/goods/'+this.plmsg.goodsId+'/comments',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					
					
					data: {
						goodsComment:{
						content: nav,
						parentId:null,
						//goodsId:_this.$route.params.ordergoodsid,
						goodsId:_this.plmsg.goodsId,
						goodsExtendId:_this.plmsg.goodsExtendId,
                        score:_this.msg.liketype,
                        answered:null,
						},
						imgList:imgur,
						orderGoodsId:_this.plmsg.id,
					} 

				}).then(function(res) {
					console.log(res.data)
					_this.msg.center=""
                    _this.msg.imgUrls=[]
                      _this.setalert()
                      if(res.data.error){
							console.log(res.data.error.message)
							return false
						}
                       
                       
                        _this.orderlist[_this.shopind].goodsDtos[_this.orgoodidind].status=3
                        _this.setalert()
                       
                     setTimeout(() => {
					_this.shareissueshow=false
					_this.Allleft=20

				}, 1000)
                       
				}).catch(function(err) {
					console.log(err)
					 _this.alertn(2,"评价未成功")
                 _this.timeout()
				})

			}
		}
			

		},
		mounted: function() {
             var _this = this
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
			//请求订单信息
               _this.orderData()
			}).catch(function(err) { 
				console.log(err)
				
			})
			
			
			this.tabclass = this.$route.params.orderid

		}
		/**/

	}
</script>
<style src='./../../assets/css/order.css'></style>
<style>
	.order-good {
		border-bottom: none;
	}
	
	.order-good .nametype p {
		min-height: 1rem;
	}
	
	span.paidPrice {
		color: orangered;
		font-size: 0.6rem;
	}
	
	.order-good .status small.act {
		float: right;
		display: block;
		padding: 0.1rem;
		text-align: center;
		font-size: 0.6rem;
		border-radius: 0.1rem;
		background: #D20000;
		color: white;
	}
	
	.order-good {
		position: relative;
	}
	
	.order-good .status small.ac {
		text-align: center;
		background: #D20000;
		color: white;
		top: 3.1rem;
		padding: 0.1rem;
		border-radius: 0.2rem;
		color: white;
		top: 3rem;
	}
	
	.delclass {
		position: fixed;
		width: 100%;
		z-index: 100;
		height: 100%;
		top: 0px;
		left: 0px;
	}
	
	.delclass .nav {
		width: 80%;
		background: white;
		margin: 5rem auto;
		box-shadow: 1px 1px 50px rgba(0, 0, 0, .3);
	}
	
	.delclass .nav .tip {
		height: 1.6rem;
		line-height: 1.6rem;
		padding-left: 0.5rem;
		background: whitesmoke;
		font-size: 0.7rem;
	}
	
	.delclass .nav .center {
		padding: 0.5rem 0rem;
		padding-left: 0.5rem;
		border-bottom: solid 1px whitesmoke;
		border-top: solid 1px whitesmoke;
		font-size: 0.7rem;
	}
	
	.delclass .nav .but {
		height: 2rem;
	}
	
	.delclass .nav .but span {
		display: block;
		float: right;
		margin: 0.3rem 0.5rem;
		border: solid 1px whitesmoke;
		text-align: center;
		line-height: 1.4rem;
		font-size: 0.7rem;
		padding: 0rem 0.5rem;
	}
	.uliketype {
		width: 100%;
		display: inline-block;
	}
	
	.uliketype span {
		display: block;
		width: 2rem;
		text-align: center;
		float: left;
		color: #AAAAAA;
		margin: 0.8rem 0.5rem;
	}
	
	.uliketype span i {
		font-size: 1.2rem;
	}
	
	.uliketype span small {
		display: block;
		width: 100%;
		font-size: 0.6rem;
	}
	
	.uliketype p {
		font-size: 0.6rem;
		color: #AAAAAA;
		padding-left: 0.5rem;
	}
	
	.uliketype span.active {
		color: orangered;
	}
	
	.take-but{
		text-align: center;
	}
	
	.order-itemNav{
		padding: 0.5rem 0rem;
		padding-left: 0.5rem;
	}
</style>

<style src='./../../assets/css/comment.css'></style>