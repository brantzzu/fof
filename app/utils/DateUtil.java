package utils;

import org.joda.time.DateTime;

import play.libs.F.T2;

public class DateUtil {

	public static T2<String,String> latestYear(){ 
		String dateFormatString = "yyyy-MM-dd"; 
		
		String endDate = DateTime.now().toString(dateFormatString); 
		String startDate = DateTime.now().minusMonths(12).toString(dateFormatString); 
		
		return new T2<String,String>(startDate,endDate); 
	}
	public static T2<String,String> latestHalfYear(){ 
		String dateFormatString = "yyyy-MM-dd"; 
		
		String endDate = DateTime.now().toString(dateFormatString); 
		String startDate = DateTime.now().minusMonths(6).toString(dateFormatString); 
		
		return new T2<String,String>(startDate,endDate); 
	}
	public static T2<String,String> latestQuarter(){ 
		String dateFormatString = "yyyy-MM-dd"; 
		
		String endDate = DateTime.now().toString(dateFormatString); 
		String startDate = DateTime.now().minusMonths(3).toString(dateFormatString); 
		
		return new T2<String,String>(startDate,endDate); 
	}
	public static T2<String,String> latestOneMonth(){ 
		String dateFormatString = "yyyy-MM-dd"; 
		
		String endDate = DateTime.now().toString(dateFormatString); 
		String startDate = DateTime.now().minusMonths(1).toString(dateFormatString); 
		
		return new T2<String,String>(startDate,endDate); 
	}
}
