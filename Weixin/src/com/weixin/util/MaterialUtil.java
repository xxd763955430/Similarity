package com.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;
import com.weixin.message.openid.ArticleList;
import com.weixin.pojo.WeixinMedia;
public class MaterialUtil {
	private static Logger log = LoggerFactory.getLogger(MaterialUtil.class);
	/**
	 * 上传图文消息素材
	 * @param articles
	 * @param accessToken
	 * @return mediaid
	 */
	public static String uploadArticle(ArticleList articles,String accessToken)
	{
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		JSONObject jsonaobj= JSONObject.fromObject(articles);
		log.info("uploadArticle:{}",jsonaobj);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",jsonaobj.toString());
		log.info("uploadArticle:{}",jsonObject);
		String media_id=jsonObject.getString("media_id");
		return media_id;
	}
	/**
	 * 获取素材数目
	 * @param accessToken
	 */
	public static void getMaterialCount(String accessToken)
	{
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",null);
		log.info("getMaterialCount:{}",jsonObject);
	}
	/**
	 * 获取素材列表
	 * @param accessToken
	 */
	public static void getBatchMeterial(String accessToken)
	{
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonaobj= "{\"type\":\"image\",\"offset\":0,\"count\":20}";
		System.out.println(jsonaobj.toString());
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST",jsonaobj.toString());
		log.info("getBatchMeterial:{}",jsonObject);
	}
	
	/**
	 *  上传图片获取url不占用素材数
	 * @param accessToken
	 * @param type
	 * @param filename
	 * @param path
	 * @return
	 */
	public static WeixinMedia uploadImgReUrl(String accessToken,String filename, String path) {
    WeixinMedia weixinMedia = null;
    try {
            // 拼装请求地址
            String uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
            uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken);
            URL url = new URL(uploadMediaUrl);
            String result = null;
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                    System.out.println("上传的文件不存在");

            }
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);
            // 请求正文信息
            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + filename + "\" \r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream out = new DataOutputStream(con.getOutputStream());
            // 输出表头         
            out.write(head);
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
            }
            in.close();
            // 结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                    buffer.append(line);
            }
            if (result == null) {
                    result = buffer.toString();
            }
            reader.close();
            // 使用JSON-lib解析返回结果
            JSONObject jsonObject = JSONObject.fromObject(result);
            log.info("uploadImgReUrl:{}",jsonObject);
            weixinMedia = new WeixinMedia();
            //如果返回有url字段
            if(jsonObject.has("url")){
                weixinMedia.setUrl(jsonObject.getString("url"));
                }        
    }
    	catch(FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	catch (IOException e) {      
    		e.printStackTrace();
     } 
    return weixinMedia;
	}
	/**
	 * 上传本地临时素材到微信服务器
	 * @param accessToken
	 * @param filename
	 * @param path
	 * @return media的id
	 */
	public static WeixinMedia uploadLocalMedia(String accessToken,String type,String filename, String path) {
	    WeixinMedia weixinMedia = null;
	    try {
	            // 拼装请求地址
	    		String uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	    		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
	            URL url = new URL(uploadMediaUrl);
	            String result = null;
	            File file = new File(path);
	            if (!file.exists() || !file.isFile()) {
	                    System.out.println("上传的文件不存在");
	            }
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
	            con.setDoInput(true);
	            con.setDoOutput(true);
	            con.setUseCaches(false); // post方式不能使用缓存
	            // 设置请求头信息
	            con.setRequestProperty("Connection", "Keep-Alive");
	            con.setRequestProperty("Charset", "UTF-8");
	            // 设置边界
	            String BOUNDARY = "----------" + System.currentTimeMillis();
	            con.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);
	            // 请求正文信息
	            // 第一部分：
	            StringBuilder sb = new StringBuilder();
	            sb.append("--"); // 必须多两道线
	            sb.append(BOUNDARY);
	            sb.append("\r\n");
	            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + filename + "\" \r\n");
	            sb.append("Content-Type:application/octet-stream\r\n\r\n");
	            byte[] head = sb.toString().getBytes("utf-8");
	            // 获得输出流
	            OutputStream out = new DataOutputStream(con.getOutputStream());
	            // 输出表头         
	            out.write(head);
	            // 文件正文部分
	            // 把文件已流文件的方式 推入到url中
	            DataInputStream in = new DataInputStream(new FileInputStream(file));
	            int bytes = 0;
	            byte[] bufferOut = new byte[1024];
	            while ((bytes = in.read(bufferOut)) != -1) {
	                    out.write(bufferOut, 0, bytes);
	            }

	            in.close();
	            // 结尾部分
	            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
	            out.write(foot);
	            out.flush();
	            out.close();
	            StringBuffer buffer = new StringBuffer();
	            BufferedReader reader = null;
	            // 定义BufferedReader输入流来读取URL的响应
	            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    buffer.append(line);
	            }
	            if (result == null) {
	                    result = buffer.toString();
	            }
	            reader.close();
	            // 使用JSON-lib解析返回结果
	            JSONObject jsonObject = JSONObject.fromObject(result);
	            log.info("uploadLocalMedia{}",jsonObject);
	            weixinMedia = new WeixinMedia();
	            if(jsonObject.has("type")){
	            weixinMedia.setType(jsonObject.getString("type"));
	            }
	           if (jsonObject.has("thumb_media_id"))
	           {
	                  weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
	           }
	           if(jsonObject.has("media_id")){
	                    weixinMedia.setMediaId(jsonObject.getString("media_id"));
	            }
	           if(jsonObject.has("created_at")){
	        	   weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));
	           	}        
	    }
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
	    catch (IOException e) {
	        System.out.println("发送POST请求出现异常！" + e);
	        e.printStackTrace();

	    } 
	    return weixinMedia;
		}
	/**
	 * 上传永久媒体文件
	 * @param accessToken
	 * @param type
	 * @param filename
	 * @param path
	 * @return
	 */
	public static WeixinMedia uploadPermanentMedia(String accessToken,String type,String filename, String path) {
	    WeixinMedia weixinMedia = null;
	    try {
	            // 拼装请求地址
	    	 	String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
	    		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
	            URL url = new URL(uploadMediaUrl);
	            String result = null;
	            File file = new File(path);
	            if (!file.exists() || !file.isFile()) {
	                    System.out.println("上传的文件不存在");
	            }
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
	            con.setDoInput(true);
	            con.setDoOutput(true);
	            con.setUseCaches(false); // post方式不能使用缓存
	            // 设置请求头信息
	            con.setRequestProperty("Connection", "Keep-Alive");
	            con.setRequestProperty("Charset", "UTF-8");
	            // 设置边界
	            String BOUNDARY = "----------" + System.currentTimeMillis();
	            con.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);
	            // 请求正文信息
	            // 第一部分：
	            StringBuilder sb = new StringBuilder();
	            sb.append("--"); // 必须多两道线
	            sb.append(BOUNDARY);
	            sb.append("\r\n");
	            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + filename + "\" \r\n");
	            sb.append("Content-Type:application/octet-stream\r\n\r\n");
	            byte[] head = sb.toString().getBytes("utf-8");
	            // 获得输出流
	            OutputStream out = new DataOutputStream(con.getOutputStream());
	            // 输出表头         
	            out.write(head);
	            // 文件正文部分
	            // 把文件已流文件的方式 推入到url中
	            DataInputStream in = new DataInputStream(new FileInputStream(file));
	            int bytes = 0;
	            byte[] bufferOut = new byte[1024];
	            while ((bytes = in.read(bufferOut)) != -1) {
	                    out.write(bufferOut, 0, bytes);
	            }

	            in.close();
	            // 结尾部分
	            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
	            out.write(foot);
	            out.flush();
	            out.close();
	            StringBuffer buffer = new StringBuffer();
	            BufferedReader reader = null;
	            // 定义BufferedReader输入流来读取URL的响应
	            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    buffer.append(line);
	            }
	            if (result == null) {
	                    result = buffer.toString();
	            }
	            reader.close();
	            // 使用JSON-lib解析返回结果
	            JSONObject jsonObject = JSONObject.fromObject(result);
	        	log.info("{}",jsonObject);
	            weixinMedia = new WeixinMedia();
	            if(jsonObject.has("type")){
	            weixinMedia.setType(jsonObject.getString("type"));
	            }
	           if (jsonObject.has("thumb_media_id"))
	           {
	                  weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
	           }
	           if(jsonObject.has("media_id")){
	                    weixinMedia.setMediaId(jsonObject.getString("media_id"));
	            }
	           if(jsonObject.has("created_at")){
	        	   weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));
	           	}        
	    }
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    } 
	    return weixinMedia;
		}
	/**
	 * 下载临时媒体文件
	 * 
	 * @param accessToken 接口访问凭证
	 * @param mediaId 媒体文件标识
	 * @param savePath 文件在服务器上的存储路径
	 * @return
	 */
	public static String getMedia(String accessToken, String mediaId, String savePath) {
		String filePath = null;
		// 拼接请求地址
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		System.out.println(requestUrl);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 根据内容类型获取扩展名
			String fileExt = CommonUtil.getFileExt(conn.getHeaderField("Content-Type"));
			String fileurl = CommonUtil.getFileExt(conn.getHeaderField("curl-G"));
			System.out.println(fileurl);
			// 将mediaId作为文件名
			filePath = savePath + mediaId + fileExt;

			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();
			conn.disconnect();
			log.info("下载媒体文件成功，filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("下载媒体文件失败：{}", e);
		}
		return filePath;
	}
}
