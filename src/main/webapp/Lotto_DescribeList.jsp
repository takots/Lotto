<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.common.Html"%>
<%@ page import="com.common.Date"%>
<%@ page import="com.lotto.model.DescribeVO"%>
<%@ page import="com.lotto.model.LottoService,com.lotto.model.LottoServiceImpl"%>
<style>
	.describeCards{
		padding : 10px;
		background-color: black;
		color: white;
		border-radius: 15px;
	}
	.desdiv1{
		text-align: center;
		color: #6009f0;
	}
	.desdiv2{
		text-align: center;
		color: #6009f0;
	}
	.desdiv3{
		text-align: center;
		color: #6009f0;
	}
	.desdiv4{
		text-size: 10px;
		text-align: center;
		color: #6009f0;
	}
</style>
<% 
Html Html = new Html();
out.print(Html.Start("描述列表")); 
Date date = new Date();
String today = date.ToDay();
LottoService service = new LottoServiceImpl();
List<DescribeVO> list = new ArrayList();
String OrgId = "";
if(session.getAttribute("OrgId") != null) {
	OrgId = session.getAttribute("OrgId").toString();
	if(!OrgId.equals("")) list = service.selectAllDescribe(OrgId);
}


StringBuffer stringBuffer = new StringBuffer();
StringBuffer stringBufferContent = new StringBuffer();
int i = 0;
String content = "" , nnstr = "" , numberStr = "" , recdate = "";
String Nnstr = "" , NumberStr = "";

for (DescribeVO vo : list){
	i++;
	content   = vo.getContent();
	nnstr     = vo.getNnstr();
	numberStr = vo.getNumberStr();
	recdate   = Date.timestampToString(vo.getRecDate());
	Nnstr = "";
    NumberStr = "";
    if(!nnstr.equals("")){
    	int c = 0;
	    for(String s : nnstr.split(",")){
	    	c++;
	    	if(c==1){
		    	Nnstr += "<tr>";
		    	Nnstr += "<th>名詞</th>";
		    	Nnstr += "<th>數字</th>";
		    	Nnstr += "</tr>";
	    	}
	    	if(!s.equals("")){
	    		Nnstr += "<tr>";
	    		Nnstr += "<td>" + s.split(":")[0] + "</td>";
	    		Nnstr += "<td>" + s.split(":")[1] + "</td>";
		    	Nnstr += "</tr>";
	    	}
		}
    }
    if(!numberStr.equals("")){
	    for(String s : numberStr.split(",")){
	    	if(!s.equals("")){
	    		NumberStr += "<div class=\"user-list\" style=\"display:inline-block;\">"+s+"</div>&nbsp;&nbsp;";
	    	}
		}
    }
    
    stringBufferContent.append(Html.CreateAccordion_Bookstrap5(i+"" ,content ,recdate ,Nnstr ,"號碼： " + NumberStr  ,"1"));
}
stringBuffer.append(stringBufferContent.toString());
out.print(stringBuffer.toString());

stringBufferContent.delete(0, stringBufferContent.length());
stringBuffer.delete(0, stringBuffer.length());
out.print(Html.End()); 
%>
