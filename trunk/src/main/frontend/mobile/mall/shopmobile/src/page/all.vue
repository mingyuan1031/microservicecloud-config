<template>
	<div id="all" class="paddingBox">
		
		
		
		<div class="all-title">
			<span></span>
			<p>
				<small>{{alltitle}}</small>
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

				nowdata: 0,
				alldata: 0,
				showdata: 6,
				goodList: [],
				tagesData:[],
                islongo:false,
                pagesize:0,
                allpagesize:2,
                
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
			
			dataNow:function(){
				this.alltitle = this.$route.params.name
			/*console.log(this.$route.params.name)*/

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
                  
                  if(this.$route.params.name=="所有"){
                  	 urldata='/api/goods?pageNum='+this.pagesize+'&pageSize=8&disabled=false'
                  }else{
                  	 urldata='/api/goods?name=' + this.$route.params.name + '&pageNum='+this.pagesize+'&pageSize=8&disabled=false'
                  }



			_this.axios({
				method: 'get', 

				url: urldata

			}).then(function(res) {
				console.log(res.data.data)
				_this.allpagesize=res.data.data.goods.pagination.pages
				_this.islongo = false 
				var tag=res.data.data.tags
				var datas=res.data.data.goods.rows
			   
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

			}

		},
		mounted: function() {
         this.alertn(5)
			

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


</style>

<style src='./../assets/css/all.css'></style>