<template>
	<div id="talk" class="talk ">

		<!--用户评论-->
		<div class="goodUsertalk">
			<div class="comment-tab">
				<ul>
					<li :class="{active:tabindex==0}" @click="tabclass(0)">全部<br/>{{allen}}</li>
					<li :class="{active:tabindex==1}" @click="tabclass(1)">喜欢<br/>{{love}}</li>
					<li :class="{active:tabindex==2}" @click="tabclass(2)">一般<br/>{{ok}}</li>
					<li :class="{active:tabindex==3}" @click="tabclass(3)">失望<br/>{{no}}</li>
				</ul>
			</div>

			<div> 
				
				<div class="nohave" v-show="datas.length==0">
					该商品当前还没有评论
				</div>
				
				
				<!--全部-->
				<div v-show="tabindex==0">
					<!--客户评价-->
					<div v-for="(item,index) in datas" class="goodusertalk">
						<div class="userMeg">
							<div class="userMeg-img" :style="{backgroundImage: 'url(' + item.creator.avatar + ')' }">
						    <!--<img :src="" />-->
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}</p>
								<span>{{item.created}}</span>
							</div>
							<div class="userMeg-evaluate">
								<span v-show="item.score==0">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span v-show="item.score==1">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span v-show="item.score==2">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

							</div>

						</div>

						<div class="goodusertalk-contentnet">
							<p>
								{{item.content}}
							</p>
							<div class="take-imgs" v-show="filesDates[index].length>0">

								<ul class="two" >
									<li v-for="(item,indexdb) in filesDates[index]"  :style="{backgroundImage: 'url(' + item.path + ')' }"  @click="Imgsrc">

										
									</li>

								</ul>
							</div>
						</div>

						<!--回复-->
						<div>

							<div class="goodusertalk-revert" v-show="!!item.reply">

								<ul>
									<li >

										<span class="Uname gf" >商家回复:</span>
										<span>{{item.reply}}</span>

									</li>

								</ul>
							</div>

						</div>

						<!--查看所有回复-->
						<div>

						</div>

					</div>

					<!--客户评价-e-->
				</div>
				<!--全部-end-->
				
				<!--喜欢-->
				<div v-show="tabindex==1">
					<!--客户评价-->
					<div v-for="(item,index) in datas" class="goodusertalk" v-show="item.score==0">
						<div class="userMeg">
							<div class="userMeg-img" :style="{backgroundImage: 'url(' + item.creator.avatar + ')' }">
						   <!-- <img :src="item.creator.avatar" />-->
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}</p>
								<span>{{item.created}}</span>
							</div>
							<div class="userMeg-evaluate">
								<span v-show="item.score==0">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span v-show="item.score==1">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span v-show="item.score==2">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

							</div>

						</div>

						<div class="goodusertalk-contentnet">
							<p>
								{{item.content}}
							</p>
							<div class="take-imgs">

								<ul class="two">
									<li v-for="(item,indexdb) in filesDates[index]"  :style="{backgroundImage: 'url(' + item.path + ')' }"  @click="Imgsrc">

										
									</li>

								</ul>
							</div>
						</div>

						<!--回复-->
						<div>

							<div class="goodusertalk-revert" v-show="!!item.reply">

								<ul>
									<li >

										<span class="Uname gf" >商家回复:</span>
										<span>{{item.reply}}</span>

									</li>

								</ul>
							</div>

						</div>

						<!--查看所有回复-->
						<div>

						</div>

					</div>

					<!--客户评价-e-->
				</div>
				<!--喜欢-end-->
				
				
				
				<!--一般-->
				<div v-show="tabindex==2">
					<!--客户评价-->
					<div v-for="(item,index) in datas" class="goodusertalk" v-show="item.score==1">
						<div class="userMeg">
							<div class="userMeg-img" :style="{backgroundImage: 'url(' + item.creator.avatar + ')' }">
						  <!--  <img :src="item.creator.avatar" />-->
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}</p>
								<span>{{item.created}}</span>
							</div>
							<div class="userMeg-evaluate">
								<span v-show="item.score==0">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span v-show="item.score==1">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span v-show="item.score==2">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

							</div>

						</div>

						<div class="goodusertalk-contentnet">
							<p>
								{{item.content}}
							</p>
							<div class="take-imgs">

								<ul class="two">
									<li v-for="(item,indexdb) in filesDates[index]"  :style="{backgroundImage: 'url(' + item.path + ')' }"  @click="Imgsrc">

										
									</li>
								</ul>
							</div>
						</div>

						<!--回复-->
						<div>

							<div class="goodusertalk-revert" v-show="!!item.reply">

								<ul>
									<li >

										<span class="Uname gf" >商家回复:</span>
										<span>{{item.reply}}</span>

									</li>

								</ul>
							</div>

						</div>

						<!--查看所有回复-->
						<div>

						</div>

					</div>

					<!--客户评价-e-->
				</div>
				<!--一般-end-->
				
				
					
				<!--差评-->
				<div v-show="tabindex==3">
					<!--客户评价-->
					<div v-for="(item,index) in datas" class="goodusertalk" v-show="item.score==2">
						<div class="userMeg">
							<div class="userMeg-img" :style="{backgroundImage: 'url(' + item.creator.avatar + ')' }">
						    <!--<img :src="item.creator.avatar" />-->
							</div>
							<div class="userMeg-Nmae">
								<p>{{item.creator.name}}</p>
								<span>{{item.created}}</span>
							</div>
							<div class="userMeg-evaluate">
								<span v-show="item.score==0">
            					<i class="layui-icon ">&#xe6c6; </i><small>喜欢</small>
            			    </span>

								<span v-show="item.score==1">
            					<i class="layui-icon ">&#xe6af; </i><small>一般</small>
            			    </span>

								<span v-show="item.score==2">
            					<i class="layui-icon ">&#xe69c; </i><small>失望</small>
            			    </span>

							</div>

						</div>

						<div class="goodusertalk-contentnet">
							<p>
								{{item.content}}
							</p>
							<div class="take-imgs">

								<ul class="two">
									<li v-for="(item,indexdb) in filesDates[index]"  :style="{backgroundImage: 'url(' + item.path + ')' }"  @click="Imgsrc">

										
									</li>

								</ul>
							</div>
						</div>

						<!--回复-->
						<div>

							<div class="goodusertalk-revert" v-show="!!item.reply">

								<ul>
									<li >

										<span class="Uname gf" >商家回复:</span>
										<span>{{item.reply}}</span>

									</li>

								</ul>
							</div>

						</div>

						<!--查看所有回复-->
						<div>

						</div>

					</div>

					<!--客户评价-e-->
				</div>
				<!--差评-end-->

				
			</div>

			<!--图片放大效果-->
			<div @click="imgNone" class="imgMagnifytake" :class="{imgshow:imgshow==true}">
				<div class="imgNav">
					<img :src="ImgUp" />
				</div>
			</div>

			<!--alert-->
			<alertopen :text=tip></alertopen>
		</div>
	</div>
</template>

<script>
	import alert from './../../components/alert.vue'
	export default {

		name: 'talk',

		data() {
			return {
				usersCache: {},
				datas:[],
				shareDataCache: {}, //
				filesDates:[],
				islongo:false,
				imgshow: false,
				pagesize: 0,
				pages: 1,
				allpagesize: 5,
				allen:0,
				tabindex: 0, //评论类型
				ImgUp: '',
				love: 0,
				ok: 0,
				no: 0,
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
				ti: require('../../assets/img/index/banner2.jpg'),
				goodusertalk: []
			}
		},
		components: {

			alertopen: alert,
		},

		methods: {

			Imgsrc: function(e) {

				this.imgshow = !this.imgshow
				var imgsrc=e.target.style.backgroundImage
				
				imgsrc=imgsrc.split('"')[1]
				
       console.log(imgsrc)
				this.ImgUp = imgsrc

			},

			imgNone: function() {
				this.imgshow = !this.imgshow
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

			/*弹框*/

			alertn: function(index, tex, type, ind, word) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert = true
				this.tip.alwarning = tex

				
			},

			alertdb: function(index, tex) {

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


        /*likelist:function(){
        	
        
        	 
           this.love=0
             this.ok=0
               this.no=0
               
			for(var i = 0; i < this.datas.length; i++) {
				if(this.datas[i].score == 0) {
					this.love++
				}
  
				if(this.datas[i].score == 1) {
					this.ok++
				}

				if(this.datas[i].score == 2) {
					this.no++
				}
			}
        },
*/

			/*加载数据*/
			dataNow: function() {
                 /**/
               	this.alertn(5) 
                var usersCache = this.usersCache
			  var shareDataCache = this.shareDataCache //
                
             var _this=this
             
             this.pagesize++

              console.log( this.$route.params.goodid)

					if(this.pagesize > this.allpagesize) {
						this.pagesize = this.allpagesize
						this.islongo = false
						this.alertn(4, "没有更多了")
						this.timeout()
						return false
					}
 
             
             
           _this.axios({
				method: 'get',
				

				url: '/api/goods/' + this.$route.params.goodid + '/comments?pageNum='+_this.pagesize+'&pageSize=5'
                                     
			}).then(function(res) {
				console.log(res.data)
				 _this.love=res.data.data.count.zero
			_this.ok=res.data.data.count.one
			_this.no=res.data.data.count.two
				 _this.islongo = false
                 _this.setalert()   
			
				/*_this=*/
              /*_this.goodusertalk=res.data.data*/
              _this.allen=res.data.pagination.total
             _this.allpagesize=res.data.pagination.pages
              var toGetuser=[]
              var datas=res.data.data.goodsComments
              
            
              var datasdfiles=res.data.data.files
              for(var i=0; i<datasdfiles.length; i++){
              	 _this.filesDates.push(datasdfiles[i])
              }
             
              
              
              
            
           
                 
              var m=0
              var len=datas.length
              var tempBlog;
              var microblogId;
              var showBlog;
              var userId;
              var shouDatas=[];
              var showUser;
              for(; m<len;m++){
               
              	       tempBlog = datas[m]; //每条评论信息
						microblogId = tempBlog.id;
						showBlog = {
							id: microblogId,
							content: tempBlog.content,
							created: tempBlog.created,
							score: tempBlog.score,
							reply:tempBlog.reply,
							replyCreated:tempBlog.reply,
							memberId:tempBlog.memberId,
							goodsExtendId:tempBlog.goodsExtendId,
							goodsId:tempBlog.goodsId,
							goodsName:tempBlog.goodsName,
							disabled:tempBlog.disabled,
						};

						// 处理用户缓存数据
						userId = tempBlog.memberId;
						showUser = usersCache[userId];

						if(!showUser) {
							showUser = {
								id: userId,
								isLoad: false,
								name: "",
								avatar: ""
							}
							usersCache[userId] = showUser;

							toGetuser.push(userId);
						}
						

						showBlog.creator = showUser;

						shouDatas.push(showBlog);
					    
						_this.datas.push(showBlog)
              	
              	
              		
              	
              	   
              	  
              
              }
              
              
              
              	
            
              	
              	if(toGetuser.length > 0) {
						//请求：;
						/*	var toGetUserIds=["1","2","3"]*/
						var ids = JSON.stringify(toGetuser);

						_this.axios({
							method: 'get',

							url: '/api/users/baseinfos?ids=' + encodeURI(ids)

						}).then(function(res) {

							
							
							
						

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

			})
				
			}

		},

		mounted: function() {
         
          
          	/*/this.goodid = this.$route.params.gooditemid*/
      /*    	console.log(this.$route.params.goodid)*/
          	
          	
          	
          	
          	this.dataNow()
          	
              var _this = this
			window.onscroll = function() {

				var scrtop = document.documentElement.scrollTop || document.body.scrollTop;
				var wheight = document.documentElement.clientHeight || document.body.clientHeight;
				var scrheight = document.documentElement.scrollHeight || document.body.scrollHeight;
				

				if(scrtop + wheight == scrheight && _this.islongo == false) {
					_this.islongo = true //正在请求值改为真，请求完成之后改为假
				

					_this.dataNow()
				}

			}

			

			 
			

		}
	}
</script>

<style src='./../../assets/css/talk.css'></style>
<style>
.nohave{
	width: 100%;
	text-align: center;
	padding: 1rem 0rem;
	font-size: 0.6rem;
}
</style>