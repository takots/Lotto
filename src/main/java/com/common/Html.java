package com.common;

public class Html {
	public String Start(String title) {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<!DOCTYPE html>");
		stringbuffer.append(	"<html lang=\"en\">");
		stringbuffer.append(	"<head>");
		stringbuffer.append(		"<meta charset=\"UTF-8\">");
		stringbuffer.append(		"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		stringbuffer.append(		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		stringbuffer.append(		"<title>"+title+"</title>");
		// swal
		stringbuffer.append(		"<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@11\"></script>");
		stringbuffer.append(		"<script src=\"sweetalert2.all.min.js\"></script>");
		// ajax
		stringbuffer.append(		"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js\"></script>");
		// 自己的css
		stringbuffer.append(        "<link href=\"/Hello1221/css/lotto/ball.css\" rel=\"stylesheet\" type=\"text/css\">");
		stringbuffer.append(	"</head>");
		stringbuffer.append(	"<body>");
		// bootstrap5
		stringbuffer.append(	"<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0\" crossorigin=\"anonymous\">");
		stringbuffer.append(	"<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8\" crossorigin=\"anonymous\"></script>");
		stringbuffer.append(	"<div class=\"container\">");
		return stringbuffer.toString();
	}
	
	public String loginandsignupHtml(String Beauty ,String isAdmin) {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(Beauty);
		stringbuffer.append("<div class=\"form-structor\">");
		stringbuffer.append("	<div class=\"signup\">");
		stringbuffer.append("		<h2 class=\"form-title\" id=\"signup\"><span>or</span>Sign up</h2>");
		stringbuffer.append("		<div class=\"form-holder\">");
		stringbuffer.append("			<input id=\"signup_name\" type=\"text\" class=\"input\" placeholder=\"請輸入使用者名稱\" />");
		stringbuffer.append("			<input id=\"signup_email\" type=\"email\" class=\"input\" placeholder=\"請輸入Email\" />");
		stringbuffer.append("			<input id=\"signup_pwd\" type=\"password\" class=\"input\" placeholder=\"清輸入密碼\" />");
		stringbuffer.append("		</div>");
		stringbuffer.append("		<input type=\"button\" class=\"submit-btn\" value=\"Sign up\" onclick=\"SignUpFunc("+isAdmin+")\">");
		stringbuffer.append("	</div>");
		stringbuffer.append("	<div class=\"login slide-up\">");
		stringbuffer.append("		<div class=\"center\">");
		stringbuffer.append("			<h2 class=\"form-title\" id=\"login\"><span>or</span>Log in</h2>");
		stringbuffer.append("			<div class=\"form-holder\">");
		stringbuffer.append("				<input id=\"login_email\" type=\"email\" class=\"input\" placeholder=\"請輸入Email\" />");
		stringbuffer.append("				<input id=\"login_pwd\" type=\"password\" class=\"input\" placeholder=\"清輸入密碼\" />");
		stringbuffer.append("			</div>");
		stringbuffer.append("			<input type=\"button\" class=\"submit-btn\" value=\"Log in\" onclick=\"LoginFunc()\">");
		stringbuffer.append("		</div>");
		stringbuffer.append("	</div>");
		stringbuffer.append("</div>");
		return stringbuffer.toString();
	}
	
	/*
	 * ex: 
	 * out.print(Html.createAButton("btn btn-outline-secondary" ,"'/Hello1221/Lotto_List.jsp'" ,"列表"));
	 * out.print(Html.createAButton("btn btn-outline-secondary" ,"'/Hello1221/Lotto_Edit_v2.jsp'" ,"選擇你的號碼"));	
	 */
	public String createAButton(String className ,String url ,String content) {
		return "<input class=\""+className+"\" type=\"button\" onclick=\"location.href="+url+"\" value=\""+content+"\" />";
	}
	
	public String createInput(String name, String value) {
		return "<input style=\"width: 15%;\" id=\"" + name + "\" name=\"" + name + "\" value=\"" + value
				+ "\" type=\"text\" autocomplete=\"off\" placeholder=\"Please input\" class=\"el-input__inner\">";
	}

	public String createButton(String name ,String name2 ,String className) {
		return "<button type=\"button\" id=\""+name+"\" name=\""+name+"\" class=\""+className+"\">"+name2+"</button>";
	}
	
	public String createTable(String thead ,String tbody){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<table class=\"table table-hover\">");
		stringBuffer.append(	"<thead>");
		stringBuffer.append(thead);
		stringBuffer.append(	"</thead>");
		stringBuffer.append(	"<tbody>");
		stringBuffer.append(tbody);
		stringBuffer.append(	"</tbody>");
		stringBuffer.append("</table>");
		return stringBuffer.toString();
	}
	
	public String addTableRow(String rowcontent){
		return "<tr>"+rowcontent+"</tr>";
	}
	public String addTableTitle(String rowcontent){
		return "<th scope=\"col\">"+rowcontent+"</th>";
	}
	public String addTableColumn(String scope ,String rowcontent){
		return "<td scope=\""+scope+"\">"+rowcontent+"</td>";
	}
	public String addTableColumn(String rowcontent){
		return "<td>"+rowcontent+"</td>";
	}
	
	// 可選號碼
	public String Lottoball(String name, String className, int number, String func) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<input type=\"checkbox\" id=\"cb_" + name + "\" name=\"cbball\" value=\"" + number
				+ "\" style=\"display:none;\">"); 
		// custom-btn btn-2 
		stringBuffer.append("<input type=\"button\" class=\""+className+"\" id=\"" + name + "\" name=\"" + name + "\" value=\""
				+ number + "\" onclick=\"" + func + "\">&nbsp;&nbsp;&nbsp;&nbsp;");
		return stringBuffer.toString();
	}

	// 可選號碼外層label & div
	public String Lottoball(String labelname, int col1, int col2, String content) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
				"<label for=\"accountName\" class=\"col-sm-" + col1 + " col-form-label  \">" + labelname + "</label>");
		stringBuffer.append("<div class=\"col-sm-" + col2 + " \">");
		stringBuffer.append(content);
		stringBuffer.append("</div>");
		return stringBuffer.toString();
	}

	public String Row(String content) {
		String str = "";
		str = "<div>" + content + "</div>";
		return str;
	}

	public String createLottoBtn() {
		Html Html = new Html();
		String str = "", str2 = "";
		int j = 0;
		for (int i = 1; i < 50; i++) {
			str += Lottoball("ball" + i, "custom-newbtn newbtn-2", i, "chooseBallFunc(this.value)");
			if (i % 7 == 0) {
				j++;
				if (j == 1) str = Lottoball("可選號碼：", 2, 10, str);
				else        str = Lottoball("", 2, 10, str);
				
				str2 += Html.Row(str); // 行
				str = "";
			}
		}
		return str2;
	}

	public String fieldset_ShowData(String legend, String id ,String content) {
		return "<fieldset><legend>" + legend + "</legend><div id=\"" + id + "\">"+content+"</div></fieldset>";
	}
	
	public String JavaScriptFunc(String jspath) {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(jspath);
		return stringbuffer.toString();
	}
	
	public String End() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("</div>");
		stringbuffer.append("</body>");
		stringbuffer.append("</html>");
		return stringbuffer.toString();
	}
	
	// bookstrap5 手風琴 (Accordion)
	public String CreateAccordion_Bookstrap5(String count ,String str ,String recdate ,String str2 ,String str3 ,String show) {
		StringBuffer stringbuffer = new StringBuffer();
		if(show.equals("1"))  show = "show";
		stringbuffer.append("<div class=\"accordion-item\">");
		stringbuffer.append(	"<h2 class=\"accordion-header\" id=\"panelsStayOpen-heading"+count+"\">");
		stringbuffer.append(	"<button class=\"accordion-button\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#panelsStayOpen-collapse"+count+"\" aria-expanded=\"true\" aria-controls=\"panelsStayOpen-collapse"+count+"\">");
		stringbuffer.append(str);
		stringbuffer.append(	"</button>");
		stringbuffer.append(	"</h2>");
		stringbuffer.append(	"<div id=\"panelsStayOpen-collapse"+count+"\" class=\"accordion-collapse collapse "+show+"\" aria-labelledby=\"panelsStayOpen-heading"+count+"\">");
		stringbuffer.append(		"<div class=\"accordion-body\">");
		stringbuffer.append(		"<h6><span class=\"badge rounded-pill bg-primary\">"+recdate+"</span></h6>");
		stringbuffer.append(		"<div class=\"alert alert-dark\">");
		stringbuffer.append(			"<table class=\"table table-striped\">");
		stringbuffer.append(str2);
		stringbuffer.append(			"</table>");
		stringbuffer.append(		"</div>");
		stringbuffer.append(		"<div class=\"alert alert-info\">");
		stringbuffer.append(str3);
		stringbuffer.append(		"</div>");
		stringbuffer.append(		"</div>");
		stringbuffer.append(	"</div>");
		stringbuffer.append(	"</div>");
		stringbuffer.append("</div>");
		return stringbuffer.toString();
	}
	
	public String CreateMsg(String str ,String str2 ,String str3) {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("<div class=\"alert alert-success\">");
		stringbuffer.append(	"<h4 class=\"alert-heading\">");
		stringbuffer.append(str);
		stringbuffer.append(	"</h4>");
		stringbuffer.append(	"<p>");
		stringbuffer.append(str2);
		stringbuffer.append(	"</p>");
		stringbuffer.append(	" <hr>");
		stringbuffer.append(	"<p class=\"mb-0\">");
		stringbuffer.append(str3);
		stringbuffer.append(	"</p>");
		stringbuffer.append("</div>");
		return stringbuffer.toString();
	}
}
