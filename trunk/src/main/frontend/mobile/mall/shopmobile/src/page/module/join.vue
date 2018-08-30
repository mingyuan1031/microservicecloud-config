<template>
	<div id="jiameng">
		<!--<a id="button_buts"></a><div><img class="jiamengImg" :src="imgUrls"></div>
-->		<div id="join">
			<ul class="mainUls">
				<li class="ulLi-spe"><p>- 我要加盟  -</p></li>
				<li class="ulLis">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../../assets/img/yuyue_imgs/map.png"/></li>				
					<li style="width: 78%; margin-left:0.4rem;"><input v-model="msg.area" type="text" placeholder="地址" required="required"></li>
					<!--<li style="width: 10%; font-size: 0.8rem; text-align: right;"><img style="width: 30%; height: auto;" src=".../assets/img/jiameng/xingzhuang.png"/></li>-->
				    </ul>
				</li>
				<li class="ulLis">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../../assets/img/yuyue_imgs/yonghu.png"/></li>				
					<li style="width: 78%; margin-left:0.4rem;"><input type="text" placeholder="姓名" v-model="msg.name"></li>
					
				    </ul>
				</li>
				<li class="ulLis">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../../assets/img/yuyue_imgs/dianhua.png"/></li>				
					<li style="width: 78%; margin-left:0.4rem;"><input type="text"maxlength="11" placeholder="电话" required="required" v-model="msg.area.phone"></li>
					
				    </ul>
				</li>
			
				<li>
					
					<div class="button_anniu" style="margin-top: 1.2rem;" ><button  type="button" style="background-color: #d82626;" @click="sub">申请加盟</button></div></li>
				<li><div class="button_anniu" style="margin-top: 0.5rem;" @click="tel"><button type="button" style="background-color: #289c09;">一键拨通</button></div></li>
			   
			</ul>
		</div>
		 <div>
		 	<ul class="jiameng_zc">
		 		<li><p class="jm_zhichi">- 加盟支持  -</p></li>
		 		<li><img class="jiamengImg" :src="zcUrl"></li>
		 		<li>
		 			<a href="#join"><div class="yijian_jm">一键加盟红田家居</div></a>
		 		</li>
		 	</ul>
		 	<ul class="jiameng_zc">
		 		<li><p class="jm_zhichi">- 加盟条件  -</p></li>
		 		<li><img class="jiamengImg" :src="tjUrl"></li>
		 		<li>
		 			<a href="#join"><div class="yijian_jm">一键加盟红田家居</div></a>
		 		</li>
		 	</ul>
		 	<ul class="jiameng_zc">
		 		<li><p class="jm_zhichi">- 其他条件  -</p></li>
		 		<li><img class="jiamengImg" :src="qtUrl"></li>
		 		<li>
		 			<a href="#join"><div class="yijian_jm">一键加盟红田家居</div></a>
		 		</li>
		 	</ul>
		 	<ul class="jiameng_zc">
		 		<li><p class="jm_zhichi">- 加盟流程-</p></li>
		 		<li><img class="jiamengImg" :src="lcUrl"></li>
		 		<li>
		 			<a href="#join"><div class="yijian_jm">一键加盟红田家居</div></a>
		 		</li>
		 	</ul>
		 	
		 </div>
		 <alertopen :text=tip></alertopen>
	</div>
</template>

<script>


import  alert from './../../components/alert.vue'
	  import axios from 'axios'
		export default {
		
		name: 'jiameng',

		data() {
			return {
				yuyueimg:"",
				 typeind:0,
				 imgUrls:require('../../assets/img/yuyue_imgs/top.png'),
				zcUrl:require('../../assets/img/yuyue_imgs/tu3.png'),
				tjUrl:require('../../assets/img/yuyue_imgs/tu4.png'),
				qtUrl:require('../../assets/img/yuyue_imgs/tu5.png'), 
				lcUrl:require('../../assets/img/yuyue_imgs/tu6.png'),
				
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
			
			call:"",
			isend:false,
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

				
				

			},
			
			alertdb: function(index, tex) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
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
		
		sub:function  () {
			
			console.log(this.msg.area)
			
			var  tel=/^1[3|4|5|7|8|9][0-9]{9}$/
			var tela=/^0\d{2,3}-?\d{7,8}$/ 
			var zuiji=/^400([0-9]){1}([0-9]{5})([0-9]){1}$/
			 

			
			if (this.msg.area==""||this.msg.area==null||this.msg.area.trim().length==0) {
				this.alertn(2, "请输入详细地址")
				
				this.timeout()
			}else if (this.msg.name==""||this.msg.name==null||this.msg.name.trim().length==0||this.msg.name.trim().length>10) {
				this.alertn(2, "姓名不能为空且不能超过10个字符")
				this.timeout()
			}else if (this.msg.phone==""||this.msg.phone==null||this.msg.phone.trim().length==0) {
				this.alertn(2, "联系方式不能为空")
				this.timeout()
			}else if(tel.test(this.msg.phone)==false&&tela.test(this.msg.phone.trim())==false&&zuiji.test(this.msg.phone.trim()) == false){
				this.alertn(2, "联系方式格式不正确")
				this.timeout()
			}else{
				this.alertn(5)
		  
					
				var _this = this
						
				_this.axios({
				method: 'post',
				url: '/api/users/0/reservations',
				
               data:{
			        
			        name:this.msg.name,
			        phone:this.msg.phone,
			        area:this.msg.area
			        
			    }
			}).then(function(res) {
				console.log(res.data)
                _this.alertn(1, "提交成功") 
              
               // _this.isEnd();
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
		},
		
		tel:function  () {
			
			console.log(this.call)
			
			window.location.href = "tel:"+this.call+""
			
		},
		
		notji:function(){
			 this.alertn(4, "5分钟内不能重复提交") 
			 setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				}, 2000)
		},
		
		isEnd:function  () {
			
				
		
			this.isend=!this.isend
			console.log(this.isend)
			setTimeout(() => {
					this.isend=!this.isend
					
				}, 5000)
			
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
                
			_this.yuyueimg=res.data.data.storecfg.poster
			_this.call=res.data.data.storecfg.reservationCall

			}).catch(function(err) {
				console.log(err)
				
				

			})
				
			}
	} 
</script>

<style >
*{margin: 0px; padding: 0px; margin: 0px; list-style: none; }
body{background-color:#f8f8f8; }
.jiamengImg{width:100%; display: block;}
.ulLis{width:76%;font-size: 0; margin: auto; height: 1rem; margin-top: 0.6rem; background-color:#f8f8f8; font-size: 0.8rem;color:#8e8e8e;line-height:0.8rem;padding: 0.6rem 0.6rem; }
.ulLis ul li{
	line-height: 1rem;
	float: left;
	height: 1rem;
	font-size: 0.8rem;
}
.ulLis ul li img{height: 1rem;}
.ulLis ul li input{width: 100%; height:1rem;background-color:#f8f8f8;border:hidden; outline: hidden;}
.ulLi-spe{text-align: center;font-size: 1.2rem; color: #eb4314; font-weight: bold;}
.button_anniu{width: 84%;  margin: auto;}
.button_anniu button{width:100%;height: 2.2rem;color: white; font-size: 0.9rem; border: none;}
.mainUls{padding:0.8rem 0 1.3rem 0; background-color:#f1ede8; margin-top:0.5rem;  border-radius: 0.2rem;-moz-box-shadow:1px 3px 10px #f8f8f8; -webkit-box-shadow:1px 3px 10px #f8f8f8; box-shadow:1px 3px 10px #f8f8f8;}
.jiameng_zc{margin: 1.5rem 0;}
.jiameng_zc li{margin: 1rem 0;}
.jm_zhichi{text-align: center;font-size: 1.2rem; color: #eb4314; font-weight: bold;}

.yijian_jm{ width:66%; text-align:center; line-height:2rem; font-size:1rem; color:white; background-image: url(../../assets/img/yuyue_imgs/button_bg.png);margin: auto; background-repeat: no-repeat; background-size: 100% 100%; height: 3rem;}
</style>