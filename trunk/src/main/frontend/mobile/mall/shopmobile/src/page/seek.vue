<template>
	<div id="seek" class="paddingBox ">
			<!--搜索框-->
			<div class="inSearch">
				<input type="text" name="" id="" value="" v-model="seekinput" placeholder="搜索商品" class="inSinput">
				<span class="inSiconseek">  <!---->
					<!-- <router-link :to="">-->
					<i class="layui-icon layui-icon-face-smile"></i>
					<!-- </router-link>-->
						
					</span>
				<small  @click="sub" class="inSiconsmall">
					搜索
			   </small>
			</div>

			<div class="inSearchlist">
				<ul>
					<li v-for="(item, index) in closk">
						
						<p><a style="display: block;" @click="adall">{{item}}</a><span @click="clear(index)"><i class="layui-icon">&#x1006;</i></span></p>
					</li>
				</ul>
			</div>

			<!--搜索款-->
			
			<div class="qk" @click="qklsjl">
				清空历史记录
			</div>

		</div>
</template>

<script>
	export default {
		
		name: 'seek',

		data() {
			return {
			seekinput: "",
			closk: [],
			}
		},

		methods: {
            	qklsjl:function  () {
				
				this.closk=[]
					/*清除后重新存储*/
					
					/*读-e*/
				var bg = [];
				var bgcol;
				if(window.localStorage.bgcolor) {
					var bgcol = JSON.parse(window.localStorage.bgcolor)
				}

				if(bgcol && bgcol.length > 0) {
					var bg = bgcol

				}

				/*读-end*/
					
				bg = this.closk
				window.localStorage.bgcolor = JSON.stringify(bg) //本地存储数组	
				
			},
			
			
			adall:function(e){
				var bg = [];
				var tx= e.toElement.innerText.trim()
								
					
					for (var i=0 ; i<this.closk.length; i++) {
					
						if(this.closk[i]==tx){
							this.closk.splice(i, 1)
							this.closk.unshift(tx)
							
						}
						
						
					}
					
				this.$router.push({ name: 'all', params: { name: tx }})
				bg = this.closk
				window.localStorage.bgcolor = JSON.stringify(bg)
			},
			
			sub: function() {
                 
				/*读-e*/
				var bg = [];
				var bgcol;
				if(window.localStorage.bgcolor) {
					var bgcol = JSON.parse(window.localStorage.bgcolor)
				}

				if(bgcol && bgcol.length > 0) {
					var bg = bgcol

				}

				/*读-end*/
				if(this.seekinput == "" || this.seekinput == null || this.seekinput.trim().length <= 0) {
					return false;
				}else if(this.closk.indexOf(this.seekinput)==-1) {
					this.closk.unshift(this.seekinput)
					if(this.closk.length > 5) {
						this.closk.splice(5, 1)

					}
				}else{
					
					for (var i=0 ; i<this.closk.length; i++) {
					
						if(this.closk[i]==this.seekinput){
							this.closk.splice(i, 1)
							this.closk.unshift(this.seekinput)
							
						}
						
						
					}
					
				}
				
			
					this.$router.push({ name: 'all', params: { name: this.seekinput }})
					
				/*存-s*/

				bg = this.closk
				window.localStorage.bgcolor = JSON.stringify(bg) //本地存储数组

				/*存-e*/

			},
			/*清除缓存*/
			clear:function  (index) {
					this.closk.splice(index, 1)
					/*清除后重新存储*/
					
					/*读-e*/
				var bg = [];
				var bgcol;
				if(window.localStorage.bgcolor) {
					var bgcol = JSON.parse(window.localStorage.bgcolor)
				}

				if(bgcol && bgcol.length > 0) {
					var bg = bgcol

				}

				/*读-end*/
					
				bg = this.closk
				window.localStorage.bgcolor = JSON.stringify(bg) //本地存储数组	
					
			}
		},

		mounted: function() {
       var bgcol;
			if(window.localStorage.bgcolor) {
				var bgcol = JSON.parse(window.localStorage.bgcolor)
			}

			if(bgcol && bgcol.length > 0) {

				this.closk = bgcol

				console.log(bgcol)

			}

	}
	} 
	
</script>

<style>
		.inSearch {
				display: block;
				padding: 0.8rem 3rem;
				position: relative;
				border-bottom: solid 1px #EEEEEE;
			}
			
			.inSearch .inSinput {
				display: block;
				width: 100%;
				font-size: 0.7rem;
				height: 1rem;
				line-height: 1rem;
				
				border: none;
			}
			
			.inSearch .inSiconseek {
				display: block;
				position: absolute;
				top: 0.8rem;
				left: 1.6rem;
				width: 1rem;
				height: 1rem;
				font-size: 1rem;
				text-align: center;
				line-height: 1rem;
				color: #AAAAAA;
			}
			
			.inSearch .inSiconsmall {
				display: block;
				position: absolute;
				top: 0.8rem;
				right: 1.6rem;
				width: 2rem;
				height: 1rem;
				font-size: 1rem;
				text-align: center;
				line-height: 1rem;
				color: #AAAAAA;
				font-size: 0.6rem;
				background: #FF0000;
                 color: white;
			}
			
			
			
			
			.inSearch .inSiconseek i {
				font-size: 1rem;
			}
			
			.inSearchlist {
				width: 100%;
			}
			
			.inSearchlist ul {
				width: 100%;
				display: inline-block;
			}
			
			.inSearchlist ul li {
				position: relative;
				width: 100%;
				border-bottom: solid 1px #EEEEEE;
			}
			
			.inSearchlist ul li p {
				display: block;
				line-height: 2rem;
				height: 2rem;
				width: 15rem;				
				padding-left: 0.5rem;
				font-size: 0.7rem;
			
				
			}
			
			.inSearchlist ul li p a{
				display: block;
				line-height: 2rem;
				height: 2rem;
				width: 15rem;				
				padding-left: 0.5rem;
				font-size: 0.7rem;
				overflow: hidden; text-overflow: ellipsis; white-space: nowrap;  
			  
				
			}
			
			.inSearchlist ul li span {
				position: absolute;
				top: 0px;
				right: 0.5rem;
				display: block;
				padding: 0.5rem 0rem;
				font-size: 0.7rem;
				color: #AAAAAA;
			}
			
			.qk{
				position: fixed;
				left: 0px;
				bottom: 0px;
				width: 100%;
				height:2rem ;
				
				line-height: 2rem;
				border-top: solid 1px #EEEEEE;
				text-align: center;
				font-size: 0.6rem;
			}
</style>