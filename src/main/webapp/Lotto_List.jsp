<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.common.Html"%>
<%@ page import="com.common.Date"%>
<%@ page import="com.lotto.model.LottoVO"%>
<%@ page import="com.lotto.model.LottoService,com.lotto.model.LottoServiceImpl"%>
<% 
Html Html = new Html();
out.print(Html.Start("號碼列表")); 
Date date = new Date();
String today = date.ToDay();
LottoService service = new LottoServiceImpl();
List<LottoVO> list = new ArrayList();
String OrgId = "";
if(session.getAttribute("OrgId") != null) {
	OrgId = session.getAttribute("OrgId").toString();
	if(!OrgId.equals("")) list = service.selectBetWhichDay(today ,OrgId);
}

// th
StringBuffer stringBufferTitle = new StringBuffer();
stringBufferTitle.append(Html.addTableTitle("項次"));
stringBufferTitle.append(Html.addTableTitle("你的號碼"));
stringBufferTitle.append(Html.addTableTitle("中獎號碼"));
stringBufferTitle.append(Html.addTableTitle("中獎數量"));
stringBufferTitle.append(Html.addTableTitle("購買時間"));

// td
StringBuffer stringBufferColumn = new StringBuffer();
StringBuffer stringBufferRow = new StringBuffer();
int i = 0;
String todayBet = "";
for (LottoVO vo : list){
	i++;
	String bet     = vo.getBet();
	String win     = vo.getWin();
	String winnum  = vo.getWinnum().toString();
	String recdate = vo.getRecDate().toString().replaceAll("-", "/").replace("T", " ");
	
    stringBufferColumn.append(Html.addTableColumn(i+""));
    for(String s : bet.split(",")){
    	if(!s.equals("")){
			todayBet += "<div class=\"user-list\" style=\"display:inline-block;\">"+s+"</div>&nbsp;&nbsp;";
    	}
	}
    stringBufferColumn.append(Html.addTableColumn(todayBet));
    stringBufferColumn.append(Html.addTableColumn(win));
    stringBufferColumn.append(Html.addTableColumn(winnum));
    stringBufferColumn.append(Html.addTableColumn(recdate));
    stringBufferRow.append(Html.addTableRow(stringBufferColumn.toString()));
    stringBufferColumn.delete(0, stringBufferColumn.length()); // 清空 stringBufferColumn
    todayBet = "";
}

// 放置內容
String thead = Html.addTableRow(stringBufferTitle.toString());
String tbody = stringBufferRow.toString();
out.print(Html.createTable(thead ,tbody));
out.print(Html.End()); 
%>
