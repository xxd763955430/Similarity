package com.weixin.domain;

import java.util.Date;
/**
 * 
 * @author xuxiaodong
 *
 */
public class User {
	private int id;//自增的id
	private String openid;//微信id
	private String name;
	private String teacherid;
	private String sex;
	private Date birthday;
	private String tel;
	private String protitle;//职称
	private String college;//学院
	public User(int id, String openid, String name, String teacherid, String sex,
			Date birthday, String tel, String protitle, String college) {
		this.id=id;
		this.openid = openid;
		this.name = name;
		this.teacherid = teacherid;
		this.sex = sex;
		this.birthday = birthday;
		this.tel = tel;
		this.protitle = protitle;
		this.college = college;
	}
	public User(String openid, String name, String teacherid, String sex,
			Date birthday, String tel, String protitle, String college) {
		this.openid = openid;
		this.name = name;
		this.teacherid = teacherid;
		this.sex = sex;
		this.birthday = birthday;
		this.tel = tel;
		this.protitle = protitle;
		this.college = college;
	}
	public User()
	{
		
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacherid() {
		return teacherid;
	}
	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getProtitle() {
		return protitle;
	}
	public void setProtitle(String protitle) {
		this.protitle = protitle;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}