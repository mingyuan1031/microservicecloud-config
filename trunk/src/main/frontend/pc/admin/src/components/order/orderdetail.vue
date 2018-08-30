<template>
	<div class="contain">
		<div style="height: 800px;overflow-y: scroll;">
		<div class="item-title">
		<div class="miaoshu">
		<h3>订单详情管理</h3>
		<h5>商城所有订单详情管理</h5>	
		</div>
		</div>
		<div class="warn">
		<Steps :current="statux">
        <Step title="未付款" content="这里是该步骤的描述信息"></Step>
        <Step title="已付款" content="这里是该步骤的描述信息" v-if="ifshows"></Step>
        <Step title="部分发货" content="这里是该步骤的描述信息" v-if="ifshows"></Step>
        <Step title="已发货" content="这里是该步骤的描述信息" v-if="ifshows"></Step>
        <Step title="已收货" content="这里是该步骤的描述信息" v-if="ifshows"></Step>
        <Step title="已撤销" content="这里是该步骤的描述信息" v-if="ifshow"></Step>
    </Steps>	
		</div>
		
        <div class="tabs-panels"> 
        	<table class="deltable">
        		<tr><th><h4>订单信息<br /><br /></h4></th><th></th><th></th></tr>
        		<tr><td>订单编号：{{orderNumber}}</td><td>订单描述：{{descr}}</td><td></td></tr>
        		<tr><td>创建时间：{{created}}</td><td>付款时间：{{paidTime}}</td><td>修改时间：{{updated}}</td></tr>
        		<tr><td>支付金额：{{paidPrice}}元</td><td>订单状态：{{status}}</td><td>付款方式：{{paidMethod}}</td></tr>
        	</table>
        	  <br /><br />
        	<table class="deltable">
        		<tr><th><h4>发货信息<br /><br /></h4></th><th></th><th></th></tr>
        		<tr><td>收货地址：{{receiverAddress}}</td><td>收货人：{{receiver}}</td></tr>
        		<tr><td>联系方式：{{receiverPhone}}</td><td>物流公司：红田家居</td></tr>
        		<tr><td>运单号：1465464</td><td>买家留言：太棒了</td></tr>
        	</table>
        	<div class="sunmoney">
            	<span class="news">实际付款：<span class="sunmoneyfont">{{paynum}}</span>元</span>
            </div> 
            <div class="headx">
       	   	<ul>  
       	   		<li style="width:30%;padding-left: 5px;">商品名称</li>
       	   		<li style="width: 15%;text-align: center;">数量</li>
       	   		<li style="width: 15%;text-align: center;">总价</li>
       	   		<li style="width: 20%;text-align: center;">状态</li>
       	   	</ul>
       	   </div> 
            <ul class="tablex" v-for="(items,index) in ullist">	       	   
       	    <li class="tablecontain">
       	   	<ul class="table_contain">
       	   		<li style="width:30%;padding-left: 5px;">
       	   			<div class="tupian"><img :src="items.smallimage" width="80"height="80"/></div>
       	   			<div class="miaoshu"><span class="productdel">{{items.name}}</span></div>
       	   		</li>
       	   		<li style="width: 15%;text-align: center;">{{items.goodsAmount}}</li>
       	   		<li style="width: 15%;text-align: center;">{{items.paidPrice}}</li>
       	   		<li style="width: 20%;text-align: center;"><div>
       	   			<span>{{items.status}}</span>
       	   		</div></li>
       	   		<li style="width: 20%;text-align: center;"></li>
       	   	</ul>
       	   </li>       	   
       </ul>
                     
        </div>
	</div>
	</div>
</template>

<script>
	var receiptid;
	export default {
        data () {
            return {
                orderid:'',
                orderNumber:'',
                paidTime:'',
                created:'',
                updated:'',
                descr:'',
                paidPrice:'',
                status:'',
                paidMethod:'',
                receiverAddress:'',
                receiver:'',
                receiverPhone:'',
                ifshow:false,
                ifshows:true,
                statux:0,
                st:'',
                paynum:'',
ullist: [
         {
			goodsAmount: 2,
			goodsId: "AAA",
			id: "4269jdkenpq8",
			name: "辣条",
			options: "A10,A11",
			orderId: "4269j0ocit4w",
			paidPrice: 10,
			smallimage: require("../../assets/shili.jpg"),
			status: 0
                }
                ]
            }
        },
        methods: {
        	requestmember(){
        		var thatid=this.orderid
        		var that=this
        		  this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/orders/'+thatid	 	
				 }).then(function(res) { 
				 	console.log(res.data.data.goodsDtos)
				 	that.orderNumber=res.data.data.orderNumber;
				 	that.paidTime=res.data.data.paidTime;
				 	that.created=res.data.data.created;
				 	that.updated=res.data.data.updated;
				 	that.descr=res.data.data.descr;
				 	that.paidPrice=res.data.data.paidPrice;
				 	receiptid=res.data.data.receiptId;				 	
				 	that.statux=res.data.data.status;
                    that.ullist=res.data.data.goodsDtos
                    for (var s=0;s<res.data.data.goodsDtos.length;s++) {
               	    if (res.data.data.goodsDtos[s].status==0) {
               	    	res.data.data.goodsDtos[s].status='未发货'
               	    } else if(res.data.data.goodsDtos[s].status==1){
               	    	res.data.data.goodsDtos[s].status='已发货'    
               	    }else if(res.data.data.goodsDtos[s].status==2){
               	    	res.data.data.goodsDtos[s].status='已收货'    
               	    }else if(res.data.data.goodsDtos[s].status==3){
               	    	res.data.data.goodsDtos[s].status='已评论'     
               	    }else{
               	    	res.data.data.goodsDtos[s].status='已加评'
               	    }
				 	if (res.data.data.paidMethod==0) {
				 		that.paidMethod='微信支付'
				 	    } 
				 	if (res.data.data.status==0) {
				 		that.status='未付款';
				 		that.statux=0;
				 	} else if(res.data.data.status==1){
				 		that.status='已付款';
				 		that.statux=1;
				 	}
				 	else if(res.data.data.status==2){
				 		that.status='部分发货';
				 		that.statux=2;
				 	}
				 	else if(res.data.data.status==3){
				 		that.status='已发货';
				 		that.statux=3;
				 	}
				 	else if(res.data.data.status==4){
				 		that.status='已收货';
				 		that.statux=4;
				 	}
				 	else if(res.data.data.status==5){
				 		that.status='已撤销';
				 		that.statux=5;
				 		that.ifshow=true;
				 		that.ifshow=false;
				 		that.st=error
				 	}			 	
				 	}
                    that.getreceipt();
				 })
				 	
        	},
//      	获取用户的地址
        	getreceipt(){
        		var that=this;     		
        		this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/addresses/'+receiptid	 	
				 }).then(function(res) {
				    that.receiverAddress=res.data.receiverAddress
				    that.receiver=res.data.receiver
				    that.receiverPhone=res.data.receiverPhone
				 })
				 
        	},
        	getParams() {
			// 取到路由带过来的参数 
			let routerparams = this.$route.query.orderid;
			       let paynum=this.$route.query.paynum
			// 将数据放在当前组件的数据内
			this.orderid=routerparams
			this.paynum=paynum
			this.requestmember();
		   }
        },
        mounted() {
		this.getParams();
	}
    }
</script>

<style>
	@import url("./orderdetail.css");
</style>