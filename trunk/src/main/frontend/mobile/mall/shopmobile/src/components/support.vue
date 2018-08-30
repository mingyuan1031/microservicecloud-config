<template>
	<div id="support">
		
		<div class="floorBox">
			<div style="margin: 0px auto; width: auto; text-align: center;">
				<ul>
				<li>
					<router-link to="/">
					店铺首页<span>|</span>
					</router-link >
				</li>

				<li>
					<router-link to="/user">
					会员中心<span>|</span>
					</router-link>
				</li>

				<li @click="attention">
					<a>关注我们<span>|</span></a>
				</li>

				<li>
					<router-link to="/share">
					<a>快享<span>|</span></a>
					</router-link>
				</li>

				<li>
					<router-link to="/card">
					店铺名片<span>|</span>
					</router-link>
				</li>

			</ul>
			</div>
			

			<div class="melogo">
				<div class="melogo-img">
					<img src="../assets/img/melogo.png" />
				</div>
				<p>技术支持：老屋新房</p>
			</div>

		</div>

		<div class="attention" v-show="attentionshow==true">
			<div class="topnone">
				<span @click="attentionnone"><i class="layui-icon ">&#x1006; </i></span>
			</div>

			<div class="attentioncenter">
				<div class="attentioncenter_img">
					<img :src="msg.img" />
				</div>

				<div class="attentioncenter_tip">
					<h3>长按图片【识别二维码】关注公众号</h3>
					<h2>无法识别二维码</h2>
					<ul>
						<li>1，打开微信，点击‘添加朋友’</li>
						<li>2，点击公众号</li>
						<li>3，搜索公众号：{{msg.name}}</li>
						<li>4，点击‘关注’</li>
						<li>5，完成</li>
					</ul>

				</div>

			</div>

		</div>

	</div>

</template>

<script>
	export default {

		name: 'support',
		

		data() {
			return {
				msg:{
				"img":  require('../assets/img/mingpian_imgs/857522379732117614.jpg'),
				"name":"老屋新房",
				 
				},
			
		        
				attentionshow: false,
			}
		}, 

		methods: {
			attention: function() {
				this.attentionshow = !this.attentionshow
			},
			attentionnone: function() {
				this.attentionshow = !this.attentionshow
			},

		},

		mounted: function() {
         
		}
	}
</script>


<style>

</style>