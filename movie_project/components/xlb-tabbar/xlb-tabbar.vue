<template>
	<view class="xlb-component-page">
		<u-tabbar
			:value="currentPage"
	@change="changePage"
		>
				<u-tabbar-item text="首页" icon="home" ></u-tabbar-item>
				<!-- <u-tabbar-item text="放映厅" icon="photo" ></u-tabbar-item> -->
				<u-tabbar-item text="我的" icon="account" ></u-tabbar-item>
		</u-tabbar>
	</view>
</template>

<script>
	export default {
		name:"xlb-tabbar",
		props: {
			value: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				//列表
				tabbarList: [
					{
						id: 1,
						name: '首页',
						icon: 'home',
						page: '/pages/index/index',
					},
					// {
					// 	id: 2,
					// 	name: '放映厅',
					// 	icon: 'home',
					// 	page: '/pages/chinema/index',
					// },
					{
						id: 2,
						name: '我的',
						icon: 'home',
						page: '/pages/my/index',
					},
				],
				//当前选中
				currentPage: this.value ? this.value : 0,
			};
		},
		methods: {
			//切换页面
			changePage(index) {
				this.currentPage = index;
				this.tabbarList.forEach(tab => {
					if ((--tab.id) === index){
						uni.reLaunch({
							url: tab.page
						})
					}
				});
			},
		},
		onLoad() {
			
		},
	}
</script>

<style lang="scss">

</style>