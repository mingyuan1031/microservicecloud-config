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
 * 功能：store_config 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-11 03:31 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "store_config",displayName = "store_config")
public class StoreConfig extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "name",displayName = "网站名称")
	private String name;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "company_id",displayName = "公司表的ID")
	private String companyId;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "logo",displayName = "网站LOGO")
	private String logo;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "header",displayName = "名片图片")
	private String header;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "scope",displayName = "经营范围")
	private String scope;
	@Column(type = Types.VARCHAR,length = 50,name = "linkman",displayName = "联系人")
	private String linkman;
	@Column(type = Types.VARCHAR,length = 20,name = "service_call",displayName = "客服电话")
	private String serviceCall;
	@Column(type = Types.VARCHAR,length = 20,name = "reservation_call",displayName = "预约的手机号")
	private String reservationCall;
	@Column(type = Types.VARCHAR,length = 100,name = "qrcode",displayName = "店铺二维码")
	private String qrcode;
	@Column(type = Types.VARCHAR,length = 150,name = "address",displayName = "地址+行政区")
	private String address;
	@Column(type = Types.VARCHAR,length = 20,name = "service_qq",displayName = "客服QQ")
	private String serviceQq;
	@Column(type = Types.VARCHAR,length = 100,name = "poster",displayName = "预约图片")
	private String poster;
	@Column(type = Types.INTEGER,name = "pinkage",displayName = "满减包邮")
	private Integer pinkage;
	@Column(type = Types.VARCHAR,length = 100,name = "storecard",displayName = "店铺名片")
	private String storecard;

    public StoreConfig() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.name) > 100) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.companyId == null) {
			validResult.put("companyId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.companyId) > 13) {
				validResult.put("companyId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.logo == null) {
			validResult.put("logo", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.logo) > 100) {
				validResult.put("logo", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.header == null) {
			validResult.put("header", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.header) > 100) {
				validResult.put("header", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.scope == null) {
			validResult.put("scope", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.scope) > 100) {
				validResult.put("scope", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.linkman) > 50) {
			validResult.put("linkman", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.serviceCall) > 20) {
			validResult.put("serviceCall", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.reservationCall) > 20) {
			validResult.put("reservationCall", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.qrcode) > 100) {
			validResult.put("qrcode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.address) > 150) {
			validResult.put("address", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.serviceQq) > 20) {
			validResult.put("serviceQq", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.poster) > 100) {
			validResult.put("poster", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.storecard) > 100) {
			validResult.put("storecard", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("name","companyId","logo","header","scope","linkman","serviceCall","reservationCall","qrcode","address","serviceQq","poster","pinkage","storecard");

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
		if(map.containsKey("pinkage")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("pinkage",String.class))) {
				validResult.put("pinkage", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("name")) {
			if (map.getTypedValue("name",String.class)  == null) {
				validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("name",String.class)) > 100) {
					validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
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
		if(map.containsKey("logo")) {
			if (map.getTypedValue("logo",String.class)  == null) {
				validResult.put("logo", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("logo",String.class)) > 100) {
					validResult.put("logo", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("header")) {
			if (map.getTypedValue("header",String.class)  == null) {
				validResult.put("header", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("header",String.class)) > 100) {
					validResult.put("header", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("scope")) {
			if (map.getTypedValue("scope",String.class)  == null) {
				validResult.put("scope", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("scope",String.class)) > 100) {
					validResult.put("scope", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("linkman")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("linkman",String.class)) > 50) {
				validResult.put("linkman", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("serviceCall")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("serviceCall",String.class)) > 20) {
				validResult.put("serviceCall", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("reservationCall")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("reservationCall",String.class)) > 20) {
				validResult.put("reservationCall", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("qrcode")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("qrcode",String.class)) > 100) {
				validResult.put("qrcode", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("address")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("address",String.class)) > 150) {
				validResult.put("address", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("serviceQq")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("serviceQq",String.class)) > 20) {
				validResult.put("serviceQq", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("poster")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("poster",String.class)) > 100) {
				validResult.put("poster", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("storecard")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("storecard",String.class)) > 100) {
				validResult.put("storecard", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setLogo(String logo){
		this.logo=logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setHeader(String header){
		this.header=header;
	}

	public String getHeader(){
		return header;
	}

	public void setScope(String scope){
		this.scope=scope;
	}

	public String getScope(){
		return scope;
	}

	public void setLinkman(String linkman){
		this.linkman=linkman;
	}

	public String getLinkman(){
		return linkman;
	}

	public void setServiceCall(String serviceCall){
		this.serviceCall=serviceCall;
	}

	public String getServiceCall(){
		return serviceCall;
	}

	public void setReservationCall(String reservationCall){
		this.reservationCall=reservationCall;
	}

	public String getReservationCall(){
		return reservationCall;
	}

	public void setQrcode(String qrcode){
		this.qrcode=qrcode;
	}

	public String getQrcode(){
		return qrcode;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return address;
	}

	public void setServiceQq(String serviceQq){
		this.serviceQq=serviceQq;
	}

	public String getServiceQq(){
		return serviceQq;
	}

	public void setPoster(String poster){
		this.poster=poster;
	}

	public String getPoster(){
		return poster;
	}

	public void setPinkage(Integer pinkage){
		this.pinkage=pinkage;
	}

	public Integer getPinkage(){
		return pinkage;
	}

	public void setStorecard(String storecard){
		this.storecard=storecard;
	}

	public String getStorecard(){
		return storecard;
	}
}
