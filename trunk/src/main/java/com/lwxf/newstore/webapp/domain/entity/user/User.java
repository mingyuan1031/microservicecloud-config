package com.lwxf.newstore.webapp.domain.entity.user;

import java.sql.Types;
import java.util.*;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.ValidateUtils;
import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.enums.user.UserSex;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;
/**
 * 功能：user 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2017-11-17 11:30
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "user",displayName = "user")
public class User extends IdEntity  {
	private static final long serialVersionUID =3761413225841761755L;
	@Column(type = Types.VARCHAR,length = 50,name = "name",displayName = "姓名：显示用")
	private String name;
	@Column(type = Types.VARCHAR,length = 50,name = "email",displayName = "邮箱")
	private String email;
	@Column(type = Types.VARCHAR,length = 200,nullable = false,name = "avatar",displayName = "")
	private String avatar;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "")
	private Date created;
	@Column(type = TypesExtend.DATETIME,name = "last_login",displayName = "")
	private Date lastLogin;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "time_zone",displayName = "时区")
	private String timeZone;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "language",displayName = "语言")
	private String language;
	@Column(type = TypesExtend.DATETIME,name = "birthday",displayName = "生日")
	private Date birthday;
	@Column(type = Types.VARCHAR,length = 20,name = "mobile",displayName = "")
	private String mobile;
	@Column(type = Types.INTEGER,defaultValue = "0",nullable = false,name = "state",displayName = "")
	private Byte state;
	@Column(type = Types.VARCHAR,length = 200,name = "background",displayName = "工作台背景图")
	private String background;
	@Column(type = Types.VARCHAR,length = 50,name = "username",displayName = "用户登录名")
	private String username;
	@Column(type = Types.TINYINT, length = 1,name = "role",displayName = "角色")
	private Integer role;
	@Column(type = Types.TINYINT, length = 1,name = "sex",displayName = "性别")
	private Integer sex;

	public User() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (LwxfStringUtils.getStringLength(this.name) > 50) {
			validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.email) > 50) {
			validResult.put("email", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		else if(!ValidateUtils.isEmail(this.email)){
			validResult.put("email", ErrorCodes.VALIDATE_INVALID_EMAIL);
		}
		if (this.avatar == null) {
			validResult.put("avatar", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.avatar) > 200) {
				validResult.put("avatar", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.timeZone == null) {
			validResult.put("timeZone", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.timeZone) > 50) {
				validResult.put("timeZone", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.language == null) {
			validResult.put("language", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.language) > 50) {
				validResult.put("language", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.mobile) > 20) {
			validResult.put("mobile", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(this.mobile!=null){
			if(!ValidateUtils.isMobile(this.mobile)){
				validResult.put("mobile", ErrorCodes.VALIDATE_INVALID_MOBILE_NO);
			}
		}
		if (this.state == null) {
			validResult.put("state", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.background) > 200) {
			validResult.put("background", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.username == null) {
			validResult.put("username", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.username) > 50) {
				validResult.put("username", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.role == null) {
			validResult.put("role", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.sex == null) {
			validResult.put("sex", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if(!UserSex.validValue(this.sex)){
				validResult.put("sex", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","name","email","avatar","lastLogin","timeZone","language","metadata","sex","birthday","mobile","state","background","username");

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
		if(map.containsKey("lastLogin")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("lastLogin",String.class))) {
				validResult.put("lastLogin", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("birthday")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("birthday",String.class))) {
				validResult.put("birthday", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("state")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("state",String.class))) {
				validResult.put("state", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("name")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("name",String.class)) > 50) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("email")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("email",String.class)) > 50) {
				validResult.put("email", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
			else if(!ValidateUtils.isEmail(map.getTypedValue("email",String.class))){
				validResult.put("email", ErrorCodes.VALIDATE_INVALID_EMAIL);
			}
		}
		if(map.containsKey("avatar")) {
			if (map.getTypedValue("avatar",String.class)  == null) {
				validResult.put("avatar", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("avatar",String.class)) > 200) {
					validResult.put("avatar", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("timeZone")) {
			if (map.getTypedValue("timeZone",String.class)  == null) {
				validResult.put("timeZone", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("timeZone",String.class)) > 50) {
					validResult.put("timeZone", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("language")) {
			if (map.getTypedValue("language",String.class)  == null) {
				validResult.put("language", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("language",String.class)) > 50) {
					validResult.put("language", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("metadata")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("metadata",String.class)) > 100) {
				validResult.put("metadata", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("mobile")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("mobile",String.class)) > 20) {
				validResult.put("mobile", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
//			else if(!ValidateUtils.isMobile(map.getTypedValue("mobile",String.class))){
//				validResult.put("mobile", ErrorCodes.VALIDATE_INVALID_MOBILE_NO);
//			}
		}
		if(map.containsKey("state")) {
			if (map.get("state")  == null) {
				validResult.put("state", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("background")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("background",String.class)) > 200) {
				validResult.put("background", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("username")) {
			if (map.get("username")  == null) {
				validResult.put("username", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("username",String.class)) > 50) {
					validResult.put("username", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("role")) {
			if (map.get("role")  == null) {
				validResult.put("role", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if(!UserRole.validValue(Integer.valueOf(map.get("role").toString()).intValue())){
					validResult.put("role", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				}
			}
		}
		if(map.containsKey("sex")) {
			if (map.get("sex")  == null) {
				validResult.put("sex", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if(!UserSex.validValue(Integer.valueOf(map.get("sex").toString()).intValue())){
					validResult.put("sex", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				}
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return email;
	}

	public void setAvatar(String avatar){
		this.avatar=avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setLastLogin(Date lastLogin){
		this.lastLogin=lastLogin;
	}

	public Date getLastLogin(){
		return lastLogin;
	}

	public void setTimeZone(String timeZone){
		this.timeZone=timeZone;
	}

	public String getTimeZone(){
		return timeZone;
	}

	public void setLanguage(String language){
		this.language=language;
	}

	public String getLanguage(){
		return language;
	}

	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}

	public Date getBirthday(){
		return birthday;
	}

	public void setMobile(String mobile){
		this.mobile=mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setState(Byte state){
		this.state=state;
	}

	public Byte getState(){
		return state;
	}

	public void setBackground(String background){
		this.background=background;
	}

	public String getBackground(){
		return background;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
