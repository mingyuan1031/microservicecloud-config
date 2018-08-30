<template>
  <div id="quickshare">
   
   
   <keep-alive>
    <router-view v-if="$route.meta.keepAlive">
        <!-- 这里是会被缓存的视图组件，比如 page1,page2 -->
    </router-view>
</keep-alive>
 
<router-view v-if="!$route.meta.keepAlive">
    <!-- 这里是不被缓存的视图组件，比如 page3 -->
</router-view>
   
   <!-- <router-view/>-->
  </div>
</template>

<script> 
	 
		import axios from 'axios'
	
	
	var cfg=window.lwxfPreload

		console.log(cfg)
	axios.defaults.headers['X-SID']=cfg.appcfg['X-SID'];
	axios.defaults.headers['PHPSESSIONID']=cfg.appcfg['PHPSESSIONID'];
	axios.defaults.headers['X-PHPSESSID']=cfg.appcfg['X-PHPSESSID'];
	axios.defaults.headers['X-C-ID']=cfg.preload['X-C-ID'];
	axios.defaults.headers['X-Requested-With']='XMLHttpRequest';
	axios.defaults.headers['ContentType']='application/json;charset=UTF-8';


export default {
  name: 'Quickshare',
  
  
  
  	methods:{
  		
  			axioses:function  () {
        
      var _this = this
						
				_this.axios({
				method: 'get',
				url: '/api/sys/touch',
			
			}).then(function(res) {
				
				console.log(res.data)
                
		

			}).catch(function(err) {
				console.log(err)
				
				

			})
				
        
  		},
  	
 
  		
  		
  	} ,
  
  mounted: function() {
  	
  	
  	
			var size = (document.documentElement.clientWidth / 360) * 20 + 'px';
			document.documentElement.style.fontSize = size
			
			setInterval(() => {
					this.axioses()
				
				}, 1500000)
			
		},
		
		
		
		

updated:function  () {
	
	document.documentElement.scrollTop=0
	document.body.scrollTop=0
	
			
	
}

,

watch: {
  '$route' (to, from) {
    const toDepth = to.path.split('/').length
    const fromDepth = from.path.split('/').length
    this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'
  }
}
}
</script>

<style>

</style>


