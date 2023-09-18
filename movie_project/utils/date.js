export default {
	//日期格式，yyyy-MM-dd
	formatDate(timestamp, format) {
		const regx = /^yyyy[-|/|\s]+MM[-|/|\s]+dd$/ig;
		const timeRegx = /^yyyy[-|/|\s]+MM[-|/|\s]+dd HH:mm:ss$/ig;
		const currentDate = new Date(timestamp);
		const year = currentDate.getFullYear();
		// string padStart方法，到第几位数之前，补0操作
		const month = String(currentDate.getMonth() + 1).padStart(2, '0');
		const day = String(currentDate.getDate()).padStart(2, "0");
		const hours = String(currentDate.getHours()).padStart(2,"0");
		const minutes = String(currentDate.getMinutes()).padStart(2,"0");
		const seconds = String(currentDate.getSeconds()).padStart(2,"0");
		//替换字符串
		//是否有时分秒
		if (regx.test(format)) {
			return format.replace("yyyy", year).replace("MM", month).replace("dd",day);
		} else if(timeRegx.test(format)) {
			return format.replace("yyyy", year).replace("MM", month).replace("dd",day).replace("hh",hours)
			.replace("HH", hours).replace("mm", minutes).replace("ss",seconds);
		} else {
			uni.showToast({
				icon:"fail",
				title:"转换日期错误，请检查！"
			})
		}
	},
	//判断当前日期是否是当天日期
	isDateIsCurrentDate(timestamp){
		const date = formatDate(timestamp, "yyyyMMdd");
		const currentDate = formatDate(new Date().getTime(), "yyyyMMdd");
		return date == currentDate;
	},
	
}

