<template>
    <div>
    	<md-input-container>
    		<label>LimitTime</label>
    		<md-input type="number" v-model="curSubItem.limitTime"></md-input>
  		</md-input-container> 
  		
  		<md-input-container>
    		<label>fileType</label>
    		<md-input v-model="curSubItem.fileType"></md-input>
  		</md-input-container> 
  		
  		 <md-switch class="md-primary" v-model="curSubItem.dbUpdate">DB update Mode</md-switch>
  		 <md-switch class="md-primary" v-model="curSubItem.copyed">Content Copyed</md-switch>
  		 
  		<md-whiteframe md-elevation="2">
  			<div class="margin20">
    			<span class="md-title">Migration Detail Rule</span>
    		</div>
    		
  		 	<md-table>
    		<md-table-header>
    			<md-table-row>
    				 <md-table-head>server Address</md-table-head>
    				 <md-table-head>Target WorkingGroup Id</md-table-head>
      				 <md-table-head>Target Repository Id</md-table-head>
    			</md-table-row>
    		</md-table-header>
    		<md-table-body>
    			<md-table-row v-for="(row, index) in curSubItem.targetList" :key="index">
      				<md-table-cell>{{row.serverAddress}}</md-table-cell>
      				<md-table-cell>{{row.targetWorkId}}</md-table-cell>
      				<md-table-cell>{{row.targetRepoId}}</md-table-cell>
      				<md-table-cell>
				      	<md-button class="md-icon-button md-primary" @click="deleteClick(row,index)">
				      		<md-icon>close</md-icon>
				      	</md-button>
				    </md-table-cell>
   				</md-table-row>
    		</md-table-body>
    		</md-table>
    		
    		<md-input-container>
    			<label>ServerAddress</label>
    			<md-input v-model="curMigSubItem.serverAddress"></md-input>
  			</md-input-container> 
  		
  			<md-input-container>
    			<label>Target Working Group Id</label>
    			<md-input v-model="curMigSubItem.targetWorkId"></md-input>
  			</md-input-container> 
  		
  			<md-input-container>
    			<label>Target Repository Id</label>
    			<md-input v-model="curMigSubItem.targetRepoId"></md-input>
  			</md-input-container> 
  			
    		<md-layout md-align="center">
    			<md-button class="md-raised md-primary" @click="addClick()">Add Mig Target</md-button>
    		</md-layout>
  		</md-whiteframe md-elevation="2">
  		
  		<md-layout md-align="center">
    		<md-button class="md-raised md-primary" @click="setClick()">Set Rule</md-button>
    	</md-layout>
    </div>
</template>

<script>
//MIGRULEMODEL = {"id":"","strId":"","fileType":"","copyed":false,"limitTime":-1,"dbUpdate":false,"targetList":[]};
//MIGSUBRULEMODEL = {"serverAddress":"","targetWorkId":"","targetRepoId":""};
module.exports = {
	props: ['curitem'],
	data: function data() {
		return {
			curSubItem: JSON.parse(JSON.stringify(MIGRULEMODEL)),
			curMigSubItem : JSON.parse(JSON.stringify(MIGSUBRULEMODEL))
		};
	},
	watch : {
        curitem : function curitem(value) {
          	if(value.id && value.ruleType == 'MIGRULE'){
          		this.curSubItem.id = value.id;
          		this.curSubItem.strId = value.migRule.strId;
          		this.curSubItem.copyed = value.migRule.copyed;
          		this.curSubItem.dbUpdate = value.migRule.dbUpdate;
          		this.curSubItem.fileType = value.migRule.fileType;
          		this.curSubItem.limitTime = value.migRule.limitTime;
          		this.curSubItem.targetList = value.migRule.targetList;
          	}
          	else{
          		this.curSubItem.id = "";
          		this.curSubItem = JSON.parse(JSON.stringify(MIGRULEMODEL));
          	}
        }
   	},
   	methods: {
		addClick : function addClick(){
			this.curSubItem.targetList.push(this.curMigSubItem);
		},
		deleteClick: function deleteClick(row,index) {
			this.curSubItem.targetList.splice(index,1);	
		},
		setClick: function setClick(){
			this.$emit('setmig', this.curSubItem);
		}
	}
}
</script>

