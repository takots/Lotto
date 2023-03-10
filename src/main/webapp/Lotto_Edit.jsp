<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.common.Html"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="com.lotto.model.LottoService,com.lotto.model.LottoServiceImpl"%>
<%
LottoServiceImpl service = new LottoServiceImpl();
service.ran="";
service.dre="";
service.ennum="";
int sesscount = 0 , rancount = 0;
String sesschooseBall = "";
session.setAttribute("CountBall","0");
Object sesscountball = session.getAttribute("CountBall");
if(sesscountball != null) {
	sesscount = Integer.parseInt(sesscountball.toString());
}

session.setAttribute("ChooseBall","");
Object chooseball = session.getAttribute("ChooseBall");
if(chooseball != null) {
	sesschooseBall = chooseball.toString();
	if(!sesschooseBall.equals("")){
		sesschooseBall = sesschooseBall.substring(0,sesschooseBall.length()-1);
	}
}

// rancount = 6 - sesscount;
// out.print("sesscount> " + sesscount + "<br>");
// out.print("rancount> " + rancount + "<br>");
// out.print("sesschooseBall> " + sesschooseBall);
JSONObject jsonobject = (JSONObject)request.getAttribute("BuyLottery");
String bet = "" , win = "" , num = "";
if(jsonobject != null){
	out.print("jsonobject> " + jsonobject);
}
Html Html = new Html();
out.print(Html.Start("功能頁面")); 
out.print("起夢範例： Crocodile in 大貓的嘴巴裡 sparrow in sea, my old friend 的兒子在狗的背上<br>");
out.print("英數規則： 大小寫不拘 A:1 ,B:2 ,... ,Z:26<br>");
%>
<div class="input-group mb-3">
<button class="btn btn-outline-secondary" id="v-pills-home-tab" onclick="location.href='/Hello1221/Lotto_Menu.jsp'">首頁<br>Home</button>
<button class="btn btn-outline-secondary" id="v-pills-replenish-tab" onclick="ReplenishNumFunc()">補齊你個號碼<br>Replenish 6 Number</button>
</div>
<div class="" id="v-pills-replenish"></div>
<br>
<div class="input-group mb-3">
  	<input type="number" class="form-control" id="v-pills-fastbuy-tab" aria-label="Recipient's username" aria-describedby="button-fastbuy">
	<button class="btn btn-outline-secondary" id="button-fastbuy" onclick="FastBuyFunc()">速選n組號碼<br>Fast Buy n</button>
</div>
<div class="" id="v-pills-fastbuy"></div>
<br>
<div class="input-group mb-3">
  <input type="text" class="form-control" id="v-pills-letDream-tab" placeholder="範例: 我夢見我在一片草地上躺著 ,袋子裡面有麵包跟貓" aria-label="Recipient's username" aria-describedby="button-letDream">
  <button class="btn btn-outline-secondary" type="button" id="button-letDream" onclick="DreamComeNumFunc()" >起夢<br>analyze</button>
  <button class="btn btn-outline-secondary" id="v-pills-profile3-tab" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasWithBackdrop" aria-controls="offcanvasWithBackdrop" onclick="OpenEditFunc3('/Hello1221/Lotto_DescribeList.jsp')">夢的描述列表<br>My Dream List</button>
	<div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasWithBackdrop" aria-labelledby="offcanvasWithBackdropLabel">
	  <div class="offcanvas-header">
	    <h5 class="offcanvas-title" id="offcanvasWithBackdropLabel">描述列表</h5>
	    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
	  </div>
	  <div class="offcanvas-body" id="v-pills-profile3">
	  </div>
	</div>
</div>
<div class="" id="v-pills-letDream"></div>
<br>
<div class="input-group mb-3">
  <input type="text" class="form-control" id="v-pills-googleTranslate-tab" placeholder="請輸入中文 ,轉換成英文後會自動轉成數字顯示" aria-label="Recipient's username" aria-describedby="button-googleTranslate">
  <button class="btn btn-outline-secondary" type="button" id="button-googleTranslate" onclick="GoogleTransFunc()" >中翻英<br>google</button>
</div>
<div class="" id="v-pills-googleTranslate"></div>
<br>
<div class="input-group mb-3">
  <input type="text" class="form-control" id="v-pills-englishToNumber-tab" placeholder="輸入英文字母 ,重複的字母僅顯示一次" aria-label="Recipient's username" aria-describedby="button-englishToNumber">
  <button class="btn btn-outline-secondary" type="button" id="button-englishToNumber" onclick="TransNumFunc()" >英翻數<br>entonu</button>
</div>
<div class="" id="v-pills-englishToNumber"></div>
<%
String arr[] = {"odd 奇數","even 偶數","倍數 multiple of 3","prime 質數"};
for(int i = 0 ;i<4 ;i++) out.print(Html.Createcheckbox_Bookstrap5("remove"+(i+1) ,(i+1) ,arr[i]));
%>
<button class="btn btn-outline-secondary" id="v-pills-removenum-tab" onclick="RemoveFunc()">排除<br>remove</button>
<%
out.print(Html.createLottoBtn()); 
%>
<div class="" id="v-pills-resultBuy"></div>
<br>
<button class="btn btn-outline-secondary" id="v-pills-buy-tab" onclick="BuyFunc()">購買這組號碼<br>let's buy</button>
<!-- <div class="" id="v-pills-profile3"></div> -->
<script>
var El3 = document.getElementById('v-pills-profile3');
function OpenEditFunc3(url){
	var iframeEl3 = document.createElement('iframe');
	iframeEl3.id  		  = 'iframe_dream';
	iframeEl3.sandbox     = 'allow-scripts allow-popups allow-modals'; 
	iframeEl3.width       = '100%';
	iframeEl3.height      = '2000px';
	iframeEl3.frameborder = "0";
	iframeEl3.scrolling   = "no";
	iframeEl3.src         = url;
	El3.appendChild(iframeEl3);
}
</script>

<%
out.print(Html.JavaScriptFunc("<script src=\"/Hello1221/js/lotto/lottofunc2.js\"></script>")); 
out.print(Html.End()); 
%>