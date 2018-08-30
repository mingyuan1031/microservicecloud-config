<template>
	<div id="index" class="paddingBox">

		<!--	<div class="inSearch">
				<router-link to="/seek">
				   <span class="inSinput" >搜索商品</span>
			    </router-link>
				<span class="inSicon">
	       		<i class="layui-icon layui-icon-face-smile">&#xe615;</i>
	       </span>
			</div>-->
			<!--轮播图-->
			<banner :text=bannerList></banner>
			<!--轮播图-end-->

			<!--首页icon-->
			<div class="inIcon"> 
				<ul>
					<li v-for="(item, index) in Hicon" @click="iconhref(item)">
				<!-- <router-link :to="{name:item.href,params:{id:item.iconName}}"  >-->
							<h3>
	   	  			 <img :src="item.image"/>
	   	  		     </h3>
							<p>{{item.name}}</p>
					<!--</router-link>-->

					</li>
                </ul>
			</div>
			<!--icon-end-->
			<!--推荐-->
			<div class="inRecommend">
				<div class="inRecommend-title">
					小编推荐/ Ta的生活灵感
				</div>
				
				<div class="inRecommend-center">
					<div class="inRecommend-singleton"  v-for="(item,index) in advertising" v-show="item.place==1">
						<a href="#">
							<img :src="item.path"/>
						</a>
					</div>
					<div class="inRecommend-double">
						<ul>
						  <li v-for="(item,index) in advertising" v-show="item.place==2">
							  <a href="#">
								<img :src="item.path"/>
							  </a>
						  </li>
					    </ul>
					</div>
					
				<!--	<div class="inRecommend-singleton">
						<a href="#">
							<img :src="inBanner[3]"/>
						</a>
					</div>-->
					
				</div>
				
			</div> 
			<!--推荐-end-->
			
			<!--商品-s-->
			<div class="goodBox">
				<ul>
					<!--@click="goodhref(item.goodsId)"-->
					<li v-for="(item, index) in goodList" >
				
							 <router-link :to="{name:'gooditem',params:{gooditemid:item.goodsId}}">
							
							<h3 :style="{backgroundImage: 'url(' + item.path + ')' }">
								<!--<img :src="item."/>-->
							</h3>
							<p style="padding: 0.3rem 0.5rem;">
								{{item.name}}
							</p>
							<div class="goodBoxbut">
								<small>￥{{item.price}}</small>							
								<b>购买</b>
								<del  v-show='item.originalprice!=""'>￥{{item.originalprice}}</del>
							</div>
						</router-link >
					</li>
					
					<!--<li>
						<a>
							<h3>
								<img src="img/index/banner2.jpg"/>
							</h3>
							<p>普兰特丨郑州红田家居 免费上门测量安装（预</p>
							<div class="goodBoxbut"></div>
						</a>
					</li>-->
				</ul>
			</div>
			
			
			<!--商品-end-->
			
			
			<!--底部信息-s-->
			 <div class="inBottom">
			 	<div class="inBone">
			 		<img src="../assets/img/index/inbone.png"/>
			 	</div>
			 	
			 	<div class="inBoicon">
			 		<ul>
			 			<li>
			 				<h3>
			 					<img src="../assets/img/index/inboicon1.png"/>
			 				</h3>
			 			
			 			</li>
			 			
			 			<li>
			 				<h3>
			 					<img src="../assets/img/index/inboicon2.png"/>
			 				</h3>
			 				
			 			</li>
			 			
			 			<li>
			 				<h3>
			 					<img src="../assets/img/index/inboicon3.png"/>
			 				</h3>
			 			
			 			</li>
			 			
			 			<li>
			 				<h3>
			 					<img src="../assets/img/index/inboicon4.png"/>
			 				</h3>
			 				
			 			</li>
			 			
			 			
			 		</ul>
			 	</div>
			 	
			 	
			 	<div class="inBocontact">
			 		<h3 class="inBocontact-title">客服电话</h3>
			 		<p>0371-8888-0863</p>
			 		<span>客服在线时间：10：00-20:00</span>
			 	</div>
			 	
			 </div>
			<!--底部信息-end-->
			<!--底部-->
           <!--技术支持-->
           <support></support>
			<floors></floors>
			
	
			
			
			<!--底部-end-->
		</div>
</template>

<script>
	import carousel from './../components/carousel.vue'
	import  support from './../components/support.vue'
	import  floor from './../components/bottom.vue'
	import axios from 'axios' 


	export default {
		name: 'index', 
		
		data() {
 
			return {
               inSinputpla:"搜索商品",
				titkle:"222",
				bannerList: [
				  {"img": "/p/carousel/45n5xzqejw8z.jpg"},
				  {"img":"/p/carousel/45n5xzqejw8w.jpg"},
				  {"img": "/p/carousel/45n5xzqejw8x.jpg"},
				 
				 
				],
				
				advertising:[],
				
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
				Hicon: [],
				
				inBanner:[
			
				],
				
				goodList:[
				],
				
			
				

			}

		},

		/**/

		components: { 
		
			banner: carousel,
			support: support,
			floors:floor,
		},

		methods: {
		 iconhref:function(item){
   	     var href=item.url
   	    /* item.isOuterLink = true;*/
   	     if(item.outerLink){
   	     	window.location='/'+href 
   	     	return;
   	     }
   	     
   	     
   	     
   	    if(href.indexOf("?")== -1){
   	    	  this.$router.push({ name: href})
   	    }else{
   	    	 
   	    	  
   	    	   var urldata=href.split("?")[0]
   	     var id=href.split("?id=")[1].split("&")[0]
   	     var name=href.split("name=")[1]
   	    this.$router.push({ name: urldata, query: { id:id , name:name}})
   	    	  
   	    }
   	    
   	   
   	
   },
   
   goodhref:function  (id) {
   	
   	 this.$router.push({ name: "gooditem", query: { gooditemid:id}})
   	
   },
   
		},
      mounted: function() {

     

  function uuid(len, radix) {
			var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
			var uuid = [];
			radix = radix || chars.length;

			for(var i = 0; i < len; i++) {
					uuid[i] = chars[0 | Math.random() * radix]
			}

			var objdate = new Date()
			var gethours = objdate.getHours()
			var getminutes = objdate.getMinutes()
			var getseconds = objdate.getSeconds()
			if (gethours < 10) gethours = "0" + gethours;
			if (getminutes < 10) getminutes = "0" + getminutes;
			if (getseconds < 10) getseconds = "0" + getseconds;
			var datex =  ''+gethours+''+getminutes+''+getseconds+''
          
            var uid=uuid.join('').toString()
              uid+=datex.toString()
             
             console.log(uid)
          
		}

   //图标跳转页面
   
  


     //  uuid(8, 16)

		


/* this.$http.get('https://192.168.1.4:8080/api/demos')
    .then((response) => {
      console.log(response.data)
    })
    .catch(function(response) {
      console.log(response)
    })
 */
        /* this.$http.jsonp('https://192.168.1.4:8080/api/').then(function(res) {
            console.log(res.data)
             
          })*/ 
          
       /*  this.$http.get('https://192.168.1.4:8080/api/demos').then((res)=>{
             
                console.log(res)
            })*/
        
         
         
/*         var instance = axios.create({ 
  baseURL: 'https://192.168.1.4:8080',
  timeout: 1000,
  headers: {'X-Custom-Header': 'foobar'}
});
         */ 
/*axios.get('https://192.168.1.4:8080/api/demos')
   .then(function(res){
          console.log(res)
        })
    .catch(function(err){
          console.log(err)
          console.log('nono') 
       });*/
      
       /* var _this=this
       _this.axios({
		    method: 'get',
		    headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    	'ContentType':'application/json;charset=UTF-8'
		    	},
		    url: '/api/demos'
		  }) 
		  .then(function (response) {
		    console.log(response.data.pagination.pageSize)
		    
		   _this.titkle=response.data.pagination
		   
		  
		    
		  })
		  .catch(function (error) {
		    console.log(error)
		    console.log('nono')
		  })*/
		 

		//icon模块
		var _this=this
		
		  var cfg=window.lwxfPreload
		  
		  
		   this.Hicon=cfg.preload.storeHomeNavs
		
		  if(cfg.preload.storeHomeNavs.length==0){
		  		_this.axios({
		 	method:'get',
		
		 	
		 	url: '/api/homenavs'
		 	
		 }).then(function  (res) {
		 	console.log(res.data)
		    
		    _this.Hicon=res.data.data
		   //_this.titkle=res.data.pagination
		 	
		 }).catch(function  (err) {
		 	console.log(err) 
		 
		 })
		  }
		
	
		 
		 
		
	
	       
		
		 _this.axios({
		 	method:'get',
		    headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    	'ContentType':'application/json;charset=UTF-8'
		    	},
		 	
		 	url: '/api/goods/home'
		 	
		 }).then(function  (res) {
		 	console.log(res.data)
		    
		   _this.goodList=res.data.data
		 	
		 }).catch(function  (err) {
		 	console.log(err) 
		 
		 })
		 
		 
		 //广告位
		  _this.axios({
		 	method:'get',
		   /* headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    	'ContentType':'application/json;charset=UTF-8'
		    	},
		 	*/
		 	url: '/api/advertisings'
		 	
		 }).then(function  (res) {
		 	
		
		    
		_this.advertising=res.data.data
		 	
		 }).catch(function  (err) {
		 	console.log(err) 
		 
		 })
      
	}
		

	}
</script>

<style src='./../assets/css/indexstyle.css'></style>

<style>
	.goodBoxbut del{
	font-size: 0.5rem; color: #AAAAAA; float: right; padding-right: 0.5rem; display: block; height: 1rem; line-height: 1rem;
}
.swiper-wrapper{
	height: 9rem;
}

</style>