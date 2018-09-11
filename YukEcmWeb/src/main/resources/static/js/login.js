/* this js need default permit from spring security */ 

Vue.use(VueMaterial)

new Vue({
	methods: {
		getPath : function getPath(event) {
			return window.location.search
		},
		login : function login(){
			console.log("login");
		}
	}
}).$mount('#login')