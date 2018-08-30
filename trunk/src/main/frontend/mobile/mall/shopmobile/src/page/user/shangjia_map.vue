<template>
	<div id="mapitem">
	<div id="dtmap" style=" position: absolute; width: 100%; height: 100%; top: 0px; left: 0px;"></div>
   
	</div>
	 
</template>


 
<script>



		export default {
		
		name: 'mapitem',

		data() {
			return {
				a:"郑州航海路第二大街兴华大厦609室",
				b:"老屋新房网络科技有限公司",
				x:113.748321,
				y:34.724328
			}
		},

		methods: {
		
			mapitem:function  () {
				 var sContent =""+this.a+"</b><br>"+this.b+"";
	var map = new BMap.Map("dtmap");
	var point = new BMap.Point(this.x,this.y);
	map.centerAndZoom(point, 18);
	var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
	map.openInfoWindow(infoWindow,point); //开启信息窗口
		map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
	
var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	//右上角，仅包含平移和缩放按钮 
	
	map.addControl(top_left_control);        
		map.addControl(top_left_navigation);
			}
            	
		},

		mounted: function() {
      
      this.mapitem()
   
       
	}
	} 

             // 百度地图API功能
	
</script>

<style>
</style>