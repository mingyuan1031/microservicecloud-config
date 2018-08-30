package com.lwxf.newstore.webapp.baseservice.sitemap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.exception.LwxfIOException;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:49
 * @Copyright：2018 Version：1.0
 * @Company：老屋新房 Created with IntelliJ IDEA
 */
public class SiteMapXMLService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String SITEMAP_XML_PATH="resources/sitemap.xml";

	private String getRootRealPath(){
		return WebUtils.getHttpServletRequest().getServletContext().getRealPath(SITEMAP_XML_PATH);
	}
	/*<url>
	<loc>https://free.easypm.cn/blogs/H8xND7piG6ed7ci5YAhBam</loc>
	<changefreq>weekly</changefreq>
	<priority>0.5</priority>
	</url>*/
	public void addNode(String loc){
		File xmlFile = new File(this.getRootRealPath());
		Document document = this.checkXmlFile(xmlFile);
		Element root = document.getRootElement();

		Element child= this.getLocElement(document,loc);
		if(null == child){
			Element url = root.addElement("url");
			Element locEl = url.addElement("loc");
			locEl.setText(loc);
			Element changefreq = url.addElement("changefreq");
			changefreq.setText("weekly");
			Element priority = url.addElement("priority");
			priority.setText("0.5");
			this.saveXmlFile(document,xmlFile);

			try{
				if(AppBeanInjector.configuration.isOnFEPublic()) {
					String result = PushSiteMapUtil.postHttp(loc);
					this.logger.error(result);
				}
			}catch (Exception e){
				this.logger.error("baidu post error",e);
			}
		}
	}

	public void deleteNode(String loc){
		File xmlFile = new File(this.getRootRealPath());
		Document document = this.checkXmlFile(xmlFile);
		Element child= this.getLocElement(document,loc);
		if(null != child){
			document.getRootElement().remove(child.getParent());
			this.saveXmlFile(document,xmlFile);
		}
	}

	private Element getLocElement(Document document, String loc){
		List<Element> urls = document.getRootElement().elements();
		for(Element url : urls){
			Element locEl = url.element("loc");
			if(loc.equals(locEl.getTextTrim())){
				return locEl;
			}
		}
		return null;
	}

	private Document checkXmlFile(File xmlFile){
		Document document=null;
		if(xmlFile.exists()){
			SAXReader reader = new SAXReader();
			try {
				document = reader.read(xmlFile);
			} catch (DocumentException e) {
				logger.error("读取保存sitemap.xml文件异常", e);
			} finally {
				reader = null;
			}
		}else{
			try {
				xmlFile.createNewFile();
			} catch (IOException e) {
				logger.error("创建sitemap.xml文件异常", e);
			}
			document = DocumentHelper.createDocument();
			Element root=document.addElement("urlset");
			root.addAttribute("xmlns","http://www.sitemaps.org/schemas/sitemap/0.9");
			this.saveXmlFile(document,xmlFile);
		}
		if(null == document){
			this.logger.error("document 为空");
			throw new LwxfIOException("document 为空");
		}
		return document;
	}

	private void saveXmlFile(Document document, File xmlFile) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(new FileWriter(xmlFile), format);
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			logger.error("保存sitemap.xml文件异常", e);
		}
	}
}