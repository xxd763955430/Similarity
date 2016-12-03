package com.weixin.junit;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.weixin.domain.User;
import com.weixin.pojo.WeixinUserInfo;
import com.weixin.util.AdvancedUtil;

public class mybatis {


	public static void main(String[] args)
	{
		    String resource = "mybatis_config.xml";
	        InputStream is = mybatis.class.getClassLoader().getResourceAsStream(resource);
	        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);	      
	        SqlSession session = sessionFactory.openSession();
	        String statement = "com.weixin.mapping.userMapper.getUser";//映射sql的标识字符串
	        List<User> user = session.selectList(statement,3);
	        System.out.println(user.size());
	}
	@Test
	public void insert()
	{
		String resource = "mybatis_config.xml";
	     InputStream is = mybatis.class.getClassLoader().getResourceAsStream(resource);
	     SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);	      
	     SqlSession session = sessionFactory.openSession();
		 WeixinUserInfo userinfo= new WeixinUserInfo();
		userinfo.setOpenId("openid");
		userinfo.setNickname("nickname");
       String statement = "com.weixin.mapping.userMapper.addWeixinUser";//映射sql的标识字符串
       int retResult = session.insert(statement,userinfo);
       session.commit();
       //使用SqlSession执行完SQL之后需要关闭SqlSession
       session.close();
       System.out.println(retResult);
	}
}
