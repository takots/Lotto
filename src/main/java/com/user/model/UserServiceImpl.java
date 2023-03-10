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
	public boolean signuppp(UserVO vo) {
		return dao.signUp(vo);
	}

	@Override
	public UserVO loginnn(JSONObject jsonobject) {
		return dao.login(jsonobject);
	}

	@Override
	public boolean nameExist(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public boolean emailExist(String email) {
		return dao.findByUseremail(email);
	}

	@Override
	public boolean emailFormat(String email) {
		String regex = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(email).find();
	}
}
