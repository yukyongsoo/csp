<template>
    <div>
    	<md-input-container>
    		<label>LimitTime</label>
    		<md-input required type="number" v-model="curSubItem.limitTime"></md-input>
  		</md-input-container>
    
    	<md-input-container>
    		<label>Target StorageId</label>
    		<md-input v-model="curSubItem.strId"></md-input>
  		</md-input-container>
  		
  		<md-layout md-align="center">
    		<md-button class="md-raised md-primary" @click="setClick()">Set Rule</md-button>
    	</md-layout>
    </div>
</template>

<script>
//DELRULEMODEL = {"id":"","strId":"","limitTime":0};
module.exports = {
	props: ['curitem'],
	data: function data() {
		return {
			curSubItem: JSON.parse(JSON.stringify(DELRULEMODEL))
		};
	},
	watch : {
        curitem : function curitem(value) {
          	if(value.id && value.ruleType == 'DESRULE'){
          		this.curSubItem.id = value.id;
          		this.curSubItem.strId = value.desRule.strId;
          		this.curSubItem.limitTime = value.desRule.limitTime;
          	}
          	else{
          		this.curSubItem.id = "";
          		this.curSubItem.strId = "";
          		this.curSubItem.limitTime = 0;
          	}
        }
   	},
   	methods: {
		setClick: function setClick(){
			this.$emit('setdes', this.curSubItem);
		}
	}
}
</script>