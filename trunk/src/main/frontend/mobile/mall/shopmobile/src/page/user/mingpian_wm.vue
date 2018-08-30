<template>
	<div id="mingpian">

		<div class="cardbanner">
			<img :src="msg.storecfg.header" />
		</div>
		<div class="mp_de">
			<ul class="mp_deon">
				<li class="mp_center_border">

					<div class="mp_de_son">
						<p class="mp_dd">商家名称</p>
						<div class="mp_float_rightbd" style="padding-top: 0.3rem;">{{msg.storecfg.name}}</div>
						

						<div class="clearfix"></div>
					</div>

				</li>
				<li class="mp_center_border">
					<div class="center_de_son">
						<p class="mp_dd">商家电话</p>

						<div class="mpphone" @click="tel">{{msg.storecfg.reservationCall}}</div>
 
								
						</div>
				</li>
				<li class="mp_center_border">
					<div class="center_de_son">
						<p class="mp_dd">联系人</p>
							<div class="mp_float_right">{{msg.storecfg.linkman}}</div>
					
						<div class="clearfix"></div>
					</div>
				</li>
				<li class="mp_center_border">
					<div class="center_de_son">
						<p class="mp_dd">经营范围</p>
						
						<div class="mp_float_rightbd ">{{msg.storecfg.scope}}</div>
						<div class="clearfix"></div>
					</div>
				</li>
				<li class="mp_center_border">
					<div class="center_de_son">
						<p class="mp_dd">商家地址</p>
						
						<div class="mp_float_rightbd" style="line-height: 0.9rem;">{{msg.storecfg.address}}</div>
						<div class="clearfix"></div>
					</div>
				</li>

				<div class="erm">
					<img src="../../assets/img/mingpian_imgs/857522379732117614.jpg" />
					<p>技术支持：老屋新房</p>
				</div>

			</ul>
			</div>

		</div>
</template>

<script>
	import axios from 'axios'
	export default {

		name: 'mingpian',

		data() {
			return {

				msg: {
					"storecfg": {

					}

				},

				overshow: false,

			}
		},

		methods: {
			overplay: function() {

				this.overshow = !this.overshow
			},
			
				tel:function  () {
			
			console.log(this.call)
			
			window.location.href = "tel:"+this.msg.storecfg.reservationCall+""
			
		},
		},

		mounted: function() {
			
			var _this = this

			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
				url: '/api/cfgdatas'

			}).then(function(res) {
				//console.log(res.data)

				_this.msg = res.data.data

				console.log(_this.msg)

			}).catch(function(err) {
				console.log(err)

			})

		}
	}
</script>
<style src='./../../assets/css/user/public.css'></style>
<style src='./../../assets/css/user/mingpian.css'></style>
<style>
	
	body{
		background: whitesmoke;
	}
	#mingpian {
		font-family: "微软雅黑";
	}
	
	.erm {
		width: 100%;
		background: whitesmoke;
	}
	
	.erm img {
		display: block;
		width: 6rem;
		margin: 0rem auto;
		padding-top: 0.5rem;
	}
	
	.erm p {
		font-size: 0.5rem;
		color: #AAAAAA;
		text-align: center;
		padding-bottom: 0.5rem;
	}
	
	.cardbanner {
		display: block;
		width: 100%;
	}
	
	.cardbanner img {
		width: 100%;
	}
	
	.over {
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-line-clamp: 2;
		line-clamp: 2;
		-webkit-box-orient: vertical;
		display: -webkit-box;
	}
	
	.mp_float_rightbd {
		float: right;
		width: 60%;
		text-align: left;
		font-size: 0.65rem;
		color: #838383;
		line-height: 0.9rem;
	}
	
	.mp_center_border {
		border: solid 1px #f7f7;
	}
	
	.mpphone{
		
		
		float: right;
		font-size: 0.65rem;
		color: #838383;
		line-height: 1.6rem;
		
	}
	
	.mp_deon ul{
		font-size: 0px;
	}
	
	.mp_deon > li{
		width: 92%;
		display: inline-block;
		font-size: 0.6rem;
		background: white;
	}
</style>