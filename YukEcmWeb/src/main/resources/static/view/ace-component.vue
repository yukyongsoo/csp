<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">ACE</span>
    	</div>
    	<md-layout> 
		    	<md-whiteframe md-elevation="2" class="component">
		    		<md-table @select="rowClick">
		  				<md-table-header>
		    				<md-table-row>
		    					 <md-table-head>AclId</md-table-head>
		     					 <md-table-head>AceId</md-table-head>
		     					 <md-table-head>Delete</md-table-head>
		    				</md-table-row>
		 				</md-table-header>
		
						<md-table-body >
						    <md-table-row v-for="(row, index) in rows" :key="index" :md-item="row" md-auto-select md-selection>
						      <md-table-cell>{{row.id}}</md-table-cell>
						      <md-table-cell>{{row.childId}}</md-table-cell>
						      <md-table-cell>
						      	<md-button class="md-icon-button md-primary" @click="deleteClick(row,index)">
						      		<md-icon>close</md-icon>
						      	</md-button>
						      </md-table-cell>
						    </md-table-row>
						</md-table-body>
					</md-table>
		    	</md-whiteframe>
	    	
	    	<md-layout md-column  class="component"> 
		    	<md-whiteframe md-elevation="2">
			    	<div>
			    		 <md-input-container>
			    			<label>Acl Id</label>
			    			<md-input disabled v-model='curItem.id'></md-input>
			  			 </md-input-container>
			  			 
			  			 <md-input-container>
			    			<label>Ace Id</label>
			    			<md-input required v-model='curItem.childId'></md-input>
			  			 </md-input-container>
			  			 
			  			 <div>
			  			 	Has Permission
			  			 </div>
			  			 
			  			<md-whiteframe md-elevation="2">
			    			<md-switch v-for="(value, key,index) in curItem.permissionMap" :key="index" class="md-primary" v-model="curItem.permissionMap[key]">{{key}}</md-switch>
			    		</md-whiteframe>
			    	</div>
		    	</md-whiteframe>
		    
		    	<md-layout md-align="center">
		    		<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
		    		<md-button class="md-raised md-primary" @click="updateClick()">Update</md-button>    		
		    		<md-button class="md-raised md-primary" @click="clean()">Clean</md-button>
		    	</md-layout>
	    	</md-layout> 
    	</md-layout>
    </div>
</template>

<script>
module.exports = {
	props: ['curid'],
	data: function data() {
		return {
			rows: [],
			curItem: JSON.parse(JSON.stringify(ACEMODEL)),
			curId : ""
		};
	},
	watch : {
        curid : function curid(value) {
          	var _this = this;
          	_this.rows = [];
          	if(value){
	          	_this.curItem.id = value;
	          	_this.curId = value;
				axios.get(GETACE, {
						headers: {
							ACE: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
					if (response.status === 200) {
						for (var i = 0; i < response.data.length; i++) {
							_this.rows.push(response.data[i]);
						}
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			}
        }
    },
	methods: {
		rowClick: function rowClick(data){
			if(data.length > 0){
        		this.curItem = data[0];
        		this.curItem.id = this.curId;
        	}
        	else {
        		this.curItem = JSON.parse(JSON.stringify(ACEMODEL));
        		this.curItem.id = this.curId;
        	}
		},
		addClick : function addClick(){
			var _this = this;
			axios.get(ADDACE, {
					headers: {
						ACE: JSON.stringify(this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.rows.push(_this.curItem);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		updateClick : function updateClick(){
			axios.get(UPDACE, {
					headers: {
						ACE: JSON.stringify(this.curItem)
					}
				}).then(function (response) {
					if (response.status !== 200) {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		deleteClick: function deleteClick(row,index) {
			var _this = this;
			axios.get(DELACE, {
					headers: {
						ACE: JSON.stringify(row)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.rows.splice(index, 1);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});				
		},
		clean : function clean(){
			this.curItem = JSON.parse(JSON.stringify(ACEMODEL));
			this.curItem.id = this.curId;
		}
	}
}
</script>
