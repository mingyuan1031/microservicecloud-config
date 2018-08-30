<style type="text/css">
	 @import './mallmenu.css';
</style>
<template>
	<div class="contain" style="z-index: -1;">
		<div class="item-title">
			<div class="miaoshu">
				<h3>前台菜单管理</h3>
				<h5>商城图标的展示与管理</h5>
			</div>
		</div>
		<div class="functions">
			<div>
				<Table border :columns="columns7" :data="data6" ref="selection" @on-selection-change="selected" ></Table>
			</div>
			<!-- 修改模态框开始-->
			<Modal v-model="modalx" title="修改菜单信息" @on-ok="modelok" @on-cancel="modelcancel">
				<Form :model="formItem" :label-width="80">
					<FormItem label="菜单名称">
						<Input v-model="pname" placeholder="请输入品牌名称..."></Input>
					</FormItem>
					<FormItem label="菜单链接">
						<Input v-model="pdes" placeholder="请输入品牌描述..."></Input>
					</FormItem>
				</Form>
			</Modal>
			<!--上传logo-->
			<Modal v-model="logoadd" title="上传图标" @on-ok="logook" @on-cancel="logocancel">
				<Upload :before-upload="handleUpload" :action="logourl" :data="updata" :on-success="newlogo">
					<Button type="ghost" icon="ios-cloud-upload-outline">请选择需要上传的图片</Button>
				</Upload>
			</Modal>
			<!--上传logo-->
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
				pname: '',
				pdes: '',
				pstatus: '',
				logoadd: '',
				logourl: '',
				file: null,
				loadingStatus: false,
				updata: {
					file: null
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
				columns7: [
					{
						type: 'index',
						align: 'center',
						title: '序号 ',
						width: 120
					},
					{
						title: '图标名称',
						key: 'name',
						align: 'center',
					},
					{
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
					},

					{
						title: '图片链接',
						key: 'url',
						align: 'center'
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
											this.modalx = true;
											this.repair(params.index);
										}
									}
								}, '修改'),
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
											this.repair(params.index);
										}
									}
								}, '上传图标')/*,
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
								}, '删除')*/
							]);
						}
					}
				],
				data6: [],
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
			//当前行删除
			remove(index) {
				deleteid = this.data6[index].id
				var that = this;
				var a = [];
				var indexs = index
				console.log(indexs)
				a.push(deleteid)
				this.axios({
						method: 'delete',
						url: '/api/brands/' + a
					})
					.then(function(response) {
						if(response.data.error == null) {
							that.data6.splice(indexs, 1);
						} else {
							that.tip();
						}
					})
			},
			repair(index) {
				repairid = this.data6[index].id;
				logoindex = index
				this.pname = this.data6[index].name;
				this.pdes = this.data6[index].url;
				this.logourl ='/api/homenavs/'+ repairid +'/files'
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
			//请求图标信息 
			alerts() {
				var $that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/homenavs'
				}).then(function(res) {
					$that.data6 = $that.initTable1 = res.data.data;
				
				})
			},
			//修改图标信息
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
			},
			//上传logo
			handleUpload(file) {
				this.updata.file = file;
			},
			//上传成功返回数据
			newlogo(file) {
				this.data6[logoindex].image = file.data.image;
			},
			//提示框的取消与确认
			ok() {
				var evens = _.filter(this.data6, function(n) {
					return !arrsid[n.id];
				});
				this.data6 = evens;
				this.axios({
					method: 'delete',
					url: '/api/brands/' + arrids,
				})
				this.$Message.info('信息已经删除');
			},
			cancel() {
				this.$Message.info('信息取消了删除');
			},
			modelok() {
				this.modifybrand();
				this.$Message.info('修改完成');
			},
			modelcancel() {
				this.$Message.info('你取消了修改');
			},
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
			this.alerts();
		}
	}
</script>

<style>
	
</style>