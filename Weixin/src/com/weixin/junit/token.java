package com.weixin.junit;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.pojo.Token;
import com.weixin.pojo.WeixinGroup;
import com.weixin.pojo.WeixinUserInfo;
import com.weixin.pojo.WeixinUserList;
import com.weixin.service.CoreService;
import com.weixin.util.AdvancedUtil;
import com.weixin.util.CommonUtil;

public class token {
	private static Logger log = LoggerFactory.getLogger(token.class);

	@Test
	public void have() {

		/*
		 * Token token=CommonUtil.getToken("wx00e7ccc71e7a1c69",
		 * "d4624c36b6795d1d99dcf0547af5443d"); String
		 * accessToken=token.getAccessToken(); WeixinUserList userlist=
		 * AdvancedUtil.getUserList(accessToken, null); String
		 * openId=userlist.getOpenIdList().get(0); WeixinUserInfo user=
		 * AdvancedUtil.getUserInfo(accessToken, openId);
		 * System.out.println(user.getNickname());
		 * AdvancedUtil.updateMemberGroup(accessToken, openId, 2);
		 */
		log.info("{}{}");
	}


	public void have1() {
		Token token = CommonUtil.getToken("wx00e7ccc71e7a1c69",
				"d4624c36b6795d1d99dcf0547af5443d");
		String accessToken = token.getAccessToken();
		List<WeixinGroup> groups = AdvancedUtil.getGroups(accessToken);
		for (WeixinGroup group : groups) {
			System.out.println(group.getId() + group.getName()
					+ group.getCount());
		}
	}
}
