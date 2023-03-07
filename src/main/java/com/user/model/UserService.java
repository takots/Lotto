package com.user.model;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface UserService {
	public boolean NameIsExist(String username);
	public boolean EmailIsExist(String email);
	public boolean EmailFormat(String email);
	public boolean SIGNUP(UserVO vo);
	public UserVO LOGIN(JSONObject jsonobject);
}
