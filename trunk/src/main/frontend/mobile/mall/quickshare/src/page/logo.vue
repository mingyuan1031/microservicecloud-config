<template>
	
	<div id="logo">
		<p @click="pos">
		登录
	</p>
	</div>
	
	
</template>

<script>
	
	import axios from 'axios'
	
		export default {
		
		name: 'logo',

		data() {
			return {
				
				
			}
		},

		methods: {
            	pos:function  () {
            		
                
                
                
                var _this=this
		 
		/*_this.axios({
		 	method:'post',
		 	headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    	'ContentType':'application/json;charset=UTF-8'
		    	},
		 	
		 	url: '/api/users/login',
		 	data:{
			       
			         loginName: "admin",
                      password: password
			    }
		 	
		 }).then(function  (res) {
		 	
		 	
		 	alert("成功了"+res.data)
		    
		   //_this.titkle=res.data.pagination
		 	
		 }).catch(function  (err) {
		 	console.log(err) 
		    console.log('不对不对')
		 })
                
                
            	
		},*/
		
		 var user = "admin";
         var passworda = "e6e061838856bf47e1de730719fb2609";
                if(!user){
                    return;
                }
		
		var _this = this
						
				_this.axios({
				method: 'post',
				url: '/api/users/login',
				headers: {
		    	'X-Requested-With':'XMLHttpRequest',
		    	'ContentType':'application/json;charset=UTF-8'
		    	},
               data:{
			        
			        loginName: "admin",
                    password:  "e6e061838856bf47e1de730719fb2609"
			    }
			}).then(function(res) {
				console.log(res.data)
               alert("成功了"+res.data)

			}).catch(function(err) {
				console.log(err)
				
				

			})
				
			}
		
		
		},

		mounted: function() {
       
	}
	} 
	
</script>

<style>
</style>