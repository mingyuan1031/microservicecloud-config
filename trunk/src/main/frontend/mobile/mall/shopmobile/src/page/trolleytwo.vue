<template>
	
	<!--模板盒子-s-->
		<div id="trolley" class="paddingBox">
			<div class="trolley-logo">
				<p>测试测试测试测试测试
					<a href="#">去登录 <i class="layui-icon ">&#xe602;</i></a>
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
										<span :class="{checked:item.checked==true}" @click="checked(index)">
										<i class="layui-icon">&#xe605;</i>
									</span>
									</div>

									<div class="items imges">
										<a href="../mobile/temple/good/gooditem.html">
											<small>
		        				  	 	<img :src="item.img"/>
		        				  	</small>
										</a>
									</div>

									<div class="items gdd">
										<a href="../mobile/temple/good/gooditem.html">
											<p>
												{{item.shopname}}
											</p>
											<span>
										{{item.title}} 
									</span>
											<span>
										x{{item.num}} 
									</span>

										</a>
										<div class="calculator">
											<small>￥{{item.totalM}}</small>
											<!--<span class="left" :class="{no:item.num=='1'}" @click="decrease(index)">-</span>
										<input readonly="readonly" class="left" type="text" name="" id="" :value="item.num" />
										<span class="left" :class="{no:item.num==item.maxNum}" class="left" @click="app(index)">+</span>-->
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
							<a>去逛逛</a>
						</li>
					</ul>
				</div>
			</div>

			<div class="close" v-if="totalNum>0">
				<ul>
					<li class="f">
						<!--	<p>共 {{totalNum}} 件 金额：</p>-->
						<p>合计：<span>{{totalMoney}}</span>元</p>
					</li>
					<li class="cen">

					</li>
					<li class="clo" @click="pro">
				         <router-link :to="{name:'paymenta',params:{shopid:0}}">去结算</router-link>
					</li>

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
	export default {
		name: 'trolley', 
		
		data() {
 
			return {
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
			totalMoney: 0,
			oldshopList: [], 
			msg:[],
			shopList: [{
					"checked": true,
					"shopname": "红田家居丨实木沙发茶几",
					"title": "圣马力诺",
					"num": 2,
					"maxNum": "9",
					"money": 1699,
					"totalM": 1699,
					"img": require('./../assets/img/index/banner2.jpg')
				}, {
					"checked": true,
					"shopname": "红田家居丨实木沙发茶几",
					"title": "西西里",
					"num": 1,
					"maxNum": "9",
					"money": 2599,
					"totalM": 2599,
					"img": require('./../assets/img/index/banner3.jpg')
				}, {
					"checked": true,
					"shopname": "红田家居丨实木沙发茶几",
					"title": "蓝色",
					"num": 2,
					"maxNum": "9",
					"money": 149,
					"totalM": 149,
					"img": require('./../assets/img/index/banner3.jpg')
				}

				, {
					"checked": true,
					"shopname": "Amazfit Cor手环",
					"title": "红色",
					"num": 1,
					"maxNum": "9",
					"money": 299,
					"totalM": 299,
					"img": require('./../assets/img/index/banner2.jpg')
				}
			],
			
			
				
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

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					if (type==true) {
							this.alertdb(ind, word)
					}
					
					this.append()
				
				}, 2000)
				 
				

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
			
			again:function  () {
				
			//归零
			this.totalNum=0
			this.totalMoney=0		

		   //重新计算数量
			for(var i = 0; i < this.shopList.length; i++) {
                
                if (this.shopList[i].checked==true) {
                	this.totalNum += this.shopList[i].num
                }
	
			}

			

			//重新计算总金额金额

			
			for(var i = 0; i < this.shopList.length; i++) {
               if (this.shopList[i].checked==true) {
				this.totalMoney += this.shopList[i].totalM
				}
			}
				
			},
				//商品是否选中
			checked: function(index) {
				this.shopList[index].checked = !this.shopList[index].checked;
				var delnum = this.shopList[index].num
				//判断选中状态
				if(this.shopList[index].checked == false) {
					this.totalNum -= delnum //总数量减去当前商品数量
					this.totalMoney -= this.shopList[index].totalM //总价减去上当前商品的总价格
					
					
				} else {
					this.totalNum += delnum //总数量加上当前商品数量
					this.totalMoney += this.shopList[index].totalM //总价格加上当前商品的总价格
					
					
				}
			},
			//当前商品数量++ 
			app: function(index) {
				this.shopList[index].num++;

				if(this.shopList[index].num > this.shopList[index].maxNum) {
					this.shopList[index].num = this.shopList[index].maxNum;
					return false;
				}

				this.totalNum++
					this.totalMoney += this.shopList[index].money
				this.shopList[index].totalM = this.shopList[index].num * this.shopList[index].money
			},
			//当前商品数量--
			decrease: function(index) {
				this.shopList[index].num--;
				if(this.shopList[index].num < 1) {
					this.shopList[index].num = 1;
					return false;
				}

				this.totalNum--
					this.totalMoney -= this.shopList[index].money
				this.shopList[index].totalM = this.shopList[index].num * this.shopList[index].money
			},

           
             
           alertdel:function  (index) {
           	this.delindex=index
           	    this.open(6, "你确认删除吗")
           	    
           	  
           },


			/*删除当前商品*/
			del: function(index) {
				//判断选中状态
				
				
				
				
				if(this.shopList[index].checked == true) {
					this.totalNum -= this.shopList[index].num //总数量减去当前商品数量
					this.totalMoney -= this.shopList[index].totalM //总价减去上当前商品的总价格
				}

				this.shopList.splice(index, 1)

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
				this.compileshow=!this.compileshow
				
				if (this.compileshow==true) {
					for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=false
				}
				}else{
					for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=true
					
						console.log(this.shopList.length+"/")
						
					
						
				}
				}
				
					this.again()
				
			},
			
			
			
			/*多选*/
			pickckend:function(){
				if(this.pickck==false){
				
				for (var i=0; i<this.shopList.length ;i++) {
					this.shopList[i].checked=true
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
				
				var delval=[]
				var datavaol=[]
				for (var i=0; i<this.shopList.length ;i++) {
					
					if (this.shopList[i].checked==false) {
					
						console.log(i)
						datavaol.push(this.shopList[i])
						
					}
					
					
                  }
				
				this.shopList=datavaol
			
			},
			
			
			/*结算传值*/
			
			
			
			pro:function  () {
			
			 
			
			 
			}
			
			
			
			
			
		},
      mounted: function() {
      	this.delM=this.shopList.length

          this.oldshopList = this.shopList
			var topList = document.querySelectorAll('.rightBox-item').length
			//初始每个商品的金额

			for(var i = 0; i < topList; i++) {

				this.shopList[i].totalM = this.shopList[i].num * this.shopList[i].money
			}
			
			this.again()

		}



		/**/

	}
	
</script>
<style src='./../assets/css/trolley.css'></style>
<style>
</style>