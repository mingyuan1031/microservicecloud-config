package com.lwxf.newstore.webapp.domain.entity.order;
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
 * 功能：city_area 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-02 11:27 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "city_area",displayName = "city_area")
public class CityArea extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "parent_id",displayName = "")
	private String parentId;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "")
	private String name;
	@Column(type = Types.VARCHAR,length = 100,name = "merger_name",displayName = "ʡ")
	private String mergerName;
	@Column(type = Types.VARCHAR,length = 100,name = "short_name",displayName = "")
	private String shortName;
	@Column(type = Types.VARCHAR,length = 100,name = "merger_short_name",displayName = "")
	private String mergerShortName;
	@Column(type = Types.TINYINT,name = "level_type",displayName = "")
	private Byte levelType;
	@Column(type = Types.VARCHAR,length = 5,name = "city_code",displayName = "")
	private String cityCode;
	@Column(type = Types.VARCHAR,length = 6,name = "zip_code",displayName = "")
	private String zipCode;
	@Column(type = Types.VARCHAR,length = 50,name = "name_pinyin",displayName = "")
	private String namePinyin;
	@Column(type = Types.VARCHAR,length = 10,name = "name_jianpin",displayName = "")
	private String nameJianpin;
	@Column(type = Types.CHAR,length = 1,name = "fisrt_char",displayName = "")
	private String fisrtChar;
	@Column(type = Types.FLOAT,name = "lng",displayName = "")
	private Float lng;
	@Column(type = Types.FLOAT,name = "lat",displayName = "γ")
	private Float lat;

    public CityArea() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.parentId == null) {
			validResult.put("parentId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.parentId) > 13) {
				validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.name) > 50) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.mergerName) > 100) {
			validResult.put("mergerName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.shortName) > 100) {
			validResult.put("shortName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.mergerShortName) > 100) {
			validResult.put("mergerShortName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.cityCode) > 5) {
			validResult.put("cityCode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.zipCode) > 6) {
			validResult.put("zipCode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.namePinyin) > 50) {
			validResult.put("namePinyin", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.nameJianpin) > 10) {
			validResult.put("nameJianpin", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.fisrtChar) > 1) {
			validResult.put("fisrtChar", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","parentId","name","mergerName","shortName","mergerShortName","levelType","cityCode","zipCode","namePinyin","nameJianpin","fisrtChar","lng","lat");

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
		if(map.containsKey("levelType")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("levelType",String.class))) {
				validResult.put("levelType", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("lng")) {
			if (!DataValidatorUtils.isFloat(map.getTypedValue("lng",String.class))) {
				validResult.put("lng", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("lat")) {
			if (!DataValidatorUtils.isFloat(map.getTypedValue("lat",String.class))) {
				validResult.put("lat", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("parentId")) {
			if (map.getTypedValue("parentId",String.class)  == null) {
				validResult.put("parentId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("parentId",String.class)) > 13) {
					validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
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
		if(map.containsKey("mergerName")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("mergerName",String.class)) > 100) {
				validResult.put("mergerName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("shortName")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("shortName",String.class)) > 100) {
				validResult.put("shortName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("mergerShortName")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("mergerShortName",String.class)) > 100) {
				validResult.put("mergerShortName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("cityCode")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("cityCode",String.class)) > 5) {
				validResult.put("cityCode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("zipCode")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("zipCode",String.class)) > 6) {
				validResult.put("zipCode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("namePinyin")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("namePinyin",String.class)) > 50) {
				validResult.put("namePinyin", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("nameJianpin")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("nameJianpin",String.class)) > 10) {
				validResult.put("nameJianpin", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("fisrtChar")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("fisrtChar",String.class)) > 1) {
				validResult.put("fisrtChar", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setParentId(String parentId){
		this.parentId=parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setMergerName(String mergerName){
		this.mergerName=mergerName;
	}

	public String getMergerName(){
		return mergerName;
	}

	public void setShortName(String shortName){
		this.shortName=shortName;
	}

	public String getShortName(){
		return shortName;
	}

	public void setMergerShortName(String mergerShortName){
		this.mergerShortName=mergerShortName;
	}

	public String getMergerShortName(){
		return mergerShortName;
	}

	public void setLevelType(Byte levelType){
		this.levelType=levelType;
	}

	public Byte getLevelType(){
		return levelType;
	}

	public void setCityCode(String cityCode){
		this.cityCode=cityCode;
	}

	public String getCityCode(){
		return cityCode;
	}

	public void setZipCode(String zipCode){
		this.zipCode=zipCode;
	}

	public String getZipCode(){
		return zipCode;
	}

	public void setNamePinyin(String namePinyin){
		this.namePinyin=namePinyin;
	}

	public String getNamePinyin(){
		return namePinyin;
	}

	public void setNameJianpin(String nameJianpin){
		this.nameJianpin=nameJianpin;
	}

	public String getNameJianpin(){
		return nameJianpin;
	}

	public void setFisrtChar(String fisrtChar){
		this.fisrtChar=fisrtChar;
	}

	public String getFisrtChar(){
		return fisrtChar;
	}

	public void setLng(Float lng){
		this.lng=lng;
	}

	public Float getLng(){
		return lng;
	}

	public void setLat(Float lat){
		this.lat=lat;
	}

	public Float getLat(){
		return lat;
	}
}
