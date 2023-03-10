package com.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.common.Crypt;
import com.user.model.UserService;
import com.user.model.UserServiceImpl;
import com.user.model.UserVO;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		UserService service = new UserServiceImpl();
		UserVO vo = new UserVO();
		HttpSession session = request.getSession();
		Crypt crypt = new Crypt();
		JSONObject jsonobject = new JSONObject();
		String action = request.getParameter("action");

		// 登入
		if(action.equals("LogIn")) {
			boolean isLogin = false;
			String email = request.getParameter("email");
			String password = request.getParameter("pwd");
			jsonobject.put("email", crypt.encodeBase64(email));
			jsonobject.put("pwd", crypt.encodeBase64(password));
			vo = service.loginnn(jsonobject);
			if (vo.getOrgId() != null) {
				isLogin = true;
			}
			session.setAttribute("OrgId"  , vo.getOrgId());
			session.setAttribute("OrgName", vo.getOrgName());
			System.out.println("vo.isAdmin()> " + vo.isAdmin());
			if (vo.isAdmin()) {
				session.setAttribute("amiadmin", vo.isAdmin());
			}
			out.print(isLogin);
			jsonobject.clear();
		}
		
		// 註冊
		if(action.equals("SignUp")) {
			String username = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("pwd");
			vo.setOrgName(username);
			vo.setEmail(crypt.encodeBase64(email));
			vo.setPassword(crypt.encodeBase64(password));
			if(service.nameExist(username)) {
				jsonobject.put("nameexist"    ,true);
				jsonobject.put("errormsg" ,"\"" + username + "\" this name 已存在，是否改成其他名稱？");
				out.print(jsonobject);
				jsonobject.clear();
				return;
			}
			if(service.emailExist(crypt.encodeBase64(email))) {
				jsonobject.put("emailexist"    ,true);
				jsonobject.put("errormsg" ,"\"" + email + "\" this email 已存在");
				out.print(jsonobject);
				jsonobject.clear();
				return;
			}
			if(!service.emailFormat(email)) {
				jsonobject.put("format"   ,false);
				jsonobject.put("errormsg" ,"\"" + email + "\" this email 格式錯誤，檢查是否少了 @");
				out.print(jsonobject);
				jsonobject.clear();
				return;
			}
			// 管理員註冊
			String keyword = request.getParameter("keyword");
			System.out.println("keyword> " + keyword);
			if(!keyword.equals("")) {
				vo.setAdmin(true);
			}else {
				vo.setAdmin(false);
			}
			out.print(service.signuppp(vo));
			jsonobject.clear();
		}
	}
}
