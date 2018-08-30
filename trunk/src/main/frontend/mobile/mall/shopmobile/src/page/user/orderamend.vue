<template>

	<div id="addsite" class="paddingBox ">
		<!---->

		<div class="pick">
			<div class="shopSite-title">
				<div class="title">
					修改地址
				</div>
			</div>

			<div class="goodMessage">
				<form>
					<ul>
						<li class="goodMlisite">
							<small>收货人：</small>
							<span>
									<input type="text" name="" id="" v-model="msg.name"value="" placeholder="名字" class="leave">
								</span>
						</li>

						<li class="goodMlisite">
							<small>联系方式：</small>
							<span>
									<input type="text" name="" id="" v-model="msg.phone" value="" placeholder="手机或电话" class="leave">
								</span>
						</li>

						<li class="goodMlisite">
							<small>选择地址：</small>
							<span @click="clickMore">{{mergername}}</span>
						</li>
						<li class="goodMlisite">
							<small>详细地址：</small>
							<span>
									<input type="text" name="" id=""  v-model="msg.detailed" value="" placeholder="如街道，楼道，门票号等" class="leave">
								</span>
						</li>
						<li class="goodMlisite">
							<small>邮政编码：</small>
							<span>
									<input type="text" name="" id=""  v-model="msg.postcode" value="" placeholder="邮政编码" class="leave">
								</span>
						</li>

					</ul>

					<div class="sietbut" @click="sitebut">
						确认
					</div>

				</form>
			</div>

		</div>

		<!--地址-s-->
		<div class="addsite" v-show="addsiteshow==true">
			<div class="addsiteNav">
				<div class="addsiteNav-title">
					选择地址
					<div class="but">
						<span @click="affirm">确认</span><small @click="clickMore">取消</small>
					</div>
				</div>

				<!--<div class="city-pick">
						<div class="item">
							<ul>
								<li v-for="(item , index) in info"  :class="{active:infoid==index}" @click="infopick(index)">{{item.name}}</li>
								

							</ul>
						</div>

						<div class="item">
							<ul>
								<li  v-for="(item , index) in info[infoid].city" :class="{active:cityid==index}" @click="citypick(index)">{{item.name}}</li>
								

							</ul>
						</div>

						<div class="item">
							<ul>
									<li  v-for="(item , index) in info[infoid].city[cityid].district" :class="{active:districtid==index}" @click="districtpick(index)">{{item.name}}</li>

							</ul>
							
						
							
						</div>
					</div>-->

				<div class="city-pick">
					<div class="item">
						<ul>
							<li v-for="(item , index) in province" :class="{active:infoid==index}" @click="pickprovince(item.id,index)">{{item.name}}</li>

						</ul>
					</div>

					<div class="item ">
						<ul>
							<li v-for="(item , index) in city" :class="{active:cityid==index}" @click="pickdistrict(item.id,index)">{{item.name}}</li>

						</ul>
					</div>

					<div class="item districtst">
						<ul>
							<li v-for="(item , index) in district" :class="{active:districtid==index}" @click="districtpick(index)">{{item.name}}</li>

						</ul>
					</div>

					<!--	<div class="item">
							<ul>
								<li  v-for="(item , index) in info[infoid].city" :class="{active:cityid==index}" @click="citypick(index)">{{item.name}}</li>
								

							</ul>
						</div>

						<div class="item">
							<ul>
									<li  v-for="(item , index) in info[infoid].city[cityid].district" :class="{active:districtid==index}" @click="districtpick(index)">{{item.name}}</li>

							</ul>
							
						
							
						</div>-->
				</div>

			</div>
		</div>

		<!--提示-->
		<!--alert-s-->

		<alertopen :text=tip></alertopen>
		<!--alert-end-->

	</div>

</template>

<script>
	import alert from './../../components/alert.vue'

	export default {
		name: 'addsite',

		data() {

			return {
				uid: '', //地址id
				name: "",
				phone: "",
				detailed: "",
				postcode: "",
				addsiteshow: false,
				alertopacity: 0,
				Alert: false,
				alertshow: 2,
				alwarning: "",
				mergername: "请选择",
				infoid: 0,
				cityid: 0,
				districtid: 0,
				defaulted: false,
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},

				axdata: {},
				msg: {
					"name": "",
					"phone": "",
					"detailed": "",
					"postcode": "",
					"city": [{
							"id": 0,
							"name": "请选择县"
						},

						{
							"id": 0,
							"name": "请选择市"
						},

						{
							"id": 0,
							"name": "请选择区/县"
						}
					],

				},

				province: [],
				city: [],
				district: [],

				cityCache: {}, //市缓存
				districtCache: {}, //县缓存

			}

		},

		/**/

		components: {

			alertopen: alert,
		},

		methods: {
			siteCk: function(index) {
				if(this.siteList[index].checked == false) {
					for(var i = 0; i < this.siteList.length; i++) {

						this.siteList[i].checked = false

					}
					this.siteList[index].checked = true
				}
			},

			clickMore: function() {
				this.addsiteshow = !this.addsiteshow

			},

			allTikenone: function() {
				this.alltalshow = !this.alltalshow
				this.Allleft = 30
			},

			alertn: function(index, tex, type, ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
				this.tip.alwarning = tex

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert = false
					if(type == true) {

					}
				}, 2000)

			},

			/*选择市*/

			pickprovince: function(id, index) {
				/*_this.city=res.data.data*/

				this.infoid = index
				this.city = []
				this.district = []
				this.cityid = 0
				this.districtid = 0

				var _this = this
				var citycache = this.cityCache
				var cityshow;
				cityshow = citycache[id]

				if(!cityshow) {
					_this.axios({
						method: 'get',
						url: '/api/cities?parentId=' + id + ''

					}).then(function(res) {

						cityshow = res.data.data
						_this.cityCache[id] = cityshow
						_this.city = _this.cityCache[id]
						_this.pickdistrict(_this.city[0].id, 0)

					}).catch(function(err) {
						console.log(err)

					})

				} else {
					this.city = cityshow
					this.pickdistrict(this.city[0].id, 0)
				}

				/*	_this.city = _this.cityCache[id]*/

				/*_this.pickdistrict(_this.city[0].id, 0)*/

				//this.pickdistrict(this.city[0].id,0)

			},

			/*选择区*/

			pickdistrict: function(id, index) {

				this.district = []
				this.districtid = 0
				this.cityid = index
				var _this = this

				var districtcache = this.districtCache
				var districtshow;

				districtshow = districtcache[id]

				if(!districtshow) {
					_this.axios({
						method: 'get',

						//url: '//api/addresses/1234567890000'
						url: '/api/cities?parentId=' + id + ''

					}).then(function(res) {

						districtshow = res.data.data
						districtcache[id] = districtshow
						_this.district = districtshow

					}).catch(function(err) {
						console.log(err)

					})

				} else {
					this.district = districtshow
				}

			},

			/*地址颜色*/

			districtpick: function(index) {
				this.districtid = index

			},

			sitebut: function() {

				//	console.log(2)\
				/*(tel.test(this.phone.trim())==false&&tel2.test(this.phone.trim())==false*/
				/* var tel=(/^1[3|4|5|7|8][0-9]{9}$/|/^(0[1-9]\d{1,2}-)\d{6,7}$/)*/
				var tel = /^1[3|4|5|7|8|9][0-9]{9}$/
				var tela = /^0\d{2,3}-?\d{7,8}$/
				var youbian = /^[1-9][0-9]{5}$/
				 var zuiji=/^400([0-9]){1}([0-9]{5})([0-9]){1}$/

				if(this.msg.name == "" || this.msg.name == "" || this.msg.name.trim().length <= 0) {
					this.alertn(2, "姓名不能为空")
					return false;
				} else if(this.msg.phone == "" || this.msg.phone == "" || this.msg.phone.trim().length <= 0) {
					this.alertn(2, "联系方式不能为空")
					return false;

				} else if(tel.test(this.msg.phone.trim()) == false && tela.test(this.msg.phone.trim()) == false&&zuiji.test(this.msg.phone.trim()) == false) {
					this.alertn(2, "联系方式格式不正确")
					return false;

				} else if(this.mergername == "请选择") {
					this.alertn(2, "请选择地址")
					return false;

				} else if(this.msg.detailed == "" || this.msg.detailed == "" || this.msg.detailed.trim().length <= 0) {
					this.alertn(2, "详细地址不能为空")
					return false;

				} else if(this.msg.detailed.trim().length > 50) {
					this.alertn(2, "详细地址字数不能超过50个")
					return false;

				} else {
					/*console.log(this.district[this.districtid])
					console.log(this.district[this.districtid].id)*/

					this.alertn(5)

					var _this = this

					_this.axios({
						method: 'put',

						url: '/api/addresses/' + this.uid + '',

						data: {

							"receiver": this.msg.name,
							"receiverPhone": this.msg.phone,
							"receiverAddress": this.mergername + "-" + this.msg.detailed,
							"cityAreaId": this.district[this.districtid].id,
							"defaulted": false,

							/* "receiver":this.msg.name, 
							  "receiverPhone": this.msg.phone,
							  "receiverAddress": this.mergername,
							  "cityAreaId":this.district[this.districtid].id,*/

							/*receiver:this.msg.name,
							receiverPhone:this.msg.phone,
							receiverAddress:this.mergername+this.detailed,
							cityAreaId:this.district[this.districtid].id,
							defaulted:0,
							status:0,*/

						}
					}).then(function(res) {
						console.log(res.data)

						_this.alertn(1, "修改成功")

						setTimeout(() => {
						_this.$router.push("/user/ordersite")
							_this.tip.alertopacity = 0
							_this.tip.alwarning = ""
							_this.tip.alertshow = 0
							_this.tip.Alert = false

							_this.typeind = 0
						}, 2000)

					}).catch(function(err) {

						console.log(err)
						_this.alertn(2, "错误")
						setTimeout(() => {
							_this.tip.alertopacity = 0
							_this.tip.alwarning = ""
							_this.tip.alertshow = 0
							_this.tip.Alert = false

						}, 2000)

					})

				}

				if(this.defaulted == true) {

					_this.axios({
						method: 'put',
						url: '/api/users/0/address/' + this.uid + '/defaulted',

					}).then(function(res) {
						console.log(res.data)

						

					}).catch(function(err) {
						console.log(err)

					})

				}

			},

			/*确认地址*/

			affirm: function() {
				this.mergername = this.district[this.districtid].mergerName
				this.msg.postcode = this.district[this.districtid].zipCode
				this.addsiteshow = !this.addsiteshow
			},

			axdataget: function(city) {

				/* 	var uid=this.$route.params.siteid*/

				var _this = this
				_this.axios({
					method: 'get',

					//url: '//api/addresses/1234567890000'
					url: '/api/cities/' + city + ''

				}).then(function(res) {

					/*	console.log(res.data)*/

					/*_this.mergername = res.data.mergerName*/
					_this.msg.postcode = res.data.zipCode

				}).catch(function(err) {
					console.log(err)

				})
			}

		},
		mounted: function() {

			var uid = this.$route.params.siteid

			var _this = this

			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
				url: '/api/addresses/' + uid + ''

			}).then(function(res) {
				console.log(res.data)
				/*_this.axdataget(res.data.cityAreaId)*/
				_this.mergername=res.data.receiverAddress.split('-')[0]
				_this.defaulted = res.data.defaulted
				_this.msg.name = res.data.receiver
				_this.msg.phone = res.data.receiverPhone
				_this.msg.detailed = res.data.receiverAddress.split('-')[1]
				_this.uid = res.data.id

				//_this.province=res.data.data

			}).catch(function(err) {
				console.log(err)

			})

			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
				url: '/api/cities?levelType=1'

			}).then(function(res) {
				//	console.log(res.data)

				_this.province = res.data.data

			}).catch(function(err) {
				console.log(err)

			})
			this.pickdistrict(110100, 0)
			this.pickprovince(110000, 0)

		}

	}
</script>
<style src='./../../assets/css/site.css'></style>
<style>

</style>