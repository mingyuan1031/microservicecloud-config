package com.lwxf.newstore.webapp.common.mobile;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vdurmont.emoji.EmojiParser;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageEntity;
import com.lwxf.newstore.webapp.baseservice.rabbitmq.MessageScope;
import com.lwxf.newstore.webapp.common.enums.MQEventEnum;
import com.lwxf.newstore.webapp.common.enums.user.UserSex;
import com.lwxf.newstore.webapp.common.mobile.weixin.XMLUtils;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.ImgJsonMsg;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.TextJsonMsg;
import com.lwxf.newstore.webapp.common.utils.ExceptionGenerateFactory;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.userThirdInfoFacade;
import static com.lwxf.newstore.webapp.facade.AppBeanInjector.weixinCfg;
import static com.lwxf.newstore.webapp.facade.AppBeanInjector.wxMpService;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class WeixinUtils {
	private static final Logger logger = LoggerFactory.getLogger(WeixinUtils.class);
	private static String c_web_authorization_path = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_base&state={2}#wechat_redirect";
	public static final String c_auth_scope_login = "snsapi_login";

	public static HttpURLConnection createConnection(String url, String method) throws Exception {
		return createConnection(url, method, "application/x-www-form-urlencoded");
	}

	public static HttpURLConnection createConnection(String url, String method, String contentType) throws Exception {
		URL requstUrl = new URL(url);
		HttpURLConnection http = (HttpURLConnection) requstUrl.openConnection();
		http.setRequestMethod(method);
		http.setRequestProperty("Content-Type", contentType);
		http.setDoOutput(true);
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
		return http;
	}

	private static String _parseFromHttpURLConncection(HttpURLConnection httpConnection) throws Exception {
		Assert.notNull(httpConnection,"httpConnection不能为空");
		InputStream is = httpConnection.getInputStream();
		int size = is.available();
		byte[] jsonBytes = new byte[size];
		is.read(jsonBytes);
		String message = new String(jsonBytes, "UTF-8");
		is.close();
		return message;
	}

	public static JSONObject parseFromHttpURLConncection(HttpURLConnection httpConnection) throws Exception {
		String message = _parseFromHttpURLConncection(httpConnection);
		return new JSONObject(message);
	}

	public static JSONObject parseFromHttpURLConncectionForEmoji(HttpURLConnection httpConnection) throws Exception {
		String message = EmojiParser.removeAllEmojis(_parseFromHttpURLConncection(httpConnection));
		return new JSONObject(message);
	}

	/**
	 * java后台获取用户微信信息
	 *
	 * @param openId
	 * @return
	 */
	public static UserThirdInfo getUserWeixinInfoByOpenId(String openId) {
		WxMpUser wxMpUser = null;
		try {
			logger.info("查询微信用户信息UserThirdInfo:getUserWeixinInfoByOpenId():openId:{}",openId);
			wxMpUser = wxMpService.getUserService().userInfo(openId);
			logger.info("根据微信信息构造UserThirdInfo:getUserWeixinInfoByOpenId():nickName:{}",wxMpUser.getNickname());
			UserThirdInfo weixinInfo = new UserThirdInfo();
			weixinInfo.setWxOpenId(wxMpUser.getOpenId());
			weixinInfo.setWxUnionId(wxMpUser.getUnionId());
			weixinInfo.setWxNickname(wxMpUser.getNickname());
			return weixinInfo;
		} catch (WxErrorException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据WxMpUser创建UserThirdInfo
	 *
	 * @param wxMpUser
	 * @return
	 */
	public static UserThirdInfo createUserThirdInfoByWxMpUser(WxMpUser wxMpUser) {
		UserThirdInfo thirdInfo = new UserThirdInfo();
		thirdInfo.setWxOpenId(wxMpUser.getOpenId());
		thirdInfo.setWxUnionId(wxMpUser.getUnionId());
		thirdInfo.setWxNickname(wxMpUser.getNickname());
		thirdInfo.setWxIsBind(Boolean.TRUE);
		thirdInfo.setWxIsSubscribe(Boolean.TRUE);
		thirdInfo.setEmailIsBind(Boolean.FALSE);
		thirdInfo.setMobileIsBind(Boolean.FALSE);
		return thirdInfo;
	}

	/**
	 * 根据微信openId获取微信用户的信息
	 *
	 * @param openId
	 * @return
	 */
	public static WxMpUser getWxMpUserByOpenId(String openId) {
		WxMpUser wxMpUser;
		try {
			wxMpUser = wxMpService.getUserService().userInfo(openId);
		} catch (WxErrorException e) {
			wxMpUser = null;
			logger.error(e.getMessage());
		}
		return wxMpUser;
	}


	/**
	 * 通过网页授权方式获取微信用户信息
	 *
	 * @param openId
	 * @param accessToken
	 * @return
	 */
	public static UserThirdInfo getUserWeixinInfoForWeb(String openId, String accessToken) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
		url = LwxfStringUtils.format(url, accessToken, openId);
		return getUserWeixinInfo(url);
	}

	private static UserThirdInfo getUserWeixinInfo(String url) {
		HttpURLConnection connection = null;
		try {
			connection = createConnection(url, RequestMethod.GET.toString());
			connection.connect();
			JSONObject jsonObj = parseFromHttpURLConncectionForEmoji(connection);

			if (jsonObj.has("errcode")) {
				throw ExceptionGenerateFactory.createException(ErrorCodes.BIZ_LOAD_USER_WEIXIN_INFO_10021);
			}
			UserThirdInfo weixinInfo = new UserThirdInfo();
			weixinInfo.setWxOpenId(jsonObj.getString("openid"));
			weixinInfo.setWxUnionId(jsonObj.getString("unionid"));
			weixinInfo.setWxNickname(jsonObj.optString("nickname","<unknown>"));
			return weixinInfo;
		} catch (Exception e) {
			logger.error("获取用户的微信信息出现异常", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}


	public static JSONObject getWebAuthorizeInfo(String code){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		url = LwxfStringUtils.format(url, weixinCfg.getWebAppId(), weixinCfg.getWebSecret(), code);
		try {
			HttpURLConnection connection = createConnection(url, RequestMethod.GET.toString());
			connection.connect();
			JSONObject jsonObj = parseFromHttpURLConncection(connection);
			connection.disconnect();
			return jsonObj;
		} catch (Exception e) {
			logger.error("获取用户的微信信息出现异常", e);
		}
		return null;
	}

	/**
	 *  微信端获取用户信息
	 * @param code
	 * @return
	 */
	public static JSONObject getWeiXinAuthorizeInfo(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		url = LwxfStringUtils.format(url, weixinCfg.getAppId(), weixinCfg.getSecret(), code);
		try {
			HttpURLConnection connection = createConnection(url, RequestMethod.GET.toString());
			connection.connect();
			JSONObject jsonObj = parseFromHttpURLConncection(connection);
			connection.disconnect();
			return jsonObj;
		} catch (Exception e) {
			logger.error("获取用户的微信信息出现异常", e);
		}
		return null;
	}

	public static String getWebAuthorizationPath(HttpServletRequest request, String weixinAppId) throws UnsupportedEncodingException {
		return LwxfStringUtils.format(c_web_authorization_path, weixinAppId, URLEncoder.encode(request.getRequestURL().toString(), StandardCharsets.UTF_8.name()), 1);
	}

	public static String getWebAuthorizationPath(String callPath, String weixinAppId, String sessionId) throws UnsupportedEncodingException {
		return LwxfStringUtils.format(c_web_authorization_path, weixinAppId, URLEncoder.encode(callPath, StandardCharsets.UTF_8.name()), 1);
	}

	/**
	 * 发送文本消息
	 * @param msg
	 * @param toUser
	 */
	public static void sendTextMsgToUser(String msg, String toUser) {
		TextJsonMsg msgObj = new TextJsonMsg();
		msgObj.setTouser(toUser);
		msgObj.setContentInfo(msg);
		AppBeanInjector.weixinJsonMsgService.pushMsg(msgObj);
	}

	/**
	 * 发送图片消息
	 * @param
	 * @param toUser
	 */
	public static void sendImgMsgToUser(String toUser,String mediaId){
		ImgJsonMsg msgObj = new ImgJsonMsg();
		msgObj.setTouser(toUser);
		msgObj.setMediaId(mediaId);
		AppBeanInjector.weixinJsonMsgService.pushMsg(msgObj);
	}

	/**
	 * 发送mq消息
	 * @param event
	 * @param userId
	 */
	public static void sendErrorMsgToUser(MQEventEnum event,String code, String msg,String userId) {
		MessageEntity error = MessageEntity.newOne(MessageScope.USER,userId ,event);
		Map map = new HashMap(1);
		Map m = new HashMap(2);
		m.put("code", code);
		m.put("msg", msg);
		map.put("error", m);
		error.setData(map);
		error.setX_TAG(AppBeanInjector.idGererateFactory.nextStringId());
		error.setScope(MessageScope.USER);
		error.setScopeId(userId);
		//AppBeanInjector.rabbitMQSender.sendMessage(true,error); TODO:
	}

	/**
	 * 请求转发到远程，请接收响应
	 * @param remoteUrl
	 * @param queryString
	 * @param reqXmlData
	 * @return
	 */
	public static ResponseData doForwardRequest(String remoteUrl, String queryString, Map<String,String> reqXmlData){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null;
		ResponseData responseData = new ResponseData();
		try{
			String path = remoteUrl+ "?" + queryString;
			logger.debug("微信请求转发远程url:{}", path);
			URL url = new URL(path);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);

			httpURLConnection.setRequestProperty("Content-Type", "application/xml");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpURLConnection.setRequestProperty("Charset", "UTF-8");

			//发送数据
			out = httpURLConnection.getOutputStream();
			String xml = XMLUtils.parseMapToXml(reqXmlData);
			out.write(xml.getBytes(StandardCharsets.UTF_8));
			out.flush();

			//响应
			int resultCode = httpURLConnection.getResponseCode();
			logger.debug("微信请求转发响应码:{}",  resultCode);

			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len;
			byte[] arr = new byte[1024];
			while((len=bis.read(arr))!= -1){
				bos.write(arr,0,len);
				bos.flush();
			}
			bos.close();
			bis.close();

			responseData.setCode(resultCode);
			responseData.setData(bos.toString("utf-8"));
		}catch (Exception e){
			logger.error(e.getMessage());
		}finally {
			if(httpURLConnection != null){
				httpURLConnection.disconnect();
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return responseData;
	}

	public static class ResponseData {
		private int code;
		private String data;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}
	}

	/**
	 * 初始化用户微信状态
	 * @param wxOpenId
	 */
	public static Integer initPlatformTag(String wxOpenId){
		Integer platformTag = (Integer) AppBeanInjector.redisUtils.hGet(RedisConstant.PLATFORM_TAG, wxOpenId);

		if(platformTag != null){
			return platformTag;
		}

		boolean newLwxfBind = false;
		List<UserThirdInfo> userThirdInfoList = userThirdInfoFacade.findByWxOpenId(wxOpenId);
		if(userThirdInfoList.isEmpty()){
			newLwxfBind = true;
		}
		if(newLwxfBind){
			platformTag = 0;
		}else {
			platformTag = 1;
		}
		if(platformTag != null){
			AppBeanInjector.redisUtils.hPut(RedisConstant.PLATFORM_TAG, wxOpenId, platformTag);
		}
		return platformTag;
	}
	public static int transitionWeiXinUserSex(int weiXinSex){
		if(weiXinSex==0||weiXinSex==1){
			return UserSex.MAN.getValue();
		}else{
			return UserSex.WOMEN.getValue();
		}
	}
}
