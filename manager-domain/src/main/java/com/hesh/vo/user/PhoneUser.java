package com.hesh.vo.user;


import java.io.Serializable;

/**
 * 
 * 号码绑定关系表
 * 
 **/
@SuppressWarnings("serial")
  public class PhoneUser implements Serializable {

  private static final long serialVersionUID = 1L;
	/**主键**/
	private Integer id;

	/**账号**/
	private String cuPhone;

	/**密码**/
	private String cuPassword;

	/**绑定业务员编号**/
	private String userPin;

	/**登录人Id**/
	private String opUserId;

	/**删除标识0是1否**/
	private Integer opFlag;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setCuPhone(String cuPhone){
		this.cuPhone = cuPhone;
	}

	public String getCuPhone(){
		return this.cuPhone;
	}

	public void setCuPassword(String cuPassword){
		this.cuPassword = cuPassword;
	}

	public String getCuPassword(){
		return this.cuPassword;
	}

	public void setUserPin(String userPin){
		this.userPin = userPin;
	}

	public String getUserPin(){
		return this.userPin;
	}

	public void setOpUserId(String opUserId){
		this.opUserId = opUserId;
	}

	public String getOpUserId(){
		return this.opUserId;
	}

	public void setOpFlag(Integer opFlag){
		this.opFlag = opFlag;
	}

	public Integer getOpFlag(){
		return this.opFlag;
	}

}
