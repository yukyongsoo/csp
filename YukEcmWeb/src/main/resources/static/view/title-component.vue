<template>
    <div>
    	<md-layout md-row>
	    	<md-layout md-column>
		    	<div class="subTitle">
	    			<span class="md-title">Folder List</span>
	    		</div>
	    		<md-whiteframe md-elevation="2" class="component" >
					<v-jstree :data="asyncData" :async="loadData" draggable @item-drop-before = "itemDropBefore" @item-click="itemClick" class="md-scrollbar"> 
	  					<template slot-scope="_">
	    					<div>
	    						<i :class="_.vm.themeIconClasses" role="presentation" v-if="!_.model.loading"></i>
	    							{{_.model.text}}
	    						<button style="border: 0px; padding:0px; background-color: transparent; cursor: pointer;" @click="customAddClick(_.vm, _.model, $event)"><i class="fa fa-plus"></i></button>
	    						<button style="border: 0px;  padding:0px; background-color: transparent; cursor: pointer;" @click="customRemoveClick(_.vm, _.model, $event)"><i class="fa fa-remove"></i></button>
	    						<button style="border: 0px;  padding:0px; background-color: transparent; cursor: pointer;" @click="customRenameClick(_.vm, _.model, $event)"><i class="fa fa-edit"></i></button>
	    					</div>
	  					</template>
	  				</v-jstree>	
				</md-whiteframe>
			</md-layout>
			
			<md-layout md-flex="80">
	    		<subdoc-component :curid="curFolderId"></subdoc-component>
			</md-layout>
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
	</div>
</template>

<script>
module.exports = {
		data: function data() {
			return {
				curFolderId : '',
				curItem : JSON.parse(JSON.stringify(FOLDERMODEL)),
				curSelect : {},
				prompt: {
			      title: 'Folder NAME INPUT DIALOG',
			      ok: 'Done',
			      cancel: 'Cancel',
			      placeholder: 'Type Folder name',
			      maxlength: 999,
			      value: '',
			      excuteType : ''
    			},
				asyncData: [],
				loadData: function loadData(oriNode, resolve) {
	            	var id = oriNode.data.id ? oriNode.data.id : 0;
	            	var child= [];
	            	if(id === 0){
	            		axios.get(GETFOLDERLIST, null).then(function (response) {
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
	            		axios.get(GETFOLDERCHILDLIST, {
							headers: {
								FOLDER: JSON.stringify(oriNode.data.value)
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
		methods: {		
			itemClick: function itemClick(node) {
				this.curFolderId = node.model.value.id;
		    },
	        customAddClick: function customAddClick(node ,item, e) {
	        	e.stopPropagation();
	        	this.prompt.excuteType = 'ADD';
	        	this.curSelect = item;
	        	this.$refs['dialog'].open();  
	        },
	        customRemoveClick: function customRemoveClick(node ,item, e) {
	            e.stopPropagation();
				axios.get(DELFOLDER, {
					headers: {
						FOLDER: JSON.stringify(item.value)
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
	        itemDropBefore : function itemDropBefore(node, item, draggedItem , e) {
				if (item.value.id !== draggedItem.value.parentId) {
					draggedItem.value.parentId = item.value.id;
					axios.get(UPDFOLDER, {
						headers: {
							FOLDER: JSON.stringify(draggedItem.value)
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
    		onClose : function onClose(type) {
      			if(type !== 'ok'){
      				this.curItem = JSON.parse(JSON.stringify(FOLDERMODEL));
      				return;
      			}
      			var _this = this;
      			if(this.prompt.excuteType === 'ADD'){
	      			this.curItem.name = this.prompt.value;
	      			this.curItem.parentId = this.curSelect.value.id;
					axios.get(ADDFOLDER, {
						headers: {
							FOLDER: JSON.stringify(_this.curItem)
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
		        	axios.get(UPDFOLDER, {
						headers: {
							FOLDER: JSON.stringify(_this.curItem)
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