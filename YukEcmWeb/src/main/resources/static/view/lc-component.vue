<template>
    <div>
    	<div class="subTitle"> 
    		<span class="md-title">LifeCycle</span>
    	</div>
    	
    	<md-layout>
			<md-whiteframe md-elevation="2" class="component">
				<md-list>
					<md-list-item v-for="(item,index) in items" :key="index" @click="listClick(item,index)">
	    				<md-icon>account_circle</md-icon>
	    					<span>{{item.name}}({{item.id}})</span>
	    				<md-button class="md-icon-button md-list-action" @click="deleteClick(item,index)">
	    					<md-icon class="md-primary">close</md-icon>
	    				</md-button>
	    			</md-list-item>    	
				</md-list>
			</md-whiteframe>
			
			<md-layout>
				<md-tabs>
	  				<md-tab id="setting" md-label="LifeCycle Setting">
	    				<md-whiteframe md-elevation="2">
							<settinglc-component :curitem="curItem" @clean="clean" @addrow="addrow"></settinglc-component> 
						</md-whiteframe>
	  				</md-tab>
	  				
	  				<md-tab id="info" md-label="LifeCycle info">
	    				<md-whiteframe md-elevation="2">
							<infolc-component :curitem="curItem"></infolc-component>
						</md-whiteframe>
	  				</md-tab>
	  			</md-tabs>	
  			</md-layout>
		</md-layout>
    </div>
</template>

<script>
module.exports = {
		data: function data() {
			return {
				items : [],
				curItem : JSON.parse(JSON.stringify(LCSETTINGMODEL))
			};
		},
		created: function created() {
			var _this = this;
			axios.get(GETLCSCH, null).then(function (response) {
				if (response.status === 200) {
					for(var i =0; i < response.data.length; i++){
						_this.items.push(response.data[i]);
					}
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		methods: {
			listClick: function listClick(item, index) {
				this.curItem = item;
			},
			clean : function clean(){
				this.curItem = JSON.parse(JSON.stringify(LCSETTINGMODEL));
			},
			addrow : function addrow(data){
				this.items.push(data);
			},
			deleteClick : function deleteClick(item,index){
				var _this = this;
				axios.get(DELLCSCH, {
					headers: {
						LCSETTING: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.items.splice(index,1);
						_this.curItem = JSON.parse(JSON.stringify(LCSETTINGMODEL));
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
							
			}
		}
}
</script>

