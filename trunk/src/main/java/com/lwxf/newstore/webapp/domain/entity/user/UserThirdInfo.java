package com.lwxf.newstore.webapp.domain.entity.user;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.annotation.Id;
import com.lwxf.mybatis.annotation.UniqueConstraint;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
/**
 * 功能：user_third_info 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-05-25 10:50
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "user_third_info",uniqueConstraints = {@UniqueConstraint(fieldNames = {"wxOpenId"})},displayName = "user_third_info")
public class UserThirdInfo {
	@Id(type = Types.CHAR,length = 12,name = "user_id")
	private String userId;
	@Column(type = Types.VARCHAR,length = 50,name = "wx_open_id",displayName = "openid 普通用户的标识，对当前开发者帐号唯一。一个openid对应一个公众号")
	private String wxOpenId;
	@Column(type = Types.VARCHAR,length = 50,name = "wx_nickname",displayName = "微信昵称")
	private String wxNickname;
	@Column(type = Types.VARCHAR,length = 50,name = "wx_union_id",displayName = "unionid 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的")
	private String wxUnionId;
	@Column(type = Types.BIT,name = "wx_is_subscribe",displayName = "0-未关注,1－关注。发送微信消息时候需要判断")
	private Boolean wxIsSubscribe;
	@Column(type = Types.BIT,defaultValue = "0",nullable = false,name = "wx_is_bind",displayName = "微信是否绑定:0-否,1-是")
	private Boolean wxIsBind;
	@Column(type = Types.BIT,nullable = false,name = "email_is_bind",displayName = "信箱是否绑定:0-否,1-是")
	private Boolean emailIsBind;
	@Column(type = Types.BIT,nullable = false,name = "mobile_is_bind",displayName = "手机号是否绑定:0-否,1-是")
	private Boolean mobileIsBind;

	public UserThirdInfo() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.userId == null) {
			validResult.put("userId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.userId) > 12) {
				validResult.put("userId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.wxOpenId) > 50) {
			validResult.put("wxOpenId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.wxNickname) > 50) {
			validResult.put("wxNickname", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.wxUnionId) > 50) {
			validResult.put("wxUnionId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.wxIsBind == null) {
			validResult.put("wxIsBind", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.emailIsBind == null) {
			validResult.put("emailIsBind", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.mobileIsBind == null) {
			validResult.put("mobileIsBind", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("userId","wxOpenId","wxNickname","wxUnionId","wxIsSubscribe","wxIsBind","emailIsBind","mobileIsBind");

	public static RequestResult validateFields(MapContext map) {
		Map<String, String> validResult = new HashMap<>();
		if(map.size()==0){
			return ResultFactory.generateErrorResult("error",ErrorCodes.VALIDATE_NOTNULL);
		}
		boolean flag;
		Set<String> mapSet = map.keySet();
		flag = propertiesList.containsAll(mapSet);
		if(!flag){
			return ResultFactory.generateErrorResult("error",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
		}
		if(map.containsKey("wxIsSubscribe")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("wxIsSubscribe",String.class))) {
				validResult.put("wxIsSubscribe", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("wxIsBind")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("wxIsBind",String.class))) {
				validResult.put("wxIsBind", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("emailIsBind")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("emailIsBind",String.class))) {
				validResult.put("emailIsBind", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("mobileIsBind")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("mobileIsBind",String.class))) {
				validResult.put("mobileIsBind", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("userId")) {
			if (map.getTypedValue("userId",String.class)  == null) {
				validResult.put("userId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("userId",String.class)) > 12) {
					validResult.put("userId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("wxOpenId")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("wxOpenId",String.class)) > 50) {
				validResult.put("wxOpenId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("wxNickname")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("wxNickname",String.class)) > 50) {
				validResult.put("wxNickname", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("wxUnionId")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("wxUnionId",String.class)) > 50) {
				validResult.put("wxUnionId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("wxIsBind")) {
			if (map.get("wxIsBind")  == null) {
				validResult.put("wxIsBind", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("emailIsBind")) {
			if (map.get("emailIsBind")  == null) {
				validResult.put("emailIsBind", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("mobileIsBind")) {
			if (map.get("mobileIsBind")  == null) {
				validResult.put("mobileIsBind", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setWxOpenId(String wxOpenId){
		this.wxOpenId=wxOpenId;
	}

	public String getWxOpenId(){
		return wxOpenId;
	}

	public void setWxNickname(String wxNickname){
		this.wxNickname=wxNickname;
	}

	public String getWxNickname(){
		return wxNickname;
	}

	public void setWxUnionId(String wxUnionId){
		this.wxUnionId=wxUnionId;
	}

	public String getWxUnionId(){
		return wxUnionId;
	}

	public void setWxIsSubscribe(Boolean wxIsSubscribe){
		this.wxIsSubscribe=wxIsSubscribe;
	}

	public Boolean getWxIsSubscribe(){
		return wxIsSubscribe;
	}

	public void setWxIsBind(Boolean wxIsBind){
		this.wxIsBind=wxIsBind;
	}

	public Boolean getWxIsBind(){
		return wxIsBind;
	}

	public void setEmailIsBind(Boolean emailIsBind){
		this.emailIsBind=emailIsBind;
	}

	public Boolean getEmailIsBind(){
		return emailIsBind;
	}

	public void setMobileIsBind(Boolean mobileIsBind){
		this.mobileIsBind=mobileIsBind;
	}

	public Boolean getMobileIsBind(){
		return mobileIsBind;
	}
}
