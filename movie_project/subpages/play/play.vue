<template>
	<view class="xlb-page">
		<view class="card">
			<view class="card-body video-box">
				<view class="video-inner">
					<video id="playVideo" ref="playRef" :src="currentSrc" :title="fullscreenTitle" type="video/mp4" @fullscreenclick="fullscreen"
						@error="videoErrorCallback" @ended="nextCollection"></video>
				</view>
				<view class="setting-system">
					<view class="left">
						<view class="icon-btn">
							<uni-icons type="hand-up" size="30" color="#fff"></uni-icons>
							<text>点赞</text>
						</view>
						<view class="icon-btn heand-down">
							<uni-icons type="hand-down" size="30" color="#fff"></uni-icons>
							<text>踩一下</text>
						</view>
						<view class="icon-btn">
							<uni-icons type="heart" size="27" color="#fff"></uni-icons>
							<text>喜欢</text>
						</view>
					</view>
					<view class="right">
						<view class="icon-btn" @click="nextCollection">
							<uni-icons custom-prefix="iconfont" type="icon-anniu-xiayibu" color="#fff"
								size="27"></uni-icons>
							<text>下一集</text>
						</view>
						<view class="icon-btn" @click="playLoad">
							<uni-icons type="loop" size="27" color="#fff"></uni-icons>
							<text>刷新</text>
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="card">
			<view class="card-body setting-video-btns">

			</view>
		</view>
		<view class="card">
			<view class="commons" v-if="commons.length > 0">
				<view class="common-item">

				</view>
			</view>
			<view class="common-empty" v-else>
				<!-- <image src=""></image> -->
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				mcList: [],
				movieInfo: {},
				commons: [],
				videoContext: null,
				options: {},
				//当前播放的url
				// currentSrc: 'https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/video/21_MV1694890387817.mp4',
				currentSrc: '',
				fullscreenTitle: '',
				//当前的播放的索引
				currentIndex: 0,
			};
		},
		methods: {
			//获取当前播放源
			//获取当前影视信息
			getMovie() {
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				const movieObj = uniCloud.importObject("movie");
				//影视详情
				movieObj.movieInfoById({
					id: this.options.movieId
				}).then(movieRes => {
					if (movieRes.affectedDocs === 1) {
						this.movieInfo = movieRes.data[0];
						//剧集列表
						movieCollectionObj.queryMovieCollection({
							movieId: this.options.movieId
						}).then(mcRes => {
							if (mcRes.affectedDocs > 0) {
								this.mcList = this.listToCollection(mcRes.data);
								this.mcList.forEach(mc => {
									if (mc._id === this.options.id) {
										this.currentSrc = mc.posterURL;
										this.movieInfo.duration = mc.duration;
										this.movieInfo.index = mc.index;
										this.currentIndex = mc.index;
									}
								})
							}
							this.setTitle(this.movieInfo.index);
						})
					}
				})
			},
			//设置标题
			fullscreen(){
				this.fullscreenTitle = `正在播放 - ${this.movieInfo.movieName} 第${this.movieInfo.index}集`;
			},
			setTitle(index) {
				//设置当前标题
				uni.setNavigationBarTitle({
					title: `正在播放 - ${this.movieInfo.movieName} 第${index}集`,
				})
			},
			//刷新
			playLoad() {
				this.videoContext.play();
			},
			videoErrorCallback: function(e) {
				console.log('视频错误信息:')
				console.log(e.target.errMsg)
			},
			//下一集
			nextCollection() {
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				movieCollectionObj.queryMovieCollection({
					movieId: this.options.movieId
				}).then(res => {
					if (res.affectedDocs > 0) {
						let list = this.listToCollection(res.data);
						let _current = list.findIndex(x => x.index == this.currentIndex);
						if (_current != -1 && list[_current].index == list[list.length - 1].index) {
							return;
						}
						this.currentSrc = list[_current + 1].posterURL;
						this.currentIndex = list[_current + 1].index;
						this.options.id = list[_current + 1]._id;
						this.setTitle(list[_current + 1].index);
						this.videoContext.play();
					}
				})
			},
			//冒泡排序
			listToCollection(list) {
				let _list = list;
				for (let i = 0; i < _list.length - 1; i++) {
					for (let j = 0; j < _list.length - i - 1; j++) {
						let current = parseInt(_list[j].index);
						let next = parseInt(_list[j + 1].index);
						if (current > next) {
							let tmp = current;
							_list[j].index = next;
							_list[j + 1].index = tmp;
						}
					}
				}
				return _list;
			},
		},
		onLoad(options) {
			// this.getMovie(options);
			this.options = options;
		},
		onReady: function(res) {
			this.videoContext = uni.createVideoContext('playVideo', this);
			this.getMovie();
		}
	}
</script>

<style lang="scss">
	.video-box {
		background: linear-gradient(180deg, #fff, #3c9cff);
		padding: 5rpx;


		.video-inner {
			width: 715rpx;

			video {
				width: 100%;
			}
		}

		.setting-system {
			display: flex;
			align-items: center;
			justify-content: space-between;
			color: #fff;

			&>view {
				display: flex;
			}
		}
	}
</style>