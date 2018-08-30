<template>
	<div id="bottom">
		<div class="floorFixed">
				<ul>
					<li class="Bohome">
						<router-link to="/">
							<img src="../assets/img/homeicon.png"/>
						</router-link >
						
					</li> 
					<li class="Botrlly"  v-for="(item ,index) in flooBox" :class="{active:flooBox[index].type==true}" @click="Bomoresh(index)">
						<!---->
						<div v-if="flooBox[index].fMulist.length>0">
							
							<span  v-if="flooBox[index].fMulist.length>0">
							 <img src="../assets/img/mumore.png"/>
						    </span>
					       {{flooBox[index].title}}
						
						<div  v-if="flooBox[index].fMulist.length>0">
							<div class="Bomore">
							
							<p v-for="(item,index) in flooBox[index].fMulist"><router-link :to="{name:item.href,params:{name:item.iconName}}">{{item.fMulistname}}</router-link></p>
							
						    </div>
						</div>
						</div>
						<!---->
						<div v-else="">
							<router-link :to="flooBox[index].herf">
							<span  v-if="flooBox[index].fMulist.length>0">
							 <img src="../assets/img/mumore.png"/>
						    </span>
					       {{flooBox[index].title}}
						</router-link>
						<div  v-if="flooBox[index].fMulist.length>0">
							<div class="Bomore">
							<p v-for="(item,index) in flooBox[index].fMulist"><router-link :to="item.herf">{{item.fMulistname}}</router-link></p>
						    </div>
						</div>
						</div>
						
						
						
					</li>
					
					
					<!--<li class="Botrlly">
						<a>
							<span>
							 <img src="img/mumore.png"/>
						    </span>
					   {{flooBox[1].title}}
						</a>
						<div  v-if="flooBox[2].fMulist.length>0">
							<div class="Bomore" >
								<p><a>hhh</a></p>
						</div>
						</div>
						
						
					</li>
					
					<li class="Botrlly" :class="{active:floorFixedI==4}" @click="Bomoresh(4)">
						<a>
							<span>
							 <img src="img/mumore.png"/>
						    </span>
					    {{flooBox[2].title}}
						</a>
					 <div v-if="flooBox[2].fMulist.length>0">
					 	<div class="Bomore">
							<p><a>hhh</a></p>
						</div>
					 </div>
						
						
					</li>-->
				</ul>
			</div>
	</div>
</template>

<script>
	
		export default {
		
		name: 'bottom',

		data() {
			return {
				flooBox:[{
					"title": "商品类别",
                    "herf":"/trolley",
                    "type": false,
					"fMulist": [{
							"fMulistname": "全部商品",
							"iconName": "所有",
					         "href":"all",
							
						},
						
					]
				},{
					"title": "购物车",
                     "herf":"/trolley",
                     "type": false,
					"fMulist": [
					]
				},{
					"title": "个人中心", 
					"type": false,
                   "herf":"/user",
					"fMulist": [
					
					]
				},
				]
			}
		},

		methods: {
            	Bomoresh:function  (index) {
				
					if (this.flooBox[index].type==false) {
						for (var i=0 ;i<this.flooBox.length;i++) {
							this.flooBox[i].type=false
						}
						
					this.flooBox[index].type=true
					}else{
						for (var i=0 ;i<this.flooBox.length;i++) {
							this.flooBox[i].type=false
						}
						this.flooBox[index].type=false
					}
				}
		},

		mounted: function() {
       
	}
	} 
	
</script>

<style>
</style>