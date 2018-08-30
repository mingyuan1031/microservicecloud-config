<template>
	   <div id="yuyue" >
 
	<div class="yuyue">
		<div class="yuyue_son"><img :src="yuyueimg" /></div>
	</div>
		
		<div class="yh_tianxie">
			<div class="yh_xinxi">
			<form action="" method="post">
				 <input class="name_t" type="text" value="" placeholder="请输入姓名"  v-model="msg.name" style="outline: none;">
                  <input class="tel_t" type="text" value="" placeholder="请输入手机号" v-model="msg.phone" style="outline: none;" >
			</form>
		    </div>
	 	</div>
	 	
	<div class="zf_xinxi">
		
		<!--<ul class="zf_bors">
			<li :class="{active:msg.typeind==index}"  @click="tabclss(index)" v-for="(item ,index) in msg.type">{{item}}㎡ </li>
		</ul>-->
		
		<div class="pick-yuyue">
			<div class="zf_bor" >住房面积</div>
							<ul>
								<li :class="{active:typeind==index}"  @click="tabclss(index)" v-for="(item ,index) in type">
									<span><i class="layui-icon">&#xe605;</i></span>{{item}}<small>m²</small> 
								</li>
								
							</ul>
						</div>
						
		
	</div>
	 
	<div class="tj_button" style="margin-top: 0px;">
		<div class="tijiao_bj" @click="sub">提交获得免费报价</div>
		<a href="tel:18595828663"><div class="dianhua_tel">一键咨询&nbsp;18595828663</div></a>
	</div>
	
	
	<!--alert-s-->
			
				<alertopen :text=tip></alertopen>
			<!--alert-end-->
			
<div>
	<router-link to="/logo">
	<p>登录</p>
	</router-link>
</div>

    </div>
</template>

<script>
	import  alert from './../components/alert.vue'
	import axios from 'axios'
		export default {
		
		name: 'yuyue',

		data() {
			return {
				yuyueimg:"",
				 typeind:0,
				type: [
			  
			  	
			  	"30-50",
			
			  
			  	"50-80",
			 
			  	
			  	"100+",
			  ], 
				tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
				msg:{ 
				"name": "",
				"phone": "",
			   
			    "area":"",
				
			},
			}
		},

		methods: {
            	
				tabclss:function  (index) {
            	this.typeind=index
            	this.msg.area=this.type[this.typeind]
				
            },
		 /*提交*/
		
		
		
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
				}, 2000)
				
				

			},
			
			alertdb: function(index, tex) {

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
		
		sub:function  () {
			
			var  tel=/^1[3|4|5|7|8][0-9]{9}$/
			if (this.msg.name==""||this.msg.name==null||this.msg.name.trim().length==0) {
				this.alertn(2, "姓名不能为空")
			}else if (this.msg.phone==""||this.msg.phone==null||this.msg.phone.trim().length==0) {
				this.alertn(2, "手机号不能为空")
			}else if(tel.test(this.msg.phone)==false){
				this.alertn(2, "手机号格式不正确")
			}else{
				this.alertn(5)
		
					
				var _this = this
						
				_this.axios({
				method: 'post',
				url: '/api/users/0/reservations',
				
               data:{
			        
			        name:this.msg.name,
			        phone:this.msg.phone,
			        area:''+this.type[this.typeind]+'m²'
			        
			    }
			}).then(function(res) {
				console.log(res.data)
                _this.alertn(1, "提交成功") 
                
				setTimeout(() => {
					_this.tip.alertopacity = 0
					_this.tip.alwarning = ""
					_this.tip.alertshow = 0 
					_this.tip.Alert=false
					_this.msg.name=""
					_this.msg.phone=""
					_this.typeind=0
				}, 2000)
				
			

			}).catch(function(err) {
				console.log(err)
				_this.alertn(2, "错误")
				setTimeout(() => {
					_this.tip.alertopacity = 0
					_this.tip.alwarning = ""
					_this.tip.alertshow = 0
					_this.tip.Alert=false
					
				}, 2000)
				

			})
				
			}
		}
		},
		
		components: { 
		
		
			
			alertopen:alert,
		},


		mounted: function() {
       //
       
       var _this = this
						
				_this.axios({
				method: 'get',
				url: '/api/cfgdatas',
			
			}).then(function(res) {
				
				console.log(res.data.data)
                
			_this.yuyueimg=res.data.data.storecfg.image

			}).catch(function(err) {
				console.log(err)
				
				

			})
				
			}
	} 
	
	
</script>
<style src='./../assets/css/user/public.css'></style>
<style src='./../assets/css/user/yuyue.css'></style>
<style>
	*{
				font-family: "微软雅黑";
			}
			
			.pick-yuyue{
              	font-size: 0px;
              	width: 92%;
              	margin: 0px auto;
              } 
              
              .pick-yuyue ul{
              	display:inline-block;
              	width: 100%;
              	margin: 0.7rem 0rem;
              }
               
               
               .pick-yuyue ul li{
              float: left;
               	height: 1rem;
               	
               	margin-bottom: 0.5rem;
               	margin-right: 0.5rem;
               	font-size: 0.7rem;
               	color: #333;
               }
			   
			   .pick-yuyue ul li small{
			   	font-size: 0.7rem;
			   	margin-left: 0.2rem;
			   }
			   
			  .pick-yuyue ul li span{
			  	width: 0.8rem;
			  	height: 0.8rem;
			  	display: block;
			  	border:solid 1px #AAAAAA;
			  	border-radius:0.2rem;
			  	float: left;
			  	margin-right: 0.3rem;
			  	text-align: center;
			  	line-height: 0.8rem;
			  }
			  
			  .pick-yuyue ul li span i{
			  	display: none;
			  	color: #4CD964;
			  	font-size: 0.6rem;
			  	font-weight:bold;
			  }
			  
			 /* .pick-yuyue ul li.active{
			  	color: orangered;
			  }
			  
			  .pick-yuyue ul li.active span{
			  	background: orangered;
			    border:solid 1px orangered;
			  }*/
			  
			  .pick-yuyue ul li.active span i{
			  display: block;
			  }
			
</style>