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
	height: 55px;
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
.paging{
	margin-top: 50px;
	float: right;
	margin-right: 20px;
}
.center{
        width: 300px;
        margin: 10px auto;
        overflow: hidden;
    }
.center-right{
        float: right;
    }
.ivu-message{
    	z-index: 9999999999;
    }
    .ivu-tabs-nav .ivu-tabs-tab{
    	height: auto;
    }
</style>
<template>
	<div>
<div class="contain" style="z-index: -1;">
	    <div>
		<div class="item-title">
		<div class="miaoshu">
		<h3>商品管理</h3>
		<h5>商城所有商品索引及管理</h5>
		</div>
		</div>
		<div class="functions">
			<div class="search">			 
               <Input v-model="seavalue" placeholder="请输入商品名称..." style="width: 300px" ></Input>
               <Button @click="requestgoods" icon="ios-search" type="ghost">商品搜索</Button>
               <Button type="info"  @click="modaladd=true">添加商品</Button>                       
               <!--<Button  size="small" type="primary"  @click="exportData(1)">导出</Button>  -->        
			</div>			
			<Table border :columns="columns7" :data="data6" ref="table" height="500"></Table>
      <!-- 添加模态框开始-->
    <Modal v-model="modaladd" title="添加商品信息" @on-ok="modelok" @on-cancel="modelcancel">
    	<div id="" style="height:300px;">
    	<Form :model="formItem" :label-width="80">
       	<FormItem label="商品名称">
            <Input v-model="productname" placeholder="请输入商品名称..."></Input>
        </FormItem>
        	<FormItem label="商品分类">
            <Cascader :data="sortdata" v-model="sortvalue" style="width: 200px;"  @on-change="selectdata" change-on-select></Cascader>		 
        </FormItem>
        </Form>
    	</div>   	  
        </Modal>
      <!--模态框结束-->
		<div class="paging">
			<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
			</div>
		</div>
</div>
</div>
</div>
</template>
<script>
import Vue from 'vue';
let _ = require("lodash");
var selectname;
var selectid;
var brands;
var allbrands=[];
var arrtmps=[];
var goods=[];
var repairid;
var tmpid;
import tableparts from '../Smallparts/tableparts'
 export default {
 	  components: {
     tableparts:tableparts
              },
  
        data () {
            return {
            	sortvalue:[],
            	seavalue:'',
            	sortdata: [{
                    value: 'beijing',
                    label: '北京',
                    children: [
                        {
                            value: 'gugong',
                            label: '故宫'
                        }                    
                    ]
                },
                        {
                    value: 'jiangsu',
                    label: '江苏',
                    children: [
                        {
                            value: 'nanjing',
                            label: '南京'                   
                        },
                        {
                            value: 'suzhou',
                            label: '苏州'                          
                        }
                    ],
                }],
            	formItem: {
                    input: ''
               },
                animal: '',
            	modalx: false,
                columns7: [
                    {
                         title: '商品ID',
                         key: 'goodsId',
                         align: 'center'
                    },
                    {
                        title: '商品名称',
                        key: 'name',
                        align: 'center',
                        width:300,
                        
                    },
                    {
                        title: '分类',
                        key: 'sortname',
                        align: 'center'
                    },                
                    {
                        title: '原价 ',
                        key: 'originalprice',
                         align: 'center'
                    },
                    {
                        title: '现价',
                        key: 'price',
                         align: 'center'
                    },
                      {
                        title: '邮费',
                        key: 'freight',
                        align: 'center'
                    },      
                     {
                        title: '创建时间',
                        key: 'created',
                         align: 'center',
                         width: 110,
                    },
                     {
                        title: '品牌',
                        key: 'brandname',
                         align: 'center'
                    },
                    {
                        title: '显示',
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
                           innerHTML: '上架'
                                   } 
                                    }),
                       h('span', {
                             slot:  'close',
                             domProps:{
                             innerHTML: '下架'
                              } 
                         })                      
                        ]
                        )
                        }
                    },            
                    {
                        title: '商品图片',
                        key: 'path',
                        align: 'center',
                        render:(h,params)=>{
                        	return h('img',{
                        		domProps:{
                                src:params.row.path},
                                style: {
                                        width: '100px',
                                        height:'50px'
                                       }                       		
                        	})
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                            h('Button', {
                                    props: {
                                        type: 'error',
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
                                }, '商品详情')                          
                            ]);
                        }
                    }
                ],
                data6: [],
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
                value:'',
                arrs:[],
                modaladd:false,
                cityList:[],
                productname:'',
                allbrands:[],
                goodsid:'',
                goods:'',
                totalpage:0,
            	pageSize:20,
            	current:1,
            	ifs:false
            }
        },
        methods: {
        	show(index){
        	  var goodsid=this.data6[index].goodsId;
        	  var goodstypeid=this.data6[index].goodsTypeId;
        	  var goodsname=this.data6[index].name
        	  for (var i=0;i<arrtmps.length;i++) {
            		if(arrtmps[i].id==goodstypeid){
            			if (arrtmps[i].parentId) {
            			    goodstypeid=arrtmps[i].parentId
            			    tmpid=goodstypeid
            			}else{
            				tmpid=goodstypeid
            			}
            		}
            	}         	  
    	      this.$router.push({ name: 'addgoods',query: {goodsid:goodsid,name:tmpid,modaladd:goodsname}})    	      
        	},
            remove (index) {
                this.data6.splice(index, 1);
            }, 
            repairs(index){
            	repairid=this.data6[index].goodsId
            	console.log(this.data6[index].goodsId)
            },
            ifshows(res){
            var result=res;
            console.log(res)
            this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goods/'+repairid,
				data:{
					disabled:result 
				}
			}).then(function(res){
				if (res.data.error) {
					alert('邮费不能为空')
				}
			})
           },
            //翻译商品品牌开始
			requestbrand(){
				var that=this
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/brands'
				}).then(function(res) {
				 for (var i=0;i<that.data6.length;i++) {
						for (var a=0;a<res.data.data.length;a++) {
							if (res.data.data[a].id==that.data6[i].brandId) {
								that.data6[i].brandname=res.data.data[a].name
							}
						}
					}
				})
			},
			//请求分类数据
			requestsort(){
				var that=this
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstypes/all'
				}).then(function(res) {
				 arrtmps=res.data.data.goodsType;

				 for (var i=0;i<that.data6.length;i++) {
						for (var a=0;a<res.data.data.goodsType.length;a++) {
							if (res.data.data.goodsType[a].id==that.data6[i].goodsTypeId) {
								that.data6[i].sortname=res.data.data.goodsType[a].name
							}
						}
					}
				 that.data6.splice(1,0);
				})
			},
            //请求商品列表数据
            requestgoods(){
				var $that = this;
				var currents =this.current;
		        var pageSizes=this.pageSize;
		        var seavalues=this.seavalue;
		     
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods?pageNum='+currents+'&pageSize='+pageSizes+'&name='+seavalues
				}).then(function(res) {
					console.log(res)
					$that.totalpage=res.data.data.goods.pagination.totalCount;
                    $that.requestbrand();					
					$that.requestsort();
					$that.data6=res.data.data.goods.rows;
				})
				
			},
			//改变商品上架的状态开始
			
		    //改变商品上架的状态结束
			//分页处理
			changepage(index){
            this.current=index;
            this.requestgoods();
            },
			//翻译商品品牌结束
			//查询全部商品分类
			requestgoodsort(){
				var $that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstypes/type'
				}).then(function(res) {
					var tempData ={}					
					var datas = res.data.data;
					var m = 0;
					var len = datas.length;
					var tmp;
					var children;
					var idx=0;
					var finalData;
					var childrenArr;
					if(len==0){
						return;
					}
					var finalDatas=[];
					var parentArr=_.filter(datas,function(data){
						return !data.parentId && !data.disabled;
					});
					for(m;m<parentArr.length;m++){
						tmp = parentArr[m];
						children=[];						
						finalData ={
							value:tmp.id,
							label:tmp.name,
							children:children
						};
						finalDatas[m]=finalData;
						tempData[tmp.id]=children;
					}
					
					var childrenArr = _.filter(datas,function(data){
						return !!data.parentId && !data.disabled;
					});
					len=childrenArr.length;
					for(m=0;m<len;m++){
						tmp = childrenArr[m];
						finalData ={
							value:tmp.id,
							label:tmp.name
						};
						children = tempData[tmp.parentId];
						if(!children){
							continue;
						}
						children[children.length]=finalData;
					}
					$that.sortdata=finalDatas;
				})
			},
			//
			selectdata(value,selectedData){
            selectid=selectedData[value.length-1].value;
            selectname=selectedData[value.length-1].label;
			},
			//添加商品
			addgoods(){
			var that=this;
			
			this.axios({
				method:'post',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goods',
				data:{
					name:that.productname,
					goodsTypeId:selectid,
					disabled:true
				}
			}).then(function(res){
			       that.goodsid=res.data.data.id
			       goods=res.data.data;
			       console.log(goods)		    
			       for (var i=0;i<arrtmps.length;i++) {
            		if(arrtmps[i].id==goods.goodsTypeId){
            			if (arrtmps[i].parentId) {
            			    goods.goodsTypeId=arrtmps[i].parentId
            			    tmpid=goods.goodsTypeId
            			}else{
            				tmpid=goods.goodsTypeId
            			}
            		}
            	} 
            	that.requestsort();           	
                that.$Message.info('添加成功');        
                setTimeout(() => {
                that.$router.push({ name: 'addgoods',
                query: {name:tmpid,modaladd:that.productname,goodsid:that.goodsid},
                params:{name:selectid}})
				}, 2000)
			})
			},
          
            //提示框的取消与确认
            ok(){          	           
                this.$Message.info('信息已经删除');                                     
            },
            cancel () {
                this.$Message.info('信息取消了删除');
            },
            //模态框取消与确认
            modelok () {
            	var that=this
            	if (that.productname==''||that.productname==null||that.sortvalue==''||that.sortvalue==null) {
            		this.$Message.info('信息不全,添加失败');  
            		return false;
            	} else{
            	this.addgoods();
            		
            	}
            	
            },
            modelcancel () {
                this.$Message.info('Clicked cancel');
            },
            //下面导出逻辑
              exportData (type) {
                if (type === 1) {
                    this.$refs.table.exportCsv({
                        filename: 'The original data'
                    });
                } else if (type === 2) {
                    this.$refs.table.exportCsv({
                        filename: 'Sorting and filtering data',
                        original: false
                    });
                } else if (type === 3) {
                    this.$refs.table.exportCsv({
                        filename: 'Custom data',
                        columns: this.columns8.filter((col, index) => index < 4),
                        data: this.data7.filter((data, index) => index < 4)
                    });
                }
            } 
        },
        mounted() {	
        	this.requestsort();
			this.requestgoods();
			this.requestgoodsort();
		}
    }
</script>

