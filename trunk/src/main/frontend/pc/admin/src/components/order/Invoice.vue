<template>
	<div class="contain">
		<div class="item-title">
		<div class="miaoshu">
		<h3>发货管理</h3>
		<h5>商城所有发货订单索引及管理</h5>
		</div>
		</div>
	
<div style="margin: 0 auto;height: 750px;overflow-y: scroll;">
	<div style="margin-top: 20px;margin-bottom: 20px;">
	<Input v-model="value" placeholder="请输入商品的标题或者发货单号进行搜索..." style="width: 300px"></Input>
    <Button type="ghost" icon="ios-search">发货单搜索</Button>   
	</div>
<div style="width: 500px;background-color: deepskyblue;height: 300px;margin-top:20px;" v-if="false">
	
</div>
<!-- 表头-->
<table class="table">
	<tr>
		<td style="width: 30%;">商品</td>
		<td style="width: 10%;">收货信息</td>
		<td style="width: 5%;">物流信息</td>
		<td style="width: 15%">实付款</td>
		<td  style="width:20%;">交易状态</td>
		<td style="width: 20%;">交易操作</td>
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
       	   		<li style="width: 20%;"></li>
       	   	</ul>
       	   </li>
       	   
       	   <li class="tablecontain">
       	   	<ul class="table_contain" v-for="(item,indexs) in items.data">
       	   		<li style="width:30%;padding-left: 5px;">
       	   			<div class="tupian"><img :src="item.smallimage" class="image" width="80" height="80"/></div>
       	   			<div class="miaoshu">
       	   				<span class="productdel">{{item.name}}<strong>&nbsp;&nbsp;&nbsp;[交易快照]</strong></span><br /><br />
       	   				<span class="sortsx" v-for="(itemx,indexs) in item.arr">{{itemx}}&nbsp;</span>
       	   			</div>
       	   		</li>
       	   		<li style="width: 15%;text-align: left;padding-left: 20px;">
       	   			<span>物流单号:{{item.logistics.logisticsNumber}}</span><br /><br /><br />
       	   			<span>物流名称:{{item.logistics.logisticsName}}</span></li>
       	   		<li style="width: 15%;text-align: center;"><span class="money">￥{{item.actualprice}}元 </span></li>
       	   		<li style="width: 20%;text-align: center;"><div>
       	   			<span v-show="item.status==0&&items.status==0">  <Tag type="dot"  color="yellow">未付款</Tag></span>
					<span v-show="item.status==0&&items.status==1">  <Tag type="dot"  color="blue">已付款</Tag></span>
					<span v-show="item.status==1">  <Tag type="dot"  color="red">待收货</Tag></span>
					<span v-show="item.status==2">  <Tag type="dot"  color="green">待收货</Tag></span>
					<span v-show="item.status==3">  <Tag type="dot"  color="info">已评论</Tag></span>      	   		
       	   		</div></li>
       	   		<li style="width: 20%;text-align: center;">
       	   			<div>
       	   			<Button @click="addnews(index,indexs)" v-show="item.status==0" type="ghost">补全发货信息</Button>
       	   			<Button type="primary" v-show="item.status==1">待收货</Button>
       	   			<Button type="primary" v-show="item.status==2">待收货</Button>
       	   			<Button type="primary" v-show="item.status==3">已评论</Button>      	   			
       	   			<Button type="primary" @click="clickfahuo(index,indexs)" v-show="item.status==0">点击发货</Button>
       	   			</div>
       	   		</li>
       	   	</ul>
       	   </li>     	  
       </ul>
</div>
<div style="display:inline-block;margin-top: 10px;width: 100%;"><div style="float: right;margin-bottom: 10px;">
	<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
</div></div>
<Modal v-model="modaladd" title="添加商品信息" @on-ok="ok" @on-cancel="cancel">
    	<div id="" style="height:300px;">
    	<Form  :label-width="80">
       	<!--<FormItem label="收货人">
            <Input v-model="reproduct" placeholder="请输入收货人..."></Input>
        </FormItem>
        <FormItem label="收货联系">
            <Input v-model="retel" placeholder="请输入收货联系..." ></Input>
        </FormItem>-->
        <FormItem label="物流单号">
            <Input v-model="renum" placeholder="请输入物流单号..."></Input>
        </FormItem>
        <FormItem label="物流名称">
            <Input v-model="rename" placeholder="请输入物流名称..."></Input>
        </FormItem>
        </Form>
    	</div>   	  
</Modal>
</div>
</div>
</template>
<script>
	
		import axios from 'axios'
	
	var cfg=window.lwxfPreloadqdmin

	
	axios.defaults.headers['X-SID']=cfg.appcfg['X-SID'];
	axios.defaults.headers['PHPSESSIONID']=cfg.appcfg['PHPSESSIONID'];
	axios.defaults.headers['X-PHPSESSID']=cfg.appcfg['X-PHPSESSID'];
	axios.defaults.headers['X-C-ID']=cfg.preload['X-C-ID'];
	axios.defaults.headers['X-Requested-With']='XMLHttpRequest';
	axios.defaults.headers['ContentType']='application/json;charset=UTF-8';

	
	
var sortx;
var groups=[];
 export default {
        data () {
            return {
            	logisticsDatas:{},//物流信息存储
            	orindex:0, //订单位置
            	orgoodid:0, //订单商品位置
            	reproduct:'',
            	retel:'',
            	renum:'',
            	rename:'',
            	modaladd:false,
            	value:'',
            	current:0, 
		        pageSize:10,
		        ifs:false,
		        totalpage:0,
		        paynum:0,
            	trlist:[
            	           	           	  
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
        	clickfahuo(index,indexs){
        		var that=this
        		var orderGoodsId=this.trlist[index].data[indexs].id
        		var orderdataId=this.trlist[index].id
        		
        		
        		
        		
        	var lobaname=this.trlist[index].data[indexs].logistics.logisticsName
            var lobanum =this.trlist[index].data[indexs].logistics.logisticsNumber
        		if (lobaname==""||lobaname==null||lobaname.trim().length==0||lobanum==""||lobanum==null||lobanum.trim().length==0) {
        			alert("请补全物流信息")
        			return false;
        		}
        		
        	
        		this.axios({
				 	method:'post',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'PHPSESSIONID':'4EC1097573C8D62CCCBB0EB5582FB3F5',
				    	'X-SID':'fe492a98a06a3e145a73ecbee66171d3_d19d6839fb4847d90b8d56edf4c3de14',
				    	'X-PHPSESSID':'2S74-FTAL-C4IF-IF5D-6QI0-PI0U-Z5RA-DCCC',
				    	'X-C-ID':'40ord3va6adp',				    	
				    	'Content-Type':'application/json;charset=UTF-8',
				    	
				    	
				 	}, 		 	
				 url: '/api/logistics',
				 
				 
				 
				 
				 data:{
				 	ids:[orderGoodsId],
				 	orderId:orderdataId,
				 	logistics:{
				 		logisticsName:lobaname,
				 		logisticsNumber:lobanum,
				 		remarks:"",
				 	}
				 }
				 
				 }).then(function(res) {
				 	console.log(res)
				 	
				 	that.trlist[index].data[indexs].status=1
				 })
        	},
        	 //补全发货信息开始
        	 addnews(index, indexs){
        	 	this.orindex=index, //订单位置
            	this.orgoodid=indexs, //
        	 	
        	 	this.rename=this.trlist[this.orindex].data[this.orgoodid].logistics.logisticsName
            	this.renum=this.trlist[this.orindex].data[this.orgoodid].logistics.logisticsNumber
        	 
        	 	
        	 	this.modaladd=true;
        	 	
        	 	
        	 	
        	 	
        	 },
        	 //补全发货信息结束
        	//请求加载订单列表
        	 requestorder(){
            	var that = this;
            	var currents =this.current;
		        var pageSizes=this.pageSize;
	            this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/orders?pageNum='+currents+'&pageSize='+pageSizes	
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
                 	 	
                 	 		logisticsName:"",
                 	 		logisticsNumber:"",
                 	 	}
                 	 	res.data.data.goods[s].logistics=obj
                 	 }
                 	 
                 	 
               	 /*   if (res.data.data.goods[s].status==0) {
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
               	  /*   for (var i=0;i<res.data.data.logistics.length;i++) {
               	    	for (var a=0;a<res.data.data.goods.length;a++) {
               	    		if (res.data.data.goods[a].id==res.data.data.logistics[i].orderGoodsId) {
               	    			res.data.data.goods[a].logistics=res.data.data.logistics[i]
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
            },
            fahuo(){
            	this.modaladd=true;
            },
            //提示框的取消与确认
            ok(){  
            
            /*	this.orindex=index, //订单位置
            	this.orgoodid=indexs, //
            	this.renum*/
            	
            	this.trlist[this.orindex].data[this.orgoodid].logistics.logisticsName=this.rename
            	this.trlist[this.orindex].data[this.orgoodid].logistics.logisticsNumber=this.renum
            	
            	
            	
            /*	this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/orders?pageNum='+currents+'&pageSize='+pageSizes	
				 
				 
				 
				 }).then(function(res) {
				 	console.log(123)
				 })
                this.$Message.info('信息已经删除');   */                                  
            },
            cancel () {
                this.$Message.info('信息取消了删除');
            },
        },
         mounted() {		
			this.resquestsort();
		}
     }
</script>

<style>
@import './Orderlist.css';
</style>