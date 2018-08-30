<template>
  <div id="summernote">
    <!--<el-row :gutter="100">
      <vue-summernote ref="editer"></vue-summernote>
      <vue-summernote ref="editer1"></vue-summernote>
    </el-row>
    <button @click="setVal">初始化</button>
    <button @click="getVal">获取</button>-->
  </div>
</template>

<script>
  export default {
    name: 'app',
    mounted () {
      var richEditor = $('#summernote').summernote({
        lang: 'zh-CN',
        placeholder: '请输入内容',
        height: 600,
        width: 1024,
        htmlMode: true,
        toolbar: [
          ['style', ['bold', 'italic', 'underline', 'clear']],
          ['fontsize', ['fontsize']],
          ['fontname', ['fontname']],
          ['color', ['color']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['insert', ['link', 'picture']]/*,
          ['mybutton', ['myVideo']]*/
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
          onImageUpload:function (files) {
            alert(files.length);
          }
        }
      })
      /*editer.$on('onImageUpload', function (files) {
        // 这里做上传图片的操作，上传成功之后便可以用到下面这句将图片插入到编辑框中
        editer.run('insertImage', 'http://vuefe.cn/images/logo.png')
      })
      editer.$on('onChange', function (contents) {
        // 当富文本框内容发生变化时做什么事
        console.log('onChange:', contents)
      })
      editer1.$on('onImageUpload', function (files) {
        // 这里做上传图片的操作，上传成功之后便可以用到下面这句将图片插入到编辑框中
        editer1.run('insertImage', 'http://vuefe.cn/images/logo.png')
      })*/
    },
    methods: {
      /*setVal () {
        // 设置初始值
        this.$refs.editer.run('code', '这里是富文本的初始值')
      },
      getVal () {
        // 获取初始值
        this.$refs.editer.run('code')
      }*/
    }
  }
</script>
