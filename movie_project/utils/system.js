//当前用户的系统信息
const systemInfo = uni.getSystemInfoSync();

export function init() {
	console.log(systemInfo);
}

/**
 * 获取当前手机屏幕
 */
export function getScreenHeight(){
	return systemInfo.screenHeight;
}

/**
 * 手机状态栏高度
 */
export function getStatusBarHeight(){
	return systemInfo.statusBarHeight;
}
