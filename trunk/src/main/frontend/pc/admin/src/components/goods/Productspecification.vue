<template>
<div class="contain" style="z-index: -1;">
		<div class="item-title">
		<div class="miaoshu">
		<h3>商品标签</h3>
		<h5>商品标签及管理</h5>
		</div>
		</div>
		<div class="functions">
			<div class="search">
               <Input v-model="searchConName1" placeholder="请输入商品标签"   style="width: 300px" ></Input>
               <Button icon="ios-search" type="ghost" @click="handleSearch1">标签搜索</Button>
               <Button type="info"  @click="modaladd=true" >新增标签</Button>
			</div>			
			<Table border :columns="columns7" :data="data6"  height="500"></Table>
			<!--修改分类-->		
		<Modal
        v-model="modalx"
        title="修改商品标签"
        @on-ok="reok"
        @on-cancel="recancel">
       <Form :model="formItem" :label-width="80">
       <FormItem label="标签名称">
            <Input v-model="rename" placeholder="请输入标签名称..."></Input>
       </FormItem>
       <FormItem label="标签种类">
            <Input v-model="restyle" placeholder="请输入标签名称..."></Input>
        </FormItem>
        </Form>
    </Modal>
    <!--修改分类  jiesu -->
    <!--添加分类  开始 -->
    <Modal
        v-model="modaladd"    title="添加新商品标签"   @on-ok="addok" @on-cancel="addcancel">
       <Form :model="formItem" :label-width="80">
       	<FormItem label="标签名称">
            <Input v-model="addname" placeholder="请输入标签名称..."></Input>
        </FormItem>
       <!-- <FormItem label="标签种类">
            <Input v-model="addstyle" placeholder="请输入标签名称..."></Input>
        </FormItem>-->
        </Form>
    </Modal>
    <!--添加分类  jiesu -->
		</div>
</div>
</template>
<script>
let _ = require("lodash");
var arrsid;
var repairid;
import tableparts from '../Smallparts/tableparts';
 export default {
        data () {
            return {
            	formItem: {
                    input: '',
                    select: '',
                    radio: 'male',
                    checkbox: [],
                    switch: true,
                    date: '',
                    time: '',
                    slider: [20, 50],
                    textarea: ''
                  },
            	cityList: [
                    {
                        value: 'New York',
                        label: 'New York'
                    }],
                columns7: [
                     {
                        type: 'index',
                        align: 'center',
                        title: '序号 ',
                        width:120                      
                     },
                    {
                        title: '标签名称',
                        key: 'name',
                        align: 'center'
                    },                        
//                  {
//                      title: '标签种类',
//                      key: 'color', 
//                      align: 'center'
//                  },
                    {
                        title: '是否显示',
                        key: 'disabled',
                        align: 'center',
                        render: (h,params) => {
                        return h("iSwitch",{
                        	props:{
                        		size: "large",
                        		'true-value':false,
                        		'false-value':true,
                        		 value:params.row.disabled
                        	},                       
                        	on:{
                        		 'on-change':(res)=>{
                        		  this.repairs(params.index);
                        		  this.ifshows(res);
                        	   }
                        	},                    	                    	
                        },[
                        h('span', {
                           slot: 'open',
                         domProps:{
                           innerHTML: '正常'
                                   } 
                                    }),
                       h('span', {
                            slot:  'close',
                             domProps:{
                             innerHTML: '关闭'
                              } 
                         })                      
                        ]
                        )
                        }
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
                                    on:{
                                        click: () => {
                                            this.modalx=true;
                                            this.repair(params.index);
                                        }
                                    }
                                }, '修改'),
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
                data6: [ ],
                yes:'全选',
                no:'取消全选',
                statu:'全选',
                model2:'',
                value:'',
                modalx: false,
                modaladd:false,
                addname:'',
                addstyle:'',
                rename:'',
                restyle:'',
                repairname:'',
                initTable1: [],
                searchConName1:''
            }
        },
        methods: {
            remove (index) {
                this.data6.splice(index, 1);
            },
            repair(index){
                this.rename=this.data6[index].name
                this.restyle=this.data6[index].color
            	repairid=this.data6[index].id
            },
            repairs(index){
            	repairid=this.data6[index].id
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
            //请求商品标签信息 
            alerts(){
            	var $that = this;
	            this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/tags'	 	
				 }).then(function(res) {
				 	 $that.data6=res.data.data;
				 	 $that.data6=$that.initTable1=res.data.data;
				 	 console.log(res);
				 })
            },
           //添加商品标签
           addpro(){
           	if (this.addname==''||this.addname==null) {
           		this.proopen();
           	} else{
           	var that=this;
			this.axios({
				method:'post',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/tags',
				data:{
					name:that.addname,
					color:that.addstyle,
					disabled:false,
				}
			}).then(function(res){
				that.data6.unshift(res.data.data);
				that.data6.splice(1,0)
			})	
           	}
			
			},
			//修改商品标签
			repairpro(){
			var that=this;
			this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/tags/'+repairid,
				data:{
					name:that.rename,
					color:'a'
				}
			}).then(function(res){
				var goodsType=_.find(that.data6, function(o) {
					return o.id===repairid; 
				});
				if(goodsType){
					goodsType.name=that.rename;
				}
			})
			},
			ifshows(res){
            var result=res;
			this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/tags/'+repairid,
				data:{
					disabled:result 
				}
			})
           },
            //提示框的取消与确认
            reok(){          	
            	this.repairpro();
                this.$Message.info('信息已经删除');                                  
                },
            recancel () {
                this.$Message.info('信息取消了删除');
            },
             addok () {
             	this.addpro();
                this.$Message.info('Clicked ok');
                },
            addcancel () {
                this.$Message.info('Clicked cancel');
            },
            //控制switch 的ajax
            //商品搜索
             handleSearch1 () {
            this.data6 = this.initTable1;
            this.data6 = this.search(this.data6, {name: this.searchConName1});
           },
           //表格搜索
            search (data, argumentObj) {
            let res = data;
            let dataClone = data;
            for (let argu in argumentObj) {
                if (argumentObj[argu].length > 0) {
                    res = dataClone.filter(d => {
                        return d[argu].indexOf(argumentObj[argu]) > -1;
                    });
                    dataClone = res;
                }
            }
            return res;
        },
        proopen(nodesc) {
				this.$Notice.open({
					title: '标签错误提醒',
					desc: nodesc ? '' : '标签添加不能为空'
				});
			}
        },
        mounted() {		
			this.alerts();
			this.$Notice.config({
				top: 100,
				duration: 3
			});
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
.routerx{
	color: #fff;
}
</style>