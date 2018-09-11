<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">Document List</span>
    	</div>
    	<md-layout md-column> 
    		<md-layout>
		    	<md-button class="md-raised md-primary" @click="addClick()">New Documnet</md-button>  		
		    </md-layout>
		    <md-layout> 
		    	<md-whiteframe md-elevation="2" class="component">
		    		<md-table>
		  				<md-table-header>
		    				<md-table-row>
		    					 <md-table-head>Document Name</md-table-head>
		    					 <md-table-head>Create Date</md-table-head>
		     					 <md-table-head>checkOut</md-table-head>
		     					 <md-table-head>Update</md-table-head>
		     					 <md-table-head>Delete</md-table-head>
		    				</md-table-row>
		 				</md-table-header>
		
						<md-table-body >
						    <md-table-row v-for="(row, index) in rows" :key="index" :md-item="row" md-auto-select md-selection>
						      <md-table-cell>{{row.name}}</md-table-cell>
						      <md-table-cell>{{row.createDate}}</md-table-cell>
						       <md-table-cell>{{row.checkOut}}</md-table-cell>
						       <md-table-cell>
						      	<md-button class="md-icon-button md-primary" @click="updateClick(row,index)">
						      		<md-icon>update</md-icon>
						      	</md-button>
						      </md-table-cell>
						      <md-table-cell>
						      	<md-button class="md-icon-button md-primary" @click="deleteClick(row,index)">
						      		<md-icon>close</md-icon>
						      	</md-button>
						      </md-table-cell>
						    </md-table-row>
						</md-table-body>
					</md-table>
		    	</md-whiteframe>
	    	</md-layout> 
    	</md-layout>
    	
    	<doc-dialog ref="dialog" @add-newdoc="addNewDoc" :curid="curId"></doc-dialog>
    </div>
</template>

<script>
module.exports = {
	props: ['curid'],
	data: function data() {
		return {
			rows: [],
			curItem: JSON.parse(JSON.stringify(DOCMODEL)),
			curId : ''
		};
	},
	watch : {
        curid : function curid(value) {
          	var _this = this;
          	_this.rows = [];
          	_this.curItem.id = value;
          	_this.curId = value;
          	if(value){
				axios.get(GETFOLDERDOCLIST, {
						headers: {
							DOC: JSON.stringify(_this.curItem)
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
		addNewDoc : function addNewDoc(item){
			item.createDate = 'Now';
			this.rows.push(item);
		},
		addClick : function addClick(){
			this.$refs['dialog'].open();  
		},
		updateClick : function updateClick(row,index){
			axios.get(UPDDOC, {
					headers: {
						DOC: JSON.stringify(row)
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
			axios.get(DELDOC, {
					headers: {
						DOC: JSON.stringify(row)
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
		}
	}
}
</script>
