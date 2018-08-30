package com.lwxf.newstore.webapp.common.jmail.sendcloudmail;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lwxf.commons.exception.LwxfIOException;
import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.exception.ExecuteFailException;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.file.FileUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:51
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GroupMailService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private JsonMapper json = JsonMapper.nonEmptyMapper();

	private static final String c_mail_list_search_url = "http://sendcloud.sohu.com/webapi/list.get.json";
	private static final String c_mail_list_member_search_url = "http://sendcloud.sohu.com/webapi/list_member.get.json";
	private static final String c_mail_list_member_add_url = "http://sendcloud.sohu.com/webapi/list_member.add.json";
	private static final String c_mail_list_member_update_url = "http://sendcloud.sohu.com/webapi/list_member.update.json";
	private static final String c_mail_list_member_delete_url = "http://sendcloud.sohu.com/webapi/list_member.delete.json";

	/**
	 * 创建用户邮件列表(按照SendCloud邮件地址列表格式生成txt文件)
	 *
	 * @param filePath
	 */
	@Transactional(readOnly = true)
	public void createMailListFile(String filePath) {
		Assert.isTrue(AppBeanInjector.configuration.isOnProd(), "只允许在线上执行");
		Assert.isTrue(filePath.endsWith(".txt"), "批量邮件列表只能是txt文件");
		// 保证每次创建的文件都是新的
		FileUtil.delete(filePath);
		OutputStreamWriter pw = null;//定义一个流
		try {
			List<User> users = AppBeanInjector.userFacade.findAll();
			pw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			StringBuilder sb = new StringBuilder();
			String name = "";
			String email;
			for (User user : users) {
				email = user.getEmail();
				if(LwxfStringUtils.isBlank(email)){
					continue;
				}
				name = user.getName();
				if (LwxfStringUtils.isEmpty(name)) {
					name = email;
				}
				sb.append(name).append("<").append(email).append(">, ").append("{\"name\":\"").append(name).append("\"}\n");
			}
			pw.write(sb.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			if (null != pw) {
				try {
					pw.close();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送群发邮件
	 *
	 * @param mailTemplate
	 * @param mailList
	 * @return
	 * @throws IOException
	 */
	public String sendGroupMail(String mailTemplate, String mailList) throws IOException {
		Assert.isTrue(AppBeanInjector.configuration.isOnProd(), "只允许在线上执行");
		Assert.isTrue(LwxfStringUtils.isNotEmpty(mailTemplate), "邮件模版不允许为空");
		Assert.isTrue(LwxfStringUtils.isNotEmpty(mailList), "邮件列表不允许为空");

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("api_user", SendCloudCfgFactory.SEND_CLOUD_USER_BATCH));
		params.add(new BasicNameValuePair("api_key", SendCloudCfgFactory.SEND_CLOUD_API_KEY));
		params.add(new BasicNameValuePair("to", mailList));
		params.add(new BasicNameValuePair("template_invoke_name", mailTemplate));
		params.add(new BasicNameValuePair("from", SendCloudCfgFactory.EMAIL_FROM));
		params.add(new BasicNameValuePair("fromname", SendCloudCfgFactory.EMAIL_FROMNAME));
		params.add(new BasicNameValuePair("use_maillist", "true"));
		params.add(new BasicNameValuePair("resp_email_id", "true"));

		HttpResponse response = this.sendRequestToSendCloud(SendCloudCfg.SEND_TEMPLATE_URL, params);

		if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
			this.logger.info("群发邮件失败！\n" + EntityUtils.toString(response.getEntity()));
			return this.json.toJson(ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, ErrorCodes.VALIDATE_GROUP_SENDING_MAIL_FAIL));
		}
		return WebConstant.REQUEST_RESULT_AJAX_EMPTY_OBJ;
	}

	public void addMailListMember(Map<String, String> params) {
		this.doOperateMailListMember(c_mail_list_member_add_url, params, "添加");
	}

	public void updateMailListMember(Map<String, String> params) {
		this.doOperateMailListMember(c_mail_list_member_update_url, params, "修改");
	}

	public void deleteMailListMember(Map<String, String> params) {
		this.doOperateMailListMember(c_mail_list_member_delete_url, params, "删除");
	}

	private void doOperateMailListMember(String url, Map<String, String> params, String actionStr) {
		Assert.isTrue(!MapUtils.isEmpty(params),"参数不允许为空：params");
		List<NameValuePair> paramList = new ArrayList<>();
		paramList.add(new BasicNameValuePair("api_user", SendCloudCfgFactory.SEND_CLOUD_USER_BATCH));
		paramList.add(new BasicNameValuePair("api_key", SendCloudCfgFactory.SEND_CLOUD_API_KEY));

		for (Map.Entry<String, String> entry : params.entrySet()) {
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			;
		}
		try {
			HttpResponse response = this.sendRequestToSendCloud(url, paramList);
			if (!(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
				this.logger.error(actionStr + "邮件地址列表成员失败！\n" + EntityUtils.toString(response.getEntity()));
				throw new ExecuteFailException(actionStr + "邮件地址列表成员失败！\n" + EntityUtils.toString(response.getEntity()));
			}
		} catch (IOException e) {
			this.logger.error("", e);
			throw new LwxfIOException(e);
		}
	}

	private HttpResponse sendRequestToSendCloud(String reqUrl, List<NameValuePair> params) throws IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();

		HttpPost httpost = new HttpPost(reqUrl);
		httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpost);
		httpost.reset();
		return response;
	}
}
