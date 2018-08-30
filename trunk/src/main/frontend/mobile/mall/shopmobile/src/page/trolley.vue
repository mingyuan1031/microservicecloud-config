<template>
	
	<!--模板盒子-s-->
		<div id="trolley" class="paddingBox" >
			<div class="trolley-logo" style="padding-top: 0.5rem;"  v-show="totalMoney>0&&compileshow==false"> 
				<p style="text-align: right;">
					还差<span>{{pinkageyou}}</span>包邮
				</p>
			</div>
            <div class="trolley-compile">
			 
					<span v-show="compileshow==false&&shopList.length!=0" @click="compiles">编辑</span>
					<span v-show="compileshow==true"  @click="compiles">完成</span>
				
			</div>
			<div class="trolley-nav">

				<div class="trolley-goods">
					<div class="goodItem">

					

						<ul>
							<li class="rightBox-item" v-for="(item ,index) in shopList">

								<div>
									<div class="items pack ">
										<span :class="{active:item.checked}" @click="checked(index)">
										<i class="layui-icon">&#xe605;</i>
									</span> 
									</div>
                                     <router-link :to="{name:'gooditem',params:{gooditemid:item.goodsId}}">
											<div class="items imges">
											
											<small>
				        				  	 	<img :src="item.bigimage"/>
				        				  	</small>
											
											</div>
                                      </router-link>
									<div class="items gdd">
									 <router-link :to="{name:'gooditem',params:{gooditemid:item.goodsId}}">
											<p>
												{{item.name}}
											</p>
											  </router-link>
											<small style="font-size: 0.5rem; color: #AAAAAA;">
												{{item.optiondatas}}
											</small>
												
											
											
											<span>
									<!--	{{item.group}} -->
									</span>
											<!--<span>
										x{{item.count}} 
									</span>-->


										
										<div class="calculator" v-show="item.stock>0&&item.disabled==false">
											<small>￥{{item.price}}</small>
											
											 <span class="left" :class="{no:item.price=='1'}"  @click="decrease(index)">-</span>
										    <input readonly="readonly" class="left" type="text" name="" id=""  :value="item.count"    />
										   <span class="left" @click="app(index)">+</span>
											<i class="layui-icon" @click="alertdel(index)">&#xe640;</i>
										</div>
										
										<div class="calculator" v-show="item.stock==0&&item.disabled==false">
											<small>￥{{item.price}}</small>
											<small>库存不足</small>
											<i class="layui-icon" @click="alertdel(index)">&#xe640;</i>
											 
										</div>
										
										<div class="calculator" v-show="item.stock>0&&item.disabled==true">
											<small>￥{{item.price}}</small>
											<small>商品下架</small>
											<i class="layui-icon" @click="alertdel(index)">&#xe640;</i>
											 
										</div>
										
										<div class="calculator" v-show="item.stock==0&&item.disabled==true">
											<small>￥{{item.price}}</small>
											<small>商品下架</small>
											<i class="layui-icon" @click="alertdel(index)">&#xe640;</i>
											 
										</div>
										
										
									</div>
								</div>

							</li>
						</ul>

					</div>
				</div>

				<div class="trolley-none" v-if="shopList.length==0">
					<ul>
						<li><i class="layui-icon ">&#xe698;</i><small>购物车空空如也~</small></li>
						
						<li >
							<router-link to="/">去逛逛</router-link>
						</li>
					</ul>
				</div>
			</div>

			<div class="close" v-if="totalMoney>0">
				<ul>
					<li class="f">
							<!--<p>共 {{totalNum}} 件 金额：</p>-->
						<p><!--合计：--><span>￥{{totalMoney}}</span>元</p>
					</li>
					<li class="cen">

					</li>
					<li class="clo" @click="pro">
					<a>去结算</a>
				     <!-- <router-link :to="{name:'paymenta',params:{shopid:porstr}}">去结算</router-link>
-->					</li>

				</ul>
			</div> 
			
			
			<div class="compile" v-show="compileshow==true" style="z-index: 100;">
				<ul>
					<li class="pick">
						<span :class="{checked:pickck==true}" @click="pickckend">
							<i class="layui-icon">&#xe605;</i>
						</span>
						
						<small @click="pickckend">
							全选 
						</small>
					</li>
                    <li class="end" @click="delmore">
                    	<span>
                    		删除
                    	</span>
                    </li>
				</ul>
			</div> 

			<!--猜你喜欢-s-->

			<!--猜你喜欢-e-->
			<!--技术支持-->
			 <support></support>
			 
			<floors></floors>
			
				<!--alert-s-->
			<alertopen   :text=tip  @child-say="openoff"  @child-none="openone"></alertopen>
			
			<!--alert-end-->
			
		
			
		</div>

		<!--模板盒子-e-->
		
		
</template>

<script>
	
	import carousel from './../components/carousel.vue'
	import  support from './../components/support.vue'
	import  floor from './../components/bottom.vue'
	import  alert from './../components/alert.vue'
	
	import axios from 'axios'
	
	export default {
		name: 'trolley', 
		
		data() {
 
			return {
				tagscache:{},
				totalMoney:0,//总价
				pinkage:0,//包邮价格
				pinkageyou:0,
				 tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
			delindex:0,//单个删除标签
			compileshow:false,	 //编辑
			pickck:false,//多选按钮
			totalNum: 0, //选中数量
			delM:0,//多选删除的索引值
			moredek:[],//多选删除数组
			proid:[],//接受商品数组
			porstr:"",//接受商品字符串
			oldshopList: [], 
			msg:[],
			goodidname:[],//商品id
			shopList: [],
			Datas:[],
			
			
				
				flooBox:[{
					"title": "商品类别",
                    "herf":"/trolley",
                    "type": false,
					"fMulist": [{
							"fMulistname": "商品分类",
							 "herf":"/trolley",
							
						},
						{
							"fMulistname": "商品分类2",
							 "herf":"/trolley",
							
						},
					]
				},{
					"title": "购物车",
                     "herf":"/trolley",
                     "type": false,
					"fMulist": [
					]
				},{
					"title": "个人中心",
					"type": false,
                   "herf":"/user",
					"fMulist": [
					
					]
				},
				]
			
			
             }

		},

		/**/

		components: { 
		
			banner: carousel,
			support: support,
			floors: floor,
			alertopen:alert,
			
		},

		methods: {
			
			
			/*弹框*/
			
			 /*弹框*/
				  
				  
				 alertn: function(index, tex,type,ind, word) {
     
				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				

			},
			
			open: function(index, tex,type,ind, word) {
     
				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

			
				

			},
			
			alertdb: function(index, tex ) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				}, 2000)
				
				

			},
				  
          
           /*弹框确认*/
           openoff:function  () {
           	
                  this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
					this.del(this.delindex)
                
           },
              /*弹框取消*/
           openone:function  (somedata) {
           	
           	  this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
           	
           },
           
           /**/
			
			
				//重新计算金额和数量
			
			
			
			
			againM:function  () {
			//重新计算总金额金额
           
         
           
			this.totalMoney=0
			var totalMoney=0
			for(var i = 0; i <this.shopList.length; i++) {
                
                if( this.shopList[i].checked==true){
                	    var m=this.shopList[i].count * this.shopList[i].price
                	    
                		totalMoney += m
                }	
			}
			
			
			 
			
		
            this.totalMoney=totalMoney.toFixed(2)
		
			this.pinkageyou=this.pinkage-this.totalMoney
			},
			
			
			againN:function  () {
				
			//重新计算数量
			this.totalNum=0
			for(var i = 0; i < this.shopList.length; i++) {
                
                if (this.shopList[i].checked==true) {
                	this.totalNum += this.shopList[i].count
                }
	
			}

			

			
			},
			
			
				//商品是否选中
			checked: function(index) {
				
				if(this.shopList[index].stock==0||this.shopList[index].disabled==true&&this.compileshow==false){
						
						this.shopList[index].checked=false
						
					}else{
						this.shopList[index].checked=!this.shopList[index].checked;
					}
				
			
				
				
				
			    this.moredek=[]
				/**/
				
				for (var i=0; i<this.shopList.length ;i++) {
					
					if(this.shopList[i].checked==true&&this.shopList[i].stock>0){
						
						this.moredek.push(this.shopList[i].id)
						
					}
					
				}
			
				
			/**/
					
					
			this.againM()
		    this.againN()		
					
				
			},
			//当前商品数量++ 
			app: function(index) {
				
				this.alertn(5) 
				
              var id=this.shopList[index].id
              var kucun=this.shopList[index].stock
              var newcount=this.shopList[index].count
             var newcountnow=newcount+=1
             
             if(newcountnow>kucun){
             	this.alertn(2,"商品库存不足,剩余"+kucun+"") 
             	this.shopList[index].count=kucun
             	this.timeout()
             	return false;
             }
             
           // console.log(newcountnow)
                var _this = this
						
				_this.axios({
				method: 'put',
				url: '/api/users/0/carts/'+id+'',
				
               data:{
			       count:newcountnow
			        
			    }
			}).then(function(res) {
				console.log(res.data)
               
		   	_this.shopList[index].count=res.data.data.count
		   	_this.setalert()
			if(_this.shopList[index].checked == true) {
			_this.againM() 
			_this.againN()
				}
		   	

			}).catch(function(err) {
				console.log(err)
				
				

			})
          

				
			},
			//当前商品数量--
			decrease: function(index) {
				this.alertn(5) 
				      var id=this.shopList[index].id
            
              var newcount=this.shopList[index].count
             var newcountnow=newcount-=1
             if(this.shopList[index].count<=1){
             	newcountnow=1
             }
           // console.log(newcountnow)
                var _this = this
						
				_this.axios({
				method: 'put',
				url: '/api/users/0/carts/'+id+'',
				
               data:{
			       count:newcountnow
			        
			    }
			}).then(function(res) {
				console.log(res.data)
               
		   	_this.shopList[index].count=res.data.data.count
		   	
		   	_this.setalert()
		   	
		   	if(_this.shopList[index].checked == true) {
			_this.againM() 
			_this.againN()
				}
		   	
			

			}).catch(function(err) {
				console.log(err)
				
				

			})
          

			},

           
             
           alertdel:function  (index) {
           	this.delindex=index
           	    this.open(6, "你确认删除吗")
           	    
           	  
           },


			/*删除当前商品*/
			del: function(index) {
				this.alertn(5) 
				
				
				
				
				
				/*  var id=this.shopList[index].id*/
				  var id="id="+this.shopList[index].id+""
				  
				  	  console.log(id)
				  
				  var _this = this
						
				_this.axios({
				method: 'delete',
				 headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    
		    	},
				url: '/api/users/0/carts?'+id+'',
				
				
			}).then(function(res) {
				console.log(res.data)
               
		  _this.trolleyData()
				
				_this.setalert()
			_this.againM() 
			_this.againN()

			}).catch(function(err) {
				console.log(err)
				
				

			})
				  
				  
				  
				
				//判断选中状态
				
				
				
				
				/*if(this.shopList[index].checked == true) {
					this.totalNum -= this.shopList[index].num //总数量减去当前商品数量
					this.totalMoney -= this.shopList[index].totalM //总价减去上当前商品的总价格
				}

				this.shopList.splice(index, 1)*/

			},
			
			
			/*底部菜单*/
			
			Bomoresh:function  (index) {
				
					if (this.flooBox[index].type==false) {
						for (var i=0 ;i<this.flooBox.length;i++) {
							this.flooBox[i].type=false
						}
						
					this.flooBox[index].type=true
					}else{
						for (var i=0 ;i<this.flooBox.length;i++) {
							this.flooBox[i].type=false
						}
						this.flooBox[index].type=false
					}
				},
			
			
				/*编辑、完成*/
			
			compiles:function  () {
				this.pickck=false
		    this.moredek=[];
			this.compileshow=!this.compileshow
				
				for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=false
				}
				
				/*if (this.compileshow==true) {
					for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=false
				}
				}else{
					for (var i=0; i<this.shopList.length ;i++) {
						
						
						
					this.shopList[i].checked=true
					
						
					
						
				}
				}
				*/
				this.againM()
				
			},
			
			
			
			/*多选*/
			pickckend:function(){
				
				this.moredek=[];
				
				if(this.pickck==false){
				
				for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=true
					
					this.moredek.push(this.shopList[i].id)
					  console.log(this.moredek)
				}
				
				this.pickck=!this.pickck
				
				}else{
					for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=false
				}
					this.pickck=!this.pickck
					
				}
				
				
			},
		
			/*多选删除*/
			
			delmore:function(){
				
				var ids=[]
				
			 	for (var i=0; i<this.moredek.length ;i++) {
					
					ids.push("id="+this.moredek[i])
					
                
					
					
				}
				
				
				var gooids=ids.join("&")
				console.log(gooids)
				this.alertn(5) 
				
				 var _this = this
						
				_this.axios({
				method: 'delete',
				 headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    
		    	},
				url: '/api/users/0/carts?'+gooids+'',
				
			
				
				
			}).then(function(res) {
				console.log(res.data)
          
          var datas=res.data.data;
				for(var i = 0; i <datas.length; i++) {
				 datas[i].checked=true
					
				}
			_this.shopList=datas;
		    _this.setalert()
		  	_this.compileshow=!_this.compileshow
			_this.againM() 
			_this.againN()

			}).catch(function(err) {
				console.log(err)
				
				
				

			})
				
				
				
				
				/*var delval=[]
				var datavaol=[]
				for (var i=0; i<this.shopList.length ;i++) {
					
					if (this.shopList[i].checked==false) {
					
						console.log(i)
						datavaol.push(this.shopList[i])
						
					}
					
					
                  }
				
				this.shopList=datavaol*/
			
			},
			
			
			/*结算传值*/
			
			
			
			pro:function  () {
			
			   /*this.alertn(5) */
			  
			  
			  
				this.proid=[]
				 var _this = this
				 
				 	for (var i=0; i<this.shopList.length ;i++) {
					
					if( this.shopList[i].checked==true){
                		this.proid.push("id="+this.shopList[i].id)
                }
					
					
				}
				 	
				 	this.proid=this.proid.join("&")
				 	
				 
				/*console.log(this.proid)
			
			      this.porstr=this.proid.join("&")
			     console.log(this.porstr)*/
			     /* console.log(this.porstr.split(","))*/
			      
			      this.$router.push({ name: 'paymenta', query: { shopid:  this.proid }})

				/*_this.axios({
				 method: 'post',
				 headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		        'Content-Type':'application/json'
		    	},
				url: '/api/orders',
				
				data:this.moredek
				
				
			}).then(function(res) {
				console.log(res.data)
          

		    _this.setalert()
		  

			}).catch(function(err) {
				console.log(err)
				
				
				

			})
				
				*/
			
			 
			},
			
			
			
			goodid:function  () {
				
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
			
			//请求购物车信息
			trolleyData:function  () {
				
				 this.alertn(5) 
      		var _this = this
				 //请求购物车信息
			_this.axios({
				method: 'get',

				
					url: '/api/users/0/carts'
 
			}).then(function(res) {
				console.log(res.data.data)
				
			
				var datas=res.data.data;
				
				for(var i = 0; i <datas.length; i++) {
					if( datas[i].stock>0&&datas[i].disabled==false){
						 datas[i].checked=true
					}
					
				
					
					
					
				
				  datas[i].optiondatas=""
					
				}
				_this.Datas=datas;
				 _this.shopList=datas;
			
			
			    var toGetTypeIds=[];
			     var toGetType=[];
				var temgood;
				var n=0
				var len= datas.length
				var temtypeid;
				var goodsDtas;
				for( ; n <len; n++) {
					
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
				
				 _this.shopList=datas

				_this.setalert()
				_this.againM()
		        _this.againN()
			   
					
			

			}).catch(function(err) {
				console.log(err)
				_this.alertn(2, "稍后重试")
				_this.timeout()
              
			}) 
			},
			
		},
      mounted: function() {
      	
      	
      	//
      	
      	  
      	
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
			//请求购物车信息
			
			_this.trolleyData()
 
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
      		
      		
/*
             //请求购物车信息
			_this.axios({
				method: 'get',

				
					url: '/api/users/0/carts'
 
			}).then(function(res) {
				
				///console.log(res.data.data)
				var datas=res.data.data;
				
				for(var i = 0; i <datas.length; i++) {
				 datas[i].checked=true
				  datas[i].optiondatas=""
					
				}
				_this.Datas=datas;
				 _this.shopList=datas;
			
			
			    var toGetTypeIds=[];
			     var toGetType=[];
				var temgood;
				var n=0
				var len= datas.length
				var temtypeid;
				var goodsDtas;
				for( ; n <len; n++) {
					
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
				
				 _this.shopList=datas

				_this.setalert()
				_this.againM()
		        _this.againN()
			   
					
				
						

			}).catch(function(err) {
				console.log(err)
				_this.alertn(2, "稍后重试")
				_this.timeout()
              
			}) */	  
	}	
		
      	
      

	}
	
</script>
<style src='./../assets/css/trolley.css'></style>
<style>
	

	
	.trolley-goods ul li .items.imges img{
		height: 4rem;
	}
	
	.trolley-goods ul li .items.gdd{
		width: 10rem;
	}
	
	.goodItem{
		padding: 0px;
	}
	
	.trolley-logo p{
		font-size: 0.7rem;
	}
	
	.trolley-logo p span{
		color: orangered;
	}
</style>