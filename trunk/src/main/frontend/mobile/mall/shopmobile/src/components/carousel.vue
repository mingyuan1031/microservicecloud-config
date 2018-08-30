<template>
	<div id="carousel" style="width: 100% ;" >
		<div class="swiper-container sw"  >
			<div class="swiper-wrapper" >

				<div class="swiper-slide" style=" height: 9rem;" v-for="(item,index) in text" :style="{backgroundImage: 'url(' + item.img + ')' }"></div>
				<!--<div class="swiper-slide" ><img  style="width: 100%;" src="./../assets/img/index/ximi2s.png"/></div>
            
            <div class="swiper-slide" ><img style="width: 100%;" src="./../assets/img/index/redminote5.png"/></div>-->

			</div>

			<div class="swiper-pagination"></div>
		</div>

	</div>

</template>

<script>
	import Swiper from 'swiper'

	export default {
		props: ['text'],
		name: 'carousel',

		data() {
			return {
				
			}
		},

		methods: {

		},

		mounted: function() {
         
        new Swiper('.swiper-container.sw ', {
       pagination: {
    el: '.swiper-pagination',
  },
             observer:true,
        loop: true,
       autoplay: {
    delay: 3000,
    stopOnLastSlide: false,
    disableOnInteraction: false,
    },
        

		})
	}
	} 
</script>

<style>
.swiper-slide{
	
	 background-position: 50% 50%;
	 background-size: cover;
}

.swiper-container{
	background: white;
}
</style>