<style>
	@import './addgoods.css';
</style>
<template>
	<div class="contain">
		<div style="overflow:scroll; width:100%; height:800px;">
			<div class="item-title">
				<div class="miaoshu">
					<h3 style="color: #1C8FEF;">商品管理</h3>
					<h5>商城所有商品索引及管理</h5>
				</div>
			</div>
			<div class="addgoods">
				<h4 class="productinformation">商品基本信息</h4>
				<dl>
					<dt><span class="redstar">*</span>商品名称:</dt>
					<dd>
						<Input v-model="goodname" placeholder="请输入商品的名称,不能超过60个字" style="width: 600px"></Input>
					</dd>
				</dl>
				<dl>
					<dt><span class="redstar">*</span>商品品牌:</dt>
					<dd>
						<Select v-model="brands" style="width:600px" on-change>
							<Option v-for="item in brand" :value="item.value" :key="item.value">{{ item.label }}</Option>
						</Select>
					</dd>
				</dl>
				<dl>
					<dt><span class="redstar">*</span>商品运费:</dt>
					<dd>
						<Input v-model="freight" placeholder="请输入商品的运费,不能超过60个字" style="width: 600px"></Input>
					</dd>
				</dl>
				<dl>
					<dt></dt>
					<dd>
						<div style="float: right;margin-right: 50px;">
							<Button type="success" @click="regoods">保存商品基本信息</Button>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>商品标签:</dt>
					<dd>
						<Select v-model="model10" multiple style="width:600px" @on-change="selecttags">
							<Option v-for="item in spec" :value="item.id" :key="item.id">{{ item.name }}</Option>
						</Select>
					</dd>
				</dl>
				<dl>

				</dl>
				<h4 class="productinformation">
					商品类型:
				</h4>
				<dl>
					<dt><br /><br />					
						<Button @click="creatprice">生成价格表</Button><br /></dt>
					<dd>
						<table class="tableform">
							<tr>
								<td colspan="2">
									<div style="text-align: left;">
										<h5 style="margin:0;padding:0;font-weight: normal;color: #FF8400;">操作提示</h5>
										<p style="color:#FF8400;font-size:12px;">1、点击生成价格表按钮可以产生价格的报价。</p>
										<p style="color:#FF8400;font-size:12px;">2、点击提交价格表可以对产生的报价进行提交。</p>
									</div>
								</td>
							</tr>
							<tr v-for="(items,index) in trlist">
								<td style="width: 10%;text-align: center;line-height: 50px; padding: 4px;border: 1px solid #e5e5e5; background: #FBFBFB;">{{items.name}} </td>
								<td class="sortnum" style="width: 85%;height: 30px;line-height: 30px;">
									<span class="sort" @click="selected(index,indexs,$event)"
										@mouseenter="mousein(index,indexs,$event)"
										v-for="(item,indexs) in items.data" 
										:class="{sorts:item.iftable==true}">
										{{item.name}} 
										&nbsp;<Icon type="close" style="cursor: pointer;" @click="close(index,indexs,$event)"></Icon>
									</span>
									<span class="addsort" @click="addsorts(index)" v-if="items.adds">添加规格值</span>
									<span style="display: inline;" v-if="items.addt">
					<Input v-model="inputvalue" placeholder="请输入规格值"  style="width: 200px;"></Input>
					<Button type="info" @click="oks(index)" size='small'>确定</Button>
					<Button type="ghost" size='small' @click="concels(index)">取消</Button>
					</span>
								</td>
							</tr>
							<tr>
								<td style="width: 10%;text-align: center;line-height: 50px; padding: 4px;border: 1px solid #e5e5e5;background: #FBFBFB;">
									<span class="addsort" @click="addsortx">添加规格</span></td>
							</tr>
						</table>
					</dd>
				</dl>
				<dl>
					<dt><span style="padding: 18px;">商品价格</span><br /><br />			
			<Button @click="upprice">提交价格表</Button>	
			</dt>
					<dd>
						<div class="edittable-table-height-con">
							<can-edit-table refs="table4" v-model="editInlineAndCellData" :editIncell="true" :columns-list="editInlineAndCellColumn"></can-edit-table>
						</div>
					</dd>
				</dl>
				<h4 class="productinformation">商品图片上传</h4>
				<dl>
					<dt style="text-align: center;padding: 10px 0 8px 0;">
						<Upload
        ref="upload"
        :show-upload-list="false"
        :default-file-list="defaultList"
        :on-success="handleSuccess"
        :format="['jpg','jpeg','png']"
        :max-size="2048"
        :on-format-error="handleFormatError"
        :on-exceeded-size="handleMaxSize"
        :before-upload="handleBeforeUpload"
        multiple
        type="drag"
        :action="goodsurl" 
        style="display: inline-block;width:58px;">
        <div style="width: 58px;height:58px;line-height: 58px;">
            <Icon type="ios-camera" size="20"></Icon>
        </div>
    </Upload>
					</dt>
					<dd>
						<div class="demo-upload-list" v-for="(item,index) in uploadList">
							<template v-if="item.status === 'finished'">
								<img :src="item.path">
								<div class="demo-upload-list-cover" style="padding-top: 25px;">
									<Icon type="ios-eye-outline" @click.native="handleView(item.path)"></Icon>
									<Icon type="ios-trash-outline" @click.native="handleRemove(index)"></Icon>
									<Icon type="ios-build-outline" @click.native="handleRemove(index)"></Icon>
								</div>
							</template>
							<template v-else>
								<Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
							</template>
						</div>
						<Modal title="View Image" v-model="visible">
							<img :src="imgs" v-if="visible" style="width: 100%">
						</Modal>
					</dd>
				</dl>

				<h4 class="productinformation">商品详情</h4>
				<!--富文本编辑器开始-->
				<div id="summernote" ref="summernote">
					<!--<el-row :gutter="100">
      <vue-summernote ref="editer"></vue-summernote>
      <vue-summernote ref="editer1"></vue-summernote>
    </el-row> -->
					<!--<button @click="setVal">初始化</button>
     <button @click="getVal">获取</button>-->
				</div>
				<div style="float: right;margin-top: 10px;margin-right: 100px;">
					<Button type="info" @click="getVal">保存商品详情</Button>
				</div>

				<!--富文本编辑器结束-->
			</div>
			<!--商品规格添加-->
			<Modal v-model="modal" title="添加商品的规格" @on-ok="ok" @on-cancel="cancel">
				<Input v-model="typevalue" placeholder="请输入商品的成本价,不能超过60个字" style="width: 200px"></Input>
			</Modal>
			<!--上传报价图片开始{手动}-->
			<Modal v-model="modaloffer" title="请选择需要上传报价的图片" @on-ok="offerok" @on-cancel="offercancel">
				<div>
					<Upload :action="upphotourl"  :on-success="Success">
						<Button icon="ios-cloud-upload-outline">Upload files</Button>
					</Upload>
					<div v-if="file !== null">Upload file: {{ file.name }}
						<Button type="text" @click="upload" :loading="loadingStatus">{{ loadingStatus ? 'Uploading' : 'Click to upload' }}</Button>
					</div>
				</div>
			</Modal>
			<!-- 上传报价图片结束-->
			<!--商品规格结束-->
			<!--<div class="footer">
				<div style="text-align: center;">
					<Button type="success">保存商品</Button><span style="width:20px;height: auto;display: inline-block;"></span>
					<Button type="info">保存商品并且预览</Button>
				</div>
			</div>-->
		</div>
	</div>
</template>
<script>
	var a = [];
	var tablearr = [];
	var groups = [];
	var arr = [];
	var goodsid = '';
	var tuarr = [];
	var obj = {};
	var objarr = [];
	var extend = [];
	var optionarr = [];
	var alltype = [];
	var cx = [];
	var price;
	var uptuindex;
	var dest;
	import 'bootstrap/dist/css/bootstrap.css'
	import 'font-awesome/css/font-awesome.css'
	import canEditTable from './canEditTable.vue';
	import tableData from './table_data.js';
	export default {
		name: 'editable-table',
		name: 'hello',
		data() {
			return {
				trlist: [
					//          	   {name:'重量',
					//          	   data:[
					//          	   {name:'200g',iftable:false},
					//          	   {name:'300g',iftable:false},
					//          	   {name:'400g',iftable:false}
					//          	   ],
					//          	   adds:true,
					//                 addt:false,
					//          	  }
				],
				defaultList: [{
						'name': 'a42bdcc1178e62b4694c830f028db5c0',
						'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
					},
					{
						'name': 'bc7521e033abdd1e92222d733590f104',
						'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
					}
				],
				value: 0,
				imgs: '',
				uploadList: [],
				modaloffer: false,
				goodsurl: '',
				imgName: '',
				model10: [],
				cityList: [],
				arrlist: [],
				freight:'',
				visible: false,			
				inputvalue: '',
				goodname: '',
				keyword: '',
				brands: '',
				originalprice: '',
				price: '',
				model1: '',
				goodsTypeId: '',
				modal: false,
				typevalue: '',
				editInlineAndCellColumn: [],
				editInlineAndCellData: [],
				tableData: [],
				specId: '',
				goodsid: '',
				viewphoto: '',
				defaultlist: [],
				spec: [],
				temparr: [],
				brand: [{
					value: '日常',
					label: '日常'
				}],
				spec: [],
				file: null,
				loadingStatus: false,
				uedata: [],
				ue: '',
				xierudata: [],
				spec1: '',
				spec2: '',
				spec3: '',
				upphotourl: '',
				extendList: '',
				goodsExtendId:'',
				arrlistx:'',
				xshow:false
			}
		},
		methods: {
			//获取商品详情信息
			getproduct() {
				var that = this;
				var temarr = [];
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods/' + goodsid
				}).then(function(res) {
					//加载富文本信息
					console.log(res)
					$("#summernote").summernote("code", res.data.data.goods.content);
					//加载选中的商品信息
					that.brands = res.data.data.goods.brandId;
					that.freight=res.data.data.goods.freight
					var cc = [];//标签数组
					for(var i = 0; i < res.data.data.tags.length; i++) {
						cc.push(res.data.data.tags[i].tagId)
					}
					that.model10=cc;			
					that.arrlist=res.data.data.goodsSpecsOptionsList;
					that.arrlistx=res.data.data.goodsExtendList	
					console.log(that.arrlistx)
					console.log(groups)
					if (that.arrlist.length==0||that.arrlistx.length==0) {
						return false;
					}else{
						for(var i = 0; i < groups.length; i++){
							if (groups[i].data) {
							for(var c = 0; c < groups[i].data.length; c++) {
							for(var a = 0; a < that.arrlist.length; a++) {
								for (var b=0;b<that.arrlist[a].options.length;b++) {
									if(that.arrlist[a].options[b] == groups[i].data[c].id) {
									   groups[i].data[c].iftable = true;
								    }
								}
						     }
					}	
							}
											
					}
					that.trlist.splice(1, 0);
					that.creatprice2();
					}										
				})
			},

			//获取上传图片对象
			handleUpload(file) {
				this.file = file;
				return false;
			},
			//           /*进行手动上传*/
			upload() {
				this.loadingStatus = true;
				setTimeout(() => {
					this.file = null;
					this.loadingStatus = false;
					this.$Message.success('Success')
				}, 1500);
			},
			// 删除不需要的报价
			remove(index) {
				this.editInlineAndCellData.splice(index, 1)
				console.log(index)
			},
			//上传报价图片开始
			upphotourlx(index) {
				uptuindex=index;
				if(this.editInlineAndCellData[index].ids){
				alert(123)
				this.upphotourl = '/api/goods/' + goodsid+ '/goodsextends/' + this.editInlineAndCellData[index].ids + '/files'	
				}else{
			    this.upphotourl = '/api/goods/' + goodsid + '/goodsextends/' + this.editInlineAndCellData[index].extendid + '/files'
				}
				
			},
			//上传报价结束
			//请求该分类下的所有规格以及规格值开始  
			postproduct() {
				var that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstype/' + that.goodsTypeId + '/specs'
				}).then(function(res) {
					for(var i = 0; i < res.data.data.length; i++) {
						groups.push({
							id: res.data.data[i].id,
							name: res.data.data[i].name,
							adds: true,
							addt: false,
							data:[]
						})
					}
					that.trlist = groups;
					that.gettype();//处理规格
				})
			},
			//请求该分类下所有的规格值
			gettype() {
				var that = this;
				var resarr = [];
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstypes/all',
				}).then(function(res) {
					alltype = res.data.data.specOption
					for(var i = 0; i < res.data.data.specOption.length; i++) {
						for(var b = 0; b < groups.length; b++) {
							if(groups[b].id == res.data.data.specOption[i].goodsSpecId) {
								res.data.data.specOption[i].Specname = groups[b].name
								res.data.data.specOption[i].iftable = false
								arr.push(res.data.data.specOption[i])
							}
						}
					}
					var map = {},
						dest = [];
					for(var i = 0; i < arr.length; i++) {
						var ai = arr[i];
						if(!map[ai.goodsSpecId]) {
							dest.push({
								id: ai.goodsSpecId,
								data: [ai]
							});
							map[ai.goodsSpecId] = ai;
						} else {
							for(var j = 0; j < dest.length; j++) {
								var dj = dest[j];
								if(dj.id == ai.goodsSpecId) {
									dj.data.push(ai);
									break;
								}
							}
						}					
					}
					for(var i = 0; i < dest.length; i++) {
						for(var s = 0; s < that.trlist.length; s++) {
							if(dest[i].id == that.trlist[s].id) {
								that.trlist[s].data = dest[i].data
							}
						}
					}
					that.trlist.splice(1, 0);
					that.getproduct();//处理基本商品加载
				})		
			}, //商品给规格添加规格值结束
			selected(index, indexs, event) {			
				if(this.trlist[index].data[indexs].iftable == false) {
					//改变点击的样式
					this.trlist[index].data[indexs].iftable = true;
					this.trlist.splice(1, 0)
				} else if(this.trlist[index].data[indexs].iftable == true) {
					//改变点击的样式
					this.trlist[index].data[indexs].iftable = false;
					this.trlist.splice(1, 0)
				}
			},
			mousein(index, indexs, event){
				console.log(index)
			},
			creatprice() {
				/*添加类别*/
				this.editInlineAndCellColumn = [{
						title: '原始价',
						align: 'center',
						key: 'originalprice',
						editable: true
					},
					{
						title: '销售价',
						align: 'center',
						key: 'price',
						editable: true
					},
					{
						title: '销售量',
						align: 'center',
						key: 'sales',
						editable: true
					},
					{
						title: '库存',
						align: 'center',
						key: 'stock',
						editable: true
					},
					{
						title: '报价图片',
						key: 'bigimage',
						align: 'center',
						render: (h, params) => {
							return h('img', {
								domProps: {
									src: params.row.bigimage
								},
								style: {
									width: '100px',
									height: '50px'
								}
							})
						}
					},
					{
						title: '操作',
						align: 'center',
						render: (h, params) => {
							return h('div', [
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
											this.modaloffer = true;
											this.upphotourlx(params.index);
										}
									}
								}, '上传图片'),
								h('Button', {
									props: {
										type: 'error',
										size: 'small'
									},
									on: {
										click: () => {
											this.remove(params.index);
										}
									}
								}, '删除')
							]);
						}
					}

				]
                console.log(this.trlist)
				for(var i = 0; i < this.trlist.length; i++) {
					if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {
						if(this.trlist[i].data[c].iftable == true) {
							tablearr.push(this.trlist[i].name)
						}
					}	
					}									
				}
				var arrtmps = _.uniq(tablearr)
				for(var b = 0; b < arrtmps.length; b++) {
					var obj = {};
					obj.title = arrtmps[b];
					obj.key = arrtmps[b];
					obj.align = 'center';
					this.editInlineAndCellColumn.unshift(obj);
				}
				//给表添加数据(1先找到选中的数据)	  
				var dataobj = {};
				for(var i = 0; i < this.trlist.length; i++) {
					if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {
						if(this.trlist[i].data[c].iftable == true) {
							dataobj[this.trlist[i].name] = '';
						}
					}
					}
				}
				var dd = [];
				for(var i = 0; i < this.trlist.length; i++) {
				if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {
						if(this.trlist[i].data[c].iftable == true) {
							dd.push(this.trlist[i].data[c])
						}
					}
					}
				}
				var arrs = dd
				var map = {},
					dests = [];
				for(var i = 0; i < arrs.length; i++) {
					var ais = arrs[i];
					if(!map[ais.Specname]) {
						dests.push({
							id: ais.Specname,
							data: [ais]
						});
						map[ais.Specname] = ais;
					} else {
						for(var j = 0; j < dests.length; j++) {
							var djs = dests[j];
							if(djs.id == ais.Specname) {
								djs.data.push(ais);
								break;
							}
						}
					}
				}
				console.log(dests)
				if(dests.length == 1) {
					var ss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						var obj1 = {};
						obj1[dests[0].data[i].Specname] = dests[0].data[i].name;
						obj1.originalprice = 0
						obj1.price = 0
						obj1.sales = 0
						obj1.stock = 0
						obj1.options = dests[0].data[i].id
						obj1.defaults = false					
						ss.push(obj1)
					}
					this.editInlineAndCellData = ss;
				} else if(dests.length == 2) {
					var sss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						for(var b = 0; b < dests[1].data.length; b++) {
							var obj2 = {};
							obj2[dests[0].data[i].Specname] = dests[0].data[i].name
							obj2[dests[1].data[b].Specname] = dests[1].data[b].name
							obj2.originalprice = 0
							obj2.price = 0
							obj2.sales = 0
							obj2.stock = 0
							obj2.options = dests[0].data[i].id + "," + dests[1].data[b].id
							obj2.defaults = false
							sss.push(obj2)
						}
					}
					this.editInlineAndCellData = sss;
				} else if(dests.length == 3) {
					var ssss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						for(var b = 0; b < dests[1].data.length; b++) {
							for(var a = 0; a < dests[2].data.length; a++) {
								var obj3 = {};
								obj3[dests[0].data[i].Specname] = dests[0].data[i].name
								obj3[dests[1].data[b].Specname] = dests[1].data[b].name
								obj3[dests[2].data[a].Specname] = dests[2].data[a].name
								obj3.originalprice = 0,
									obj3.price = 0,
									obj3.sales = 0,
									obj3.stock = 0,
									obj3.options = dests[0].data[i].id + "," + dests[1].data[b].id + "," + dests[2].data[a].id
								obj3.defaults = false
								ssss.push(obj3)
							}
						}
					}
					this.editInlineAndCellData = ssss;
				} else if(dests.length > 3) {
					this.open();
				}
		      console.log(this.editInlineAndCellData)
		      for (var c=0;c<this.arrlistx.length;c++) {
		      	for (var a=0;a<this.editInlineAndCellData.length;a++) {
		      		if (this.arrlistx[c].options.split(",").sort().toString()==this.editInlineAndCellData[a].options.split(",").sort().toString()) {
		      			this.editInlineAndCellData[a].ids=this.arrlistx[c].id
		      			this.editInlineAndCellData[a].bigimage=this.arrlistx[c].bigimage
		      		}
		      		
		      	}		      	   
		      }
		      console.log(this.editInlineAndCellData)
			},
			creatprice2() {
				/*添加类别*/
				this.editInlineAndCellColumn = [{
						title: '原始价',
						align: 'center',
						key: 'originalprice',
						editable: true
					},
					{
						title: '销售价',
						align: 'center',
						key: 'price',
						editable: true
					},
					{
						title: '销售量',
						align: 'center',
						key: 'sales',
						editable: true
					},
					{
						title: '库存',
						align: 'center',
						key: 'stock',
						editable: true
					},
					{
						title: '报价图片',
						key: 'bigimage',
						align: 'center',
						width:140,
						render: (h, params) => {
							return h('img', {
								domProps: {
									src: params.row.bigimage
								},
								style: {
									width: '100px',
									height: '50px'
								}
							})
						}
					},
					{
						title: '操作',
						align: 'center',
						width:220,
						render: (h, params) => {
							return h('div', [
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
											this.modaloffer = true;
											this.upphotourlx(params.index);
										}
									}
								}, '上传图片'),
								h('Button', {
									props: {
										type: 'info',
										size: 'small'
									},
									style: {
										marginRight: '5px'
									},
									on: {
										click: () => {
											this.reprice(params.index);
										}
									}
								}, '修改报价'),
								h('Button', {
									props: {
										type: 'error',
										size: 'small'
									},
									on: {
										click: () => {
											this.remove(params.index);
										}
									}
								}, '删除')
							]);
						}
					}

				]
				for(var i = 0; i < this.trlist.length; i++) {
					if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {						
						if(this.trlist[i].data[c].iftable == true) {
							tablearr.push(this.trlist[i].name)
						}	
						}					
					}
				}
				var arrtmps = _.uniq(tablearr)
				for(var b = 0; b < arrtmps.length; b++) {
					var obj = {};
					obj.title = arrtmps[b];
					obj.key = arrtmps[b];
					obj.align = 'center';
					this.editInlineAndCellColumn.unshift(obj);
				}
				//给表添加数据(1先找到选中的数据)	  
				var dataobj = {};
				for(var i = 0; i < this.trlist.length; i++) {
					if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {
						if(this.trlist[i].data[c].iftable == true) {
							dataobj[this.trlist[i].name] = '';
						}
					}
					}
				}
				var dd = [];
				for(var i = 0; i < this.trlist.length; i++) {
					if (this.trlist[i]["data"]) {
					for(var c = 0; c < this.trlist[i].data.length; c++) {
						if(this.trlist[i].data[c].iftable == true) {
							dd.push(this.trlist[i].data[c])
						}
					}
					}
				}
				var arrs = dd
				var map = {},
				dests = [];
				for(var i = 0; i < arrs.length; i++) {
					var ais = arrs[i];
					if(!map[ais.Specname]) {
						dests.push({
							id: ais.Specname,
							data: [ais]
						});
						map[ais.Specname] = ais;
					} else {
						for(var j = 0; j < dests.length; j++) {
							var djs = dests[j];
							if(djs.id == ais.Specname) {
								djs.data.push(ais);
								break;
							}
						}
					}
				}
				if(dests.length == 1) {
					var ss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						var obj1 = {};
						obj1[dests[0].data[i].Specname] = dests[0].data[i].name;
						obj1.originalprice = 0
						obj1.price = 0
						obj1.sales = 0
						obj1.stock = 0
						obj1.options = dests[0].data[i].id
						obj1.defaults = false
						
						ss.push(obj1)
					}
					this.editInlineAndCellData = ss;
					console.log(ss)
				} else if(dests.length == 2) {
					var sss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						for(var b = 0; b < dests[1].data.length; b++) {
							var obj2 = {};
							obj2[dests[0].data[i].Specname] = dests[0].data[i].name
							obj2[dests[1].data[b].Specname] = dests[1].data[b].name
							obj2.originalprice = 0
							obj2.price = 0
							obj2.sales = 0
							obj2.stock = 0
							obj2.options = dests[0].data[i].id + "," + dests[1].data[b].id
							obj2.defaults = false
							sss.push(obj2)
						}
					}
					this.editInlineAndCellData = sss;
				} else if(dests.length == 3) {
					var ssss = [];
					for(var i = 0; i < dests[0].data.length; i++) {
						for(var b = 0; b < dests[1].data.length; b++) {
							for(var a = 0; a < dests[2].data.length; a++) {
								var obj3 = {};
								obj3[dests[0].data[i].Specname] = dests[0].data[i].name
								obj3[dests[1].data[b].Specname] = dests[1].data[b].name
								obj3[dests[2].data[a].Specname] = dests[2].data[a].name
								obj3.options = dests[0].data[i].id + "," + dests[1].data[b].id + "," + dests[2].data[a].id
								obj3.defaults = false
								ssss.push(obj3)
							}
						}
					}
					this.editInlineAndCellData = ssss;
					console.log(ssss)
				} else if(dests.length > 3) {
					this.open();
				}
		      
		      for (var c=0;c<this.arrlistx.length;c++){	      	
		      	for (var a=0;a<this.editInlineAndCellData.length;a++) {
		      		if (this.arrlistx[c].options.split(",").sort().toString()==this.editInlineAndCellData[a].options.split(",").sort().toString()) {
		      			this.editInlineAndCellData[a].ids=this.arrlistx[c].id
		      			this.editInlineAndCellData[a].bigimage=this.arrlistx[c].bigimage
		      			this.editInlineAndCellData[a].originalprice = this.arrlistx[c].originalprice
						this.editInlineAndCellData[a].price = this.arrlistx[c].price
						this.editInlineAndCellData[a].sales = this.arrlistx[c].sales
						this.editInlineAndCellData[a].stock =this.arrlistx[c].stock 
		      		}		      			     		
		      	}		      	   
		      }
			},
			//确认价格表
			okprice() {
				console.log(this.editInlineAndCellData);
			},
			//确认价格表取消
			//判断大于三级规格
			open(nodesc) {
				this.$Notice.open({
					title: '规格错误提醒',
					desc: nodesc ? '' : '规格的数量不能超过三个,请重新选择'
				});
			},
			//添加规格以及规格值的样式
			getdata() {
				this.editInlineAndCellData = tableData.editInlineAndCellData;
			},
			addsorts(index) {			
					this.trlist[index].adds = false;
				    this.trlist[index].addt = true;					
			},
			//添加规格以及规格值的样式
			//添加规格值开始
			oks(index) {
				var that = this
				this.trlist[index].adds = true;
				this.trlist[index].addt = false;
				this.specId = this.trlist[index].id;
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstype/' + that.goodsTypeId + '/spec/' + that.specId + '/options',
					data: {
						name: that.inputvalue
					}
				}).then(function(res) {
					var a = res.data
				})
				this.trlist[index].data.push({
					name: this.inputvalue,
					iftable: false
				});
				console.log(this.trlist)
				this.inputvalue = '';
			},
			concels(index){
				this.inputvalue = '';
				this.trlist[index].adds = true;
				this.trlist[index].addt = false;							
			},
			/*添加规格开始*/
			addsortx() {
				if (this.trlist.length>3) {
					this.openm();
				}else{
					this.modal = true;
				}				
			}, 
			openm (nodesc) {
                this.$Notice.open({
                    title: '添加规格错误',
                    desc: nodesc ? '' : '规格最大数量为三个. '
                });
           },
			/*添加规格结束*/
			newstylecode() {
				this.sites.push({
					name: this.datas
				})
			},
			//添加规格
			ok() {
				var that=this;
				if (this.typevalue==''||this.typevalue==null) {
				  alert('不能规格为空');
				  return false
				} else{
					var that = this
					this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goodstype/' + that.goodsTypeId + '/specs',
					data: {
						name: that.typevalue
					}
				}).then(function(res) {
					console.log(res.data.data.id)
					that.trlist.push({
					name: that.typevalue,
					data: [],
					adds: true,
					addt: false,
					id:res.data.data.id
				})
				that.$Message.info('Clicked ok');
				console.log(that.trlist)
				})
				
				}								
			},
			//获取品牌信息
			requestbrands() {
				var that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/brands'
				}).then(function(res) {
					for(var i = 0; i < res.data.data.length; i++) {
						if(res.data.data[i].disabled == false) {
							var ob = {
								value: res.data.data[i].id,
								label: res.data.data[i].name
							}
							that.brand.push(ob)
						}

					}

				})
			},
			/*获取商品轮播图信息*/
			requesttu() {
				var that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods/' + goodsid + '/goodsshows'
				}).then(function(res) {
					var defaultlist = res.data.data
					for(var i = 0; i < defaultlist.length; i++) {
						defaultlist[i].path = defaultlist[i].path
						defaultlist[i].status = 'finished'
					}
					that.uploadList = defaultlist
				})

			},
			//上传报价开始
			upprice() {
				var that = this
				price = this.editInlineAndCellData
				console.log(price);
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					data: price,
					url: '/api/goods/' + goodsid + '/goodsextends'
				}).then(function(res) {
					console.log(res)
					for(var i = 0; i < res.data.data.length; i++) {
						for(var a = 0; a < price.length; a++) {
							if(price[a].options == res.data.data[i].options) {
								price[a].id = res.data.data[i].goodsId;
								price[a].extendid = res.data.data[i].id;
							}
						}
					}
				})
			},
			//上传报价结束
			//上传报价图片开始
			upphoto() {
				var that = this
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					data: price,
					url: '/api/goods/' + goodsid + '/goodsextends/' + goodsExtendId + '/files'
				}).then(function(res) {
					console.log(res.data.data)
				})
			},
			//上传报价图片结束
			//修改商品信息开开始
			regoods(){
				var that = this
				console.log(this.brands)
				this.axios({
					method: 'put',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods/' + goodsid,
					data: {
						name: that.goodname,
						brandId: that.brands,
						freight:that.freight
					}
				}).then(function(res) {
					   console.log(res)
				})
			},
			//修改商品结束
			//添加 选中的标签
			selecttags(value) {
				var that = this
				if(value.length>3){
				that.tagopen()
				}else{
					this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods/' + goodsid + '/goodstags/',
					data: {tags:value}
				})
				}
				
			},
			tagopen (nodesc) {
                this.$Notice.open({
                    title: '添加错误提醒',
                    desc: nodesc ? '' : '最多可以添加三个商品标签. '
                });
          },
			cancel() {
				this.$Message.info('Clicked cancel');
			},
			offerok() {
				this.$Message.info('你确认了上传报价图片');
			},
			offercancel() {
				this.$Message.info('你取消了上传报价图片');
			},
			//处理上传轮播图图片
			handleView(path) {
				this.imgs = path;
				this.visible = true;
			},
			//删除轮播图
			handleRemove(index) {
				console.log(index)
				var id = this.uploadList[index].id;
				var that = this;
				this.axios({
					method: 'delete',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/goods/' + goodsid + '/goodsshows/' + id
				}).then(function(res) {
					that.uploadList.splice(index, 1);
				})
			},
			//获取商品标签数据
			requesttags() {
				var that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/tags'
				}).then(function(res) {
					var ccc=[];
					
					for (var a=0;a<res.data.data.length;a++) {
						if (res.data.data[a].disabled==false) {
							ccc.push(res.data.data[a])
						}
					}
					that.spec = ccc
				})
			},
			handleSuccess(res, file) {
				var arrobj = {};
				arrobj = res.data;
				arrobj.status = 'finished'
				this.uploadList.push(arrobj);
				console.log(arrobj)
				this.uploadList.splice(1, 0)
				console.log(this.uploadList)
			},
			//上传报价图片成功 
			Success(file){
				this.editInlineAndCellData[uptuindex].bigimage=file.data.file;
				this.editInlineAndCellData.splice(1,0)
				console.log(file.data.file)
			},
			handleFormatError(file) {
				this.$Notice.warning({
					title: '图片格式错误',
					desc: '文件' + file.name + '格式错误, 请选择png或者jqg.'
				});
			},
			handleMaxSize(file) {
				this.$Notice.warning({
					title: '上传错误',
					desc: '图片 ' + file.name + '太大,不能超多2M.'
				});
			},
			handleBeforeUpload(file) {
				console.log(file)
				const check = this.uploadList.length < 6;
				if(!check) {
					this.$Notice.warning({
						title: '最多可以上传6张图片'
					});
				}
				return check;
			},
			getParams() {
				// 取到路由带过来的参数 
				this.trlist = [];
				groups=[];
				dest=[];
				let routerparams = this.$route.query.name;
				goodsid = this.$route.query.goodsid;
				let modaladd = this.$route.query.modaladd
				// 将数据放在当前组件的数据内				
				this.goodsTypeId = routerparams
				this.goodname = modaladd;
				/*上传图片路径*/
				this.goodsurl = '/api/goods/' + goodsid + '/goodsshows/files'
				//请求该分类下的规格

				this.postproduct();
				this.requesttu();
			},
			setVal() {
				// 设置初始值
				this.$refs.editer.run('code', '这里是富文本的初始值')
			},
			getVal() {
				// 获取初始值
				var code = $('#summernote').summernote('code');
				this.axios({
					method: 'put',
					url: '/api/goods/' + goodsid + '/contents',
					data: {
						uploadFilesList: {},
						content: code
					}
				}).then(function(res) {
					console.log(res)
				})
				console.log(code)
			},
			//加载选中的规格值开始		
			//加载选中的规格值结束
			sendFile(files, editor, $editable) {
				var datas = new FormData();
				console.log(files)
				datas.append("file", files[0]);
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'multipart/form-data'
					},
					url: '/api/goods/' + goodsid + '/files',
					data: datas
				}).then(function(res) {
					$('#summernote').summernote('insertImage', res.data.data.path, 'img');
				})
			},
			//修改报价组合信息
			reprice(index){
				console.log(this.editInlineAndCellData[index]);
				this.axios({
					method: 'put',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'multipart/form-data'
					},
					url: '/api/goods/'+goodsid+'/goodsextends/'+this.editInlineAndCellData[index].ids,
					data: {
						 sales:1,
						 stock:2,
						 defaults:false,
						 originalprice:4,
						 price:5
					     }
				}).then(function(res) {
					console.log(res)
				})
			},
			// 删 规格值
			close(index){
				alert(123)
			}
		},
		watch: {
			// 监测路由变化,只要变化了就调用获取路由参数方法将数据存储本组件即可
			'$route': 'getParams'
		},
		mounted() {
			var that = this;		
			this.editInlineAndCellColumn=[];
		    this.editInlineAndCellData=[];
		    this.arrlist=[];
		    this.arrlistx=[];
	 tablearr = [];
	 groups = [];
	 arr = [];
	 obj = {};
	 alltype = [];
			this.getParams();
			this.getdata();
			this.requesttags();
			this.requestbrands();
			this.$Notice.config({
				top: 100,
				duration: 3
			});
			var richEditor = $('#summernote').summernote({
				lang: 'zh-CN',
				placeholder: '请输入内容',
				height: 400,
				htmlMode: true,
				toolbar: [
					['style', ['bold', 'italic', 'underline', 'clear']],
					['fontsize', ['fontsize']],
					['fontname', ['fontname']],
					['color', ['color']],
					['para', ['ul', 'ol', 'paragraph']],
					['insert', ['link', 'picture']]
				],
				fontSizes: ['8', '9', '10', '11', '12', '14', '18', '24', '36'],
				fontNames: [
					'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New',
					'Helvetica Neue', 'Helvetica', 'Impact', 'Lucida Grande',
					'Tahoma', 'Times New Roman', 'Verdana'
				],
				buttons: {
					//myVideo: myVideoBtn
				},
				callbacks: {
					onSubmit: function() {
						vm.richContent = $('#summernote').summernote('code')
					},
					onImageUpload: function(files, editor, $editable) {
						that.sendFile(files);
					}
				}
			})

		},
		components: {
			canEditTable
		}
	}
</script>