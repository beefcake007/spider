package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.HttpClientLog;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import bean.LoginData;


public class Login extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnLocal;
	
	 /** 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("================调用了Login的doPost()=============");
		
		LoginData loginData = (LoginData) request.getSession().getAttribute("loginData");

		loginData.setTxtUserName(request.getParameter("txtUserName"));
		loginData.setTextBox2(request.getParameter("TextBox2"));
		loginData.setTxtSecretCode(request.getParameter("txtSecretCode"));
		
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod("http://222.24.62.120/default2.aspx");
		
//		String __VIEWSTATE = request.getParameter("__VIEWSTATE");
//		String Button1= request.getParameter("Button1");
//		String hidPdrs= request.getParameter("hidPdrs");
//		String hidsc = request.getParameter("hidsc");
//		String lbLanguage= request.getParameter("lbLanguage");
//		String RadioButtonList1= request.getParameter("RadioButtonList1");
//		String TextBox2 = request.getParameter("TextBox2");
//		String txtSecretCode = request.getParameter("txtSecretCode");
//		String txtUserName = request.getParameter("txtUserName");
		
		NameValuePair[]  nvps = new NameValuePair[9];
		
		nvps[0] = new NameValuePair("__VIEWSTATE",loginData.getViewState());
		nvps[1] = new NameValuePair("Button1","");
		nvps[2] = new NameValuePair("hidPdrs","");
		nvps[3] = new NameValuePair("hidsc","");
		nvps[4] = new NameValuePair("lbLanguage","");
		nvps[5] = new NameValuePair("RadioButtonList1","学生");
		nvps[6] = new NameValuePair("TextBox2",loginData.getTextBox2());
		nvps[7] = new NameValuePair("txtSecretCode",loginData.getTxtSecretCode());
		nvps[8] = new NameValuePair("txtUserName",loginData.getTxtUserName());
		
		post.setRequestBody(nvps);
		System.out.println("Cookie:"+loginData.getCookie());	
		post.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());
		
		new HttpClientLog().printRequestHeadersLog(post);
		
		httpClient.executeMethod(post);
		new HttpClientLog().printResponseHeadersLog(post);
		BufferedReader bufr = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
		String line = null;
		while((line=bufr.readLine())!=null)
		{
			if(line.contains("验证码不正确！"))
			{
				request.getSession().setAttribute("errorInfo", "对不起，您输入的验证码有误哦。");
				response.sendRedirect("fill.jsp");
				return;
			}
		}
		System.out.println(post.getResponseBodyAsString());
		//    /xs_main.aspx?xh=13101010115
		Header header = post.getResponseHeader("Location");
		if(header==null)
		{
			request.getSession().setAttribute("errorInfo", "对不起，登录失败惹、、、坏了 !!");
			response.sendRedirect("fill.jsp");
			return;
		}
		returnLocal=header.getValue();
		if(!returnLocal.contains("xs_main.aspx"))
		{
			System.out.println("不正确的返回===》Location: "+returnLocal);
			String errorInfo = post.getResponseBodyAsString();
			request.setAttribute("errorInfo", errorInfo);
			request.getRequestDispatcher("LoginError.jsp").forward(request, response);
			post.releaseConnection();
			return;
		}
		System.out.println("Location: "+returnLocal);
		post.releaseConnection();
		
		
		GetMethod get = new GetMethod("http://222.24.62.120"+returnLocal);
		get.addRequestHeader("Cookie", "ASP.NET_SessionId="+loginData.getCookie());
		new HttpClientLog().printRequestHeadersLog(get);
		httpClient.executeMethod(get);
		new HttpClientLog().printResponseLog(get);
		request.getSession().setAttribute("Cookie", "ASP.NET_SessionId="+loginData.getCookie());
		System.out.println(get.getResponseBodyAsString());
	}

}
