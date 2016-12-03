package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.weixin.service.CoreService;

/**
 * 处理用户的获得电话号码的消息请求
 * 
 * @author XiaodongXu
 *
 */
public class GetPhoneNumList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 处理GET请求
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		int sendCount = 50;
		if(sendCount>CoreService.phoneNumList.size()){
			sendCount = CoreService.phoneNumList.size();
		}
		List<String> sendList = new ArrayList<String>();
		while(sendCount > 0){
			sendList.add(CoreService.phoneNumList.get(0));
			CoreService.phoneNumList.remove(0);
		}
		JSONArray jsonArray = new JSONArray(sendList);
		PrintWriter out = response.getWriter();
		out.println(jsonArray);
	}

}
