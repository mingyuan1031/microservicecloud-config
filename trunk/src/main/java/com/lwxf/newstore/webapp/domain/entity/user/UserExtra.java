package com.lwxf.newstore.webapp.domain.entity.user;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.annotation.ForeignKey;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
/**
 * 功能：user_extra 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-05-25 09:52
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "user_extra",displayName = "user_extra")
public class UserExtra {
	@Column(type = Types.CHAR,length = 12,nullable = false,name = "user_id",displayName = "")
	@ForeignKey(refEntityClass = User.class,refFieldName = "id")
	private String userId;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,updatable = false,name = "salt",displayName = "")
	private String salt;
	@Column(type = Types.VARCHAR,length = 100,name = "password",displayName = "")
	private String password;
	@Column(type = TypesExtend.DATETIME,nullable = false,name = "updated",displayName = "更新日期，创建时候等于创建日期")
	private Date updated;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "token",displayName = "")
	private String token;

	public UserExtra() {
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
		if (this.salt == null) {
			validResult.put("salt", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.salt) > 50) {
				validResult.put("salt", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.password) > 100) {
			validResult.put("password", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.updated == null) {
			validResult.put("updated", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.token == null) {
			validResult.put("token", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.token) > 100) {
				validResult.put("token", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("userId","password","len","pp","updated","token");

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
		if(map.containsKey("len")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("len",String.class))) {
				validResult.put("len", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("updated")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("updated",String.class))) {
				validResult.put("updated", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("password")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("password",String.class)) > 100) {
				validResult.put("password", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("pp")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("pp",String.class)) > 4) {
				validResult.put("pp", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("updated")) {
			if (map.get("updated")  == null) {
				validResult.put("updated", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("token")) {
			if (map.getTypedValue("token",String.class)  == null) {
				validResult.put("token", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("token",String.class)) > 100) {
					validResult.put("token", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
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

	public void setSalt(String salt){
		this.salt=salt;
	}

	public String getSalt(){
		return salt;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return password;
	}

	public void setUpdated(Date updated){
		this.updated=updated;
	}

	public Date getUpdated(){
		return updated;
	}

	public void setToken(String token){
		this.token=token;
	}

	public String getToken(){
		return token;
	}
}
