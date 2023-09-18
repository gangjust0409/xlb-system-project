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
						<uni-icons custom-prefix="iconfont"  type="icon-message" c
							color="#fff" size="30"></uni-icons>
					</view>
				</view>
			</u-sticky>
			<!-- 天气预报等信息 -->
			<view class="weather-access-box card">
				<view class="card-body">
					<view class="weather">
						<image :src="userAddressWeatherInfo.weatherPic"></image>
						<view class="city-weather">
							<view class="city">{{userAddressWeatherInfo.province}} - {{userAddressWeatherInfo.city}}</view>
							<view class="info">
								<text>{{userAddressWeatherInfo.weather}} </text>
								<text>{{userAddressWeatherInfo.temperature_float}}°C {{userAddressWeatherInfo.winddirection}}风{{userAddressWeatherInfo.windpower}}级 </text>
							</view>
						</view>
					</view>
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
						<view class="movie-item" v-for="movie in movieList" :key="movie._id"
						@click="toDetail(movie)">
							<!-- <view class="header"></view> todo 使用瀑布流-->
							<view class="movie-pic">
								<image v-if="movie.pic" :src="movie.pic" :alt="movie.movieName"
									:title="movie.movieName"></image>
								<image v-else src="https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/img/load-img-404.png" alt="404" title="404"></image>
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
			<u-loadmore 
			        :status="status" 
					loadingIcon="spinner"
			        loading-text="正在加载中,喝杯奶茶吧..." 
			        loadmore-text="轻轻上拉" 
			        nomore-text="没有更多了,跟客服反馈一下吧..." 
			    />
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
				//地址和天气信息
				userAddressWeatherInfo: {
					
				},
				status: 'loadmore',
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
					if(this.total === res.affectedDocs) {
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
					}),...this.movieList];
				} catch (e) {
					console.log(e);
				}
			},
			//跳转详情页
			toDetail(movie){
				uni.navigateTo({
					url:`/subpages/detail/detail?id=${movie._id}`
				})
			},
			//天气api
			async getWeather(){
				let that = this;
				const weatherObj = uniCloud.importObject("weather");
				//地址信息
				uni.request({
					url:`https://restapi.amap.com/v3/ip?key=eb385c677cef048eaa967df3b9002cc7`,
					success(res) {
						if (res.statusCode === 200) {
						uni.request({
							url: `https://restapi.amap.com/v3/weather/weatherInfo?key=eb385c677cef048eaa967df3b9002cc7&city=${res.data.adcode}&extensions=base&output=json`,
							method: "get",
							async success(ret) {
								if (ret.statusCode === 200) {
									//地址信息
									that.userAddressWeatherInfo.province = res.data.province;
									that.userAddressWeatherInfo.city = res.data.city;
									that.userAddressWeatherInfo.adcode = res.data.adcode;
									that.userAddressWeatherInfo.rectangle = res.data.rectangle;
									//天气
									if (ret.data.lives.length > 0) {
										let _=ret.data.lives[0];
										that.userAddressWeatherInfo.weather = _.weather;
										that.userAddressWeatherInfo.temperature_float = _.temperature_float;
										that.userAddressWeatherInfo.winddirection = _.winddirection;
										that.userAddressWeatherInfo.windpower = _.windpower;
										const res = await weatherObj.getWeatherByName({weatherName: _.weather, type: that.weatherToName(_.weather)})
										if (res.affectedDocs > 0) {
											that.userAddressWeatherInfo.weatherPic = res.data[0].img;
										}
									}
								}
							}
							
						})
						}
					}
				})
			},
			//转天气名称
			weatherToName(weatherName){
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
			this.queryParams.pageSize+=10;
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
					text{
						&:last-of-type{
							margin-left: 3rpx;
						}
					}
				}
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