package com.kkbc.entity;


public class Member {
	
	private String member_serialno;
	private String name;
	private String sex;
	private String province;
	private String diploma;
	private String degree;
	private String learn_format;
	private Float avg_point;
	private Float work_time;
	private Float manage_time;
	private String company;
	private String company_address;
	private String job;
	private String job_level;
	private Float salary;
	private Integer company_num;
	private Integer staff_num;
	private String industry;
	private String job_function;
	
	private Integer toefl_score;
	private Float ielts_score;
	private Integer gmat_score;
	private Float gre_score;
	
	private String toefl_date;
	private String ielts_date;
	private String gmat_date;
	private String gre_date;
	
	private Integer program;
	
	//身份证号
	private String idnumber;
	//单位性质 
	private String company_property;
	//资产总额
	private Float general_assets;
	//年营业额
	private Float annual_sales_volume;
	//单位简称或备注
	private String company_comment;
	private String degree_type;
	
	private boolean toefl_volid;
	private boolean ielts_volid;
	private boolean gmat_volid;
	private boolean gre_volid;
	
	public String getMember_serialno() {
		return member_serialno;
	}
	public void setMember_serialno(String member_serialno) {
		this.member_serialno = member_serialno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getLearn_format() {
		return learn_format;
	}
	public void setLearn_format(String learn_format) {
		this.learn_format = learn_format;
	}
	public Float getManage_time() {
		return manage_time;
	}
	public void setManage_time(Float manage_time) {
		this.manage_time = manage_time;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJob_level() {
		return job_level;
	}
	public void setJob_level(String job_level) {
		this.job_level = job_level;
	}
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}
	public Integer getStaff_num() {
		return staff_num;
	}
	public void setStaff_num(Integer staff_num) {
		this.staff_num = staff_num;
	}
	public Float getAvg_point() {
		return avg_point;
	}
	public void setAvg_point(Float avg_point) {
		this.avg_point = avg_point;
	}
	public Float getWork_time() {
		return work_time;
	}
	public void setWork_time(Float work_time) {
		this.work_time = work_time;
	}
	public Integer getCompany_num() {
		return company_num;
	}
	public void setCompany_num(Integer company_num) {
		this.company_num = company_num;
	}
	public Integer getToefl_score() {
		return toefl_score;
	}
	public void setToefl_score(Integer toefl_score) {
		this.toefl_score = toefl_score;
	}
	public Float getIelts_score() {
		return ielts_score;
	}
	public void setIelts_score(Float ielts_score) {
		this.ielts_score = ielts_score;
	}
	public Integer getGmat_score() {
		return gmat_score;
	}
	public void setGmat_score(Integer gmat_score) {
		this.gmat_score = gmat_score;
	}
	public Integer getProgram() {
		return program;
	}
	public void setProgram(Integer program) {
		this.program = program;
	}
	public String getDegree_type() {
		return degree_type;
	}
	public void setDegree_type(String degree_type) {
		this.degree_type = degree_type;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getJob_function() {
		return job_function;
	}
	public void setJob_function(String job_function) {
		this.job_function = job_function;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public Float getGeneral_assets() {
		return general_assets;
	}
	public void setGeneral_assets(Float general_assets) {
		this.general_assets = general_assets;
	}
	public Float getAnnual_sales_volume() {
		return annual_sales_volume;
	}
	public void setAnnual_sales_volume(Float annual_sales_volume) {
		this.annual_sales_volume = annual_sales_volume;
	}
	public String getCompany_property() {
		return company_property;
	}
	public void setCompany_property(String company_property) {
		this.company_property = company_property;
	}
	public String getCompany_comment() {
		return company_comment;
	}
	public void setCompany_comment(String company_comment) {
		this.company_comment = company_comment;
	}
	public Float getGre_score() {
		return gre_score;
	}
	public void setGre_score(Float gre_score) {
		this.gre_score = gre_score;
	}
	public String getToefl_date() {
		return toefl_date;
	}
	public void setToefl_date(String toefl_date) {
		this.toefl_date = toefl_date;
	}
	public String getIelts_date() {
		return ielts_date;
	}
	public void setIelts_date(String ielts_date) {
		this.ielts_date = ielts_date;
	}
	public String getGmat_date() {
		return gmat_date;
	}
	public void setGmat_date(String gmat_date) {
		this.gmat_date = gmat_date;
	}
	public String getGre_date() {
		return gre_date;
	}
	public void setGre_date(String gre_date) {
		this.gre_date = gre_date;
	}
	public boolean getToefl_volid() {
		return toefl_volid;
	}
	public void setToefl_volid(boolean toefl_volid) {
		this.toefl_volid = toefl_volid;
	}
	public boolean getIelts_volid() {
		return ielts_volid;
	}
	public void setIelts_volid(boolean ielts_volid) {
		this.ielts_volid = ielts_volid;
	}
	public boolean getGmat_volid() {
		return gmat_volid;
	}
	public void setGmat_volid(boolean gmat_volid) {
		this.gmat_volid = gmat_volid;
	}
	public boolean getGre_volid() {
		return gre_volid;
	}
	public void setGre_volid(boolean gre_volid) {
		this.gre_volid = gre_volid;
	}
	

}
