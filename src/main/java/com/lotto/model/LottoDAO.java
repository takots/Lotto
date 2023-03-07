package com.lotto.model;

import java.util.List;

public interface LottoDAO {
	// 產生今日號碼
	public String todayLotto(String str);

	// 今天特別號
	public void todaySpecial(String str);

	// x 目前已改使用v2 購買跟兌獎同時
	public LottoVO buyLotto(String str);

	// 購買一組號碼
	public boolean buyLotto_v2(Integer OrgId, String str);

	// 查今日樂透號碼與特別號
	public String selectTodayLotto();

	// 系統兌獎
	public void timeupGoLotto();

	// 每個獎有誰
	public void letsCount();

	// 存取 Data
	public void SaveDreamData(String str) throws Exception;

	// 根據描述找出對應的數字
	public String insertDream(Integer OrgId, String str);
	
	// 解夢列表
	public List<DescribeVO> findByDream(String OrgId);
	
	// 將英文轉成數字存起來
	public void SaveEnNum(DescribeVO vo);
	
	public List<LottoVO> findAllBet(String today ,String orgid);
	
	public void batchRandomBuy(Integer OrgId ,List list);
}
