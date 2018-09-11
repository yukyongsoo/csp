<template>
<md-speed-dial md-open="hover" md-direction="top" class="md-fab-bottom-right">
	<md-button class="md-fab" md-fab-trigger>
		<md-icon md-icon-morph md-src="assets/icon-close.png"></md-icon>
		<md-icon md-src="assets/icon-open.png"></md-icon>
	</md-button>

	<md-button @click="help()" class="md-fab md-primary md-mini md-clean">
		<md-icon md-src="assets/icon-help.png"></md-icon>
		<md-tooltip md-direction="left">Help</md-tooltip>
	</md-button>
	
	<md-button @click="reinit()" class="md-fab md-primary md-mini md-clean">
		<md-icon md-src="assets/icon-reinit.png"></md-icon>
		<md-tooltip md-direction="left">Reinit Cache</md-tooltip>
	</md-button>

	<md-button @click="logout()" class="md-fab md-primary md-mini md-clean">
		<md-icon md-src="assets/icon-logout.png"></md-icon>
		<md-tooltip md-direction="left">Logout</md-tooltip>
	</md-button>
</md-speed-dial>
</template>

<script>
module.exports = {
	methods: {
		logout : function logout() {
			axios.get('logout').then(function (response) {
			   	window.location.replace('login');
			})
			.catch(function (error) {
			   OpenError(error)
			   window.location.replace('login');
			});
		},
		help : function help() {
			router.push('help')
		},
		reinit : function reinit(){
			axios.get(REINITCACHE).then(function (response) {
			   
			})
			.catch(function (error) {
			   OpenError(error)
			});
		}
	}
}
</script>