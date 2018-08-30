package com.lwxf.newstore.webapp.common.authcode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:24:52
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AuthCodeServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AuthCodeServlet.class);
	public static final String SESSION_KEY_AUTHCODE="authCode";
	@Override
	protected void doGet(HttpServletRequest reqeust,
						 HttpServletResponse response) throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		HttpSession session = reqeust.getSession();

		AuthCodeGenerator vCode = new AuthCodeGenerator(140,40,5,20);
		if(logger.isDebugEnabled()){
			logger.debug("AuthCodeServlet-session-ID:"+session.getId());
			logger.debug("AuthCodeServlet-code:"+vCode.getCode());
		}
		session.setAttribute(SESSION_KEY_AUTHCODE, vCode.getCode());
		vCode.write(response.getOutputStream());
	}
/**
 * web.xml 添加servlet
 <servlet>
 <servlet-name>authCodeServlet</servlet-name>
 <servlet-class>com.lwxf.newstore.webapp.common.authcode.AuthCodeServlet</servlet-class>
 </servlet>
 <servlet-mapping>
 <servlet-name>authCodeServlet</servlet-name>
 <url-pattern>/authCode</url-pattern>
 </servlet-mapping>

 在地址栏输入XXX/authCode 测试
 */
}
