package com.weixin.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.pojo.Token;
import com.weixin.util.AdvancedUtil;
import com.weixin.util.CommonUtil;
import com.weixin.util.MenuUtil;

/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-10-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static PersonalMenu getPersonalMenu()
	{
		ClickButton join = new ClickButton();
		join.setName("会员注册");
		join.setType("click");
		join.setKey("join");
		ViewButton send = new ViewButton();
		send.setName("群发消息测试");
		send.setType("view");
		send.setUrl("http://xxd763955430.eicp.net/Weixin/sendMessage.jsp");
		PersonalMenu pmenu=new PersonalMenu();
		pmenu.setButton(new Button[]{join,send});
		Matchrule rule=new Matchrule();
		rule.setGroup_id(2);
		pmenu.setMatchrule(rule);
		return pmenu;
	}
	private static Menu getMenu() {
		ClickButton join = new ClickButton();
		join.setName("会员注册1");
		join.setType("click");
		join.setKey("join");
		/*ViewButton send = new ViewButton();
		send.setName("群发消息");
		send.setType("view");
		send.setUrl("http://xxd763955430.eicp.net/Weixin/sendMessage.jsp");*/
		Menu menu=new Menu();
		menu.setButton(new Button[]{join});
		return menu;
		
/*
		ClickButton btn12 = new ClickButton();
		btn12.setName("ITeye");
		btn12.setType("click");
		btn12.setKey("iteye");

		ViewButton btn13 = new ViewButton();
		btn13.setName("CocoaChina");
		btn13.setType("view");
		btn13.setUrl("http://www.iteye.com");

		ViewButton btn21 = new ViewButton();
		btn21.setName("淘宝");
		btn21.setType("view");
		btn21.setUrl("http://m.taobao.com");

		ViewButton btn22 = new ViewButton();
		btn22.setName("京东");
		btn22.setType("view");
		btn22.setUrl("http://m.jd.com");

		ViewButton btn23 = new ViewButton();
		btn23.setName("唯品会");
		btn23.setType("view");
		btn23.setUrl("http://m.vipshop.com");

		ViewButton btn24 = new ViewButton();
		btn24.setName("当当网");
		btn24.setType("view");
		btn24.setUrl("http://m.dangdang.com");

		ViewButton btn25 = new ViewButton();
		btn25.setName("苏宁易购");
		btn25.setType("view");
		btn25.setUrl("http://m.suning.com");

		ViewButton btn31 = new ViewButton();
		btn31.setName("百度");
		btn31.setType("view");
		btn31.setUrl("http://www.baidu.com");

		ViewButton btn32 = new ViewButton();
		btn32.setName("一窝88");
		btn32.setType("view");
		btn32.setUrl("http://www.yi588.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("技术交流");
		mainBtn1.setSub_button(new Button[] { btn11});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("购物");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("网页游戏");
		mainBtn3.setSub_button(new Button[] { btn31, btn32 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });*/
	}

	public static void main(String[] args) {
		String accessToken=AdvancedUtil.getAccessToken();
		if (null != accessToken) {
			// 创建菜单
			//boolean result = MenuUtil.createMenu(getMenu(), accessToken);
			boolean result = MenuUtil.deleteMenu(accessToken);
			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}
}
