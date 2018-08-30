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
		<h3>销售明细 </h3>
		<h5>网站系统销售明细</h5>
		</div>
		</div>
		<Alert>
       <span class="tips"><Icon type="ios-checkmark-outline" size="16" color="red"></Icon>&nbsp;&nbsp;操作提示</span>
  <template slot="desc">商品销量统计排行.</template>
    </Alert>
		<div class="functions">
			<div class="search">
               <Select v-model="model3" size="small" style="width:100px">
               <Option v-for="item in allone" :value="item.value" :key="item.value">{{ item.label }}</Option>
               </Select>
               <Select v-model="model4" size="small" style="width:100px">
               <Option v-for="item in alltwo" :value="item.value" :key="item.value">{{ item.label }}</Option>
               </Select>
                   <DatePicker type="daterange"  placement="bottom-end" placeholder="选择日期..." style="width: 200px" size="small"></DatePicker>
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
                        title: '商品名称',
                        key: 'name'
                    },
                    {
                        title: '商品货号',
                        key: 'numbers'
                    },
                    {
                        title: '数量',
                        key: 'sort'
                    },                 
                     {
                        title: '日期',
                        key: 'times'
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
                        times:'1000'                      
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
            handleSelectAll (status) {
            	if (this.one==false) {
            		this.$refs.selection.selectAll(false);
            		this.statu=this.yes;
            		this.one=true
            	} else{
            		 this.$refs.selection.selectAll(true);
            		 this.statu=this.no;
            		 this.one=false;                 		 
            	}
               
            },
            alerts(){
            	alert(123)
            }
        }
    }
</script>

