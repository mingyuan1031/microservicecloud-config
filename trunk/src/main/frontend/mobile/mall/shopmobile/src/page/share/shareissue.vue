<template>

	<div id="take">

		<div class="take">
			<form >

				<textarea placeholder="写些什么吧" @keyup="numsiez" v-model="msg.center"></textarea>
				<small class="num">{{msg.center.trim().length}}/{{maxnumsize}}</small>

				<!--<div class="uliketype">
                    		<p>选择满意度</p>
							<span :class="{active:msg.liketype==0}" @click="liketab(0)">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span :class="{active:msg.liketype==1}" @click="liketab(1)">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span :class="{active:msg.liketype==2}" @click="liketab(2)">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

							</div>-->

				<ul id="imgid">

					<p>点击下面按钮选择图片(图片最多只能上传6张)<small id="tip"></small></p>

					<li v-for="(item,index) in msg.imgUrls">
						<span class="moreimg">
	    				<img :src="item.path"/>
	    			    <small @click="removeimg(index)">X</small>
	    			</span>

					</li>

					<li>
						<span class="moreimg"></span>
						<input @change="upimg" type="file" multiple="multiple" name="" id="" value="" />
					</li>

				</ul>

				<ul></ul>

				<span @click="sub" class="take-but">
						提交
					</span>

			</form>
		</div>


    <!--alert-s-->
			
				<alertopen :text=tip></alertopen>
			<!--alert-end-->

	</div>

</template>

<script>
	
	import  alert from './../../components/alert.vue'
	
	export default {

		name: 'take',

		data() {
			return {
				maxnumsize: 200, //最大字符个数
				imgUrls: [],
				morimg: [],
				imgData: {
					accept: 'image/gif, image/jpeg, image/png, image/jpg',
				},
tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
				msg: {
					"center": "",

					"imgUrls": [],
				},
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
			
			
			
			upimg: function(e) {
              
				var _this = this
                
				this.morimg = []
				this.morimg = e.target.files
				/*	console.log(this.morimg)*/
			/*	var imgfiles = new FormData(e.target.files[0]	)*/
			     var imgfiles=e.target.files[0]
				var imgdata = e.target.files[0]

				var imgsize = imgdata.size
				/*
				for (var i=0 ; i<imgdata.length; i++) {
					var imgsize = imgdata[i].size
					var imgstyle = imgdata[i].type
					
					
					if(this.imgData.accept.indexOf(imgstyle) == -1) {

					document.getElementById("tip").innerHTML = "请选择正确的图片格式"
					 _this.setalert()
					return false;
				} else if(imgsize > 5242880) {

					document.getElementById("tip").innerHTML = "图片大小不能超过5M"
					 _this.setalert()
					return false;
				} else if(this.msg.imgUrls.length+imgdata.length> 5) {

					document.getElementById("tip").innerHTML = "图片数量不能超过6张"
					  _this.setalert()
					return false;
				} 
				} */
				 
				 var formData = new FormData();
				  formData.append('files', e.target.files[0]); 
				/*for (var i=0 ; i<imgdata.length; i++) {
					var datimg=imgdata[i]
					console.log(datimg)
					
				      formData.append('FileList', datimg); // 文件数据
				}*/
				
					document.getElementById("tip").innerHTML = ""
					console.log(e.target.files[0])
					
					
					 
                     this.alertn(5)
					
					var _this = this
					
				
					_this.axios({
						method: 'post',
						
						url: '/api/microblogs/files',
                        
						data: formData

					}).then(function(res) {
						 var igdatas=res.data.data
						
                      
                      for(var i=0; i<igdatas.length;i++){
                      	_this.msg.imgUrls.push(igdatas[i])
                      }
console.log(res.data)

					
						 
						  _this.setalert()
							 
					}).catch(function(err) {
						console.log(err)
						 _this.alertn(2,"图片上传失败")
                         _this.timeout()
						
					})

				

		

		},

		/*清除图片*/

		removeimg: function(index) {
			
			this.msg.imgUrls.splice(index,1)
			console.log(	this.msg.imgUrls)
		/*	var imgid=this.msg.imgUrls[index].id
			console.log(imgid)
			 var _this = this
						
				_this.axios({
				method: 'delete',
				headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    
		    	},
				url: '/api/microblogs/files/'+imgid+'',
				
				
			}).then(function(res) {
				console.log(res.data)
                _this.msg.imgUrls=res.data.data
			}).catch(function(err) {
				console.log(err)
				
				

			})*/
		},

		/*字符长度*/

		numsiez: function() {

			if(this.msg.center.length >= this.maxnumsize) {

				this.msg.center = this.msg.center.substr(0, this.maxnumsize)
			}
		},

		/*提交*/

		sub: function() {
			if(this.msg.center.trim().length < 5) {
				document.getElementById("tip").innerHTML = "评论字数不能少于5个字";
				return false;
			} else {
				//console.log(this.msg)
				document.getElementById("tip").innerHTML = ""

				var _this = this

				var nav = this.msg.center
				var imgur=[];
				
				
				for (var i=0;i<this.msg.imgUrls.length;i++) {
					
					imgur.push("imageId="+this.msg.imgUrls[i].id)
					

				}
				
				console.log(imgur)
				 var imagrs=imgur.join("&")
				_this.axios({
					method: 'post',
					url: '/api/microblogs/?'+imagrs+'',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					data: {
						content: nav,
                       
					}

				}).then(function(res) {
					console.log(res.data)
					_this.msg.center=""
                    _this.msg.imgUrls=[]
				}).catch(function(err) {
					console.log(err)
					/* _this.alertn(2,"点赞未成功")
                 _this.timeout()*/
				})

			}
		}
	},
 components: { 
	alertopen:alert,
		},
	mounted: function() {

	}
	}
</script>

<style>
	.uliketype {
		width: 100%;
		display: inline-block;
	}
	
	.uliketype span {
		display: block;
		width: 2rem;
		text-align: center;
		float: left;
		color: #AAAAAA;
		margin: 0.8rem 0.5rem;
	}
	
	.uliketype span i {
		font-size: 1.2rem;
	}
	
	.uliketype span small {
		display: block;
		width: 100%;
		font-size: 0.6rem;
	}
	
	.uliketype p {
		font-size: 0.6rem;
		color: #AAAAAA;
		padding-left: 0.5rem;
	}
	
	.uliketype span.active {
		color: orangered;
	}
	
	.take-but{
		text-align: center;
	}
</style>

<style src='./../../assets/css/shareissue.css'></style>