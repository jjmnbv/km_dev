//格式化日期
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]):(("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

// 获取今天已经过的毫秒数
function getTodayMS(paramDate) {
	var totalMillSecodes = paramDate.getHours() * 60 * 60 * 1000;
	totalMillSecodes += paramDate.getMinutes() * 60 * 1000;
	totalMillSecodes += paramDate.getSeconds() * 1000;
	totalMillSecodes += paramDate.getMilliseconds();
	return totalMillSecodes;
}

// 获取本周已经过的毫秒数
function getTsWeekMS(paramDate) {
	return (paramDate.getDay() - 1) * 24 * 60 * 60 * 1000
			+ getTodayMS(paramDate);
}

// 获取本月已经过的毫秒数
function getTsMonthMS(paramDate) {
	return (paramDate.getDate() - 1) * 24 * 60 * 60 * 1000
			+ getTodayMS(paramDate);
}

//获取本季度毫秒数(getMonth() 范围为0~11)
function getTsQuarterMs(paramDate){
	
	return paramDate.getTime()-new Date(paramDate.getFullYear(),Math.floor(paramDate.getMonth()/3)*3).getTime();
}

//获取本年毫秒数
function getTsYearMs(paramDate){
	return paramDate.getTime()-new Date(paramDate.getFullYear(),0).getTime();
}

//今天、昨天、本周、上周、本月、上月、本季、上季、本年、上年
//根据时间范围计算开始和结束时间
function changeDateRange(jDateRange,jStartDateEle,jEndDateEle) {
	var nowTime = new Date();
	var totalMs = nowTime.getTime();
	var beginTime, endTime = null;
	switch (jDateRange.val()) {
	case 'today':
		beginTime = new Date(totalMs - getTodayMS(nowTime));
		endTime = nowTime;
		break;
	case 'ysday':
		beginTime = new Date(totalMs - getTodayMS(nowTime) - 24 * 60 * 60* 1000)
		endTime = new Date(totalMs - getTodayMS(nowTime) - 1);
		break;
	case 'tsweek':
		beginTime = new Date(totalMs - getTsWeekMS(nowTime));
		endTime = nowTime;
		break;
	case 'lsweek':
		beginTime = new Date(totalMs - getTsWeekMS(nowTime) - 7 * 24 * 60 * 60* 1000);
		endTime = new Date(totalMs - getTsWeekMS(nowTime) - 1);
		break;
	case 'tsmonth':
		beginTime = new Date(totalMs - getTsMonthMS(nowTime));
		endTime = nowTime;
		break;
	case 'lsmonth':
		//先取结束时间以方便算出上个月的总天数
		endTime = new Date(totalMs - getTsMonthMS(nowTime) - 1);
		beginTime = new Date(totalMs - getTsMonthMS(nowTime)- endTime.getDate() * 24 * 60 * 60 * 1000);
		break;
	case 'tsQuearter':
		beginTime = new Date(totalMs - getTsQuarterMs(nowTime));
		endTime = nowTime;
		break;
	case 'lsQuearter'://先计算季度末时间，以方便计算季初时间
		endTime = new Date(totalMs - getTsQuarterMs(nowTime)-1);
		beginTime = new Date(endTime.getFullYear(),Math.floor(endTime.getMonth()/3)*3);
		break;
	case 'tsYear':
		beginTime = new Date(totalMs - getTsYearMs(nowTime));
		endTime = nowTime;
		break;
	case 'lsYear':
		beginTime = new Date(nowTime.getFullYear()-1,0);
		endTime = new Date(totalMs - getTsYearMs(nowTime)-1);
		break;
	}
	
	beginTime ? jStartDateEle.val(beginTime.format("yyyy-MM-dd hh:mm:ss"))
			: jStartDateEle.val('');
	endTime ? jEndDateEle.val(endTime.format("yyyy-MM-dd hh:mm:ss"))
			: jEndDateEle.val('');
}
