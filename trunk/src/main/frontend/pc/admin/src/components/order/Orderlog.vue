<template>
<div class="contain" style="z-index: -1;">
		<div class="item-title">
		<div class="miaoshu">
		<h3>订单日志</h3>
		<h5>所有用户操作订单生成的日志明细</h5>
		</div>
		</div>
		<Alert>
       <span class="tips"><Icon type="ios-checkmark-outline" size="16" color="red"></Icon>&nbsp;&nbsp;操作提示</span>
  <template slot="desc">所有用户操作订单生成的日志明细</template>
    </Alert>
		<div class="functions">
			<div class="search">
               <Select v-model="model2" size="small" style="width:100px">
               <Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
               </Select>
               <Input v-model="value" placeholder="订单编号..." style="width: 150px" size="small"></Input>
               <Input v-model="value" placeholder="订单时间" style="width: 150px" size="small"></Input>               
               <Button type="info" size="small">搜索</Button>
			</div>			
			<Table border :columns="columns7" :data="data6" ref="selection"></Table>
			<div class="paging">
			<Page :total="100"></Page>
			</div>
		</div>
</div>
</template>
<script>
 export default {
        data () {
            return {
            	model2:'',
            	cityList:'',
            	value:'',
                columns7: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: '订单ID',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('Icon', {
                                    props: {
                                        type: 'person'
                                    }
                                }),
                                h('strong', params.row.name)
                            ]);
                        }
                    },
                    {
                        title: '操作动作',
                        key: 'age'
                    },
                    {
                        title: '操作员',
                        key: 'address'
                    },
                    {
                        title: '操作备注',
                        key: 'address'
                    },                   
                    {
                        title: '操作时间',
                        key: 'address'
                    },
                    {
                        title: 'Action',
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
                    {
                        name: 'John Brown',
                        age: 18,
                        address: 'NewPark'
                    },
                    {
                        name: 'Jim Green',
                        age: 24,
                        address: 'Londork'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydneyrk'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydneyrk'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydneyark'
                    },
                    {
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawark'
                    }
                ]
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
                this.$refs.selection.selectAll(status);
            },
            alerts(){
            	alert(123)
            }
        }
    }
</script>

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
</style>