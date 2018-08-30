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
 * 功能：system_activity 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-30 08:51
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "system_activity",displayName = "system_activity")
public class SystemActivity extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "company_id",displayName = "公司id")
	private String companyId;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,updatable = false,name = "event",displayName = "活动事件：来源于SystemActivityEvent.java")
	private String event;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "操作人id")
	private String creator;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "r1",displayName = "所操作资源的id")
	private String r1;
	@Column(type = Types.CHAR,length = 13,updatable = false,name = "r2",displayName = "所操作资源的父资源id")
	private String r2;
	@Column(type = Types.LONGVARCHAR,updatable = false,name = "r3",displayName = "所操作的资源内容")
	private String r3;
	@Column(type = Types.TINYINT,nullable = false,updatable = false,name = "scope",displayName = "资源范围：来源与ResourceScope.java的定义")
	private Integer scope;

	public SystemActivity() {
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
		if (this.event == null) {
			validResult.put("event", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.event) > 20) {
				validResult.put("event", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.creator == null) {
			validResult.put("creator", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.creator) > 13) {
				validResult.put("creator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.r1 == null) {
			validResult.put("r1", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.r1) > 13) {
				validResult.put("r1", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.r2) > 13) {
			validResult.put("r2", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.scope == null) {
			validResult.put("scope", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList();

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

	public void setEvent(String event){
		this.event=event;
	}

	public String getEvent(){
		return event;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setCreator(String creator){
		this.creator=creator;
	}

	public String getCreator(){
		return creator;
	}

	public void setR1(String r1){
		this.r1=r1;
	}

	public String getR1(){
		return r1;
	}

	public void setR2(String r2){
		this.r2=r2;
	}

	public String getR2(){
		return r2;
	}

	public void setR3(String r3){
		this.r3=r3;
	}

	public String getR3(){
		return r3;
	}

	public void setScope(Integer scope){
		this.scope=scope;
	}

	public Integer getScope(){
		return scope;
	}
}