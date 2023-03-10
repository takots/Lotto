package com.lotto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.lotto.model.DescribeVO;
import com.lotto.model.LottoService;
import com.lotto.model.LottoServiceImpl;

public class LottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		LottoService service = new LottoServiceImpl();
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		System.out.println("action> " + action);
		// 產生今日樂透
		if(action.equals("Today")) {
			Map<String , String> map = new HashMap<>();
			map.put("lotto", service.todayLotto());
			map.put("special", service.todaySpecial());
			JSONObject json = new JSONObject(map);
			out.print(json); // 今天樂透數據
//			service.goLotto(); // 兌獎
			service.countNumbers(); // 每個獎有誰
		}
		
		if(action.equals("ChooseBall")) {
			String ball = "" , calculate = "" , value = "";
	        ball = request.getParameter("ball");
	        log("ball> " + ball);
	    	calculate = request.getParameter("calculate"); // 是否取消這個號碼 KEY:minus ,Plus
	    	value = request.getParameter("value"); // 是否取消這個號碼 KEY:minus ,Plus
	    	service.saveSessionBall(session ,ball ,calculate);
		}
		
		// 使用者點選後可以補齊 6 個數字
		if(action.equals("UserRandomBall")) {
			String sesschooseBall = "";
			Object chooseball = session.getAttribute("ChooseBall");
			if(chooseball != null) {
				sesschooseBall = chooseball.toString();
			}
			System.out.println("sesschooseBall> " + sesschooseBall);
			out.print(service.replenishWithRandom(sesschooseBall));
		}
		
		// 周公解夢
		if(action.equals("CallTheGodOfWealth")) {
			int OrgId = 0;
			if (session.getAttribute("OrgId") != null) {
				OrgId = (Integer)session.getAttribute("OrgId");
			}
			String describeStr = "" ,str = "";
			describeStr = request.getParameter("describeStr");
			System.out.println("describeStr123> " + service.dreamNumber(OrgId, describeStr));
			// 可能發生一個名詞都沒有對到的情況 ,需要返回訊息告知使用者
			out.print(service.dreamNumber(OrgId, describeStr));
		}
		
		if(action.equals("EnTransToNum")) {
			String str = "" , func = "";
			str = request.getParameter("wordtotransNum");
			func = request.getParameter("func");
			int OrgId = 0;
			if (session.getAttribute("OrgId") != null) {
				OrgId = (Integer)session.getAttribute("OrgId");
			}
			if(func.equals("GOOGLE")) { // google翻譯後轉成英文再轉成數字
				try {
					DescribeVO vo = service.englishToNumber(1 ,service.googleTranslate(str) ,OrgId); 
					out.print(vo.getNumberStr()+"#"+vo.getContent());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(func.equals("ENNUM")) {
				DescribeVO vo = service.englishToNumber(2 ,str ,OrgId); 
				out.print(vo.getNumberStr());
			}
		}
		
		// 購買號碼
		if(action.equals("Buy")) {
			int OrgId = 0;
			if (session.getAttribute("OrgId") != null) {
				OrgId = (Integer)session.getAttribute("OrgId");
			}
			String str = "";
			str = request.getParameter("value");
			if (service.isSixNumber(str).getBoolean("sum")) { // 判斷號碼是6個
				out.print(service.buyLotto(OrgId, str));
			} else {
				out.print(service.isSixNumber(str));
			}
		}
		
		if(action.equals("fastRandomBuy")) {
			int OrgId = 0;
			if (session.getAttribute("OrgId") != null) {
				OrgId = (Integer)session.getAttribute("OrgId");
			}
			int i = 0;
			i = Integer.parseInt(request.getParameter("value"));
			out.print(service.fastRandomBuy(OrgId, i));
		}
		
		if(action.equals("Remove")) {
			String str1 = "" , str2 = "";
			str1 = request.getParameter("value");
			str2 = request.getParameter("removevalue");
			out.print(service.removeNumber(str1 ,str2));
		}
	}

}
