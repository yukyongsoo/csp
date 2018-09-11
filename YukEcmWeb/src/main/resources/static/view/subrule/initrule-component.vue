<template>
    <div>
    	<md-input-container>
    		<label>Init Repository Id</label>
    		<md-input required v-model="curSubItem.repoId"></md-input>
  		</md-input-container>
  		
  		 <md-switch class="md-primary" v-model="curSubItem.required">Required</md-switch>
  		 
  		 <md-layout md-align="center">
    		<md-button class="md-raised md-primary" @click="setClick()">Set Rule</md-button>
    	</md-layout>
    </div>
</template>

<script>
module.exports = {
	props: ['curitem'],
	data: function data() {
		return {
			curSubItem: JSON.parse(JSON.stringify(INITRULEMODEL))
		};
	},
	watch : {
        curitem : function curitem(value) {
          	if(value.id && value.ruleType == 'INITRULE'){
          		this.curSubItem.id = value.id;
          		this.curSubItem.repoId = value.initRule.repoId;
          	}
          	else{
          		this.curSubItem.id = "";
          		this.curSubItem.repoId = "";
          	}
        }
   	},
   	methods: {
		setClick: function setClick(){
			this.$emit('setinit', this.curSubItem);
		}
	}
}
</script>
