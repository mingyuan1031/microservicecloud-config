<template>

	<div id="site" class="paddingBox ">
		<!---->

		<div class="pick">
			<div class="shopSite-title">
				<div class="title">
					地址管理
				</div>
			</div>

			<div class="pickcontent">

				<div class="site-itemsite">
					<ul style="padding: 0rem;">

						<div class="notsite" v-show="siteList.length<=0">
							还没有地址，赶紧去去添加吧~
						</div>

						<li v-for="(item,index) in siteList">
						 <div class="site-nav">
						 	<div class="item pack">
								<span :class="{siteCk:item.defaulted==true}" @click="siteCk(index)">
						  					<i class="layui-icon">&#xe605;</i>
						  				</span>
							</div>

							<div class="item siteNav">
								<p>收货人：{{item.receiver}} <span>{{item.receiverPhone}}</span></p>
								<!--<h3>收货地址：{{item.districtId}} {{item.districtId}}</h3>-->
								<h3>收货地址:{{item.receiverAddress}}</h3>
							</div>
						 </div>
							
							<div class="siteBut">
							
								<span @click="delsite(item.id)" v-show="item.defaulted==false"> <i class="layui-icon " >&#xe640;</i>删除</span>
								<span v-show="item.defaulted==true" @click="mrdz(item.id)"> <i class="layui-icon " >&#xe640;</i>删除</span>
								<span>
									<router-link :to="{name:'amend',params:{siteid:item.id}}">
								   		<i class="layui-icon ">&#xe642;</i>修改
								     </router-link>
								</span>
							</div>

							<!--<div class="siteAmend">
						  			 
						  			</div>-->

						</li>

					</ul>
				</div>

			</div>

			<div class="appenD">
				<router-link to="/user/siet/addsite">
					<p>添加新地址</p>
				</router-link>
			</div>

		</div>
		
		<!--alert-s-->
			
				<alertopen :text=tip></alertopen>
			<!--alert-end-->

	</div>

</template>

<script>
	import axios from 'axios'
  import  alert from './../../components/alert.vue'
	export default {
		name: 'site',

		data() {

			return {

				 tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},

				siteList:{


				},

			}

		},

		/**/

		components: { 
		
				
				alertopen:alert,
		},
		methods: {
				 alertn: function(index, tex,type,ind, word) {

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
			
			mrdz:function(uid){
				
				var _this=this
				
				if(this.siteList.length>1){
					this.alertn(4,"默认地址不可删除")
					setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0 
					this.tip.Alert=false
					
				}, 2000)
				}else{
								//alert(uid)
				this.alertn(5)
			    var _this = this
						
				_this.axios({
				method: 'delete',
				url: '/api/addresses/'+uid+'',

			}).then(function(res) {
				console.log(res.data)
               _this.alertn(1,"删除成功")
             // _this.siteList=res.data.data
              _this.ax()
				setTimeout(() => {
					_this.tip.alertopacity = 0
					_this.tip.alwarning = ""
					_this.tip.alertshow = 0 
					_this.tip.Alert=false
					
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
			
			
			siteCk: function(index) {
				
				
			    this.alertn(5)
				var _this = this
				
				_this.axios({
				method: 'put',
				url: '/api/users/0/address/'+this.siteList[index].id+'/defaulted',
				
              
			}).then(function(res) {
				
               if(res.data.error){
				_this.alertn(2,res.data.error.message)
				_this.timeout()
		        return false
					   }
		        _this.ax()
			
		   	

			}).catch(function(err) {
				console.log(err)
				
				

			})
          

				
				/*if(this.siteList[index].defaulted == 0) {
					for(var i = 0; i < this.siteList.length; i++) {

						this.siteList[i].defaulted = 0

					}
					this.siteList[index].defaulted = 1
				}*/
			},
			
			
			//删除地址
			delsite:function  (uid) {
				
				//alert(uid)
				this.alertn(5)
			    var _this = this
						
				_this.axios({
				method: 'delete',
				url: '/api/addresses/'+uid+'',

			}).then(function(res) {
				console.log(res.data)
               _this.alertn(1,"删除成功")
             // _this.siteList=res.data.data
              _this.ax()
				setTimeout(() => {
					_this.tip.alertopacity = 0
					_this.tip.alwarning = ""
					_this.tip.alertshow = 0 
					_this.tip.Alert=false
					
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
			
			
			
			ax:function  () {
				 this.alertn(5) 
				var _this = this

			_this.axios({
				method: 'get',

				url: '/api/users/0/address' 

			}).then(function(res) {
				console.log(res.data.data)
				
				_this.siteList=res.data.data

              _this.setalert()

			}).catch(function(err) {
				console.log(err)

			}) 
			}
			

		},
		mounted: function() {

			/*this.ax()*/
			 this.alertn(5) 
				var _this = this

			_this.axios({
				method: 'get',

				url: '/api/users/0/address' 

			}).then(function(res) {
				
				
				console.log(res.data.data)
				
				_this.siteList=res.data.data

               _this.setalert()
              

			}).catch(function(err) {
				console.log(err)
				 _this.alertn(2,"请稍后重试") 
              _this.timeout()
			})
		}
		/**/

	}
</script>
<style src='./../../assets/css/site.css'></style>
<style>
body{
	background: whitesmoke;
}

.shopSite-title{
	padding: 0rem;
}
.site-itemsite ul{
	padding: 0rem;
}

.site-itemsite ul li{
	border-bottom: none;
	padding: 0.5rem 0rem;
	padding-bottom: 0rem;
	
}
</style>