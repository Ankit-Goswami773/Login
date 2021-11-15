package com.loginproject.controller;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class SempleTest {
	public static void main(String[] args) {
	//	  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	
		Calendar date1 = Calendar.getInstance();		
		Date date  = new Date(date1.getTimeInMillis());
		 Timestamp timestamp  = new Timestamp(date.getTime());
		    System.out.println(timestamp);  
		    Date datenext  = new Date(date1.getTimeInMillis()+(10*60*1000));		   
		    Timestamp timestamp2  = new Timestamp(datenext.getTime());
	   long time = timestamp2.getTime();
	   long time2 = timestamp.getTime();
	 System.err.println(((time - time2)/1000)/60);
		    
	}
}
