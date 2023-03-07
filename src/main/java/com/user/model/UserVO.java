package com.user.model;

import lombok.Data;

@Data
public class UserVO implements java.io.Serializable{
	private Integer orgId;
	private String email;
	private String password;
	private String orgName;
	private boolean isAdmin;
}
