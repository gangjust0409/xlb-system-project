import date from '@/utils/date.js'
const uids = []
export default {


	/**
	 * 生成规则：当天日期+0001
	 */
	getUid() {
		let id;
		const datestamp = new Date().getTime();
		if (uids.length > 0) {
			const uid = uids[0];
			const date = String(uid).substring(0, uid.length - 4)
			const newId = String(uid).substring(uid.length - 4, uid.length)
			if (date.formatDate(datestamp, "yyyyMMdd") == date) {
				id = date + String(parseInt(newId) + 1).padStart(2, "00")
			} else {
				id = date.formatDate(datestamp, "yyyyMMdd") + '001';
			}
		} else {
			id = date.formatDate(datestamp, "yyyyMMdd") + '001';
		}
		uids.unshift(id);
		console.log(uids);
		return id;
	},

}