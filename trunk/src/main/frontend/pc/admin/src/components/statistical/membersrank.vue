<style>
.contain{
margin-left: 210px;
margin-right: 10px;
}
h3{
	font-size: 16px;
    font-weight: normal;
    line-height: 20px;
    color: #333;
    display: inline-block;
}
h5{
	font-size: 12px;
    font-weight: normal;
    line-height: 18px;
    color: #777;
    display: inline-block;
    margin-left: 10px;
}
.item-title{
	height: 35px;
	line-height: 25px;
    white-space: nowrap;
    width: 98%;
    padding-top: 3px;
    margin: 20px 1%;
    border-bottom: solid 1px #E6E6E6;
}
.search{
	display: inline-block;
	margin-top: 10px;
	margin-bottom: 30px;
	display: inline-block;
}
.listtittle{
	 font-size: 12px;
    font-weight: normal;
    line-height: 24px;
    color: #777;
    vertical-align: bottom;
    letter-spacing: normal;
    display: inline-block;
    padding: 0
}
.tips{
	color: #0ba4da;
}
.routerx{
	color: #fff;
}
</style>
<template>
	<div>
<div class="contain" style="z-index: -1;">    
		<div class="item-title">
		<div class="miaoshu">
		<h3>统计报表 - 会员排行</h3>
		<h5>网站系统会员排行</h5>
		</div>
		</div>
		<Alert>
       <span class="tips"><Icon type="ios-checkmark-outline" size="16" color="red"></Icon>&nbsp;&nbsp;操作提示</span>
  <template slot="desc">会员购买统计排行</template>
    </Alert>
		<div class="functions">
			<div class="search">
				
			   <Input v-model="value" placeholder="手机号码..." style="width: 150px" size="small"></Input>
               <Input v-model="value" placeholder="emil..." style="width: 150px" size="small"></Input>
               <DatePicker type="daterange"  placement="bottom-end" placeholder="选择日期的范围" style="width: 200px"size="small"></DatePicker>
               <Button type="info" size="small">搜索</Button>                     
			</div>			
			<Table border :columns="columns7" :data="data6" ref="selection"></Table>
			<div class="paging">
			<Page :total="100"></Page>
			</div>
		</div>
</div>
</div>
</template>
<script>
 export default {
        data () {
            return {
            	options2:'',
                columns7: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: 'ID',
                        key: 'id',
                    },
                    {
                        title: '排行',
                        key: 'name'
                    },
                    {
                        title: '会员名称',
                        key: 'numbers'
                    },
                    {
                        title: '订单数',
                        key: 'sort'
                    },
                    {
                        title: '购物金额',
                        key: 'price'
                    },             
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.index)
                                        }
                                    }
                                }, '编辑'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.index)
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }
                ],
                data6: [
                    {   id:151,
                        name: '实木床柜',
                        numbers: 18,
                        sort: '床柜',
                        price:1000,
                        interduce:'推荐',
                        updown:'上架',
                        news:'yes',
                        kucun:'1000'                      
                    }             
                ],
                allsort:[
                    {
                        value: 'allsort',
                        label: '所有分类'
                    },
                    {
                        value: 'twosui',
                        label: '0-2岁'
                    },
                ],
                allbrand:[
                    {
                        value: 'allbrand',
                        label: '所有品牌'
                    }
                ],
                allone:[
                    {
                        value: 'allsort',
                        label: '所有分类'
                    },
                    {
                        value: 'twosui',
                        label: '0-2岁'
                    },
                ],
                alltwo:[
                    {
                        value: 'allsort',
                        label: '所有分类'
                    },
                    {
                        value: 'twosui',
                        label: '0-2岁'
                    },
                ],
                yes:'全选',
                no:'取消全选',
                model1:'',
                model2:'',
                model3:'',
                model4:'',
                statu:'全选',
                value:''
            }
        },
        methods: {
            show (index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
                })
            },
            remove (index) {
                this.data6.splice(index, 1);
            },
            alerts(){
            	alert(123)
            }
        }
    }
</script>

