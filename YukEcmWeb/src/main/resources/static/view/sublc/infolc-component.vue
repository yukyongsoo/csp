<template>
    <div> 	
    	<md-switch class="md-primary" v-model="autoRefresh">AutoRefresh</md-switch>
    	
    	<md-input-container>
    		<label>State</label>
    		<md-input disabled v-model="curItem.state"></md-input>
  		</md-input-container>
  		
    	<md-input-container>
    		<label>Total</label>
    		<md-input disabled v-model="curItem.totalTarget"></md-input>
  		</md-input-container>
    	
    	<md-input-container>
    		<label>Excuted</label>
    		<md-input disabled v-model="curItem.excuted"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>Error</label>
    		<md-input disabled v-model="curItem.error"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>Start Time</label>
    		<md-input disabled v-model="curItem.start"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>End Time</label>
    		<md-input disabled v-model="curItem.end"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>Running Time</label>
    		<md-input disabled v-model="curItem.runTime"></md-input>
  		</md-input-container>
  		
    	<md-layout md-align="center">
    		<md-button class="md-raised md-primary" @click="startClick()">Start</md-button>
    		<md-button class="md-raised md-primary" @click="stopClick()">Stop</md-button>
    	</md-layout>
    </div>
</template>

<script>
module.exports = {
	props: ['curitem'],
	data : function data(){
		return {
			curItem : JSON.parse(JSON.stringify(LCINFOMODEL)),
			autoRefresh : true,
			interval: null
		};
	},
	watch : {
        curitem : function curitem(value) {
          	var _this = this;
          	clearInterval(_this.interval);
          	if(value.id){
          		_this.curItem.id = value.id;
				_this.interval = setInterval(function () {
      				_this.loadData();
   				}.bind(_this), 10000);
			}
			else{
				_this.curItem = JSON.parse(JSON.stringify(LCINFOMODEL));
			}
        }
   	},
	methods : {
		startClick : function startClick(){
			var _this = this;
			axios.get(STARTLC, {
					headers: {
						LCINFO: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status !== 200) {
					OpenError(response.data);
				} 
			}).catch(function (error) {
				OpenError(error);
			});
		},
		stopClick : function stopClick(){
			var _this = this;
			axios.get(STOPLC, {
					headers: {
						LCINFO: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status !== 200) {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		loadData: function loadData() {
			 var _this = this;
	    	 if(_this.autoRefresh){
				axios.get(GETLCINFO, {
						headers: {
							LCINFO: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
					if (response.status === 200) {
						_this.curItem = response.data;
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
					clearInterval(_this.interval);
				});	
			}
	    }
	},
	beforeDestroy: function(){
		clearInterval(this.interval);
	}
}
</script>
