package com.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.message.resp.TextMessage;
import com.weixin.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author XiaodongXu
 * @date 2016-11-17
 */
public class CoreService {

	// 日志对象
	private static Logger log = LoggerFactory.getLogger(CoreService.class);

	// 未审核的弹幕消息
	public static List<String> originalBarrageList = new ArrayList<String>();
	// 已审核的弹幕消息
	public static List<String> checkedBarrageList = new ArrayList<String>();
	// 手机号列表
	public static List<String> phoneNumList = new ArrayList<String>();

	/**
	 * 处理微信发来的请求并向用户发送消息
	 * 
	 * @param request
	 * @return XML
	 */
	public static String processRequest(HttpServletRequest request) {
		// 用于回复的XML消息
		String respXml = null;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 配置发送对象
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(requestMap.get("FromUserName"));
			textMessage.setFromUserName(requestMap.get("ToUserName"));
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// 根据消息类型处理消息
			String returnMessage = null;
			switch (requestMap.get("MsgType")) {
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				returnMessage = dealEventMessage(requestMap);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				returnMessage = dealTextMessage(requestMap);
				break;
			default:
				returnMessage = "发送 ‘弹幕内容（纯文本）’+‘手机号’ 进行抽奖";
				break;
			}
			if (null != returnMessage) {
				textMessage.setContent(returnMessage);
				respXml = MessageUtil.messageToXml(textMessage);
			} else {
				respXml = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	/**
	 * 处理事件类消息
	 * 
	 * @param requestMap
	 * @return
	 */
	private static String dealEventMessage(Map<String, String> requestMap) {
		String result = null;
		switch (requestMap.get("Event")) {
		case MessageUtil.EVENT_TYPE_SUBSCRIBE:
			result = "欢迎关注华理信息学院研究生会！";
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 处理文本类消息
	 * 
	 * @param requestMap
	 * @return
	 */
	private static String dealTextMessage(Map<String, String> requestMap) {
		String result = null;
		String content = requestMap.get("Content");
		content = splitContent(content);
		if (cleanContent(content)) {
			originalBarrageList.add(content.replace(" ",""));
		}
		log.info("{}", content);
		result = "发送 ‘弹幕内容（纯文本）’+‘手机号’ 进行抽奖";
		return result;
	}

	/**
	 * 切分原始消息中的弹幕消息和手机号
	 * 
	 * @param content
	 * @return
	 */
	private static String splitContent(String content) {
		String[] temp = content.split("\\+");
		int size = temp.length;
		String phoneNum = temp[size - 1];
		try {
			Double.parseDouble(phoneNum);
			if (size > 1) {
				content = content.substring(0, content.length() - phoneNum.length() - 1);
				if (phoneNum.matches("^1[3,4,5,7,8]\\d{9}$")) {
					phoneNumList.add(phoneNum);
				}
			} else if(size > 0){
				if (phoneNum.matches("^1[3,4,5,7,8]\\d{9}$")) {
					phoneNumList.add(phoneNum);
					content = "";
				}
			}
		} catch (Exception e) {
			log.warn("{}", "非手机号进行转换");
		}
		return content;
	}

	/**
	 * 对弹幕消息进行清洗，如果包含表情信息则忽略
	 * 
	 * @param content
	 * @return
	 */
	private static boolean cleanContent(String content) {
		if (content.contains("/:")) {
			return false;
		}
		if (content.contains("收到不支持的消息类型，暂无法显示")) {
			return false;
		}
		if (content.equals("")) {
			return false;
		}
		return true;
	}

}
