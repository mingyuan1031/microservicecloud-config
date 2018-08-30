<style>
    @import './mallinformation.css';
</style>
<template>
	<div>
		<div class="contain" style="z-index: -1;">
			<div class="contai">
			<Tabs value="name1">
			<TabPane label="商城设置" name="name1">
<div style="background-color: #f0f0f0;height: 800px;">
  <div class="taboneleft">
    <div class="onepart">
    	<div class="parthead"><i class="ivu-icon ivu-icon-ios-cloud-upload-outline"></i>&nbsp网站LOGO</div>
    	<div style="margin-top: 80px;text-align: center;">
		<Upload :action="uplogourl" :headers="headers" :data="uplogodata" ref="upload">
        <Button type="ghost" icon="ios-cloud-upload-outline">上传网站Logo的照片</Button>
        </Upload>
        </div>
    	</div>
    <div class="twopart">
    	<div class="parthead"><i class="ivu-icon ivu-icon-ios-cloud-upload-outline"></i>&nbsp预约广告图</div>
    	<div style="margin-top: 80px;text-align: center;">
		<Upload :action="upappointmenturl" :data="upappointmentdata">
        <Button type="ghost" icon="ios-cloud-upload-outline">选择上传预约广告图</Button>
        </Upload>
        </div>
    </div>
    <div class="threepart">
    	<div class="parthead"><i class="ivu-icon ivu-icon-ios-cloud-upload-outline"></i>&nbsp店铺二维码</div>
    	<div style="margin-top: 80px;text-align: center;">
		<Upload :action="upqrcodeurl"  :data="upqrcodedata">
        <Button type="ghost" icon="ios-cloud-upload-outline">上传店铺二维码</Button>
       </Upload>     
       </div>
        <div style="height: 10px;"></div>
   </div>
  
</div>
<div class="forms">
	<h3 class="sets"><img src="../../common/images/set.png" width="18"  class="setimg"/>信息设置</h3><br /><br />
	<div class="tipx">
		<Alert type="success" show-icon>以下可以修改网站的基本信息</Alert>
	</div>
	
	<div class="infocontain">
	<table border="none" class="xinx">
	<tr><td><span class="inforset">网站名称</span><Input v-model="webname" placeholder="请输入网站名称..." style="width: 500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">经营范围</span><Input v-model="productname" placeholder="请输入经营范围..." style="width: 500px"></Input><br /></td></tr>	
	<tr><td><span class="inforset">联系电话</span><Input v-model="tel" placeholder="请输入联系电话..." style="width:500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">预约手机</span><Input v-model="phone" placeholder="请输入预约手机..." style="width:500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">客服QQ</span><Input v-model="QQnum" placeholder="请输入客服QQ..." style="width:500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">满减包邮</span><Input v-model="packagemail" placeholder="请输入满减包邮的金额..." style="width:500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">具体地址</span><Input v-model="loaction" placeholder="请输入具体地址..." style="width:500px"></Input><br /></td></tr>
	<tr><td><span class="inforset">网站联系人</span><Input v-model="contacter" placeholder="请输入联系人..." style="width:500px"></Input><br /></td></tr>
	<tr><td style="text-align: left;height:80px;padding-left:100px;"><div>
    	<Upload :action="upcardurl"  :data="upcarddata">
    		
    		<Button type="ghost" icon="ios-cloud-upload-outline">上传店铺名片</Button>
    	</Upload>
    </div></td></tr>
	<tr><td><Button size="large" type="info" @click="putinformations">保存修改</Button></td></tr>
	  
	</table>			   	                     
	</div>
</div>
</div>
				</TabPane>
				<TabPane label="系统设置" name="name2">
				<Alert type="warning" show-icon> 
					 在此修改商城的基本配置
				</Alert>
					<div style="height: 50px;"></div>
					<div style="width: 800px;">
						<ul>
							<li>
								<div>
									<span style="margin-left: 4%;padding-right:2%;">店铺欢迎语</span>
									<div style="display: inline-block;">
										<Input v-model="welcomeword" type="textarea" :autosize="{minRows: 3,maxRows: 8}" placeholder="请输入店铺欢迎语..." style="width: 600px"></Input>
									</div>
								</div>
							</li>
							<li>
								<div style="text-align: left;margin: 20px 0;">
									<span style="margin-left: 4%;padding-right:2%;">网站关键字</span>
									<div style="display: inline-block;">
									<Input v-model="keyword" placeholder="请输入网站关键字..." style="width:600px"></Input>
									<!--<span class="tip">网站关键字，便于SEO</span>-->
								  </div>
								  </div>
							</li>							
							<li>
								<div style="text-align: left;height: 60px;line-height: 60px;">
									<span style="margin-left: 4%;padding-right:2%;float: left;">店铺的图片</span>
									<span style="display: inline;">
	<div class="demo-upload-list" v-for="item in uploadList">
        <template v-if="item.status === 'finished'">
            <img :src="item.url">
            <div class="demo-upload-list-cover">
                <Icon type="ios-eye-outline" @click.native="handleView(item.name)" style="padding-top:20px ;"></Icon>
                <!--<Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>-->
            </div>
        </template>
        <template v-else>
            <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
        </template>
    </div>
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
        :action="upshoptuurl"
        :data='upshoptudata'
        style="display: inline-block;width:58px;">
        <div style="width: 58px;height:58px;line-height: 58px;padding-top: 16px;">
            <Icon type="camera" size="20"></Icon>
        </div>
    </Upload>
    <Modal title="View Image" v-model="visible">
        <img :src="downshoptuurl" v-if="visible" style="width: 100%">
    </Modal>
			</span>
					</div>
							</li>
							<li class="wxmsg">
								<div>
									<span style="float:left; display: block; line-height: 73px; margin-left: 1%; padding-right: 2%;">自定义微信菜单</span>
									<div style="float: left;">
										<textarea  v-model="wxmsgcenter" class="wxmsg_textarea" style="width: 600px; height: 100px;"></textarea>
									</div>
									<span style="float:left; display: block; line-height: 73px; margin-left: 4%; padding-right: 0.5%;"></span>
								</div>
							</li>
							<li>
						<div style="float: right;margin-right: 90px;">
							<Button size="large" type="info" @click="shopmsg">保存修改</Button>
						</div>
							</li>
						</ul>
						</div>
				
				</TabPane>
			</Tabs>

		</div>
		</div>
	</div>
</template>
<script>
	//商铺的id用于修改
	var ids;
	var idx;
	import axios from 'axios'
	var cfg=window.lwxfPreloadqdmin 	
	axios.defaults.headers['X-SID']=cfg.appcfg['X-SID']
	axios.defaults.headers['X-PHPSESSID']=cfg.appcfg['X-PHPSESSID']
	axios.defaults.headers['X-C-ID']=cfg.appcfg['X-C-ID']
	axios.defaults.headers['X-Requested-With']='XMLHttpRequest'
	axios.defaults.headers['ContentType']='application/json;charset=UTF-8'
	export default {
		data() {
			return {
				wxmsgcenter:"",
				packagemail:'',
				webname: '',
				webtitle: '',
				productname: '',
				sorts: '',
				contacter: '',
				tel: '',
				phone: '',
				loaction: '',
				QQnum: '',
				name1: '',
				welcomeword: '',
				keyword:'',
				textareas: '',
				images:'',
				systemid:'',
				uplogourl:'',
				uplogodata:{
				    file:'logo',
				    type:0
				},
				upshoptuurl:'',
				upshoptudata:{
				    file:'header',
				    type:1
				},
				upappointmenturl:'',
				upappointmentdata:{
					file:'image',
				    type:2
				},
				upqrcodeurl:'',
				upqrcodedata:{
				    file:'qrcode',
				    type:3
				},
				upcardurl:'',
				upcarddata:{
				    file:'screenshot',
				    type:4
				},
				downshoptuurl:'',
			    headers: {
					'X-Requested-With': 'XMLHttpRequest',
					'ContentType': 'application/json;charset=UTF-8'
				},				
				defaultList: [{
						'name':'',
						'url': ''
				}					
				],
				imgName: '',
				visible: false,
				uploadList: []
			}
		},
		methods: {
			//获取当前页面店铺的数据
			requestinformations() {
			var $that = this;
				this.axios({
					method: 'get',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url: '/api/cfgdatas'
				}).then(function(res) {
					$that.webname = res.data.data.storecfg.name;
					$that.productname = res.data.data.storecfg.scope;
					$that.contacter = res.data.data.storecfg.linkman;
					$that.phone = res.data.data.storecfg.reservationCall;
					$that.tel = res.data.data.storecfg.serviceCall;
					$that.loaction = res.data.data.storecfg.address;
					$that.QQnum = res.data.data.storecfg.serviceQq;
					$that.images = res.data.data.storecfg.logo;			
					$that.keyword=res.data.data.syscfg.seoKeywords;
					$that.welcomeword=res.data.data.syscfg.welcome;					
					$that.downshoptuurl=res.data.data.storecfg.header;
					$that.packagemail=res.data.data.storecfg.pinkage
					$that.defaultList[0].url=$that.downshoptuurl;
				    $that.defaultList[0].name='header';
					$that.systemid=res.data.data.syscfg.id;
					ids=res.data.data.storecfg.id;
					idx=res.data.data.syscfg.id;
					//上传图片路径
					$that.uplogourl='/api/storecfgs/'+res.data.data.storecfg.id+'/files';
					$that.upappointmenturl='/api/storecfgs/'+res.data.data.storecfg.id+'/files';
					$that.upqrcodeurl='/api/storecfgs/'+res.data.data.storecfg.id+'/files';
					$that.upshoptuurl='/api/storecfgs/'+res.data.data.storecfg.id+'/files';
					$that.upcardurl='/api/storecfgs/'+res.data.data.storecfg.id+'/files';
				})
			},
		//修改商城信息
			putinformations() {
				var that=this;
				 if(/^\d+$/.test(that.packagemail)){
               alert("整数");
                }
				this.axios({
					method: 'put',
				    url: '/api/storecfgs/'+ids,
                 data:{
                 	linkman:that.contacter,
                    name:that.webname,
                    scope:that.productname,
                    reservationCall:that.phone,
                    serviceCall:that.tel,
                    address:that.loaction,
                    serviceQq:that.QQnum,
                    pinkage:that.packagemail
                 },
				}).then(function(res) {
					console.log(res)
				})
			},
			//修改商城配置信息
			shopmsg() {
				var that=this
				
				
				that.axios({
					method: 'put',
				    url: '/api/syscfgs/'+idx,
                 data:{
                  seoKeywords:this.keyword,
                  welcome:this.welcomeword,
                  wxmenus:this.wxmsgcenter //微信菜单信息
                 },
				}).then(function(res) {
					if(res.data.error){
		              		console.log(res.data.error.message)
						    return false
					   }
					console.log(res)
				})																				
			},
			//上传微信菜单信息
			wxmsg:function  () {
				var that=this
				var center=this.wxmsgcenter.trim()
				var xp=cfg.appcfg['X-PHPSESSID']
				var xsid=cfg.appcfg['X-SID']
				
				console.log(xp)
				console.log(xsid)
				console.log(this.uuid)
				that.axios({
					method: 'put',
					url: '/syscfgs/'+ids+'/wxmenus',
					
					headers: {
						'X-PHPSESSID':xp,
						'X-SID':xsid	,
						'X-TAG':this.uuid,
						'X-Requested-With': 'XMLHttpRequest',
						'Accept': 'application/json'
					},
					
					data:{
						wxmenus:center
					}
					
					}).then(function(res) {
					
						if(res.data.error){
		              		console.log(res.data.error.message)
						    return false
					   }
						
						console.log(res.data.data)
		              
		
					}).catch(function(err) {
						console.log(err)
						
					})												
			},
			//上传文件的函数配置
			handleView(name) {
				this.imgName = name;
				this.visible = true;
			},
			//删除展示的图片
			handleRemove(file) {
				const fileList = this.$refs.upload.fileList;
				this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
			},
			//操作成功显示的图片
			handleSuccess(res, file) {
				file.url = this.downshoptuurl;
				file.name = 'header';
				
			},
			//文件格式错误
			handleFormatError(file) {
				this.$Notice.warning({
					title: 'The file format is incorrect',
					desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
				});
			},
			//文件大小错误
			handleMaxSize(file) {
				this.$Notice.warning({
					title: 'Exceeding file size limit',
					desc: 'File  ' + file.name + ' is too large, no more than 2M.'
				});
			},
			//上传数量的限制 
			handleBeforeUpload() {
				const check = this.uploadList.length <2;
				if(!check) {
					this.$Notice.warning({
						title: 'Up to five pictures can be uploaded.'
					});
				}
				return check;
			},
			clearUploadedImage () {
				alert(123)
              this.$refs.upload.clearFiles();
				}
		},
		beforeMount: function() {
			this.requestinformations();
		},
		mounted() {
			this.uploadList = this.$refs.upload.fileList;
			this.requestinformations();
			var len=8
			var radix=16
			var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
			var uuid = [];
			radix = radix || chars.length;

			for(var i = 0; i < len; i++) {
					uuid[i] = chars[0 | Math.random() * radix]
			}

			var objdate = new Date()
			var gethours = objdate.getHours()
			var getminutes = objdate.getMinutes()
			var getseconds = objdate.getSeconds()
			if (gethours < 10) gethours = "0" + gethours;
			if (getminutes < 10) getminutes = "0" + getminutes;
			if (getseconds < 10) getseconds = "0" + getseconds;
			var datex =  ''+gethours+''+getminutes+''+getseconds+''        
            var uid=uuid.join('').toString()
            uid+=datex.toString()           
            this.uuid=uid          
			this.wxmsgcenter=cfg.preload.syscfg.wxmenus
		}
	}
</script>