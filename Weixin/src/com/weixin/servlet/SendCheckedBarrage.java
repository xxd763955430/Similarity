package com.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.service.CoreService;

/**
 * 处理用户发送审核后的弹幕消息
 * 
 * @author XiaodongXu
 *
 */
public class SendCheckedBarrage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(SendCheckedBarrage.class);
	/**
	 * 处理用户GET请求
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String[] textList = request.getParameterValues("textList");
		for (int i = 0; i < textList.length; i++) {
			CoreService.checkedBarrageList.add(textList[i]);
			log.info("{}",textList[i]);
			log.info("{}",CoreService.checkedBarrageList.size());
		}
	}
	
	/**
	 * 处理用户POST请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
