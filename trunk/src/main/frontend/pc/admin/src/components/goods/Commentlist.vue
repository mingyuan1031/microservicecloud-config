<template>
<div class="contain" style="z-index: -1;">
		<div class="item-title">
		<div class="miaoshu">
		<h3>评价管理 </h3>
		<h5>商品交易评价管理</h5>
		</div>
		</div>	
		<div class="functions">
			<div class="search">
               <Input v-model="uservalue" placeholder="搜索商品名..." style="width: 300px" ></Input>
               <Button   @click="getrequest" icon="ios-search" type="ghost">评价搜索</Button>            
			</div>
			
			<Table border :columns="columns7" :data="data6" height="500"></Table>
			<div class="paging">
			<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
			</div>
		</div>
		<!--模态框开始-->
		<Modal
        v-model="opencom"
        title="用户评论详情"
        @on-ok="ok"
        @on-cancel="cancel">
        <p>{{commont}}</p>     
       </Modal>
		<!--模态框结束-->
		<!--添加回复开始-->
		<Modal
        v-model="openanswer"
        title="回复买家"
        @on-ok="ok"
        @on-cancel="cancel">
        <Input v-model="value8" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="仅能回复买家一次..." />   
       </Modal>
		<!--添加回复结束-->
</div>
</template>
<script>
var repairid;
var members=[];
var resquertlist=[];
var deleteid;
var deletecommentid;
var commentid;
var destorycommentid;
var destoryid;
var idx;
export default {
        data () {
            return {
            	value8:'',
            	opencom:false,
            	totalpage:0,
            	pageSize:20,
            	current:1,
            	ifs:false,
            	openanswer:false,
                columns7: [              
                    {
                        title: '商品',
                        key: 'goodsName',
                        align: 'center'
                    },
                  {
                      title: '用户',
                      key: 'name',
                      align: 'center'
                  },            
                    {
                        title: '评论内容',
                        key: 'content'
                    },                 
                     {
                        title: '评论时间',
                        key: 'created',                      
                    },            
                    {
                        title: '评价分',
                        key: 'score',
                        align: 'center',
                        width:80
                    },
                    {
                        title: '卖家回复',
                        key: 'reply'
                    },
                    {
                        title: '卖家回复时间',
                        key: 'replyCreated',
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
                        		'true-value':true,
                        		'false-value':false,
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
                        width: 400,
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
                                }, '评论详情'),
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
                                           this.removex(params.index) 
                                        }
                                    }
                                }, '删除'),
                                 h('Button', {
                                    props: {
                                        type: 'warning',
                                        size: 'small'
                                    },
                                     style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                           this.deletebuyer(params.index) 
                                        }
                                    }
                                }, '清空回复'),
                                
                                 h('Button', {
                                    props: {
                                        type: 'success',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {                                         
                                            this.addanswer(params.index)
                                        }
                                    }
                                }, '添加卖家回复')
                            ]);
                        }
                    }
                ],
                data6: [],       
                uservalue:'',
                commont:'',
                getrequests:[],
                answerid:'',
                answergoodsid:'',
                answergoodsExetendid:'',
                idx:''
            }
        },
        methods: {
            show (index) {
              this.opencom=true;
              this.commont=this.data6[index].content
            },
            addanswer(index){
            	if (this.data6[index].reply==''||this.data6[index].reply==null) {
            	this.openanswer=true;
            	this.answerid=this.data6[index].id;
            	this.answergoodsid=this.data6[index].goodsId;
                this.answergoodsExetendid=this.data6[index].goodsExtendId
                this.idx=index
            	}else{
            		this.openanswer=false;
            		alert('只能回复一次')
            	}
            	
            },
            //删除功能
            remove (index) {
                var that=this
                deleteid=this.data6[index].goodsId;
                deletecommentid=this.data6[index].id;
                this.axios({
            		method: 'delete',
            		headers: {
            			'X-Requested-With': 'XMLHttpRequest',
            			'ContentType': 'application/json;charset=UTF-8'
            		},
            	url:'/api/goods/'+deleteid+'/comments/'+deletecommentid,         		
            	}).then(function(res) {
            		that.data6.splice(index, 1);
            	})
               
            },
            //获取修改id
            repairs(index){
            	commentid=this.data6[index].id;
            	repairid=this.data6[index].goodsId
            },
             //是否显示
            ifshows(res){
            var result=res;
            if (result=="0") {
            	result=true
            } else{
            	result=false
            }
			this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goods/'+repairid+'/comments/'+commentid,
				data:{
					disabled:result 
				}
			})
           },
            ok () {          	
            	this.answerbuyer();
                this.$Message.info('Clicked ok');
            },
            cancel () {
                this.$Message.info('Clicked cancel');
            },
           changepage(index){
            this.current=index;
            this.getrequest();
            },
            //请求评论信息
            getrequest() {
            	var that = this;
            	var currents =this.current;
		        var pageSizes=this.pageSize;
		        var uservalue=this.uservalue;
            	this.axios({
            		method: 'get',
            		headers: {
            			'X-Requested-With': 'XMLHttpRequest',
            			'ContentType': 'application/json;charset=UTF-8'
            		},
            		url: '/api/goods/comments?pageNum='+currents+'&pageSize='+pageSizes+'&goodsName='+uservalue
            	}).then(function(res) {
            		console.log(res)
            		that.totalpage=res.data.pagination.total;         		
            		for(let a = 0; a < res.data.data.length; a++) {
            			if(res.data.data[a].score == 0) {
            				res.data.data[a].score = '优'
            			} else if(res.data.data[a].score == 1) {
            				res.data.data[a].score = '良'
            			} else {
            				res.data.data[a].score = '差'
            			}
            		}         		
            		that.data6=res.data.data.goodsComments         		
            		for (var a=0;a<that.data6.length;a++) {
                 		resquertlist.push(that.data6[a].memberId)
                 	}  
                 	console.log(_.uniq(resquertlist))
                 	that.requestmember();                                             	
            	})
            },
           /* 回复买家*/
            answerbuyer(){
            	var that=this
            	this.axios({
            		method: 'post',
            		headers: {
            			'X-Requested-With': 'XMLHttpRequest',
            			'ContentType': 'application/json;charset=UTF-8'
            		},
            		url: '/api/goods/'+that.answergoodsid+'/comments',
            		data:{
            			goodsComment:{
            			content:this.value8,
            			parentId:this.answerid,
            			goodsId:this.answergoodsid,
            			goodsExtendId:this.answergoodsExetendid,
            			answered:null
            			}
            		}
            	}).then(function(res) {
            		that.data6[that.idx].reply=res.data.data;
            		that.data6.splice(1,0)
            	})
            },
            
            /* 删除卖家评论*/
            deletebuyer(index){
                destoryid=this.data6[index].goodsId;
                destorycommentid=this.data6[index].replyId;
            	var that=this
            	this.axios({
            		method: 'delete',
            		headers: {
            			'X-Requested-With': 'XMLHttpRequest',
            			'ContentType': 'application/json;charset=UTF-8'
            		},
            		url: '/api/goods/'+destoryid+'/comments/'+destorycommentid          	
            	}).then(function(res) {
            		that.data6[index].reply=''
            	})
            },
            requestmember(){
            	var that=this;
            	var ids=JSON.stringify(_.uniq(resquertlist));
            	this.axios({
            		method: 'get',
            		headers: {
            			'X-Requested-With': 'XMLHttpRequest',
            			'ContentType': 'application/json;charset=UTF-8'
            		},
            		url: '/api/users/baseinfos?ids='+encodeURI(ids)          		
            	}).then(function(res) {
            		console.log(res.data.data)
            		for (var a=0;a<res.data.data.length;a++) {
            			for (var i=0;i<that.data6.length;i++) {
            				if (that.data6[i].memberId==res.data.data[a].id) {
            					that.data6[i].name=res.data.data[a].name
            				}
            			}            			
            		}
            		that.data6.splice(1,0)
            		console.log(that.data6)
            	})
            }   
            },
            mounted() {
            	this.getrequest();
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