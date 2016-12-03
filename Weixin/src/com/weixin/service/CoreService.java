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
 * ���ķ�����
 * 
 * @author XiaodongXu
 * @date 2016-11-17
 */
public class CoreService {

	// ��־����
	private static Logger log = LoggerFactory.getLogger(CoreService.class);

	// δ��˵ĵ�Ļ��Ϣ
	public static List<String> originalBarrageList = new ArrayList<String>();
	// ����˵ĵ�Ļ��Ϣ
	public static List<String> checkedBarrageList = new ArrayList<String>();
	// �ֻ����б�
	public static List<String> phoneNumList = new ArrayList<String>();

	/**
	 * ����΢�ŷ������������û�������Ϣ
	 * 
	 * @param request
	 * @return XML
	 */
	public static String processRequest(HttpServletRequest request) {
		// ���ڻظ���XML��Ϣ
		String respXml = null;
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���÷��Ͷ���
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(requestMap.get("FromUserName"));
			textMessage.setFromUserName(requestMap.get("ToUserName"));
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// ������Ϣ���ʹ�����Ϣ
			String returnMessage = null;
			switch (requestMap.get("MsgType")) {
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				returnMessage = dealEventMessage(requestMap);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				returnMessage = dealTextMessage(requestMap);
				break;
			default:
				returnMessage = "���� ����Ļ���ݣ����ı�����+���ֻ��š� ���г齱";
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
	 * �����¼�����Ϣ
	 * 
	 * @param requestMap
	 * @return
	 */
	private static String dealEventMessage(Map<String, String> requestMap) {
		String result = null;
		switch (requestMap.get("Event")) {
		case MessageUtil.EVENT_TYPE_SUBSCRIBE:
			result = "��ӭ��ע������ϢѧԺ�о����ᣡ";
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * �����ı�����Ϣ
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
		result = "���� ����Ļ���ݣ����ı�����+���ֻ��š� ���г齱";
		return result;
	}

	/**
	 * �з�ԭʼ��Ϣ�еĵ�Ļ��Ϣ���ֻ���
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
			log.warn("{}", "���ֻ��Ž���ת��");
		}
		return content;
	}

	/**
	 * �Ե�Ļ��Ϣ������ϴ���������������Ϣ�����
	 * 
	 * @param content
	 * @return
	 */
	private static boolean cleanContent(String content) {
		if (content.contains("/:")) {
			return false;
		}
		if (content.contains("�յ���֧�ֵ���Ϣ���ͣ����޷���ʾ")) {
			return false;
		}
		if (content.equals("")) {
			return false;
		}
		return true;
	}

}
