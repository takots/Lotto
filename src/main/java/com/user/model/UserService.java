package com.user.model;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface UserService {
	// is name exist
	public boolean nameExist(String username); 
	
	// is email exist
	public boolean emailExist(String email);
	
	// check eamil format
	public boolean emailFormat(String email);
	
	public boolean signuppp(UserVO vo);
	public UserVO  loginnn(JSONObject jsonobject);
}
