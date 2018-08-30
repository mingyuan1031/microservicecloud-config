<template>
	<div>
<div class="contain" style="z-index: -1;">    
		<div class="item-title">
		<div class="miaoshu">
		<h3>预约订单</h3>
		<h5>预约订单订单管理以及查询</h5>
		</div>
		</div>
		<div class="functions">
			<div class="search">
			   <Select v-model="ypeople"  style="width:200px;">
               <Option v-for="item in allsort" :value="item.value" :key="item.value">{{ item.label }}</Option>
              </Select>           
               <Select v-model="ystaue"  style="width:200px">
               <Option v-for="item in allone" :value="item.value" :key="item.value">{{ item.label }}</Option>
               </Select>
               <DatePicker type="daterange" placement="bottom-end" placeholder="请输入日期范围" style="width: 200px"  v-model="timerange"></DatePicker>
               <Input v-model="value" placeholder="搜索词..." style="width: 200px" ></Input>
               <Button   @click="queryinformations" type="ghost">预约单搜索</Button>                         
			</div>
			<Table border :columns="columns7" :data="data6" ref="selection" @on-selection-change="selected" height="500"></Table>
			<div class="paging">
			<Page :total="totalpage" :page-size="pageSize"  show-total  @on-change="changepage" :transfer='ifs'></Page>
			</div>
	<!--修改模态框开始-->
	<Modal v-model="modalx" title="修改预约信息" @on-ok="modelok" @on-cancel="modelcancel">
       <Form :model="formItem" :label-width="80">
       	<FormItem label="预约人">
            <Input v-model="yname" placeholder="请输入预约人姓名..."></Input>
        </FormItem>
        <FormItem label="电话">
            <Input v-model="ytel" placeholder="请输入预约电话..."></Input>
        </FormItem>
        <FormItem label="住房面积">
            <Input v-model="yarea" placeholder="请输入住房面积..."></Input>
        </FormItem>
        <FormItem label="备注">
            <Input v-model="ybeizu" placeholder=""></Input>
        </FormItem>
        <FormItem label="状态">
            <Select v-model="statusnum">
                <Option :value="item.value" :key="item.value"  v-for="item in statuss">{{ item.label }}</Option>
            </Select>
        </FormItem>
       </Form>
      </Modal>
      <!--模态框结束-->
		</div>
</div>
</div>
</template>
<script>
var arrsid;
var deleteid='';
var arrids;
var ids;
var repairid;
var deletename;
 export default {
        data () {
            return {
            	yname:'',
            	ytel:'',
            	yarea:'',
            	totalpage:0,
            	pageSize:20,
            	current:1,
            	ifs:false,
            	ybeizu:'',
            	ajaxHistoryData:[],
            	statuss: [
                    {
                        label: '未下单',
                        value: 0
                    },
                    {
                        label: '商谈中',
                        value: 1
                    },{
                        label: '已下单',
                        value: 2
                    },{
                        label: '已取消 ',
                        value: 3
                    }
                    ],
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
                columns7: [
                    {   
                    	title: '序号',
                        type: 'index',
                        width: 60,
                        align: 'center'
                    },              
                    {
                        title: '用户名称 ',
                        key: 'userId',
                        align: 'center'	
                    },
                    {
                        title: '预约人',
                        key: 'name',
                        align: 'center'
                    },
                    {
                        title: '预约电话',
                        key: 'phone',
                        align: 'center'
                    },
                    {
                        title: '住房面积',
                        key: 'area',
                        align: 'center'
                    },
                    {
                        title: '预约时间',
                        key: 'created',
                        align: 'center'
                    },
                    {
                        title: '备注',
                        key: 'descr',
                        align: 'center',
                        ellipsis:true
                    },
//                  {
//                      title: '状态',
//                      key: 'status',
//                      align: 'center'
//                  },
                    {
                        title: '状态',
                        key: 'statux',
                        align: 'center',
                        render: (h, params) => {
                        const row = params.row;
                        var color
                        var text 
                         if(row.status === 0){
                         	color='orange';
                         	text = '未处理'
                         }else if(row.status === 1){
                         	color='blue';
                         	text = '已下单'
                         }else if(row.status === 2){
                         	color='green';
                         	text = '商谈中'
                         }else if(row.status === 3){
                         	color='red';
                         	text = '交易取消'
                         }
                        return h('Tag', {
                            props: {
                                type: 'dot',
                                color: color
                            }
                        }, text);
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
                                    on: {
                                        click: () => {
                                            this.modalx = true;
                                            this.repair(params.index);
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
                allsort:[
                    {
                        value: '',
                        label: '请选择'
                    },
                    {
                        value: 'allsort',
                        label: '预约人 '
                    },
                    {
                        value: 'twosui',
                        label: '预约电话'
                    },
                ],
                allone:[
                    {
                        value: '',
                        label: '全部'
                    },
                    {
                        value: '0',
                        label: '未处理'
                    },
                    {
                        value: '1',
                        label: '已下单'
                    },
                    {
                        value: '3',
                        label: '商谈中'
                    },
                    {
                        value: '4',
                        label: '已取消'
                    }
                ],
                yes:'全选',
                no:'取消全选',
                ypeople:'',
                model2:'',
                ystaue:'',
                model4:'',
                statu:'全选',
                value:'',
                modalx: false,
                options2:'',
                arrs:[],
                statusnum:'',
                timerange:''
            }
        },
        methods: {
        	//页面单选删除
           remove(index){
           	    deleteid=this.data6[index].id
           	    deletename=this.data6[index].statux
           	    if (deletename=='已下单'||deletename=='商谈中'){
				this.time();
			    return false
					}else{
					radiodeleteinformations();
				 this.data6.splice(index, 1); 
					}     	             
                console.log(deletename);              
            },
           repair(index){
            	repairid=this.data6[index].id;
            	this.yname=this.data6[index].name;
            	this.ytel=this.data6[index].phone;
            	this.yarea=this.data6[index].area;
            	console.log(this.data6[index].status)
                this.statusnum=this.data6[index].status;
                this.ybeizu=this.data6[index].descr
            },
             //数据单选删除
            radiodeleteinformations(){
            var that=this
         	 var a=[];
           	 a.push(deleteid)        	
					 that.axios({
                 method: 'delete',
                url: '/api/reservations/'+deleteid,
               })	
         	
            },
            //全选
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
            //多选选中的index值
            selected(selection){
            	arrsid={};
            	arrids=[];
	         	for(var i=0;i<=selection.length-1;i++){ 
	         		arrsid[selection[i].id]=true;
	         		arrids.push(selection[i].id)
	          }       
            },
            //提示框的取消与确认，多选数据的删除 
            ok(){          	
                var evens = _.filter(this.data6, function(n) {          
                	return !arrsid[n.id];                
                }); 
                this.data6=evens;           
         	    this.axios({
                method: 'delete',
                url: '/api/reservations/'+arrids,
              })
                this.$Message.info('信息已经删除');                                     
            },
            cancel () {
                this.$Message.info('信息取消了删除');
            },
            modelok () {
          	    this.putinformations();
                this.$Message.info('Clicked ok');
            },
            modelcancel () {
                this.$Message.info('Clicked cancel');
            },          
            // 修改预约信息
            putinformations() {
				var that=this
				this.axios({
				  method: 'put',
				  url: '/api/reservations/'+repairid,
                   data:{
                   name:that.yname,
                   phone:that.ytel,
                   area:that.yarea,
                   descr:that.ybeizu,
                   status:that.statusnum
                 },
				}).then(function(res) {
			var goodsType=_.find(that.data6, function(o) {
			return o.id===repairid; 
				});
			if(goodsType){
					goodsType.name=that.yname;
					goodsType.phone=that.ytel;
					goodsType.area=that.yarea;
					goodsType.descr=that.ybeizu;
					if (that.statusnum==0) {
						that.statusnum='未下单'
					} else if(that.statusnum==1){
						that.statusnum='商谈中'
					}else if(that.statusnum==2){
						that.statusnum='已下单'
					}else{
						that.statusnum='已取消'
					}
					goodsType.status=that.statusnum;
			}
				})
			},
			//按多条件查询
          queryinformations() {
		    var that=this
		    var names;
		    var phones;
		    var currents =this.current;
		    var pageSizes=this.pageSize;
            console.log(this.ypeople)
            if(this.ypeople=='allsort'){
            	 names=this.value;
            	 phones='';
            }else if(this.ypeople=='twosui'){
            	 phones=this.value;
            	 names='';
            }else{
            	names='';
            	phones='';
            }
            var stas=this.ystaue;
            var time1=this.timerange[0];
            var time2=this.timerange[1];
            if (time1=='') {
            	time1= new Date("1998/4/13")
            }
            if(time2==''){
            	time2= new Date("2099/4/13")
            }
               var y = time1.getFullYear();
               var m = time1.getMonth() + 1;
               var d = time1.getDate();
               var starttimes= y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d)            
               var sy = time2.getFullYear()
               var sm = time2.getMonth() + 1
               var sd = time2.getDate();
               var endtimes=sy + "-" + (sm < 10 ? "0" + sm : sm) + "-" + (sd < 10 ? "0" + sd : sd)
				this.axios({
					method: 'get',
				    url: '/api/reservations?name='+names+'&phone='+phones+'&status='+stas+'&startTime='+starttimes+'&endTime='+endtimes+'&pageNum='+currents+'&pageSize='+pageSizes	
				}).then(function(res) {				
					that.data6=res.data.data;
					that.totalpage=res.data.pagination.total; 
					for(var i=0;i<res.data.data.length;i++){
						 if (res.data.data[i].status==0) {
						 	res.data.data[i].statux="未处理";
						 } else if(res.data.data[i].status==1){
						 	res.data.data[i].statux="商谈中";
						 }else if(res.data.data[i].status==2){
						 	res.data.data[i].statux="已下单";
						 }else{
						 	res.data.data[i].statux="已取消";
						 }
					}	
				
				}).catch(function(res){					
						alert('服务器正在忙，请刷新')
				})
			},
			// 获取历史记录信息
            changepage(index){
            this.current=index;
            this.queryinformations();
            },
            oks () {
                this.$Message.info('You click ok');
            },
            cancels () {
                this.$Message.info('You click cancel');
            },
            //全局消息提醒
            time (){
                this.$Notice.open({
                    title: '删除错误',
                    desc: '商谈中与已下单的预约信息无法进行删除',
                    top: 100,
                    duration: 3
                });          
            }
        },
        mounted() {		
			this.queryinformations();
			this.$Notice.config({
               top: 100,
               duration: 3
               });
		}
    }
</script>

