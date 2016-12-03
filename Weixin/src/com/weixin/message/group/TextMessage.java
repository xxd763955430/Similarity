package com.weixin.message.group;

import java.util.Map;
public class TextMessage extends BaseMessage {
	private Map<String,String> text;

	public Map<String, String> getText() {
		return text;
	}

	public void setText(Map<String, String> text) {
		this.text = text;
	}
}
