<template>
	
	<div id="usermsg" class="paddingBox ">
			<!---->

			<div class="pick">
				

				<div class="goodMessage">
					<form>
						
						<div class="tx">
							<span>
							<small  :style="{backgroundImage: 'url(' + msg.avatar + ')' }">
								
								<input @change="upimg" type="file" name="" id="" value="" />
							</small>
							</span>
							
							<p id="tip">点击头像可修改头像</p>
						</div>
						
						<ul>
							
							
							
							<li class="goodMlistumg">
								<small>昵称：</small>
								<span >
									<input  type="text" name="" id="" v-model="msg.name"value="" placeholder="请输入名字" class="leave">
								</span>
							</li>
							
							<!--<li class="goodMlistumg">
								<small>简介：</small>
								<span>
									<input type="text" name="" id="" v-model="msg.brief"value="" placeholder="个人简介" class="leave">
								</span>
							</li>-->
							
							<!--<li class="goodMlistumg">
								<small>生日：</small>
								<span>
									<!--<input type="date" name="" id="" v-model="msg.birthday"value="" placeholder="" class="leave">
										<input type="text" v-model="msg.birthday" name="" id="" value="" class="leave">
								</span>
							</li>-->

							<li class="goodMlistumg">
								<small>性别：</small>
								<span class="xingbie">
									
									
										<b class="layui-icon " :class="{blue:msg.sex==0}" @click="pickxb(0)">&#xe662;男</b>
									    <b class="layui-icon " :class="{red:msg.sex==1}" @click="pickxb(1)">&#xe661;女</b>

								</span>
							</li>
							
							<li class="goodMlistumg">
								<small>联系：</small>
								<span >
									<input   type="text" name="" id="" v-model="msg.mobile" value="" placeholder="请输入手机号或电话" class="leave">
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
		
		name: 'usermsg',

		data() {
			return {
				addsiteshow: false,

            morimg:[],//上传图长度
            imgData: {
					accept: 'image/gif, image/jpeg, image/png, image/jpg',
				}, //图片格式
            infoid:0,
            cityid:0,
            mergername:"请选择",
            districtid:0,
            tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
		
			  
			   msg:{},
				
			province:[],
			city:[],
			district:[],
				
				
				
		
			
			

			}
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
			
			
			upimg: function(e) {
				   
				    var _this=this
					this.morimg = []
					this.morimg.push(e.target.files[0])
					var img = e.target.files[0]
					  var formData = new FormData();
					 
				
						var imgsize = img.size
					var imgstyle = img.type
					
					 
					if(this.imgData.accept.indexOf(imgstyle) == -1) {

						//document.getElementById("tip").innerHTML = "请选择正确的图片格式"
						return false;
					} else if(imgsize > 5242880) {

						//document.getElementById("tip").innerHTML = "图片大小不能超过5M"
						return false;
					}else {
						
						 /*document.getElementById("tip").innerHTML = ""*/
					
					       this.alertn(5)
					        var datimg=img
						
							
						    formData.append('files', datimg); // 文件数据
						    
						    console.log(datimg)
				
							_this.axios({
								method: 'put',
								
								url: '/api/users/0/avatar', 
								
								data:formData
		
							}).then(function(res) {
								
							     	console.log(res.data)
							     	
							     	
							     	_this.msg.avatar=res.data.data.avatar
		                    
								
								   _this.setalert()
									 
							}).catch(function(err) {
								console.log(err)
								 _this.alertn(2,"图片上传失败")
		                         _this.timeout()
								
							})

						   
					}
				
					
						
						
					  
					

				/*	for(var i = 0; i < this.morimg.length; i++) {
						var reader = new FileReader();
						reader.readAsDataURL(this.morimg[i]);
						reader.onload = function(e) {

							_this.msg.imges=e.target.result

							console.log(usermsg.msg.imges)

						}

					}
					
					*/
					
					
					
					
 
				},
			
		
			sitebut: function() {
				//	console.log(2)\
				/*(tel.test(this.phone.trim())==false&&tel2.test(this.phone.trim())==false*/
				/* var tel=(/^1[3|4|5|7|8][0-9]{9}$/|/^(0[1-9]\d{1,2}-)\d{6,7}$/)*/
			     var tel=/^1[3|4|5|7|8|9][0-9]{9}$/
			     var tela=/^0\d{2,3}-?\d{7,8}$/ 
			    var zuiji=/^400([0-9]){1}([0-9]{5})([0-9]){1}$/
			     var phoneval=tela.test(this.phone)
				if(this.msg.name == "" || this.msg.name == "" || this.msg.name.trim().length <= 0|| this.msg.name.trim().length >10) {
					this.alertn(2, "昵称不能为空且不能超过10个字符")
					this.timeout()
					return false;
				} else if(this.msg.mobile.trim().length>0&&tel.test(this.msg.mobile.trim())==false&&tela.test(this.msg.mobile.trim())==false&&zuiji.test(this.msg.mobile.trim()) == false) {
					this.alertn(2, "联系方式格式不正确") 
					this.timeout()
					return false;

				}else{
					console.log(this.msg.mobile)
					
					this.alertn(5)
					
					var _this = this
				
					_this.axios({
						method: 'put',

						url: '/api/users/0', 
						
						data:{
							name:this.msg.name,
							mobile:this.msg.mobile,
							sex:this.msg.sex,
							
						}

					}).then(function(res) {
						_this.alertn(1,"修改成功")
						_this.timeout()
					  
							 
					}).catch(function(err) {
						console.log(err)
						 _this.alertn(2,"修改失败")
                     
                      _this.timeout()
						
					})
					
					
					
					
					
					
					
				}
			},
			
			/*选择性别*/
			pickxb:function  (index) {
				this.msg.sex=index
			},
			
			/*选择区*/
            
           		pickdistrict:function(id,index){
			
				this.district=[]
				this.districtid=0
				this.cityid=index
				var _this = this

			_this.axios({
				method: 'get', 

				//url: '//api/addresses/1234567890000'
					url: '/api/cities?parentId='+id+''

			}).then(function(res) {
				
				
				_this.district=res.data.data

            

			}).catch(function(err) {
				console.log(err)
             
			}) 
				
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

			
					url: '/api/cities?parentId='+id+''

			}).then(function(res) {
				
				
				_this.city=res.data.data
                
               _this.pickdistrict(_this.city[0].id,0)
            

			}).catch(function(err) {
				console.log(err)
             
			}) 
			
			
			
			//this.pickdistrict(this.city[0].id,0)
			 
			/*this.pickdistrict(,0)*/  //更新对应的区
				
			},
            
            /*选择市*/
            
            districtpick:function  (index) {
            	this.districtid=index
            }, 
            
            /*确认地址*/ 
           
           affirm:function  () {
           this.mergername=this.district[this.districtid].mergerName
           	this.msg.postcode=this.district[this.districtid].zipCode
           	this.addsiteshow = !this.addsiteshow
           	
          
           }
          
		},


		components: { 
		
		
			
			alertopen:alert,
		},


		mounted: function() {
       
       var _this=this
       
      
			_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
				url: '/api/users/0'

			}).then(function(res) {

				console.log(res.data)
				_this.msg=res.data.data
				/*console.log(_this.msg)
*/
			}).catch(function(err) {
				console.log(err)

			})
			
			
			/*_this.axios({
				method: 'get',

				//url: '//api/addresses/1234567890000'
					url: '/api/cities?levelType=1'

			}).then(function(res) { 
				console.log(res.data)
				
				_this.province=res.data.data

         
 
			}).catch(function(err) {
				console.log(err)
             
			}) */
		 this.pickdistrict(110100,0)
			 this.pickprovince(110000,0)

		}
		
	

			
		   
       
	
	} 
</script>

<style src='./../../assets/css/usermsg.css'></style>

<style>
	.tx{
			display: block;
				padding: 0.5rem;
				
			}
			
			.tx span{
			    position: relative;
				display: block;
				width: 5rem;
				height: 5rem;
				margin: 0px auto;
				border-radius: 50%;
				overflow: hidden;
				
				background-size: 100%;
			}
			
			.tx span small{
				display: block;
				width: 100%;
				height: 5rem;
				background-position: 50% 50%;
				background-size: cover;
				display: block;
				
				
			}
			
			.tx span small img{
				display: block;
				position: absolute;
				top: 0px;
				left: 0px;
				right: 0px;
				bottom: 0px;
			    margin: auto ;
			    vertical-align:middle;
			
				
				
			}
			
			.tx  p{
				color: #AAAAAA;
				font-size: 0.6rem;
				display: block;
				position: relative;
				text-align: center;
				padding-top: 0.3rem;
			}
			
			.tx span input{
				position: absolute;
				display: block;
				top: 0px;
				left: 0px;
				width: 5rem;
				opacity: 0;
				height: 5rem;
			}
.xingbie b.blue{
	color: cornflowerblue;
}


.xingbie b.red{
	color: red;
}


.goodMessage ul li.goodMlistumg small.blue{
	color: cornflowerblue;
}

.goodMessage ul li.goodMlistumg small.red{
	color: red;
}
</style>