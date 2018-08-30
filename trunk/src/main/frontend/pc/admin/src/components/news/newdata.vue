<template>
	<div class="contain" style="z-index: -1;">
		<div class="item-title">
			<div class="miaoshu">
				<h3>企业动态</h3>

			</div>
		</div>
		<div class="functions">

			<div class="newdataclass">
				<p>动态分类</p>
				<ul>
					<li v-for="(item , index) in newsdatas" style="padding-right: 15px;">
						<span @dblclick="getYname(index)">{{item.name}}</span>
						<small @click="remYove(index)">x</small>
						<div class="alertinput">
							<input v-model="subclassName" type="" name="" id="" placeholder="分类名称">

							<span @click="clearYsure(index)">确认</span> <span @click="clearYopen(index)">取消</span>

						</div>
					</li>

					<li v-show="openshow==true" class="iteminput">

						<div class="alertinput">
							<input v-model="subclassName" type="" name="" id="" placeholder="分类名称">

							<span @click="clearlisure">确认</span> <span @click="clearliopen">取消</span>

						</div>
					</li>

					<li class="but" @click="appendCenter">
						添加分类
					</li>
				</ul>
              
           

				
				
			</div>
			
			
			   <div class="tab-title">
              	<Select v-model="mewtypecacle" style="width:200px;">
              	   <Option value="000" key="000" >全部</Option>
				   <Option v-for="item in mewtypecacledatas" :value="item.id" :key="item.id" >{{ item.name }}</Option>
				</Select>
				 <button  @click="sodata" style="background: cornflowerblue; margin-right: 5px;">搜索</button>
                <button @click="addnews">添加资讯</button>
              </div>

			<div>
				<Table border :columns="columns7" :data="newDatas" ref="selection" @on-selection-change="selected" height="500"></Table>
				<div class="paging">
					<!--:transfer='ifs'-->
					<!---->
					<Page :total="totalpage" :page-size="pageSize" show-total @on-change="pagenewdatas" :transfer='ifs'></Page>
				</div>
			</div>
			<!-- 添加模态框开始-->
			<!-- -->
			<Modal v-model="modalx" title="修改菜单信息" @on-ok="addnewok" @on-cancel="addnewno">
				<Form :model="formItem" :label-width="90">
					<FormItem label="标题名称">
						<Input v-model="pname" placeholder="请输入标题名称..."></Input>
					</FormItem>

					<FormItem label="选择标题类别">
						<!--<i-select :model.sync="" style="width:200px">
				           <i-option v-for="(items,keys) in newsdatas"  :value="items.id">{{ items.name }}</i-option>
				         </i-select>-->

						<Select v-model="newtypeid" style="width:200px;" >
							<Option  v-for="item in newsdatas" :value="item.id" :key="item.id">{{ item.name }}</Option>
						</Select>

					</FormItem>

				</Form>
			</Modal>

			<!--修改-->
			<Modal v-model="modalget" title="修改资讯信息" @on-ok="modalgetok" @on-cancel="modalgetno">
				<Form :model="formgetItem" :label-width="90">
					<FormItem label="标题名称">
						<Input v-model="getname" placeholder="请输入动态分类名称..."></Input>
					</FormItem>

					<FormItem label="标题类别">

						<Input v-model="newgettypeid" disabled="disabled" placeholder="请输入动态分类名称..."></Input>

					</FormItem>

				</Form>
			</Modal>

			<!--上传logo-->
			<Modal v-model="logoadd" title="上传图片" @on-ok="logook" @on-cancel="logocancel">
				

				<ul class="upimg" style="display: block;">
					<li class="uphtml" style="margin-bottom: 10px;">
						<Icon type="plus"></Icon>
						<input type="file" name=""  multiple="multiple" id="" @change="upimg">
					</li>
					
                    <span  v-for="item in imgName">{{item}}</span>
               

				</ul>


               

				

			</Modal>
			<!--上传logo-->

			<!--上传html-->

			<Modal v-model="logoahtml" title="上传html文件" @on-ok="uphtmlsure" @on-cancel="uphtmlno">
				<div class="uphtml">
					<Icon type="plus"></Icon>
					<input type="file" name="" id="" @change="uphtml">
				</div>

				{{htmldata}}

			</Modal>
			<!--上传html-->
		</div>
	</div>
</template>
<script>
	let _ = require("lodash");
	var arrsid;
	var deleteid = '';
	var arrids;
	var repairid;
	var logoindex;
	export default {
		data() {
			return {
				totalpage: 0,
				pageSize: 10, //分页总数
				newpage: 1,
				ifs: false,
                mewtypecacle:"000",//搜索
             
				newtypeid: "",
				newsdatas: [], //分类信息
				mewtypecacledatas:[],
				htmldata: "", //html文件名称
				formDatahtml: '', //html文件资源
				newsdatasCacle: {},
				subclassName: "",
				openshow: false,
				pname: '',
				pdes: '',
				pstatus: '',
				logoadd: '',
				logourl: '',
				logoahtml: false,
				wjhtml: '',
				uphtmldata: {
					file: null
				},
				imgName:[],//图片名称

				modalget: false,
				getname: "",
				newgettypeid: "",
				getnewindex: "",
				file: null,
				loadingStatus: false,
				updata: {
					file: null
				},
				imgData: {
					accept: 'image/gif, image/jpeg, image/png, image/jpg',
				},
				formItem: {
					select: '',
					radio: 'male',
					checkbox: [],
					switch: true,
					date: '',
					time: '',
					slider: [20, 50],
					textarea: ''
				},

				formgetItem: {
					select: '',
					radio: 'male',
					checkbox: [],
					switch: true,
					date: '',
					time: '',
					slider: [20, 50],
					textarea: ''
				},
				columns7: [{
						type: 'index',
						align: 'center',
						title: '序号 ',
						width: 80,
					},
					{
						title: '标题',
						key: 'name',
						align: 'center',
					},

					{
						title: '动态分类',
						key: 'newsTypeName',
						align: 'center',
						width: 120,
					},
					/*{
						title: '菜单图标',
						key: 'image',
						align: 'center',
						render: (h, params) => {
							return h('img', {
								domProps: {
									src: params.row.image
								},
								style: {
									width: '100px',
									height: '50px'
								}
							})
						}
					},*/
					{
						title: '动态页面链接',
						key: 'path',
						align: 'center'
					},
					{
						title: '图片链接',
						key: 'imgpath',
						align: 'center'
					},

					{
						title: '是否置顶',
						key: 'top',
						align: 'center',
						width: 120,
						render: (h, params) => {
							return h("iSwitch", {
								props: {
									size: "large",
									'true-value': true,
									'false-value': false,
									value: params.row.top
								},
								on: {
									'on-change': (res) => {
										/*  this.repairs();*/
										this.iftop(res, params.index);
									}
								},
							}, [
								h('span', {
									slot: 'open',
									domProps: {
										innerHTML: '是'
									}
								}),
								h('span', {
									slot: 'close',
									domProps: {
										innerHTML: '否'
									}
								})
							])
						}
					},

					{
						title: '是否发布',
						key: 'disabled',
						align: 'center',
						width: 120,
						render: (h, params) => {
							return h("iSwitch", {
								props: {
									size: "large",
									'true-value': false,
									'false-value': true,
									value: params.row.disabled
								},
								on: {
									'on-change': (res) => {
										/*this.repairs(params.index);*/
										this.ifdisabled(res, params.index);
									}
								},
							}, [
								h('span', {
									slot: 'open',
									domProps: {
										innerHTML: '是'
									}
								}),
								h('span', {
									slot: 'close',
									domProps: {
										innerHTML: '否'
									}
								})
							])
						}
					},

					{
						title: '操作',
						key: 'action',
						width: 300,
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
											this.logoahtml = true;
											this.getnewindex = params.index
											this.htmldata = ""
											/*this.repairhtml();*/
										}
									}
								}, '上传html文件'),
								h('Button', {
									props: {
										type: 'success',
										size: 'small'
									},
									style: {
										marginRight: '5px'
									},
									on: {
										click: () => {
											this.logoadd = true;
											this.updata.file = "";
											this.getnewindex = params.index
										}
									}
								}, '上传图片'),
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
											this.modalget = true
											this.getnewdata(params.index);
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
											this.removenew(params.index);

										}
									}
								}, '删除')
							]);
						}
					}
				],
				newDatas: [],
				yes: '全选',
				no: '取消全选',
				statu: '全选',
				model2: '',
				searchConName1: '',
				initTable1: [],
				modalx: false,
				modaladd: false,
				logoadd: false,
				animal: '',
				addcontent: '',
				addname: '',
				addcontent: ''
			}
		},
		methods: {
			//添加动态分类数据结构
			appendCenter: function(index) {

				if(this.newsdatas.length >= 50) {
					alert("分类个数不能超过50")
					return false
				}

				this.subclassName = ""
				this.openshow = !this.openshow
			},

			//取消
			clearYopen: function(index) {
				var eve = event.target
				eve.parentNode.parentNode.classList.remove("iteminput")

				/* if(this.subclassName==""||this.subclassName==null||this.subclassName.trim().length==0||this.datas[index].name.trim().length==0){
				 		this.newsdatas.splice(index,1)
				 		
				 }*/

				this.subclassName = ""

				/* this.rmclass()*/

			},

			//创建确认
			clearlisure: function() {

				/*   for(var i= 0; i<this.newsdatas.length; i++){
				   	console.log(this.newsdatas[i].name)
				   	  if (this.newsdatas[i].name.indexOf(this.subclassName)==-1) {
				   	  	 
				   	  }else{
				   	  	alert("不能出现重复命名")
				   	  	return false;
				   	  }
				   }*/

				if(this.subclassName == "" || this.subclassName == null || this.subclassName.trim().length == 0) {
					alert("请输入子类名称")
					/*this.openshow=false*/
					return false;

				}
				this.$Message.loading('正在操作...', 0);
				var _this = this
				_this.axios({
					method: 'post',

					url: '/api/newstypes',

					data: {
						name: this.subclassName
					}

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}


                   var data=res.data.data
					var obj =data
					console.log(res.data.data)

					_this.newsdatas.push(obj)
					_this.mewtypecacledatas=_this.newsdatas
					_this.newsdatasCacle[obj.id]=obj
					_this.subclassName = ""
					_this.openshow = false
					_this.$Message.success('操作成功');
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');
				})

			},

			//创建取消

			clearliopen: function() {
				this.subclassName = ""
				this.openshow = false

			},

			//双击修改分类
			getYname: function(index) {

				var eve = event.target
				eve.parentNode.classList.add("iteminput")

				this.subclassName = this.newsdatas[index].name

			},

			//修改菜单名称
			clearYsure: function(index) {

				if(this.subclassName == "" || this.subclassName == null || this.subclassName.trim().length == 0) {
					alert("请输入子类名称")
					return false;
				} else if(this.subclassName == this.newsdatas[index].name) {
					var eve = event.target
					eve.parentNode.parentNode.classList.remove("iteminput")
					this.subclassName = ""
					return false;
				}

				var id = this.newsdatas[index].id
				this.$Message.loading('正在操作...', 0);
				var _this = this
				_this.axios({
					method: 'put',

					url: '/api/newstypes/' + id + '',

					data: {
						name: this.subclassName
					}

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}
					_this.$Message.success('操作成功');
					_this.newsdatas[index].name = _this.subclassName
					_this.subclassName = ""
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');
				})

				var eve = event.target
				eve.parentNode.parentNode.classList.remove("iteminput")
			},

			//取消修改菜单名称
			clearYopen: function(index) {
				var eve = event.target
				eve.parentNode.parentNode.classList.remove("iteminput")

				this.subclassName = ""

				/* this.rmclass()*/

			},

			//删除分类
			remYove: function(index) {
				var _this = this
				var id = this.newsdatas[index].id
				this.$Modal.confirm({
                    title: '提示',
                    content: '<p>是否确认删除该内容</p>',
                    onOk: () => {
                       	this.$Message.loading('正在删除...', 0);

				
				_this.axios({
					method: 'DELETE',

					url: '/api/newstypes/' + id + '',

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}
					_this.newsdatas.splice(index, 1)
					_this.$Message.success('删除成功');

				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('删除失败');
				})
                    },
                    onCancel: () => {
                       
                    }
                });

			

			},

			//添加资讯
			addnews: function() {
				this.modalx = true
				this.newtypeid = ""
				this.pname = ""

			},

			//确认添加企业资讯
			addnewok: function() {

				var _this = this
				var newstypeid = this.newtypeid

				if(this.pname == "" || this.pname == null || this.pname.trim().length == 0) {
					alert('请输入标题')
					return false;
				} else if(this.newtypeid == "" || this.newtypeid == null || this.newtypeid.trim().length == 0) {
					alert('请选择动态分类')
					return false;
				}

				this.$Message.loading('正在添加...', 0);

				_this.axios({
					method: 'post',

					url: '/api/news/' + newstypeid,
					data: {
						name: this.pname
					}

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}
					
					

					/*	var obj = res.data.data.news
					obj.newsTypeName = _this.newsdatasCacle[obj.newsTypeId].name
					obj.path=""
					_this.newDatas.push(obj)
					console.log(res.data)
                   _this.totalpage=res.data.pagination.total
                  
                   if(_this.newDatas.length>_this.pageSize){
                   
                   	_this.newDatas.splice(_this.pageSize, 1)
                   }*/

					_this.newdatas()

					_this.$Message.success('添加成功');

				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('添加失败');
				})

			},

			//取消推荐资讯

			addnewno: function() {
				this.pname = "",
					this.newtypeid = ""
			},

			//是否发布
			ifdisabled: function(res, index) {

				var _this = this
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				this.$Message.loading('正在操作...', 0);

				_this.axios({
					method: 'put',

					url: '/api/news/' + newstypeid + '/' + id + '/disabled'

				}).then(function(res) {
					if(res.data.error) {

						//alert(
						_this.$Message.error('操作失败：' + res.data.error.message + '');
						return false

					}

					console.log(res.data)
					_this.$Message.success('操作成功');
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');

				})
			},

			//iftop 是否置顶

			iftop: function(res, index) {

				var _this = this
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				this.$Message.loading('正在操作...', 0);

				_this.axios({
					method: 'put',

					url: '/api/news/' + newstypeid + '/' + id + '/top'

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}

					console.log(res.data)
					_this.$Message.success('操作成功');
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');

				})

			},

			//当前资讯
			removenew(index) {
				  var _this = this
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				   this.$Modal.confirm({
                    title: '提示',
                    content: '<p>确认是否删除该内容</p>',
                    onOk: () => {
                       	const msg = this.$Message.loading('正在删除...', 0);
                       	
                       	_this.axios({
					method: 'DELETE',

					url: '/api/news/' + newstypeid + '/' + id,

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}

					_this.newDatas.splice(index, 1)

					_this.$Message.success('删除成功');

				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('删除失败');
				})
                       	
                    },
                    onCancel: () => {
                        this.$Message.info('点击了取消');
                    }
                });
			

				

				

			},
			// 修改资讯
			getnewdata(index) {

				this.getname = this.newDatas[index].name
				this.newgettypeid = this.newDatas[index].newsTypeName
				this.getnewindex = index

			},

			//修改资讯确认
			modalgetok: function() {
				var _this = this
				var index = this.getnewindex
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				_this.axios({
					method: 'put',

					url: '/api/news/' + newstypeid + '/' + id,

					data: {
						name: this.getname
					}

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}

					_this.newDatas[index].name = _this.getname

					_this.$Message.success('修改成功');

				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('修改失败');
				})

			},

			//qx修改资讯确认
			modalgetno: function() {
				this.modalget = false
			},

			//input获取要上传的文件
			uphtml: function(e) {
				var data = e.target.files[0]
				var datasize = data.size
				var datatype = data.type
				var formData = new FormData();
				console.log(data)
				console.log(datasize)
				console.log(datatype)

				if(datatype != "text/html" && datatype != "text/htm") {
					alert("只能上传html或者htm文件")
					return false;
				} else if(datasize > 2097152) {

					alert("文件大小不能超过2MB")
					return false;
				}

				/* */
				this.htmldata = data.name

				formData.append('files', data);
				var _this = this
				var index = this.getnewindex
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				_this.axios({
					method: 'put',

					url: '/api/news/' + newstypeid + '/' + id + '/htmls',

					data: formData

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						console.log(res.data.error.message)
						return false

					}

					var data = res.data.data
					_this.newDatas[index].path = ""
					_this.newDatas[index].path = data

					console.log(_this.newDatas)

					_this.$Message.success('操作成功');

				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');

				})

			},

			//确认上传html文件

			uphtmlsure: function() {

				this.htmldata = ""

			},
			//取消上传html文件
			uphtmlno: function() {

			},

			upimg: function(e) {
				
                var _this = this
				var index = this.getnewindex
				var id = this.newDatas[index].id
				var newstypeid = this.newDatas[index].newsTypeId
				
				var imgdata = e.target.files
				var formData = new FormData();
				
				for(var i = 0; i < imgdata.length; i++) {

					var imgsize = imgdata[i].size
					var imgstyle = imgdata[i].type
					var name=imgdata[i].name
					
					this.imgName.push(name)
					
					if(this.imgData.accept.indexOf(imgstyle) == -1) {

						this.$Message.error('请选择正确的图片格式');
						return false;
					} else if(imgsize > 5242880) {
						
                       this.$Message.error('图片大小不能超过5M');
						return false;
					} else {

						var datimg = imgdata[i]
						console.log(datimg)
						formData.append('fileList', datimg); // 多选文件数据

                      

					}

				}

			

				_this.axios({
					method: 'post',
					url: '/api/news/' + newstypeid + '/' + id + '/files',
					data: formData

				}).then(function(res) {
                   
                   

				}).catch(function(err) {
					console.log(err)

				})

			},

            //搜索返回
           sodata:function  () {
           	  console.log(this.mewtypecacle)
           	  this.newdatastype()
           },


			//消息提醒
			tip(nodesc) {
				this.$Notice.open({
					title: '信息提醒',
					desc: nodesc ? '' : '删除失败存在该品牌的商品'
				});
				this.$Notice.config({
					top: 200
				});
			},

			//上传图片
			
			//上传文件
			repairhtml(index) {
				/* var id=this.newDatas[index].id
                 var newstypeid = this.newDatas[index].newsTypeId
                 this.wjhtml = '/api/news/'+newstypeid+'/'+id+'/htmls'
                                 
*/

				/*repairid = this.data6[index].id;
				logoindex = index
				this.pname = this.data6[index].name;
				this.pdes = this.data6[index].url;*/

			},
			handleSelectAll(status) {
				if(this.one == false) {
					this.$refs.selection.selectAll(false);
					this.statu = this.yes;
					this.one = true
				} else {
					this.$refs.selection.selectAll(true);
					this.statu = this.no;
					this.one = false;
				}

			},
			selected(selection) {
				arrsid = {};
				arrids = [];
				for(var i = 0; i <= selection.length - 1; i++) {
					arrsid[selection[i].id] = true;
					arrids.push(selection[i].id)
				}
			},
			//请求企业动态数据信息 
			newdatas() {
				var _this = this
				this.$Message.loading('正在操作...', 0);
				_this.axios({
					method: 'get',
					url:'/api/news?pageNum=' + this.newpage + '&pageSize=' + this.pageSize + ''

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}
					var datas = res.data.data

					_this.totalpage = res.data.pagination.total

					for(var i = 0; i < datas.length; i++) {
						datas[i].imgpath='/news/'+datas[i].newsTypeId+'/'+datas[i].id
						var newstypeid = datas[i].newsTypeId
						datas[i].newsTypeName = _this.newsdatasCacle[newstypeid].name
						
						if(!datas[i].path){
							datas[i].path=""
						}
						
					};

					_this.newDatas = res.data.data
					console.log(res.data)
					_this.$Message.success('操作成功');
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');

				})

			},
            
            newdatastype() {
				var _this = this
                 
              
                 
                if ( this.mewtypecacle=="000") {
                var path='/api/news?pageNum=' + this.newpage + '&pageSize=' + this.pageSize + ''
                } else{
                
                var	path='/api/news/'+this.mewtypecacle+'?pageNum=' + this.newpage + '&pageSize=' + this.pageSize + ''
                }



				this.$Message.loading('正在操作...', 0);
				_this.axios({
					method: 'get',
					url:path

				}).then(function(res) {
					if(res.data.error) {

						alert(res.data.error.message)
						return false

					}
					var datas = res.data.data

					_this.totalpage = res.data.pagination.total

					for(var i = 0; i < datas.length; i++) {
						datas[i].imgpath='/news/'+datas[i].newsTypeId+'/'+datas[i].id
						var newstypeid = datas[i].newsTypeId
						datas[i].newsTypeName = _this.newsdatasCacle[newstypeid].name
						
						if(!datas[i].path){
							datas[i].path=""
						}
						
					};

					_this.newDatas = res.data.data
					console.log(res.data)
					_this.$Message.success('操作成功');
				}).catch(function(err) {
					console.log(err)
					_this.$Message.error('操作失败');

				})

			},
              
			pagenewdatas(index) {
				console.log(index)
				this.newpage = index
				this.newdatas()
			},

			/*//修改图标信息
			modifybrand() {
				var that = this
				this.axios({
					method: 'put',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/homenavs/'+ repairid,
					data: {
						name: that.pname,
						url: that.pdes
					}
				}).then(function(res) {
					var goodsType = _.find(that.data6, function(o) {
						return o.id === repairid;
					});
					if(goodsType) {
						goodsType.name = that.pname;
						goodsType.url = that.pdes;
					}
				})
			},*/
			//上传logo
			handleUpload(file) {

				this.updata.file = file;
				console.log(file)
			},

			//上传成功返回数据
			newlogo(file) {

				console.log(file)

				/*this.data6[logoindex].image = file.data.image;*/
			},

			//提示框的取消与确认
			ok() {
				/*var evens = _.filter(this.data6, function(n) {
					return !arrsid[n.id];
				});
				this.data6 = evens;
				this.axios({
					method: 'delete',
					url: '/api/brands/' + arrids,
				})
				this.$Message.info('信息已经删除');*/
			},
			cancel() {
				this.$Message.info('信息取消了删除');
			},
			/*modelok() {
				this.modifybrand();
				this.$Message.info('修改完成');
			},*/
			/*modelcancel() {
				this.$Message.info('你取消了修改');
			},*/
			addok() {
				this.addbrands();
				this.$Message.info('完成添加');
			},
			addcancel() {
				this.$Message.info('取消了添加');
			},
			logook() {
				this.$Message.info('完成logo添加');
			},
			logocancel() {
				this.$Message.info('取消了logo添加');
			},
			handleSearch1() {
				this.data6 = this.initTable1;
				this.data6 = this.search(this.data6, {
					name: this.searchConName1
				});
			}
		},
		mounted() {

			var newsdatascacle = this.newsdatasCacle
			var _this = this
			_this.axios({
				method: 'get',

				url: '/api/newstypes'

			}).then(function(res) {
				if(res.data.error) {

					alert(res.data.error.message)
					return false

				}
				var datas = res.data.data
				_this.newsdatas = res.data.data
				
				_this.mewtypecacledatas=res.data.data
				
				//搜索数据
				/*var ob={
					name:"全部",
					id:"000",
					disabled:false,
				}
				
				_this.mewtypecacledatas.unshift(ob)*/
				
				var newtyidshow;
				var newtyid;
				for(var i = 0; i < datas.length; i++) {

					newtyid = datas[i].id
					newtyidshow = newsdatascacle[newtyid]
					if(!newtyidshow) {

						newtyidshow = datas[i]

					}

					newsdatascacle[newtyid] = newtyidshow

					/* _this.newsdatasCacle=newsdatascacle*/
				}

				/*console.log(  _this.newsdatasCacle["469u9ow9cutc"])*/
				_this.newdatas();
				console.log(res.data.data)

			}).catch(function(err) {
				console.log(err)

			})

		}
	}
</script>

<style>
	ul {
		list-style: none;
	}
	
	.newdataclass {
		width: 100%;
		display: inline-block;
		
	}
	
	.newdataclass p {
		float: left;
		font-size: 12px;
		color: #333333;
		line-height: 30px;
		margin-right: 10px;
	}
	
	.newdataclass ul {
		float: left;
		margin-bottom: 20px;
	}
	
	.newdataclass ul li {
		float: left;
		height: 30px;
		font-size: 14px;
		padding: 0px 8px;
		line-height: 30px;
		border: solid 1px #EEEEEE;
		cursor: pointer;
		margin-right: 8px;
		position: relative;
	}
	
	.newdataclass ul li.but {
		color: white;
		border: solid 1px cornflowerblue;
		background: cornflowerblue;
		border-radius: 3px;
	}
	
	.newdataclass ul li .alertinput {
		background: white;
		width: 185px;
		position: absolute;
		top: -1px;
		left: -1px;
		z-index: 2;
		border: solid #D2D2D2 1px;
	}
	
	.newdataclass ul li .alertinput input {
		width: 100%;
		height: 30px;
		line-height: 30px;
		border: none;
		font-size: 14px;
		outline: none;
		display: block;
		display: block;
		padding-left: 5px;
	}
	
	.newdataclass ul li .alertinput span {
		line-height: 28px;
		background: none;
		width: 30px;
		text-align: center;
		border: none;
		font-size: 13px;
		display: block;
		float: left;
		cursor: pointer;
	}
	
	.newdataclass ul li .alertinput span:hover {
		opacity: 0.8;
	}
	
	.newdataclass ul li .alertinput {
		display: none;
	}
	
	.newdataclass ul li.iteminput .alertinput {
		display: block;
	}
	
	.newdataclass ul li small {
		position: absolute;
		top: 0px;
		right: 2px;
		display: block;
		font-size: 13px;
		line-height: 28px;
		cursor: pointer;
	}
	
	.newdataclass button {
		clear: both;
		
		background: #009687;
		color: white;
		padding: 5px 10px;
		font-size: 14px;
		border: none;
		border-radius: 5px;
		margin-top: 20px;
		cursor: pointer;
	}
	
	.uphtml {
		position: relative;
		width: 60px;
		height: 60px;
		border: dashed 1px #666666;
		text-align: center;
		font-weight: 100;
		font-size: 30px;
		line-height: 60px;
		cursor: pointer;
		overflow: hidden;
	}
	
	.uphtml input {
		position: absolute;
		display: block;
		width: 60px;
		height: 60px;
		top: 0px;
		left: 0px;
		opacity: 0;
	}
	
	.uphtml i {
		width: 100%;
		display: block;
		line-height: 60px;
		font-size: 30px;
	}
	
	.upimg {
		display: block;
	}
	
	.upimg li {
		
		display: block;
		
		width: 60px;
		height: 60px;
		border: dashed 1px #666666;
		margin-right: 10px;
	}
	
	.upimg li img {
		display: block;
		width: 100%;
		height: 100%;
	}
	
		.upimg span{
			display: block;
			width: 100%;
			line-height: 1.5em;
		}
	
	.tab-title{
	display: inline-block;
		width: 100%;
		clear: both;
		margin-bottom: 20px;
	}
	
	
	.tab-title button {
		clear: both;
		
		background: #009687;
		color: white;
		padding: 0px 10px;
		height: 32px;
		font-size: 14px;
		border: none;
		border-radius: 5px;
		
		cursor: pointer;
	}
</style>