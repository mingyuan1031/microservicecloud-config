package com.lwxf.newstore.webapp.domain.entity.config;
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
 * 功能：system_config 实体类
 *
 * @author：zhangjiale(zjl869319827@outlook.com)
 * @created：2018-07-05 03:50 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "system_config",displayName = "system_config")
public class SystemConfig extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,name = "seo_keywords",displayName = "网站热搜词")
	private String seoKeywords;
	@Column(type = Types.VARCHAR,length = 300,name = "welcome",displayName = "进入微信时候的欢迎语")
	private String welcome;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "company_id",displayName = "公司id")
	private String companyId;
	@Column(type = Types.VARCHAR,length = 1000,name = "wxmenus",displayName = "微信菜单")
	private String wxmenus;

    public SystemConfig() {
     }

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (LwxfStringUtils.getStringLength(this.seoKeywords) > 50) {
			validResult.put("seoKeywords", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.welcome) > 300) {
			validResult.put("welcome", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.companyId == null) {
			validResult.put("companyId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.companyId) > 13) {
				validResult.put("companyId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.wxmenus) > 1000) {
			validResult.put("wxmenus", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}

		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("seoKeywords","welcome","companyId","wxmenus");

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
		if(map.containsKey("seoKeywords")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("seoKeywords",String.class)) > 50) {
				validResult.put("seoKeywords", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("welcome")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("welcome",String.class)) > 300) {
				validResult.put("welcome", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("companyId")) {
			if (map.getTypedValue("companyId",String.class)  == null) {
				validResult.put("companyId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("companyId",String.class)) > 13) {
					validResult.put("companyId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("wxmenus")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("wxmenus",String.class)) > 1000) {
				validResult.put("wxmenus", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setSeoKeywords(String seoKeywords){
		this.seoKeywords=seoKeywords;
	}

	public String getSeoKeywords(){
		return seoKeywords;
	}

	public void setWelcome(String welcome){
		this.welcome=welcome;
	}

	public String getWelcome(){
		return welcome;
	}

	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public String getWxmenus() {
		return wxmenus;
	}

	public void setWxmenus(String wxmenus) {
		this.wxmenus = wxmenus;
	}
}
