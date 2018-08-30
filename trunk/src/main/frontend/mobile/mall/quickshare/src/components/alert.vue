<template>
	<div id="alertopen" >
		<div class="alertnav" v-bind:style="{'opacity':tip.alertopacity}" @click="alertnone">
				<div class="alert" v-show="tip.Alert==true">
					<div class="tip  hint" v-show="tip.alertshow==3">
						<i class="layui-icon ">&#xe698;</i>
						<p>已加入购物车</p>
					</div>
					<!--1正确-->
					<div class="tip  true" v-show="tip.alertshow==1">
						<i class="layui-icon ">&#xe605;</i>
						<p>{{tip.alwarning}}</p>
					</div>
					<!--2警告-->
					<div class="tip  warning" v-show="tip.alertshow==2">
						<i class="layui-icon ">&#xe608;</i>
						<p>{{tip.alwarning}}</p>
					</div>
					<!--提示-->
					<div class="tip  wtip" v-show="tip.alertshow==4">
						
						<span>{{tip.alwarning}}</span>
					</div>
					<!--5加载-->
					<div class="tip  xhuan" v-show="tip.alertshow==5">
						<i class="layui-icon ">&#xe63e;</i>
						<p>{{tip.alwarning}}</p>
					</div>
					
					<div class="tip  inquiry" v-show="tip.alertshow==6">
						<p class="conter">{{tip.alwarning}}</p>
						<div class="end">
							<div class="item">
								<span @click="mynone"> 取消</span>
							</div>
							<div class="item">
								<span @click="mysure">确认</span>
							</div>
						</div>
					</div>

				</div>
	    </div>
	</div>
	
</template>

<script>
	export default {
		props: ['text'],
		name: 'alertopen',

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
          },
          
          
          
          setalert:function  () {
			
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
				
				
			},
			
			timeout:function  () {
				setTimeout(() => {
					this.tip.alertopacity = 0
					this.tip.alwarning = ""
					this.tip.alertshow = 0
					this.tip.Alert=false
					
				}, 2000)
			},
          
		},

		mounted: function() {
         
        this.tip=this.text
        
        console.log(this.text)
	}
	} 
	
</script>

<style>
	
	.wtip{
		display: block;
		background: #000000;
		color: white;
		
		font-size: 0.6rem;
		height: 1rem;
		line-height: 1rem;
		
		
	}
	
	.alert .tip.wtip{
		
		padding: 0rem;
		
		background: none;
	
		
	}
	
	.alert .tip.wtip span{
		border-radius: 0.2rem;
		display: block;
		padding: 0.2rem 0.3rem;
		background: rgba(00, 00, 00, 0.5);
		margin: 0px auto;
		width: 6rem;
	}
</style>