package com.lwxf.newstore.webapp.baseservice.sitemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:17:16
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class PushSiteMapUtil {
	/**
	 * 百度链接实时推送
	 *
	 * @param url
	 * @return
	 */
	public static String postHttp(String url) throws IOException {
		if(url==null){
			return null;
		}

		List<String> params= Arrays.asList(url);
		if (null == params || params.isEmpty()) {
			return null;
		}
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		String bdUrl = "http://data.zz.baidu.com/urls?site=free.easypm.cn&token=LidknrQYhFxxngwq";
		try {
			URLConnection conn = new URL(bdUrl).openConnection();// 建立URL之间的连接
			conn.setRequestProperty("Host", "data.zz.baidu.com");// 设置通用的请求属性
			conn.setRequestProperty("User-Agent", "curl/7.12.1");
			conn.setRequestProperty("Content-Length", "83");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setDoInput(true);// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			out = new PrintWriter(conn.getOutputStream());// 获取conn对应的输出流
			String param = "";// 发送请求参数
			for (String s : params) {
				param += s + "\n";
			}
			out.print(param.trim());
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} finally {
			try {
				if (out != null){
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
