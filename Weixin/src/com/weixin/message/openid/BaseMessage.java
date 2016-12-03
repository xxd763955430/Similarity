package com.weixin.message.openid;

import java.util.List;

public class BaseMessage {
	private List<String> touser;
	private String msgtype;
	public List<String> getTouser() {
		return touser;
	}
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
}
