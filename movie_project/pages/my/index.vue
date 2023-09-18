<template>
	<view class="xlb-page">
		<view class="content">
			<view class="content-header" :class="{'unlogin-pic': !token}">
				<!-- <view class="setting-box">
					<uni-icons type="gear" color="#fff" size="30"></uni-icons>
				</view> -->
				<view class="user-pic">
					<image src="@/static/images/my-icons/unlogin-pic.png"></image>
				</view>
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
							<text>APP使用教程</text>
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
	export default {
		data() {
			return {
				token: "null",
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
			login() {
				if (this.isCheck.length <= 0) {
					uni.showModal({
						content: "请先勾选已阅读",
						success() {
							this.isCheck.push('isCheck');
							console.log(this.isCheck);
						},
						fail() {
							this.isCheck.length = 0;
						}
					})
					return;
				}
				uni.preLogin({
					provider: "univerify",
					success(ret) {
						console.log(res);

						uni.login({
							provider: "univerify",
							univerifyStyle: {
								"fullScreen": false, // 是否全屏显示，默认值： false
								"backgroundColor": "#ffffff", // 授权页面背景颜色，默认值：#ffffff
								"backgroundImage": "", // 全屏显示的背景图片，默认值："" （仅支持本地图片，只有全屏显示时支持）  
								"icon": {
									"path": "static/xxx.png", // 自定义显示在授权框中的logo，仅支持本地图片 默认显示App logo
									"width": "60px", //图标宽度 默认值：60px
									"height": "60px" //图标高度 默认值：60px
								},
								"closeIcon": {
									"path": "static/xxx.png" // 自定义关闭按钮，仅支持本地图片。 HBuilderX3.3.7+版本支持
								},
								"phoneNum": {
									"color": "#202020" // 手机号文字颜色 默认值：#202020  
								},
								"slogan": {
									"color": "#BBBBBB" //  slogan 字体颜色 默认值：#BBBBBB  
								},
								"authButton": {
									"normalColor": "#3479f5", // 授权按钮正常状态背景颜色 默认值：#3479f5  
									"highlightColor": "#2861c5", // 授权按钮按下状态背景颜色 默认值：#2861c5（仅ios支持）  
									"disabledColor": "#73aaf5", // 授权按钮不可点击时背景颜色 默认值：#73aaf5（仅ios支持）  
									"textColor": "#ffffff", // 授权按钮文字颜色 默认值：#ffffff  
									"title": "本机号码一键登录", // 授权按钮文案 默认值：“本机号码一键登录”  
									"borderRadius": "24px" // 授权按钮圆角 默认值："24px" （按钮高度的一半）
								},
								"otherLoginButton": {
									"visible": true, // 是否显示其他登录按钮，默认值：true  
									"normalColor": "", // 其他登录按钮正常状态背景颜色 默认值：透明 
									"highlightColor": "", // 其他登录按钮按下状态背景颜色 默认值：透明 
									"textColor": "#656565", // 其他登录按钮文字颜色 默认值：#656565  
									"title": "其他登录方式", // 其他登录方式按钮文字 默认值：“其他登录方式”  
									"borderColor": "", //边框颜色 默认值：透明（仅iOS支持）  
									"borderRadius": "0px" // 其他登录按钮圆角 默认值："24px" （按钮高度的一半）
								},
								"privacyTerms": {
									"defaultCheckBoxState": true, // 条款勾选框初始状态 默认值： true
									"isCenterHint": false, //未勾选服务条款时点击登录按钮的提示是否居中显示 默认值: false (3.7.13+ 版本支持)
									"uncheckedImage": "", // 可选 条款勾选框未选中状态图片（仅支持本地图片 建议尺寸 24x24px）(3.2.0+ 版本支持)   
									"checkedImage": "", // 可选 条款勾选框选中状态图片（仅支持本地图片 建议尺寸24x24px）(3.2.0+ 版本支持)   
									"checkBoxSize": 12, // 可选 条款勾选框大小
									"textColor": "#BBBBBB", // 文字颜色 默认值：#BBBBBB  
									"termsColor": "#5496E3", //  协议文字颜色 默认值： #5496E3  
									"prefix": "我已阅读并同意", // 条款前的文案 默认值：“我已阅读并同意”  
									"suffix": "并使用本机号码登录", // 条款后的文案 默认值：“并使用本机号码登录”  
									"privacyItems": [ // 自定义协议条款，最大支持2个，需要同时设置url和title. 否则不生效  
										{
											"url": "https://", // 点击跳转的协议详情页面  
											"title": "用户服务协议" // 协议名称  
										}
									]
								},
								"buttons": { // 自定义页面下方按钮仅全屏模式生效（3.1.14+ 版本支持）
									"iconWidth": "45px", // 图标宽度（高度等比例缩放） 默认值：45px
									"list": [{
											"provider": "apple",
											"iconPath": "/static/apple.png" // 图标路径仅支持本地图片
										},
										{
											"provider": "weixin",
											"iconPath": "/static/wechat.png" // 图标路径仅支持本地图片
										}
									]
								}
							},
							success(res) {
								console.log(res);
							}
						})
					}
				})
			},
		}
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