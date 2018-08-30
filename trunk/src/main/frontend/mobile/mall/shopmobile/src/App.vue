<template>
  <div id="app">
   
   
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

		var cacse = document.getElementById("lwxf_preload")
  	var pfcfg = cacse.innerText.replace(/_e@gt@e_/g, '>').replace(/_e@lt@e_/g, '<')
	var cfg = JSON.parse(pfcfg.trim());
	
	/*console.log(cfg.appcfg['X-SID'])*/
	
	axios.defaults.headers['X-SID']=cfg.appcfg['X-SID']
	axios.defaults.headers['X-PHPSESSID']=cfg.appcfg['X-PHPSESSID']
	axios.defaults.headers['X-C-ID']=cfg.appcfg['X-C-ID']
	axios.defaults.headers['X-Requested-With']='XMLHttpRequest'
	axios.defaults.headers['ContentType']='application/json;charset=UTF-8'
	
	
	
export default {
  name: 'App',
  mounted: function() {
  	
  	
  	
			var size = (document.documentElement.clientWidth / 360) * 20 + 'px';
			document.documentElement.style.fontSize = size
			
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
