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
.routerx:hover{
	color: #FFF;
}
</style>
<template>	
<div class="contain" style="z-index: -1;">    
		<div class="item-title">
		<div class="miaoshu">
		<h3>会员管理</h3>
		<h5>网站系统会员索引与管理</h5>
		</div>
		</div>
		<div class="functions">
			<div class="search">		  
               <Input v-model="searchConName1" placeholder="请输入会员昵称..."   icon="search" style="width: 300px"></Input>
               <Button type="ghost"  @click="getmember">会员搜索</Button>
              <!-- <Button type="success" size="small" @click="modaladd = true">添加会员</Button>-->
          <!--     <Button @click="handleSelectAll('{{one}}')" size="small" type="error">{{statu}}</Button>   -->          
			</div>			
			<Table border :columns="columns7" :data="data6" ref="selection" height="500" ></Table>
     <div class="paging">
			<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs' :current='current'></Page>
	 </div>
</div>
</div>
</template>
<script>
	var repairid;
	var repairstate;
 export default {
        data () {
            return {
            	options2:'',
            	current:1,
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
                animal: '爪哇犀牛',
                searchConName1:'',
                initTable1: [],
                columns7: [           
                    {
                        title: '会员昵称',
                        key: 'name',
                        align: 'center'
                    },
                    {
                        title: '角色',
                        key: 'role',
                        align: 'center'
                    },
                    {
                        title: '性别',
                        key: 'sex',
                        align: 'center'
                    },                  
                    {
                        title: '注册日期 ',
                        key: 'created',
                        align: 'center'
                    },
                    {
                        title: '最后登陆时间 ',
                        key: 'lastLogin',
                        align: 'center'
                    },
                    {
                        title: '生日日期',
                        key: 'birthday',
                        align: 'center'
                    },                   
                    {
                        title: '禁用/启用',
                        key: 'state',
                        align: 'center',
                        render: (h,params) => {
                        return h("iSwitch",{
                        	props:{
                        		size: "large",
                        		'true-value':1,
                        		'false-value':0,
                        		 value:params.row.state
                        	},                       
                        	on:{
                        		 'on-change':(res)=>{
                        		  this.repairs(params.index);
                        		  this.memberifshows(res);
                        	   }
                        	},                    	                    	
                        },[
                        h('span', {
                           slot: 'open',
                          domProps:{
                           innerHTML: '启用'
                                   } 
                                    }),
                       h('span', {
                            slot:  'close',
                             domProps:{
                             innerHTML: '禁用'
                              } 
                         })                      
                        ]
                        )
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
                modalx: false,
                modaladd: false,
                data6:[],
                totalpage:0,
            	pageSize:30,
            	ifs:false,
            	ajaxHistoryData:[],
            }
        },
        methods: {
        	//获取修改id
        	repairs(index){
             repairid=this.data6[index].id;
            },
           //获取会员信息
            getmember(){
           	var that = this;
           	var currents =this.current;
		    var pageSizes=this.pageSize;
		    var name=this.searchConName1
		    console.log(name)
	            this.axios({
				 	method:'get',
				 	headers:{
				 		'X-Requested-With':'XMLHttpRequest',
				    	'ContentType':'application/json;charset=UTF-8'
				 	}, 		 	
				 url: '/api/members?pageNum='+currents+'&pageSize='+pageSizes+'&keywords='+name	 	
				 }).then(function(res) {	
				 	 for (var i=0;i<res.data.data.length;i++) {
				 	 	if(res.data.data[i].sex==0){
				 	 	   res.data.data[i].sex='男'
				 	 	}else{
				 	 	   res.data.data[i].sex='女'
				 	 	}
				 	 	if(res.data.data[i].role==0){
				 	 	   res.data.data[i].role='会员';
				 	 	}else if(res.data.data[i].role==1){
				 	 	   res.data.data[i].role='店员';
				 	 	}else if(res.data.data[i].role==2){
				 	 	   res.data.data[i].role='店长';
				 	 	}else{
				 	 	   res.data.data[i].role='店主';
                         }
                   } 
                     that.data6= res.data.data;
                     that.totalpage=res.data.pagination.total; 

              })
           }, //控制会员是否禁用  
           memberifshows(res){
            var result=res;
			this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/members/'+repairid+'/state/'+result,				
			})
            },
            //分页
             changepage(index){
             this.current=index;
             this.getmember();
            },
            //表格搜索                  
            modelok () {
                this.$Message.info('Clicked ok');
            },
            modelcancel () {
                this.$Message.info('Clicked cancel');
            }
        },
        mounted() {		
			this.getmember();
		}
    }
</script>

