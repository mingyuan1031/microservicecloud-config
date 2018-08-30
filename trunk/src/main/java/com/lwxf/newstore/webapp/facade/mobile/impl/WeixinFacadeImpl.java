package com.lwxf.newstore.webapp.facade.mobile.impl;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.baseservice.cache.constant.RedisConstant;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.common.dto.UserInfoObj;
import com.lwxf.newstore.webapp.common.enums.Blank;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.ApplyClerkSuccessMsg;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.annotation.Resource;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;

import java.net.URL;



import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import org.apache.commons.collections4.CollectionUtils;

import com.lwxf.commons.exception.ErrorCodes;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.Base58;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;

import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.mobile.WeixinUtils;
import com.lwxf.newstore.webapp.common.mobile.weixin.WeixinCfg;
import com.lwxf.newstore.webapp.common.mobile.weixin.XMLUtils;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.UserExtraUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.mobile.WeixinFacade;



import static com.lwxf.newstore.webapp.facade.AppBeanInjector.*;


/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:48
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("weixinFacade")
public class WeixinFacadeImpl implements WeixinFacade {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final Pattern eventPattern=Pattern.compile(".*?\\{.*\\}");
	private static String menu_api_url = "https://api.weixin.qq.com/cgi-bin/menu/{0}?access_token={1}";
	private static String menu_api_action_create = "create";
	private static String menu_api_action_get = "get";
	private static String menu_api_action_delete = "delete";
	private static String jsapi_signature_str = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";
	private static String UPLOAD_THUMB ="thumb";//缩略图
	private static String UPLOAD_IMAGE ="image";//缩略图
	@Resource
	private UserThirdInfoService userThirdInfoService;
	@Resource
	private WeixinCfg weixinCfg;
	@Resource
	private WxMpService wxMpService;
	@Resource(name = "companyService")
	private CompanyService companyService;
	@Resource(name = "storeConfigService")
	private StoreConfigService storeConfigService;
	@Override
	@Transactional
	public void bindWeixin(String userId, String orgUserId, String openId) {
		UserThirdInfo weixinInfo = WeixinUtils.getUserWeixinInfoByOpenId(openId);
		weixinInfo.setUserId(userId);
		userThirdInfoService.bindingWeixin(orgUserId, weixinInfo);
	}

	@Override
	@Transactional
	public void doRequestXmlData(String queryString, Map<String, String> reqXmlData) {
		Assert.notNull(reqXmlData,"微信事件数据不能为空");
		String msgType = reqXmlData.get(XMLUtils.c_xml_msg_key_MsgType);
		String content = reqXmlData.get("Content");
		//如果是文本消息
//		if (msgType.equals("text")){
//			if (content.equals("1")){
//				String filePath = "C:/Users/Administrator/Desktop/code.jpg";
//				String fileType = "thumb";
//				String media_id = this.upload(filePath,fileType);
//				String openId = reqXmlData.get(XMLUtils.c_xml_msg_key_FromUserName);
//				WeixinUtils.sendImgMsgToUser(openId,media_id);
//			}
//		}
		if(XMLUtils.c_xml_msg_type_event.equals(msgType)){
			String event = reqXmlData.get(XMLUtils.c_xml_msg_key_Event);
			String openId = reqXmlData.get(XMLUtils.c_xml_msg_key_FromUserName);

			// 处理订阅事件
			if(XMLUtils.c_xml_msg_event_subscribe.equals(event)){
				doSubscribe(queryString, reqXmlData, openId);
			}else if(XMLUtils.c_xml_msg_event_unsubscribe.equals(event)){ // 取消订阅
				doUnSubscribe(queryString, reqXmlData, openId);
			}else if(XMLUtils.c_xml_msg_event_scan.equals(event)){ // 扫描
				doScan(queryString, reqXmlData, openId);
			}else if(XMLUtils.c_xml_msg_event_click.equals(event)){//点击事件
				doClick(reqXmlData, openId);
			}
		}
	}

	private  void doClick(Map<String, String> reqXmlData, String openId){
		String eventKey = reqXmlData.get(XMLUtils.c_xml_msg_key_EventKey);
		if(LwxfStringUtils.isEmpty(eventKey)){
			logger.debug("微信扫码eventKey为空,未处理");
			return;
		}
		//如果是绑定的微信名片点击事件
		if (eventKey.equals("storecard")){
			StoreConfig storeConfig = this.storeConfigService.findOneByLimit();
			String storeCardImgUrl = storeConfig.getStorecard();
			String filePath = AppBeanInjector.configuration.getUploadFileRootDir().concat(storeCardImgUrl);
			String fileType = this.UPLOAD_IMAGE;
			//获取上传到微信服务器的图片的media_id
			String media_id = this.upload(filePath,fileType);
			WeixinUtils.sendImgMsgToUser(openId,media_id);
		}
	}
	private void doScan(String queryString, Map<String, String> reqXmlData, String openId) {
		String eventKey = reqXmlData.get(XMLUtils.c_xml_msg_key_EventKey);
		if(LwxfStringUtils.isEmpty(eventKey)){
			logger.debug("微信扫码eventKey为空,未处理");
			return;
		}
		// 不存在用户，不进行任何操作
		List<UserThirdInfo> userThirdInfos = this.userThirdInfoService.findByWxOpenId(openId);
		if(CollectionUtils.isEmpty(userThirdInfos)){
			return;
		}
		Matcher matcher = eventPattern.matcher(eventKey);

		if(matcher.matches()){
			//带场景串的关注
			String[] arr = eventKey.split("_");
			Map map = JsonMapper.nonDefaultMapper().fromJson(arr[0], Map.class);
			Object userObj = map.get("user");
			Object roleObj = map.get("role");
			Object flagObj = map.get("flag");
			String userId = userObj==null?null:userObj.toString();
			String roleStr = roleObj==null?null:map.get("role").toString();
			UserThirdInfo thirdInfo = userThirdInfos.get(0);
			User user = AppBeanInjector.userService.findById(userId);
			//如果flag的值是0，则是绑定店主的二维码
			if(flagObj!=null&&Blank.BINDSHOPKEEPER.getValue()==(Integer) flagObj){
				//如果该微信已经绑定
				if(thirdInfo.getWxIsBind()){
					WeixinUtils.sendTextMsgToUser("您好，您的微信已经绑定其他账号", openId);
					return;
				}
				if (LwxfStringUtils.isBlank(userId)) {
					throw new IllegalArgumentException("绑定店主时缺少用户id");
				}
				//如果该用户的角色是店主
				if(user.getRole()==UserRole.SHOPKEEPER.getValue()){
					//this.userThirdInfoService.bindingWeixin(null,thirdInfo);
					MapContext update= MapContext.newOne();
					update.put("userId", thirdInfo.getUserId());
					update.put("wxIsBind",Boolean.TRUE);
					update.put("wxOpenId",openId);
					this.userThirdInfoService.updateByWxOpenId(update);
					return;
				}
				else {
					WeixinUtils.sendTextMsgToUser("您好，当前登录的用户没有店主权限", openId);
					return;
				}
			}
			//添加店员操作
			if(flagObj!=null&&Blank.ADDCLERK.getValue()==(Integer) flagObj){
                WebUtils.putDataToRequestMap("userId",userId);//放在这里到活动日志里面用
				User userByOppenId = userService.findById(thirdInfo.getUserId());
				//如果是店主或者店长绑定店员，则返回
				if(userByOppenId.getRole().equals(UserRole.SHOPKEEPER.getValue())||userByOppenId.getRole().equals(UserRole.MANAGER.getValue())){
					WeixinUtils.sendTextMsgToUser("您好，店主身份和店长身份不能绑定店员", openId);
					return;
				}
				if(userByOppenId.getRole().equals(UserRole.CLERK.getValue())){
					WeixinUtils.sendTextMsgToUser("您好，您已经是本店店员了", openId);
					return;
				}
				//如果是关注后取消
				if(!thirdInfo.getWxIsSubscribe()){
					MapContext update= MapContext.newOne();
					update.put("wxIsSubscribe",Boolean.TRUE);
					update.put("wxOpenId",openId);
					this.userThirdInfoService.updateByWxOpenId(update);
				}
				MapContext update= MapContext.newOne();
				//如果是禁用用户 则修改为可用，且修改其角色为店员
				if(userByOppenId.getState()==UserState.DISABLED.getValue()){
					update.put(WebConstant.KEY_ENTITY_STATE,UserState.ENABLED.getValue());
				}
				update.put(WebConstant.KEY_ENTITY_ID,thirdInfo.getUserId());
				update.put(WebConstant.KEY_ENTITY_ROLE,UserRole.CLERK.getValue());
				AppBeanInjector.userService.updateByMapContext(update);

				Company company = this.companyService.findById(WebUtils.getCurrCompanyId());
				ApplyClerkSuccessMsg weixinMsg = new ApplyClerkSuccessMsg(company.getName());
				weixinMsg.setUserInfo(userByOppenId);
				weixinMsg.setTouser(thirdInfo.getWxOpenId());
				WebUtils.putDataToRequestMap(WebConstant.WX_TEMPLATE_MSG_APPLY_SUCCESS,weixinMsg);
				return;
			}
		}
	}

	private void doUnSubscribe(String queryString, Map<String, String> reqXmlData, String openId) {
		//处理取消订阅事件
		this.userThirdInfoService.unSubscribeByWxOpenId(openId);
	}

	private void doSubscribe(String queryString, Map<String, String> reqXmlData, String openId) {
		// TODO：需要修改欢迎语
		WeixinUtils.sendTextMsgToUser("您好，欢迎关注红田家具商城", openId);

		String eventKey = reqXmlData.get(XMLUtils.c_xml_msg_key_EventKey);
		Matcher matcher = eventPattern.matcher(eventKey);
		WxMpUser wxMpUser = WeixinUtils.getWxMpUserByOpenId(openId);
		MapContext update;
		String roleStr = null;

		List<UserThirdInfo> thirdInfos = this.userThirdInfoService.findByWxOpenId(openId);
		UserThirdInfo thirdInfo = CollectionUtils.isEmpty(thirdInfos) ? null : thirdInfos.get(0);
		if (matcher.matches()) {
			//带场景串的关注
			String[] arr = eventKey.split("_");
			Map map = JsonUtil.fromJson(arr[1], Map.class);
			Object userObj = map.get("user");
			WebUtils.putDataToRequestMap("userId",userObj);//放在这里到活动日志里面用
			Integer flagStr = (Integer) map.get("flag");
			String userId = userObj == null ? null : userObj.toString();
			// 绑定店主
			if (null != flagStr) {
				int flagInt = Integer.valueOf(flagStr).intValue();
				if (Blank.BINDSHOPKEEPER.getValue() == flagInt) {
					if (LwxfStringUtils.isBlank(userId)) {
						throw new IllegalArgumentException("绑定店主时缺少用户id");
					}
					if (thirdInfo != null && !thirdInfo.getUserId().equals(userId) && thirdInfo.getWxIsBind()) {
						//将用户的之前绑定的信息删除
						this.userThirdInfoService.deleteByOppenId(openId);
					}
					User user = AppBeanInjector.userService.findById(userId);
					if (null == user || user.getRole().intValue() != UserRole.SHOPKEEPER.getValue()) { // 非店主身份不能进行绑定操作
						WeixinUtils.sendTextMsgToUser("非店主身份不能进行绑定操作", openId);
						return;
					}
					UserThirdInfo adminInfo = thirdInfo == null ? this.userThirdInfoService.findByUserId(userId) : thirdInfo;
					update = UserExtraUtil.createUserThirdInfoMapByWxMpUser(wxMpUser);
					update.put("userId", adminInfo.getUserId());
					update.put("wxIsBind", Boolean.TRUE);
					update.put("wxOpenId", openId);
					this.userThirdInfoService.updateByMapContext(update);
					return;
				}
				//添加店员
				if (Blank.ADDCLERK.getValue() == flagInt) {
					//该用户已经存在，但是是普通用户
					User user = null;
					if (CollectionUtils.isNotEmpty(thirdInfos)) {
						user = userService.findById(thirdInfo.getUserId());
						if (user != null && user.getRole().equals(UserRole.MEMBER.getValue())) {
							MapContext mapContext = MapContext.newOne();
							mapContext.put(WebConstant.KEY_ENTITY_ROLE, UserRole.CLERK.getValue());
							mapContext.put(WebConstant.KEY_ENTITY_ID, thirdInfo.getUserId());
							//如果该用户已经关注过，但是是未启用的用户
							if(user.getState().equals(UserState.DISABLED.getValue())){
								mapContext.put(WebConstant.KEY_ENTITY_STATE, UserState.ENABLED.getValue());
							}
							//修改第三方信息
							MapContext updateThirdInfo = MapContext.newOne();
							updateThirdInfo.put("userId", thirdInfo.getUserId());
							updateThirdInfo.put("wxIsBind", Boolean.TRUE);
							updateThirdInfo.put("wxIsSubscribe", Boolean.TRUE);
							this.userThirdInfoService.updateByMapContext(updateThirdInfo);

							AppBeanInjector.userService.updateByMapContext(mapContext);
							Company company = this.companyService.findById(WebUtils.getCurrCompanyId());
							ApplyClerkSuccessMsg weixinMsg = new ApplyClerkSuccessMsg(company.getName());
							weixinMsg.setUserInfo(user);
							weixinMsg.setTouser(thirdInfo.getWxOpenId());
							UserExtra userExtra = AppBeanInjector.userExtraService.findById(userId);
							if(null == userExtra){
								userExtra = new UserExtra();
								userExtra.setUserId(userId);
								userExtra.setUpdated(DateUtil.getSystemDate());
								UserExtraUtil.saltingPassword(userExtra, ShiroUtil.generateMD5(WebConstant.USER_DEFAULT_PASSWORD));
								AppBeanInjector.userExtraService.add(userExtra);
							}else{
								UserExtra saltExtra = new UserExtra();
								UserExtraUtil.saltingPassword(saltExtra,ShiroUtil.generateMD5(WebConstant.USER_DEFAULT_PASSWORD));
								saltExtra.setUserId(userId);
								AppBeanInjector.userExtraService.updateUserExtra(saltExtra);
							}
							WebUtils.putDataToRequestMap(WebConstant.WX_TEMPLATE_MSG_APPLY_SUCCESS,weixinMsg);
							return;
						}
					}
					//该用户不存在

					/*// 1. 创建用户账号（user）
					user = UserExtraUtil.createUserByWxMpUser(wxMpUser);
					user.setRole(UserRole.CLERK.getValue());//设置用户的角色为店员
					AppBeanInjector.userService.add(user);

					// 2. 创建用户扩展信息（UserExtro）
					UserExtra extra = new UserExtra();
					extra.setUserId(user.getId());
					UserExtraUtil.saltingPassword(extra, ShiroUtil.generateMD5(WebConstant.USER_DEFAULT_PASSWORD));
					AppBeanInjector.userExtraService.add(extra);

					// 3. 创建用户第三方账号信息（UserThirdInfo）
					thirdInfo = WeixinUtils.createUserThirdInfoByWxMpUser(wxMpUser);
					thirdInfo.setUserId(user.getId());
					thirdInfo.setWxIsSubscribe(Boolean.TRUE);
					this.userThirdInfoService.add(thirdInfo);*/
					this.createUser(wxMpUser,UserRole.CLERK);
					Company company = this.companyService.findById(WebUtils.getCurrCompanyId());
					ApplyClerkSuccessMsg weixinMsg = new ApplyClerkSuccessMsg(company.getName());
					weixinMsg.setUserInfo(user);
					weixinMsg.setTouser(thirdInfo.getWxOpenId());
					WebUtils.putDataToRequestMap(WebConstant.WX_TEMPLATE_MSG_APPLY_SUCCESS,weixinMsg);


					return;
				}
			}
		}
			//如果不是带有场景的关注  即添加普通用户或者更新信息
			if (!matcher.matches() && eventKey .equals("")) {
				// 创建新用户
				if (CollectionUtils.isEmpty(thirdInfos)) {
					/*// 1. 创建用户账号（user）
					User user = UserExtraUtil.createUserByWxMpUser(wxMpUser);
					user.setRole(UserRole.MEMBER.getValue());
					AppBeanInjector.userService.add(user);

					// 2. 创建用户扩展信息（UserExtro）
					UserExtra extra = new UserExtra();
					extra.setUserId(user.getId());
					UserExtraUtil.saltingPassword(extra, ShiroUtil.generateMD5(WebConstant.USER_DEFAULT_PASSWORD));
					AppBeanInjector.userExtraService.add(extra);

					// 3. 创建用户第三方账号信息（UserThirdInfo）
					thirdInfo = WeixinUtils.createUserThirdInfoByWxMpUser(wxMpUser);
					thirdInfo.setUserId(user.getId());
					this.userThirdInfoService.add(thirdInfo);*/

					this.createUser(wxMpUser,UserRole.MEMBER);
				} else {
					// 更新用户的订阅状态
					thirdInfo = thirdInfos.get(0);
					update = MapContext.newOne();
					update.put("wxOpenId", openId);
					update.put("wxIsBind",Boolean.TRUE);
					update.put("wxIsSubscribe", Boolean.TRUE);
					this.userThirdInfoService.updateByWxOpenId(update);

					// 更新用户的状态为启用
					update = MapContext.newOne();
					update.put(WebConstant.KEY_ENTITY_ID, thirdInfo.getUserId());
					update.put(WebConstant.KEY_ENTITY_STATE, UserState.ENABLED.getValue());
					update.put(WebConstant.KEY_ENTITY_ROLE, UserRole.MEMBER.getValue());
					AppBeanInjector.userService.updateByMapContext(update);
				}
			}
		}
	@Override
	public String createMenu(String menuJson) {
		JsonMapper json = JsonMapper.nonEmptyMapper();
		if(LwxfStringUtils.isEmpty(menuJson)){
			RequestResult result = ResultFactory.generateErrorResult(ErrorCodes.BIZ_REQUEST_PARAM_ERROR_10000, i18nUtil.getMessage("BIZ_REQUEST_PARAM_ERROR_10000"));
			return json.toJson(result);
		}

		try {
			wxMpService.getMenuService().menuCreate(menuJson);
			return WebConstant.REQUEST_RESULT_AJAX_EMPTY_OBJ;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return json.toJson(ResultFactory.generateErrorResult(ErrorCodes.SYS_UNKNOW_00000, i18nUtil.getMessage("SYS_UNKNOW_00000")));
	}


	@Override
	public String deleteMenu() {
		JsonMapper json = JsonMapper.nonEmptyMapper();
		try {
			wxMpService.getMenuService().menuDelete();
			return WebConstant.REQUEST_RESULT_AJAX_EMPTY_OBJ;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return json.toJson(ResultFactory.generateErrorResult(ErrorCodes.SYS_EXECUTE_FAIL_00001, i18nUtil.getMessage("SYS_EXECUTE_FAIL_00001")));
	}

	@Override
	public String getJSSDKConfig(String url) {
		JsonMapper json = JsonMapper.nonEmptyMapper();
		Map<String,Object> result = new HashMap<>();
		Date curDate = new Date();
		Long timestamp = curDate.getTime();
		String nonceStr= Base58.base58Uuid();
		// "jsapi_ticket={0}&noncestr={1}W&timestamp={2}&url={3}"
		String signatureStr = null;
		try {
			signatureStr = LwxfStringUtils.format(jsapi_signature_str, wxMpService.getJsapiTicket(), nonceStr,timestamp,url);
		} catch (WxErrorException e) {
			logger.error(e.getMessage());
		}
		String signature= LwxfStringUtils.sha1(signatureStr);
		result.put("appId", weixinCfg.getAppId());
		result.put("nonceStr", nonceStr);
		result.put("timestamp", timestamp);
		result.put("signature", signature);
		return json.toJson(ResultFactory.generateRequestResult(result));
	}

	@Override
	@Transactional
	public RequestResult unbindWeixinByUserId(String userId) {
		if(!userId.equals(WebUtils.getCurrUserId())){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_MB_ONLY_UNBIND_SELF_WX_10028,i18nUtil.getMessage("BIZ_MB_ONLY_UNBIND_SELF_WX_10028"));
		}
		User user = userService.findById(userId);
		if(null == user){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_USER_NOT_FOUND_10002,i18nUtil.getMessage("BIZ_USER_NOT_FOUND_10002"));
		}

		UserThirdInfo thirdInfo = this.userThirdInfoService.findByUserId(userId);
		if(null == thirdInfo || !thirdInfo.getWxIsBind()){
			// 已解绑处理
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_MB_NOT_BIND_WX_10029,i18nUtil.getMessage("BIZ_MB_NOT_BIND_WX_10029"));
		}

		this.userThirdInfoService.unbindWeixinByUserId(userId);

		//解绑后如果用户不存在其他账号则发送退出登陆标识
		if(LwxfStringUtils.isBlank(user.getEmail()) && LwxfStringUtils.isBlank(user.getMobile())){
			Map map = new HashMap(1);
			map.put("logout", Boolean.TRUE);
			return ResultFactory.generateRequestResult(map);
		}

		return ResultFactory.generateSuccessResult();
	}

	/**
	 * 文件上传的方法
	 * @param filePath
	 * @param
	 * @return
	 */
	@Transactional
	@Override
	public String upload(String filePath,String fileType){
		//返回结果
		JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
		String result=null;
		File file=new File(filePath);
		try {
			if(!file.exists()||!file.isFile()){
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String token=wxMpService.getAccessToken();
			String urlString="https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+token+"&type="+fileType;
			URL url=new URL(urlString);
			HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");//以POST方式提交表单
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);//POST方式不能使用缓存
			//设置请求头信息
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			//设置边界
			String BOUNDARY="----------"+System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			//请求正文信息
			//第一部分
			StringBuilder sb=new StringBuilder();
			sb.append("--");//必须多两条道
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName()+"\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			System.out.println("sb:"+sb);

			//获得输出流
			OutputStream out=new DataOutputStream(conn.getOutputStream());
			//输出表头
			out.write(sb.toString().getBytes("UTF-8"));
			//文件正文部分
			//把文件以流的方式 推送道URL中
			DataInputStream din=new DataInputStream(new FileInputStream(file));
			int bytes=0;
			byte[] buffer=new byte[1024];
			while((bytes=din.read(buffer))!=-1){
				out.write(buffer,0,bytes);
			}
			din.close();
			//结尾部分
			byte[] foot=("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");//定义数据最后分割线
			out.write(foot);
			out.flush();
			out.close();
			if(HttpsURLConnection.HTTP_OK==conn.getResponseCode()){

				StringBuffer strbuffer=null;
				BufferedReader reader=null;
				try {
					strbuffer=new StringBuffer();
					reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String lineString=null;
					while((lineString=reader.readLine())!=null){
						strbuffer.append(lineString);

					}
					if(result==null){
						result=strbuffer.toString();
						System.out.println("result:"+result);
					}
				} catch (IOException e) {
					System.out.println("发送POST请求出现异常！"+e);
					e.printStackTrace();
				}finally{
					if(reader!=null){
						reader.close();
					}
				}

			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,Object> restr =  jsonMapper.fromJson(result,Map.class);
		logger.info("返回的字符串是="+restr);
		if (restr==null){
			logger.error("restr不能为空");
		}
		String media_id = null;
		if (restr.containsKey("thumb")){
			media_id = restr.get("thumb_media_id").toString();
		}else {
			media_id = restr.get("media_id").toString();
		}
		return media_id;
	}

	@Override
	@Transactional
	public UserInfoObj createUser(WxMpUser wxMpUser, UserRole userRole) {
		// 1. 创建用户账号（user）
		User user = UserExtraUtil.createUserByWxMpUser(wxMpUser);
		user.setRole(userRole.getValue());
		AppBeanInjector.userService.add(user);

		// 2. 创建用户扩展信息（UserExtro）
		UserExtra extra = new UserExtra();
		extra.setUserId(user.getId());
		UserExtraUtil.saltingPassword(extra, ShiroUtil.generateMD5(WebConstant.USER_DEFAULT_PASSWORD));
		AppBeanInjector.userExtraService.add(extra);

		// 3. 创建用户第三方账号信息（UserThirdInfo）
		UserThirdInfo thirdInfo = WeixinUtils.createUserThirdInfoByWxMpUser(wxMpUser);
		thirdInfo.setUserId(user.getId());
		AppBeanInjector.userThirdInfoService.add(thirdInfo);
		AppBeanInjector.redisUtils.hPut(RedisConstant.PLATFORM_TAG, wxMpUser.getOpenId(), Integer.valueOf(1));
		return UserInfoObj.newOne(user,extra,thirdInfo);
	}
}
