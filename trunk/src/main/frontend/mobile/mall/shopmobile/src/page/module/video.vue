<template>
	<div id="videoshow" class="video">
			<div class="Vitem" v-for="(item, index) in videplist">
				<div class="Vimg" >
					<div class="Vimg_img"  @click="play(index)" :style="{backgroundImage: 'url(' + item.cover + ')' }">
					
					</div>
					
					<video id="video" width="100%" style="" controls="" >
						  <source :src="item.path" type="video/mp4">
						您的浏览器不支持Video标签。
						</video>
					<div class="Vcenter" >
					<p>{{item.name}}</p>
					
				</div>
				</div>
				
				
				
			</div>
			
		<!--	<div>
				
			</div>-->
			
			
		</div>
		
</template>

<script>
	
	export default {
		
		name: 'videoshow',

		data() {
			return {
					 videplist: [
				]
			}
		},
 
		methods: {
            	play:function (index) {
						
					 var videos=document.getElementsByTagName('video')
					 
					 for (var i=0; i<videos.length; i++) {
					 	
					 	videos[i].pause()
					 	
					 }
					 
					 videos[index].play()
					 
					 
					 
					 console.log(videos)
            		  // e.target.()
            	},
		},

		mounted: function() {
       
        
       	var _this = this
		_this.axios({
						method: 'get',
						url: '/api/videofiles',//
                         
						
					}).then(function(res) {
						console.log(res.data)
						
						if(res.data.error){
							console.log(res.data.error.message)
						}
						
						
					_this.videplist=res.data.data
					
						
						
							
							
					
						
							 
					}).catch(function(err) {
						console.log(err)
						
						
					})

       
       
       
       
	}
	} 
	
	
</script>

<style src='./../../assets/css/video.css'></style>
<style>
	.Vimg{
		position: relative;
	}
	
	.Vimg_img{
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
		height: 100%;
		z-index: 1;
		overflow: hidden;
		 background-position: 50% 50%;
	    background-size: cover;
	    
	}
	
	.Vimg_img img{
		display: block;
		width: 100%;
		min-height: 100%;
		
	}
</style>