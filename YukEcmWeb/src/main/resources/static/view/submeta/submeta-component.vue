<template>
    <div class="margin20">
    	<md-whiteframe md-elevation="2">
  			<div class="margin20">
    			<span class="md-title">MetaData Detail</span>
    		</div>
    		
  			<md-table @select="rowClick">
    		<md-table-header>
    			<md-table-row>
    				 <md-table-head>KeyName</md-table-head>
      				 <md-table-head>Order</md-table-head>
      				 <md-table-head>Type</md-table-head>
      				 <md-table-head>Delete</md-table-head>
    			</md-table-row>
    		</md-table-header>
    		<md-table-body>
    			<md-table-row v-for="(row, index) in rows" :key="index" :md-item="row" md-auto-select md-selection>
    				  <md-table-cell>{{row.keyName}}</md-table-cell>
				      <md-table-cell>{{row.order}}</md-table-cell>
				      <md-table-cell>{{row.type}}</md-table-cell>
				      <md-table-cell>
				      	<md-button class="md-icon-button md-primary" @click="deleteClick(row,index)">
				      		<md-icon>close</md-icon>
				      	</md-button>
				      </md-table-cell>
				</md-table-row>
    		</md-table-body>
    		</md-table> 	
    
    		<md-input-container>
    			<label>Name</label>
    			<md-input v-model="curSubItem.keyName"></md-input>
  			 </md-input-container>
    		
    		<md-input-container>
    			<label >Order</label>
    			<md-input type="number" v-model="curSubItem.order"></md-input>
  			 </md-input-container>
  			 
  			  <md-input-container>
    			<label for="type">Type</label>
   				<md-select name="type" id="type" v-model="curSubItem.type">
     				<md-option value="STRING">STRING</md-option>
      				<md-option value="INT">INT</md-option>
      				<md-option value="DATE">DATE</md-option>
      				<md-option value="LONG">LONG</md-option>
      				<md-option value="FLOAT">FLOAT</md-option>
      				<md-option value="BOOLEAN">BOOLEAN</md-option>
      				<md-option value="BYTE">BYTE</md-option>
    			</md-select>
 			 </md-input-container>
 			 
    		<md-layout md-align="center">
    			<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
    		</md-layout>
    	</md-whiteframe>
    </div>
</template>

<script>
module.exports = {
	props: ['curitem'],
	data : function data(){
		return {
			rows : [],
			curItem : JSON.parse(JSON.stringify(METASETTINGMODEL)),
			curSubItem : JSON.parse(JSON.stringify(METASETTINGTYPEMODEL))
		};
	},
	watch : {
        curitem : function curitem(value) {
          	var _this = this;
          	if(value.name){
	          	_this.curItem.name = value.name;
	          	for(var obj in value.map){
					_this.rows.push(value.map[obj]);
				}
			}
			else{
				_this.rows = [];
				_this.curItem = JSON.parse(JSON.stringify(METASETTINGMODEL));
				_this.curSubItem = JSON.parse(JSON.stringify(METASETTINGTYPEMODEL));
			}
        }
   	},
	methods : {
		rowClick: function rowClick(data){
			if(data.length > 0)
        		this.curSubItem = data[0];
        	else
        		this.curSubItem = JSON.parse(JSON.stringify(METASETTINGTYPEMODEL));
		},
		addClick : function addClick(item,index){
			var _this = this;
			_this.curItem.map[_this.curSubItem.keyName] = _this.curSubItem;
			axios.get(ADDMETASUBSETTING, {
					headers: {
						METASETTING: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.rows.push(_this.curSubItem);
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		deleteClick : function deleteClick(row,index){
			var _this = this;
			_this.curItem.map[row.keyName] = row;
			axios.get(DELMETASUBSETTING, {
					headers: {
						METASETTING: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.rows.splice(index,1);
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