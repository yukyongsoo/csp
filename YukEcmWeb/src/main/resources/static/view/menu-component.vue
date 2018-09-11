<template>
<div>
	<md-toolbar>
		<div class="md-toolbar-container">
			<h3 class="md-title">Menu</h3>
		</div>
	</md-toolbar>
	<md-list>
		<md-list-item  @click="user()">
			<md-icon md-src="assets/icon-user.png"></md-icon><span>User</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="group()">
			<md-icon md-src="assets/icon-group.png"></md-icon><span>Group</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="acl()">
			<md-icon md-src="assets/icon-acl.png"></md-icon><span>Acl/Ace</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="cluster()">
			<md-icon md-src="assets/icon-cluster.png"></md-icon><span>Cluster</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="repo()">
			<md-icon md-src="assets/icon-repo.png"></md-icon><span>Repository</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="str()">
			<md-icon md-src="assets/icon-str.png"></md-icon><span>Storage</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="pipe()">
			<md-icon md-src="assets/icon-pipe.png"></md-icon><span>Pipe</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="rule()">
			<md-icon md-src="assets/icon-rule.png"></md-icon><span>Rule</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="work()">
			<md-icon md-src="assets/icon-work.png"></md-icon><span>WorkingGroup</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="lc()">
			<md-icon md-src="assets/icon-lc.png"></md-icon><span>LifeCycle</span>
		</md-list-item>
	</md-list>
	<md-list>
		<md-list-item  @click="meta()">
			<md-icon md-src="assets/icon-meta.png"></md-icon><span>MetaData</span>
		</md-list-item>
	</md-list>
</div>
</template>

<script>
module.exports = {
	methods: {
		user : function user() {
			router.push('user');
			this.$emit('close-menu', null);
		},
		group : function group() {
			router.push('group');
			this.$emit('close-menu', null);
		},
		acl : function acl() {
			router.push('acl');
			this.$emit('close-menu', null);
		},
		cluster : function cluster() {
			router.push('cluster');
			this.$emit('close-menu', null);
		},
		repo : function repo() {
			router.push('repo');
			this.$emit('close-menu', null);
		},
		str : function str() {
			router.push('str');
			this.$emit('close-menu', null);
		},
		pipe : function pipe() {
			router.push('pipe');
			this.$emit('close-menu', null);
		},
		rule : function rule() {
			router.push('rule');
			this.$emit('close-menu', null);
		},
		work : function work() {
			router.push('work');
			this.$emit('close-menu', null);
		},
		lc : function lc() {
			router.push('lc');
			this.$emit('close-menu', null);
		},
		meta : function meta() {
			router.push('meta');
			this.$emit('close-menu', null);
		}
	}
}
</script>
