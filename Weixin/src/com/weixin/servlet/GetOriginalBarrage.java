package com.weixin.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.weixin.service.CoreService;

/**
 * 处理用户请求审核消息
 * 
 * @author XiaodongXu
 *
 */
public class GetOriginalBarrage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 处理用户GET请求
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String parameter = request.getParameter("param");
		if (null != parameter) {
			switch (parameter) {
			case "clear":
				CoreService.checkedBarrageList.clear();
				out.print("");
				break;
			case "check":
				out.print(getBarrage(20));
				break;
			case "count":
				out.print(getCount());
				break;
			default:
				break;
			}
		} else {
			out.print("");
		}
		out.close();
	}

	/**
	 * 获取列表中的数量
	 * @return
	 */
	private JSONArray getCount(){
		List<String> countList = new ArrayList<String>();
		countList.add(String.valueOf(CoreService.originalBarrageList.size()));
		countList.add(String.valueOf(CoreService.checkedBarrageList.size()));
		return JSONArray.fromObject(countList);
	}
	
	/**
	 * 将指定条数弹幕组合成JSON串
	 * 
	 * @param num
	 * @return
	 */
	private JSONArray getBarrage(int num) {
		List<String> danmuList = new ArrayList<String>();
		if (num > CoreService.originalBarrageList.size()) {
			num = CoreService.originalBarrageList.size();
		}
		while (num > 0) {
			danmuList.add(CoreService.originalBarrageList.get(0));
			CoreService.originalBarrageList.remove(0);
			num--;
		}
		return JSONArray.fromObject(danmuList);
	}

}
