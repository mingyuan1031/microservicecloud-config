<template>
	<div id="user">
		<div class="paddingBox">
			<div class="center_ba ">
				<div class="center_ba_son user_titie">
					<!--已登录-->
					<div >
						<router-link to="/user/usermsg">
							<div class="user_img" :style="{backgroundImage: 'url(' + msg.avatar + ')' }">
								<!--<img :src="msg.avatar" />-->
							</div>

							<div class="user_center">

								<div class="uid">
									<p>
										{{msg.name}}
									</p>
									<!--<span>	{{msg.title}} </span>-->
									<ul>
										<li v-show="msg.type==0" style="background: cornflowerblue;">普通会员</li>
										<li v-show="msg.type==1">设计师</li>
										<li v-show="msg.type==2" style="background: cadetblue;">安装工</li>
									</ul>
								</div>

							</div>
						</router-link>
					</div>

					<!--未登录-->

					<!--<div v-show="msg.uid==-1">

						<div class="user_img">
							<img :src="msg.imges" />
						</div>

						<div class="user_center">

							<div class="noid">
								<p>登录/注册</p>
							</div>

						</div>
					</div>-->

				</div>

			</div>
			<div class="center_list ">
				<ul class="center_li ">
					<li>
						<router-link :to="{name:'order',params:{orderid:0}}">
							<div class="item">

								<img src="../assets/img/center/ico2.jpg" />
								<span style="line-height: 0.8rem; ">{{allorder}}</span>
							</div>

							<p>全部</p>
						</router-link>
					</li>
					<li>
						<router-link :to="{name:'order',params:{orderid:1}}">
							<div class="item">
								<img src="../assets/img/center/ico1.jpg" />
								<span style="line-height: 0.8rem; ">{{daifu}}</span>
							</div>

							<p>待付款</p>
						</router-link>
					</li>
					<li>
						<router-link :to="{name:'order',params:{orderid:2}}">
							<div class="item">
								<img src="../assets/img/center/ico3.jpg" />
								<span style="line-height: 0.8rem; ">{{yifu}}</span>
							</div>

							<p>待发货</p>
						</router-link>
					</li>
					<li>
						<router-link :to="{name:'order',params:{orderid:3}}">
							<div class="item">
								<img src="../assets/img/center/ico4.jpg" />
								<span style="line-height: 0.8rem; ">{{sohuo}}</span>
							</div>

							<p>待收货</p>
						</router-link>
					</li>
					<li>
						<router-link :to="{name:'order',params:{orderid:4}}">
							<div class="item">
								<img src="../assets/img/center/ico5.jpg" />
								<span style="line-height: 0.8rem; ">{{pinjia}}</span>
							</div>

							<p>待评价</p>
						</router-link>
					</li>

					<div class="clearfix "></div>

				</ul>
			</div>
			<div class="center_de ">
				<ul class="center_deon ">
					<li class="center_border ">
						<router-link :to="{name:'order',params:{orderid:0}}">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/dingdan.png" />
								</div>
								<p class="wode_dd ">我的订单</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>

					<li class="center_border ">
						<router-link to="/user/myshare">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/kuaixiang.png" />
								</div>
								<p class="wode_dd ">我的快享
								</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>

					<li class="center_border ">
						<router-link to="/user/subscribe">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/yueyu.png" />
								</div>
								<p class="wode_dd ">我的预约</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>

					<li class="center_border ">
						<router-link to="/user/paymenthistory">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/zhifu.png" />
								</div>
								<p class="wode_dd ">支付记录</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>

			<!--		<li class="center_border ">
						<router-link to="/orderpaymenta">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/centers (3).png" />
								</div>
								<p class="wode_dd ">我的分销</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>
					<li class="center_bor ">
						<router-link to="/card">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/centers (4).png" />
								</div>
								<p class="wode_dd ">领券中心</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>-->
					<li class="center_border ">
						<router-link to="/user/site">
							<div class="center_de_son ">
								<div class="center_img ">
									<img src="../assets/img/center/dizhi.png" />
								</div>
								<p class="wode_dd ">地址管理</p>
								<div class="float_right ">
									<img src="../assets/img/center/jt.png" />
								</div>
								<div class="clearfix "></div>
							</div>
						</router-link>
					</li>

				</ul>
			</div>
			<!--底部-->

			<support></support>

			<floors></floors>
		</div>

	</div>
</template>

<script>
	import support from './../components/support.vue'
	import floor from './../components/bottom.vue'
	export default {
		name: 'user',

		data() {

			return {

				msg: {
					

				},
				
				allorder:0,
				daifu:0,
               yifu:0,
               sohuo:0,
               pinjia:0,
			}

		},

		/**/

		components: {
			support: support,
			floors: floor
		},

		methods: {

		},
		mounted: function() {
			var _this = this
			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
				url: '/api/users/0'

			}).then(function(res) {
          
				
				_this.msg=res.data.data
			
console.log(_this.msg)
			}).catch(function(err) {
				console.log(err)

			})
			
			
			_this.axios({
				method: 'get',

				
				url: '/api/users/0/orders'

			}).then(function(res) {


				  
				  
				  var datas=res.data.data;
                  var n=0;
                  var len=datas.length
                  _this.allorder=datas.length
                
				for( ; n <len; n++) {
					
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
				
				     

                   }
				  
				  
				  
				  
				  
				  
			}).catch(function(err) {
				
        
			})
			
			//预加载数据
			
				/*var cfg=window.lwxfPreload
	
	           _this.msg=cfg.preload.user*/

		}
		/**/

	}
</script>

<style src='./../assets/css/user/center.css'></style>
<style src='./../assets/css/user/public.css'></style>

<style>
	.user_titie {
		position: relative;
		width: 100%;
		height: 3.6rem;
		overflow: hidden;
		background: url(../assets/img/center/usertitlebg.jpg) no-repeat center;
		background-size: cover;
		background-position: 50% 50%;
		padding: 2rem 0rem;
	}
	
	.user_img {
		width: 3.6rem;
		height: 3.6rem;
		border-radius: 50%;
		overflow: hidden;
		margin-left: 1rem;
		float: left;
		 background-position: 50% 50%;
	    background-size: cover;
		/*background-size: 100% 100%;
		
		position: relative;*/
		
	}
	
	.user_img img {
		display: block;
		width: 100%;
		position: absolute;
		top: 0px;
		left: 0px;
		right: 0px;
		bottom: 0px;
		margin: auto;
		min-width: 3.6rem;
		min-height: 100%;
		max-width: 5rem;
		
	    
		
	}
	
	.user_center {
		float: left;
		margin-left: 0.3rem;
	}
	
	.user_center .uid {
		padding-top: 0.2rem;
		width: 12rem;
	}
	
	.user_center .uid p {
		font-size: 0.7rem;
		color: #FFFFFF;
		font-size: 0.7rem;
	}
	
	.user_center .uid span {
		display: block;
		font-size: 0.7rem;
		color: #FFFFFF;
		font-size: 0.6rem;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: 100%;
	}
	
	.user_center .noid {
		color: white;
		font-size: 0.6rem;
		line-height: 3rem;
	}
	
	.user_center .uid ul {
		width: 100%;
		margin-top: 0.2rem;
	}
	
	.user_center .uid ul li {
		float: left;
		font-size: 0.5rem;
		color: white;
		background: orangered;
		padding: 0.1rem 0.2rem;
		border-radius: 0.2rem;
	}
	
	.center_deon li {
		background: white;
	}
	
	.user_center {
		margin-top: 0.5rem;
	}
	
	.user_center .uid p{
		line-height: 2rem;
		font-size: 0.8rem;
	}
</style>