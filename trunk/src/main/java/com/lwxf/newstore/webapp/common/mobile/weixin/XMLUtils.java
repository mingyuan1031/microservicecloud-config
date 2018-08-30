package com.lwxf.newstore.webapp.common.mobile.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:42
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class XMLUtils {
	// xml消息体的key定义
	public static final String c_xml_msg_key_ToUserName ="ToUserName";
	public static final String c_xml_msg_key_FromUserName ="FromUserName";
	public static final String c_xml_msg_key_CreateTime="CreateTime";
	public static final String c_xml_msg_key_MsgType ="MsgType";
	public static final String c_xml_msg_key_Event ="Event";
	public static final String c_xml_msg_key_EventKey="EventKey";
	public static final String c_xml_msg_key_Ticket="Ticket";

	// xml消息类型定义
	public static final String c_xml_msg_type_event = "event";

	// 事件类型定义
	public static final String c_xml_msg_event_subscribe = "subscribe";
	public static final String c_xml_msg_event_unsubscribe = "unsubscribe";
	public static final String c_xml_msg_event_scan = "SCAN";
	public static final String c_xml_msg_event_click = "CLICK";

	public static Map<String,String> parseXmlFromRequest(HttpServletRequest request) throws Exception{
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(request.getInputStream());
		List<Element> elements = document.getRootElement().elements();
		Map<String,String> msgMap = new HashMap<>();
		for(int m=0,len=elements.size();m<len;m++){
			Element element = elements.get(m);
			msgMap.put(element.getName(),element.getText());
		}
		return msgMap;
	}

	public static String parseMapToXml(Map<String, String> map) throws Exception{
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("xml");
		for (Map.Entry<String, String> me : map.entrySet()) {
			Element empName = rootElement.addElement(me.getKey());
			empName.setText(me.getValue());
		}
		return document.asXML();
	}
}
