<template>
	<div class="contain" >
		<div class="item-title">
		<div class="miaoshu">
		<h3>商品分类管理</h3>
		<h5>网站文章分类添加与管理</h5>
		</div>
		</div>
	 <div id="goodstypes">
	 	 <div style="margin-bottom: 10px;">
	 	<Input v-model="value" placeholder="请输入分类的名称..." style="width: 300px" />
	 	<Button type="ghost" icon="ios-search">商品分类搜索</Button>
	 	<Button type="info" @click="addone">添加分类</Button>
	 	</div> 	 
	 	<div class="goodstypes">
	 	<ul>
	      	<li style="width: 1.95%;"></li>
	        <li style="width: 22.6%;">ID</li>
	        <li style="width: 32.5%;">分类</li>
	        <li class="item_disabled" style="width: 5.55%;">状态</li>
	        <li  style="width: 36.5%;">操作</li>
	      </ul>
	      </div>
	    <div class="table_tab" style="height: 600px;overflow: scroll;">
	    <table border="1px" cellspacing="" cellpadding=""  style="width: 100%;">
		<tbody  class="table_n" v-for="(item,index) in typeData" >
	      <tr>
	      	<th class="th_f"><small v-show="item.types.length>0" @click="tabshow">[+]</small><span @click="tabhide">[-]</span></th>	      	
	        <th>{{item.id}}</th>
	        <th>{{item.name}}</th>
	        <th class="item_disabled"><small v-show="item.disabled==true" @click="tabs(index)" class="tabs">禁用</small><span v-show="item.disabled==false" @click="tabs(index)" class="tabs">启用</span></th>      
	        <th  class="item_operation">
	        	<button style="background-color: deepskyblue;" @click="editor(index)">编辑</button>
	        	<button class="del" @click="deletesort(index)">删除</button>
	        <Button type="info" @click="addtwo(index)">添加二级分类</Button>
	        </th>
	      </tr>
	      <tr class="table_child" v-for="(itemdb,indexdb) in typeData[index].types">
	      	<th style="width: 30px;"></th>
	        <th style="padding-left: 30px; padding-right: 0px;">{{itemdb.id}}</th>
	        <th>{{itemdb.name}}</th>
	        <th class="item_disabled"><small v-show="itemdb.disabled==true" @click="tabx(indexdb,index)">禁用</small><span v-show="itemdb.disabled==false" @click="tabx(indexdb,index)">启用</span></th>
	        <th class="item_operation"><button @click="editortwo(indexdb,index)">编辑</button><button class="del">删除</button></th>
	      </tr>    	
		</tbody>   			
	</table>
	 </div>
	</div>
	 <!--修改分类  开始 -->
    <Modal v-model="modaladd" title="修改新商品分类" @on-ok="addok" @on-cancel="addcancel">
       <Form  :label-width="80">
       	<FormItem label="分类名称">
            <Input v-model="addname" placeholder="请输入分类名称..."></Input>
        </FormItem>    
        </Form>
    </Modal>
    <!--修改分类  jiesu -->
    <!--添加分类  jiesu -->
    <Modal v-model="modaladds" title="添加一级商品分类" @on-ok="ok" @on-cancel="cancel">
       <Form  :label-width="80">
       	<FormItem label="分类名称">
            <Input v-model="addnames" placeholder="请输入分类名称..."></Input>
        </FormItem>    
        </Form>
    </Modal>
    <!--添加分类  jiesu -->
    <!--添加 二级分类  jiesu -->
    <Modal v-model="modaladdx" title="添加二级商品分类" @on-ok="oks" @on-cancel="cancels">
       <Form  :label-width="80">
       	<FormItem label="分类名称">
            <Input v-model="addnamex" placeholder="请输入分类名称..."></Input>
        </FormItem>    
        </Form>
    </Modal>
    <!--添加二级分类  jiesu -->
     <!--修改 二级分类  jiesu -->
    <Modal v-model="modals" title="修改二级商品分类" @on-ok="oksx" @on-cancel="cancelsx">
       <Form  :label-width="80">
       	<FormItem label="分类名称">
            <Input v-model="addnamexsx" placeholder="请输入二级分类名称..."></Input>
        </FormItem>    
        </Form>
    </Modal>
    <!--修改二级分类  jiesu -->
	</div>	
</template>

<script>
    var editorid="";
    var repairid;
    var addoneid;
    var indextwo;
    var repairids;
    var editoridsx;
	import axios from 'axios'
	export default {

		name: 'goodstypes',

		data() {
			return {
				typeCache:{},
				typeData:[],
				addnamexs:'',
				addnamexsx:'',
				value:'',
				modals:false,
				addname:'',
				modaladd:false,
				modaladds:false,
				modaladdx:false,
				formItem:[],
				repairname:'',
				addnames:'',
				addtwoid:'',
				addnamex:'',
				repairidsx:'',
				editoridsx:''				
			}
		},

		methods: {
            //去掉chass 展开页面            
            tabshow:function(e){
            	
               e.target.parentNode.parentNode.parentNode.classList.remove("table_n")
				
            },
            
            tabhide:function(e){
				e.target.parentNode.parentNode.parentNode.classList.add("table_n")
            }
            ,
            editor(index){
            	this.modaladd=true;
            	var that=this;
                repairid=this.typeData[index].id
            	this.addname=this.typeData[index].name
                editorid=this.typeData[index].id			    
            },
            editortwo(indexdb,index){
            	this.modals=true;
            	var that=this;
            	console.log(indexdb)
            	console.log(this.typeData[index].types[indexdb].name)
            	this.addnamexsx=this.typeData[index].types[indexdb].name
                editoridsx=this.typeData[index].types[indexdb].id;
            },
            addone(){
            	this.modaladds=true;
            	var that=this;
            },
            addtwo(index){
            	this.modaladdx=true;
            	var that=this;
            	this.addtwoid=this.typeData[index].id;
            	indextwo=index           	
            },
            //改变状态         
            tabs(index){
            	var result=!this.typeData[index].disabled
                repairids=this.typeData[index].id
            	this.axios({
                method: 'put',
                url: '/api/goodstypes/'+repairids,
                data:{
					disabled:result			
				}
                }).then(function(res){
           	      console.log(res)
            })
            	this.typeData[index].disabled=!this.typeData[index].disabled;
            },
            //改变二级状态         
            tabx(indexdb,index){
            	var result=!this.typeData[index].types[indexdb].disabled
                repairids=this.typeData[index].types[indexdb].id
            	this.axios({
                method: 'put',
                url: '/api/goodstypes/'+repairids,
                data:{
					disabled:result			
				}
                }).then(function(res){
           	      console.log(res)
            })
            	this.typeData[index].types[indexdb].disabled=!this.typeData[index].types[indexdb].disabled;
            },
            //删除一级分类
            deletesort(index){
             var deleteid=this.typeData[index].id
             var a=[];
           	 a.push(deleteid)
         	 this.axios({
             method: 'delete',
             url: '/api/goodstypes/'+a,
           }).then(function(res){
           	  if (res.data.error) {
           	  	alert('该分类正在被使用，无法删除该分类')
           	  } else{
           	  	this.typeData.splice(index,1)
           	  }
           })        
            },
            //修改分类开始
            addok(){
            	var that=this;         	
            	this.repairname=this.addname
            	var that=this;
			    this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goodstypes/'+editorid,
				data:{
					name:that.repairname			
				}
			}).then(function(res){
				if (res.data.error) {
					alert("资源正在被使用")
				} else{
					var goodsType=_.find(that.typeData, function(o) {
					return o.id===repairid; 
				   });
				   if(goodsType){
					goodsType.name=that.repairname;
				}
				}
				
			})
                this.$Message.info('信息已经修改');                                     
                },
            //修改分类结束  
		 //添加一级分类开始
		ok(){
			if (this.addnames==''||this.addnames==null) {
				this.sortopen();
			}else{
				 var that=this;
			this.axios({
				method:'post',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goodstypes/',
				data:{
					name:that.addnames,
					sort:100,
					disabled:false,
					parentId:null
				}
			}).then(function(res){
				if (res.data.error) {
					alert('不允许重复')
				} else{
				res.data.data.types=[];
				console.log(that.typeData)
				console.log(res.data.data)
			    that.typeData.unshift(res.data.data);
				}				
			})
			 this.$Message.info('信息已经添加');
			}
	       
			},
			cancel(){
			this.$Message.info('信息取消了添加');
			},
            //添加一级分类结束
            
            //添加二级分类开始
            oks(){
	        var that=this;
			this.axios({
				method:'post',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goodstypes/',
				data:{
					name:that.addnamex,
					sort:100,
					disabled:false,
					parentId:that.addtwoid
				}
			}).then(function(res){
				console.log(res)
				if (res.data.error) {
					alert('不允许重复')
				} else{
				res.data.data.types=[];
				console.log(that.typeData)
				console.log(res.data.data)
			    that.typeData[indextwo].types.unshift(res.data.data);
				}				
			})
			 this.$Message.info('信息已经添加');
			},
		   oksx(){
			var that=this;
			this.axios({
				method:'put',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/goodstypes/'+editoridsx,
				data:{
					name:that.addnamexsx,
					sort:100,
					disabled:false
				}
			}).then(function(res){
				if (res.data.error) {
					alert("资源正在被使用")
				} else{
					var goodsType=_.find(that.typeData, function(o) {
					return o.id===editoridsx; 
				   });
				   if(goodsType){
					goodsType.name=that.repairname;
				}
				}
			})
			},
			cancels(){
			    this.$Message.info('信息取消了添加');
			},
            //添加二级分类结束
            addcancel () {        	
                this.$Message.info('信息取消了删除');
            },
            cancelsx(){
            	this.$Message.info('信息取消了删除');
            },
            sortopen(nodesc) {
                this.$Notice.open({
                    title: '添加分类错误',
                    desc: nodesc ? '' : '添加的分类不能为空 '
                });
            }
		},
		mounted: function() {
			
	    var _this=this
		 
		_this.axios({
		 	method:'get',
		
		 	
		 	url: '/api/goodstypes/all'
		 	
		 }).then(function  (res) {
		 	console.log(res.data)
		 	var goodparentshowdatas=[]
		 	var typecache=_this.typeCache
		    var gopai=res.data.data.typeSpec
		 	var goodstypedatas=res.data.data.goodsType
		 	var  showtype;
		 	var goodsw;
		 	var goodstyshowarry=[]
		 	
		 	for(var i=0 ; i<goodstypedatas.length ;i++){
		 		
		 	    
		 		var goodstyshowid=goodstypedatas[i].parentId
		 	
		 	         if(goodstyshowid){
		 				goodstyshowarry.push(goodstypedatas[i])
		 			}		 		
		 	}
		 	
		 	
		 	
		 	for(var m=0 ; m<goodstyshowarry.length; m++){
		 	var showDa=[]
		 	var pid=goodstyshowarry[m].parentId
		    var showid=typecache[pid]
		      
		   
		    
		      if(!showid){
		      	showDa.push(goodstyshowarry[m])
		      	  // console.log(showDa)
		      	typecache[pid]=showDa
		       
		      }else{
		      	typecache[pid].push(goodstyshowarry[m])
		      }
	
		 	}		 			 			 				 		 	
		  	for(var n=0 ; n<goodstypedatas.length ;n++){
		  		  showtype=goodstypedatas[n]
		  		  
		  		  if(!showtype.parentId){
		  		  	showtype.types=[]
		  		  	if(!!typecache[showtype.id]){
		  		  	  showtype.types=typecache[showtype.id]
		  		  	 
		  		  	}
		  		  	
		  		  	_this.typeData.push(showtype)
		  		  
		  		  }

		  	}
	 	
		 }).catch(function  (err) {
		 	console.log(err) 
		 
		 }),
		 this.$Notice.config({
				top: 100,
				duration: 3
			});
          
		}		 
	}

	
</script>

<style>
	.table_tab table{
   border-collapse: collapse;
    border-spacing: 0;
    background: #ffffff;
    border: 1px solid #E9E9E9;
    color: #333333;
    width: 100%;
	}
	
	.table_tab thead{
	display: table-header-group;
    vertical-align: middle;
    border-color: inherit;
	}
	   
	.table_tab thead th{
		background: whitesmoke;
	}
	  
	
	.table_tab th{
		padding: 10px 15px;
		padding-right: 0px;
		font-size: 14px;
		text-align: center;
		font-weight: 100;
		border: 1px solid #E3e3e3;
	}
	   .table_tab tbody{
	   background: #EEEEEE;
	   }
      
		.table_tab tbody.table_n .table_child{
			display: none
		}
		
		.table_tab tbody.table_n{
			background: none;
		}
		
    .th_f{
    	width: 30px;
    }
   
    .th_f small{
    	font-size: 14px;
    	display: none;
    }
    
    .th_f span{
    	display: block;
    }
    
    .table_n .th_f small{
    	display: block;
    }
    
    .table_n .th_f span{
    	display: none;
    }
    
    .table_tab th.item_disabled{
    	text-align: center;
    	padding-left: 0px;
    }
    
    .table_tab th.item_disabled span{
    	color: green;
    }
    
    .table_tab th.item_disabled small{
    	color: red;
    }
    
    .table_tab th.item_show{
    	text-align: center;
    	padding-left: 0px;
    }
    
    .table_tab th.item_show span{
    	display: inline-block;
        color: white;
        background: cadetblue;
    	padding: 0px 10px;
    	border-radius: 3px;
    	text-align: center;
    	line-height: 1.5em;
    	margin: 0px auto;
    	
    }
    
    .table_tab th.item_show small{
    
        color: #333333;
       border: solid 1px #E2E2E2;
    	padding: 0px 10px;
    	border-radius: 3px;
    	text-align: center;
    	line-height: 1.5em;
    	margin: 0px auto;
    	
    }
    
    .table_tab th.item_show span,small:hover{
    	cursor: pointer;
    	opacity: 0.8;
    }
    
     .table_tab th small{
     	font-size: 14px;
     }
     
     .table_tab th.item_operation button{
     		display: inline-block;
     		background: #009699;
     		border: none;
     		color: white;
     		border-radius: 3px;
     		color: #333333;
     		margin-right: 5px;
     		font-size: 12px;
     		color:white;
     		padding:3px 5px ;
     		cursor: pointer;
     }
     
     .table_tab th.item_operation button:hover{
     	opacity: 0.9;
     }
     
      .table_tab th.item_operation button.del{
      	background: #FF5733;
      }
      .goodstypes{
      	overflow: auto;
      }
      .goodstypes ul li{
      	float: left;
      	list-style: none;
      }
      .goodstypes ul li{
      	 padding: 4px;
         border: 1px solid #e5e5e5;
         line-height: 40px;
         font-size: 14px;
         text-align: center;
         background-color: #F5F5F5;
         height: 40px;
         padding-top: 0;
      }
      .tabs{
      	cursor: pointer;
      }
</style>