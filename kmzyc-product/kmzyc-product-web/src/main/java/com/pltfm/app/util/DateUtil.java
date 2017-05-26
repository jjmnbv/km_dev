package com.pltfm.app.util;

import java.util.Date;

public class DateUtil {
	public static Date getMiddDate(Date startDate,Date endDate){
		Long midd = (endDate.getTime()+startDate.getTime())/2;
		Date middDate = new Date(midd);
		return middDate;
	}
	
	
}
