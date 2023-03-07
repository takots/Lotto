<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.common.Html"%>
<%
Html Html = new Html();
out.print(Html.Start("使用者登入註冊頁面"));
out.print(Html.loginandsignupHtml("<link href=\"/Hello1221/css/user/login.css\" rel=\"stylesheet\" type=\"text/css\">" ,""));
out.print(Html.JavaScriptFunc("<script src=\"/Hello1221/js/user/intro.js\"></script>")); 
out.print(Html.End()); 
%>
