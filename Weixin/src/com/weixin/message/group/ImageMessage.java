package com.weixin.message.group;

import java.util.Map;

public class ImageMessage extends BaseMessage {
	private Map<String,String> image;

	public Map<String, String> getImage() {
		return image;
	}

	public void setImage(Map<String, String> image) {
		this.image = image;
	}

}
