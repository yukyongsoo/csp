<template>
    <div>
    	<div class="subTitle">
    		<span class="md-title">User</span>
    	</div>
    	<md-layout> 
		    <md-whiteframe md-elevation="2" class="component" >
		    	<md-table @select="listClick">
	  				<md-table-header>
	    				<md-table-row>
	    					 <md-table-head>Id</md-table-head>
	     					 <md-table-head>Name</md-table-head>
	     					 <md-table-head>Delete</md-table-head>
	    				</md-table-row>
	 				</md-table-header>
	
					<md-table-body >
					    <md-table-row v-for="(row, index) in items" :key="index" :md-item="row" md-auto-select md-selection>
					      <md-table-cell>{{row.id}}</md-table-cell>
					      <md-table-cell>{{row.name}}</md-table-cell>
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
					<div class="margin20">
						<md-input-container>
							<label>User Id</label>
							<md-input v-model="curItem.id"></md-input>
						</md-input-container>
					 
						<md-input-container>
							<label>Name</label>
							<md-input required v-model="curItem.name"></md-input>
						</md-input-container>
				
						<md-input-container>
							<label>Password</label>
							<md-input type="password" required v-model="curItem.password"></md-input>
						</md-input-container>
						
						<md-input-container>
							<label>GroupId</label>
							<md-input required v-model="curItem.parentid"></md-input>
						</md-input-container>
						
						<md-input-container>
							<label for="type">UserManageType</label>
		   					<md-select name="type" id="type" v-model="curItem.UserManageType">
		     					<md-option value="ADMIN">ADMIN</md-option>
		      					<md-option value="SYSTEM">SYSTEM</md-option>
		      					<md-option value="PERMISSION">PERMISSION</md-option>
		      					<md-option value="DOCADMIN">DOCADMIN</md-option>
		      					<md-option value="PUBLIC">PUBLIC</md-option>
		    				</md-select>
						</md-input-container>
					 </div>
				</md-whiteframe>
				
				<md-layout md-align="center">
					<md-button class="md-raised md-primary" @click="addClick()">Add</md-button>
					<md-button class="md-raised md-primary" @click="updClick()">Update</md-button>
					<md-button class="md-raised md-primary" @click="cleanClick()">Clean</md-button>
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
				curItem : JSON.parse(JSON.stringify(USERMODEL))
			};
		},
		created: function created() {
			var _this = this;
			axios.get(GETUSER, null).then(function (response) {
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
        			this.curItem = JSON.parse(JSON.stringify(USERMODEL));
			},
			addClick: function addClick() {
				var _this = this;
				axios.get(ADDUSER, {
					headers: {
						USER: JSON.stringify(_this.curItem)
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
			updClick: function updClick() {
				axios.get(UPDUSER, {
					headers: {
						USER: JSON.stringify(this.curItem)
					}
				}).then(function (response) {
					if (response.status !== 200) {
						OpenError(response.data);
					} 
				}).catch(function (error) {
					OpenError(error);
				});
			},
			deleteClick: function deleteClick(item,index) {
				var _this = this;
				axios.get(DELUSER, {
					headers: {
						USER: JSON.stringify(item)
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
				this.curItem = JSON.parse(JSON.stringify(USERMODEL))
			}
		}
}
</script>
