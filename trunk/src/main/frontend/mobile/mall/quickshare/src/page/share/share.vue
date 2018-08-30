<template>
	<div id="share" style="background: #EEEEEE;" class="paddingBox">

		<!--轮播图-s-->
		<div>

			<banner :text=bannerList></banner>
		</div>
		<!--轮播图-e-->
		<!--用户评论-->
		<div class="goodUsershare">
			<!-- {{datas}}-->
			<div>
				<!--全部-->
				<div>
					<!--datas-->

					<div class="tiezinone" v-show="datas.length==0">
						当前还没有帖子发表
					</div>

					<div v-for="(item,index) in datas" class="userTalk">

						<div class="userMeg">
							<div class="userMeg-img" :style="{backgroundImage: 'url(' + item.creator.avatar + ')' }">
								
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}<span>{{item.created}}</span></p>
								
							</div>
							<!--<div class="userMeg-evaluate">
									<span v-show="item.attention==false" @click="attention(index)"><small>关注</small></span>
                                    <span v-show="item.attention==true"><small>已关注</small></span>
								</div>-->

						</div>

						<div class="userTalk-content">
							<!--	<router-link to="/share/sharedetails">-->
								<!--<router-link :to="{name:'sharedetails',params:{shareid:item.id}}">-->
							<p >
								{{item.content}}
							</p>
								<!--</router-link>-->
							<div class="take-imgs" v-show="datas[index].files.length>0">

								<ul class="two" >
									<!-- -->
									<li v-for="(itemdb,indexdb) in datas[index].files" >
                                     <!--  <div :style="{backgroundImage: 'url(' + itemdb.path + ')'}" class="imgcenter">-->
                                     	<div class="imgcenter" :style="{backgroundImage: 'url(' + itemdb.path + ')' }" @click="Imgsrc(index,indexdb)" style=" background-position: 50% 50%;
	    background-size: cover;">
                                           
                                       </div>
										
									</li>

								</ul>
							</div>

							<div class="take-share">
								<div class="takeShare_center">

									

									<div class="item" @click="needreply(index)" style="margin-left: 0.3rem;">
						           {{item.comments.length}}<i><img src="../../assets/img/take.png"/></i>
									</div>

									<div class="item " v-show="datas[index].whether==false" @click="praise(index)">
											 {{item.praises.length}} <i><img src="../../assets/img/likeactive.png"/></i>
									</div>

									<div class="item active" v-show="datas[index].whether==true" @click="delpraise(index)">
										{{item.praises.length}}  <i class="layui-icon"><img src="../../assets/img/like.png"/></i> 
									</div>
								</div>

							</div>

							
							<div class="praiseitem" v-show="datas[index].praises.length>0">
								
							<ul class="praiseitemul">
									<span> <i><img style="width: 0.9rem; height: 0.9rem; margin-right: 0.2rem;" src="../../assets/img/kong.png"/></i> </span>
									<li v-for="(iteme,indexdb) in datas[index].praises">
										<div  class="parnav" :style="{backgroundImage: 'url(' + iteme.avatar + ')' }" @click="nameshow">
                                           
                                       </div>
                                       
                                         <div class="parname">
                                         {{iteme.name}}
                                       </div>
                                       
                                       
                                       
                                       
									</li>
								

								</ul>
								</div>
								
							

							

							<!--点赞人end-->

							<div class="share_reply" :class="{disnone:datas[index].comments.length>6}" v-show="datas[index].comments.length>0">
								<div class="reply_itme">
									<!--<div class="reply_time">{{item.created}}</div> 发帖时间-->
									<div class="reply_reply">
										<div class="reply_id" v-for="(item , indexTwo) in datas[index].comments">
											<div class="share-title" @click="replyandreply(index ,indexTwo)" style="font-size: 0.65rem;">
												<span style="color: #0b83c1; ">{{item.creator.name}}</span>
												<small v-show='!item.parentId==false'>回复</small>
												<span v-show='!item.parentId==false' style="color: orangered;">{{item.parentCreator.name}}</span>
												<small style="margin-right: 0.3rem; color: #0b83c1; ">:</small>{{item.content}}
											</div>

											
										</div>

									</div>
								</div>
								<!--遮罩-->
								<div class="moreblock">
									<div class="opend"></div>
									<p @click="parentB">点击查看更多评论</p>

								</div>

								<div class="morenone">

									<p @click="parentN">收起全部评论</p>

								</div>

							</div>
							<!--回复-end-->

						</div>

					</div>

				</div>
				<!--全部-end-->

			</div>

			<!--图片放大效果-->
			<!--:class="{imgshow:imgshow==true}"-->
		<!--	<div class="imgMagnify" :class="{open_block:alertopen==true}" @click="imgNone"> 
				
				<div class="imgNav">
				
					<div class="swiper-container snot">
						<div class="swiper-wrapper" id="tran">
							<div class="swiper-slide" id="bannerid" v-for="(item,index) in caselist"  >
								<img   style="width: 100% ;" :src="item.path" >
							</div>
						</div>
					</div> 

				</div>
			</div>	-->
		
		
		   <div class="imgMagnify" :class="{open_block:alertopen==true}" @click="imgNone"> 
				
				<div class="imgNav">
				
				     <img :src="caselist"/>
					 
				</div>
			</div>
				
				
				
			

			<!--回复框-s-->
			<div class="reply_form" v-show="srkshow==true">
				<div class="reply_bg" @click="srkhide"></div>
				<div class="reply_input">
					<input type="text" name="" id="" value="" v-model="srkcenter" :placeholder="srkname">

					<span class="but" @click="publishone" v-show="butindex==1">发表</span>
					<span class="but" @click="publishtwo" v-show="butindex==0">发表</span>
				</div>
			</div>

			<!--回复框-end-->

			<div class="kuaijie">
			
					<img  @click="shareissueblock" src="../../assets/img/share/sadffsa.png" />
				
			</div </div>

		</div>

		<div class="nonemore" v-show="isnone==true">
			<span>没有更多了</span>
		</div>

		<!--alert-s-->

		<alertopen :text=tip></alertopen>
		<!--alert-end-->
		
		
		<!--发帖页面-->
		<div>
			<div >

		<div class="take" :style="{'left':Allleft+'rem'}" id="opquick" style="display: none;">
			<div class="top_left">
         	<i class="layui-icon layui-icon-face-smile" @click="black">&#xe603;</i>
         		发表快享
         	</div>
			<form >

				<textarea placeholder="写些什么吧" @keyup="numsiez" v-model="msg.center"></textarea>
				<small class="num">{{msg.center.trim().length}}/{{maxnumsize}}</small>

				

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
                <div >
                <span @click="sub" class="take-but">
						提交
				</span>
				
				{{localIds}}
				
                </div>
				

			</form>
		</div>


    

	</div>
		</div>
		
		<!--发帖页面-end-->

	</div>
</template>

<script>
	import Swiper from 'swiper'
	import carousel from './../../components/carousel.vue'
	import alert from './../../components/alert.vue'
  

	/* var quickshareCache = {
	 	usersCache:{},
	 	shareDataCache:{}
	 }*/
	// window.quickshareCache=quickshareCache;
	export default {

		name: 'share',

		data() {
			
			return {
               localIds:"",//微信选择图片信息
				caselist:"",
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				Allleft:20,
				usersCache: {},
				shareDataCache: {}, //
				datas: [], //数据结构
				blogid: "", //帖子id
				commentid: "", //评论id
				pagesize: 0,
				pages: 5,
				allpagesize: 3,
				alertopen: false,
				srkshow: false, //s输入框显示隐藏
				srkname: "@", //输入框中的姓名
				srknameid: 0, //要回复人的id
				srkcenter: "", //输入框中的内容
				replyindex: 0, //回复评论区第几个
				wzindex: 0, //评论帖子的索引
				butindex: 0, //评论框
				nameaxiosname: "", //评论创建人姓名
				shareid: [], //快享用户数据结构
				imgshow: false,
				tabindex: 0, //评论类型
				ImgUp: '',
				stylist: 0, //设计师数量
				install: 0, //安装工
				manager: 0, //店长
				islongo: false, //是否正在加载数据
				isnone: false,
				udata:"",
			nameshowtype: false,
				ti: require('./../../assets/img/index/banner2.jpg'),
				usermsg: {
					"uid": "0",
					"nameimg": require('./../../assets/img/index/banner3.jpg'),
					"name": "1705",

				}, //当前用户消息
				userTalk: [],

				bannerList: [
				 {"img": "/p/carousel/45n5xzqejw8z.jpg"},
					  {"img":"/p/carousel/45n5xzqejw8w.jpg"},
				  {"img": "/p/carousel/45n5xzqejw8x.jpg"},
				   
				],
				
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
				
				shareissueshow:false,
				getfiles:[],//发帖片

			}
		},

		methods: {
			//微信选择图片
			
	/*		wxpick:function(){
				
				this.localIds="启动"
				
				
				
			wx.chooseImage({
			count: 6, // 默认9
			sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			success: function (res) {
			var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	
			}
			});
			},*/
			
			
			//弹框
			alertn: function(index, tex, type, ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
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

			Imgsrc: function(index, indexn) {
				
				/*e.target.parentNode.parentNode.classList.remove("disnone")
				e.target.parentNode.parentNode.classList.add("disblock")*/
				
		   document.getElementsByTagName('body')[0].classList.remove("oyyes")
		   document.getElementsByTagName('body')[0].classList.add("oyno")
				
               //this.caselist=[]
//             /this.caselist = this.datas[index].files
                //
				
                this.caselist=""
                this.caselist = this.datas[index].files[indexn].path
                
				this.alertopen = !this.alertopen

				/*var tleft = -((indexn) * document.getElementById("bannerid").scrollWidth)

				document.getElementById("tran").style.transform = "translate3d(" + tleft + "px, 0px ,0px)"*/

			},

			imgNone: function() {
				this.alertopen = !this.alertopen
				document.getElementsByTagName('body')[0].classList.remove("oyno")
		   document.getElementsByTagName('body')[0].classList.add("oyyes")
			},

			clickMore: function(index) {
				this.alltalshow = !this.alltalshow
				this.alltalkIndex = index
				this.Allleft = 0
			},

			allTikenone: function() {
				this.alltalshow = !this.alltalshow
				this.Allleft = 30
			},

			/*切换class*/

			tabclass: function(index) {
				this.tabindex = index
			},

			/*关注*/
			attention: function() {
				this.msg.attention = true
			},

			/*点赞*/
			praise: function(index) {

				

				this.alertn(5)

				var _this = this
				var uid = this.datas[index].id
				
				_this.axios({
					method: 'post',
					url: '/api/microblogs/' + uid + '/praises',

				}).then(function(res) {
				  var udata=_this.udata
	
					var obj = {
									"name": udata.name,
									"id": udata.id,
			                        "avatar": udata.avatar
								}
					
					       _this.datas[index].praises.unshift(obj)
							_this.datas[index].whether = true
							_this.setalert()		
							


				}).catch(function(err) {
					console.log(err)
					_this.alertn(2, "点赞未成功")
					_this.timeout()
				})

			},

			//取消点赞
			delpraise: function(index) {
				this.alertn(5)

				var _this = this
				var uid = this.datas[index].id
				
				_this.axios({
					method: 'delete',
					url: '/api/microblogs/' + uid + '/praises',

				}).then(function(res) {
					
					
                        
                     	
					var parusers=_this.datas[index].praises
					var actv=_this.udata.avatar
					for(var i=0 ; i<parusers.length ;i++){
						
						if(actv==parusers[i].avatar){
							
							_this.datas[index].praises.splice(i,1)
							
						}
						
						
						
					}
					_this.datas[index].whether = false
                  	_this.setalert()      
              
					
					
					
					
					_this.datas[index].whether = false
					_this.setalert()

				}).catch(function(err) {
					console.log(err)
					_this.alertn(2, "取消点赞未成功")
					_this.timeout()
				})
			},

			

			/*展示全部评论*/
			parentB: function(e) {

				e.target.parentNode.parentNode.classList.remove("disnone")
				e.target.parentNode.parentNode.classList.add("disblock")
			},

			parentN: function(e) {

				e.target.parentNode.parentNode.classList.add("disnone")
				e.target.parentNode.parentNode.classList.remove("disblock")
			},

			/*回复评论区的人的名字*/

			replyandreply: function(index, indexTwo) {
				this.srkcenter = ""
				this.wzindex = index
				this.srkname = "@"
				
				this.butindex = 0

				this.srkname += this.datas[index].comments[indexTwo].creator.name //回复人姓名
				this.srknameid = this.datas[index].comments[indexTwo].creator.id //回复人id
				this.commentid = this.datas[index].comments[indexTwo].id //评论id
				this.blogid = this.datas[index].id
				
				if(this.srknameid==this.udata.id){
					
				}else{
					 this.srkshow = !this.srkshow
				}
				
               
			},

			
			/*确认回复评论区的人*/
			publishtwo: function() {

				if(this.srkcenter == "" || this.srkcenter == "" || this.srkcenter.trim().length == 0) {

				} else  if( this.srkcenter.trim().length >100) {

                 this.alertn(2, "评论字数不能超过100")
                 this.timeout()

				} else{

					var microblog = this.blogid

					//					var /this.srkname
					var _this = this
					this.alertn(5)
					_this.axios({
						method: 'post',

						url: '/api/microblogs/' + microblog + '/comments',

						data: {
							content: this.srkcenter,
							parentId: this.commentid,
							parentCreator: this.srknameid
                            
						}

					}).then(function(res) {
                        
                       
                        
                        
                        
						var toGetUserIds = []
						if(!res.data.data){
						  _this.alertn(2, "不能回复自己")
						    _this.timeout()
							return false
						}else{ 
							
						}
						toGetUserIds.push(res.data.data.creator)
						var ids = JSON.stringify(toGetUserIds);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {
                         
							_this.nameaxiosname = res.data.data[0].name

							var obj = {

								"content": _this.srkcenter,
								"parentId": _this.commentid,
                                  
								"creator": {
									"name": _this.nameaxiosname,
									"id":res.data.data[0].id,
								},
								"parentCreator": {
									"name": _this.srkname.replace("@", "")
								},
								

							}

							_this.datas[_this.wzindex].comments.push(obj)
							var newdate = new Date

							_this.srkshow = !_this.srkshow
							_this.srkcenter = ""

							_this.setalert()

						}).catch(function(err) {
							console.log(err)
                            _this.alertn(2, "评论发送失败")
						    _this.timeout()
						})

					}).catch(function(err) {
						console.log(err)
						
						_this.timeout()

					})

				}

			},

			/*发表回复贴主*/

			publishone: function() {

				if(this.srkcenter == "" || this.srkcenter == "" || this.srkcenter.trim().length == 0) {

				} else if( this.srkcenter.trim().length >100) {

                 this.alertn(2, "评论字数不能超过100")
                  this.timeout()

				} else {
					var microblog = this.blogid

					var _this = this
					this.alertn(5)
					_this.axios({
						method: 'post',

						url: '/api/microblogs/' + microblog + '/comments',

						data: {
							content: this.srkcenter

						}

					}).then(function(res) {

						var toGetUserIds = []
						toGetUserIds.push(res.data.data.creator)
						var ids = JSON.stringify(toGetUserIds);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {

							_this.nameaxiosname = res.data.data[0].name

							var obj = {

								"content": _this.srkcenter,
								"parentId": "",

								"creator": {
									"name": _this.nameaxiosname,
									"id":res.data.data[0].id,
								},
								"parentCreator": {
									"name": "666"
								},
								"content": _this.srkcenter,

							}

							_this.datas[_this.wzindex].comments.push(obj)
							var newdate = new Date
						
							_this.srkshow = !_this.srkshow
							_this.srkcenter = ""

							_this.setalert()

						}).catch(function(err) {
							console.log(err)
                            _this.alertn(2, "评论发送失败")
						    _this.timeout()
						})

						/*	*/
					}).catch(function(err) {
						console.log(err)
					
						_this.timeout()

					})

					//重新计算长度

					/*this.allreply()*/

				}
			},

			//请求用户名字

			nameaxios: function(name) {
				var _this = this
				var toGetUserIds = []
				toGetUserIds.push(name)
				var ids = JSON.stringify(toGetUserIds);

				_this.axios({
					method: 'get',

					url: '/api/users/baseinfos?ids=' + encodeURI(ids)

				}).then(function(res) {

					

					_this.nameaxiosname = res.data.data.name

				}).catch(function(err) {
					console.log(err)

				})
			},

			/*关闭输入框*/

			srkhide: function() {
				this.srkshow = !this.srkshow
			},

			/*重新计算总评论数*/
			allreply: function() {

				for(var i = 0; i < this.userTalk.length; i++) {
					this.userTalk[i].alltake = 0
					this.userTalk[i].alltake += this.userTalk[0].morereply.length

				}
			},

			//评论给贴主

			needreply: function(index) {
				this.srkcenter = ""
				this.srkname = "@"
				this.butindex = 1
				this.srkshow = !this.srkshow
				this.srkname += this.datas[index].creator.name
				this.blogid = this.datas[index].id

				this.wzindex = index
			},

			dataNow: function() {
				this.alertn(5)
				var _this = this
				var usersCache = this.usersCache
				var shareDataCache = this.shareDataCache //
				var pagenum = Math.ceil(this.allpagesize / 5)
				this.pagesize++

					if(this.pagesize > this.allpagesize) {
						this.pagesize = this.allpagesize
						this.islongo = false
						this.alertn(4, "没有更多了")
						this.timeout()
						return false
					}

				_this.axios({
					method: 'get',

					url: '/api/microblogs?pageNum=' + this.pagesize + '&pageSize=20'

				}).then(function(res) {
			
					_this.islongo = false
					_this.setalert()
					_this.allpagesize = res.data.pagination.pages
					//_this.setalert()
					//_this.userTalk=res.data.data.data
					var shouDatas = [];
					var resultData = res.data.data;
					var tempBlogs = resultData.microblogs;
					var m = 0;
					var len = tempBlogs.length;
					var tempBlog;
					var showBlog;
					var showUser;
					var toGetUserIds = [];
					var userId;
					var shareDataCacheObj;
					var tempComments;
					var tempPraises;
					var tempFiles;
					var microblogId;
					for(; m < len; m++) {
						tempBlog = tempBlogs[m]; //每个帖子的发帖信息
						microblogId = tempBlog.id;
						showBlog = {
							id: microblogId,
							content: tempBlog.content,
							created: tempBlog.created,
							whether: false,
						};

						// 处理用户缓存数据
						userId = tempBlog.creator;
						showUser = usersCache[userId];

						if(!showUser) {
							showUser = {
								id: userId,
								isLoad: false,
								name: "",
								avatar: ""
							}
							usersCache[userId] = showUser;

							toGetUserIds.push(userId);
						}
						

						showBlog.creator = showUser;

						tempComments = [];
						tempPraises = [];
						tempFiles = [];
						shareDataCacheObj = {
							comments: tempComments,
							files: tempFiles,
							praises: tempPraises
						};

						showBlog.comments = tempComments;
						showBlog.files = tempFiles;
						showBlog.praises = tempPraises;

						shareDataCache[microblogId] = shareDataCacheObj;

						shouDatas.push(showBlog);

						_this.datas.push(showBlog)
					}

					/*   _this.datas=shouDatas;*/
					console.log(_this.datas)

					// 处理评论
					var blogComments = res.data.data.comments;
					var tempComment;
					var showComment;
					var commentCreator;
					var parentCreator;
					var parentCreatorId;
					for(m = 0, len = blogComments.length; m < len; m++) {
						tempComment = blogComments[m];
						microblogId = tempComment.microblogId;
						showComment = {
							id: tempComment.id,
							content: tempComment.content,
							created: tempComment.created,
							creator: tempComment.creator,
							status: tempComment.status,
							parentId: tempComment.parentId,
							type: tempComment.type,

							creator: {},
							parentCreator: {}
						}
						

						// 创建人处理

						commentCreator = usersCache[tempComment.creator];

						if(!commentCreator) {
							commentCreator = {
								id: tempComment.creator,
								name: "",
								isLoad: false,
								avatar: ""
							};
							usersCache[tempComment.creator] = commentCreator;
							toGetUserIds.push(tempComment.creator);
						}
						showComment.creator = commentCreator;

						
						// 回复贴处理
						parentCreatorId = tempComment.parentCreator;
						if(parentCreatorId) {
							parentCreator = usersCache[parentCreatorId];

							if(!parentCreator) {
								parentCreator = {
									id: tempComment.parentCreator,
									name: "",
									isLoad: false,
									avatar: ""
								};
								usersCache[parentCreatorId] = parentCreator;
								toGetUserIds.push(parentCreatorId);
							}

							showComment.parentCreator = parentCreator;

							
						}

						// 放到帖子的评论显示数据的缓存里            	
						shareDataCache[microblogId].comments.push(showComment);
					}

					// 处理点赞
					var praiseDates = resultData.praises;
					var tempraise;
					var showPraise;

					for(m = 0, len = praiseDates.length; m < len; m++) {
						tempraise = praiseDates[m]
						microblogId = tempraise.microblogId;

						//
						showPraise = usersCache[tempraise.memberId];

						if(!showPraise) {
							showPraise = {
								id: tempraise.memberId,
								name: "",
								isLoad: false,
								avatar: ""

							}

							usersCache[tempraise.memberId] = showPraise
							toGetUserIds.push(tempraise.memberId);
						}

						shareDataCache[microblogId].praises.push(showPraise);

					}

					// 处理帖子图片            

					var filesDates = resultData.files;
					var temfiles;
					var showFiles;

					for(m = 0, len = filesDates.length; m < len; m++) {
						temfiles = filesDates[m]
						microblogId = temfiles.belongId;

						//
						showFiles = usersCache[temfiles.memberId];

						showFiles = {
							path: temfiles.path,

						}

						shareDataCache[microblogId].files.push(showFiles);

					}

					//处理是否点赞

					var whetherDatas = resultData.userPraises

					for(var m = 0; m < _this.datas.length; m++) {

						var microblogid = _this.datas[m].id

						for(var n = 0; n < whetherDatas.length; n++) {
							
							if(microblogid == whetherDatas[n].microblogId) {
								

								_this.datas[m].whether = true
							}

						}

					}

					// 请求未加载的数据
					if(toGetUserIds.length > 0) {
						//请求：;
						/*	var toGetUserIds=["1","2","3"]*/
						var ids = JSON.stringify(toGetUserIds);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {

							
							_this.setalert()

							var userInfos = res.data.data;
							m = 0, len = userInfos.length;
							var tempUser;
							var fromCacheUser;
							for(; m < len; m++) {
								var tempUser = userInfos[m];
								fromCacheUser = usersCache[tempUser.id];
								if(!fromCacheUser.isLoad) {
									fromCacheUser.name = tempUser.name;
									fromCacheUser.avatar = tempUser.avatar; //:""
									fromCacheUser.isLoad = true
								}
							}

						}).catch(function(err) {
							console.log(err)

						})

						/**/

						//

					}

				}).catch(function(err) {
					console.log(err)
					_this.alertn(2, "出错了")
					_this.timeout()
				})

			},
			
			//发帖
			
			shareissueblock:function(){
				/*this.shareissueshow=!this.shareissueshow*/
				document.getElementsByTagName('body')[0].classList.remove("oyyes")
		         document.getElementsByTagName('body')[0].classList.add("oyno")
				
				this.Allleft=0
				document.getElementById("opquick").style.display="block"
				this.msg.imgUrls=[] 
				this.getfiles=[]
			},
			
			black:function(){
				document.getElementsByTagName('body')[0].classList.remove("oyno")
		         document.getElementsByTagName('body')[0].classList.add("oyyes")
				this.Allleft=20
				
			},
			
			upimg: function(e) {
        
				var _this = this
                
				this.morimg = []
				this.morimg = e.target.files
				/*	console.log(this.morimg)*/
			/*	var imgfiles = new FormData(e.target.files[0]	)*/
			     var imgfiles=e.target.files
				 var imgdata = e.target.files
                 var formData = new FormData();
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
				} else if(this.msg.imgUrls.length+ imgdata.length> 6) {

					document.getElementById("tip").innerHTML = "图片数量不能超过6张"
					  _this.setalert()
					return false;
				} else {
					       document.getElementById("tip").innerHTML = ""
					
					        var datimg=imgdata[i]
						
							console.log(datimg)
						    formData.append('fileList', datimg); // 多选文件数据
					       // formData.append('files', imgdata);  // 文件数据
					
					 
					
						}
							
							
							
						}
				
					
				
						
					
                     this.alertn(5)
					
					var _this = this
				/*	console.log(imgfiles)*/
					_this.axios({
						method: 'post',
						
						url: '/api/microblogs/files',
                        
						data: formData

					}).then(function(res) {
						 var igdatas=res.data.data

                     for(var i=0; i<igdatas.length;i++){
                      	_this.msg.imgUrls.push(igdatas[i])
                      	var obj={
							path:igdatas[i].path
						}
                      	
                      	_this.getfiles.push(obj)
                      }
 
 
                   /* var obj={
							path:igdatas.path
						}
                     
						_this.msg.imgUrls.push(igdatas)*/
					/*	_this.getfiles.push(obj)*/
						
						
						
						
						
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
			this.getfiles.splice(index,1)
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
		
		 //显示快享点赞人昵称
         nameshow:function  (e) {
         	
         	
             
              	e.target.parentNode.classList.add("nameshow")
               setTimeout(() => {
						e.target.parentNode.classList.remove("nameshow")
				}, 3000)
             /*  if(this.nameshowtype==false){
                
                  setTimeout(() => {
						e.target.parentNode.classList.remove("nameshow")
						this.nameshowtype=false

				}, 3000)
                
               }

          
         this.nameshowtype=true*/
        
       
         
         	
         	
         },

		/*提交*/

		sub: function() {
			
		
			if(this.msg.center.trim().length ==0&&this.msg.imgUrls.length==0) {
				document.getElementById("tip").innerHTML = "帖子内容不能为空";
				return false;
			} else if(this.msg.center.trim().length>200) {
				document.getElementById("tip").innerHTML = "帖子内容不能超过200个字符";
				return false;
			} else {
				//console.log(this.msg)
				this.alertn(5)
				document.getElementById("tip").innerHTML = ""

				var _this = this

				var nav = this.msg.center
				var imgur=[];
				
					
				
				for (var i=0;i<this.msg.imgUrls.length;i++) {
					
					imgur.push("imageId="+this.msg.imgUrls[i].id)
					

				}
				
			
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
				
					console.log(res.data.data)
					
					var toGetUserIds = []
						toGetUserIds.push(res.data.data.creator)
						var ids = JSON.stringify(toGetUserIds);
                        var datas=res.data.data
						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {

							_this.nameaxiosname = res.data.data[0].name

                                
							var obj = {
                            comments:[],
                            content:datas.content,
                            created:datas.created,
                             creator:{
                             	avatar:res.data.data[0].avatar,
                             	id:res.data.data[0].id,
                             	isLoad:true,
                             	name:res.data.data[0].name,
                             },
                             files:_this.getfiles,
                             id:datas.id,
                             praises:[],
                             whether:false,
							}

							/*_this.datas.comments.push(obj)*/
							 
							 _this.datas.unshift(obj)
	                       document.body.scrollTop=0
                            
						}).catch(function(err) {
							console.log(err)
                           
						   
						})
					
					
					 _this.alertn(1,"发表成功")
					
					 document.getElementsByTagName('body')[0].classList.remove("oyno")
		         document.getElementsByTagName('body')[0].classList.add("oyyes")
					_this.msg.center=""
                    _this.msg.imgUrls=[]
                     setTimeout(() => {
					_this.Allleft=20
					

				}, 1000)
                 
                 
                   _this.timeout()
				}).catch(function(err) {
					console.log(err)
				 _this.alertn(2,"发表未成功")
                 _this.timeout()
                 
                 document.getElementsByTagName('body')[0].classList.remove("oyno")
		         document.getElementsByTagName('body')[0].classList.add("oyyes")
                 
				})

			}
		}
			

		},

		components: {

			banner: carousel,
			alertopen: alert,
			
		},

		mounted: function() {

			this.dataNow()

			//Math.ceil

		

			this.alertn(5)
			var _this = this
			
			  _this.axios({
				method: 'get',
				url: '/api/users/0'
			}).then(function(res) {         				
				_this.udata=res.data.data			
			}).catch(function(err) {
				console.log(err)
			})
		
			
			
			new Swiper('.swiper-container.snot', {
               observer:true,
              
			})

		

			var _this = this
			window.onscroll = function() {

				var scrtop = document.documentElement.scrollTop || document.body.scrollTop;
				var wheight = document.documentElement.clientHeight || document.body.clientHeight;
				var scrheight = document.documentElement.scrollHeight || document.body.scrollHeight;
				var pagenum = Math.ceil(_this.allpagesize / 5)

				if(scrtop + wheight == scrheight && _this.islongo == false) {
					
					
					_this.islongo = true //正在请求值改为真，请求完成之后改为假
					_this.alertn(5)

					/*   if( _this.pagesize>pagenum){
   	    	_this.setalert()
   	    	_this.islongo=false
   	    	_this.isnone=true
   	    	setTimeout(() => {
			_this.isnone=false

				}, 2000)
   	       return false
   	       
   	    }*/

					_this.dataNow()
				}

			}

		},

	}
</script>

<style src='./../../assets/css/share.css'></style>
<style src='./../../assets/css/shareissue.css'></style>
<style>
	.reply_itme .reply_reply {
		padding-left: 0px;
	
	}
	
	.kuaijie {
		position: fixed;
		left: 0px;
		bottom: 5px;
		width: 100%;
		height: 50px;
		margin: 0px auto;
	}
	
	.kuaijie img {
		display: block;
		width: 50px;
		margin: 0px auto;
	}
	
	.nonemore {
		width: 100%;
		position: fixed;
		bottom: 2rem;
		left: 0px;
		z-index: 99;
	}
	
	.nonemore span {
		display: block;
		margin: 0px auto;
		border-radius: 0.2rem;
		width: 4rem;
		color: white;
		background: #000000;
		height: 1rem;
		line-height: 1rem;
		text-align: center;
		font-size: 0.6rem;
		background-color: rgba(00, 00, 00, 0.7);
	}
	
	.take ul li span img {
		display: block;
		background: white;
	}
	
	.share_reply {
		position: relative;
	}
	
	.share_reply.disnone {
		display: block;
		height: 10rem;
		overflow: hidden;
	}
	
	.share_reply .moreblock {
		display: none;
		position: absolute;
		left: 0px;
		width: 100%;
		bottom: 0px;
	}
	
	.share_reply .moreblock .opend {
		width: 100%;
		height: 60px;
		background: url(../../assets/img/share/btm.png) center;
		background-size: 100% 100%;
	}
	
	.share_reply .moreblock p {
		width: 100%;
		display: block;
		font-size: 0.5rem;
		text-align: center;
		background: white;
		margin: 0px;
	}
	
	.share_reply .moreblock span {
		width: 100%;
		display: none;
		font-size: 0.5rem;
		text-align: center;
		background: white;
		margin: 0px;
	}
	
	.share_reply.disnone .moreblock {
		display: block;
	}
	
	.share_reply.disnone .moreblock span {
		display: block;
	}
	
	.share_reply .morenone {
		display: none;
		position: absolute;
		left: 0px;
		width: 100%;
		bottom: 0px;
	}
	
	.share_reply.disblock .morenone {
		display: block;
	}
	
	.share_reply.disblock .morenone p {
		width: 100%;
		display: block; 
		font-size: 0.65rem;
		text-align: center;
		background: white;
		margin: 0px;
	}
	
	.share-title {
		font-size: 0.65rem;
	}
	
	.share-title small {
		margin: 0rem 1px;
		font-size: 0.65rem;
	}
	
	.parover {
		display: block;
		clear: both;
		font-size: 0.5rem;
		width: 100%;
		color: cornflowerblue;
	}
	
	.navpar {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		display: block;
	}
	
	.tiezinone {
		display: block;
		text-align: center;
		padding-top: 50px;
	}
	
	.share_reply.disblock {
		padding-bottom: 1rem;
	}
	
	
	
	
	.swiper-wrapper{
		height: auto;
	}
	
	.swiper-slide{
		height: auto;
	}
	
	
	#bannerid{
		
		height: 100%;
		overflow: hidden;
		
	}
	
	#bannerid img{
		display: block;
		position: absolute;
		top: 0px;
		left: 0px;
		right: 0px;
		bottom: 0px;
		margin: auto;
		z-index: 200;
	}
	
	.imgMagnify .imgNav{
		padding: 0px;
		height: 100%;
		
	}
	
	.swiper-container{
		top: 0px;
		left: 0px;
		background: black;
		width: 100%;
		
		overflow: auto;
	
	}
	
	
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
	
	.take textarea{
		width: 18rem;
		
	}
	.take-butnone{
	 position: absolute;
	 top: 0px;
	 right: 0px;
	 width: 2rem;
	 margin: 0rem 1rem;
	 padding: 0.3rem 0rem;
	 font-size: 0.8rem;
	 background: none;
	 color: #333333;
	}
	
	
	.share-title span{
		font-size: 0.65rem;
	}
	
	.imgcenter{
		width: 100%;
		display: block;
		height: 5.3rem;
	    background-position: 50% 50%;
	    background-size: cover;
	}
	
	
</style>

<style src='./../../assets/css/shareissue.css'></style>