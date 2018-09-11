<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">Repository</span>
    	</div>
    	
    	<md-layout>
		    	<md-whiteframe md-elevation="2" class="component">
		    		<md-table @select="rowClick">
		  				<md-table-header>
		    				<md-table-row>
		    					 <md-table-head>Id</md-table-head>
		     					 <md-table-head>Name</md-table-head>
		     					 <md-table-head>Delete</md-table-head>
		    				</md-table-row>
		 				</md-table-header>
		
						<md-table-body >
						    <md-table-row v-for="(row, index) in items" :key="index" :md-item="row" md-auto-select md-selection>
						      <md-table-cell>{{row.id}}</md-table-cell>
						      <md-table-cell>{{row.name}}</md-table-cell>
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
				    		<label>Repository Id</label>
				    		<md-input disabled v-model="curItem.id"></md-input>
				  		</md-input-container>
		  		
				  		<md-input-container>
				    		<label>Name</label>
				    		<md-input v-model="curItem.name"></md-input>
				  		</md-input-container>
		  		
				    	<md-tabs>
				  			<md-tab id="storage" md-label="Set Storage">
				    			<strrepo-component :curid="curItem.id"></strrepo-component>
				 			</md-tab>
				 			<md-tab id="pipe" md-label="Set Pipe">
				 				<piperepo-component :curid="curItem.id"></piperepo-component>
				 			</md-tab>
				    	</md-tabs>
		    		</div>
		    	</md-whiteframe>
    	
		    	<md-layout md-align="center">
					<md-button class="md-raised md-primary" @click="add()">Add</md-button>
					<md-button class="md-raised md-primary" @click="update()">Update</md-button>
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
				items : [],
				curItem : JSON.parse(JSON.stringify(REPOMODEL))
			};
		},
		created: function created() {
			var _this = this;
			axios.get(GETREPOSITORY, null).then(function (response) {
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
			rowClick : function listClick(data){
				if(data.length > 0)
        		this.curItem = data[0];
        	else
        		this.curItem = JSON.parse(JSON.stringify(REPOMODEL));
			},
			add : function add(){
				var _this = this;
				axios.get(ADDREPOSITORY, {
					headers: {
						REPOSITORY: JSON.stringify(this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.curItem.id = response.data;
					_this.items.push(_this.curItem);
				} else {
					OpenError(response.data);
				}
				}).catch(function (error) {
					OpenError(error);
				});
			},
			update : function update(){
				axios.get(UPDREPOSITORY, {
					headers: {
						REPOSITORY: JSON.stringify(this.curItem)
					}
				}).then(function (response) {
				if (response.status !== 200) {
					OpenError(response.data);
				} 
				}).catch(function (error) {
					OpenError(error);
				});
			},
			deleteClick : function deleteClick(item,index){
				var _this = this;
				axios.get(DELREPOSITORY, {
					headers: {
						REPOSITORY: JSON.stringify(item)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.items.splice(index,1);
				} else {
					OpenError(response.data);
				}
				}).catch(function (error) {
					OpenError(error);
				});
			},			
			clean : function clean(){
				this.curItem = JSON.parse(JSON.stringify(REPOMODEL))
			}
		} 
}
</script>