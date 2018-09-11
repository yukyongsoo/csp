<template>
	<md-dialog md-open-from="#custom" md-close-to="#custom" ref="dialog">
		<md-dialog-title>Create New Document</md-dialog-title>
  		<md-dialog-content>
  			<md-input-container>
		    	<label>DocumentName</label>
		    	<md-input v-model='curItem.name'></md-input>
		  	</md-input-container>
  			
  			<md-input-container>
		    	<label>Working Group</label>
		    	<md-input v-model='curItem.workId'></md-input>
		  	</md-input-container>
  			
  			<md-input-container>
		    	<label>Acl Id</label>
		    	<md-input v-model='curItem.aclId'></md-input>
		  	</md-input-container>
		  	
		  	 <input id="file" type="file" class="md-primary" @change="onFileChange"/>
  		</md-dialog-content>

		<md-dialog-actions>
			<md-button class="md-primary" @click="close">Cancel</md-button>
			<md-button class="md-primary" @click="addDocument">Ok</md-button>
		</md-dialog-actions>
	</md-dialog>
</template>

<script>
module.exports = {
	props: ['curid'],
	data: function data() {
		return {
			curItem: JSON.parse(JSON.stringify(DOCMODEL)),
			curId : '',
			fileObject : {}
		};
	},
	watch : {
        curid : function curid(value) {
          	if(value){
				this.curId = value;				
			}
			else{
				this.curId = '';
			}	
        }
    },
	methods: {
		open : function open(){
			this.curItem = JSON.parse(JSON.stringify(DOCMODEL));
			this.$refs['dialog'].open();  
		},
		close : function close() {
			this.$refs['dialog'].close();
		},
		onFileChange : function onFileChange(e) {
			var files = e.target.files || e.dataTransfer.files;
			if (!files.length)
				return;
			fileObject = files;
		},
		addDocument : function addDocument(){
			var _this = this;
			var formData = new FormData();
			formData.append("file", fileObject[0]);
			_this.curItem.folderId = _this.curId;
			axios.post(ADDDOC,formData, {
				headers: {
					"Content-Type": "multipart/form-data",
					DOC: JSON.stringify(_this.curItem)
				}
			}).then(function (response) {
				if (response.status === 200) {
					_this.curItem.id = response.data;
					_this.$emit('add-newdoc', _this.curItem);
				} else {
					OpenError(response.data);
				}
			}).catch(function (error) {
				OpenError(error);
			}).finally(function() {
				_this.close();
			});
		}
	}
}
</script>
