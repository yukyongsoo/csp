<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">Working Group</span>
    	</div>
    	
    	<md-layout>
		    <md-whiteframe md-elevation="2" class="component">
		    	<md-table @select="rowClick">
		    		<md-table-header>
		    			<md-table-row>
		    				 <md-table-head>Id</md-table-head>
		      				 <md-table-head>Name</md-table-head>
		      				 <md-table-head>Audit</md-table-head>
		      				 <md-table-head>Delete</md-table-head>
		    			</md-table-row>
		    		</md-table-header>
		    		<md-table-body>
		    			<md-table-row v-for="(row, index) in rows" :key="index" :md-item="row" md-auto-select md-selection>
		      				<md-table-cell>{{row.id}}</md-table-cell>
		      				<md-table-cell>{{row.name}}</md-table-cell>
		      				<md-table-cell>{{row.audit}}</md-table-cell>
		      				<md-table-cell>
						      <md-button class="md-icon-button md-primary" @click="deleteClick(row,index)">
						      	<md-icon>close</md-icon>
						      </md-button>
						    </md-table-cell>
		   				</md-table-row>
		    		</md-table-body>
		    	</md-table>
		    </md-whiteframe>
		    	
		    <md-layout md-column class="component">
		    	<md-whiteframe md-elevation="2">
		    	<div>
		    		 <md-input-container>
		    			<label>Work Id</label>
		    			<md-input disabled v-model="curItem.id"></md-input>
		  			 </md-input-container>
		  			 
		  			 <md-input-container>
		    			<label>Name</label>
		    			<md-input required v-model="curItem.name"></md-input>
		  			 </md-input-container>
		  			 
		  			 <md-switch class="md-primary" v-model="curItem.audit">Audit</md-switch>
		    		
		    		<md-whiteframe md-elevation="2">
		    			<subwork-component :curid="curItem.id"></subworkcomponent>
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
	data: function data() {
		return {
			rows: [],
			curItem: JSON.parse(JSON.stringify(WORKINGMODEL))
		};
	},
	created: function created() {
		var _this = this;
		axios.get(GETWORK, null).then(function (response) {
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
	},
	methods: {
		rowClick: function rowClick(data){
			if(data.length > 0)
        		this.curItem = data[0];
        	else
        		this.curItem = JSON.parse(JSON.stringify(WORKINGMODEL));
		},
		addClick : function addClick(){
			var _this = this;
			axios.get(ADDWORK, {
					headers: {
						WORKINGGROUP: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.curItem.id = response.data;
						_this.rows.push(_this.curItem);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
			});		
		},
		updateClick : function updateClick(){
			var _this = this;
			axios.get(UPDWORK, {
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
		deleteClick: function deleteClick(row,index) {
			var _this = this;
			axios.get(DELWORK, {
					headers: {
						WORKINGGROUP: JSON.stringify(row)
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
			this.curItem = JSON.parse(JSON.stringify(WORKINGMODEL));
		}
	}
}
</script>
