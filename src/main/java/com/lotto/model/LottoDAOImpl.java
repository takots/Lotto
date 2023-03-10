package com.lotto.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.common.Date;
import com.common.Func;
import com.common.GoogleTranslate;

import core.sql.LinkSQL;

public class LottoDAOImpl implements LottoDAO {

	private Statement stmt = null;
	private ResultSet rs = null;
	private Connection conn = null;

	LinkSQL sqlserver = new LinkSQL();
	Date date = new Date();
	Func func = new Func();

	public void LinkToMySQL() {
		try {
			Class.forName(sqlserver.DB_DRIVER);
			conn = DriverManager.getConnection(sqlserver.DB_URL, sqlserver.USER, sqlserver.PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ReSetResult(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 釋放空間
	public void free() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String createTodayLotto(String str) {
		// str 是隨機產生的6個號碼
		LinkToMySQL();
		PreparedStatement pstmt = null;
		String today = date.ToDay();
		String SqlStr = "";
		String L_Lotto = "";
		try {
			// 先確認是否有今日資料
			SqlStr = "SELECT TOP 1 L_RecId ,L_Lotto FROM lo_main WHERE L_RecDate BETWEEN '" + today + " 00:00:00'"
					+ " AND '" + today + " 23:59:59'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				L_Lotto = rs.getString("L_Lotto");
			} else {
				// 無資料再新增
				SqlStr = "INSERT INTO lo_main (L_Lotto ,L_isOpen) VALUES (?,?)";
				pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
				pstmt.setString(1, "," + str + ",");
				pstmt.setInt(2, 1); // L_isOpen 已開獎
				pstmt.executeUpdate();
				pstmt.close();
				L_Lotto = str;
				System.out.println("todayLotto SqlStr> " + SqlStr);
			}
			System.out.println("L_Lotto> " + L_Lotto);
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
		System.out.println("L_Lotto> " + L_Lotto);
		return L_Lotto;
	}

	@Override
	public void createTodaySpecial(String str) {
		LinkToMySQL();
		PreparedStatement pstmt = null;
		String today = date.ToDay();
		String SqlStr = "";
		String L_RecId = "";
		try {
			SqlStr = "SELECT TOP 1 L_RecId FROM lo_main WHERE L_RecDate BETWEEN '" + today + " 00:00:00'" + " AND '"
					+ today + " 23:59:59'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				L_RecId = rs.getString("L_RecId");
				SqlStr = "UPDATE lo_main SET L_Special=? WHERE L_RecId=" + L_RecId;
				pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
				pstmt.setString(1, str);
				pstmt.executeUpdate();
				pstmt.close();
				System.out.println("todaySpecial SqlStr> " + SqlStr);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
	}

	@Override
	public boolean insertUserBuyLotto(Integer OrgId, String str) {
		LinkToMySQL();
		String SqlStr = "";
		String today = date.ToDay();
		PreparedStatement pstmt = null;
		int L_RecId = 0;
		String L_isOpen = "";
		boolean isBuy = false;
		try {
			SqlStr = "SELECT TOP 1 L_RecId ,L_isOpen FROM lo_main where L_RecDate BETWEEN '" + today + " 00:00:00'"
					+ " AND '" + today + " 23:59:59'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				L_RecId = rs.getInt("L_RecId");
				L_isOpen = rs.getString("L_isOpen");
				if (L_isOpen.equals("1")) { // 已開獎就變成買下一期
					L_RecId += 1;
				}
			} else { // 八點前還沒產生號碼
				SqlStr = "SELECT TOP 1 L_RecId +1 as L_RecId FROM lo_main ORDER BY L_RecId desc";
				ReSetResult(SqlStr);
				if (rs.next()) {
					L_RecId = rs.getInt("L_RecId");
				}
				if (L_RecId == 0) {
					L_RecId = 1;
				}
			}
			SqlStr = "INSERT INTO lo_bet (B_LRecId ,B_Bet ,B_BuildOrg) VALUES (?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
			pstmt.setInt(1, L_RecId);
			pstmt.setString(2, "," + str + ",");
			pstmt.setInt(3, OrgId);
			pstmt.executeUpdate();
			pstmt.close();
			isBuy = true;
			System.out.println("buyLotto_v2 SqlStr> " + SqlStr);
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
		return isBuy;
	}

	@Override
	public String queryTodayLotto() {
		LinkToMySQL();
		Date date = new Date();
		String today = date.ToDay();
		String SqlStr = "";
		String L_Lotto = "", L_Special = "";
		try {
			SqlStr = "SELECT TOP 1 L_RecId ,L_Lotto ,L_Special FROM lo_main WHERE L_RecDate BETWEEN '" + today
					+ " 00:00:00'" + " AND '" + today + " 23:59:59'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				L_Lotto = rs.getString("L_Lotto");
				L_Special = rs.getString("L_Special");
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
		System.out.println("selectTodayLotto> " + L_Lotto + "#" + L_Special);
		return L_Lotto + "#" + L_Special;
	}

	@Override
	public void claimLotto() {
		LinkToMySQL();
		String SqlStr = "";
		PreparedStatement pstmt = null;
		String L_RecId = "", L_Lotto = "", L_Special = "";
		String B_RecId = "", B_Win = "", B_Bet = "", B_Special = "0";
		Date date = new Date();
		String today = date.ToDay();
		int count = 0, B_Winnum = 0;
		try {
			SqlStr = " SELECT L_RecId ,L_Lotto ,L_Special ,B_RecId ,B_Bet FROM lo_main ,lo_bet WHERE L_RecId = B_LRecId"
					+ " AND L_RecDate BETWEEN '" + today + " 00:00:00'" + " AND '" + today + " 23:59:59'"
					+ " AND B_RecDate BETWEEN '" + today + " 00:00:00'" + " AND '" + today + " 23:59:59'";
			ReSetResult(SqlStr);
			// 兌獎
			while (rs.next()) {
				L_RecId = rs.getString("L_RecId");
				L_Lotto = rs.getString("L_Lotto");
				L_Lotto = L_Lotto.substring(1, L_Lotto.length() - 1);
				L_Special = rs.getString("L_Special"); // 1,0
				B_RecId = rs.getString("B_RecId");
				B_Bet = rs.getString("B_Bet");
				B_Bet = B_Bet.substring(1, B_Bet.length() - 1);
				if (!L_Lotto.equals("") && !B_Bet.equals("")) {
					for (String v : B_Bet.split(",")) {
						count++;
						for (String s : L_Lotto.split(",")) {
							if (v.equals(s)) {
								B_Winnum++;
								B_Win += "," + v;
							}
						}
						if (v.equals(L_Special))
							B_Special = "1"; // 特別號
					}
					B_Win = func.reSort(B_Win) + ",";
				} else {
					B_Win = "";
					B_Winnum = 0;
				}
				SqlStr = "UPDATE lo_bet SET B_LRecId=? ,B_Win=? ,B_Winnum=? ,B_Special=? WHERE B_RecId=" + B_RecId;
				pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
				pstmt.setInt(1, Integer.parseInt(L_RecId));
				pstmt.setString(2, B_Win);
				pstmt.setInt(3, B_Winnum);
				pstmt.setString(4, B_Special);
				pstmt.executeUpdate();
				pstmt.close();
				System.out.println("timeupGoLotto SqlStr> " + SqlStr);
				// 重置
				B_Winnum = 0;
				B_Win = "";
				B_Special = "0";
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
	}

	@Override
	public void updateLottoCount() {
		LinkToMySQL();
		String SqlStr = "";
		PreparedStatement pstmt = null;
		Date date = new Date();
		String today = date.ToDay();
		String getStr[] = { "One", "Two", "Three", "Four", "Five", "Six" };
		String arr[] = { "", "", "", "", "", "" };
		String L_RecId = "", B_RecId = "", B_Winnum = "";
		try {
			/*
			 * 數每組使用者號碼共中了幾個數字 要使用此方法的需要滿足兩個條件 1. 已存在今日號碼 2. 有人購買
			 */
			SqlStr = " SELECT B_RecId ,B_LRecId ,B_Winnum FROM lo_main ,lo_bet"
					+ " WHERE lo_main.L_RecId = lo_Bet.B_LRecId" + " AND (B_RecDate BETWEEN '" + today + " 00:00:00'"
					+ " AND '" + today + " 23:59:59')";
			ReSetResult(SqlStr);
			System.out.println("letsCount SqlStr> " + SqlStr);
			while (rs.next()) {
				L_RecId = rs.getString("B_LRecId");
				B_RecId = rs.getString("B_RecId");
				B_Winnum = rs.getString("B_Winnum");
				System.out.println("B_RecId> " + B_RecId + " , " + "B_Winnum> " + B_Winnum);
				switch (B_Winnum) {
				case "1":
					arr[0] += B_RecId + ",";
					break;
				case "2":
					arr[1] += B_RecId + ",";
					break;
				case "3":
					arr[2] += B_RecId + ",";
					break;
				case "4":
					arr[3] += B_RecId + ",";
					break;
				case "5":
					arr[4] += B_RecId + ",";
					break;
				case "6":
					arr[5] += B_RecId + ",";
					break;
				default:
					break;
				}
			}
//			System.out.println(Arrays.toString(arr));
//			System.out.println("arr.length> " + arr.length);
			/*
			 * 不存在上述兩個條件 ,則select 不到 L_RecId 造成下面這句sql 去更新的過程中報錯 所以加上
			 * if(!L_RecId.equals(""))
			 */
			if (!L_RecId.equals("")) {
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i + " > " + arr[i]);
					SqlStr = "UPDATE lo_main SET L_get" + getStr[i] + "=? WHERE L_RecId=" + L_RecId;
					pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
					if (!arr[i].equals("")) {
						pstmt.setString(1, "," + arr[i]);
					} else {
						pstmt.setString(1, "''");
					}
					System.out.println("pstmt> " + pstmt.toString());
					pstmt.executeUpdate();
					pstmt.close();
					System.out.println("letsCount SqlStr> " + SqlStr);
				}
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
	}

	@Override
	public void saveDreamData(String str) throws Exception {
		LinkToMySQL();
		GoogleTranslate google = new GoogleTranslate();
		String SqlStr = "", titleStr = "";
		String str2 = "";
		String C_RecId = "", C_fileParentId = "";
		String cname = "", cno = "", number = "", name = "", transName = "";
		PreparedStatement pstmt = null;
		try {
			SqlStr = "SELECT TOP 1 C_RecId FROM lo_classdata WHERE C_NO='Dream'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				C_RecId = rs.getString("C_RecId");
			} else {
				// 無此資料新增第一筆
				SqlStr = "INSERT INTO lo_classdata (C_Name, C_NO) VALUES (?,?)";
				pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
				pstmt.setString(1, "做夢");
				pstmt.setString(2, "Dream");
				pstmt.executeUpdate();
				pstmt.close();

				SqlStr = "SELECT TOP 1 C_RecId FROM lo_classdata WHERE C_NO='Dream'";
				ReSetResult(SqlStr);
				if (rs.next()) {
					C_RecId = rs.getString("C_RecId");
				}
			}
			System.out.println("C_RecId> " + C_RecId);

			// 第一層
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonobject = jsonArray.getJSONObject(i);
				cname = jsonobject.get("cname").toString();
				cno = jsonobject.get("cno").toString();
				SqlStr = "INSERT INTO lo_classdata (C_Name, C_NO, C_ParentId) VALUES (?,?,?)";
				pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
				pstmt.setString(1, cname);
				pstmt.setString(2, cno);
				pstmt.setInt(3, Integer.parseInt(C_RecId));
				pstmt.executeUpdate();
				pstmt.close();
				System.out.println(cno + "> " + SqlStr);
				SqlStr = "SELECT TOP 1 C_RecId FROM lo_classdata WHERE C_NO='" + cno + "'";
				ReSetResult(SqlStr);
				if (rs.next()) {
					C_fileParentId = rs.getString("C_RecId");
				}

				// 第二層
				str2 = jsonobject.get("data").toString();
				JSONArray jsonArray2 = new JSONArray(str2);
				for (int j = 0; j < jsonArray2.length(); j++) {
					JSONObject jsonobject2 = jsonArray2.getJSONObject(j);
					name = jsonobject2.get("name").toString();
					transName = google.translate("zh-CN", "en", name);
					number = jsonobject2.get("number").toString();
					SqlStr = "INSERT INTO lo_classdata (C_Name, C_Number ,C_NO ,C_ParentId) VALUES (?,?,?,?)";
					pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
					pstmt.setString(1, name);
					pstmt.setInt(2, Integer.parseInt(number));
					pstmt.setString(3, transName);
					pstmt.setInt(4, Integer.parseInt(C_fileParentId));
					pstmt.executeUpdate();
					pstmt.close();
				}
			}
		} catch (SQLException se) {
			System.out.println("transName> " + transName); // 截斷 sql varchar 開大點
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}

	}

	@Override
	public String insertDream(Integer OrgId, String str) {
		LinkToMySQL();
		String SqlStr = "";
		String nameAndNumber = "";
		String numStr = "";
		String name = "", enname = "", number = "";
		PreparedStatement pstmt = null;
		boolean isNext = false;
//		System.out.println("------------ findByDream ------------");
//		System.out.println(str);
		try {
			// 0304 新增比對英文 C_NO
			SqlStr = "SELECT C_Name ,C_Number ,C_NO from lo_classdata";
			ReSetResult(SqlStr);
			while (rs.next()) {
				name = rs.getString("C_Name");
				enname = rs.getString("C_NO");
				number = rs.getString("C_Number");
				if (str.indexOf(name) > -1) {
					isNext = true;
					numStr += number + ",";
					nameAndNumber = nameAndNumber + "," + name + ":" + number;
				} else if (str.indexOf(enname) > -1) { // 區分 nameAndNumber
					isNext = true;
					numStr += number + ",";
					nameAndNumber = nameAndNumber + "," + enname + ":" + number;
				}
			}
			numStr = func.reSort(numStr);
			if (isNext) {
				nameAndNumber = nameAndNumber + ",";
				numStr = numStr + ",";
			}
			System.out.println("numStr> " + numStr);
			System.out.println("nameAndNumber> " + nameAndNumber);
			SqlStr = "INSERT INTO lo_describe (D_Content ,D_Type ,D_NNStr ,D_NumberStr ,D_BuildOrg) VALUES (?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
			pstmt.setString(1, str);
			pstmt.setInt(2, 3);
			pstmt.setString(3, nameAndNumber);
			pstmt.setString(4, numStr);
			pstmt.setInt(5, OrgId);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
		if (!numStr.equals("")) {
			numStr = numStr.substring(0, numStr.length() - 1);
		}
		return numStr;
	}

	@Override
	public void saveEnglishAndNumber(DescribeVO vo) {
		LinkToMySQL();
		String SqlStr = "";
		PreparedStatement pstmt = null;
		try {
			SqlStr = "INSERT INTO lo_describe (D_Type ,D_Content ,D_NNStr ,D_NumberStr ,D_BuildOrg) VALUES (?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
			pstmt.setInt(1, vo.getType());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getNnstr() + ",");
			pstmt.setString(4, vo.getNumberStr() + ",");
			pstmt.setInt(5, vo.getBuildOrg());
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println(vo);
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			sqlserver.closeResource(conn, rs, stmt, pstmt);
		}
	}

	@Override
	public List<LottoVO> findAllBet(String day, String orgid) {
		LinkToMySQL();
		List<LottoVO> list = new ArrayList<LottoVO>();
		LottoVO vo = null;
		String SqlStr = "", DateStr = "";
		String L_RecId = "", L_Lotto = "";
		System.out.println("day> " + day);
		System.out.println("orgid> " + orgid);
		try {
			SqlStr = "SELECT TOP 1 L_RecId ,L_Lotto FROM lo_main WHERE L_RecDate BETWEEN '" + day + " 00:00:00'"
					+ " AND '" + day + " 23:59:59'";
			ReSetResult(SqlStr);
			System.out.println("SqlStr> " + SqlStr);
			if (rs.next()) {
				L_RecId = rs.getString("L_RecId");
				L_Lotto = rs.getString("L_Lotto");
			}

			if (!day.equals("")) {
				DateStr = " AND ( B_RecDate BETWEEN '" + day + " 00:00:00'" + " AND '" + day + " 23:59:59' )";
			}
			if (L_RecId.equals("")) {
				SqlStr = " SELECT * FROM lo_bet WHERE B_BuildOrg = " + orgid + DateStr + " ORDER BY B_RecDate DESC";
				ReSetResult(SqlStr);
				System.out.println("SqlStr> " + SqlStr);
				while (rs.next()) {
					vo = new LottoVO();
					vo.setBet(rs.getString("B_Bet"));
					vo.setWin(rs.getString("B_Win"));
					vo.setWinnum(rs.getInt("B_Winnum"));
					vo.setRecDate(rs.getTimestamp("B_RecDate"));
					list.add(vo);
				}
			} else {
				SqlStr = " SELECT lo_bet.* ,L_Lotto FROM lo_bet ,lo_main WHERE lo_main.L_RecId=lo_bet.B_LRecId"
						+ DateStr + "AND B_BuildOrg = " + orgid + " ORDER BY B_RecDate DESC";
				ReSetResult(SqlStr);
				System.out.println("SqlStr> " + SqlStr);
				while (rs.next()) {
					vo = new LottoVO();
					vo.setLotto(rs.getString("L_Lotto"));
					vo.setBet(rs.getString("B_Bet"));
					vo.setWin(rs.getString("B_Win"));
					vo.setWinnum(rs.getInt("B_Winnum"));
					vo.setRecDate(rs.getTimestamp("B_RecDate"));
					list.add(vo);
				}
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
		return list;
	}

	@Override
	public List<DescribeVO> findByDream(String OrgId) {
		LinkToMySQL();
		List<DescribeVO> list = new ArrayList<DescribeVO>();
		DescribeVO vo = null;
		String SqlStr = "";
		try {
			SqlStr = " SELECT D_Content ,D_NNStr ,D_NumberStr ,D_RecDate FROM lo_describe WHERE D_Type = 3 AND D_BuildOrg = "
					+ OrgId + " ORDER BY D_RecId DESC";
			ReSetResult(SqlStr);
			while (rs.next()) {
				vo = new DescribeVO();
				vo.setContent(rs.getString("D_Content"));
				vo.setNnstr(rs.getString("D_NNStr"));
				vo.setNumberStr(rs.getString("D_NumberStr"));
				vo.setRecDate(rs.getTimestamp("D_RecDate"));
				list.add(vo);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
		return list;
	}

	@Override
	public void batchRandomBuy(Integer OrgId, List list) {
		LinkToMySQL();
		String today = date.ToDay();
		PreparedStatement pstmt = null;
		int L_RecId = 0;
		String L_isOpen = "";
		String SqlStr = "";
		try {
			SqlStr = "SELECT TOP 1 L_RecId ,L_isOpen FROM lo_main where L_RecDate BETWEEN '" + today + " 00:00:00'"
					+ " AND '" + today + " 23:59:59'";
			ReSetResult(SqlStr);
			if (rs.next()) {
				L_RecId = rs.getInt("L_RecId");
				L_isOpen = rs.getString("L_isOpen");
				if (L_isOpen.equals("1")) { // 已開獎就變成買下一期
					L_RecId += 1;
				}
			} else { // 八點前還沒產生號碼
				SqlStr = "SELECT TOP 1 L_RecId +1 as L_RecId FROM lo_main ORDER BY L_RecId desc";
				ReSetResult(SqlStr);
				if (rs.next()) {
					L_RecId = rs.getInt("L_RecId");
				}
				if (L_RecId == 0) {
					L_RecId = 1;
				}
			}
			SqlStr = "INSERT INTO lo_bet (B_LRecId ,B_Bet ,B_BuildOrg) VALUES (?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(SqlStr);
			for (int i = 0; i < list.size(); i++) {
				pstmt.setInt(1, L_RecId);
				pstmt.setString(2, "," + list.get(i) + ",");
				pstmt.setInt(3, OrgId);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			free();
		}
	}
}
