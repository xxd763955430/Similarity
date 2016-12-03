package com.weixin.message.group;

import java.util.Map;

public class BaseMessage {
	private Map<String, Object> filter;
	private String msgtype;

	public Map<String, Object> getFilter() {
		return filter;
	}

	public void setFilter(Map<String, Object> filter) {
		this.filter = filter;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}
