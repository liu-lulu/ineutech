package com.kkbc.entity;

public class MemberCertificate {
	private String member_serialno;
	private String certificate_name;
	private String issuing_authority;
	private boolean calculate;
	public String getCertificate_name() {
		return certificate_name;
	}
	public void setCertificate_name(String certificate_name) {
		this.certificate_name = certificate_name;
	}
	public boolean isCalculate() {
		return calculate;
	}
	public void setCalculate(boolean calculate) {
		this.calculate = calculate;
	}
	public String getMember_serialno() {
		return member_serialno;
	}
	public void setMember_serialno(String member_serialno) {
		this.member_serialno = member_serialno;
	}
	public String getIssuing_authority() {
		return issuing_authority;
	}
	public void setIssuing_authority(String issuing_authority) {
		this.issuing_authority = issuing_authority;
	}
	

}
