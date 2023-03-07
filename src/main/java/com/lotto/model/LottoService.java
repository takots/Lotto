package com.lotto.model;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public interface LottoService {
	// 隨機產生6個號碼
	public String randomSixNumber();

	// 產生今日樂透號碼 01~49
	public String TODAY();

	// 產生今日特別號 01~08
	public String TODAYSPECIAL();

	// 買一組號碼
	public boolean BUY(Integer OrgId, String str);

	// 檢查是不是6個號碼
	public JSONObject buySixNumber(String str);

	// 找今日樂透號碼 + 特別號
	public String SELECTODAY();

	// 八點開獎
	public boolean TIMEUP() throws ParseException;

	// 兌獎
	public void GOLOTTERY();

	// 計算每個獎項有誰
	public void countHowManyNumbers();

	// 協助使用者補滿6個號碼
	public JSONObject replenishWithRandom(String chooseBall);

	// 每次的選號畫面新增或移除號碼
	public JSONObject saveSessionBall(HttpSession session, String ball, String calculate);

	// 存解夢數據用
	public void dreamData(String filepath) throws Exception;

	// 谷歌翻譯
	public String googleTranslate(String str) throws Exception;

	// 將英文名詞轉成數字
	public DescribeVO enTONum(Integer type ,String str ,Integer OrgId);

	// 一段描述比對解夢號碼
	public String dreamNumber(Integer OrgId, String str);
	
	// 購買號碼列表
	public List<LottoVO> selectBetWhichDay(String today ,String orgid);
	
	public List<DescribeVO> selectAllDescribe(String OrgId);
	
	public List<String> FastRandomBuy(Integer OrgId ,Integer x);
}
