export default {
	getCityWeather(weather){
		switch(weather) {
			case "晴":
				return '@/static/images/weather/during_sunny.png';
			case "阴":
				return '@/static/images/weather/during_cloundDay.png';
			case "多云":
				return '@/static/images/weather/during_partlyCloundly.png';
		}
	},
}