<style>
	@import url("login.css");
</style>
<template>
	<div class="login_contain">
	<div class="form">
		<div class="h_header">红田分销商城登录</div>
		<div style="margin-left: 50px;">
		<Form>
        <FormItem prop="user">
            <Input type="text" v-model="user" placeholder="请输入用户名" style="width: 250px" size="large">
                <Icon type="ivu-icon ivu-icon-person" slot="prepend" style="20px"></Icon>
            </Input>
        </FormItem>
        <br />
      
        <FormItem prop="password">
            <Input type="password" v-model="password" placeholder="请输入密码..." style="width: 250px" size="large">
                <Icon type="ivu-icon ivu-icon-locked" slot="prepend" style="20px"></Icon>
            </Input>
        </FormItem>
        <br />
        <br />
        <FormItem>
            <Button type="primary" @click="handleSubmit"  size="large" style="width: 250px">登录</Button>
        </FormItem>
    </Form>
    </div>
	</div>
	</div>
</template>

<script>
	import md5 from 'js-md5';
	export default {
        data () {
            return {              
                    user: '',
                    password: ''
            }
        },
        methods: {
            handleSubmit() {
                var that=this;
                this.axios({
				method:'post',
				headers:{
				'X-Requested-With': 'XMLHttpRequest',
				'ContentType': 'application/json;charset=UTF-8'
				},
				url:'/api/users/login',
				data:{
					 loginName: that.user,
                     password: md5(that.password)
				   }
			}).then(function(res){
				if (res.data.error) {
					alert('账号密码错误');				
				} else{
					console.log(res.data.go)
					window.location.href=res.data.go;
				}
			    
			})
            }
        }
    }
</script>