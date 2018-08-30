<template>
	<div id="all" class="paddingBox">
		
		
		
		<div class="all-title">
			<span></span>
			<p>
				<small @click="typeshowdata">{{alltitle}}</small>
			</p>
		</div>

		<div class="all-good">
			
			<div class="tionone" v-show="goodList.length==0">
				没有相关搜索结果
			</div>
			
			<ul>
				<li v-for="(item , index) in goodList" >
					 <router-link :to="{name:'gooditem',params:{gooditemid:item.goodsId}}"> 
						<div class="item imgshow">
							<img :src="item.path" :title="item.name" :alt="item.name">
						</div>
						<div class="item allgoodmsg">
							<p><small v-show="item.brandname!=''">{{item.brandname}}</small>{{item.name}}</p>
							<div class="tags" >
								<span v-show=" tagesData[index]!='null'" v-for="(itemdb , indbex) in tagesData[index]" >{{itemdb.name}}<small>/</small></span>
							</div>
							<div class="allgoodM">
								<b>￥{{item.price}}</b><del>￥{{item.originalprice}}</del><small>已销：{{item.sumSales}}件</small>
							</div>
						</div>
					 </router-link >
				</li>

			</ul>
			
			
		

			<!--点击加载更多-s-->
			<!--<div class="allmore">
				<div class="allclickmore" >
					<span @click="alertopen(5)">点击加载更多...</span>
				</div>
				<div class="allnonemore" v-show="showdata>=alldata">
					没有更多了...
				</div>
			</div>-->

			<!--点击加载更多-s-->

        <!--未找到匹配项-s-->
        
        <div class="nonwshow" v-show="nonwshow==true">
			没有你想要搜索的商品
		</div>
      <!--未找到匹配项-e-->
		</div>


        <div class="typeclass" v-show="typeshow" >
        	<div class="typeclassbg" @click="typeshowdata"></div>
	         <div class="typeclasscenter">
	         	
	         	<p>选择分类</p>
	         	
	         	<ul id="getul">
	         		<li v-for="(item,index) in typeData"  @click="picktype(index)">
	         			<span>{{item.name}}</span>
	         		</li>
	         	</ul>
	         </div>
       </div>

		<!--底部-->

	<!--	<support></support>-->

		<!--alert-s-->
		<alertopen :text=tip @child-say="openoff" @child-none="openone"></alertopen>

		<!--alert-end-->

	</div>
</template>

<script>
	import support from './../components/support.vue'
	import alert from './../components/alert.vue'
	export default {
		name: 'all',

		data() {

			return {
				brandscache:{},
				nonwshow:false,
				alltitle: "全部商品",
				tip: {
					"alertopacity": "0",
					"Alert": false,
					"alertshow": 2,
					"alwarning": "",
				},
               typeCache:[],
               typeData:[],
               typeid:"",
				nowdata: 0,
				alldata: 0,
				showdata: 6,
				goodList: [],
				tagesData:[],
                islongo:false,
                pagesize:0,
                allpagesize:2,
                typeshow:false,
				floorFixedI: [{
						"type": false,

					},

					{
						"type": false,

					},

					{
						"type": false,

					},

					{
						"type": false,

					},
				],

				flooBox: [{
					"title": "商品类别",

					"fMulist": [{
						"fMulistname": "商品分类",
						"fMuhref": "6.44大屏'，5300mAh大电量",

					}, ]
				}, {
					"title": "购物车",

					"fMulist": []
				}, {
					"title": "个人中心",

					"fMulist": []
				}, ]

			}

		},

		/**/

		components: {

			support: support,
			alertopen: alert,
		},

		methods: {
			append: function() {
				if(this.showdata >= this.alldata) {
					this.showdata = this.alldata
				}
				this.showdata += 6

				for(var i = 0; i < this.showdata; i++) {
					var obj = {

						"image": require('./../assets/img/index/banner3.jpg'),
						"name": "普兰特丨郑州红田家居 免费上门测量安装（预",
						"title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
						"price": 336,
					}

					this.goodList.push(obj)
				}

			},

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

			open: function(index, tex, type, ind, word) {

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

			alertopen: function() {

				this.alertn(5, )

			},

			openoff: function() {

				this.tip.alertopacity = 0
				this.tip.alwarning = ""
				this.tip.alertshow = 0
				this.tip.Alert = false

			},

			openone: function(somedata) {

				this.tip.alertopacity = 0
				this.tip.alwarning = ""
				this.tip.alertshow = 0
				this.tip.Alert = false

			},
			
			typeshowdata:function(){
				this.typeshow=!this.typeshow
			},
			
			
			dataNow:function(){
				
			/*console.log(this.$route.params.name)*/
	       this.alertn(5)
			var _this = this

               this.pagesize++

					if(this.pagesize > this.allpagesize) {
						this.pagesize = this.allpagesize
						this.islongo = false
						this.alertn(4, "没有更多了")
						this.timeout()
						return false
					}

                 var urldata=''
                  
                  if(this.typeid=="complete"){
                  	 urldata='/api/goods?pageNum='+this.pagesize+'&pageSize=8&disabled=false'
                  }else{
                  	 urldata='/api/goods?goodsTypeId=' + this.typeid + '&pageNum='+this.pagesize+'&pageSize=8&disabled=false'
                  }


                                   
			_this.axios({
				method: 'get', 

				url: urldata

			}).then(function(res) {
				
				_this.allpagesize=res.data.data.goods.pagination.pages
				_this.islongo = false 
				var tag=res.data.data.tags
				var datas=res.data.data.goods.rows
			   console.log(datas)
			   
			   
				for(var i=0; i<datas.length ;i++){
					datas[i].brandname=""
					
					
					if(!datas[i].brandId){
					   datas[i].brandname=""
					}else{
							datas[i].brandname= _this.brandscache[datas[i].brandId].name
					}
					
					_this.goodList.push( datas[i])
					
				}
			
            
                
                for(var n=0; n<tag.length ;n++){
                	
                	
                	
					
				_this.tagesData.push(tag[n])

				}
				
		        /*  console.log(_this.tagesData)*/
					
				    _this.setalert()

			}).catch(function(err) { 
				console.log(err)
				_this.alertn(2,"错误，稍后重试")
                  _this.timeout() 
			})

			},
			
			picktype:function(index){
				
				
				var divname = document.querySelector("#getul");
              var getli = divname.querySelectorAll('li');
              
              for(var i=0; i<getli.length; i++){
              	getli[i].classList.remove("active")
              }
				
				var eve=event.target
				eve.parentNode.classList.add("active")
				
				this.alltitle=this.typeData[index].name
				
				this.typeid=this.typeData[index].id
				
			   	this.goodList=[]
  
				this.pagesize=0
				this.allpagesize=2
				this.dataNow()
				
				this.typeshow=false
				
			},
			
			
			goodtype:function(){
				  var _this=this
		 
		_this.axios({
		 	method:'get',
		
		 	
		 	url: '/api/goodstypes/all'
		 	
		 }).then(function  (res) {
		 	
		 	
		 	
		 	var goodparentshowdatas=[]
		 	var typecache=_this.typeCache
		  
		 	var goodstypedatas=res.data.data.goodsType
		 	var  showtype;
		 	var goodsw;
		 	var goodstyshowarry=[]
		 	
		 	for(var i=0 ; i<goodstypedatas.length ;i++){
		 		
		 	    
		 		var goodstyshowid=goodstypedatas[i].parentId
		 	
		 	         if(goodstyshowid){
		 				goodstyshowarry.push(goodstypedatas[i])
		 			}
		 		      
		 	
		 		
		 	}
		 	
		 	
		 	
		 	for(var m=0 ; m<goodstyshowarry.length; m++){
		 	var showDa=[]
		 	var pid=goodstyshowarry[m].parentId
		    var showid=typecache[pid]
		      
		   
		    
		      if(!showid){
		      	showDa.push(goodstyshowarry[m])
		      	  // 
		      	typecache[pid]=showDa
		       
		      }else{
		      	typecache[pid].push(goodstyshowarry[m])
		      }
		      
		      	
		 
		 
		 		
		 		
		 		
		 		
		 		
		 	}
		 	
		 	
		 		
		 	
		 	
		  	for(var n=0 ; n<goodstypedatas.length ;n++){
		  		  showtype=goodstypedatas[n]
		  		  
		  		  if(!showtype.parentId){
		  		  	showtype.types=[]
		  		  	if(!!typecache[showtype.id]){
		  		  	  showtype.types=typecache[showtype.id]
		  		  	 
		  		  	}
		  		  	
		  		  	_this.typeData.push(showtype)
		  		  
		  		  }
		  		  
		  		  
		  		  
		  		
		  		  
		  		
		  	}
		 	var obj={
		 		disabled:false,
				id:"complete",
				name:"全部"
				
		 	}
		 	
		 	_this.typeData.unshift(obj)
		 	
		
		 	
		 	
		 }).catch(function  (err) {
		 	console.log(err) 
		 
		 })
		 	
		 
		 	
		  	
		   
			}

           

		},
		mounted: function() {
         this.alertn(5)
         
         
        this.typeid=this.$route.query.id
        this.alltitle = this.$route.query.name
         
        this.goodtype()
         
         
         
			

           var _this = this
			window.onscroll = function() {

				var scrtop = document.documentElement.scrollTop || document.body.scrollTop;
				var wheight = document.documentElement.clientHeight || document.body.clientHeight;
				var scrheight = document.documentElement.scrollHeight || document.body.scrollHeight;
				

				if(scrtop + wheight == scrheight && _this.islongo == false) {
					_this.islongo = true //正在请求值改为真，请求完成之后改为假
					_this.alertn(5)


					_this.dataNow()
				}

			}
			
			   //查询全部品牌信息
	   
	  
	   
	       _this.axios({
				method: 'get', 

				url: '/api/brands'

			}).then(function(res) {
				_this.alertn(5)
				    var brandsCache=_this.brandscache
			/*	console.log(res.data.data)*/
				var brandsdata=res.data.data
			
				
				
				var brandshow;
				var brandsid; 
				for(var i=0 ; i<brandsdata.length ;i++){
					
					 brandsid=brandsdata[i].id
					
						brandshow = brandsCache[brandsid];
						
						if(!brandshow){
							 brandshow={
								content:brandsdata[i].content,
								id:brandsdata[i].id,
								disabled:brandsdata[i].disabled,
								name:brandsdata[i].name,
							}
						}
					 brandsCache[brandsid] = brandshow;

				   
					
				}
				
			  _this.dataNow()
					

			}).catch(function(err) { 
				console.log(err)
				
			})
		


		}
		
	
		
		
		
		/**/

	}
</script>


<style>
.nonwshow{
	width: 100%;
	text-align: center;
	font-size: 0.6rem;
	color: #AAAAAA;
	line-height: 2rem;
}


.inSearch .inSinput{
	background: white;
}

.tionone{
	text-align: center;
	font-size: 0.6rem;
	color: #333333;
	padding: 0.5rem 0rem;
}

.typeclass{
	position: fixed;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
	
	z-index: 9;
}

.typeclassbg{
	position: relative;
	width: 100%;
	height: 100%;
	
	z-index: 10;
}

.typeclasscenter{
	position: absolute;
	top: 0px;
	left: 0px;
	z-index: 11;
	background: white;
	padding: 0.5rem 0rem;
	width: 100%;
	padding-bottom: 1rem;
	box-shadow: 1px 1px 50px rgba(0,0,0,.3);
	height: 15rem;
	overflow: auto;
	padding: 0.5rem 0rem;
	
}

.typeclasscenter p{
	display: block;
	font-size: 0.7rem;
	line-height: 2rem;
	text-align: center;
}

.typeclasscenter ul{
	display: inline-block;
	width: 100%;
}

.typeclasscenter ul li{
	float: left;
	width: 25%;
	text-align: center;
	line-height: 1rem;
	font-size: 0.7rem;
	
}

.typeclasscenter ul li span{
	display: block;
	width:3rem ;
	height: 1rem;
	line-height: 1rem;
	margin: 0px auto;
	border: solid 1px #DDDDDD;
	overflow: hidden;
	margin-bottom: 0.5rem;
	text-overflow: ellipsis;
}

.typeclasscenter ul li.active span{
	color: orangered;
	border: solid 1px orangered;
}

</style>

<style src='./../assets/css/all.css'></style>