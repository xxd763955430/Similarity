package com.weixin.junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



public class product {
 private static Connection connection = null;
 private static PreparedStatement preparedStatement = null;
 private static String driver="com.mysql.jdbc.Driver";
 private static String url="jdbc:mysql://127.0.0.1:3306/product?useUnicode=true&characterEncoding=utf8";
 private static String userName="root";
 private static String password="root";
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			 connection = DriverManager.getConnection(url, userName, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
@Test
 public void have()
 {	
	System.out.println(filterString("（ROWA)49U2000"));
 }
 public static String filterString(String model)
 {

	 model=model.replaceAll("[\u4e00-\u9fa5]","");//去除中文
	 model=model.replaceAll("\\s","");//去除空格
	 model=model.replaceAll("\\(.*?\\)","");//去除英文括号
	 model=model.replaceAll("\\（.*?\\）","");//去除中文括号
	 model=model.replaceAll("\\（.*?\\)","");
	 model=model.replaceAll("\\(.*?\\）","");
	 return model;
 }
 public static void main(String[] args)
 { 
	 Map<String,String> product_idMap= getIdModelMap("select * from newgoods_jd");
	 int lineNum=0;
	 for(Map.Entry<String, String> entry:product_idMap.entrySet())
	 {
		 String model=filterString(entry.getValue());
		 String product_id=entry.getKey();
		 String SQL_UPDATE=String.format("UPDATE newgoods_jd set model='%s' where product_id='%s'",model,product_id);
		 lineNum++;
		 System.out.println(lineNum+SQL_UPDATE);	 
		 updateModel(SQL_UPDATE);
	 }
	 freeConnection();
 }
 public static int updateModel(String sql)
 {
	int affectRow=0;
	try {
		preparedStatement = connection.prepareStatement(sql); 
		affectRow = preparedStatement.executeUpdate();	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		freepreparedStatement();
	}
	return affectRow;     
 }
 
 public static Map<String,String> getIdModelMap(String sql,String...parameters)
 {
	 ResultSet rs = null;
	 Map<String,String> product_idMap=new HashMap<String, String>();
	 PreparedStatement preparedStatement=null;
	try {
		preparedStatement = connection.prepareStatement(sql); 
		for (int i = 0; i < parameters.length; i++) {
			preparedStatement.setString(i + 1, parameters[i]);
        }
		rs = preparedStatement.executeQuery();
		while(rs.next())
		{
			String product_id=rs.getString("product_id");
			String model=rs.getString("model");
			product_idMap.put(product_id, model);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		freeResultSet(rs);
		
	}
	return product_idMap;
     
 }
	public static void freeResultSet(ResultSet rs) {
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	public static void freepreparedStatement()
	{
		if (preparedStatement != null)
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void freeConnection()
	{
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}				
	}
	
}
