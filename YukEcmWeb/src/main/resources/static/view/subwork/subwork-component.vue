<template>
    <div>
    	 <md-boards :md-swipeable="true">
  				<md-board id="slide1">
  					<md-list>
    					<md-list-item v-for="(item,index) in curItem.initList" :key="index">
    						<md-icon md-src="assets/icon-work.png"></md-icon>
    						<span>{{item}}</span>
	    					<md-button class="md-icon-button md-list-action" @click="deleteRuleRowClick(item,index)">
	    						<md-icon class="md-primary">close</md-icon>
	    					</md-button>
    					</md-list-item>    				
    				 </md-list>
    				 
    				 <md-input-container>
    					<label>Initial Rule Id</label>
    					<md-input v-model="curInitInput"></md-input>
  			 		</md-input-container>
  			 		
  			 		<md-button class="md-raised md-primary" @click="addRuleRowClick()">Add Rule</md-button>
  				</md-board>

 				<md-board id="slide2">
   					<md-input-container>
    					<label>Current Migration Rule Id</label>
    					<md-input v-model="curItem.migId"></md-input>
  					</md-input-container>
  					
  					<md-button class="md-raised md-primary" @click="addRuleClick('MIGRULE')">Set Rule</md-button>
  					<md-button class="md-raised md-primary" @click="deleteRuleClick('MIGRULE')">UnSet Rule</md-button>
  				</md-board>

  				<md-board id="slide3">
   					<md-input-container>
    					<label>Current Destruction Rule Id</label>
    				<md-input v-model="curItem.desId"></md-input>
  			 		</md-input-container>
  			 		
  			 		<md-button class="md-raised md-primary" @click="addRuleClick('DESRULE')">Set Rule</md-button>
  			 		<md-button class="md-raised md-primary" @click="deleteRuleClick('DESRULE')">UnSet Rule</md-button>
  				</md-board>
			</md-boards>
    </div>
</template>

<script>
module.exports = {
	props: ['curid'],
	data: function data() {
		return {
			curItem: JSON.parse(JSON.stringify(WORKINGMODEL)),
			curInitInput : ""
		};
	},
	watch : {
        curid : function curid(value) {
          	if(value){
          		var _this = this;
          		_this.curItem.id = value;
				axios.get(GETWORKRULE, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.curItem = response.data;
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});		
          	}
          	else{
          		this.curItem = JSON.parse(JSON.stringify(WORKINGMODEL));
          	}
        }
   	},
   	methods: {
		addRuleRowClick: function addRuleRowClick(){
			var _this = this;
			_this.curItem.tempType = "INITRULE";
			_this.curItem.tempId = _this.curInitInput;
			axios.get(ADDWORKRULE, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.curItem.initList.push(_this.curInitInput);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		deleteRuleRowClick: function deleteRuleRowClick(row,index){
			var _this = this;
			_this.curItem.tempType = "INITRULE";
			_this.curItem.tempId = row;
			axios.get(DELWORKRULE, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.curItem.initList.splice(index, 1);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		addRuleClick: function addRuleClick(type){
			var _this = this;
			if(type === 'MIGRULE'){
				_this.curItem.tempType = "MIGRULE";
				_this.curItem.tempId = _this.curItem.migId;
			}
			else if(type === 'DESRULE'){
				_this.curItem.tempType = "DESRULE";
				_this.curItem.tempId = _this.curItem.desId;
			}
			axios.get(ADDWORKRULE, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status !== 200) {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		deleteRuleClick: function deleteRuleClick(type){
			var _this = this;
			if(type === 'MIGRULE'){
				_this.curItem.tempType = "MIGRULE";
				_this.curItem.tempId = _this.curItem.migId;
			}
			else if(type === 'DESRULE'){
				_this.curItem.tempType = "DESRULE";
				_this.curItem.tempId = _this.curItem.desId;
			}
			axios.get(DELWORKRULE, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						if(type === 'MIGRULE'){
							_this.curItem.migId = "";
						}
						else if(type === 'DESRULE'){
							_this.curItem.desId = "";
						}
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