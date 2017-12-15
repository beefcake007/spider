package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log.HttpClientLog;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class ShowImg extends HttpServlet
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		System.out.println("================调用了Servlet的doGet()=============");
		doPost(request,response);
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
		//"http://61.139.105.138/CheckCode.aspx"
		String ImgCookie = request.getParameter("ASP.NET_SessionId");
		System.out.println("============================================");
		System.out.println("收到请求参数为：" + ImgCookie);
		System.out.println("================准备用此参数去获取验证码图片===================");

		HttpClient httpClient = new HttpClient();
		GetMethod getMethodImg = new GetMethod("http://222.24.62.120/CheckCode.aspx");
		getMethodImg.addRequestHeader("Referer", "http://222.24.62.120/default2.aspx");
		getMethodImg.addRequestHeader("Cookie", "ASP.NET_SessionId=" + ImgCookie);
		new HttpClientLog().printRequestHeadersLog(getMethodImg);
		httpClient.executeMethod(getMethodImg);
		byte[] img = getMethodImg.getResponseBody();
		response.getOutputStream().write(img);
		getMethodImg.releaseConnection();
	}
}
