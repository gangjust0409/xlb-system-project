<script>
	export default {
		onLoad() {
			uni.reLaunch({
				url:'/pages/index/index'
			})
			console.log("跳转至首页成功！");
		},
	}
</script>
<template>
	<view></view>
</template>