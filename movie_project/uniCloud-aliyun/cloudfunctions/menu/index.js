'use strict';
const db = uniCloud.database();
exports.main = async (event, context) => {
	//event为客户端上传的参数
	const menus = await db.collection("Menus").get();
	//返回数据给客户端
	return menus
};
