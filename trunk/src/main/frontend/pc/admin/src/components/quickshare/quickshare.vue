<template>
	<div class="contain">
		
		<!--帖子内容的模态框开始-->
		<Modal
        v-model="modal1"
        :title="title"
        @on-ok="ok"
        @on-cancel="cancel">
       {{colgvalue}}
       </Modal>
    <!--帖子内容的模态框结束-->
    <!--帖子点赞的模态框开始-->
		<Modal
        v-model="modal2"
        title="帖子点赞详情"
        @on-ok="ok"
        @on-cancel="cancel">
       <p v-for="(item,index) in comments" class="comments">{{index+1}}&nbsp{{item.n}} <br /><br /></p>
       </Modal>
    <!--帖子点赞的模态框结束-->  
		<div class="item-title">
		<div class="miaoshu">
		<h3>快享管理</h3>
		<h5>快享帖子以及评论管理</h5>
		</div>
		</div>	
<div style="margin: 0 auto;overflow-y: scroll;height: 750px;">
	<div style="margin-top: 20px;margin-bottom: 20px;">
	<Input v-model="searchvalue" placeholder="请输入 帖子的内容进行搜索..." style="width: 300px"></Input>
    <Button type="ghost" icon="ios-search"  @click="searchpage">帖子搜索</Button>
	</div>
<!-- 表头-->
<table class="table">
	<tr>
		<td style="width: 50%;">帖子内容</td>
		<td style="width: 30%;">评论内容</td>
		<td style="width: 10%;">点赞人</td>
		<td style="width: 10%">帖子操作</td>		
	</tr>
</table>	      	   
       	   <div class="tablecontain">
       	   	<ul class="table_containx" v-for="(item,indexs) in trlist">
       	   		<li style="width:50%;padding-left: 5px;">
       	   			<div class="tupians">
       	   				<img :src="item.smallimage" class="imagex"/>
       	   				<ul class="photos">
       	   					<li @click="tabphoto(index,indexs)" v-for="(itemx,index) in item.files">
       	   						 <img :src="itemx.path" width="50" height="52"/>
       	   					</li>     	   			
       	   				</ul>
       	   			</div>
       	   			<div class="miaoshus">
       	   				<div class="productdels">{{item.content}}</div><br />
       	   				<div class="df">
       	   					<div class="df_l">
       	   					<img :src="item.avatar" width="60" class="df_limg"/>
       	   					<div class="zhanghao">
       	   						<span class="zhanghao1">{{item.creator.name}}</span><br />
       	   					    <span class="zhanghao1">{{item.created}}</span>
       	   					</div>
       	   					</div>
       	   					<div class="df_r">
       	   						<span class="dianzan"><Icon type="ios-heart" /><span>{{item.praisesnum}}</span></span>
       	   						<span class="dianzan"><Icon type="ios-chatboxes" /><span>{{item.commentsnum}}</span></span>      	   						      	   						
       	   					</div>
       	   				</div>
       	   				<div></div>
       	   			</div>
       	   		</li>
       	   		<li style="width: 30%;padding: 10px 10px;width: 503px;overflow-y: auto;word-wrap:break-word">
       	   			<span class="conment">帖子评论内容:</span><br />
       	   			<span class="content" v-for="chat in item.comments">{{chat.creator.name}}{{chat.parentCreator?' 回复 '+ chat.parentCreator.name:''}}:{{chat.content}}<br /></span>
       	   		    
       	   		</li>
       	   		<li style="width: 10%;text-align: left;overflow-y: auto;word-wrap:break-word;width: 167px;padding-left: 10px;">
       	   			<span class="zan" v-for="(pariser,index) in item.praises" @click="dianzan(indexs,index)">{{pariser.member.name}}送出了一个赞! </span>          	   			
       	   		</li>
       	   		<li style="width: 10%;text-align: left;padding-top: 50px;padding-left: 40px;">
       	   			<div class="money">
       	   			 <Poptip
                      confirm
                      title="你确定想要删除该帖子?"
                      @on-ok="deleteok(indexs)"
                      @on-cancel="deletecancel">
                      <Button type="info"><Icon type="ios-trash-outline" />&nbsp;删除帖子</Button>
                       </Poptip>       	   			
       	   		   </div></li>
       	   	</ul>
       	   </div>     	  
       <div style="display:inline-block;margin-top: 10px;width: 100%;"><div style="float: right;margin-bottom: 10px;">
	<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
</div></div>
</div>
</div>
</div>
</template>
<script>
var sortx;
var groups=[];
var ids=[];
var requestuser=[];
var commentname=[];
 export default {
        data () {
            return {
            	baseUrl:"http://localhost:8080",
            	userMapping:{},
            	totalpage:0,
            	pageSize:10,
            	current:1,
            	value:'',
            	modal1: false,
            	modal2: false,
            	colgvalue:'',
            	comments:[],
            	tabphotourl:"../../assets/tmpphoto.jpg",
            	title:'',
            	ifs:false,
            	searchvalue:'',
            	trlist:[
            	   /*{
                    content:'故事从主人公漩涡鸣人的孤dasdsadasdsa儿生活开始，他的父母为了保护村子，将攻击村子九尾妖狐封印到了他体内，鸣人因此受尽了村人的冷落，只是拼命用各种恶作剧试图吸引大家的注意力。好在还是有依鲁卡老师的关心，鸣人的性格才没有变得扭曲，他总是干劲十足、非常乐观。 为了让更多的人认可自己，鸣人的目标是成为火影',            	   	          	   
            	    smallimage:'',
            	    praises:[],
            	    praisesnum:0,
            	    commentsnum:0,
            	    comments:[],
            	    files:[],
            	    status:'',
            	    name:'',
            	    type:'',
            	    creator:'张三',
            	    created:'2018-12-9'
            	  } */           	             	  
            ]
           	              	
        }
        },
        methods:{
        	
//      	//初始化加载图片
//      	//如果加载的图片的不够，补上暂无图片的提示图
//      	intphoto(){
//      		for (var a=0;a<this.trlist.length;a++) {
//      			this.trlist[a].smallimage=this.tabphotourl;
//      			while (this.trlist[a].files.length<6) {
//      			    this.trlist[a].files.push({path:this.tabphotourl})
//      		    } 
//      		}       	  
//      	},
        	//点击获取帖子的详情
        	colgdelite(indexs,index){
        		this.modal1 = true;
        		this.title='帖子内容详'
        		this.colgvalue=this.trlist[indexs].content
        	},
        	//点击获取评论详情
        	dianzan(indexs,index){
        		this.modal2 = true;
        		this.title='帖子评论详情'
        		for (var c=0;c<this.trlist[indexs].pariser.length;c++) {
        			this.comments.push({n:this.trlist[indexs].pariser[c].n+'送出了一个赞'})
        		}
        		
        	},
        	//获取帖子数据
        	  requestinformations() {
				var that = this;
				var arrs=[];
				var contents=this.searchvalue;
				var currents =this.current;
		        var pageSizes=this.pageSize;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/microblogs?pageNum='+currents+'&pageSize='+pageSizes+'&content='+contents
				}).then(function(res) {
					console.log(res)
					that.totalpage=res.data.pagination.total; 
					   //构造图片数据结构
					var arr = res.data.data.files;
					arr = !!arr?arr:[];
                    var fileMapping = {},
                    	oneBlogFilesMapping = {},
                    	bolgFiles,
                    	ai;
                   for(var i = 0; i < arr.length; i++){
                        ai= arr[i];
                        bolgFiles = oneBlogFilesMapping[ai.resourceId];
                        if(!bolgFiles){
                        	bolgFiles = [];
                        	oneBlogFilesMapping[ai.resourceId]=bolgFiles;
                        }
                        bolgFiles.push(ai);
                        fileMapping[ai.id]=ai;
              }	
               
					var userMapping=that.userMapping;
                   //构建点赞数据结构
                   var arrs = res.data.data.praises; 
                   arrs = !!arrs?arrs:[];   
                   var praiseMapping = {},
                    dests,
                    ais,
                    userTmp,
                    oneBlogPraisesMapping={};
                   for(var i = 0; i < arrs.length; i++){
                       ais = arrs[i];
                       userTmp = userMapping[ais.memberId];
                       if(!userTmp){
                       	userTmp = {
                       		id:ais.memberId,
                       		name:'',
                       		avatar:'',
                       		isLoaded:false
                       	}
                       	userMapping[ais.memberId]=userTmp;
                       }
                       ais["member"]=userTmp;
                       dests = oneBlogPraisesMapping[ais.microblogId];
                       if(!dests){
                       	dests = [];
                       	oneBlogPraisesMapping[ais.microblogId]=dests;
                       }
                       dests.push(ais);
                  }
                   
                   // 构建评论数据结构
                   var comments=res.data.data.comments;
                   comments=!!comments?comments:[];
                   var commentsMapping={},
                   commentTmp,           
                   oneBlogcommentMapping={},
                   oneBlogComments=[];
                   for(var m=0,len=comments.length;m<len;m++){
                   	commentTmp=comments[m];
                   	oneBlogComments.push(commentTmp);
                   	// 创建人处理
                   	userTmp = userMapping[commentTmp.creator];
                   	if(!userTmp){
                       	userTmp = {
                       		id:ais.memberId,
                       		name:'',
                       		avatar:'',
                       		isLoaded:false
                       	}
                       	userMapping[ais.memberId]=userTmp;
                   }
                   commentTmp.creator=userTmp;
                   // 回复人处理
                   if(commentTmp.parentCreator){
                   		userTmp = userMapping[commentTmp.parentCreator];
	                   	if(!userTmp){
	                       	userTmp = {
	                       		id:ais.memberId,
	                       		name:'',
	                       		avatar:'',
	                       		isLoaded:false
	                       	}
	                       	userMapping[commentTmp.parentCreator]=userTmp;
	                   }
	                   	commentTmp.parentCreator=userTmp;
                   }
                   }
                   	oneBlogcommentMapping[commentTmp.microblogId]=oneBlogComments;
              //将点赞集合在一个id里结束
             //循环将数据的三个属性集合在一个数据结构中
             var blogsArr = that.trlist;    //
             var blogFilesTmp;
             var blogsFromData = res.data.data.microblogs;
             var blogTmp;
             var blogPraiseArr;
             var creatorTmp;
             var blogCommentArr;
             for(var q=0;q<blogsFromData.length;q++){
             	// 博客处理图片
             	blogTmp = blogsFromData[q];
             	blogFilesTmp = oneBlogFilesMapping[blogTmp.id];
             	if(!blogFilesTmp){
             		blogFilesTmp=[];
             	}
             	blogTmp["files"]=blogFilesTmp;
             	// 处理点赞
             	blogPraiseArr = oneBlogPraisesMapping[blogTmp.id];
             	if(!blogPraiseArr){
             		blogPraiseArr =[];
             	}
             	blogTmp["praises"]=blogPraiseArr;
             	
             	// 处理评论
             	blogCommentArr = oneBlogcommentMapping[blogTmp.id];
             	if(!blogCommentArr){
             		blogCommentArr =[];
             	}
             	blogTmp["comments"]=blogCommentArr;
             	
             	// 创建人处理
             	creatorTmp = userMapping[blogTmp.creator];
             	if(!creatorTmp){
             		creatorTmp = {
                   		id:blogTmp.creator,
                   		name:'',
                   		avatar:'',
                   		isLoaded:false
                   	}
             		userMapping[blogTmp.creator]=creatorTmp;
             	}
             	blogTmp.creator = creatorTmp;
	             blogsArr.push(blogTmp);
	             blogTmp.praisesnum = blogPraiseArr.length;
	             blogTmp.commentsnum=blogCommentArr.length;
             }
             
              //加载点赞和评论数结束
              var toLoadUserIds = [];
              _.forEach(userMapping, function(value) {
				  if(!value.isLoaded){
				  	toLoadUserIds.push(value.id);
				  }
				});
               that.requestm(toLoadUserIds);
				})
				},
			 //请求会员的数据
			 requestm(toLoadUserIds){
			  var that=this;	
			  var idx=JSON.stringify(toLoadUserIds);	
			  console.log(idx)
			 	this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/users/baseinfos?ids='+encodeURI(idx)
				}).then(function(res) {
					   requestuser=res;
					    console.log(res)	
					    var userMapping = that.userMapping;
					    var userInfo;
					    var users = res.data.data;
					    var userTmp;
					   	for (let cb=0;cb<users.length;cb++) {
					    	userInfo = users[cb];
					    	userTmp = userMapping[userInfo.id];
					    	userTmp.name=userInfo.name;
					    	userTmp.avatar = userInfo.avatar;
					    	userTmp.isLoaded = true;
					   	}
				}) 				
				console.log(that.trlist)
				
				for (var i=0;i<that.trlist.length;i++) {
					 that.trlist[i].smallimage=''
				}
				for (var i=0;i<that.trlist.length;i++) {
					 that.trlist[i].smallimage=that.trlist[i].files[0].path;
					 that.trlist[i].avatar=that.trlist[i].creator.avatar
				}
			 },
			tabphoto(index,indexs){
				var trObj = this.trlist[indexs];
     	     trObj.smallimage=trObj.files[index].path;
     	     trObj.files.splice(1,0)
     	     console.log(this.trlist[indexs].smallimage)
        	},
        	//改变分页
        	changepage(index){
        	this.trlist=[];
            this.current=index;
            this.requestinformations();
            },
            searchpage(){
            this.requestinformations();
            },
             /*删除快享帖子*/
             deleteok (indexs) {
             	var that=this
             	this.axios({
                 method: 'delete',
                url: '/api/microblogs/'+this.trlist[indexs].id,
              }).then(function(){
              	that.open();
              })
            	this.trlist.splice(indexs,1)
            },
             deletecancel () {
                this.$Message.info('You click cancel');
            },
             //删除提示
             open (nodesc) {
                this.$Notice.open({
                    title: '删除提醒 ',
                    desc: nodesc ? '' : '已经成功删除帖子. '
                });
            }
            /*删除快享帖子*/
           ,
            ok () {
               this.$Message.info('Clicked ok');
             },
             cancel () {
                 this.$Message.info('Clicked cancel');
             }
         },
         mounted(){
         	this.requestinformations();
         	this.$Notice.config({
               top: 100,
               duration: 3
              });
         }
         }
 </script>

<style>
@import './quickshare.css';
</style>