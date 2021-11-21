package com.kh.ajax.smart.model.vo;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Smart {

	
	private String name;
	private int amount;
	private Date date;
	private int rank;
	
	public Smart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Smart(String name, int amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	public Smart(String name, int amount,Date date) {
		super();
		this.name = name;
		this.amount = amount;
		this.date = date;
	}
	public Smart(String name, int amount,int rank) {
		super();
		this.name = name;
		this.amount = amount;
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		return "Smart [name=" + name + ", amount=" + amount + "]";
	}

	
	
}
