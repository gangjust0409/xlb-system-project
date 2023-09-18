<template>
	<view class="xlb-page">
		<view class="table-list">
			<u-swipe-action>
				<u-swipe-action-item :options="settingOptions" v-for="movie in moveList" :key="movie._id" show>
					<view class="card" @longpress="showMovieModel(movie)">
						<view class="swipe-action u-border-top u-border-bottom">
							<view class="swipe-action__content">
								<view class="table-item card-body">
									<image v-if="movie.pic" :src="movie.pic" class="pic"
										@click="viewMoviePic(movie.pic)">
									</image>
									<image v-else class="pic"
										src="https://mp-2f404463-4a21-4e83-abed-ede3b9b92e79.cdn.bspapp.com/img/load-img-404.png"
										@click="viewMoviePic()"></image>
									<view class="movie-info">
										<text>{{movie._id}}</text>
										<text>{{movie.movieName}}</text>
										<text>导演：{{movie.director}}</text>
										<text>评分：{{movie.rating}}</text>
									</view>
								</view>
							</view>
						</view>
					</view>
				</u-swipe-action-item>
			</u-swipe-action>
		</view>
		<u-popup :show="showMovie" mode="center" @close="movieClose" @open="movieOpen" ref="movieRef">
			<!-- 列表 -->
			<view class="movie-box" v-if="movieCollectionsVisiblle">
				<view class="movie-setting-box">
					<u-button text="新增剧集" type="primary" icon="plus-square-fill"
						@click="movieCollectionsVisiblle = false"></u-button>
					<view class="close" @click="movieClose">
						<u-icon name="close-circle" color="#fff" size="35"></u-icon>
					</view>
				</view>
				<view class="movie-collection-list">
					<view class="movie-collection-list__item" v-for="mc in moveiCollectionList" :key="mc.id">
						<view class="left">
							<view class="movie-name">{{mc.movieId}}</view>
							<view class="file-name">{{mc.fileName}}</view>
							<view class="collectioin">{{mc.collectionIndex}}</view>
						</view>
					</view>
				</view>
			</view>

			<!-- 添加 -->
			<view class="movie-box add-box" v-else>
				<u--form labelPosition="left" :model="movieForm" ref="movieForm" labelWidth="80">
					<u-form-item label="上传第几集">
						<u--input v-model="movieForm.index" placeholder="输入集数(默认在最后一次添加的影视后)" border="none"
							inputAlign="right" suffixIcon="arrow-right"
							:suffixIconStyle="{color: '#A2A4A6'}"></u--input>
					</u-form-item>
					<u-form-item label="影视频" borderBottom labelPosition="top">
						<u-upload maxCount="1" :fileList="movieForm.fileList" accept="video"
							@afterRead="afterReadVideo">
							<view class="uload-btn" v-if="!movieForm.thumb">
								<uni-icons type="cloud-upload-filled" color="#909399" size="60"></uni-icons>
							</view>
						</u-upload>
						<view class="upload-videos" v-if="movieForm.thumb">
							<view class="upload-video">
								<image :src="movieForm.thumb"></image>
								<view class="movie-info"></view>

								<uni-icons class="deleteImg" type="closeempty" color="#fff" size="15"></uni-icons>
							</view>
						</view>
						<view class="progress" v-if="uploadMovieViedeoLoadingNumber">
							<u-line-progress :percentage="uploadMovieViedeoLoadingNumber" activeColor="#5ac725"
								height="20"></u-line-progress>
						</view>
					</u-form-item>
				</u--form>
				<view class="submit-box">
					<u-button type="primary" shape="circle" text="提交" @click="submitForm"></u-button>
					<u-button type="primary" shape="circle" plain text="返回"
						@click="movieCollectionsVisiblle = true"></u-button>
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				moveList: null,
				settingOptions: [{
						text: "添加剧集"
					},
					{
						text: "删除"
					},
				],
				queryParams: {
					pageNum: 1,
					pageSize: 10
				},
				showMovie: false,
				movieForm: {
					index: null,
					thumb: null,
					posterURL: null,
				},
				//是否添加
				movieCollectionsVisiblle: true,
				//加载loading
				uploadMovieViedeoLoadingNumber: null,
				//剧集列表
				moveiCollectionList: [],
			}
		},
		methods: {
			//加载所有的影视信息
			async loadMovieList() {
				const movie = uniCloud.importObject("movie");
				const res = await movie.queryList({
					pageNum: (this.queryParams.pageNum - 1) * this.queryParams.pageSize,
					pageSize: this.queryParams.pageSize
				});
				if (res.affectedDocs > 0) {
					this.moveList = res.data;
				}
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
			movieClose() {
				this.showMovie = false;
			},
			movieOpen() {
				this.showMovie = true;
			},
			showMovieModel(movie) {
				this.movieForm.movieId = movie._id;
				console.log(movie, this.movieForm.movieId);
				this.movieForm.movieName = movie.movieName;
				this.queryMovieCollection(movie._id);
				this.movieOpen();
			},
			//提交文件后
			async afterReadVideo({
				file
			}) {
				this.movieForm.thumb = file.thumb;
				this.movieForm.duration = file.duration;
				this.movieForm.posterURL = file;

			},
			async submitForm() {
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				if (!this.movieForm.index) {
					// this.movieForm.index = 
				}
				const posterUrl = this.movieForm.posterURL;
				//将中文转为英文
				const fileName = new Date().getTime();
				const videoName = this.movieForm.index + "_" + "MV" + fileName + posterUrl.url.substring(
					posterUrl.url.lastIndexOf("."));
				if (posterUrl) {
					//压缩文件
					/* uni.getVideoInfo({
						src: posterUrl.url,
						success(res) {
							console.log(res);
							uni.compressVideo({
								src: posterUrl.url,
								bitrate: res.bitrate,
								fps: res.fps,
								resolution: 0.6,
								success(ret) {
									console.log(ret);
								},
								fail(err) {
									Promise.reject("压缩：" + JSON.stringify(err))
								}
							})
						},
						fail(err) {
							Promise.reject("获取视频信息：" + JSON.stringify(err))
						}
					}) */
					try {
						let that = this;
						//上传视频
						const ret = await uniCloud.uploadFile({
							filePath: posterUrl.url,
							cloudPathAsRealPath: true,
							cloudPath: `/video/${videoName}`,
							onUploadProgress(res) {
								that.uploadMovieViedeoLoadingNumber = Math.floor((res.loaded / res.total) *
									100);
							},
						});
						console.log(ret);
						if (ret.success) {
							this.movieForm.posterURL = ret.fileID;
							this.uploadMovieViedeoLoadingNumber = null;
							const res = await movieCollectionObj.addMovieCollection(this.movieForm);
							console.log(res);
							if (res.id) {
								this.movieCollectionsVisiblle = true;
								uni.showToast({
									icon: "success",
									title: "添加成功！"
								})
							}
						}
					} catch (err) {
						console.log(err);
						uni.showToast({
							icon: "exception",
							title: "异常：" + err
						})
					}
				}
			},
			//查询当前影视剧集
			async queryMovieCollection(movieId) {
				const movieCollectionObj = uniCloud.importObject("movieCollection");
				const res = await movieCollectionObj.queryMovieCollection({movieId});
				console.log(res);
				if (res.affectedDocs >= 1) {
					this.moveiCollectionList = res.data.map(mc => {
						const fileName = mc.posterURL.substring(mc.posterURL.lastIndexOf("/") + 1);
						return {
							id: mc._id,
							fileName,
							collectionIndex: mc.index,
							duration: mc.duration,
							movieId: mc.movieId
						}
					});
					console.log(this.moveiCollectionList);
				}
			},
		},
		onLoad() {
			this.loadMovieList();
		}

	}
</script>

<style lang="scss">
	.table-list {
		.card {
			.table-item {
				display: flex;

				.pic {
					width: 225rpx;
					height: 250rpx;
					margin-right: 15rpx
				}

				.movie-info {
					flex: 1;
					line-height: 2;
					@include flex(column);
				}
			}
		}
	}

	.movie-box {
		width: 680rpx;
		height: 820rpx;
		position: relative;

		.movie-setting-box {
			width: 200rpx;
			padding: 30rpx 20rpx;

			.close {
				position: absolute;
				top: -10%;
				left: 50%;
				transform: translateX(-50%);
			}
		}

		.movie-collection-list {
			margin: 20rpx 0;

			&__item {}
		}

		&.add-box {
			padding: 15rpx 25rpx;
		}
	}

	.upload-videos {
		.upload-video {
			&>image {
				width: 170rpx;
				height: 190rpx;
			}
		}

		margin: 15rpx 0 0 15rpx;
		position: relative;
		overflow: hidden;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.progress {
		padding: 15rpx 20rpx;
	}

	.submit-box {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 0 10px;
		position: sticky;
		bottom: 10px;
		left: 0;
		right: 0;

		button {
			&:last-of-type {
				margin-left: 10px;
			}
		}
	}
</style>