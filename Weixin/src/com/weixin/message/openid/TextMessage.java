package com.weixin.message.openid;


import java.util.Map;


public class TextMessage extends BaseMessage {
	private Map<String,String> text;

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}
	
/*	Ⱥ���ı���Ϣ
  	Token token= CommonUtil.getToken("wx281d2913ca8a63b8", "cb2db33877885fbfa5b6fbdab0c63ae4");
	String accessToken=token.getAccessToken();
	WeixinUserList user=AdvancedUtil.getUserList(accessToken, null);
	List<String> openId= user.getOpenIdList();
	WeixinUserInfo userinfo= AdvancedUtil.getUserInfo(accessToken,openId.get(0));
	String nickname=userinfo.getNickname();
	System.out.println(nickname);
	TextMessage textmessage=new TextMessage();
	textmessage.setTouser(openId);
	textmessage.setMsgtype("text");
	Map<String,String> text=new HashMap<>();
	text.put("content", "hello from boxer");
	textmessage.setText(text);
	AdvancedUtil.sendMsgByOpenId(accessToken, openId, textmessage);*/
}
