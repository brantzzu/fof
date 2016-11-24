package utils;

import play.Play;

public class PlayConfigurations {
	public static String s_pageSize = Play.configuration.getProperty("crud.pageSize", "20");
	public static int pageSize = Integer.valueOf(s_pageSize);  
}
