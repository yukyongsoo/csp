<template>
	<div>
	    <md-layout :md-gutter="16">
	    	<md-layout md-column> 
		    	<div class="subTitle">
		    		<span class="md-title">Group</span>
		    	</div>
			    <md-whiteframe md-elevation="2" class="component" >
					<v-jstree :data="asyncData" :async="loadData" @item-click="itemClick" draggable @item-drop-before = "itemDropBefore" class="md-scrollbar"> 
	  					<template slot-scope="_">
	    					<div>
	    						<i :class="_.vm.themeIconClasses" role="presentation" v-if="!_.model.loading"></i>
	    							{{_.model.text}}
	    						<button style="border: 0px;  padding:0px; background-color: transparent; cursor: pointer;" @click="customAddClick(_.vm, _.model, $event)"><i class="fa fa-plus"></i></button>
	    						<button style="border: 0px;  padding:0px; background-color: transparent; cursor: pointer;" @click="customRemoveClick(_.vm, _.model, $event)"><i class="fa fa-remove"></i></button>
	    						<button style="border: 0px;  padding:0px; background-color: transparent; cursor: pointer;" @click="customRenameClick(_.vm, _.model, $event)"><i class="fa fa-edit"></i></button>
	    					</div>
	  					</template>
	  				</v-jstree>	
				</md-whiteframe>
			</md-layout> 
			
			<md-layout md-column>
				<div class="subTitle">
	    			<span class="md-title">Contained User List</span>
	    		</div>
	    		<md-whiteframe md-elevation="2">
		    		<md-list>
						<md-list-item v-for="(item,index) in curGroupedItem" :key="index" @click="unGroupClick(item,index)">
			    			<md-icon>account_circle</md-icon>
			    				<span>{{item.name}}({{item.id}})</span>
			    		</md-list-item>    	
					</md-list>
				 </md-whiteframe>
			</md-layout>
				
	    		
			<md-layout md-column>
				<div class="subTitle">
	    			<span class="md-title">User List</span>
	    		</div>		
	    		<md-whiteframe md-elevation="2">
		    		<md-list>
						<md-list-item v-for="(item,index) in curUnGroupedItem" :key="index" @click="groupClick(item,index)">
			    			<md-icon>account_circle</md-icon>
			    				<span>{{item.name}}({{item.id}})</span>
			    		</md-list-item>    	
					</md-list>
				</md-whiteframe>
			</md-layout>
			
			<md-dialog-prompt
	  			:md-title="prompt.title"
	  			:md-ok-text="prompt.ok"
	  			:md-cancel-text="prompt.cancel"
	  			:md-input-maxlength="prompt.maxlength"
	  			:md-input-placeholder="prompt.placeholder"
	  			@close="onClose"
	  			v-model="prompt.value"
	  			ref="dialog">
			</md-dialog-prompt>
	    </md-layout>
    </div>
</template>

<script>
module.exports = {
		data: function data() {
			return {
				curItem : JSON.parse(JSON.stringify(GROUPMODEL)),
				curSelect : {},
				curGroupId : "",
				curGroupedItem : [],
				curUnGroupedItem : [],
				prompt: {
			      title: 'GROUP NAME INPUT DIALOG',
			      ok: 'Done',
			      cancel: 'Cancel',
			      placeholder: 'Type Group name',
			      maxlength: 999,
			      value: '',
			      excuteType : ''
    			},
				asyncData: [],
				loadData: function loadData(oriNode, resolve) {
	            	var id = oriNode.data.id ? oriNode.data.id : 0;
	            	var child= [];
	            	if(id === 0){
	            		axios.get(GETGROUPLIST, null).then(function (response) {
							if (response.status === 200) {
								for(var i =0; i < response.data.length; i++){
									child.push({
										text : response.data[i].name,
										value : response.data[i]
									});									
								}
								resolve(child);	     
							} else {
								OpenError(response.data);
							}
						}).catch(function (error) {
							OpenError(error);
						});	      		            	
	            	}
	            	else{
	            		axios.get(GETGROUPCHILDLIST, {
							headers: {
								GROUP: JSON.stringify(oriNode.data.value)
							}
						}).then(function (response) {
							if (response.status === 200) {
								for(var i =0; i < response.data.length; i++){
									child.push({
										text : response.data[i].name,
										value : response.data[i]
									});									
								}
								resolve(child);	     
							} else {
								OpenError(response.data);
							}
						}).catch(function (error) {
							OpenError(error);
						});	            	
	            	}	    
				}
			};
		},
		created: function created() {
			var _this = this;
			axios.get(GETUSER, null).then(function (response) {
				if (response.status === 200) {
					for(var i =0; i < response.data.length; i++){
						_this.curUnGroupedItem.push(response.data[i]);
					}
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			});
		},
		methods: {		
			itemClick: function itemClick(node) {
				var _this = this;
				var userItem = JSON.parse(JSON.stringify(USERMODEL));
				this.curGroupId = node.model.value.id;
				userItem.parentid = this.curGroupId;
				axios.get(GETGROUPUSER, {
					headers: {
						USER: JSON.stringify(userItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						for (var i = 0; i < response.data.length; i++) {
							_this.curGroupedItem.push(response.data[i]);
						}
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
		    },
			unGroupClick : function unGroupClick(item,index){
				var _this = this;
				item.parentid = '';
				axios.get(UPDUSERPARENT, {
					headers: {
						USER: JSON.stringify(item)
					}
				}).then(function (response) {
					if (response.status === 200) {						
						_this.curGroupedItem.splice(index, 1);
					} 
					else{
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			},		
			groupClick : function groupClick(item,index){
				var _this = this;
				item.parentid = this.curGroupId;
				axios.get(UPDUSERPARENT, {
					headers: {
						USER: JSON.stringify(item)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.curGroupedItem.push(item);
					} 
					else{
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			},
	        customAddClick: function customAddClick(node ,item, e) {
	        	e.stopPropagation();
	        	this.prompt.excuteType = 'ADD';
	        	this.curSelect = item;
	        	this.$refs['dialog'].open();  
	        },
	        customRemoveClick: function customRemoveClick(node ,item, e) {
	            e.stopPropagation();
				axios.get(DELGROUP, {
					headers: {
						GROUP: JSON.stringify(item.value)
					}
				}).then(function (response) {
					if (response.status === 200) {
						var index = node.parentItem.indexOf(item);
	            		node.parentItem.splice(index, 1);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
	        },
	        itemDropBefore (node, item, draggedItem , e) {
				if (item.value.id !== draggedItem.value.parentId) {
					draggedItem.value.parentId = item.value.id;
					axios.get(UPDGROUP, {
						headers: {
							GROUP: JSON.stringify(draggedItem.value)
						}
					}).then(function (response) {
						if (response.status !== 200) {
							OpenError(response.data);
						} 
					}).catch(function (error) {
						OpenError(error);
					});
	            }
        	},
	        customRenameClick : function customRenameClick(node ,item, e) {
	        	this.curSelect = item;
	        	this.prompt.value = item.value.name;
	        	this.prompt.excuteType = 'UPD';
	        	this.$refs['dialog'].open();
	        },
    		onClose(type) {
      			if(type !== 'ok'){
      				this.curItem = JSON.parse(JSON.stringify(GROUPMODEL));
      				return;
      			}
      			var _this = this;
      			if(this.prompt.excuteType === 'ADD'){
	      			this.curItem.name = this.prompt.value;
	      			this.curItem.parentId = this.curSelect.value.id;
					axios.get(ADDGROUP, {
						headers: {
							GROUP: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
						if (response.status === 200) {
							_this.curItem.id = response.data;
		            		_this.curSelect.addChild({
		                   		text: _this.curItem.name,
		                    	value: JSON.parse(JSON.stringify(_this.curItem))
		             		});					
						} else {
							OpenError(response.data);
						}
					}).catch(function (error) {
						OpenError(error);
					});
				}
				else if(this.prompt.excuteType === 'UPD'){
					this.curItem = this.curSelect.value;
					this.curItem.name = this.prompt.value;
		        	axios.get(UPDGROUP, {
						headers: {
							GROUP: JSON.stringify(_this.curItem)
						}
					}).then(function (response) {
						if (response.status === 200) {
							_this.curSelect.value.name = _this.prompt.value;
		        			_this.curSelect.text = _this.prompt.value;
						} 
						else{
							OpenError(response.data);
						}
					}).catch(function (error) {
						OpenError(error);
					});
				}
    		}
		}
}
</script>
