<template>

<div id="addsite" class="paddingBox ">
			<!---->

			<div class="pick">
				<div class="shopSite-title">
					<div class="title">
						添加地址
					</div>
				</div>

				<div class="goodMessage">
					<form>
						<ul>
							<li class="goodMlist">
								<small>收货人：</small>
								<span>
									<input type="text" name="" id="" v-model="msg.name"value="" placeholder="名字" class="leave">
								</span>
							</li>

							<li class="goodMlist">
								<small>联系电话：</small>
								<span>
									<input type="text" name="" id="" v-model="msg.phone" value="" placeholder="手机或电话" class="leave">
								</span>
							</li>

							<li class="goodMlist">
								<small>选择地址：</small>
								<span @click="clickMore" style="color: #333333; margin-left: 0.3rem;">{{msg.city[0].name}} {{msg.city[1].name}} {{msg.city[2].name}}</span>
							</li>
							<li class="goodMlist">
								<small>详细地址：</small>
								<span>
									<input type="text" name="" id=""  v-model="msg.detailed" value="" placeholder="如街道，楼道，门票号等" class="leave">
								</span>
							</li>
							<li class="goodMlist">
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
								<li v-for="(item , index) in province"  :class="{active:infoid==index}" @click="pickprovince(item.id,index)">{{item.name}}</li>
								

							</ul>
						</div>
						
						<div class="item ">
							<ul >
								<li v-for="(item , index) in city"  :class="{active:cityid==index}" @click="pickdistrict(item.id,index)">{{item.name}}</li>
								

							</ul>
						</div>
						
						
						<div class="item districtst">
							<ul>
								<li v-for="(item , index) in district"  :class="{active:districtid==index}" @click="districtpick(index)">{{item.name}}</li>
								

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
	
	  import  alert from './../../components/alert.vue'
	
	export default {
		name: 'addsite',

		data() {

			return {

            name: "",
			phone: "",
			detailed: "",
			postcode: "",
			addsiteshow: false,
			alertopacity: 0,
			Alert: false,
			alertshow: 2,
			alwarning: "",
            
            infoid:0,
            cityid:0,
            districtid:0,
            tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
			
               
			   msg:{
				"name": "",
				"phone": "",
				"detailed": "",
			   "postcode": "",
				"city": [
			  {
			  	"id":0,
			  	"name":"请选择县"
			  },
			  
			  {
			  	"id":0,
			  	"name":"请选择市"
			  },
			  
			  {
			  	"id":0,
			  	"name":"请选择区/县"
			  }], 
			  
			  
			},
			  
			

   
   
   province:[],
	city:[],
	district:[],

			}

		},

		/**/

		components: { 
		
				
				alertopen:alert,
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
			
			 alertn: function(index, tex,type,ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					if (type==true) {
						
					}
				}, 2000)
				
				

			},
			
			/*选择市*/
			
			pickprovince:function(id,index){

				this.infoid=index
				this.city=[]
				this.district=[]
				this.cityid=0
				this.districtid=0
				
				var _this = this

			_this.axios({
				method: 'get',

			
					url: '/api/cityAreas/parent/'+id+''

			}).then(function(res) {
				
				
				_this.city=res.data
                
                _this.pickdistrict(_this.city[0].id,0)
            

			}).catch(function(err) {
				console.log(err)
             
			}) 
			
			
			
			//this.pickdistrict(this.city[0].id,0)
			 
			/*this.pickdistrict(,0)*/  //更新对应的区
				
			},
			
			
			/*选择区*/
			
			pickdistrict:function(id,index){
				
				this.districtid=0
				this.cityid=index
				var _this = this

			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
					url: '/api/cityAreas/parent/'+id+''

			}).then(function(res) {
				
				
				_this.district=res.data

            

			}).catch(function(err) {
				console.log(err)
             
			}) 
				
			},
			
			
			/*地址颜色*/
			
			 
            districtpick:function  (index) {
            	this.districtid=index
            },
			
			
				sitebut: function() {
				//	console.log(2)\
				/*(tel.test(this.phone.trim())==false&&tel2.test(this.phone.trim())==false*/
				/* var tel=(/^1[3|4|5|7|8][0-9]{9}$/|/^(0[1-9]\d{1,2}-)\d{6,7}$/)*/
			     var tel=/^1[3|4|5|7|8][0-9]{9}$/
			     var tela=/^0\d{2,3}-?\d{7,8}$/ 
			     var youbian=/^[1-9][0-9]{5}$/
			     
				if(this.msg.name == "" || this.msg.name == "" || this.msg.name.trim().length <= 0) {
					this.alertn(2, "姓名不能为空")
					return false;
				} else if(this.msg.phone == "" || this.msg.phone == "" || this.msg.phone.trim().length <= 0) {
					this.alertn(2, "手机号不能为空")
					return false;

				} else if(tel.test(this.msg.phone.trim())==false&&tela.test(this.msg.phone.trim())==false) {
					this.alertn(2, "手机号格式不正确") 
					return false;

				} else if( 	this.msg.city[0].name=="请选择省"|| this.msg.city[1].name=="请选择市"||this.msg.city[2].name=="请选择区/县") {
					this.alertn(2, "请选择地址")
					return false;

				} else if(this.msg.detailed == "" || this.msg.detailed == "" || this.msg.detailed.trim().length <= 0) {
					this.alertn(2, "详细地址不能为空")
					return false;

				} else if(this.msg.postcode == "" || this.msg.postcode == "" || this.msg.postcode.trim().length <= 0) {
					this.alertn(2, "邮政编码不能为空")
					return false;

				}else if(youbian.test(this.msg.postcode.trim())==false) {
					this.alertn(2, "邮政编码格式不正确")
					return false;

				}else{
					console.log(this.msg)
				}
			},
			
		
            
         
           
            
            /*确认地址*/
           
           affirm:function  () {
            this.msg.city[0].name=this.province[this.infoid].name
           	this.msg.city[1].name=this.city[this.cityid].name
           	this.msg.city[2].name=this.district[this.districtid].name
           	this.msg.city[0].id=this.province[this.infoid].id
           	this.msg.city[1].id=this.city[this.cityid].id
           	this.msg.city[2].id=this.district[this.districtid].id
           	
           	this.addsiteshow = !this.addsiteshow
           }

		},
		mounted: function() {
			var _this = this

			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
					url: '/api/cityAreas/level/1'

			}).then(function(res) {
				console.log(res.data)
				 console.log(11)
				_this.province=res.data

            console.log(_this.province)

			}).catch(function(err) {
				console.log(err)
               console.log("00")
			}) ,
			 this.pickdistrict(110100,0)
			this.pickprovince(110000,0)
		   
		
		}
		
		
		/**/

	}
</script>
<style src='./../../assets/css/site.css'></style>
<style>
</style>