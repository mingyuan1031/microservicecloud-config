package com.lwxf.newstore.webapp.domain.entity.news;
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
 * 功能：news 实体类
 *
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-20 09:30 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "news",displayName = "news")
public class News extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "资源标题")
	private String name;
	@Column(type = Types.VARCHAR,length = 100,name = "path",displayName = "html资源的路径")
	private String path;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "news_type_id",displayName = "资讯所属的分类id")
	private String newsTypeId;
	@Column(type = Types.BIT,nullable = false,name = "is_top",displayName = "是否置顶 true 置顶 false 不置顶")
	private Boolean top;
	@Column(type = TypesExtend.DATETIME,name = "toped",displayName = "置顶时间 用于多个置顶同时存在时 排序使用")
	private Date toped;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否禁用 true 禁用 false  启用")
	private Boolean disabled;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "创建人id")
	private String creator;

    public News() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.name) > 50) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.path) > 100) {
			validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.newsTypeId == null) {
			validResult.put("newsTypeId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.newsTypeId) > 13) {
				validResult.put("newsTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.top == null) {
			validResult.put("top", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
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
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("name","path","top","toped","disabled");

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
		if(map.containsKey("top")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("top",String.class))) {
				validResult.put("top", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("toped")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("toped",String.class))) {
				validResult.put("toped", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("disabled")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("disabled",String.class))) {
				validResult.put("disabled", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("name")) {
			if (map.getTypedValue("name",String.class)  == null) {
				validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("name",String.class)) > 50) {
					validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("path")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("path",String.class)) > 100) {
				validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("top")) {
			if (map.get("top")  == null) {
				validResult.put("top", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("disabled")) {
			if (map.get("disabled")  == null) {
				validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
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

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setNewsTypeId(String newsTypeId){
		this.newsTypeId=newsTypeId;
	}

	public String getNewsTypeId(){
		return newsTypeId;
	}

	public void setTop(Boolean top){
		this.top=top;
	}

	public Boolean getTop(){
		return top;
	}

	public void setToped(Date toped){
		this.toped=toped;
	}

	public Date getToped(){
		return toped;
	}

	public void setDisabled(Boolean disabled){
		this.disabled=disabled;
	}

	public Boolean getDisabled(){
		return disabled;
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
}
