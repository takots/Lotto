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
	public String generateTodayLotto() {
		String str = randomSixNumber();
		return dao.createTodayLotto(str);
	}

	@Override
	public String generateTodaySpecial() {
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
	public String replenishWithRandom(String str) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for (int i = 1; i < 50; i++) list.add(i);
		int num = 0;
		String arr[] = str.split(",");

		if (arr.length < 6) {
			// str 是選取的值 ,這邊的行為是去除已存在
			for (int i = 0; i < arr.length; i++) {
				num++;
				list.remove(Integer.parseInt((arr[i])) - (i + 1));
			}
			Collections.shuffle(list); // 打亂
			// 剩幾個號碼要產生
			for (int i = 1; i <= 6 - num; i++) {
				list2.add(list.get(i));
			}
			Collections.sort(list2); // 排序
			str = ""; // 清除
			for (int i = 0; i < list2.size(); i++) {
				str += list2.get(i) + ",";
			}
			ran = str;
			str += "," + str;
		}
		return str;
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
	public JSONObject englishToNumber(Integer type, String str, Integer OrgId) {
		DescribeVO vo = new DescribeVO();
		String str0 = str;
		/* 1. 使用 StringBuilder 替代 String 串接：在每次將 String 串接時，Java 都會建立一個新的 String 物件，
		 * 因此當需要將多個 String 串接時，使用 StringBuilder 可以有效地減少記憶體使用量和提升效能
		 * 2. equals 方法比較字元是否相等這種方式比較慢 ,使用 charAt 方法可以更快速地取得字串中的字元
		 * */
		StringBuilder result = new StringBuilder();
		String nnstr = "";
		str = str.toLowerCase();
		JSONObject jsonobject = new JSONObject();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < alphabet.length; i++) {
            char c = alphabet[i];
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == c) {
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    result.append(i + 1);
                }
            }
		}
		str = func.reSort(result.toString());
		for(String s : str.split(",")) {
            if(!s.equals("")) nnstr += "," + s +":"+ alphabet[Integer.parseInt(s)-1];
		}
		if(!nnstr.equals("")) nnstr = nnstr + ",";
		
		vo.setType(type);
		vo.setContent(str0);
		vo.setNnstr(nnstr);
		vo.setNumberStr(str);
		vo.setBuildOrg(OrgId);
		dao.saveEnglishAndNumber(vo);
		
		jsonobject.put("content", str0);
		jsonobject.put("numberStr", str);
		if (type == 1) {
			googlennum = str;
		} else if (type == 2) {
			ennum = str;
		}
		return jsonobject;
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
