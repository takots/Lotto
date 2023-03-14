package com.lotto.model;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface LottoService {
	// 隨機產生6個號碼
	public String randomSixNumber();

	// 產生今日樂透號碼 01~49
	public String generateTodayLotto();

	// 產生今日特別號 01~08
	public String generateTodaySpecial();

	// 買一組號碼
	public boolean buyLotto(Integer OrgId, String str);

	// 檢查是不是6個號碼
	public JSONObject isSixNumber(String str);

	// 找今日樂透號碼 + 特別號
	public String selectTodayLotto();

	// 八點開獎
	public boolean timeup() throws ParseException;

	// 兌獎
	public void goLotto();

	// 計算每個獎項有誰
	public void countNumbers();

	// 協助使用者補滿6個號碼
	public String replenishWithRandom(String chooseBall);

	// 每次的選號畫面新增或移除號碼
	public void saveSessionBall(HttpSession session, String ball, String calculate);

	// 存解夢數據用
	public void dreamData(String filepath) throws Exception;

	// 谷歌翻譯
	public String googleTranslate(String str) throws Exception;

	// 將英文名詞轉成數字
	public JSONObject englishToNumber(Integer type, String str, Integer OrgId);

	// 一段描述比對解夢號碼
	public String dreamNumber(Integer OrgId, String str);

	// 購買號碼列表
	public List<LottoVO> selectBetWhichDay(String today, String orgid);

	// 所有描述的列表
	public List<DescribeVO> selectAllDescribe(String OrgId);

	// 速選 n 組號碼
	public List<String> fastRandomBuy(Integer OrgId, Integer x);

	// 排除方式
	public JSONObject removeNumber(String str1, String str2);
}
