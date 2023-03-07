package com.lotto.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.common.GoogleTranslate;
import com.common.Date;
import com.common.Func;

public class LottoServiceImpl implements LottoService {
	public static String ran; // 隨機產生數字
	public static String dre; // 解夢
	public static String ennum; // 英轉數
	private LottoDAO dao;
	Func func = new Func();

	public LottoServiceImpl() {
		dao = new LottoDAOImpl();
	}

	public String randomSixNumber() {
		String str = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for (int i = 1; i < 50; i++)
			list.add(i);
		Collections.shuffle(list); // 打亂
		for (int i = 1; i <= 6; i++)
			list2.add(list.get(i)); // 取6
		Collections.sort(list2); // 排序
		for (int i = 0; i < list2.size(); i++)
			str += list2.get(i) + ",";
		str = str.substring(0, str.length() - 1);
		return str;
	}

	@Override
	public String TODAY() {
		String str = randomSixNumber();
		return dao.todayLotto(str);
	}

	@Override
	public String TODAYSPECIAL() {
		String str = randomSixNumber();
		ArrayList<Integer> list = new ArrayList<Integer>();
		str = dao.todayLotto(str);
		str = str.substring(1, str.length()-1);
		String arr[] = str.split(",");
		for (int i = 1; i < 50; i++)
			list.add(i);
		for (int i = 0; i < 6; i++) {
			list.remove(Integer.parseInt((arr[i])) - (i + 1));
		}
		System.out.println("special> " + list.toString());
		Collections.shuffle(list); // 打亂
		str = list.get(0).toString();
		System.out.println("today special> " + str);
		dao.todaySpecial(str);
		return str;
	}

	@Override
	public boolean BUY(Integer OrgId, String str) {
		return dao.buyLotto_v2(OrgId, str);
	}

	@Override
	public JSONObject buySixNumber(String str) {
		int count = 0;
		JSONObject jsonobject = new JSONObject();
		if (!str.equals("")) {
			for (String s : str.split(",")) {
				count++;
			}
		}
		System.out.println("count> " + count);
		if (count > 6) {
			jsonobject.put("sum", false);
			jsonobject.put("errormsg", "超過了，你需要選擇 6 個號碼，你現在是 " + count + " 個號碼");
		} else if (count < 6) {
			jsonobject.put("sum", false);
			jsonobject.put("errormsg", "太少了，你需要選擇 6 個號碼，你現在是 " + count + " 個號碼");
		} else {
			jsonobject.put("sum", true);
		}
		return jsonobject;
	}

	@Override
	public String SELECTODAY() {
		return dao.selectTodayLotto();
	}

	@Override
	public boolean TIMEUP() throws ParseException {
		Date date = new Date();
		if (date.WhatTime("20:00:00")) {
			System.out.println("八點了");
			GOLOTTERY();
		}
		System.out.println("還沒八點");
		return date.WhatTime("20:00:00");
	}

	@Override
	public void GOLOTTERY() {
		dao.timeupGoLotto();
	}

	@Override
	public void countHowManyNumbers() {
		dao.letsCount();
	}

	@Override
	public JSONObject replenishWithRandom(String sessChooseBall) {
		String str = "";
		ran = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		Map<String, String> map = new HashMap<>();
		for (int i = 1; i < 50; i++)
			list.add(i);
		// 單次選取超出6個 會有錯
		int num = 0;
		// session 存的是單次選取的值 ,這邊的行為是去除已存在
		System.out.println("------- ReplenishWithRandom --------");
		System.out.println("sessChooseBall> " + sessChooseBall);
		if (sessChooseBall.equals(","))
			sessChooseBall = "";
		if (!sessChooseBall.equals("")) {
			if (sessChooseBall.startsWith(","))
				sessChooseBall = sessChooseBall.substring(1, sessChooseBall.length());
			if (sessChooseBall.endsWith(","))
				sessChooseBall = sessChooseBall.substring(0, sessChooseBall.length() - 1);

			String arr[] = sessChooseBall.split(",");
			System.out.println("arr> " + Arrays.toString(arr));
			for (int i = 0; i < arr.length; i++) {
				num++;
				list.remove(Integer.parseInt((arr[i])) - (i + 1));
			}
		}
		System.out.println(list.toString());

		if (num < 6) {
			Collections.shuffle(list); // 打亂
			for (int i = 1; i <= 6 - num; i++)
				list2.add(list.get(i));
			Collections.sort(list2); // 排序
			for (int i = 0; i < list2.size(); i++)
				str += list2.get(i) + ",";
			str = str.substring(0, str.length() - 1);
			str = "," + str;
		}
		ran = str + ",";
//		sysout("chooseBall> " + chooseBall);
		map.put("randomBall", str);
		map.put("chooseBall", "," + sessChooseBall);
		map.put("dreamBall" , dre);
		map.put("ennumBall" , ennum);
		JSONObject response = new JSONObject(map);
		return response;
	}

	@Override
	public JSONObject saveSessionBall(HttpSession session, String ball, String calculate) {
		Map<String, String> map = new HashMap<>();
		System.out.println("-------- SaveSessionBall --------");
		System.out.println("ran> " + ran);
		// 存給 LotteryServlet_UserRandom 隨機產生 n 個 把已選的去除掉
		String sesscountBall = "", sesschooseBall = "";
		Object chooseball = session.getAttribute("ChooseBall");
		if (chooseball != null) {
			sesschooseBall = chooseball.toString();
		}
		if (!sesschooseBall.startsWith(",")) {
			sesschooseBall = "," + sesschooseBall;
		}
		// 按完會 新增(plus) 或 移除(minus) 號碼
		ball = ball + ",";
		if (calculate.equals("plus")) {
			session.setAttribute("ChooseBall", sesschooseBall + ball);
		} else if (calculate.equals("minus")) {
			ball = "," + ball;
			System.out.println("ball> " + ball);
			sesschooseBall = sesschooseBall.replace(ball, ","); // 取消要去掉
			session.setAttribute("ChooseBall", sesschooseBall);
			ran = ran.replace(ball, ",");
			dre = dre.replace(ball, ",");
			ennum = ennum.replace(ball, ",");
		}
		chooseball = session.getAttribute("ChooseBall");
		if (chooseball != null) {
			sesschooseBall = chooseball.toString();
		}
		if (sesschooseBall.endsWith(",")) {
			session.setAttribute("CountBall",
					sesschooseBall.substring(0, sesschooseBall.length() - 1).split(",").length);
		} else if (sesschooseBall.equals(",")) {
			session.setAttribute("CountBall", 0);
		}

		Object countball = session.getAttribute("CountBall");
		if (countball != null) {
			sesscountBall = countball.toString();
		}
		System.out.println("sesscount> " + sesscountBall);
		System.out.println("sesschooseBall> " + sesschooseBall);
		if (ran != null) {
			map.put("randomBall", ran);
		}
		map.put("randomBall", ran);
		map.put("chooseBall", sesschooseBall);
		map.put("dreamBall" , dre);
		map.put("ennumBall" , ennum);
		JSONObject response = new JSONObject(map);
		return response;
	}

	@Override
	public void dreamData(String filepath) throws Exception {
		File file = new File(filepath);
		String str = "", str2 = "", titleStr = "", fileName = "";
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String s;
		while ((s = bufferedReader.readLine()) != null) {
			str += s;
		}
		bufferedReader.close();
		fileReader.close();
		dao.SaveDreamData(str);
	}

	@Override
	public String googleTranslate(String str) throws Exception {
		GoogleTranslate google = new GoogleTranslate();
		String result = google.translate("zh-CN", "en", str);
		return result;
	}

	@Override
	public DescribeVO enTONum(Integer type, String str ,Integer OrgId) {
		DescribeVO vo = new DescribeVO();
		String str0 = str;
		String D_NNStr = "";
		str = str.toLowerCase();
		Map<String, String> map = new HashMap<>();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < alphabet.length; i++) {
			map.put(alphabet[i] + "", i + 1 + "");
			map.put(i + 1 + "" ,alphabet[i] + "");
		}
		String result = "";
		for (String s : str.split("")) {
			if (map.containsKey(s)) {
				result += "," + map.get(s);
			}
		}
		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		result = func.reSort(result);
		for (String s : result.split(",")) {
			if (map.containsKey(s)) {
				D_NNStr += "," + s + ":" + map.get(s);
			}
		}
		vo.setType(type);
		vo.setContent(str0);
		vo.setNnstr(D_NNStr);
		vo.setNumberStr(result);
		vo.setBuildOrg(OrgId);
//		System.out.println(vo);
		dao.SaveEnNum(vo);
		ennum = result;
		map.clear();
		return vo;
	}

	@Override
	public String dreamNumber(Integer OrgId, String str) {
		dre = dao.insertDream(OrgId, str);
		return dre;
	}

	@Override
	public List<LottoVO> selectBetWhichDay(String today ,String orgid) {
		return dao.findAllBet(today ,orgid);
	}

	@Override
	public List<DescribeVO> selectAllDescribe(String OrgId) {
		return dao.findByDream(OrgId);
	}

	@Override
	public List<String> FastRandomBuy(Integer OrgId ,Integer x) {
		List<String> list = new ArrayList<>();
		for(int i=1 ;i<=x ;i++) {
			list.add(randomSixNumber());
		}
		dao.batchRandomBuy(OrgId, list);
		return list;
	}
}
