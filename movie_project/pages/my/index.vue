<template>
	<view class="xlb-page">
		<view class="content">
			<view class="content-header" :class="{'unlogin-pic': !token}">
				<!-- <view class="setting-box">
					<uni-icons type="gear" color="#fff" size="30"></uni-icons>
				</view> -->
				<view class="user-pic" v-if="!token">
					<image src="@/static/images/my-icons/unlogin-pic.png"></image>
				</view>
				<view class="login-title">请登录</view>
				<view class="user-nickname" v-if="token">
					<text>Aronds</text>
					<uni-icons type="compose" size="20" color="#fff"></uni-icons>
				</view>
				<view class="interactive-box" v-if="token">
					<view class="interactive-item">
						<text>关注</text>
						<text>100</text>
					</view>
					<view class="interactive-item">
						<text>粉丝</text>
						<text>100</text>
					</view>
				</view>
				<view class="back-1"></view>
				<view class="back-2"></view>
			</view>
			<view class="login-box" v-if="!token">
				<u-button text="手机号一键登录" type="primary" @click="login"></u-button>
				<view class="description">
					<u-checkbox-group v-model="isCheck" @change="yiyuedu">
						<u-checkbox shape="circle" name="isCheck" label="我已经阅读"></u-checkbox>
					</u-checkbox-group>
					<view class="yuedu" @click="yiyuedu"> <u-link href="https://uviewui.com/" text="《隐私政策》"></u-link>
					</view>
				</view>
			</view>
			<view class="content-body" v-if="token">
				<view class="box card">
					<view class="card-body my-setting__btns">
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/brand.png" mode=""></image>
							<text>添加分类</text>
						</view>
						<view @click="toPage('/subpages/uploadMovie/uploadMovie')" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/famlity.png"></image>
							<text>影视上传</text>
						</view>
						<view @click="toPage('/subpages/uploadMovieCollection/uploadMovieCollection')"
							class="my-setting__btns__item">
							<image src="@/static/images/my-icons/movie-collection.png" mode=""></image>
							<text>剧集上传</text>
						</view>
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/auth.png"></image>
							<text>申请管理</text>
						</view>
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/kefu.png" mode=""></image>
							<text>联系客服</text>
						</view>
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/like.png"></image>
							<text>我喜欢的</text>
						</view>
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/notify.png" mode=""></image>
							<text>我的通知</text>
						</view>
						<view @click="toPage()" class="my-setting__btns__item">
							<image src="@/static/images/my-icons/tousu.png"></image>
							<text>投诉建议</text>
						</view>
					</view>
				</view>
				<!-- 另一个栏 -->
				<view class="card other-info">
					<view class="card-body other-info-list">
						<view class="other-info-item">
							<text>检查最新</text>
							<uni-icons type="forward" size="30" color="#c4c6c9"></uni-icons>
						</view>
						<view class="other-info-item">
							<text>关于我们</text>
							<uni-icons type="forward" size="30" color="#c4c6c9"></uni-icons>
						</view>
					</view>
				</view>
				<!-- 退出登录按钮 -->
				<view class="loginout-btn">
					退出登录
				</view>
			</view>
		</view>
		<!-- tabbar -->
		<xlb-tabbar :value="1"></xlb-tabbar>
	</view>
</template>

<script>
	import univerifyStyle from '@/utils/univerifyStyle.js'
	export default {
		data() {
			return {
				token: null,
				isCheck: [],
			};
		},
		methods: {
			//跳转到页面
			toPage(url) {
				if (!url) {
					uni.showToast({
						icon: "error",
						title: "暂时没有该资源，请看看其他的吧！"
					})
					return;
				}
				uni.navigateTo({
					url
				})
			},
			yiyuedu(e) {
				console.log(this.isCheck);
				this.isCheck = e;
			},
			//登录
			async login() {
				let that = this;
				if (this.isCheck.length <= 0) {
					uni.showModal({
						content: "请先勾选已阅读",
						success() {
							console.log(this.isCheck);
							this.isCheck = ['isCheck'];
						},
						fail() {
							this.isCheck.length = 0;
						}
					})
					return;
				}
				const userObj = uniCloud.importObject("user");


				uni.preLogin({
					provider: 'univerify',
					success() { //预登录成功
						uni.login({
							provider: 'univerify',
							univerifyStyle: { // 自定义登录框样式
								//参考`univerifyStyle 数据结构`
								...univerifyStyle
							},
							async success(res) { // 登录成功
								uni.showModal({
									title: "打印",
									content: res
								})
								 // {openid:'登录授权唯一标识',access_token:'接口返回的 token'}
								if (res.errMsg === "login:ok") {
									const addressInfo = uni.getStorageSync("addressInfo");
									if (addressInfo) {
										addressInfo = JSON.parse(addressInfo);
									}
									const res = await userObj.doLogin({
										authResult: res.authResult,
										otherInfo: {
											...addressInfo
										}
									})
									if (res.code === 0) {
										uni.setStorageSync("token", res.data.token);
										that.token = res.data.token;
										uni.showToast({
											icon: "success",
											title: res.message
										})

									}
								}
							},
							fail(error) { // 登录失败
								uni.showModal({
									title: `状态码：${error.errCode}`,
									content: `原因：${error.errMsg}`
								})
							}
						})
					},
					fail(error) { // 预登录失败
						uni.showModal({
							title: `状态码：${error.errCode}`,
							content: `原因：${error.errMsg}`
						})
					}
				})
			},
		},
		//判断是否登录
		onShow() {
			const token = uni.getStorageSync("token");
			if (token) {
				this.token = token;
			}
		},
	}
</script>

<style lang="scss">
	.content-header {
		height: 400rpx;
		background-color: #3C9CFF;
		position: relative;

		&.unlogin-pic {
			padding-top: 150rpx;
		}

		.setting-box {
			text-align: right;
			padding-right: 20rpx;
		}

		.user-pic {
			width: 160rpx;
			height: 155rpx;
			border-radius: 50%;
			background-color: #fff;
			padding: 5rpx;
			margin: 0 auto;

			image {
				width: 100%;
				height: 100%;
				border-radius: 50%;
			}

		}
		.login-title{
			font-size: 32rpx;
			text-align: center;
			color: #fff;
			margin-top: 20rpx;
		}

		.user-nickname {
			text-align: center;
			font-size: 32rpx;
			margin-top: 10rpx;
			color: #fff;

			text {
				margin-right: 10rpx;
			}
		}

		.interactive-box {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 20rpx;
			color: #fff;
			font-size: 28rpx;

			.interactive-item {
				display: flex;
				align-items: center;

				&:last-of-type {
					margin-left: 20rpx;
				}

				text {
					&:first-of-type {
						width: 80rpx;
						display: inline-block;
					}
				}
			}
		}

		.back-1 {
			width: 100%;
			height: 50rpx;
			background-color: #3C9CFF;
			border-radius: 0 0 50% 50%;
			position: absolute;
			bottom: -20rpx;
			right: 0;
			left: 0;
		}

		.back-2 {
			background: linear-gradient(180deg, #3C9CFF, #fff);
			@extend .back-1;
			bottom: -67rpx;
		}
	}

	.login-box {
		margin: 120rpx auto;
		width: 600rpx;

		.description {
			display: flex;
			margin: 10rpx 0;

			.u-radio-group {
				flex: 0 !important;
			}

			.yuedu {
				display: flex;
				align-items: center;
			}
		}
	}

	.content-body {
		position: relative;
		top: -70rpx;
		left: 0;
		right: 0;
		padding: 0 20rpx;

		.box {
			&.card {
				display: flex;
				background-color: #fff;
				border-radius: 8px;
				z-index: 888;

				.card-body {
					&.my-setting__btns {
						display: flex;
						align-items: center;
						justify-content: space-between;
						flex-wrap: wrap;

						.my-setting__btns__item {
							width: 140rpx;
							display: flex;
							flex-direction: column;
							text-align: center;
							margin-bottom: 20rpx;
							font-size: 32rpx;

							image {
								width: 80rpx;
								height: 80rpx;
								display: block;
								margin: 10rpx auto;
							}
						}
					}
				}
			}

		}

		.other-info {
			margin-top: 20rpx;

			.card-body {
				padding: 0rpx;

				&.other-info-list {
					.other-info-item {
						display: flex;
						align-items: center;
						justify-content: space-between;
						border-bottom: 1px solid $u-info-disabled;
						padding: 20rpx;
						font-size: 32rpx;

						&:last-of-type {
							border-bottom: 0;
						}
					}
				}
			}
		}

		.loginout-btn {
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			color: #fff;
			background-color: $u-primary;
			font-size: 32rpx;
			margin: 10rpx auto 0 auto;
			border-radius: 5px;

			&:active {
				background-color: $u-primary-dark;
			}
		}
	}
</style>