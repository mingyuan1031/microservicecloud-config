<template>
	<div id="pinpai">
		  <img class="pinpaiImg" :src="pinpai_aUrl">
		  <img class="pinpaiImg" :src="pinpai_bUrl">
		  <img class="pinpaiImg" :src="pinpai_cUrl">
		  <img class="pinpaiImg" :src="pinpai_dUrl">
		  <img class="pinpaiImg" :src="pinpai_eUrl">
		  <img class="pinpaiImg" :src="pinpai_fUrl">
		 
	</div>
</template>

<script>
	  export default {
		name: 'pinpai',
		data() {
			return {
				pinpai_aUrl:require('../../assets/img/brand/1 (1).png'),
				pinpai_bUrl:require('../../assets/img/brand/1 (2).png'),
				pinpai_cUrl:require('../../assets/img/brand/1 (3).png'),
				pinpai_dUrl:require('../../assets/img/brand/1 (4).png'),
				pinpai_eUrl:require('../../assets/img/brand/1 (5).png'), 
				pinpai_fUrl:require('../../assets/img/brand/1 (6).png'), 
		       }

		  },
		  methods: {
		     
         },

	}

</script>

<style >
*{margin: 0px; padding: 0px; margin: 0px; list-style: none; }
body{background-color:#f8f8f8; }
.pinpaiImg{width:100%; display: block;}
</style>