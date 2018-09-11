<template>
    <div>
    	<md-input-container>
    		<label>LifeCycle Id</label>
    		<md-input disabled v-model="curItem.id"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>Name</label>
    		<md-input v-model="curItem.name"></md-input>
  		</md-input-container>
  			
  		<md-input-container>
    		<label>Rule Type</label>
   			<md-select name="Rule type" v-model="curItem.type">
      			<md-option value="MIGRULE">Migration Rule</md-option>
      			<md-option value="DESRULE">Destruction Rule</md-option>
    		</md-select>
  		</md-input-container>
  		
  		 <md-input-container>
	   		<label>WorkingGroup Id</label>
	   		<md-input v-model="curItem.workId"></md-input>
	  	</md-input-container>
  		
  		<md-switch class="md-primary" v-model="curItem.loopBack">LoopBack</md-switch>
  		
	    <md-input-container>
	   		<label>Thread</label>
	   		<md-input type="number" v-model="curItem.thread"></md-input>
	  	</md-input-container>
	  	
	  	<md-input-container>
	   		<label>Start Range</label>
	   		<md-input type="number" v-model="curItem.startingRange"></md-input>
	  	</md-input-container>
	  	
	  	 <md-input-container>
	   		<label>End Range</label>
	   		<md-input type="number" v-model="curItem.endRange"></md-input>
	  	</md-input-container>
	  	
	  	<md-input-container>
    		<label>Starting Cron Expression</label>
    		<md-input v-model="curItem.startingCron"></md-input>
  		</md-input-container>
  		
  		<md-input-container>
    		<label>End Cron Expression</label>
    		<md-input v-model="curItem.endingCron"></md-input>
  		</md-input-container>
    	
    	<md-layout md-align="center">
    		<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
    		<md-button class="md-raised md-primary" @click="clean()">Clean</md-button>
    	</md-layout>
    </div>
</template>

<script>
module.exports = {
	props: ['curitem'],
	data : function data(){
		return {
			curItem : JSON.parse(JSON.stringify(LCSETTINGMODEL))
		};
	},
	watch : {
        curitem : function curitem(value) {
          	var _this = this;
          	if(value.id){
	          	_this.curItem = value;
			}
			else{
				_this.curItem =  JSON.parse(JSON.stringify(LCSETTINGMODEL));
			}
        }
   	},
	methods : {
		addClick : function addClick(){
			var _this = this;
			axios.get(ADDLCSCH, {
					headers: {
						LCSETTING: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.curItem.id = response.data;
					_this.$emit('addrow',_this.curItem);
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		clean : function clean(){
			this.$emit('clean');
		}
	}
}
</script>


