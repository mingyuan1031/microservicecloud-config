webpackJsonp([1],{0:function(t,e){},"9arE":function(t,e){},NHnr:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=s("7+uW"),n=s("mtWM"),i=s.n(n),o=document.getElementById("lwxf_preload").innerText.replace(/_e@gt@e_/g,">").replace(/_e@lt@e_/g,"<"),c=JSON.parse(o.trim());console.log(c.appcfg["X-SID"]),i.a.defaults.headers["X-SID"]=c.appcfg["X-SID"],i.a.defaults.headers["X-PHPSESSID"]=c.appcfg["X-PHPSESSID"],i.a.defaults.headers["X-C-ID"]=c.appcfg["X-C-ID"],i.a.defaults.headers["X-Requested-With"]="XMLHttpRequest",i.a.defaults.headers.ContentType="application/json;charset=UTF-8";var r={name:"Card",methods:{axioses:function(){this.axios({method:"get",url:"/api/sys/touch"}).then(function(t){console.log(t.data)}).catch(function(t){console.log(t)})}},mounted:function(){var t=this,e=document.documentElement.clientWidth/360*20+"px";document.documentElement.style.fontSize=e,setInterval(function(){t.axioses()},15e5)},updated:function(){document.documentElement.scrollTop=0,document.body.scrollTop=0},watch:{$route:function(t,e){var s=t.path.split("/").length,a=e.path.split("/").length;this.transitionName=s<a?"slide-right":"slide-left"}}},l={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"card"}},[e("keep-alive",[this.$route.meta.keepAlive?e("router-view"):this._e()],1),this._v(" "),this.$route.meta.keepAlive?this._e():e("router-view")],1)},staticRenderFns:[]};var d=s("VU/8")(r,l,!1,function(t){s("ymvF")},null,null).exports,p=s("/ocq"),u={name:"logo",data:function(){return{}},methods:{pos:function(){this.axios({method:"post",url:"/api/users/login",headers:{"X-Requested-With":"XMLHttpRequest",ContentType:"application/json;charset=UTF-8"},data:{loginName:"admin",password:"e6e061838856bf47e1de730719fb2609"}}).then(function(t){console.log(t.data),alert("成功了"+t.data)}).catch(function(t){console.log(t)})}},mounted:function(){}},_={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",{attrs:{id:"logo"}},[e("p",{on:{click:this.pos}},[this._v("\n\t登录\n")])])},staticRenderFns:[]};var m=s("VU/8")(u,_,!1,function(t){s("dqUd")},null,null).exports,v={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"mingpian"}},[a("div",{staticClass:"cardbanner"},[a("img",{attrs:{src:s("oJGt")}})]),t._v(" "),a("div",{staticClass:"mp_de"},[a("ul",{staticClass:"mp_deon"},[a("li",{staticClass:"mp_center_border"},[a("div",{staticClass:"mp_de_son"},[a("p",{staticClass:"mp_dd"},[t._v("商家名称")]),t._v(" "),a("div",{staticClass:"mp_float_right"},[t._v("上海红田")]),t._v(" "),a("div",{staticClass:"clearfix"})])]),t._v(" "),a("li",{staticClass:"mp_center_border"},[a("div",{staticClass:"center_de_son"},[a("p",{staticClass:"mp_dd"},[t._v("商家电话")]),t._v(" "),a("div",{staticClass:"mpphone"},[t._v("0371-8888086")])])]),t._v(" "),a("li",{staticClass:"mp_center_border"},[a("div",{staticClass:"center_de_son"},[a("p",{staticClass:"mp_dd"},[t._v("联系人")]),t._v(" "),a("div",{staticClass:"mp_float_right"},[t._v("上海红田")]),t._v(" "),a("div",{staticClass:"clearfix"})])]),t._v(" "),a("li",{staticClass:"mp_center_border"},[a("div",{staticClass:"center_de_son"},[a("p",{staticClass:"mp_dd"},[t._v("经营范围")]),t._v(" "),a("div",{staticClass:"mp_float_rightbd "},[t._v("生产、销售：橱柜、家具、衣柜、全屋定制家具、板材、木屋、工艺品、人造石、铝合金制品、木制门窗、家居用品、网络技术开发、维护、运营、经营本公司自产产品出口业务。")]),t._v(" "),a("div",{staticClass:"clearfix"})])]),t._v(" "),a("li",{staticClass:"mp_center_border"},[a("div",{staticClass:"center_de_son"},[a("p",{staticClass:"mp_dd"},[t._v("商家地址")]),t._v(" "),a("div",{staticClass:"mp_float_rightbd",staticStyle:{"line-height":"0.9rem"}},[t._v("河南省焦作市温县产业集聚区谷黄路南侧红田家居")]),t._v(" "),a("div",{staticClass:"clearfix"})])]),t._v(" "),a("div",{staticClass:"erm"},[a("img",{attrs:{src:s("czz5")}}),t._v(" "),a("p",[t._v("技术支持：老屋新房")])])])])])}]};var f=s("VU/8")({name:"mingpian",data:function(){return{msg:{storecfg:{}},overshow:!1}},methods:{overplay:function(){this.overshow=!this.overshow}},mounted:function(){}},v,!1,function(t){s("z/4O"),s("qKWS"),s("kmI/")},null,null).exports;a.a.use(p.a);var h=new p.a({routes:[{path:"/",name:"card",component:f,meta:{title:"商家名片-新零售"}},{path:"/logo",name:"logo",component:m,meta:{title:"登录"}}]}),g=s("8+8L");s("pFqO"),s("Qwh1"),s("9arE");a.a.prototype.axios=i.a,a.a.use(g.a),a.a.config.productionTip=!1,h.beforeEach(function(t,e,s){t.meta.title&&(document.title=t.meta.title),s()}),new a.a({el:"#storecard",router:h,components:{Storecard:d},template:"<Storecard/>"})},Qwh1:function(t,e){},czz5:function(t,e,s){t.exports=s.p+"img/857522379732117614.f45e822.jpg"},dqUd:function(t,e){},"kmI/":function(t,e){},oJGt:function(t,e,s){t.exports=s.p+"img/menmian.bd56128.png"},pFqO:function(t,e){},qKWS:function(t,e){},ymvF:function(t,e){},"z/4O":function(t,e){}},["NHnr"]);
//# sourceMappingURL=storecard.229abf7f7ca851dc9ee9.js.map