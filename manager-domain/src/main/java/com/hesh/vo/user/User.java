package com.hesh.vo.user;


import java.io.Serializable;

/**
 * 
 * 用户表
 * 
 **/
@SuppressWarnings("serial")
  public class User implements Serializable{

  private static final long serialVersionUID = 1L;
	/**主键**/
	private Integer id;

	/**账号**/
	private String userName;

	/**密码**/
	private String userPassword;

	/**动态验证码**/
	private String dynamicCode;

	/**标识主要区别是否为管理员01**/
	private Integer flag;

	/**删除标识0是1否**/
	private Integer opFlag;



	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return this.userPassword;
	}

	public void setDynamicCode(String dynamicCode){
		this.dynamicCode = dynamicCode;
	}

	public String getDynamicCode(){
		return this.dynamicCode;
	}

	public void setFlag(Integer flag){
		this.flag = flag;
	}

	public Integer getFlag(){
		return this.flag;
	}

	public void setOpFlag(Integer opFlag){
		this.opFlag = opFlag;
	}

	public Integer getOpFlag(){
		return this.opFlag;
	}

}
