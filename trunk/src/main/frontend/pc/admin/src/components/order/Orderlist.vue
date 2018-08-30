<template>
	<div class="contain">
		<div class="item-title">
		<div class="miaoshu">
		<h3>订单管理</h3>
		<h5>商城所有订单索引及管理</h5>
		</div>
		</div>
	
<div style="margin: 0 auto;height: 750px;overflow-y: scroll;">
	<div style="margin-top: 20px;margin-bottom: 20px;">
	<Input v-model="orderNumber" placeholder="请输入商品的标题或者订单号进行搜索..." style="width: 300px"></Input>
    <Button type="ghost" icon="ios-search" @click="requestorder">订单搜索</Button>
    
	</div>
<div style="width: 500px;background-color: deepskyblue;height: 300px;margin-top:20px;" v-if="false">
	
</div>
<!-- 表头-->
<table class="table">
	<tr>
		<td style="width: 30%;">商品</td>
		<td style="width: 10%;">单价</td>
		<td style="width: 5%;">数量</td>
		
		<td style="width: 15%">实付款</td>
		<td  style="width:10%;">交易状态</td>
		<td style="width: 20%;">交易信息 </td>
		<td style="width: 10%;">备注</td>
	</tr>
</table>
<!-- 表头-->
<div>
       <ul class="tablex" v-for="(items,index) in trlist">
       	   <li class="head">
       	   	<ul>      	   		
       	   		<li style="width:15%;"><span class="tabletime"><Icon type="calendar"></Icon>&nbsp;&nbsp;下单时间:<strong>{{items.created}}</strong></span></li>
       	   		<li style="width: 15%;"><span class="tabletime"><Icon type="clipboard"></Icon>&nbsp;&nbsp;订单号: {{items.orderNumber}}</span></li>
       	   		<li style="width: 10%;"><span class="moneys">运费:{{items.freight}}</span></li>
       	   		<li style="width: 5%;"></li>
       	   		<li style="width: 15%;"><span class="moneys">共计:{{items.paidPrice}}元</span></li>
       	   		<li style="width: 20%;"><span class="orderdei" @click="modeloks(index)">订单详情</span></li>
       	   		<li style="width: 20%;">商品发货信息</li>
       	   	</ul>
       	   </li>
       	   
       	   <li class="tablecontain">
       	   	<ul class="table_contain" v-for="(item,indexs) in items.data">
       	   		<li style="width:30%;padding-left: 5px;">
       	   			<div class="tupian"><img :src="item.smallimage" class="image" width="80" height="80"/></div>
       	   			<div class="miaoshu">
       	   				<span class="productdel">{{item.name}}<strong>&nbsp;&nbsp;&nbsp;[交易快照]</strong></span><br /><br />
       	   				<span class="sortsx" v-for="(itemx,indexs) in item.arr">{{itemx}}</span>
       	   			</div>
       	   		</li>
       	   		<li style="width: 10%;text-align: center;">    	   			
       	   			<!--<span class="originalprice"><s>原价￥{{item.originalprice}}</s></span><br /><br />-->
       	   			<span>单价￥{{item.paidPrice}}</span>
       	   		</li>
       	   			
       	   		<li style="width: 5%;text-align: center;">{{item. goodsAmount}}</li>
       	   		<li style="width: 15%;text-align: center;"><span class="money">￥{{item.actualprice}}</span></li>
       	   		<li style="width: 10%;text-align: center;"><div>
       	   			<!--<span class="statecolor"><Icon type="checkmark-circled" color="#19be6b"></Icon>{{item.status}}</span><br /><br />
       	   			<span>商品详情</span>-->
       	   			 <span v-show="item.status==0&&items.status==0"><Tag type="dot" color="yellow">未付款</Tag></span>
					<span v-show="item.status==0&&items.status==1"><Tag type="dot" color="blue">未发货</Tag></span>
					<span v-show="item.status==1"><Tag type="dot" color="red">待收货</Tag></span>
					<span v-show="item.status==2"><Tag type="dot" color="green">已收货</Tag></span>
					<span v-show="item.status==3"><Tag type="dot" color="info">已评论</Tag></span>                           	   			
       	   		</div></li>
       	   		<li style="width: 30%;text-align: left;cursor: pointer;padding-left: 20px;">
       	   			<div>
       	   			<span>物流名称:{{item.logistics.logisticsName}}</span><br /><br /><br />
       	   			<span>物流单号:{{item.logistics.logisticsNumber}}</span>
       	   			</div>
       	   		</li>
       	   		
       	   	<!--	<li style="width: 10%;text-align: center;">    	   			
       	   			<!--<span class="originalprice"><s>原价￥{{item.originalprice}}</s></span><br /><br />-->
       	   		<!--	{{item.paidPrice}}
       	   		</li>-->
       	   	</ul>
       	   </li>     	  
       </ul>
</div>
<div style="display:inline-block;margin-top: 10px;width: 100%;"><div style="float: right;margin-bottom: 10px;">
	<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
</div></div>
</div>
</div>
</template>
<script>
var sortx;
var groups=[];
 export default {
        data () {
            return {
            	logisticsDatas:{},//物流信息存储
            	value:'',
            	current:0, 
		        pageSize:10,
		        ifs:false,
		        totalpage:0,
		        paynum:0,
		        orderNumber:'',
            	trlist:[
            	   {
            	   	orderId:'',
            	   	created:'2018-07-19',
            	    orderNumber:'186416171710778895',
            	    orderStatus:'未付款',
            	    price:'15345',
            	    data:[
            	   {
            	   	smallimage:require("../../assets/shili.jpg"),
                    name:'中国电信官方旗舰店 河南手机充值20元电信话费直充快充电信充值',
                    paidPrice:'19.96',
                    originalprice:'2000',
                    goodsAmount:1,
                    actualprice:'21545',
                    arr:'',
                    status:'交易成功'                    
            	   }         	   
            	   ]
            	  }         	          	           	  
            ]
           	              	
        }
        },
        methods:{
        	//请求规格
        	resquestsort(){
        		var that = this;
        		var ddd=[];
        		this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/goodstypes/all'	 	
				 }).then(function(res) {
				 	 console.log(res.data.data)
                   for (var b=0;b<res.data.data.specOption.length;b++) {
                   	for (var g=0;g<res.data.data.typeSpec.length;g++) {
                   		if (res.data.data.specOption[b].goodsSpecId==res.data.data.typeSpec[g].id) {
                   			res.data.data.specOption[b].goodsname=res.data.data.typeSpec[g].name
                   		  }
                   	}                 	   
                   }
                   groups=res.data.data.specOption;
                  
                   that.requestorder();		 						 
				 })
        	},
        	//请求加载订单列表
        	 requestorder(){
            	var that = this;
            	var currents =this.current;
		        var pageSizes=this.pageSize;
		        var orderNumbers=this.orderNumber;
	            this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/orders?pageNum='+currents+'&pageSize='+pageSizes+'&orderNumber='+orderNumbers	
				 }).then(function(res) {
                 console.log(res.data.data)
                 
                 
                 
                 
                 
                 
                 
                  var logisticsdata=res.data.data.logistics
				 	 var logisticsCache=that.logisticsDatas
				 	 var objshow;
				 	 
				 	 for (var i=0 ; i<logisticsdata.length ;i++) {
				 	 	 var obj=logisticsdata[i].orderGoodsId
				 	 	
				 	 	
							 objshow=logisticsdata[i]
					
					 logisticsCache[obj] = objshow;
				 	 	 
				 	 	
				 	 	
				 	 }
				 	 
				 	 
				 	 
                 
                 
                 
                 
                 that.totalpage=res.data.pagination.total; 
                 for (var s=0;s<res.data.data.goods.length;s++) {
                 		var logistiId=res.data.data.goods[s].id
                 	
                 	if(that.logisticsDatas[logistiId]){
                 	 	
                 	res.data.data.goods[s].logistics=that.logisticsDatas[logistiId]
                 	 	
                 	 }else{
                 	 	var obj={
                 	 		logisticsName:"无",
                 	 		receiver:"无",
                 	 		logisticsNumber:"无",
                 	 	}
                 	 	res.data.data.goods[s].logistics=obj
                 	 }
                 	 
                 	
                 	
               	  /*  if (res.data.data.goods[s].status==0) {
               	    	res.data.data.goods[s].status='未发货'
               	    } else if(res.data.data.goods[s].status==1){
               	    	res.data.data.goods[s].status='已发货'    
               	    }else if(res.data.data.goods[s].status==2){
               	    	res.data.data.goods[s].status='已收货'    
               	    }else if(res.data.data.goods[s].status==3){
               	    	res.data.data.goods[s].status='已评论'     
               	    }else{
               	    	res.data.data.goods[s].status='已加评'
               	    }*/
               	    res.data.data.goods[s].actualprice=res.data.data.goods[s].goodsAmount*res.data.data.goods[s].paidPrice;
               	    var arrtem=res.data.data.goods[s].options.split(",");
               	    res.data.data.goods[s].arr=[];
               	    for (var ace=0;ace<arrtem.length;ace++) {
                    	 res.data.data.goods[s][arrtem[ace]]=arrtem[ace];           	 
                    	 for ( var k=0;k<groups.length;k++) {
               	           if (res.data.data.goods[s][arrtem[ace]]==groups[k].id) {             	           	   
               	           	   res.data.data.goods[s].arr.push([groups[k].goodsname]+":"+[groups[k].name]);           	           	              	           	   
               	           }
               	      }
                    }
               	   /* for (var i=0;i<res.data.data.logistics.length;i++) {
               	    	for (var a=0;a<res.data.data.goods.length;a++) { 
               	    			if (res.data.data.goods[a].id==res.data.data.logistics[i].orderGoodsId) {
               	    			    res.data.data.goods[a].logistics.logisticsName=res.data.data.logistics[i]
               	    		}              	    		
               	    	}
               	    }*/
               	    }
                    //根据数据的id，将数组里具有相同id的组成同一个数组（一个数组分成几个数组）
                   var map={},
                   dest=[];
                  for(var i = 0; i < res.data.data.goods.length; i++){
                  var ai = res.data.data.goods[i];
                  if(!map[ai.orderId]){
                   dest.push({
                    id:ai.orderId,    
                    data: [ai]
                        });
                   map[ai.orderId] = ai;
                }else{
                  for(var j = 0; j < dest.length; j++){
                    var dj = dest[j];
                    if(dj.id == ai.orderId){
                     dj.data.push(ai);
                   break;
                   }
                   }
                }                 
               }
                  for (var a=0;a<dest.length;a++) {
                   	for (var c=0;c<res.data.data.orders.length;c++) {
                   		if (res.data.data.orders[c].id==dest[a].id) {
                   			res.data.data.orders[c].data=dest[a].data
                   		} 
                   	}
                   }
                 that.trlist=res.data.data.orders;
                console.log(that.trlist)
				 })
            },
            deleteorder(index){
            	this.trlist.splice(index,1)
            	console.log(index)
            },
            deleteprodute(index,indexs){
                console.log(indexs);
                console.log(index);
                this.trlist[index].children.splice(indexs,1)
            },
            //改变分页
        	changepage(index){
        	this.trlist=[];
            this.current=index;
            this.resquestsort();
            },
            //跳转订单详情页面
            modeloks(index) {
            console.log(index)
             var orderid=this.trlist[index].id;
             var paynum=this.trlist[index].paidPrice
                setTimeout(() => {
                this.$router.push({ name: 'orderdetail',query: {orderid:orderid,paynum:paynum}, params: {name:orderid}})
				}, 2000)               
            }
        },
         mounted() {		
			this.resquestsort();
		}
     }
</script>

<style>
@import './Orderlist.css';
</style>