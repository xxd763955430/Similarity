package com.weixin.message.group;

import java.util.Map;

public class NewsMessage extends BaseMessage {
	private Map<String,String> mpnews;

	public Map<String, String> getMpnews() {
		return mpnews;
	}

	public void setMpnews(Map<String, String> mpnews) {
		this.mpnews = mpnews;
	}
	
}
