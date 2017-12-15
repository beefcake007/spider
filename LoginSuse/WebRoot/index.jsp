<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.apache.commons.httpclient.*"%>
<%@page import="org.apache.commons.httpclient.Cookie"%>  
<%@page import="org.apache.commons.httpclient.HttpClient        "%>  
<%@page import="org.apache.commons.httpclient.HttpException     "%>  
<%@page import="org.apache.commons.httpclient.methods.PostMethod"%>  
<%@page import="org.apache.commons.httpclient.methods.GetMethod"%>  
<%@page import="org.apache.commons.httpclient.Header"%> 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <!-- 自动跳转到获取ViewState参数和cookie的Servlet中 -->
  <jsp:forward page="GetViewAndCookie"></jsp:forward>
  <body>
  </body>
</html>
