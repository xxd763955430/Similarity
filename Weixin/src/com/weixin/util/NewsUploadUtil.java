package com.weixin.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.weixin.message.openid.Article;
import com.weixin.message.openid.ArticleList;
import com.weixin.message.openid.NewsMessage;
import com.weixin.pojo.WeixinMedia;
import com.weixin.pojo.WeixinUserList;
//群发图文消息工具类
public class NewsUploadUtil {
	/**
	 * 上传本地图片到微信服务器
	 * @param accessToken
	 * @param path
	 * @return
	 */
	public static List<String> uploadLocalImg(String accessToken,String path)
	{
		 List<String> imgUrl=new ArrayList<String>();
		 File imagepath=new File(path);
		 File[] images = imagepath.listFiles();	
		 for(int i=1;i<images.length;i++)
		   {
			   WeixinMedia media= MaterialUtil.uploadImgReUrl(accessToken, images[i].getName(), path+images[i].getName());
			   imgUrl.add(media.getUrl());	
		   }
		 return imgUrl;
	}
	/**
	 * 删除本地图片
	 * @param path
	 */
	public static void deleteLocalImg(String path)
	{
		File imagepath=new File(path);
		File[] images = imagepath.listFiles();
		for(File image:images)
		   {
			  image.delete();
		   }
	}
	/**
	 * 文章中加入图片
	 * @param content
	 * @param urlList
	 * @return
	 */
	public static String addImgToContent(String content,List<String> urlList)
	{
		String news=new String();
		for(String url:urlList)
		{
			news+=String.format("<img src='%s'/><br/>",url);
		}
		news+=content;
		return news;
	}
	/**
	 * 获取缩略图id
	 * @param accessToken
	 * @param path
	 * @return
	 */
	public static String getThumbMediaId(String accessToken,String path)
	{
		File imagepath=new File(path);
		File[] images = imagepath.listFiles();		  	  
		File image=images[0];
		WeixinMedia media= MaterialUtil.uploadLocalMedia(accessToken,"image",image.getName(), path+image.getName());
		String thumbmediaid=media.getMediaId();
		return thumbmediaid;
	}
	/**
	 * 初始化article列表
	 * @param thumb_media_id
	 * @param title
	 * @param content
	 * @param digest
	 * @return
	 */
	public static ArticleList getArticle(String thumb_media_id,String title,String content,String digest)
	{
		ArticleList articles=new ArticleList();
		List<Article> articleList=new ArrayList<Article>();
		Article article=new Article();	
		article.setThumb_media_id(thumb_media_id);
		article.setTitle(title);
		article.setContent(content);
		article.setDigest(digest);
		article.setShow_cover_pic(1);
		articleList.add(article);
		articles.setArticles(articleList);
		return articles;
	}
	/**
	 * 根据id群发图文消息
	 */
	public static void sendNewsMessage(ArticleList articles,String accessToken)
	{
		String newsMediaid=MaterialUtil.uploadArticle(articles, accessToken);
		WeixinUserList userList=AdvancedUtil.getUserList(accessToken, null);
		List<String> openId= userList.getOpenIdList();
		NewsMessage newsmessage=new NewsMessage();
		newsmessage.setMsgtype("mpnews");
		newsmessage.setTouser(openId);
		Map<String,String> mpnews=new HashMap<String, String>();
		mpnews.put("media_id", newsMediaid);
		newsmessage.setMpnews(mpnews);
		GroupSendUtil.sendMsgByOpenId(accessToken, openId, newsmessage);		
	}
	/**
	 * 获取纯文本
	 * @param htmlStr
	 * @return
	 */
	public static String getTextFromHtml(String htmlStr)
	{
		 String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
		 Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	     Matcher m_html = p_html.matcher(htmlStr);  
	     htmlStr= m_html.replaceAll(""); // 过滤html标签
	     return htmlStr;
	}
	/**
	 * 获取摘要
	 * @param contentText
	 * @return
	 */
	public static String getDigest(String contentText)
	{
		String digest=null;
		if(contentText.length()>=54)
		digest=contentText.substring(0,54);
		else
		digest=contentText.substring(0, contentText.length());	
		return digest;
	}
}
