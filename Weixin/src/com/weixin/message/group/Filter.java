package com.weixin.message.group;
//分组群发过滤
public class Filter {
	private Boolean is_to_all;
	private int group_id;
	public Boolean getIs_to_all() {
		return is_to_all;
	}
	public void setIs_to_all(Boolean is_to_all) {
		this.is_to_all = is_to_all;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	
}
