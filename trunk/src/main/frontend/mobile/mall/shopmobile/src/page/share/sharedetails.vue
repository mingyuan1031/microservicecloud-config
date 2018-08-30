<template>
	<div id="share"  class="paddingBox">

		
		<!--用户评论-->
		<div class="goodUsershare">
			<!-- {{datas}}-->
			<div>
				<!--全部-->
				<div>
					<!--datas-->

					

					<div v-for="(item,index) in datas" class="userTalk">

						<div class="userMeg">
							<div class="userMeg-img">
								<img :src="item.creator.avatar" />
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}</p>
								<span>{{item.created}}</span>
							</div>
							<!--<div class="userMeg-evaluate">
									<span v-show="item.attention==false" @click="attention(index)"><small>关注</small></span>
                                    <span v-show="item.attention==true"><small>已关注</small></span>
								</div>-->

						</div>

						<div class="userTalk-content">
							
							<p>
								{{item.content}}
							</p>
							<!--	</router-link>-->
							<div class="take-imgs" v-show="datas[index].files.length>0">

								<ul class="two">
									<li @click="Imgsrc(index,indexdb)" v-for="(itemdb,indexdb) in datas[index].files">

										<img :src="itemdb.path "/>
									</li>

								</ul>
							</div>

							<div class="take-share">
								<div class="takeShare_center">

									<div class="item">
										<i class="layui-icon">&#xe641;</i>
										<!-- 28 :{{index}}-->
									</div>

									<div class="item" @click="needreply(index)">
										<i class="layui-icon">&#xe63a;</i> {{item.comments.length}}
									</div>

									<div class="item " v-show="datas[index].whether==false" @click="praise(index)">
										<i class="layui-icon">&#xe756;</i> {{item.praises.length}}
									</div>

									<div class="item active" v-show="datas[index].whether==true" @click="delpraise(index)">
										<i class="layui-icon">&#xe756;</i> {{item.praises.length}}
									</div>
								</div>

							</div>

							<!--点赞人-->
							<div class="praiseitem" v-show="datas[index].praises.length<2&&datas[index].praises.length>0">
								<!--"-->
								<ul>
									<li v-for="(iteme,indexdb) in datas[index].praises">{{iteme.name}},</li>

									<span>觉得很赞</span>

								</ul>

							</div>

							<div class="praiseitem" v-show="datas[index].praises.length>=2">
								<!--"-->
								<ul class="navpar">
									<li v-for="(iteme,indexdb) in datas[index].praises">{{iteme.name}},</li>

								</ul>

								<small class="parover">等{{item.praises.length}}人觉得很赞</small>

							</div>

							<!--点赞人end-->

							<div class="share_reply" :class="{disnone:datas[index].comments.length>6}">
								<div class="reply_itme">
									<!--<div class="reply_time">{{item.created}}</div> 发帖时间-->
									<div class="reply_reply">
										<div class="reply_id" v-for="(item , indexTwo) in datas[index].comments">
											<div class="share-title" @click="replyandreply(index ,indexTwo)">
												<span>{{item.creator.name}}</span>
												<small v-show='!item.parentId==false'>回复</small>
												<span v-show='!item.parentId==false' style="color: orangered;">{{item.parentCreator.name}}</span>
												<small>:</small>{{item.content}}
											</div>

											<!--<div class="reply_title">

												<div class="reply_name"><span>{{item.creator.name}}</span>
												<small v-show='!item.parentId==false'>回复</small><span v-show='!item.parentId==false' style="color: orangered;" >{{item.parentCreator.name}}</span>
												</div>
												
											</div>

											<div class="reply_center"  @click="replyandreply(index ,indexTwo)">
												{{item.content}}
											</div>-->

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
			<div class="imgMagnify" :class="{open_block:alertopen==true}"  @click="imgNone">
				<div class="imgbg"></div>
				<div class="imgNav">
					<!--<img :src="ImgUp" />-->
					<div class="swiper-container snot">
						<div class="swiper-wrapper" id="tran">
							<div class="swiper-slide" id="bannerid" v-for="(item,index) in caselist"><img style="width: 100% ;" :src="item.path" />
							</div>
						</div>
					</div>

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
				<router-link to="/share/shareissue">
					<img src="../../assets/img/sadffsa.png" />
				</router-link>
			</div </div>

		</div>

		<div class="nonemore" v-show="isnone==true">
			<span>没有更多了</span>
		</div>

		<!--alert-s-->

		<alertopen :text=tip></alertopen>
		<!--alert-end-->

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

				caselist: [
				
					
				],

				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
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
				ti: require('./../../assets/img/index/banner2.jpg'),
				usermsg: {
					"uid": "0",
					"nameimg": require('./../../assets/img/index/banner3.jpg'),
					"name": "1705",

				}, //当前用户消息
				userTalk: [],

				bannerList: [{
						"img": require('./../../assets/img/index/banner2.jpg')
					},
					{
						"img": require('./../../assets/img/index/banner2.jpg')
					},
					{
						"img": require('./../../assets/img/index/banner2.jpg')
					},
					{
						"img": require('./../../assets/img/index/banner2.jpg')
					},
				],

			}
		},

		methods: {
			
			
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
                this.caselist=[]
                
				this.caselist = this.datas[index].files

                
				this.alertopen = !this.alertopen

				var tleft = -((indexn) * document.getElementById("bannerid").scrollWidth)

				document.getElementById("tran").style.transform = "translate3d(" + tleft + "px, 0px ,0px)"

			},

			imgNone: function() {
				this.alertopen = !this.alertopen
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
					

					var obj = {

						"name": res.data.data.praiseUser.name,
						"id": res.data.data.praiseUser.id,

					}

					var praiseUser = []

					
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
					
					
                        
                     if(res.data.data.length==0){
                     	_this.datas[index].praises = []
                     }else{
                     var toGetUserIds=[]
                     for(var i=0 ; i<res.data.data.length ;i++){
                     	toGetUserIds.push(res.data.data[i].memberId)
                     }
                     
                   
						var ids = JSON.stringify(toGetUserIds);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {
							_this.datas[index].praises=[]
							 for(var i=0 ; i<res.data.data.length ;i++){
                     	              var obj={
			                            	name:res.data.data[i].name,
			                            	id:res.data.data[i].id
			                            }
                     	              
                     	             _this.datas[index].praises.push(obj)
                                }
                           
							
							_this.setalert()

						
						}).catch(function(err) {
							console.log(err)

						})
                     
                     
                     }
					
					
					
					
					
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
				this.srkshow = !this.srkshow
				this.srkname += this.datas[index].comments[indexTwo].creator.name //回复人姓名
				this.srknameid = this.datas[index].comments[indexTwo].creator.id //回复人id
				this.commentid = this.datas[index].comments[indexTwo].id //评论id
				this.blogid = this.datas[index].id

			},

			
			/*确认回复评论区的人*/
			publishtwo: function() {

				if(this.srkcenter == "" || this.srkcenter == "" || this.srkcenter.trim().length == 0) {

				} else {

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

					//console.log(res.data.data)

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
			    var blogId=this.$route.params.shareid

					

				_this.axios({
					method: 'get',


                  
					url: '/api/microblogs/'+blogId

				}).then(function(res) {
					
					_this.islongo = false
					_this.setalert()
					
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
				
						tempBlog = tempBlogs; //每个帖子的发帖信息
						microblogId = tempBlogs.id;
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
						/*if(!showUser.isLoad){
							toGetUserIds.push(userId); //请求之后应该把isLoad值改为真
						}*/

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
					

					/*   _this.datas=shouDatas;*/
					

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
						//console.log(usersCache)

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

						/*if(!commentCreator.isLoad){
							toGetUserIds.push(tempComment.creator);
						}*/

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

							/*if(!parentCreator.isLoad){
								toGetUserIds.push(parentCreator);
							}*/
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

		},

		components: {

			banner: carousel,
			alertopen: alert,
		},

		mounted: function() {

			this.dataNow()
          
			this.alertn(5)
			var _this = this
			
			new Swiper('.swiper-container.snot', {
               observer:true,
			})

			

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
	
	.share_reply.disblock {
		padding-bottom: 1rem;
	}
	
	.share_reply.disblock .morenone p {
		width: 100%;
		display: block;
		font-size: 0.5rem;
		text-align: center;
		background: white;
		margin: 0px;
	}
	
	.share-title {
		font-size: 0.5rem;
	}
	
	.share-title small {
		margin: 0rem 1px;
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
</style>