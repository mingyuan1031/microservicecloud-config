<template>
	<div class="contain">
		<div style="padding-left: 300px;margin-top: 200px;">
		<Upload :before-upload="handleUpload" action="/api/storecfgs/{cfgId}/files" :data="updata">
            <Button type="ghost" icon="ios-cloud-upload-outline">选择需要上传的文件 </Button>
        </Upload>
         </div>
       </div>
</template>

<script>
	  export default {
        data () {
            return {
                file: null,
                loadingStatus: false,
                updata:{
                	file:null
                }
            }
        },
        methods: {
            handleUpload (file) {
                this.updata.file = file;             
            }
        }
    }
</script>

<style>
</style>