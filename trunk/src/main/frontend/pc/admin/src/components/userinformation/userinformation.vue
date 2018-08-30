<style>
	@import './userinformation.css';
</style>
<template>
	<div class="usercontain" style="z-index:-1;">
		<div class="user_form">
	    <p class="user_form_header">
	     <i class="ivu-icon ivu-icon-person"></i>
	    	个人信息管理
	    </p>
	    <div class="user_form_contain">
	    <p>
	    <span><span class="redstar">*</span>用户名称</span>
	    <Input style="width:300px;" v-model="username">
        </Input>
        </p>
        <p><span>网站名称：</span><span>{{company}}</span></p>
        <p><span>联系电话：</span><span>{{phones}}</span></p>
        <!--<p><span>登录密码：</span> <Button type="text" @click="modal1=true">修改密码</Button></p>-->
        <p><span>绑定微信：</span> <Button type="text" @click="addboss">绑定微信</Button></p>
        <p>
        </p>
	     </div>
		</div>
	  <!--  模态框开始-->
	<Modal
        v-model="modal1"
        title="修改个人密码"
        @on-ok="ok"
        @on-cancel="cancel">
          <span><span class="redstar">*</span>原密码</span><Input v-model="value" placeholder="请输入原密码..."></Input>
          <br /><br />
          <span><span class="redstar">*</span>新密码</span><Input v-model="value" placeholder="请输入新密码..."></Input>
          <br /><br />
          <span><span class="redstar">*</span>确认新密码</span><Input v-model="value" placeholder="请输入新密码..."></Input>
    </Modal>
	  <!--  模态框结束 -->
	   <!--  模态框开始-->
	<Modal
        v-model="modaladd"
        title="绑定店主"
        @on-ok="bossok"
        @on-cancel="bosscancel">
        <div style="text-align: center;">
        	<img :src="bossimgurl"/>
        </div>         
    </Modal>
	  <!--  模态框结束 -->
	</div>
</template>
<script>
	 var cacse = document.getElementById("lwxf_preload")
  	 var pfcfg = cacse.innerText.replace(/_e@gt@e_/g, '>').replace(/_e@lt@e_/g, '<')
	 var cfg = JSON.parse(pfcfg.trim());
	 console.log(cfg);
	 
	export default {
        data () {
            return {
                modal1: false,
                modaladd:false,
                value: '',
                bossimgurl:'',
                username:'',
                company:'',
                phones:''
            }
        },
        methods: {
        	addboss(){
        	    this.modaladd=true;
        		var that = this;
				this.axios({
					method: 'post',
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'ContentType': 'application/json;charset=UTF-8'
					},
					url:'/api/users/0/qrcodes/bingshopkeeper'
				}).then(function(res) {
					that.bossimgurl= 'https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket='+res.data.data.ticket			
				})
        	},
            ok () {
                this.$Message.info('完成了密码修改');
            },
            cancel () {
                this.$Message.info('取消了修改密码');
            },
            bossok () {
                this.$Message.info('完成了店主添加');
            },
            bosscancel () {
                this.$Message.info('取消了店主添加');
            },
            reloads(){
            	this.username=cfg.preload.user.username;
            	this.company=cfg.preload.company.name;
            	this.phones=cfg.preload.storecfg.serviceCall
            }
        },
        mounted() {
			this.reloads();
		}
    }
</script>


