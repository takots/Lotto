package com.lotto.model;

import static org.hamcrest.CoreMatchers.nullValue;

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

import lombok.val;

import com.common.Date;
import com.common.Func;

public class LottoServiceImpl implements LottoService {
	public static String ran; // 隨機產生數字
	public static String dre; // 解夢
	public static String googlennum; // google + 英轉數
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
	public String todayLotto() {
		String str = randomSixNumber();
		return dao.createTodayLotto(str);
	}

	@Override
	public String todaySpecial() {
		String str = randomSixNumber();
		ArrayList<Integer> list = new ArrayList<Integer>();
		str = dao.createTodayLotto(str);
		str = str.substring(1, str.length() - 1);
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
		dao.createTodaySpecial(str);
		return str;
	}

	@Override
	public boolean buyLotto(Integer OrgId, String str) {
		return dao.insertUserBuyLotto(OrgId, str);
	}

	@Override
	public JSONObject isSixNumber(String str) {
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
	public String selectTodayLotto() {
		return dao.queryTodayLotto();
	}

	@Override
	public boolean timeup() throws ParseException {
		Date date = new Date();
		if (date.WhatTime("20:00:00")) {
			System.out.println("八點了");
			goLotto();
		}
		System.out.println("還沒八點");
		return date.WhatTime("20:00:00");
	}

	@Override
	public void goLotto() {
		dao.claimLotto();
	}

	@Override
	public void countNumbers() {
		dao.updateLottoCount();
	}

	@Override
	public String replenishWithRandom(String sessChooseBall) {
		String str = "", sess = func.reSort(sessChooseBall);
		String str2 = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for (int i = 1; i < 50; i++)
			list.add(i);
		// 單次選取超出6個 會有錯
		// session 存的是單次選取的值 ,這邊的行為是去除已存在
		int num = 0;
		if (!sess.equals(""))
			str2 += sess + ",";
		if (dre != null)
			str2 += dre + ",";
		if (googlennum != null)
			str2 += googlennum + ",";
		if (ennum != null)
			str2 += ennum + ",";

		str2 = func.reSort(str2);
		str2 = str2.substring(1, str2.length());
		String arr[] = str2.split(",");
		System.out.println("str2> " + str2);
		System.out.println("arr> " + Arrays.toString(arr));
		System.out.println("arr.length> " + arr.length);
		System.out.println("list4> " + list.toString());

		if (arr.length < 6) {
			for (int i = 0; i < arr.length; i++) {
				num++;
				list.remove(Integer.parseInt((arr[i])) - (i + 1));
			}
			Collections.shuffle(list); // 打亂
			for (int i = 1; i <= 6 - num; i++)
				list2.add(list.get(i));
			Collections.sort(list2); // 排序
			for (int i = 0; i < list2.size(); i++)
				str += list2.get(i) + ",";
			ran = str;
			str2 += "," + str;
		}
		System.out.println("str2> " + str2);
		System.out.println("str> " + str);
		return str2;
	}

	@Override
	public void saveSessionBall(HttpSession session, String ball, String calculate) {
		// 存給 LotteryServlet_UserRandom 隨機產生 n 個 把已選的去除掉
		String count = "", choose = "";
		Object chooseball = session.getAttribute("ChooseBall");
		if (chooseball != null) {
			choose = chooseball.toString();
		}
//		System.out.println("choose> " + choose);
		// 如果數字前面沒有逗號 ,移除的時候會無法移除
		if (!choose.startsWith(",")) { 
			choose = "," + choose;
		}
		
		// 按完會 新增(plus) 或 移除(minus) 號碼
		ball = ball + ",";
		if (calculate.equals("plus")) {
			session.setAttribute("ChooseBall", choose + ball);
		} else if (calculate.equals("minus")) {
			ball = "," + ball;
			System.out.println("ball> " + ball);
			choose = choose.replace(ball, ","); // 取消要去掉
			session.setAttribute("ChooseBall", choose);
		}
		chooseball = session.getAttribute("ChooseBall");
		if (chooseball != null) {
			choose = chooseball.toString();
		}
		if(choose.equals(",")) choose = "";
		// 計算目前自選號有幾顆
		if(choose.equals("")) { 
			session.setAttribute("CountBall", 0);
		}else {
			int j=0;
			for(String s : choose.split(",")) {
				if(!s.equals("")) j++;
			}
			session.setAttribute("CountBall", j);
			Object countball = session.getAttribute("CountBall");
			if (countball != null) {
				count = countball.toString();
			}
		}
//		System.out.println("choose2> " + choose);
//		System.out.println("length> " + count);
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
		dao.saveDreamData(str);
	}

	@Override
	public String googleTranslate(String str) throws Exception {
		GoogleTranslate google = new GoogleTranslate();
		String result = google.translate("zh-CN", "en", str);
		return result;
	}

	@Override
	public DescribeVO englishToNumber(Integer type, String str, Integer OrgId) {
		DescribeVO vo = new DescribeVO();
		String str0 = str;
		String D_NNStr = "";
		str = str.toLowerCase();
		Map<String, String> map = new HashMap<>();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < alphabet.length; i++) {
			map.put(alphabet[i] + "", i + 1 + "");
			map.put(i + 1 + "", alphabet[i] + "");
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
		dao.saveEnglishAndNumber(vo);
		if (type == 1) {
			googlennum = result;
		} else if (type == 2) {
			ennum = result;
		}
		map.clear();
		return vo;
	}

	@Override
	public String dreamNumber(Integer OrgId, String str) {
		dre = dao.insertDream(OrgId, str);
		return dre;
	}

	@Override
	public List<LottoVO> selectBetWhichDay(String today, String orgid) {
		return dao.findAllBet(today, orgid);
	}

	@Override
	public List<DescribeVO> selectAllDescribe(String OrgId) {
		return dao.findByDream(OrgId);
	}

	@Override
	public List<String> fastRandomBuy(Integer OrgId, Integer x) {
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= x; i++) {
			list.add(randomSixNumber());
		}
		dao.batchRandomBuy(OrgId, list);
		return list;
	}

	@Override
	public JSONObject removeNumber(String str1, String str2) {
		Map<String, String> map = new HashMap<>();
//		System.out.println("str1> " + str1);
//		System.out.println("str2> " + str2);
		String str = "";
		if (!str1.equals("") && !str2.equals("")) {
			str1 = "," + str1;
			for (String s : str1.split(",")) {
				if (!s.equals("")) {
					int i = Integer.parseInt(s);
					// 排除奇數
					if (str2.indexOf("1") > -1) {
						if (i % 2 == 1) {
							str1 = str1.replace("," + s + ",", ",");
							str += s + ",";
						}
					}

					// 排除偶數
					if (str2.indexOf("2") > -1) {
						if (i % 2 == 0) {
							str1 = str1.replace("," + s + ",", ",");
							str += s + ",";
						}
					}

					// 排除3的倍數
					if (str2.indexOf("3") > -1) {
						if (i % 3 == 0) {
							str1 = str1.replace("," + s + ",", ",");
							str += s + ",";
						}
					}

					// 排除質數
					if (str2.indexOf("4") > -1) {
						boolean prime = true;
						for (int n = 2; n < i; n++) {
							if (i % n == 0) {
								prime = false; // 非質數傳false
								break;
							}
						}
						if (prime) {
							str1 = str1.replace("," + s + ",", ",");
							str += s + ",";
						}
					}
				}
			}
		}
		if (str.lastIndexOf(",") > -1)
			str = str.substring(0, str.lastIndexOf(","));
		if (str1.equals(",")) {
			str1 = "";
		} else {
			str1 = str1.substring(1, str1.length() - 1);
		}
		map.put("remove", str);
		map.put("keep", str1);
		JSONObject response = new JSONObject(map);
//		System.out.println("str> " + str);
//		System.out.println("str1> " + str1);
		return response;
	}
}
