<template>
	<view class="xlb-page" :style="{'height':pageHeight+'px'}">
		<view class="content card">
			<view class="card-body">
				<!-- 注意，如果需要兼容微信小程序，最好通过setRules方法设置rules规则 -->
				<u--form labelPosition="left" :model="movieForm" ref="movieForm" labelWidth="80">
					<u-form-item label="影视名称" prop="movieName">
						<u--input v-model="movieForm.movieName" placeholder="输入影视名称" border="none" inputAlign="right"
							suffixIcon="arrow-right" :suffixIconStyle="{color: '#A2A4A6'}"></u--input>
					</u-form-item>
					<u-form-item label="导演" prop="director">
						<u--input v-model="movieForm.director" placeholder="输入导演" border="none" inputAlign="right"
							suffixIcon="arrow-right" :suffixIconStyle="{color: '#A2A4A6'}"></u--input>
					</u-form-item>
					<u-form-item label="分类" prop="checkedGenerAllList">
						<view class="down-data"
							:class="{'gener-list':(checkedGenerAllList &&checkedGenerAllList.length>0)}"
							@click="showGenreChange(true)">
							<view class="no-active-text" v-if="checkedGenerAllList &&checkedGenerAllList.length<=0">
								<text>请选择分类</text>
								<u-icon name="arrow-right" size="20" color="#A2A4A6"></u-icon>
							</view>
							<view class="active-box" v-if="checkedGenerAllList &&checkedGenerAllList.length>0">
								<view class="active-item" v-for="item in checkedGenerAllList" :key="item.genreName">
									<u-tag :text="item.genreName" type="primary" size="mini" closable :show="item.close"
										@close="generClose(item)"></u-tag>
								</view>
							</view>
						</view>
						<u-popup :show="showGenre" mode="bottom" @close="showGenreChange(false)"
							@open="showGenreChange(true)">
							<view class="gener-box">
								<view class="gener-header">
									<text class="cancel" @click="showGenreChange(false)">取消</text>
									<text class="confrim" @click="genreConfirm">确定</text>
								</view>
								<view class="gener-body">
									<u-checkbox-group v-model="checkedGenerList" placement="column" labelSize="22"
										size="18" shape="circle">
										<u-checkbox :customStyle="{marginBottom: '8px'}"
											v-for="(item, index) in genreList" :key="index" :label="item.genreName"
											:name="item.genreName">
										</u-checkbox>
									</u-checkbox-group>
									<u-checkbox-group v-model="checkedRemainGenerList" placement="column" labelSize="22"
										size="18" shape="circle">
										<u-checkbox :customStyle="{marginBottom: '8px'}"
											v-for="(item, index) in genreRemainList" :key="index"
											:label="item.genreName" :name="item.genreName">
										</u-checkbox>
									</u-checkbox-group>
								</view>
							</view>
						</u-popup>
					</u-form-item>
					<u-form-item label="上映日期">
						<view class="down-data" @click="showDatetime(true)">
							<text>{{movieForm.releaseDateText}}</text>
							<u-icon name="arrow-right" size="20" color="#A2A4A6"></u-icon>
						</view> <!--40年-->
						<u-datetime-picker :show="dateTimeShow" mode="date" minDate="1000 * 1000 * 60 *24*30*12*40"
							v-model="movieForm.releaseDate" @cancel="showDatetime(false)"
							@confirm="confirmReleaseDate"></u-datetime-picker>
					</u-form-item>
					<u-form-item label="描述" prop="description">
						<u--textarea v-model="movieForm.description" placeholder="描述" count autoHeight maxlength="1000"
							border="bottom" height="120"></u--textarea>
					</u-form-item>
				</u--form>
			</view>
		</view>
		<view class="card">
			<view class="card-body">
				<u--form labelPosition="left" :model="movieForm" ref="uForm" labelWidth="80">
					<u-form-item label="影视封面" borderBottom labelPosition="top">
						<u-upload maxCount="1" :fileList="fileList" @afterRead="afterRead" @delete="deletePic">
							<view class="uload-btn" v-if="!movieForm.tempPic">
								<uni-icons type="cloud-upload-filled" color="#909399" size="60"></uni-icons>
							</view>
						</u-upload>
						<view class="upload-pic" v-if="movieForm.tempPic">
							<image :src="movieForm.tempPic"></image>
							<uni-icons class="deleteImg" @click="deleteMoviePic" type="closeempty" color="#fff"
								size="15"></uni-icons>
						</view>
					</u-form-item>
				</u--form>
			</view>
		</view>
		<view class="submit-box">
			<u-button type="primary" shape="circle" text="提交" @click="submitForm"></u-button>
			<u-button type="primary" shape="circle" plain text="返回" @click="cancel"></u-button>
		</view>
		<u-toast ref="uToast"></u-toast>
		<u-modal :show="isAddFlag" content='影视信息已添加成功,请问还继续添加影视信息吗？' showCancelButton confirmText="继续"
			@cancel="cancelAdd" @confirm="comfirmAdd"></u-modal>
	</view>
</template>

<script>
	import date from '@/utils/date.js'
	import uuid from '@/utils/UUID.js'
	import equipment from '@/mixins/equipment.js'
	export default {
		mixins: [equipment],
		data() {
			return {
				//上传影视的form
				movieForm: {
					movieName: null,
					director: null,
					genreIds: [],
					releaseDate: null,
					releaseDateText: '请选择日期',
					pic: null,
					tempPic: null,
					description: '',
				},
				dateTimeShow: false,
				showGenre: false,
				//分类列表
				genreList: [],
				//分类数据库没有处理过数据
				genreDtaList: [],
				//剩余的分类列表
				genreRemainList: [],
				//分类选中的
				checkedGenerList: [],
				//剩余分类列表选中
				checkedRemainGenerList: [],
				//封面
				fileList: [],
				//总分类列表
				checkedGenerAllList: [],
				isAddFlag: false,
			};
		},
		methods: {
			//展示选择日期
			showDatetime(dateTimeShow) {
				this.dateTimeShow = dateTimeShow
			},
			confirmReleaseDate(timestamp) {
				//格式化日期
				this.movieForm.releaseDateText = date.formatDate(timestamp.value, "yyyy-MM-dd");
				this.movieForm.releaseDate = timestamp.value;
				this.showDatetime(false);
			},
			//展示分类
			async showGenreChange(flag) {
				try {
					const genre = uniCloud.importObject("genre");
					const res = await genre.queryList();
					this.genreDtaList = res.data;
					const {
						list1,
						list2
					} = this.listToTwoList(this.genreDtaList, res.affectedDocs);
					this.genreList = list1;
					this.genreRemainList = list2;
				} catch (e) {
					uni.showToast({
						icon: "fail",
						title: "请求云对象失败！"
					})
				}

				this.showGenre = flag;
			},
			//分类确定
			genreConfirm() {
				this.checkedGenerAllList = [...this.checkedGenerList, ...this.checkedRemainGenerList].map(genreName => {
					return {
						...this.genreDtaList.find(x => x.genreName == genreName),
						close: true
					};
				});
				//处理分类id
				this.movieForm.genreIds = this.checkedGenerAllList.map(cg => cg._id);
				this.showGenre = false;
			},
			//将一个集合转成两个相同的集合，多余的
			listToTwoList(list, total) {
				const list1 = [],
					list2 = [];
				let length = Math.floor(total / 2);
				for (let i in list) {
					if (i <= length) {
						list1.push(list[i]);
					} else if (length <= total) {
						list2.push(list[i]);
					} else {

					}
				}
				return {
					list1,
					list2
				};
			},
			validateForm() {
				if (this.movieForm.movieName == null || this.movieForm.movieName == '' || this.movieForm.movieName ==
					undefined) {
					this.$refs['uToast'].show({
						message: "请输入影视名称",
						icon: "close-circle",
						type: "error"
					})
					return false;
				} else if (this.movieForm.director == null || this.movieForm.director == '' || this.movieForm.director ==
					undefined) {
					this.$refs['uToast'].show({
						message: "请输入导演",
						icon: "close-circle",
						type: "error"
					})
					return false;
				} else if (this.movieForm.genreIds == null || this.movieForm.genreIds.length <= 0) {
					this.$refs['uToast'].show({
						message: "请选择影视所属分类",
						icon: "close-circle",
						type: "error"
					})
					return false;
				} else {
					return true;
				}
			},
			async submitForm() {
				const movie = uniCloud.importObject("movie");
				let that = this;
				if (this.validateForm()) {
					//图片不为空
					if (this.movieForm.pic) {
						const imgName = this.movieForm.movieName + String(this.movieForm.pic.url).substring(this
							.movieForm
							.pic
							.url.lastIndexOf("."));
						uni.compressImage({
							src: this.movieForm.pic.url,
							quality: 60,
							async success(res) {
								const file = await uniCloud.uploadFile({
									filePath: res.tempFilePath,
									cloudPath: '/img/' + imgName,
									cloudPathAsRealPath: true,
									onUploadProgress(res) {
										console.log(res);
									}
								});

								//处理图片
								that.movieForm.pic = file.fileID;
								//最后处理分类id
								that.movieForm.genreIds = that.checkedGenerAllList.map(cg => cg._id);
								//保存
								that.movieForm.rating = 0;
								that.movieForm.uploadId = "1";
								that.movieForm.uploadDate = new Date().getTime();
								const addRes = await movie.add(that.movieForm);
								that.isAddSuccess(addRes);
							}
						})
					} else {
						//最后处理分类id
						that.movieForm.genreIds = that.checkedGenerAllList.map(cg => cg._id);
						//保存
						that.movieForm.rating = 0;
						that.movieForm.uploadId = "1";
						that.movieForm.uploadDate = new Date().getTime();
						const addRes = await movie.add(that.movieForm);
						that.isAddSuccess(addRes);
					}

				}
			},
			//判断是否添加成功
			isAddSuccess(res) {
				if (res.id) {
					this.isAddFlag = true;
				} else {
					uni.showToast({
						icon: "fail",
						"title": "添加影视信息错误！"
					})
				}
			},
			//继续添加
			comfirmAdd() {
				this.movieForm.movieName = null;
				this.movieForm.director = null;
				this.movieForm.genreIds = null;
				this.checkedGenerAllList.length = 0;
				this.movieForm.pic = null;
				this.checkedGenerList = null;
				this.checkedRemainGenerList = null;
				this.isAddFlag = false;
				this.movieForm.tempPic = null
				this.movieForm.description = null;
			},
			//取消添加
			cancelAdd(id) {
				uni.showToast({
					icon: "success",
					title: `id ${id},已添加成功！`
				});
				this.isAddFlag = false;
				uni.redirectTo({
					url: '/pages/my/index'
				})
			},
			//移除图片
			deleteMoviePic() {
				this.movieForm.tempPic = null;
				this.movieForm.pic = null;
			},
			cancel() {
				uni.navigateBack();
			},
			//删除分类
			generClose(gener) {
				if (this.checkedGenerAllList.length <= 0) {
					return;
				}
				this.checkedGenerAllList.forEach((g, i) => {
					if (g._id == gener._id) {
						//将旧的多选框默认去掉
						this.getTwoListToCurrentObj(g);
						this.checkedGenerAllList.splice(i, 1);
					}
				});
			},
			//查找两个列表是否存在当前对象
			getTwoListToCurrentObj(currentObj) {
				if (currentObj.genreName) {
					//不为空
					const generIndex = this.checkedGenerList.findIndex(genreName => genreName === currentObj.genreName);
					const generRemainIndex = this.checkedRemainGenerList.findIndex(genreName => genreName === currentObj
						.genreName);
					if (generIndex != -1) {
						this.checkedGenerList.splice(generIndex, 1)
					} else if (generRemainIndex != -1) {
						this.checkedRemainGenerList.splice(generRemainIndex, 1);
					}
				} else {
					//为空
					uni.showToast({
						icon: "fail",
						title: "当前对象的id不存在！"
					})
					return;
				}
			},
			afterRead(img) {
				this.movieForm.tempPic = img.file.url;
				this.movieForm.pic = img.file;
			},
			deletePic(a, b) {
				console.log(a, b);
			},
		}
	}
</script>

<style lang="scss">
	.picker-items{ width: 100% !important;; }
	.down-data {
		display: flex;
		align-items: center;
		justify-content: flex-end;

		text {
			margin-left: 3.5px;
			color: #A2A4A6;
		}

		.no-active-text {
			display: flex;

			text {
				margin-right: 7rpx;
			}
		}

		&.gener-list {
			display: flex;
			flex-direction: column;

			.active-box {
				display: flex;
				flex-wrap: wrap;

				.active-item {
					margin-right: 10rpx;
				}
			}
		}
	}



	.upload-pic {
		width: 300rpx;
		height: 360rpx;
		margin: 15rpx 0 0 15rpx;
		position: relative;
		overflow: hidden;

		image {
			width: 100%;
			height: 100%;
		}

		.deleteImg {
			position: absolute;
			top: -3px;
			right: -3px;
			width: 25px;
			height: 25px;
			display: flex;
			justify-content: center;
			align-items: center;
			background-color: rgba(0, 0, 0, .6);
			border-radius: 5px 0 0px 25px;
		}
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

	.gener-box {
		background-color: #fff;
		height: 750rpx;

		.gener-header {
			padding: 40rpx 60rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1px solid #666;

			.cancel {
				color: #666;
			}

			.confrim {
				color: #3c9cff;
			}
		}

		.gener-body {
			padding: 40rpx 60rpx;
			overflow: auto;
			display: flex;

			.u-checkbox-group {
				margin-right: 80rpx;
			}
		}
	}
</style>