package com.lwxf.newstore.webapp.domain.entity.scheme;
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

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;

/**
 * 功能：scheme_praise 实体类
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 02:32 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "scheme_praise",displayName = "scheme_praise")
public class SchemePraise extends IdEntity {
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "scheme_id",displayName = "设计方案的id")
	private String schemeId;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "member_id",displayName = "成员id")
	private String memberId;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;

    public SchemePraise() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.schemeId == null) {
			validResult.put("schemeId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.schemeId) > 13) {
				validResult.put("schemeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.memberId == null) {
			validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.memberId) > 13) {
				validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
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

	private final static List<String> propertiesList = Arrays.asList("schemeId","memberId");

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
		if(map.containsKey("schemeId")) {
			if (map.getTypedValue("schemeId",String.class)  == null) {
				validResult.put("schemeId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("schemeId",String.class)) > 13) {
					validResult.put("schemeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("memberId")) {
			if (map.getTypedValue("memberId",String.class)  == null) {
				validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("memberId",String.class)) > 13) {
					validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setSchemeId(String schemeId){
		this.schemeId=schemeId;
	}

	public String getSchemeId(){
		return schemeId;
	}

	public void setMemberId(String memberId){
		this.memberId=memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}
}
