<template>
    <div>
    	<md-layout>
	    	<md-layout md-flex="47">
		    	<md-whiteframe md-elevation="2" class="wh100">
		    		<div>Storage List</div>
				    	<md-list>
				    		<md-list-item v-for="(item,index) in items" :key="index" @click="addClick(item,index)">
				    			<md-icon  md-src="assets/icon-str.png"></md-icon>
				    				<span>{{item.name}}({{item.id}})</span>
				    		</md-list-item>
				    	</md-list>
		    	</md-whiteframe>
	    	</md-layout>
    	
	    	<md-layout  md-flex="5">
				<md-icon>keyboard_arrow_right</md-icon>
			</md-layout>
			
		 	<md-layout md-flex="47">
		    	<md-whiteframe md-elevation="2" class="wh100">	
		  		 	<md-table>
		    		<md-table-header>
		    			<md-table-row>
		    				 <md-table-head >Storage Id</md-table-head>
		      				 <md-table-head >Put Order</md-table-head>
		      				 <md-table-head >Get Order</md-table-head>
		      				 <md-table-head>Delete</md-table-head>
		    			</md-table-row>
		    		</md-table-header>
		    		<md-table-body>
		    			<md-table-row v-for="(row,index) in rows" :key="index" :md-item="row">
		      				<md-table-cell>{{row.strId}}</md-table-cell>
		      				<md-table-cell>{{row.putOrder}}</md-table-cell>
		      				<md-table-cell>{{row.getOrder}}</md-table-cell>
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
    </div>
</template>

<script>
module.exports = {
	props: ['curid'],
	data: function data() {
		return {
			items : [],
			rows : [],
			curItem : JSON.parse(JSON.stringify(REPOMODEL))
		};
	},
	created: function created() {
		var _this = this;
		axios.get(GETSTORAGE, null).then(function (response) {
			if (response.status === 200) {
				for (var i = 0; i < response.data.length; i++) {
					_this.items.push(response.data[i]);
				}
			} else {
				OpenError(response.data);
			}
		}).catch(function (error) {
			OpenError(error);
		});
	},
	watch : {
        curid : function curid(value) {
          	var _this = this;
          	_this.rows = [];
          	if(value){
	          	_this.curItem.id = value;
				axios.get(GETREPOSTR, {
						headers: {
							REPOSITORY: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
					if (response.status === 200) {
						for(var obj in response.data.putOrderList){
							_this.rows.push(response.data.putOrderList[obj]);
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
	methods : {
		addClick : function addClick(item,index){
			var _this = this;
			_this.curItem.id = _this.curid;
			var temp = JSON.parse(JSON.stringify(SUBREPOSTRMODEL))
			temp.strId = item.id;
			_this.curItem.putOrderList = {0 : temp};
			axios.get(ADDREPOSTR, {
					headers: {
						REPOSITORY: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.rows.push(response.data.putOrderList[0]);
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		deleteClick : function deleteClick(row,index){
			var _this = this;
			_this.curItem.id = _this.curid;
			_this.curItem.putOrderList = {0 : row};
			axios.get(DELREPOSTR, {
					headers: {
						REPOSITORY: JSON.stringify(_this.curItem)
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
