package com.lwxf.newstore.webapp.common.jmail.mail;

import java.util.Set;

import com.lwxf.newstore.webapp.common.jmail.MailSend;

/**
 * 功能：邮件抽象类，对MailSend的抽象实现
 * 可直接继承该抽象类，实现定制功能，
 * 也可只实现MailSend接口来实现定制功能
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class AbstractMailSend implements MailSend {
	protected static final String CONTENT_TEMPLATE;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\"background:#DBDBDB;margin:0;padding:30px;\">")
				.append("<div style=\"width:550px;background:#f7f7f7;margin:50px auto;-webkit-font-smoothing: antialiased;font-family:'微软雅黑', 'Helvetica Neue', sans-serif, SimHei;font-size:12px;\">")
				.append("<div style=\"background:#474d57;padding:10px;\">")
				.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"transparent\">")
				.append("<tbody>")
				.append("<tr>")
				.append("<td><a href=\"https://easypm.cn\" target=\"_blank\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGUAAAASCAYAAABCWxC2AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QzAwMEEzQkQ5QkNBMTFFNDkwOEJBNDNBNUVDOEM5RDEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QzAwMEEzQkU5QkNBMTFFNDkwOEJBNDNBNUVDOEM5RDEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpDMDAwQTNCQjlCQ0ExMUU0OTA4QkE0M0E1RUM4QzlEMSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpDMDAwQTNCQzlCQ0ExMUU0OTA4QkE0M0E1RUM4QzlEMSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PrfU/BwAAAe5SURBVHjazFlncFRV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\" alt=\"EasyPM-让研发管理更简单\" /></a></td>")
				.append("<td width=\"60\" style=\"text-align: center;\"><a style=\"color:#fff;text-decoration: none;font-size:12px;\" href=\"https://easypm.cn/blogs\" target=\"_blank\">官方博客</a></td>")
				.append("<td width=\"40\" style=\"text-align: center;\"><a style=\"color:#fff;text-decoration: none;font-size:12px;\" href=\"https://easypm.cn/login\" target=\"_blank\">登录</a></td>")
				.append("<td width=\"40\" style=\"text-align: center;\"><a style=\"color:#fff;text-decoration: none;font-size:12px;\" href=\"https://easypm.cn/register\" target=\"_blank\">注册</a></td>")
				.append("</tr>")
				.append("</tbody>")
				.append("</table>")
				.append("</div>")
				.append("<div style=\"padding:10px 30px;\">")
				.append("{0}") // 邮件内容部分
				.append("<p>请勿回复此邮件，如果有疑问，请联系我们：service@easypm.cn</p>")
				.append("<p>- EasyPM 团队</p>")
				.append("<hr style=\"border:none;border-top:1px solid #ccc;\">")
				.append("<p style=\"font-size:9pt; color:#b5b0b0;\">如果你没有注册过EasyPM，请忽略此邮件。© 2015 easypm.cn</p>")
				.append("</div>")
				.append("<div>")
				.append("</div>");
		CONTENT_TEMPLATE = sb.toString();
	}
	protected Set<String> tos; // 防止出现重复地址
	protected String persional = "EasyPM"; // from的个性化名称
	protected String message = ""; // 邮件的附内容

	@Override
	public String getPersonal() {
		return this.persional;
	}

	public void setPersonal(String personal) {
		this.persional = personal;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Set<String> getTo() {
		return this.tos;
	}

	@Override
	public void setTo(Set<String> tos) {
		this.tos = tos;
	}
}
