<template>
	<div id="loser">
		<div class="zf_tip">
			
			<div class="zf_tipimg">
				<img src="../../assets/img/loser.png">
			</div>
			
			<p>支付失败</p>
			<span>
				<router-link to="/">
				返回首页
				</router-link>
			</span>
		</div>
	</div>
	
	
</template>

<script>
</script>

<style>
	
	.zf_tip{
		margin-top: 2rem;
	}
	
	.zf_tip p{
		font-size: 0.7rem;
		text-align: center;
		line-height: 2rem;
	}
	
	.zf_tip span a{
		font-size: 0.7rem;
		display: block;
		text-align: center;
		color: #FF4444;
		
	}
	
	.zf_tipimg {
		display: block;
		width: 100%;
		height: 4rem;
		overflow: hidden;
	}
	
	
	.zf_tipimg img{
		display: block;
		width: 5rem;
		margin: 0px auto;
	}
</style>