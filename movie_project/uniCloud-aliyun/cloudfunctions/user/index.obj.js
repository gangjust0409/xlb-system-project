// 云对象教程: https://uniapp.dcloud.net.cn/uniCloud/cloud-obj
// jsdoc语法提示教程：https://ask.dcloud.net.cn/docs/#//ask.dcloud.net.cn/article/129
const db = uniCloud.database();
module.exports = {
	_before: function() { // 通用预处理器

	},
	/**
	 * method1方法描述
	 * @param {string} param1 参数1描述
	 * @returns {object} 返回值描述
	 */
	/* 
	method1(param1) {
		// 参数校验，如无参数则不需要
		if (!param1) {
			return {
				errCode: 'PARAM_IS_NULL',
				errMsg: '参数不能为空'
			}
		}
		// 业务逻辑
		
		// 返回结果
		return {
			param1 //请根据实际需要返回值
		}
	}
	*/
   //获取手机号
   async getPhoneNumber(options){
		//得到openId和 access_token
	   const {
	   	openid,
	   	access_token
	   } = options.authResult;
	   const res = await uniCloud.getPhoneNumber({
	   	appid: 'wx9fa0e1013392b835',
	   	provider: 'univerify',
	   	apiKey: '9ebe15686090f99ab8eb30137a113015', // 在开发者中心开通服务并获取apiKey
	   	apiSecret: '63e33052f834e7852ca4fe62055ecfaa', // 在开发者中心开通服务并获取apiSecret
	   	access_token,
	   	openid
	   })
	   if(res.statusCode === 200 && res.errMsg === "request:ok" && res.data.success){
		   return res.data.phoneNumber;
	   }
	   return null;
   },
   //查询
   async getUserByPhone(phone){
	   return await db.collection("Uuser").where({"phone":phone }).get();
   },
	//登录
	async doLogin(options) {
		//查询手机号是否存在过
		const phoneNumber = await this.getPhoneNumber(options);
		const user = await this.getUserByPhone(phoneNumber);
		let res;
		//手机号没有注册过
		if(!user){
			res = await db.collection("Uuser").add({
				"userName": "GC_" + phoneNumber,
				"nickName": "未设置",
				"password" : null,
				"email": null,
				"registerDate": new Date().getTime(),
				"otherInfo": options.otherInfo,
				"pic": null
			});
		}
		if (res._id) {
			//记录token
			const token = 'GC_TOKEN_' + new Date().getTime();
			db.collection("LoginLogs").add({
				userId: res._id,
				token,
				createdDate: new Date().getTime()
			});
			return {
				code: 0,
				message: "登录成功！",
				data: {
					token
				}
			}
		}
	},
	//根据token返回用户信息
	async getUserInfoByToken(token){
		return await db.collection("LoginLogs").where({token}).get();
	},
	
}