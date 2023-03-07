package com.user.model;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface UserDAO {
	public boolean selectUserName(String username);
	public boolean selectUserEmail(String email);
	public boolean userSignUp(UserVO vo);
	public UserVO userLogin(JSONObject jsonobject);
}
