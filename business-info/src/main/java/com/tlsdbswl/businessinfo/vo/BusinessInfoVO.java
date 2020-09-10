package com.tlsdbswl.businessinfo.vo;

import java.io.Serializable;

public class BusinessInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String vldtVlKrnNm;
	private String adptDt;
	private String scsnDt;
	private String jnngpCnt;
	private String name;
	private String no;
	private String addr;
	private String wkplJnngStcd;
	private String nwAcqzrCnt;
	private String lssJnngpCnt;
	
	public BusinessInfoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessInfoVO(String vldtVlKrnNm, String adptDt, String scsnDt, String jnngpCnt, String name, String no,
			String addr, String wkplJnngStcd, String nwAcqzrCnt, String lssJnngpCnt) {
		super();
		this.vldtVlKrnNm = vldtVlKrnNm;
		this.adptDt = adptDt;
		this.scsnDt = scsnDt;
		this.jnngpCnt = jnngpCnt;
		this.name = name;
		this.no = no;
		this.addr = addr;
		this.wkplJnngStcd = wkplJnngStcd;
		this.nwAcqzrCnt = nwAcqzrCnt;
		this.lssJnngpCnt = lssJnngpCnt;
	}

	public String getVldtVlKrnNm() {
		return vldtVlKrnNm;
	}

	public void setVldtVlKrnNm(String vldtVlKrnNm) {
		this.vldtVlKrnNm = vldtVlKrnNm;
	}

	public String getAdptDt() {
		return adptDt;
	}

	public void setAdptDt(String adptDt) {
		this.adptDt = adptDt;
	}

	public String getScsnDt() {
		return scsnDt;
	}

	public void setScsnDt(String scsnDt) {
		this.scsnDt = scsnDt;
	}

	public String getJnngpCnt() {
		return jnngpCnt;
	}

	public void setJnngpCnt(String jnngpCnt) {
		this.jnngpCnt = jnngpCnt;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getWkplJnngStcd() {
		return wkplJnngStcd;
	}

	public void setWkplJnngStcd(String wkplJnngStcd) {
		this.wkplJnngStcd = wkplJnngStcd;
	}

	public String getNwAcqzrCnt() {
		return nwAcqzrCnt;
	}

	public void setNwAcqzrCnt(String nwAcqzrCnt) {
		this.nwAcqzrCnt = nwAcqzrCnt;
	}

	public String getLssJnngpCnt() {
		return lssJnngpCnt;
	}

	public void setLssJnngpCnt(String lssJnngpCnt) {
		this.lssJnngpCnt = lssJnngpCnt;
	}

	@Override
	public String toString() {
		return "BusinessInfoVO [vldtVlKrnNm=" + vldtVlKrnNm + ", adptDt=" + adptDt + ", scsnDt=" + scsnDt
				+ ", jnngpCnt=" + jnngpCnt + ", name=" + name + ", no=" + no + ", addr=" + addr + ", wkplJnngStcd="
				+ wkplJnngStcd + ", nwAcqzrCnt=" + nwAcqzrCnt + ", lssJnngpCnt=" + lssJnngpCnt + "]";
	}
}
