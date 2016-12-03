package com.weixin.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.weixin.message.openid.BaseMessage;

public class GroupSendUtil {
	private static Logger log = LoggerFactory.getLogger(GroupSendUtil.class);
	/**
	 * 根据分组群发消息
	 * @param accessToken
	 * @param groupId 分组id
	 * @param message
	 * @return String
	 * @author xxd
	 */
	public static String sendMsgByGroup(String accessToken, int groupId,com.weixin.message.group.BaseMessage message)
	{
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		Map<String,Object> filter= message.getFilter();
		filter.remove("group_id");
		filter.put("group_id", groupId);
		message.setFilter(filter);
		JSONObject jsonData =JSONObject.fromObject(message);
		//获取返回的json数据
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonData.toString());
		log.info("sendMsgByGroup:{}",jsonObject);
		return jsonObject.toString();
	}	
	/**
	 * 通过openId群发消息
	 * @param accessToken
	 * @param openId 消息接受者的id
	 * @param message 各种消息
	 * @return
	 * @author xxd
	 */
	public static String sendMsgByOpenId(String accessToken, List<String> openId,BaseMessage message)
	{
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		message.setTouser(openId);
		// 需要提交的json数据
		JSONObject jsonData =JSONObject.fromObject(message);
		//获取返回的json数据
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonData.toString());
		log.info("sendMsgByOpenId:{}",jsonObject);
		return jsonObject.toString();
	}
}
