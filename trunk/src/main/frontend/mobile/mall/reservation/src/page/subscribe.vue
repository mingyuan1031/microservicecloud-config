<template>
	
	<div id="order" class="order ">

			<div class="order-nav">
				<!--全部订单-->
				<div class="order-class">
					<!--没有货-->
					<div class="nonal" v-show="orderlist.length==0">
						<p>空空如也~</p>

					</div>

					<div class="order-item" v-for="(item, index) in orderlist" :key="index">

						<div class="order-itemNav">

							<div class="ordernum">
								预约时间：{{item.created}}
								<span v-show="item.status==0">未处理</span>
								<span v-show="item.status==1">商谈中</span>
								<span v-show="item.status==2">已下单</span>
								<span v-show="item.status==3">已取消</span>
								
							</div>

							<div class="order-good">
                                 <p>姓名：{{item.name}}</p>
                                 <p>手机号：{{item.phone}}</p>
                                 <p>住房面积：{{item.area}}</p>
							</div>

						</div>
					</div>

				</div>

				<!--全部订单-->

			</div>
<alertopen :text=tip></alertopen>
		</div>
	
</template>

<script>
	
		import  alert from './../components/alert.vue'
		export default {
		
		name: 'order',

		data() {
			return {
	        tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},			
     
			orderlist: []
		
			}
		},
		
		components: { 
			alertopen:alert,
		},

		methods: {
            	delorder: function(index) {
				this.orderlist.splice(index, 1)
			},
			
			alertn: function(index, tex,type,ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				/*setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					if (type==true) {
							this.alertdb(ind, word)
					}
				}, 2000)*/
				
				

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
		},
		
		

		mounted: function() {
			
			
			 this.alertn(5) 
      	
      		var _this = this

			_this.axios({
				method: 'get',
               url: '/api/users/0/reservations'
               
 
			}).then(function(res) {
				
			console.log(res.data)
				_this.orderlist=res.data.data
				_this.setalert()

			}).catch(function(err) {
				console.log(err)
				
			_this.alertn(2, "稍后重试")
				_this.timeout()
              
			}) 
			
       
	}
	} 
	
	
</script>
<style src='./../assets/css/subscribe.css'></style>
<style>
</style>