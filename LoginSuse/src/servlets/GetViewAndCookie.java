package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.HttpClientLog;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import bean.LoginData;
/**
 * 负责获取viewState和cookie，将cookie传递给ShowImg，ShowImg来获取图片并显示在fill.jsp上面
 * @author fanye
 *
 */
public class GetViewAndCookie extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpClientLog log = new HttpClientLog();
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
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
		LoginData loginData = new LoginData();
		
		
  		String NET_SessionId = null;
  
 		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod("http://222.24.62.120/default2.aspx");
		
		getMethod.addRequestHeader("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, */*");
		getMethod.addRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; GWX:QUALIFIED; Shuame)");
		getMethod.addRequestHeader("Accept-Encoding", "gzip, deflate");
		getMethod.addRequestHeader("Host", "222.24.62.120");
		getMethod.addRequestHeader("DNT", "1");
		getMethod.addRequestHeader("Connection", "Keep-Alive");
		getMethod.addRequestHeader("Pragma", "no-cache");	
		log.printRequestHeadersLog(getMethod);
		
		httpClient.executeMethod(getMethod);
		
		log.printResponseLog(getMethod);
		String content;
		BufferedReader bufr = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(),"utf-8"));
		while((content=bufr.readLine())!=null)
		{
			if(content.contains("__VIEWSTATE"))
			{
				//value="dDwyODE2NTM0OTg7Oz7I2Cct+u86RN/NdUhLZSrpUl6ZsQ=="
				String result = content.substring(content.indexOf("value=\"")+7, 
						content.lastIndexOf("\""));
				content=result;
				loginData.setViewState(result);
				break;
			}
		}
		
		NET_SessionId = getMethod.getResponseHeader("Set-Cookie").getValue();
		NET_SessionId = NET_SessionId.substring(NET_SessionId.indexOf("=")+1,NET_SessionId.indexOf(";"));

		loginData.setCookie(NET_SessionId);
		System.out.println("获取到请求参数为："+NET_SessionId);
		
		request.getSession().setAttribute("loginData", loginData);
		response.sendRedirect("fill.jsp");//重定向
	}

}
