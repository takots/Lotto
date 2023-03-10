<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.common.Html"%>
<%@ page import="com.lotto.model.LottoService,com.lotto.model.LottoServiceImpl"%>
<%
Html Html = new Html();
LottoService service = new LottoServiceImpl();
out.print(Html.Start("號碼首頁")); 
String str = "" , str2 = "", todayLotto = "";
boolean isAdmin = false;
if(session.getAttribute("amiadmin") != null) {
	isAdmin = Boolean.parseBoolean(session.getAttribute("amiadmin").toString());
	if(isAdmin) {
		str = service.selectTodayLotto();
		if(str.equals("#")){
			str = "";
		}else if(!str.equals("") && str.indexOf("#")>-1){
			str2 = str.split("#")[1];
			str = str.split("#")[0].substring(1, str.split("#")[0].length()-1);
			for(String s : str.split(",")){
				todayLotto += "<div class=\"newbtn-list\" style=\"display:inline-block;\">"+s+"</div>&nbsp;&nbsp;";
			}
			todayLotto += "<div class=\"specbtn\" style=\"display:inline-block;\">"+str2+"</div>&nbsp;&nbsp;";
		}
	}
}
%>
<script src="/Hello1221/js/lotto/lottofunc.js"></script>
<br>
<%if(isAdmin && str.equals("")){%>
	<button class="btn btn-outline-secondary" id="v-pills-generate-tab" onclick="CreateToDayFunc()">產生今日號碼<br>Generate Today's Number</button>
<%}else{%>
	<button class="btn btn-outline-secondary" id="v-pills-profile-tab" onclick="OpenListFunc('/Hello1221/Lotto_List.jsp')">今日號碼列表<br>Lotto List</button>
<!-- 	<button class="btn btn-outline-secondary" id="v-pills-profile2-tab" onclick="OpenEditFunc('/Hello1221/Lotto_Edit.jsp')">選擇你的號碼<br>Choose Your Number</button> -->
	<button class="btn btn-outline-secondary" id="v-pills-profile2-tab" onclick="OpenEditFunc2('/Hello1221/Lotto_Edit.jsp')">選擇你的號碼2<br>Choose Your Number2</button>
	<br><br><div class="" id="v-pills-home"><%=todayLotto %></div>
	<div class="" id="v-pills-profile"></div>
	<div class="" id="v-pills-profile2"></div>
<%}%>
<script>
$(document).ready(function() {
	OpenListFunc('/Hello1221/Lotto_List.jsp');
});
var isList = true , isEdit = true;
function gohome(){
	$("#iframe_edit").remove();
	$("#iframe_list").remove();
	isList = true;
	isEdit = true;
}

var El = document.getElementById('v-pills-profile');
function OpenListFunc(url){
	if(isList){
		$("#iframe_edit").remove();
		isList = false;
		isEdit = true;
		iframeEl = document.createElement('iframe');
		iframeEl.id  		  = 'iframe_list';
		iframeEl.sandbox      = 'allow-scripts allow-popups'; //允許在iframe裡使用js  
		iframeEl.width        = '100%'; //設定 寬度
		iframeEl.height       = '400px'; //設定 長度
		iframeEl.frameborder  = "0";    // 0為 不顯示邊框， 1為顯示編框
		iframeEl.scrolling    = "yes";
		iframeEl.src          = url;
		El.appendChild(iframeEl);
	}
}
var El2 = document.getElementById('v-pills-profile2');
function OpenEditFunc(url){
	if(isEdit){
		$("#iframe_list").remove();
		isEdit = false;
		isList = true;
		var iframeEl2 = document.createElement('iframe');
		iframeEl2.id  		  = 'iframe_edit';
		iframeEl2.sandbox     = 'allow-scripts allow-popups allow-modals'; 
		iframeEl2.width       = '100%';
		iframeEl2.height      = '2000px';
		iframeEl2.frameborder = "0";
		iframeEl2.scrolling   = "no";
		iframeEl2.src         = url;
		El2.appendChild(iframeEl2);
	}
}
function OpenEditFunc2(url){
	window.open(url,'_self','');
}
</script>
<%
out.print(Html.End()); 
%>