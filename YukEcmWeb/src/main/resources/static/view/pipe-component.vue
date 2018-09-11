<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">Pipe</span>
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
					<div class="margin20 padlast">
						<md-input-container>
		    				<label>Pipe Id</label>
		    				<md-input disabled v-model="curItem.id"></md-input>
		  			 	</md-input-container>
						
						<md-input-container>
							<label>Name</label>
							<md-input required v-model="curItem.name"></md-input>
						</md-input-container>
					 
						<md-input-container>
							<label>ClassName</label>
							<md-input required v-model="curItem.className"></md-input>
						</md-input-container>
				
						<md-switch class="md-primary" v-model="curItem.passOnError">passOnError</md-switch>
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
			curItem : JSON.parse(JSON.stringify(PIPEMODEL))
		};
	},
	created: function created() {
		var _this = this;
			axios.get(GETPIPE, null).then(function (response) {
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
			rowClick: function rowClick(data){
				if(data.length > 0)
	        		this.curItem = data[0];
	        	else
	        		this.curItem = JSON.parse(JSON.stringify(PIPEMODEL));
			},
			add : function add(){
				var _this = this;
				axios.get(ADDPIPE, {
					headers: {
						PIPE: JSON.stringify(this.curItem)
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
				axios.get(UPDPIPE, {
					headers: {
						PIPE: JSON.stringify(this.curItem)
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
				axios.get(DELPIPE, {
					headers: {
						PIPE: JSON.stringify(item)
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
				this.curItem = JSON.parse(JSON.stringify(PIPEMODEL))
			}
	}
}
</script>
