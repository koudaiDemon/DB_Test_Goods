package com.cWww.excel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class TypeTools {
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public static int getInt(String s){
		if(Pattern.matches("^\\d*$",s)){
			return Integer.parseInt(s);
		}
		return 0;
	};
	
	public static double getDouble(String s){
		if(Pattern.matches("^\\d+\\.??\\d+$", s)){
			return Double.parseDouble(s);
		}
		return 0.0;
	}
	
	public static Date getDate(String s){
		Date date = null;
		try {
			if((s != null) && (!"".equals(s))){
				date = sdf.parse(s);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static BigDecimal getBigDecimal(String s){
		if(Pattern.matches("^\\d+\\.??\\d+$", s)){
			return new BigDecimal(s);
		}
		return new BigDecimal("0");
	}
	
	
	public static String getDate(Date date){
		if(date != null){
			return sdf.format(date);
		}
		return "";
	}
	
}
