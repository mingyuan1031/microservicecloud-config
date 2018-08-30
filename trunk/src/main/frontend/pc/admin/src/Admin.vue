<template>
  <div class="app-main">
    <router-view></router-view>
  </div>
</template>

<script type="text/javascript">
	

	
	
      var cacse = document.getElementById("lwxf_preload")
  	  

      if(cacse){
      	var pfcfg = cacse.innerText.replace(/_e@gt@e_/g, '>').replace(/_e@lt@e_/g, '<')
	      var cfg = JSON.parse(pfcfg.trim())
	    
	      window.lwxfPreloadqdmin=cfg
	      
      }else{
      	 window.lwxfPreloadqdmin={
    "appcfg": {
        "reservedWords": "all|module|label|status|filter",
        "PHPSESSIONID": "3143705830CADE52F036F02252A7FBDB",
        "baseUrl": "http://newstore.htjjsc.com",
        "uploadAvatarRange": "image/png,image/gif,image/jpg,image/jpeg,image/pjpeg,image/x-ms-bmp,image/bmp",
        "X-SID": "c876a78f2754bd7df66fd2d757383fa7_cd90cd8120c8c37457b0a3949c2a24d7",
        "X-PHPSESSID": "GCXT-4IF7-3D8P-0MOT-C00X-JW2U-8Z4H-G61B",
        "pageSizeLimit": 50,
        "uploadAvatarMaxsize": 2,
        "uploadBackgroundMaxsize": 5,
        "uploadFileRootDir": "/wwwdata/workarea/upload"
    },
    "preload": {
        "types": {
            "typeSpec": [
                {
                    "id": "44kn2lnwwutc",
                    "name": "1231",
                    "goodsTypeId": "44k068bb4cn4"
                },
                {
                    "id": "44rvabclwa2o",
                    "name": "份量",
                    "goodsTypeId": "44rtwwqynwu8"
                },
                {
                    "id": "44yda5i6b5s0",
                    "name": "规格",
                    "goodsTypeId": "44ycs16cewhs"
                }
            ],
            "specOption": [
                {
                    "id": "44rvbgb1ylts",
                    "goodsSpecId": "44rvabclwa2o",
                    "name": "大份"
                },
                {
                    "id": "44rvbo9yark0",
                    "goodsSpecId": "44rvabclwa2o",
                    "name": "中份"
                },
                {
                    "id": "44rvbw6cqku8",
                    "goodsSpecId": "44rvabclwa2o",
                    "name": "小份"
                },
                {
                    "id": "44ydfvbxlvk0",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "预约上门测量定金"
                },
                {
                    "id": "44ydi94bke80",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "地柜含台面（红橡木门板+实木多层柜体）"
                },
                {
                    "id": "44ydl33iu1a8",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "吊柜（红橡木门板+实木多层柜体）"
                },
                {
                    "id": "44ydmio6d1q8",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "吊柜（桦木门板+实木多层柜体）"
                },
                {
                    "id": "44ygsvmvomps",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "地柜含台面（桦木门板+实木多层柜体）"
                },
                {
                    "id": "45kq12k0tbm9",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "地柜含台面（实木多层门板+实木多层柜体）"
                },
                {
                    "id": "45kq12k0tbma",
                    "goodsSpecId": "44yda5i6b5s0",
                    "name": "吊柜（实木多层门板+实木多层柜体）"
                }
            ],
            "goodsType": [
                {
                    "id": "44k068bb4cn4",
                    "name": "111122aa",
                    "sort": 100,
                    "disabled": false,
                    "parentId": ""
                },
                {
                    "id": "44k0ebd7785c",
                    "name": "比克大魔王",
                    "sort": 100,
                    "disabled": true,
                    "parentId": ""
                },
                {
                    "id": "44rtwwqynwu8",
                    "name": "菜品",
                    "sort": 20,
                    "disabled": false,
                    "parentId": ""
                },
                {
                    "id": "44ycs16cewhs",
                    "name": "橱柜",
                    "sort": 20,
                    "disabled": false,
                    "parentId": ""
                }
            ]
        },
        "brands": [
            {
                "id": "44k3jery6olc",
                "name": "23",
                "disabled": false,
                "content": "23",
                "image": "/p/brand/44k3jery6olc/44k3s0kcuhhc.jpg"
            },
            {
                "id": "44rupfufmscg",
                "name": "英国菜",
                "disabled": false,
                "content": "",
                "image": ""
            }
        ],
        "storeHomeNavs": [
            {
                "id": "aasgvcx",
                "name": "全部",
                "url": "goodstypeall?id=complete&name=全部",
                "status": false,
                "sequence": 10,
                "image": "/p/storehomenav/aasgvcx/45n55ods8ydc.png",
                "outerLink": false
            },
            {
                "id": "asghgfs",
                "name": "橱柜",
                "url": "goodstypeall?id=44ycs16cewhs&name=橱柜",
                "status": false,
                "sequence": 10,
                "image": "/p/storehomenav/asghgfs/45n56b2x16gw.png",
                "outerLink": false
            },
            {
                "id": "dsfdhrj",
                "name": "衣柜",
                "url": "goodstypeall?id=45jox28og3y8&name=衣柜",
                "status": false,
                "sequence": 11,
                "image": "/p/storehomenav/dsfdhrj/45n56ov3d340.png",
                "outerLink": false
            },
            {
                "id": "asdas",
                "name": "电器",
                "url": "goodstypeall?id=45joxt385on4&name=电器",
                "status": false,
                "sequence": 20,
                "image": "/p/storehomenav/asdas/45n573b9pvcw.png",
                "outerLink": false
            },
            {
                "id": "asdsad",
                "name": "预约",
                "url": "subscribeshow",
                "status": false,
                "sequence": 30,
                "image": "/p/storehomenav/asdsad/45n5aogj88hs.png",
                "outerLink": false
            },
            {
                "id": "dbsfsg",
                "name": "可视",
                "url": "videoshow",
                "status": false,
                "sequence": 42,
                "image": "/p/storehomenav/dbsfsg/45n59g8ifytc.png",
                "outerLink": false
            },
            {
                "id": "gnteb",
                "name": "设计案例",
                "url": "",
                "status": false,
                "sequence": 45,
                "image": "/p/storehomenav/gnteb/45n5b2btxkow.png",
                "outerLink": false
            },
            {
                "id": "sdfdgsgdf",
                "name": "快享",
                "url": "share",
                "status": false,
                "sequence": 50,
                "image": "/p/storehomenav/sdfdgsgdf/45n5bclceu4g.png",
                "outerLink": false
            }
        ],
        "permissions": {
            "a": "*",
            "res": "*",
            "qs": "1111111111",
            "cfg": "*",
            "g": "*",
            "og": "*",
            "gc": "*",
            "l": "*",
            "gd": "*",
            "o": "*"
        },
        "X-C-ID": "40ord3va6adp",
        "company": {
            "id": "40ord3va6adp",
            "name": "上海红田家居"
        },
        "storecfg": {
            "id": "4274kgtki4n5",
            "name": "上海红田家居",
            "companyId": "40ord3va6adp",
            "logo": "/p/storecfg/4274kgtki4n5/42icxpfbrpc0.png",
            "header": "/p/storecfg/4274kgtki4n5/43okczjuw2rk.jpg",
            "scope": "橱柜 衣柜",
            "linkman": "上海红田家居",
            "serviceCall": "037188880863",
            "reservationCall": "037188880861",
            "qrcode": "/p/storecfg/4274kgtki4n5/42icoi4l31mo.jpg",
            "address": "河南省焦作市温县",
            "serviceQq": "",
            "poster": "/p/storecfg/4274kgtki4n5/44vzjqi2b3eo.jpg",
            "pinkage": 200,
            "storecard": "/p/storecfg/4274kgtki4n5/44ze9tt0hg5c.jpg"
        },
        "syscfg": {
            "id": "sadas",
            "seoKeywords": "红田 橱柜",
            "welcome": "欢迎关注上海红田家居微信公众号",
            "companyId": "40ord3va6adp",
            "wxmenus": "{\"button\":[{\"name\":\"预约\",\"sub_button\":[{\"type\":\"view\",\"name\":\"预约\",\"url\":\"http://newstore.htjjsc.com/reservation\"},{\"type\":\"view\",\"name\":\"商城\",\"url\":\"http://newstore.htjjsc.com/mall\"}]},{\"type\":\"view\",\"name\":\"快享\",\"url\":\"http://newstore.htjjsc.com/quickshare\"},{\"name\":\"企业动态\",\"sub_button\":[{\"type\":\"click\",\"name\":\"关于我们\",\"key\":\"storecard\"},{\"type\":\"view\",\"name\":\"加盟我们\",\"url\":\"http://newstore.htjjsc.com/news/42a603svpts0/2018081800002.html\"},{\"type\":\"view\",\"name\":\"企业文化\",\"url\":\"http://newstore.htjjsc.com/news/44y8ez7stgjo/2018081800001.html\"}]}]}"
        },
        "advertising": [],
        "user": {
            "birthday": "1984-11-07",
            "lastLogin": "2018-08-20 10:21:53",
            "role": true,
            "created": "2018-06-06 18:17:22",
            "sex": 1,
            "name": "店主",
            "timeZone": "GMT+:08:00",
            "language": "zh-CN",
            "id": "3yqk0lao8s1s",
            "avatar": "/man.png",
            "state": 1,
            "username": "admin"
        },
        "tags": [
            {
                "id": "44jyfpac854w",
                "name": "热卖",
                "color": "11",
                "disabled": true
            },
            {
                "id": "44k0wayo0sg0",
                "name": "222",
                "color": "erwe",
                "disabled": true
            },
            {
                "id": "44k0xw99of7k",
                "name": "2221",
                "color": "erwe",
                "disabled": true
            },
            {
                "id": "44k0z18epqf4",
                "name": "ewqr",
                "color": "23",
                "disabled": false
            },
            {
                "id": "44k0zle1pukg",
                "name": "23",
                "color": "23",
                "disabled": false
            },
            {
                "id": "44k0zxs038xs",
                "name": "233",
                "color": "232",
                "disabled": false
            },
            {
                "id": "44k119jxoc1s",
                "name": "qwer",
                "color": "2323",
                "disabled": false
            },
            {
                "id": "44k11ujqyakg",
                "name": "qwer33",
                "color": "232322",
                "disabled": false
            },
            {
                "id": "44k12iskz08w",
                "name": "231231432",
                "color": "1341",
                "disabled": false
            },
            {
                "id": "44k12tozirr4",
                "name": "23123143223",
                "color": "1341123",
                "disabled": false
            },
            {
                "id": "44k1b51wy1hc",
                "name": "qerq",
                "color": "qerqe",
                "disabled": false
            },
            {
                "id": "44k1bm8ssdmo",
                "name": "qerqee",
                "color": "qerqeee",
                "disabled": false
            },
            {
                "id": "44k1buxn2kn4",
                "name": "qerqeew11",
                "color": "qerqeee22",
                "disabled": false
            },
            {
                "id": "44k1c35mjfnk",
                "name": "qerqeew112",
                "color": "qerqeee222",
                "disabled": false
            },
            {
                "id": "44k1cb05jzls",
                "name": "qerqeew1122",
                "color": "qerqeee2222",
                "disabled": false
            },
            {
                "id": "44k2dkx7tiww",
                "name": "111",
                "color": "222",
                "disabled": false
            },
            {
                "id": "44k2elq8yi2o",
                "name": "11112",
                "color": "22212",
                "disabled": false
            },
            {
                "id": "44k2ggeivzls",
                "name": "11",
                "color": "222",
                "disabled": false
            },
            {
                "id": "44k2hbni5pts",
                "name": "1122",
                "color": "22222",
                "disabled": false
            },
            {
                "id": "44klwtvh0zcw",
                "name": "32",
                "color": "33",
                "disabled": false
            },
            {
                "id": "44klx1etzvnk",
                "name": "3233",
                "color": "33",
                "disabled": false
            },
            {
                "id": "44klx7xmy9s0",
                "name": "323333",
                "color": "33",
                "disabled": false
            },
            {
                "id": "44s29s5pph4w",
                "name": "好吃",
                "color": "blank",
                "disabled": false
            },
            {
                "id": "44s2akf8ufwg",
                "name": "星空",
                "color": "blue",
                "disabled": false
            }
        ]
    }
}
}
/*	
*/
	
</script>

<style>
html,body{
    width: 100%;
    height: auto;
    background: #f0f0f0;
    overflow: hidden;
}
.app-main{
    width: 100%;
    height: 100%;
}
</style>


