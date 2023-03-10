package com.lotto.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lotto.model.LottoDAO;
import com.lotto.model.LottoDAOImpl;
import com.lotto.model.LottoService;
import com.lotto.model.LottoServiceImpl;

public class LottoTest {
	private LottoDAO dao;
	private LottoService service;

	/*
	 *  執行順序
	 *  1. todayLottery
	 *  2. todaySpecial
	 *  3. buyLottery_v2 可以執行很多次(購買一組號碼)
	 *  4. timeupGoLottery 兌獎
	 */
	
	@BeforeEach
	public void before() {
		dao = new LottoDAOImpl();
		service = new LottoServiceImpl();
	}

	@AfterEach
	public void after() {
		dao = null;
		service = null;
	}

	@Test
	public void todayLottery() {
		assertNotNull(service.todayLotto());
	}

	@Test
	public void todaySpecial() {
		assertNotNull(service.todaySpecial());
	}

	@Test
	public void buyLottery_v2() { // sess ,str
		for(int i=0 ;i<=5 ; i++) {
			assertTrue(service.buyLotto(3,service.randomSixNumber()));
		}
	}

	@Test
	public void BuySixNumber() {
		String str = "5,13,39";
		System.out.println(service.isSixNumber(str));
	}

	@Test
	public void selectTodayLottery() {
		assertNotNull(service.selectTodayLotto());
	}

	@Test
	public void timeup() throws ParseException {
		assertFalse(service.timeup());
	}

	@Test
	public void timeupGoLottery() {
		service.goLotto();
	}

	@Test
	public void letsCount() {
		service.countNumbers();
	}

	@Test
	public void ReplenishWithRandom() {
		String str = "5,13,39";
		service.replenishWithRandom(str);
	}

	@Test
	public void checkDreamData() throws Exception {
		service.dreamData("D:\\springboot\\jsondata\\hello1221_data1.json");
	}

	@Test
	public void checkDreamData2() throws Exception {
		service.dreamData("D:\\springboot\\jsondata\\hello1221_data2.json");
	}

	@Test
	public void EnTONum() {
		service.englishToNumber(1, "wklewq" ,3);
	}
	
	@Test
	public void findByDream() { 
		String str = "sssss";
		System.out.println(service.dreamNumber(3,str));
	}
	
	@Test
	public void fastBuy() {
		List list = service.fastRandomBuy(50 ,5);
		System.out.println(list);
	}
}
