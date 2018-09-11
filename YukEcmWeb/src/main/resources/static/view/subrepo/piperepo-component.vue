<template>
    <div> 
    	<md-layout>
  			<md-layout md-flex="50">
		    	<md-whiteframe md-elevation="2" class="wh100">
		    		<div>Pipe List</div>
		    		<md-list>
		    			<md-list-item v-for="(item,index) in items" :key="index" @click="addClick(item,index)">
		    				<md-icon>filter_list</md-icon>
		    					<span>{{item.name}}({{item.id}})</span>
		    			</md-list-item>
		    		<md-list>
		    	</md-whiteframe>
	    	</md-layout>
	    	
	    	<md-layout  md-flex="5">
		    	<md-icon>keyboard_arrow_right</md-icon>
		    </md-layout>
		    
	    	<md-layout  md-flex="45">
		  		<md-whiteframe md-elevation="2" class="wh100">
		  			<div>Adjusted List</div>
		  		 	<md-list>
		    			<md-list-item v-for="(row, index) in rows" :key="index" @click="deleteClick(row,index)">
		    				<md-icon>filter_list</md-icon>
		    					<span>{{row.pipeId}}({{row.order}})</span>
		    			</md-list-item>
		    		<md-list>
		  		</md-whiteframe md-elevation="2">
	  		</md-layout>
  		</md-layout>
    </div>
</template>

<script>
module.exports = {
	props: ['curid'],
	data : function data(){
		return {
			items : [],
			rows : [],
			curItem : JSON.parse(JSON.stringify(REPOMODEL))
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
	watch : {
        curid : function curid(value) {
          	var _this = this;
          	if(_this.curid){
	          	_this.curItem.id = _this.curid;
				axios.get(GETREPOPIPE, {
						headers: {
							REPOSITORY: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
					if (response.status === 200) {
						for(var obj in response.data.pipeMap){
							_this.rows.push(response.data.pipeMap[obj]);
						}
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			}
			else{
				_this.rows = [];
			}
        }
   	},
	methods : {
		addClick : function addClick(item,index){
			var _this = this;
			_this.curItem.id = _this.curid;
			var temp = JSON.parse(JSON.stringify(SUBREPOPIPEMODEL))
			temp.pipeId = item.id;
			_this.curItem.pipeMap = {0 : temp};
			axios.get(ADDREPOPIPE, {
					headers: {
						REPOSITORY: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
				if (response.status === 200) {
					_this.rows.push(response.data.pipeMap[0]);
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
			_this.curItem.pipeMap = {0 : row};
			axios.get(DELREPOPIPE, {
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
