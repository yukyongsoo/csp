<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">MetaData</span>
    	</div>
    		<md-layout>
			    <md-whiteframe md-elevation="2" class="component">
			    	<md-table @select="rowClick">
			    		<md-table-header>
			    			<md-table-row>
			    				 <md-table-head>Name</md-table-head>
			      				 <md-table-head>Type</md-table-head>
			      				 <md-table-head>Delete</md-table-head>
			    			</md-table-row>
			    		</md-table-header>
			    		<md-table-body >
							    <md-table-row v-for="(row, index) in rows" :key="index" :md-item="row" md-auto-select md-selection>
							      <md-table-cell>{{row.name}}</md-table-cell>
							      <md-table-cell>{{row.type}}</md-table-cell>
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
			    	<div class="margin20 padlast">
			    		<md-input-container>
			    			<label>Name</label>
			    			<md-input v-model="curItem.name"></md-input>
			  			 </md-input-container>
			  
			  			 <md-input-container>
			    			<label for="type">Type</label>
			   				<md-select name="type" id="type" v-model="curItem.type">
			     				<md-option value="ADD">ADD</md-option>
			      				<md-option value="GET">GET</md-option>
			      				<md-option value="DEL">DEL</md-option>
			    			</md-select>
			 			 </md-input-container>
			  			 
			    		 <md-input-container>
			    			<label>Query</label>
			    			<md-input v-model="curItem.query"></md-input>
			  			 </md-input-container>
			  			 
			  			<submeta-component class="subcomponent" :curitem="curItem"></submeta-component>
			    	</div>
			    	</md-whiteframe>
			    	
			    	<md-layout md-align="center">
			    		<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
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
			curItem: JSON.parse(JSON.stringify(METASETTINGMODEL))
		};
	},
	created: function created() {
		var _this = this;
		axios.get(GETMETASETTINGLIST, null).then(function (response) {
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
        		this.curItem = JSON.parse(JSON.stringify(METASETTINGMODEL));
		},
		addClick : function addClick(){
			var _this = this;
			axios.get(ADDMETASETTING, {
					headers: {
						METASETTING: JSON.stringify(this.curItem)
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
		deleteClick: function deleteClick(row,index) {
			var _this = this;
			axios.get(DELMETASETTING, {
					headers: {
						METASETTING: JSON.stringify(row)
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
			this.curItem = JSON.parse(JSON.stringify(METASETTINGMODEL));
		}
	}
}
</script>