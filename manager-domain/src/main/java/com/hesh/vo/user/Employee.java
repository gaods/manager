package com.hesh.vo.user;


import com.hesh.BaseEntityBean;

/**
 * 
 * 员工表
 * 
 **/
@SuppressWarnings("serial")
  public class Employee extends BaseEntityBean {

  private static final long serialVersionUID = 1L;
	/****/
	private Integer id;

	/**姓名**/
	private String name;

	/**手机号**/
	private String telphone;

	/**工号**/
	private String emno;
	//1完成注册，2注册失败
 	private String flag;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setTelphone(String telphone){
		this.telphone = telphone;
	}

	public String getTelphone(){
		return this.telphone;
	}

	public void setEmno(String emno){
		this.emno = emno;
	}

	public String getEmno(){
		return this.emno;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
