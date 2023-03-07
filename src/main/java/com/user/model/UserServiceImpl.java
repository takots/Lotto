package com.user.model;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class UserServiceImpl implements UserService{
	private UserDAO dao;
	public UserServiceImpl(){
		dao = new UserDAOImpl();
	}
	
	@Override
	public boolean SIGNUP(UserVO vo) {
		return dao.userSignUp(vo);
	}

	@Override
	public UserVO LOGIN(JSONObject jsonobject) {
		return dao.userLogin(jsonobject);
	}

	@Override
	public boolean NameIsExist(String username) {
		return dao.selectUserName(username);
	}

	@Override
	public boolean EmailIsExist(String email) {
		return dao.selectUserEmail(email);
	}

	@Override
	public boolean EmailFormat(String email) {
		String regex = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(email).find();
	}
}
