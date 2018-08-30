<style>
	@import url("./layout.css");
</style>
<template>
	<div class="layout">
		<Layout>
			<Header :style="{position: 'fixed', width: '100%',top:'2px'}" class="heas">
				<Menu mode="horizontal" theme="light" active-name="1">
					<div class="layout-nav">
						<div style="display: inline-block;">
							<img :src="headimg"   style="height: 60px;border-radius: 50%;"/>
						</div>	
						    <Submenu name="1">
							<template slot="title">
							<Icon type="stats-bars"></Icon>
								个人中心
							</template>
							<MenuItem name="3-1">
							<router-link to="/userinformation" class="liststyle">个人中心</router-link>
							</MenuItem>
							<MenuItem name="3-2">退出登录</MenuItem>
						</Submenu>								
					</div>
				</Menu>
			</Header>
			
			<div style="height: 70px;"></div>
			<Content :style="{margin: '0', background: '#fff'}">
				<!--侧边开始-->
				<div class="sidee">
					<Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto',top:'0px'}" class="hse">
						<div><img src="../../common/images/adminlogo.png" class="logo" /></div>
						<Menu theme="dark" width="auto" @on-select="cookis" @on-open-change="cookx" :active-name="cookes" :open-names="cooke">
							<Submenu name="1">
								<template slot="title">
									<Icon type="person-stalker"></Icon>
									店员管理
								</template>
								<MenuItem name="1-1">
								<router-link to="/administratorlist" class="routers">店员管理</router-link>
								</MenuItem>
							</Submenu>
								<Submenu name="2">
								<template slot="title">
									<Icon type="paper-airplane"></Icon>
									预约管理
								</template>
								<MenuItem name="2-1">
								<router-link to="/reservations" class="routers">预约单管理</router-link>
								</MenuItem>
							</Submenu>
							<Submenu name="3">
								<template slot="title">
									<Icon type="ios-navigate"></Icon>
									商品管理
								</template>
								<MenuItem name="3-1">
								<router-link to="/goodslist" class="routers">商品列表</router-link>
								</MenuItem>
								<MenuItem name="3-2">
								<router-link to="/goodssort" class="routers">商品分类</router-link>
								</MenuItem>
							
								<MenuItem name="3-3">
								<router-link to="/productspecification" class="routers">商品标签</router-link>
								</MenuItem>
								<MenuItem name="3-4">
								<router-link to="/brandlist" class="routers">品牌列表</router-link>
								</MenuItem>
								
								<MenuItem name="3-5">
								<router-link to="/commentlist" class="routers">评论列表</router-link>
								</MenuItem>
							
							</Submenu>
							<Submenu name="4">
								<template slot="title">
									<Icon type="document"></Icon>
									订单管理
								</template>
								<MenuItem name="4-1">
								<router-link to="/orderlist" class="routers">订单列表</router-link>
								</MenuItem>
								<MenuItem name="4-2">
								<router-link to="/invoice" class="routers">发货单</router-link>
								</MenuItem>
								<!--<MenuItem name="2-5">
								<router-link to="/addorder" class="routers">添加订单</router-link>
								</MenuItem>-->
								<!--<MenuItem name="2-6">
								<router-link to="/orderlog" class="routers">订单日志</router-link>
								</MenuItem>-->
							</Submenu>
							<Submenu name="5">
								<template slot="title">
									<Icon type="person"></Icon>
									会员管理
								</template>
								<MenuItem name="5-1">
								<router-link to="/memberlist" class="routers">会员管理</router-link>
								</MenuItem>
							</Submenu>
							<Submenu name="6">
								<template slot="title">
									<Icon type="social-usd"></Icon>
									商城管理
								</template>
								<MenuItem name="6-1">
								<router-link to="/mallinformation" class="routers">商城信息</router-link>
								</MenuItem>
								<MenuItem name="6-2">
								<router-link to="/mallmenu" class="routers">前台菜单</router-link>
								</MenuItem>
							</Submenu>
							<Submenu name="7">
								<template slot="title">
									<Icon type="share"></Icon>
									快享管理
								</template>
								<MenuItem name="7-1">
								<router-link to="/quickshare" class="routers">帖子管理</router-link>
								</MenuItem>
							</Submenu>
							<Submenu name="8">
								<template slot="title">
									<Icon type="ios-chatboxes-outline"></Icon>
									 企业动态
								</template>
								<MenuItem name="7-1">
								<router-link to="/newdata" class="routers">企业动态管理</router-link>
								</MenuItem>
							</Submenu>
							<!--<Submenu name="7">
								<template slot="title">
									<Icon type="pricetag"></Icon>
									统计管理
								</template>
								<MenuItem name="7-1">
								<router-link to="/layout/membersrank" class="routers">会员统计</router-link>
								</MenuItem>
								<MenuItem name="7-2">
								<router-link to="/layout/saledetails" class="routers">销售明细</router-link>
								</MenuItem>
								<MenuItem name="7-3">
								<router-link to="/layout/salesituation" class="routers">商品销售</router-link>
								</MenuItem>
							</Submenu>-->
							
						
						</Menu>
					</Sider>
				</div>
				<!--侧边1结束-->
				<div class="contains">
					<router-view></router-view>
				</div>
			</Content>
			<Footer class="layout-footer-center">2011-2018 &copy; 河南老屋新房网络科技有限公司</Footer>
		</Layout>
	</div>
</template>
<script>
	
	//菜单刷新H5本地存储
	var data2 = JSON.parse(localStorage.getItem('b'));
	export default {
		data() {
			return {
				cookes: '',
				cooke: data2,
				headimg:''
			}
		},
		methods: {
			cookis(name) {
				const info = name
				localStorage.setItem('a', JSON.stringify(info));
			},
			cookx(demo) {
				const infos = demo
				localStorage.setItem('b', JSON.stringify(infos));
			},
			upcookis() {
				var data1 = JSON.parse(localStorage.getItem('a'));
				this.cookes = data1;
			},
			/*请求加载个人信息*/
			resqustuser(){
				var that=this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/users/0'
				}).then(function(res) {
					 that.headimg=res.data.data.avatar
				})
			},
		},
		mounted: function() {
			this.upcookis();
			this.resqustuser();
		}
	}
</script>