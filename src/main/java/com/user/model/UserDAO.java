package com.user.model;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface UserDAO {
	public boolean findByUsername(String username);
	public boolean findByUseremail(String email);
	public boolean signUp(UserVO vo);
	public UserVO  login(JSONObject jsonobject);
}
