package com.tlsdbswl.businessinfo.vo;

import java.io.Serializable;

public class BusinessInfoListVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String date;
	private String seq;
	private String name;
	private String no;
	private String addr;
	
	public BusinessInfoListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessInfoListVO(String seq, String name, String no, String addr, String date) {
		super();
		this.seq = seq;
		this.name = name;
		this.no = no;
		this.addr = addr;
		this.date = date;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "BusinessInfoVO [date=" + date + ", seq=" + seq + ", name=" + name + ", no=" + no + ", addr=" + addr
				+ "]";
	}
	
}
