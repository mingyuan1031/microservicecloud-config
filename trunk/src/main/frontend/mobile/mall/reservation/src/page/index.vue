<template>
	<div id="yuyue">
		<div id="top"><img class="yuyueImg" :src="imgUrl"></div>
		<div id="subscribeshow">
			<ul class="mainUl" >
				
				<li class="ulLi">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../assets/img/yuyue_imgs/fangzi.png"/></li>
					<li style="width: 30%;  font-size: 0.7rem; text-align: center;">户型面积</li>
					<li style="width: 50%;"><input type="number" v-model="msg.area" required="required"></li>
					<li style="width: 10%; font-size: 0.8rem; text-align: right;">㎡</li>
				    </ul>
				</li>
				<li class="ulLi">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../assets/img/yuyue_imgs/yonghu.png"/></li>
					<li style="width: 30%;  font-size: 0.7rem; text-align: center;" required="required">您的姓名</li>
					<li style="width: 50%;"><input type="text" v-model="msg.name"></li>
					
				    </ul>
				</li>
				<li class="ulLi">
					<ul style="font-size: 0;">
					<li style="width: 8%; "><img style="width: 100%; " src="../assets/img/yuyue_imgs/dianhua.png"/></li>
					<li style="width: 30%;  font-size: 0.7rem; text-align: center;">您的电话</li>
					<li style="width: 50%;"><input type="tel" v-model="msg.phone" required="required"></li>
					
				    </ul>
				</li>
				<li><div style="width: 100%; margin:auto; text-align: center;margin-top: 2rem; font-size: 0.7rem;">已有<span style="color: red;font-size: 0.7rem;">{{numBer}}</span>位业主提交需求</div></li>
				<li><a name="button_btu"></a><div style="width: 96%;  margin: auto; margin-top: 0.8rem;">
					<span @click="sub" class="subutton">提交需求  获取报价及方案</span></div></li>
				<!--<li><div style="width: 100%; margin:auto; text-align: center;margin-top: 0.8rem; font-size: 0.6rem; color: #8e8e8e;">－同意注册成为红田家居平台用户－</div></li>-->
			</ul>
		</div>
		<div style="margin-top: 1rem;"><img class="yuyueImg" :src="middleUrl"></div>
		<div><img class="yuyueImg" :src="bottomUrl"></div>
		<ul class="fixed" style="font-size: 0;">
			<li class="yuyueLi" @click="tel">{{call}}</li>
			<li class="yuyueLibutton" ><a href="#subscribeshow"><span >立即预约</span></a></li>
		</ul>
		
		<alertopen :text=tip></alertopen>
		
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
				 numBer:0,
				 imgUrl:require('../assets/img/yuyue_imgs/tu1.png'),
				middleUrl:require('../assets/img/yuyue_imgs/tu2.png'),
				bottomUrl:require('../assets/img/yuyue_imgs/tu3.png'),
				
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
			
			
			
			var  tel=/^1[3|4|5|7|8|9][0-9]{9}$/
			var tela=/^0\d{2,3}-?\d{7,8}$/ 
			var zuiji=/^400([0-9]){1}([0-9]{5})([0-9]){1}$/
			 

			
			if (this.msg.area==""||this.msg.area==null||this.msg.area.trim().length==0||this.msg.area.trim().length>10) {
				this.alertn(2, "请输入住房面积")
				
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
                 _this.numBer+=1
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
			
		    var  cfg=window.lwxfPreload
			this.numBer=cfg.preload.reservations
				
			}
	
	} 
	
	

</script>

<style >
*{margin: 0px; padding: 0px; margin: 0px; list-style: none; }
body{background-color:#f8f8f8; }
.yuyueImg{width:100%; display: block; margin-bottom: 3rem;}
.ulLi{width:86%;font-size: 0; margin: auto; height: 1rem; margin-top: 0.6rem; background-color:#f8f8f8; font-size: 0.8rem;color:#8e8e8e;line-height:0.8rem;padding: 0.6rem 0.6rem; }
.ulLi ul li{
	line-height: 1rem;
	float: left;
	height: 1rem;
	font-size: 0.8rem;
}
.ulLi ul li img{height: 1rem;}
.ulLi ul li input{width: 100%; height:1rem;background-color:#f8f8f8;border:hidden; outline: hidden;}
.mainUl{padding:0.8rem 0 1.3rem 0; margin:0 0.4rem;background-color: white;  border-radius: 0.2rem;-moz-box-shadow:1px 3px 10px #f8f8f8; -webkit-box-shadow:1px 3px 10px #f8f8f8; box-shadow:1px 3px 10px #f8f8f8;}
.yuyueLi{width: 60%; background-color: #da9a1b; font-size: 0.9rem; color: white; text-align: center; height: 2.2rem; line-height: 2.2rem; display: inline-block;}
.yuyueLibutton span{display: block; height: 2.2rem; line-height: 2.2rem;  background-color: #d82626; font-size: 0.9rem; color: white; text-align: center;  width: 100%;}
.yuyueLibutton{width:40%;  display: inline-block;}
.yuyueLibutton span{border: none; display: block;}
.fixed{width:100%;display:block;position: fixed; left:0px; bottom:0px;}

.subutton{
	width:100%;background-color: #d82626; height: 2rem;color: white; font-size: 0.8rem;
	line-height: 2rem;
	display: block;
	text-align: center;
}

</style>