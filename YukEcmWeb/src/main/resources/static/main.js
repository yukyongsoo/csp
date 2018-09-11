Vue.use(VueMaterial)
Vue.use(VueRouter)

httpVueLoader.httpRequest = function(url) {
    return axios.get(url)
    .then(function(res) {
        return res.data;
    })
    .catch(function(err) {
        return Promise.reject(err.status);
    });
}

httpVueLoader.register(Vue, 'view/title-component.vue');
httpVueLoader.register(Vue, 'view/speedmenu-component.vue');
httpVueLoader.register(Vue, 'view/menu-component.vue');
httpVueLoader.register(Vue, 'view/acl-component.vue');
httpVueLoader.register(Vue, 'view/cluster-component.vue');
httpVueLoader.register(Vue, 'view/lc-component.vue');
httpVueLoader.register(Vue, 'view/meta-component.vue');
httpVueLoader.register(Vue, 'view/pipe-component.vue');
httpVueLoader.register(Vue, 'view/repo-component.vue');
httpVueLoader.register(Vue, 'view/rule-component.vue');
httpVueLoader.register(Vue, 'view/str-component.vue');
httpVueLoader.register(Vue, 'view/user-component.vue');
httpVueLoader.register(Vue, 'view/group-component.vue');
httpVueLoader.register(Vue, 'view/work-component.vue');
httpVueLoader.register(Vue, 'view/help-component.vue');
httpVueLoader.register(Vue, 'view/ace-component.vue');
httpVueLoader.register(Vue, 'view/subrule/initrule-component.vue');
httpVueLoader.register(Vue, 'view/subrule/migrule-component.vue');
httpVueLoader.register(Vue, 'view/subrule/desrule-component.vue');
httpVueLoader.register(Vue, 'view/subrepo/piperepo-component.vue');
httpVueLoader.register(Vue, 'view/subrepo/strrepo-component.vue');
httpVueLoader.register(Vue, 'view/sublc/infolc-component.vue');
httpVueLoader.register(Vue, 'view/sublc/settinglc-component.vue');
httpVueLoader.register(Vue, 'view/subwork/subwork-component.vue');
httpVueLoader.register(Vue, 'view/submeta/submeta-component.vue');
httpVueLoader.register(Vue, 'view/subhelp/subhelpstr-component.vue');
httpVueLoader.register(Vue, 'view/subhelp/subhelprepo-component.vue');
httpVueLoader.register(Vue, 'view/subhelp/subhelprule-component.vue');
httpVueLoader.register(Vue, 'view/subhelp/subhelpwork-component.vue');
httpVueLoader.register(Vue, 'view/subtitle/subdoc-component.vue');
httpVueLoader.register(Vue, 'view/subtitle/doc-dialog.vue');

const router = new VueRouter({
	 routes: [
		  { path: '/title', component: Vue.component('title-component') },
		  { path: '/acl', component: Vue.component('acl-component') },
		  { path: '/cluster', component: Vue.component('cluster-component') },
		  { path: '/lc', component: Vue.component('lc-component') },
		  { path: '/meta', component: Vue.component('meta-component') },
		  { path: '/pipe', component: Vue.component('pipe-component') },
		  { path: '/repo', component: Vue.component('repo-component') },
		  { path: '/rule', component: Vue.component('rule-component') },
		  { path: '/str', component: Vue.component('str-component') },
		  { path: '/user', component: Vue.component('user-component') },
		  { path: '/group', component: Vue.component('group-component') },
		  { path: '/work', component: Vue.component('work-component') },
		  { path: '/help', component: Vue.component('help-component') }
	]
})

Vue.component('initrule-component');
Vue.component('migrule-component');
Vue.component('desrule-component');
Vue.component('piperepo-component');
Vue.component('strrepo-component');
Vue.component('infolc-component');
Vue.component('settinglc-component');
Vue.component('subwork-component');
Vue.component('submeta-component');
Vue.component('subhelpstr-component');
Vue.component('subhelprepo-component');
Vue.component('subhelprule-component');
Vue.component('subhelpwork-component');
Vue.component('subdoc-component');
Vue.component('doc-dialog');
Vue.component('ace-component');
Vue.component('vue-jstree');

var main = new Vue({
	router : router,
	data: function data() {
    	return {
    		content: 'No data For Alert',
    		ok: 'Sorry...'
        };
    },
    methods: {
    	open : function open() {
            this.$refs.alert.open();
        },
        close : function close() {
            this.$refs.alert.close();
        },
        closeMenu : function closeMenu(){
        	this.$refs.menu.close();
        },
        title : function title() {
			router.push('title');
		},
    }
}).$mount('#app')

router.push('title');

var OpenError = function OpenError(message){
	if(typeof message.response == "object"){
		main.content = message.response.data;	
	}
	else
		main.content = message.stack;
	main.open();
}

/*Vue.config.errorHandler = function (err, vm, info) {
	console.log(err);
}*/

Vue.config.warnHandler = function (err, vm, info) {
	console.log(err);
}
