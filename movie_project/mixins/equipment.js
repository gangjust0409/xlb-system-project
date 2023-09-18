export default{
	data(){
		return{
			pageHeight: 0,
		}
	},
	methods: {
		//获取当前屏幕的高都
		getEquipmentHeight(){
			const systemInfo = uni.getSystemInfoSync();
			this.pageHeight = systemInfo.screenHeight;
		}
	},
	mounted(){
		this.getEquipmentHeight();
	},
}