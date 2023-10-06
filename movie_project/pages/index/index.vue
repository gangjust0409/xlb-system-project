<template>
	<view class="xlb-page">
		<view class="content">
			<!-- 头部 -->
			<u-sticky offset-top="0">
				<view class="xlb-header">
					<view class="weather">
						<uni-icons type="scan" color="#fff" size="30"></uni-icons>
					</view>
					<view class="search-input">
						<uni-icons custom-prefix="iconfont" class="search-icon" type="icon-search"
							size="16"></uni-icons>
						<text>龙门客栈</text>
					</view>
					<view class="message">
						<uni-icons custom-prefix="iconfont" type="icon-message" c color="#fff" size="30"></uni-icons>
					</view>
				</view>
			</u-sticky>
			<!-- 天气预报等信息 -->
			<view class="weather-access-box card">
				<view class="card-body">
					<view class="weather" v-if="userAddressWeatherInfo.city && userAddressWeatherInfo.weather">
						<image :src="userAddressWeatherInfo.weatherPic"></image>
						<view class="city-weather">
							<view class="city">{{userAddressWeatherInfo.city}} - {{userAddressWeatherInfo.district}}
							</view>
							<view class="info">
								<text>{{userAddressWeatherInfo.weather}} </text>
								<text>{{userAddressWeatherInfo.temperature_float}}°C {{userAddressWeatherInfo.winddirection}} {{userAddressWeatherInfo.windpower}}
								</text>
							</view>
						</view>
					</view>
					<view class="weather-no" v-else @click="getWeather">请重新获取天气信息</view>
					<view class="access">
						<view>访问量： <text>1025</text></view>
						<view>影视总量：<text>205</text></view>
					</view>
				</view>
			</view>
			<!-- 轮播图 -->
			<view class="xlb-swiper card">
				<view class="card-body">
					<u-swiper :list="swiperList" :height="150"></u-swiper>
				</view>
			</view>
			<view class="xlb-notify">
				<u-notice-bar :text="notify"></u-notice-bar>
			</view>
			<!-- 首页4个按钮 -->
			<view class="xlb-menu-box card">
				<view class="xlb-menus card-body">
					<view class="xlb-menu-item" v-for="menu in menuList" :key="menu.id">
						<image :src="menu.icon" :alt="menu.title" :title="menu.title"></image>
						<text>{{menu.title}}</text>
					</view>
				</view>
			</view>
			<!-- 作品推荐 -->
			<view class="movie-container">
				<view class="movie-box">
					<view class="movie-list">
						<view class="movie-item" v-for="movie in movieList" :key="movie._id" @click="toDetail(movie)">
							<!-- <view class="header"></view> todo 使用瀑布流-->
							<view class="movie-pic">
								<image v-if="movie.pic" :src="movie.pic" :alt="movie.movieName"
									:title="movie.movieName"></image>
								<image v-else
									src="https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/img/load-img-404.png"
									alt="404" title="404"></image>
								<view class="overy"><u-icon name="play-circle" color="#fff" size="35"></u-icon>
								</view>
							</view>
							<view class="movie-name">{{movie.movieName}}</view>
							<view class="movie-desction" v-html="movie.description"></view>
							<view class="movie-genre-box">
								<u-tag v-for="genre in movie.genreList" :key="genre._id" :text="genre.genreName"
									class="genreTag" size="mini" type="primary"></u-tag>
							</view>
							<view class="movie-rating">
								<text>好评度：{{movie.rating * 0.01 * 100}}%</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<u-loadmore :status="status" loadingIcon="spinner" loading-text="正在加载中,喝杯奶茶吧..." loadmore-text="轻轻上拉"
				nomore-text="没有更多了,跟客服反馈一下吧..." />
		</view>
		<!-- tabbar -->
		<xlb-tabbar :value="0"></xlb-tabbar>
	</view>
</template>

<script>
	import weather from '@/utils/weather.js'
	export default {
		data() {
			return {
				swiperList: [
					'https://cdn.uviewui.com/uview/swiper/swiper1.png',
					'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					'https://cdn.uviewui.com/uview/swiper/swiper3.png',
				],
				//菜单列表
				menuList: [],
				//影视列表
				movieList: [],
				queryParams: {
					pageNum: 0,
					pageSize: 10
				},
				total: 0,
				//地址信息和天气信息
				userAddressWeatherInfo: {
					province: null, //省
					city: null, //市
					country: null, //国家
					adcode: null, //城市编码 用于天气查询
					district: null, //区
					street: null, //街道
				},
				status: 'loadmore',
				notify: "2023年09月24日上传了100部影片。",
			};
		},
		methods: {
			async loadMenus() {
				const {
					result
				} = await uniCloud.callFunction({
					name: "menu"
				});
				this.menuList = result.data;
			},
			async loadMovie(callback) {
				try {
					this.loading = 'loading';
					const movie = uniCloud.importObject("movie");
					const gener = uniCloud.importObject("genre");
					const res = await movie.queryList(this.queryParams);
					const genreRes = await gener.queryList();
					//判断是否有数据
					if (this.total === res.affectedDocs) {
						this.status = 'nomore';
						return;
					}
					this.total = res.affectedDocs;
					this.movieList = [...res.data.map(movie => {
						let genreList = [];
						if (genreRes.data && genreRes.data.length > 0) {
							genreList = movie.genreIds.map(id => genreRes.data.find(gl => gl._id == id));
						}
						return {
							...movie,
							genreList
						};
					}), ...this.movieList];
				} catch (e) {
					console.log(e);
				}
			},
			//跳转详情页
			toDetail(movie) {
				uni.navigateTo({
					url: `/subpages/detail/detail?id=${movie._id}`
				})
			},
			//天气api
			async getWeather() {
				let that = this;
				uni.getLocation({
					geocode: true,
					success(locationRes) {
						that.getAddressByLongitudeAndLatitude(locationRes.longitude, locationRes.latitude);
					}
				})

			},
			//百度地图api根据经纬度获取地址
			getAddressByLongitudeAndLatitude(longitude, latitude) {
				let that = this;
				const weatherObj = uniCloud.importObject("weather");
				uni.request({
					url: `https://api.map.baidu.com/reverse_geocoding/v3/?ak=oRr5Y256wstYuzSmPp3fTlgMu8fYAOD6&output=json&coordtype=wgs84ll&location=${latitude},${longitude}`,
					success(res) {
						if (res.statusCode === 200 && res.data.status === 0) {
							const data = res.data.result;
							that.userAddressWeatherInfo.province = data.addressComponent.province;
							that.userAddressWeatherInfo.city = data.addressComponent.city;
							that.userAddressWeatherInfo.country = data.addressComponent.country;
							that.userAddressWeatherInfo.adcode = data.addressComponent.adcode;
							that.userAddressWeatherInfo.district = data.addressComponent.district;
							that.userAddressWeatherInfo.street = data.addressComponent.street;
							that.userAddressWeatherInfo.town = data.addressComponent.town;
							that.userAddressWeatherInfo.town_code = data.addressComponent.town_code;
							uni.setStorageSync("addressInfo", JSON.stringify(that.userAddressWeatherInfo));
							//天气情况
							uni.request({
								url: `https://api.map.baidu.com/weather/v1/?district_id=${that.userAddressWeatherInfo.adcode}&data_type=all&ak=oRr5Y256wstYuzSmPp3fTlgMu8fYAOD6`,
								method: "get",
								async success(ret) {
									if (ret.statusCode === 200 && ret.data.status === 0) {
										console.log(ret);
										//天气
										let _ = ret.data.result.now;
										console.log(_);
										that.userAddressWeatherInfo.weather = _.text;
										that.userAddressWeatherInfo.temperature_float = _
											.temp;
										that.userAddressWeatherInfo.winddirection = _.wind_dir; //风的方向
										that.userAddressWeatherInfo.windpower = _.wind_class; //风力
										const res = await weatherObj.getWeatherByName({
											weatherName: _.text,
											type: that.weatherToName(_.text)
										})
										if (res.affectedDocs > 0) {
											that.userAddressWeatherInfo.weatherPic = res.data[0].img;
										}
									}
								}

							})
						}
					}
				})
			},
			//转天气名称
			weatherToName(weatherName) {
				const hours = new Date().getHours();
				if (hours >= 8 && hours <= 19) {
					return '日';
				} else {
					return '夜';
				}
			},
		},
		mounted() {
			this.loadMenus();
			this.loadMovie();
			this.getWeather();
		},
		//下拉刷新
		onPullDownRefresh() {
			this.getWeather();
			this.queryParams.pageNum = 0;
			//刷新
			this.loadMovie(() => {
				uni.stopPullDownRefresh();
			});

		},
		//触底刷新
		onReachBottom() {
			this.queryParams.pageSize += 10;
			this.loadMovie();
		},
	}
</script>

<style lang="scss">
	.xlb-header {
		display: flex;
		align-items: center;
		height: 80rpx;
		background-color: #3c9cff;

		view {
			flex: 1;
			text-align: center;

			&.search-input {
				flex: 6;
				background-color: #fff;
				border-radius: 10rpx;
				margin: 10rpx 0 20rpx 0;
				height: 60rpx;
				display: flex;
				justify-content: flex-start;
				align-items: center;
				color: #606266;
				font-size: 28rpx;

				.search-icon {
					margin: 0 10rpx 0 20rpx;

				}
			}

		}
	}

	.weather-access-box {
		.card-body {
			display: flex;

			.weather {
				flex: 2;
				display: flex;
				padding-right: 10rpx;

				image {
					width: 90rpx;
					height: 90rpx;
					margin-right: 10rpx;
				}

				.city-weather {
					font-size: 26rpx;
					line-height: 2;
					margin-left: 5px;

					text {
						&:last-of-type {
							margin-left: 3rpx;
						}
					}
				}
			}
			.weather-no{
				font-size: 28rpx;
				text-align: center;
				line-height: 2;
			}

			.access {
				flex: 1;
				font-size: 26rpx;
				line-height: 2;
				padding-right: 20rpx;
				text-align: right;
			}
		}
	}

	/* .xlb-swiper {
		height: 650rpx;
	} */
	.xlb-notify{
		margin-bottom: 20rpx;
	}
	.xlb-menu-box {
		.xlb-menus {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.xlb-menu-item {
				display: flex;
				flex-direction: column;
				text-align: center;

				&>image {
					width: 80rpx;
					height: 80rpx;
					margin: 0 auto;
				}

				text {
					font-size: 28rpx;
				}
			}
		}
	}

	.movie-container {
		.movie-box {
			.movie-list {
				display: flex;
				justify-content: space-between;
				flex-wrap: wrap;
				padding: 0 10px;

				.movie-item {
					width: 42vw;
					padding: 15rpx;
					background-color: #fff;
					border-radius: 5px;
					margin-bottom: 20rpx;

					.movie-pic {
						width: 300rpx;
						height: 360rpx;
						position: relative;

						image {
							width: 100%;
							height: 100%;
						}

						.overy {
							display: none;
						}

						&:hover {
							.overy {
								background-color: rgba(0, 0, 0, .5);
								position: absolute;
								top: 0;
								right: 0;
								left: 0;
								bottom: 0;
								display: flex;
								justify-content: center;
								align-items: center;
								transition: all 1s;
							}
						}
					}

					.movie-name {
						font-size: 28rpx;
						margin: 10rpx 0 20rpx 0;
						font-weight: 600;
					}

					.movie-desction {
						font-size: 24rpx;
						height: 11%;
						line-height: 1.5;
						color: #606266;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 2;
						overflow: hidden;
						text-overflow: ellipsis;
					}

					.movie-genre-box {
						margin: 10rpx 0;
						display: flex;
						height: 23px;
						flex-wrap: wrap;
						white-space: nowrap;
						text-overflow: ellipsis;
						overflow: hidden;

						&>view {
							margin-right: 10rpx;
						}
					}

					.movie-rating {
						text-align: right;
						font-size: 24rpx;
						margin: 10rpx 0;
						color: #606266;
					}
				}
			}
		}
	}
</style>