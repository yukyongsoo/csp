<template>
    <div>
   		<div class="subTitle">
    		<span class="md-title">Cluster</span>
    	</div>
    	<md-layout>
		    	<md-whiteframe md-elevation="2" class="component">
		    		<md-table @select="listClick">
		  				<md-table-header>
		    				<md-table-row>
		    					 <md-table-head>Apnum</md-table-head>
		     					 <md-table-head>Address</md-table-head>
		     					 <md-table-head>Delete</md-table-head>
		    				</md-table-row>
		 				</md-table-header>

						<md-table-body >
						    <md-table-row v-for="(row, index) in items" :key="index" :md-item="row" md-auto-select md-selection>
						      <md-table-cell>{{row.apNum}}</md-table-cell>
						      <md-table-cell>{{row.address}}</md-table-cell>
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
					<div>
						<md-input-container>
							<label>Ap Number</label>
							<md-input v-model="curItem.apNum"></md-input>
						</md-input-container>
					 
						<md-input-container>
							<label>Address</label>
							<md-input required  v-model="curItem.address"></md-input>
						</md-input-container>
				
						<md-switch class="md-primary"  v-model="curItem.state">State</md-switch>
					 </div>
				</md-whiteframe>
				
				<md-layout md-align="center">
					<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
					<md-button class="md-raised md-primary"	@click="cleanClick()">Clean</md-button>
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
				curItem : JSON.parse(JSON.stringify(CLUSTERMODEL))
			};
		},
		created: function created() {
			var _this = this;
			axios.get(GETCLUSTER, null).then(function (response) {
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
			listClick: function listClick(data) {
				if(data.length > 0)
        		this.curItem = data[0];
        	else
        		this.curItem = JSON.parse(JSON.stringify(CLUSTERMODEL));
			},
			addClick: function addClick() {
				var _this = this;
				axios.get(ADDCLUSTER, {
					headers: {
						CLUSTER: JSON.stringify(_this.curItem)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.items.push(_this.curItem);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			},
			deleteClick: function deleteClick(item,index) {
				var _this = this;
				axios.get(DELCLUSTER, {
					headers: {
						CLUSTER: JSON.stringify(item)
					}
				}).then(function (response) {
					if (response.status === 200) {
						_this.items.splice(index, 1);
					} else {
						OpenError(response.data);
					}
				}).catch(function (error) {
					OpenError(error);
				});
			},
			cleanClick: function cleanClick() {
				this.curItem = JSON.parse(JSON.stringify(CLUSTERMODEL))
			}
		}
}
</script>