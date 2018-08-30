<template>
	
	<div id="order" class="order ">

			<div class="order-nav">
				<!--全部订单-->
				<div class="order-class">
					<!--没有货-->
					<div class="nonal" v-show="orderlist.length==0">
						<p>当前未有支付记录~</p>

					</div>

					<div class="order-item" v-for="(item, index) in orderlist" :key="index">

						<div class="order-itemNav">

							<div class="ordernum">
							
								支付单号：{{item.paidNum}}<!--<span @click="delorder(index)">删除信息</span>-->
							</div>
							
                             
                             <div class="order-money">
                                <h3>
                                	<span>￥{{item.paidPrice}}元</span>
                                	<small>微信支付</small>
                                </h3>
							</div>
							
							<div class="ordernum">
							
								支付时间：{{item.created}}<!--<span @click="delorder(index)">删除信息</span>-->
							</div>
                             
							<!--<div class="order-good">
								<a>
									 <p><b>小米手机</b><span>￥ 1600.00</span><small>x2</small></p>
								</a>
                                <a>
                                	<p><b>小米小爱音响</b></b><span>￥ 299.00</span><small>x2</small></p>
                                </a>
                                <a>
                                	 <p><b>小米手环3</b><span>￥ 169.00</span><small>x2</small></p>
                                </a>
                               
							</div>-->

						</div>
					</div>

				</div>

				<!--全部订单-->

			</div>
      <alertopen :text=tip></alertopen>
		</div>
	
</template>

<script>
	
		import  alert from './../../components/alert.vue'
		
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

			orderlist: [

			]
		
			}
		},

		methods: {
			
				alertn: function(index, tex,type,ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				
				

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
			
			
            	delorder: function(index) {
				this.orderlist.splice(index, 1)
			},
		},
 components: { 
	alertopen:alert,
		},
		mounted: function() {
			this.alertn(5)
			var _this = this
			_this.axios({
				method: 'get',

				
				url: '/api/users/0/paidrecords'

			}).then(function(res) {
          
				console.log(res.data.data)
				_this.orderlist=res.data.data
			if(res.data.error){
				_this.alertn(2,res.data.error.message)
				_this.timeout()
		        return false
					   }

                  _this.setalert()

			}).catch(function(err) {
				console.log(err)

			})
			
			
       
	}
	} 
	
	
</script>
<style src='./../../assets/css/paymenthistory.css'></style>
<style>
</style>