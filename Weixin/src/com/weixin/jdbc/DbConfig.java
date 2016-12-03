package com.weixin.jdbc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


//数据库配置类
public class DbConfig {
	private String driver;
	private String url;
	private String userName;
	private String password;
	public DbConfig() {
		try {
			InputStream inputStream = DbConfig.class.getClassLoader().getResourceAsStream("mysql.properties");  
			//InputStream inputStream = new BufferedInputStream(new FileInputStream("config/mysql.properties"));			
			Properties p = new Properties();
			p.load(inputStream);
			this.driver = p.getProperty("driver");
			this.url = p.getProperty("url");
			this.userName = p.getProperty("username");
			this.password = p.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getDriver() {
		return driver;		
	}

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}