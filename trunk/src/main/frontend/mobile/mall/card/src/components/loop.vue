<template>
	<div id="loop" >
		<!--<div class="alertnav"  @click="alertnone" style="opacity: 1;">
				
		<div class="tip  xhuan" >
			
			<p>{{tip.alwarning}}</p>			
		</div>
				
		</div>  -->
		
		
		<div class="loop">
			<i class="layui-icon ">&#xe63e;</i>
		</div>
		
		
		
	</div>
	
</template>

<script>
	export default {
		props: ['text'],
		name: 'loop',

		data() {
			return {
				somedata:"66666666",
				tip:{
				"alertopacity": "1",
				"Alert": true,
				"alertshow":6,
				"alwarning": "确认要删除",
				
				},
			}
		},

		methods: {
          alertnone:function  () {
          /*
          	 	this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false*/
          	 
          },
          
          mysure:function  () {
          	 this.$emit('child-say',this.somedata);
          	
       
          },
          
          mynone:function  () {
          	this.$emit('child-none',this.somedata);
          }
          
		},

		mounted: function() {
         
        this.tip=this.text
        
        console.log(this.text)
	}
	} 
</script>

<style>
	
	.loop{
		position: fixed;
		width: 100%;
		height: 100%;
		z-index: 99;
		left:0px;
		top: 0px;
		background: ;
	}
	
	
.loop i{
	display: block;
	font-size: 2rem;
	width: 2rem;
	height: 2rem;
	color: orangered;
	font-weight: bold;
	text-align: center;
	line-height: 2rem;
	padding: 0.2rem;
	border-radius: 50%;
	margin: 3rem auto;
	background: white;
}
</style>