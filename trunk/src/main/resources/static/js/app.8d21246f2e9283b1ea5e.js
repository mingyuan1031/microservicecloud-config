webpackJsonp([1],{0:function(t,e){},"9arE":function(t,e){},L1LO:function(t,e){},Lcxl:function(t,e){},MVGf:function(t,e){},NHnr:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=i("7+uW"),n={name:"App",mounted:function(){var t=document.documentElement.clientWidth/360*20+"px";document.documentElement.style.fontSize=t},updated:function(){document.documentElement.scrollTop=0,document.body.scrollTop=0},watch:{$route:function(t,e){var i=t.path.split("/").length,a=e.path.split("/").length;this.transitionName=i<a?"slide-right":"slide-left"}}},s={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"app"}},[e("keep-alive",[this.$route.meta.keepAlive?e("router-view"):this._e()],1),this._v(" "),this.$route.meta.keepAlive?this._e():e("router-view")],1)},staticRenderFns:[]};var o=i("VU/8")(n,s,!1,function(t){i("Lcxl")},null,null).exports,l=i("/ocq"),r={props:["text"],name:"alertopen",data:function(){return{somedata:"66666666",tip:{alertopacity:"1",Alert:!0,alertshow:6,alwarning:"确认要删除"}}},methods:{alertnone:function(){},mysure:function(){this.$emit("child-say",this.somedata)},mynone:function(){this.$emit("child-none",this.somedata)}},mounted:function(){this.tip=this.text,console.log(this.text)}},c={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{attrs:{id:"alertopen"}},[i("div",{staticClass:"alertnav",style:{opacity:t.tip.alertopacity},on:{click:t.alertnone}},[i("div",{directives:[{name:"show",rawName:"v-show",value:1==t.tip.Alert,expression:"tip.Alert==true"}],staticClass:"alert"},[i("div",{directives:[{name:"show",rawName:"v-show",value:3==t.tip.alertshow,expression:"tip.alertshow==3"}],staticClass:"tip  hint"},[i("i",{staticClass:"layui-icon "},[t._v("")]),t._v(" "),i("p",[t._v("已加入购物车")])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:1==t.tip.alertshow,expression:"tip.alertshow==1"}],staticClass:"tip  true"},[i("i",{staticClass:"layui-icon "},[t._v("")]),t._v(" "),i("p",[t._v(t._s(t.tip.alwarning))])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:2==t.tip.alertshow,expression:"tip.alertshow==2"}],staticClass:"tip  warning"},[i("i",{staticClass:"layui-icon "},[t._v("")]),t._v(" "),i("p",[t._v(t._s(t.tip.alwarning))])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:5==t.tip.alertshow,expression:"tip.alertshow==5"}],staticClass:"tip  xhuan"},[i("i",{staticClass:"layui-icon "},[t._v("")]),t._v(" "),i("p",[t._v(t._s(t.tip.alwarning))])]),t._v(" "),i("div",{directives:[{name:"show",rawName:"v-show",value:6==t.tip.alertshow,expression:"tip.alertshow==6"}],staticClass:"tip  inquiry"},[i("p",{staticClass:"conter"},[t._v(t._s(t.tip.alwarning))]),t._v(" "),i("div",{staticClass:"end"},[i("div",{staticClass:"item"},[i("span",{on:{click:t.mynone}},[t._v(" 取消")])]),t._v(" "),i("div",{staticClass:"item"},[i("span",{on:{click:t.mysure}},[t._v("确认")])])])])])])])},staticRenderFns:[]};var p=i("VU/8")(r,c,!1,function(t){i("MVGf")},null,null).exports,u=i("mtWM"),h=i.n(u),m={name:"yuyue",data:function(){return{yuyueimg:"",typeind:0,type:["30-50","50-80","100+"],tip:{alertopacity:"0",Alert:!1,alertshow:2,alwarning:""},msg:{name:"",phone:"",area:""}}},methods:{tabclss:function(t){this.typeind=t,this.msg.area=this.type[this.typeind]},alertn:function(t,e,i,a,n){var s=this;this.tip.alertopacity=3,this.tip.alertshow=t,this.tip.Alert=!0,this.tip.alwarning=e,setTimeout(function(){s.tip.alertopacity=0,s.tip.alwarning="",s.tip.alertshow=0,s.tip.Alert=!1,1==i&&s.alertdb(a,n)},2e3)},alertdb:function(t,e){var i=this;this.tip.alertopacity=3,this.tip.alertshow=t,this.tip.Alert=!0,this.tip.alwarning=e,setTimeout(function(){i.tip.alertopacity=0,i.tip.alwarning="",i.tip.alertshow=0,i.tip.Alert=!1},2e3)},sub:function(){if(""==this.msg.name||null==this.msg.name||0==this.msg.name.trim().length)this.alertn(2,"姓名不能为空");else if(""==this.msg.phone||null==this.msg.phone||0==this.msg.phone.trim().length)this.alertn(2,"手机号不能为空");else if(0==/^1[3|4|5|7|8][0-9]{9}$/.test(this.msg.phone))this.alertn(2,"手机号格式不正确");else{this.alertn(5);var t=this;t.axios({method:"post",url:"/api/users/0/reservations",data:{name:this.msg.name,phone:this.msg.phone,area:this.type[this.typeind]+"m²"}}).then(function(e){console.log(e.data),t.alertn(1,"提交成功"),setTimeout(function(){t.tip.alertopacity=0,t.tip.alwarning="",t.tip.alertshow=0,t.tip.Alert=!1,t.msg.name="",t.msg.phone="",t.typeind=0},2e3)}).catch(function(e){console.log(e),t.alertn(2,"错误"),setTimeout(function(){t.tip.alertopacity=0,t.tip.alwarning="",t.tip.alertshow=0,t.tip.Alert=!1},2e3)})}}},components:{alertopen:p},mounted:function(){var t=this;t.axios({method:"get",url:"/api/cfgdatas"}).then(function(e){console.log(e.data.data),t.yuyueimg=e.data.data.storecfg.image}).catch(function(t){console.log(t)})}},v={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{attrs:{id:"yuyue"}},[i("div",{staticClass:"yuyue"},[i("div",{staticClass:"yuyue_son"},[i("img",{attrs:{src:t.yuyueimg}})])]),t._v(" "),i("div",{staticClass:"yh_tianxie"},[i("div",{staticClass:"yh_xinxi"},[i("form",{attrs:{action:"",method:"post"}},[i("input",{directives:[{name:"model",rawName:"v-model",value:t.msg.name,expression:"msg.name"}],staticClass:"name_t",staticStyle:{outline:"none"},attrs:{type:"text",value:"",placeholder:"请输入姓名"},domProps:{value:t.msg.name},on:{input:function(e){e.target.composing||t.$set(t.msg,"name",e.target.value)}}}),t._v(" "),i("input",{directives:[{name:"model",rawName:"v-model",value:t.msg.phone,expression:"msg.phone"}],staticClass:"tel_t",staticStyle:{outline:"none"},attrs:{type:"text",value:"",placeholder:"请输入手机号"},domProps:{value:t.msg.phone},on:{input:function(e){e.target.composing||t.$set(t.msg,"phone",e.target.value)}}})])])]),t._v(" "),i("div",{staticClass:"zf_xinxi"},[i("div",{staticClass:"pick-yuyue"},[i("div",{staticClass:"zf_bor"},[t._v("住房面积")]),t._v(" "),i("ul",t._l(t.type,function(e,a){return i("li",{class:{active:t.typeind==a},on:{click:function(e){t.tabclss(a)}}},[t._m(0,!0),t._v(t._s(e)),i("small",[t._v("m²")])])}))])]),t._v(" "),i("div",{staticClass:"tj_button",staticStyle:{"margin-top":"0px"}},[i("div",{staticClass:"tijiao_bj",on:{click:t.sub}},[t._v("提交获得免费报价")]),t._v(" "),t._m(1)]),t._v(" "),i("alertopen",{attrs:{text:t.tip}}),t._v(" "),i("div",[i("router-link",{attrs:{to:"/logo"}},[i("p",[t._v("登录")])])],1)],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("span",[e("i",{staticClass:"layui-icon"},[this._v("")])])},function(){var t=this.$createElement,e=this._self._c||t;return e("a",{attrs:{href:"tel:18595828663"}},[e("div",{staticClass:"dianhua_tel"},[this._v("一键咨询 18595828663")])])}]};var d=i("VU/8")(m,v,!1,function(t){i("L1LO"),i("TuDq"),i("tl+q")},null,null).exports,f={name:"logo",data:function(){return{}},methods:{pos:function(){this.axios({method:"post",url:"/api/users/login",headers:{"X-Requested-With":"XMLHttpRequest",ContentType:"application/json;charset=UTF-8"},data:{loginName:"admin",password:"e6e061838856bf47e1de730719fb2609"}}).then(function(t){console.log(t.data),alert("成功了"+t.data)}).catch(function(t){console.log(t)})}},mounted:function(){}},_={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"logo"}},[e("p",{on:{click:this.pos}},[this._v("\n\t登录\n")])])},staticRenderFns:[]};var g=i("VU/8")(f,_,!1,function(t){i("qpx4")},null,null).exports;a.a.use(l.a);var w=new l.a({routes:[{path:"/reservation",name:"reservation",component:d,meta:{title:"预约"}},{path:"/logo",name:"logo",component:g,meta:{title:"登录"}}]}),y=i("8+8L");a.a.prototype.axios=h.a,i("pFqO"),i("Qwh1"),i("9arE"),a.a.use(y.a),a.a.config.productionTip=!1,w.beforeEach(function(t,e,i){t.meta.title&&(document.title=t.meta.title),i()}),new a.a({el:"#app",router:w,components:{App:o},template:"<App/>"})},Qwh1:function(t,e){},TuDq:function(t,e){},pFqO:function(t,e){},qpx4:function(t,e){},"tl+q":function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.8d21246f2e9283b1ea5e.js.map