<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'fill.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function reLoade()
	{
		var imgVar = document.getElementById('img');
		imgVar.src=imgVar.src+'?';
	}
	</script>
  </head>
  
  <body>
  <%
  	Object o=request.getSession().getAttribute("errorInfo"); 
  	if(o!=null)
  	{
  		out.print("<script type='text/javascript'/>alert('"+o.toString()+"');</script> ");
  	}
   %>
   <!-- 将参数填好，提交给Login处理 -->
    <form action="Login" method="post">
		<p>用户名：<input name="txtUserName" type="text" id="txtUserName" tabindex="1" size="15" value="13101010115"/></p>
		<p>密　码：<input name="TextBox2" type="password" id="TextBox2" tabindex="2" size="16" value=""/>	</p>
		<p>验证码：<input name="txtSecretCode" type="text" id="txtSecretCode" tabindex="3" size="4"/>&nbsp
		<img id="img" src="ShowImg?ASP.NET_SessionId=${loginData.cookie}"/>
		<a onclick="reLoade()" href="">看不清的话就点我换一张吧</a>
		<p><input type="submit" value="登录">&nbsp<input type="reset" value="重置"></p>
	</form>
  </body>
</html>
