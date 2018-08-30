<template>
<div id="all" class="paddingBox">
          <div class="all-title">
          	<span></span>
          	<p>
          		<small>{{alltitle}}</small>
          	</p>
          </div>
          
          <div class="all-good">
          	<ul>
          		<li v-for="(item , index) in goodList">
          			<a>
          			<div class="item imgshow" >
          				<img :src="item.image" :title="item.name" :alt="item.name">
          			</div>
          			<div class="item allgoodmsg">
          				<p><small>红田</small>{{item.name}}</p>
          				<span>{{item.title}}</span>
          				<div class="allgoodM">
          					<b>￥{{item.nowPrice}}</b><del v-show='item.del!=""' >￥{{item.beginPrice}}</del><small>已销：{{item.sumSales}}</small>
          				</div>
          			</div>
          			</a>
          		</li>
          		
          	
          	</ul>
          	
          	<!--点击加载更多-s-->
          	<div class="allmore">
          		<div class="allclickmore" v-show="alldata>showdata">
          			<span @click="alertopen(5)">点击加载更多...</span>
          		</div>
          		<div  class="allnonemore"  v-show="showdata>=alldata">
          			没有更多了...
          		</div>
          	</div>
          	
          	<!--点击加载更多-s-->
          	
          	
          </div>
		
			

	
			<!--底部-->
	
			
			<support></support>
			
			
		<!--alert-s-->
			<alertopen   :text=tip  @child-say="openoff"  @child-none="openone"></alertopen>
			
			<!--alert-end-->
             
		</div>
</template>

<script>
	
	
	import  support from './../components/support.vue'
	import  alert from './../components/alert.vue'
	export default {
		name: 'all', 
		
		data() {
 
			return {
        alltitle:"全部商品"       ,
               tip:{
				"alertopacity": "0",
				"Alert": false,
				"alertshow":2,
				"alwarning": "",
				},
			
				nowdata:6,
				alldata:30,
				showdata:6,
				goodList:[{
					"image": require('./../assets/img/index/banner2.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					 "del":"339",
				},{
					"image": require('./../assets/img/index/banner3.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					  "del":""
				},{
					"image": require('./../assets/img/index/banner2.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					  "del":""
				},{
					"image":require('./../assets/img/index/banner3.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					  "del":""
				},{
					"image": require('./../assets/img/index/banner2.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					  "del":""
					 
				},{
					"image": require('./../assets/img/index/banner3.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
					  "del":""
				},
				
				],
				
				floorFixedI:[
                {
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
				
				flooBox:[{
					"title": "商品类别",

					"fMulist": [{
							"fMulistname": "商品分类",
							"fMuhref": "6.44大屏'，5300mAh大电量",
							
						},
					]
				},{
					"title": "购物车",

					"fMulist": [
					]
				},{
					"title": "个人中心",

					"fMulist": [
					]
				},
				]

			}

		},

		/**/

		components: { 
		
		
			support: support,
			alertopen:alert,
		},
		
		

		methods: {
			append:function  () {
					if (this.showdata>=this.alldata) {
						this.showdata=this.alldata
					}
					this.showdata+=6
					 
					for (var i=0; i<this.showdata; i++) {
						 	var obj={
			 		
			 		"image": require('./../assets/img/index/banner3.jpg'),
					 "name": "普兰特丨郑州红田家居 免费上门测量安装（预",
					  "title": "温控感应 / 独立安全门 / 750℃阻燃 / 3C安全认证​手机",
					 "price":336,
			 	}
			 	
			 	this.goodList.push(obj)
					}
					
				},
				
				   /*弹框*/
				  
				  
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
							this.alertdb(ind, word)
					}
					
					this.append()
				
				}, 2000)
				 
				 

			},
			
			open: function(index, tex,type,ind, word) {
     
				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

			
				

			},
			
			alertdb: function(index, tex ) {

				this.tip.alertopacity = 3
				this.tip.alertshow = index
				this.tip.Alert=true
				this.tip.alwarning = tex

				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				}, 2000)
				
				

			},
				  
           alertopen:function  () {
           	
         
            this.alertn(5,)
            
            
           },
           
           openoff:function  () {
           	
            this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				
           	
           },
           
           openone:function  (somedata) {
           	
           	  this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
           	
           }
		
			
		},
      mounted: function() {
              
              if(this.$route.params.name==""||this.$route.params.name==null){
              	  this.alltitle="全部商品"
              }else{
              	 this.alltitle=this.$route.params.name
              };

          
            console.log(this.$route.params.name)
             
             	var _this = this

			_this.axios({
				method: 'get',

				
					url: '/api/goods?name='+this.alltitle+''

			}).then(function(res) { 
				console.log(res.data)
				
				_this.goodList=res.data.data
	console.log(_this.goodList)
         
 
			}).catch(function(err) {
				console.log(err)
             
			}) 
		
		   
		

		
      
   
		}
		/**/

	}
</script>

<style src='./../assets/css/all.css'></style>
<style>
</style>