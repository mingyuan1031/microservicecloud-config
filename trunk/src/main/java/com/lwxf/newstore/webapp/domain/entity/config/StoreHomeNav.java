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
 * 功能：store_home_nav 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-08-15 04:13 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "store_home_nav",displayName = "store_home_nav")
public class StoreHomeNav extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 20,nullable = false,name = "name",displayName = "菜单名称")
	private String name;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "url",displayName = "地址链接")
	private String url;
	@Column(type = Types.BIT,nullable = false,name = "status",displayName = "状态， 0:显示, 1:不显示")
	private Boolean status;
	@Column(type = Types.INTEGER,nullable = false,name = "sequence",displayName = "图标的排序")
	private Integer sequence;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "image",displayName = "图标")
	private String image;
	@Column(type = Types.BIT,defaultValue = "0",nullable = false,name = "is_outer_link",displayName = "内部跳转或外部跳转")
	private Boolean outerLink;

    public StoreHomeNav() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.name) > 20) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.url == null) {
			validResult.put("url", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.url) > 50) {
				validResult.put("url", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.status == null) {
			validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.sequence == null) {
			validResult.put("sequence", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.image == null) {
			validResult.put("image", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.image) > 50) {
				validResult.put("image", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.outerLink == null) {
			validResult.put("outerLink", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","name","url","status","sequence","image","outerLink");

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
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("sequence")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("sequence",String.class))) {
				validResult.put("sequence", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("outerLink")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("outerLink",String.class))) {
				validResult.put("outerLink", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("name")) {
			if (map.getTypedValue("name",String.class)  == null) {
				validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("name",String.class)) > 20) {
					validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("url")) {
			if (map.getTypedValue("url",String.class)  == null) {
				validResult.put("url", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("url",String.class)) > 50) {
					validResult.put("url", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("status")) {
			if (map.get("status")  == null) {
				validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("sequence")) {
			if (map.get("sequence")  == null) {
				validResult.put("sequence", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("image")) {
			if (map.getTypedValue("image",String.class)  == null) {
				validResult.put("image", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("image",String.class)) > 50) {
					validResult.put("image", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("outerLink")) {
			if (map.get("outerLink")  == null) {
				validResult.put("outerLink", ErrorCodes.VALIDATE_NOTNULL);
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

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return url;
	}

	public void setStatus(Boolean status){
		this.status=status;
	}

	public Boolean getStatus(){
		return status;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}

	public Integer getSequence(){
		return sequence;
	}

	public void setImage(String image){
		this.image=image;
	}

	public String getImage(){
		return image;
	}

	public void setOuterLink(Boolean outerLink){
		this.outerLink=outerLink;
	}

	public Boolean getOuterLink(){
		return outerLink;
	}
}
