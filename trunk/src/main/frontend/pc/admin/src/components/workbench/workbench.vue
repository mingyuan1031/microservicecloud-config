<template>
	<div class="containxx">
	  <!--左半部分开始-->	
     <div class="w_side">
     	<div class="w_sidetop">
     		<div class="peo"> 		
     		<div class="imgs">
     			<img src="../../assets/login.jpg" width="100"/>
     		</div>
     		<div class="id">
     			<p class="i_name">温水煮青蛙</p>
     			<p class="i_accent">超级管理员</p>
     		</div>
     		</div>
     		<div class="loginmsg">
     			<p class="logintime">
     			登陆时间：2018-12-12-14：34
     			</p>
     			<p class="loginlocation">
     				登陆地点：郑州 
     			</p>
     		</div>
     	</div>
     	<div class="w_sidebottom">
     		<p><Icon type="clipboard"></Icon>&nbsp;&nbsp;<b>待处理订单</b></p><br />
     	    <Table height="200" :columns="columns1" :data="data1" :row-class-name="rowClassName"></Table>
     	  
     	    <p><Icon type="paper-airplane"></Icon>&nbsp;&nbsp;<b>待处理预约</b></p><br />
     	    <Table height="200" :columns="columns2" :data="data2"></Table>
     	    <div class="notic">
     	    	<span>技术支持：老屋新房网络科技<span class="sension">当前版本号：v-1.10</span></span>
     	    </div>
     	</div>
     </div>
         <!-- 左半部分结束-->
        <!--  右半部分开始-->
     <div class="w_contain">
     	<div class="w_containtop">
     		<div class="part">
     			<div class="tu1"><img src="../../assets/images/admin01.png" width="80"/></div>
     			<div class="sales">
     				<span class="salesnum">11233</span><br />
     				<span class="salesword">当日销售额</span>
     				</div>
     		</div>
     		<div class="part">
     			<div class="tu2"><img src="../../assets/images/admin2.png" width="80"/></div>
     			<div class="sales">
     			    <span class="salesnum">11233</span><br />
     				<span class="salesword">当日单数</span>
     				</div>
     		</div>
     		<div class="part">
     			<div class="tu3"><img src="../../assets/images/admin3.png" width="80"/></div>
     			<div class="sales">
     			    <span class="salesnum">11233</span><br />
     				<span class="salesword">当日预约</span>
     				</div>
     		</div>
     		<div class="part">
     			<div class="tu4"><img src="../../assets/images/admin4.png" width="80"/></div>
     			<div class="sales">
     			<span class="salesnum">11233</span><br />
     			<span class="salesword">当日销售量</span>
     			</div>
     		</div>
     	</div>
     	<div class="w_containbottom">
     		<div class="w_containbottoml">
     			<p><Icon type="clipboard"></Icon>&nbsp;&nbsp;<b>订单总数统计</b></p>
     
     			<div id="myChart"></div>
     			<p><Icon type="clipboard"></Icon>&nbsp;&nbsp;<b>月订单排名</b></p>
     			<Table  :columns="columns3" :data="data3"></Table>
     		</div>
     		<div class="w_containbottomr">		
     			<div id="myChart2"><img src="../../assets/images/chinamap.png" width="300"/></div>
     			<p style="margin-left: 100px;"><b>新零售系统覆盖省份分布图</b></p>
     			<p>此图显示是以2018-5-16日统计为准，河南老屋新房新零售系统已经以郑州为中心推广到上海，北京，田天津，重庆，河北,山东，江苏，四川，青海，内蒙古自治区，而且还在以不可估量的速度发展。</p>
     		</div>
     	</div>
     </div>
         <!--右半部分结束-->
         <div class="w-bottom">
         	<p class="h_sals">销售情况统计 <span class="sns">销售数量</span></p>
         	<div id="myChart3"></div>
         </div>
	</div>
</template>

<script>
	var echarts = require('echarts');
	 export default {
        data () {
            return {
                columns1: [
                    {
                        title: '订单编号',
                        key: 'ordernum'
                    },
                    {
                        title: '联系人',
                        key: 'lianxi'
                    }
                ],
                columns2: [
                    {
                        title: '电话号码',
                        key: 'phonernum'
                    },
                    {
                        title: '联系人',
                        key: 'lianxi'
                    }
                ],
                columns3: [
                    {
                        title: '商品名称',
                        key: 'pname'
                    },
                    {
                        title: '销售数量',
                        key: 'snum'
                    },
                    {
                        title: '排名',
                        key: 'rank'
                    }
                ],
                data1: [
                    {
                        ordernum: '123123123',
                        lianxi:'张三'
                    },
                    {
                        ordernum: '123123123',
                        lianxi:'张三'
                    },
                    {
                        ordernum: '123123123',
                        lianxi:'张三'
                    },
                   {
                        ordernum: '123123123',
                        lianxi:'张三'
                    }
                ],
                data2: [
                    {
                        phonernum: '123123123',
                        lianxi:'张三'
                    },
                    {
                        phonernum: '123123123',
                        lianxi:'张三'
                    },
                    {
                        phonernum: '123123123',
                        lianxi:'张三'
                    },
                   {
                        phonernum: '123123123',
                        lianxi:'张三'
                    }
                ],
                data3: [
                    {
                        pname: '123123123',
                        snum:'张三',
                        rank:'1'
                    },
                    {
                        pname: '123123123',
                        snum:'张三',
                        rank:'1'
                    },
                    {
                        pname: '123123123',
                        snum:'张三',
                        rank:'1'
                    },
                    {
                        pname: '123123123',
                        snum:'张三',
                        rank:'1'
                    }
                ]
            }
        },
       mounted(){
    this.drawLine();
  },
  methods: {
  	rowClassName (row, index) {
                if (index === 0) {
                    return 'demo-table-info-row';
                } else if (index === 3) {
                    return 'demo-table-error-row';
                }
                return '';
            }, 
  drawLine(){
        // 基于准备好的dom，初始化echarts实例
 var myChart = echarts.init(document.getElementById('myChart'));
// 绘制图表
myChart.setOption({
   title : {
        text: '某站点用户访问来源',
        subtext: '',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
    },
    series : [
        {
           
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'直接访问'},
                {value:310, name:'邮件营销'},
                {value:234, name:'联盟广告'},
                {value:135, name:'视频广告'},
                {value:1548, name:'搜索引擎'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});
    let myChart3 = this.$echarts.init(document.getElementById('myChart3'));
        // 绘制图表
   myChart3.setOption({
    title: {
        text: '每日销售统计'
    },
    tooltip: {},
    xAxis: {
        data: ['1号', '2号', '3号', '4号', '5号', '6号','7号','8号','9号','10号','11号','12号','13号','14号','15号','16号','17号','18号','19号','20号','21号','22号','23号','24号','25号','26号','27号','28号','29号','30号','31号']
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20,5, 20, 36, 10, 10, 20]
    }]
});
    }
    
    }
  }
</script>

<style>
	.containxx{
		width: 100%;
		margin-left: 210px;
        margin-right: 10px; 
        background-color: #f0f0f0;
        height: 1000px;
	}
	.w_side{
		width: 29%;
		min-height: 600px;
		float: left;
		margin-top: 20px;
		margin-right: 1%;
	}
	.w_contain{
		width: 54%;
		min-height: 600px;
		float: left;
	}
	.w_sidetop{
		height: 180px;
		background-color: #fff;
	}
	.w_sidebottom{
		height: 485px;	
		margin-top: 20px;
		background-color: #fff;
		padding: 0 10px;
	}
	.w_containtop{
		height: 100px;
		margin-top: 20px;
		background-color: #fff;
	}
	.w_containbottom{
		margin-top: 20px;
		height: 668px;
		background-color: #fff;
		padding: 10px;
	}
	.peo{
		padding: 8px 8px;
		height: 85px;
	}
	.imgs{
		float: left;	
	}
	.id{
		float: left;
		width: 150px;
		height: 75px;
		margin-left:60px;
		padding: 10px 10px;
	}
	.i_name{
		font-size: 20px;
		font-weight: 700;
		font-family: "微软雅黑";
		margin-bottom: 10px;
	}
	.i_accent{
		font-size: 14px;
		color: #2C3E50;
		font-weight: 700;
		font-family: "微软雅黑";
	}
	.loginmsg{
		width: 330px;
		height: 100px;
	}
	.logintime{
		margin-top: 20px;
		margin-bottom: 15px;
		border-top: 1px solid f0f0f0;
	}
	.loginlocation,.logintime{
		text-align: left;
		margin-left: 20px;
		font-family: "微软雅黑";
		font-weight: 600;
		font-size: 13px;
	}
	.part{
		margin-top: 10px;
		width: 24%;
		height: 100px;
		float: left;
		margin-left: 1%;
	}
	.tu1,.tu2,.tu3,.tu4,.sales{
		float: left;
	}
	.salesnum{
		color: #2d8cf0;
		font-size: 24px;
		font-weight: 700;
		margin-top: 20px;
	}
	.salesword{
		font-size: 14px;
		font-weight: 600;
		text-align: center;
	}
	.w_containbottoml{
		width: 40%;
		float: left;
	}
	.w_containbottomr{
		width: 58%;
		float: left;
		margin-left: 2%;
	}
	.tu1{
		background-color: #2D8CF0;
		border-radius: 2px;
	}
	.tu2{
		background-color: #64d572;
		border-radius: 2px;
	}
	.tu3{
		background-color: #edb530;
		border-radius: 2px;
	}
	.tu4{
		width: 80px;
		background-color: deeppink;
		border-radius: 2px;
	}
	.tu1,.tu2,.tu3,.tu4{
		margin-right: 15px;
	}
	.sales{
		margin-top: 10px;
	}
	#myChart{
		width: 350px;
		height: 350px;
	}
	#myChart2{
		width: 300px;
		height:350px;
		
	}
	#myChart2 img{
		margin-left: 80px;
		margin-top: 80px;
	}
	#myChart3{
		margin-right: 20%;
		width: 80%;
		height: 350px;
	}
	.w-bottom{
		width: 100%;
		float: left;
	}
	.sns{
		float: right;
	}
	.h_sals{
		padding: 10px 10px;
		background-color: #f25e43;
		color: #ffffff;
		border-radius: 3px;
	}
	.sension{
		float: right;
	}
	.notic{
		margin-top: 20px;
		background-color: #fff;
		height: 108px;
		line-height: 108px;
	    padding: 0 20px;
	    width: 100%;
	}
	.notic p{
		font-size: 12px;
		font-weight: 700;
        
	}
	.w_sidebottom p{
		height: 20px;
		line-height: 30px;
	}
	.ivu-table .demo-table-info-row th{
        background-color: #2db7f5;
        color: #fff;
    }
    .ivu-table .demo-table-error-row th{
        background-color: #ff6600;
        color: #fff;
    }
   
</style>