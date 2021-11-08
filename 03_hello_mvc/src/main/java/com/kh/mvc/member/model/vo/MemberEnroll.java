package com.kh.mvc.member.model.vo;

import java.sql.Date;

public class MemberEnroll {

	private String memberId;
	private String password;
	private String memberName;
	private Date birthday;
	private String email;
	private String tel;
	private String address;
	private String gender;
	private String hobby;
	public MemberEnroll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberEnroll(String memberId, String password, String memberName, Date birthday, String email, String tel,
			String address, String gender, String hobby) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.birthday = birthday;
		this.email = email;
		this.tel = tel;
		this.address = address;
		this.gender = gender;
		this.hobby = hobby;
	}
	public MemberEnroll(String password, String memberName, Date birthday, String email, String tel,
			String address, String gender, String hobby) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.birthday = birthday;
		this.email = email;
		this.tel = tel;
		this.address = address;
		this.gender = gender;
		this.hobby = hobby;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	
	
	
}
