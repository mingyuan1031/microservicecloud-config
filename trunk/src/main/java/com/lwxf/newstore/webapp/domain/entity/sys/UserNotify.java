package com.lwxf.newstore.webapp.domain.entity.sys;
import java.util.*;
import java.sql.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.annotation.Column;

import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
/**
 * 功能：user_notify 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-30 08:51
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "user_notify",displayName = "user_notify")
public class UserNotify extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "company_id",displayName = "公司id")
	private String companyId;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "user_id",displayName = "")
	private String userId;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "system_activity_id",displayName = "系统活动日志id")
	private String systemActivityId;
	@Column(type = Types.TINYINT,updatable = false,name = "grouping",displayName = "消息分组：来源于NotifyGrouping.java")
	private Integer grouping;
	@Column(type = Types.BIT,nullable = false,name = "is_readed",displayName = "是否已读")
	private Boolean readed;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = TypesExtend.DATETIME,name = "top_time",displayName = "消息置顶的时间")
	private Date topTime;

	public UserNotify() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.companyId == null) {
			validResult.put("companyId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.companyId) > 13) {
				validResult.put("companyId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.userId == null) {
			validResult.put("userId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.userId) > 13) {
				validResult.put("userId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.systemActivityId == null) {
			validResult.put("systemActivityId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.systemActivityId) > 13) {
				validResult.put("systemActivityId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.readed == null) {
			validResult.put("readed", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("readed","topTime");

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
		if(map.containsKey("readed")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("readed",String.class))) {
				validResult.put("readed", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("topTime")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("topTime",String.class))) {
				validResult.put("topTime", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("readed")) {
			if (map.get("readed")  == null) {
				validResult.put("readed", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setSystemActivityId(String systemActivityId){
		this.systemActivityId=systemActivityId;
	}

	public String getSystemActivityId(){
		return systemActivityId;
	}

	public void setGrouping(Integer grouping){
		this.grouping=grouping;
	}

	public Integer getGrouping(){
		return grouping;
	}

	public void setReaded(Boolean readed){
		this.readed=readed;
	}

	public Boolean getReaded(){
		return readed;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setTopTime(Date topTime){
		this.topTime=topTime;
	}

	public Date getTopTime(){
		return topTime;
	}
}
