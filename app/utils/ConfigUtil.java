package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import play.Play;
/**
 * 配置文件工具类
 * 
 */
public class ConfigUtil {
	
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("wechat.properties"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

	public static void setProps(Properties p) {
		props = p;
	}
}