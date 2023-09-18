<template>
	<view class="xlb-page">
		<view class="row card">
			<view class="card-body movie-detail-box">
				<view class="movie-pic">
					<image v-if="movieDetail.pic" :src="movieDetail.pic" @click="viewMoviePic(movieDetail.pic)"></image>
					<image v-else
						src="https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/img/load-img-404.png"
						@click="viewMoviePic()"></image>
				</view>
				<view class="movie-info">
					<view class="movie-name">{{movieDetail.movieName}}</view>
					<view class="movie-director">
						<text class="label">导演：</text>{{movieDetail.director}}
					</view>
					<view class="movie-rating">
						<text class="label">评分：
						</text> <!-- <u-rate :count="5" v-model="movieDetail.rating" allowHalf
							disabled></u-rate> -->
						<text>0.1分</text>
					</view>
					<view class="releaseDate">
						上映日期：{{movieDetail.releaseDate?movieDetail.releaseDate:'未知'}}
					</view>
					<view class="play-btn">
						<u-button text="立即播放" type="primary" @click="play()"
							:disabled="mcList &&mcList.length===0"></u-button>
					</view>
				</view>
			</view>
		</view>
		<view class="row card movie-collection">
			<view class="card-header header-title">
				剧集：
			</view>
			<view class="card-body movie-collection__list" v-if="mcList && mcList.length > 0">
				<view class="movie-collection__list__item-inner" v-for="mc in mcList" :key="mc._id" @click="play(mc)">
					{{mc.index}}
				</view>
			</view>
			<view class="card-body" v-else style="color: #909399;padding-left: 40rpx;font-size: 28rpx;">
				暂无片源！
			</view>
		</view>
		<!-- 分类 -->
		<view class="row card genre-box">
			<view class="card-header header-title">分类：</view>
			<view class="card-body genre-list">
				<view class="u-tag-item" v-for="genre in genreList" :key="genre._id">
					<u-tag :text="genre.genreName" size="mini" shape="circle"></u-tag>
				</view>
			</view>
		</view>
		<!-- 详情 -->
		<view class="row card movei-descrition">
			<view class="card-header header-title">详情：</view>
			<view class="card-body">
				<u-read-more ref="uReadMore" :show-height="200" :toggle="true" openText="收起" closeText="展开详情">
					<u-parse :content="movieDetail.description" @load="load"></u-parse>
				</u-read-more>
			</view>
		</view>
	</view>
	</view>
</template>

<script>
	import date from '@/utils/date.js'
	export default {
		data() {
			return {
				//影视详情信息
				movieDetail: {},
				//剧集
				mcList: [],
				//分类
				genreList: [],
			};
		},
		methods: {
			//获取当前的影视详情信息
			async getMovieDetail(id) {
				//请求 ajax
				const movieObj = uniCloud.importObject("movie");
				const res = await movieObj.movieInfoById({
					id
				});
				if (res.affectedDocs === 1 && res.data.length === 1) {
					this.movieDetail = res.data[0];
					this.movieDetail.releaseDate = date.formatDate(this.movieDetail.releaseDate, "yyyy-MM-dd");
					this.generFunMovieCollection(this.movieDetail._id);
					uni.setNavigationBarTitle({
						title: this.movieDetail.movieName
					});
					console.log(this.movieDetail);
				}
			},
			//分类和剧集
			async generFunMovieCollection(movieId) {
				const gener = uniCloud.importObject("genre");
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				//分类
				const genreRes = await gener.queryList();
				if (genreRes.affectedDocs > 0) {
					this.genreList = this.movieDetail.genreIds.map(g => {
						return genreRes.data.find(x => x._id == g);
					})
					console.log(this.genreList);
				}
				//剧集
				const mcRes = await movieCollectionObj.queryMovieCollection({
					movieId
				});
				if (mcRes.affectedDocs > 0) {
					this.mcList = this.listToCollection(mcRes.data);
				}
			},
			load() {
				this.$refs.uReadMore.init();
			},
			//冒泡排序
			listToCollection(list){
				let _list = list;
				for(let i = 0; i < _list.length - 1; i++) {
					for(let j = 0; j < _list.length - i - 1; j++) {
						let current = parseInt(_list[j].index);
						let next = parseInt(_list[j + 1].index);
						if (current > next){
							let tmp = current;
							_list[j].index = next;
							 _list[j + 1 ].index = tmp;
						}
					}
				}
				return _list;
			},
			//查看当前影视封面
			viewMoviePic(picSrc) {
				let src = "";
				if (picSrc) {
					src = picSrc;
				} else {
					src = "https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/img/load-img-404.png";
				}
				uni.previewImage({
					urls: [src]
				});
			},
			//播放视频
			play(movie) {
				console.log(movie);
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				//如果是通过点击按钮的，从第一集开始
				if (!movie) {
					//默认视频第一个集剧
					movieCollectionObj.queryMovieCollection({
						movieId: this.movieDetail._id
					}).then(res => {
						if (res.affectedDocs > 0) {
							uni.navigateTo({
								url: `/subpages/play/play?id=${this.listToCollection(res.data)[0]._id}&movieId=${this.movieDetail._id}`
							})
						}
					})
				} else {
					uni.navigateTo({
						url: `/subpages/play/play?id=${movie._id}&movieId=${this.movieDetail._id}`
					})
				}
			},
		},
		onLoad(options) {
			this.getMovieDetail(options.id);
		},
		onShow() {

		}
	}
</script>

<style lang="scss">
	.movie-detail-box {
		display: flex;
		justify-content: space-between;

		.movie-pic {
			width: 300rpx;
			height: 350rpx;
			border: 1px solid #efefef;
			padding: 10rpx;
			border-radius: 8px;

			image {
				width: 100%;
				height: 100%;
				border-radius: 8px;
			}
		}

		.movie-info {
			flex: 1;
			margin-left: 20rpx;
			line-height: 2;

			.movie-name {
				font-size: 34rpx;
				font-weight: 600;
			}

			.movie-director {}

			.movie-rating {
				display: flex;
			}

			.play-btn {
				width: 150rpx;
				margin-top: 25rpx;
			}
		}
	}

	.movie-collection {

		&__list {
			display: flex;
			flex-wrap: wrap;

			&__item-inner {
				padding: 5rpx 20rpx;
				margin-right: 20rpx;
				margin-bottom: 10rpx;
				border-radius: 5px;
				border: 1px solid #666;
			}
		}
	}

	.movie-description {
		height: 300rpx;
	}

	.genre-box {

		.genre-list {
			padding-left: 20rpx;
			display: flex;
			flex-wrap: wrap;

			.u-tag-item {
				margin-right: 10rpx;
				margin-bottom: 10rpx;
			}
		}
	}
</style>