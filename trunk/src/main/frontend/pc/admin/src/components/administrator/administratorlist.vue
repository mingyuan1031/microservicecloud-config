<style>
@import './administratorlist.css';
</style>
<template>
	<div>
<div class="contain" style="z-index: -1;">    
		<div class="item-title">
		<div class="miaoshu">
		<h3>店员管理</h3>
		<h5>网站运营店员管理列表</h5>
		</div>
		</div>
		<div class="functions">
			<div class="w_add">			 
			 	 <Button type="info"  @click="addmanager">添加店员</Button>		            
			</div>
			<div>
			<Table border :columns="columns7" :data="data6" ref="selection"></Table>	
			</div>					
		<!-- 修改模态框开始-->
	    <Modal v-model="modalx" title="修改店员信息" @on-ok="modelok" @on-cancel="modelcancel">
        <RadioGroup v-model="animal" @on-change="redianpu">
        <Radio label="2"><span>店长</span></Radio>
        <Radio label="1"><span>店员</span></Radio>
        </RadioGroup>
        </Modal>
      <!--模态框结束-->
      <!-- 添加会员模态框开始-->
	    <Modal v-model="modaladd" title="请店员扫描该二维码" @on-ok="addok" @on-cancel="addcancel">
	    	<div style="text-align: center;">
	    		<img :src="imgurl"/>
	    	</div>       
        </Modal>
      <!--模态框结束-->
      
		</div>
</div>
</div>
</template>
<script>
var deleteid;
var repairid;
var redianpuvalue;
var redianpuname;
 export default {
        data () {
            return {
            	animal:'',
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
                animal: '',
            	modalx:false,
            	modaladd:false,
            	imgurl:'',
                columns7: [
                    {
                    	title:'ID',
                    	key:'id'
                    },
                    {
                        title: '用户登录名',
                        key: 'username'
                    },
                    {
                        title: '所属角色',
                        key: 'role'
                    },
                    {
                        title: '生日',
                        key: 'birthday'
                    },
                    {
                    	title:'性别',
                    	key:'sex'
                    },
                    { 
                    	title:'姓名',
                    	key:'name'
                    },              
                    {
                        title: '上一次登录',
                        key: 'lastLogin'
                    }, 
                    {
                    	title:'创建时间',
                    	key:'created'
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
                                        	if(this.data6[params.index].role=="店主"){
                                        	this.modalx=false;
                                        	this.time1();
                                        	}else{
                                        	this.modalx=true;
                                            this.repair(params.index);	
                                        	}
                                        }
                                    }
                                }, '修改'),
                                
                                
                               h('Poptip', {
        props: {
            confirm: true,
            title: '您确定要删除这条数据吗?',
            transfer: true
        },
        on: {
            'on-ok': () => {
               this.remove(params.index);
            }
        }
    }, [
         h('Button', {
                              props: {
                                        type: 'error',
                                        size: 'small'
                                    }                                    
                                }, '删除')
    ])                    
                                
                                
                                
                            ]);
                        }
                    }
                ],         
                data6: [],                            
                model1:'',
                model2:'',
                model3:'',
                model4:'',             
                value:''
            }
        },
        methods: {
            remove (index) {
                if (this.data6[index].role=='店主') {
                	this.time ();
                	return false;
                } else{
                deleteid= this.data6[index].id;
                this.deletemanager();
                console.log(deleteid) 
                this.data6.splice(index, 1);
                }           
            },
            //获取修改id
           repair(index){
             	repairid= this.data6[index].id;
             	console.log(this.data6[index].id)
             },
            // 加载店员列表 
             requestmanager(){        
              	var $that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url:'/api/clerks'
				}).then(function(res) {
				    $that.data6=res.data.data;
				    for (var i=0;i<res.data.data.length;i++) {
				    	 if (res.data.data[i].sex==1) {
				    	 	res.data.data[i].sex= '女'
				    	 } else{
				    	 	res.data.data[i].sex= '男'
				    	 }
				    }
		            for (var i=0;i<res.data.data.length;i++) {
		           	 if (res.data.data[i].role==0) {
		           	  	res.data.data[i].role='会员'
		           	  } else if(res.data.data[i].role==1){
		           	  	res.data.data[i].role='店员'
		           	  }else if(res.data.data[i].role==2){
		           	  	res.data.data[i].role='店长'
		           	  }else{
		           	  	res.data.data[i].role='店主'
		           	  }
		            }
				})
             },
            //添加管理员
           addmanager(){
           this.modaladd=true;
              	var that = this;
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url:'/api/users/0/qrcodes/addclerk'
				}).then(function(res) {
					that.imgurl= 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='+res.data.data.ticket			
				})
            },
             //删除管理员 
              deletemanager(){
              	var that = this;
				this.axios({
					method: 'delete',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url:'/api/clerks/'+deleteid
				}).then(function(res) {
					console.log(res)			
				})
             },
            redianpu(value){
             	redianpuvalue=value;
             	if (value==1) {
             		redianpuname='店员'
             	} else{
             		redianpuname='店长'
             	}
             },
            modelok () {
            	var that = this;
				this.axios({
					method: 'put',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url:'/api/clerks/'+repairid+'/roles/'+redianpuvalue
				}).then(function(res) {
					if (res.data.data.role==1) {
				   		res.data.data.role='店员';
				   	} else{
				   		res.data.data.role='店长';
				   	}
					var goodsType=_.find(that.data6, function(o) {
					return o.id===res.data.data.id; 
				     });
				   if(goodsType){			   	
					goodsType.role=res.data.data.role
				   }
				   var selected=_.find(that.data6, function(o) {
					return o.id===repairid; 
				     });
				   if(selected){			   	
					selected.role=redianpuname
				   }
				   console.log(goodsType)
				})
                this.$Message.info('Clicked ok');
            },
            //搜索过滤开始
            
            //搜索过滤结束
            
            
            modelcancel () {
                this.$Message.info('Clicked cancel');
            },
            addok(){
            	this.$Message.info('Clicked ok');
            },
            addcancel(){
            	this.$Message.info('取消店员添加');
            },
             //全局消息提醒
            time (){
                this.$Notice.open({
                    title: '删除错误',
                    desc: '店主无法进行删除',
                    top: 100,
                    duration: 3
                });          
            },
            time1 (){
                this.$Notice.open({
                    title: '修改错误',
                    desc: '店主无法进行修改',
                    top: 100,
                    duration: 3
                });          
            }
        },
        mounted() {
			this.requestmanager();
			this.$Notice.config({
               top: 100,
               duration: 3
               });
		}
    }
</script>

