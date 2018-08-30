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
 * 功能：address 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-02 11:27 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "address",displayName = "address")
public class Address extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "member_id",displayName = "会员ID关联会员表")
	private String memberId;
	@Column(type = Types.VARCHAR,length = 10,name = "receiver",displayName = "收货人")
	private String receiver;
	@Column(type = Types.VARCHAR,length = 11,name = "receiver_phone",displayName = "收货人电话")
	private String receiverPhone;
	@Column(type = Types.VARCHAR,length = 100,name = "receiver_address",displayName = "收货人地址")
	private String receiverAddress;
	@Column(type = Types.VARCHAR,length = 13,nullable = false,name = "city_area_id",displayName = "行政区划Id")
	private String cityAreaId;
	@Column(type = Types.BIT,name = "is_defaulted",displayName = "是否是默认地址0不是1是")
	private Boolean defaulted;
	@Column(type = Types.TINYINT,nullable = false,name = "status",displayName = "地址状态-1删除 0 正常 1 禁用")
	private Integer status;
	@Column(type = TypesExtend.DATETIME,nullable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = TypesExtend.DATETIME,name = "updated",displayName = "修改时间")
	private Date updated;

    public Address() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.memberId == null) {
			validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.memberId) > 13) {
				validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.receiver) > 10) {
			validResult.put("receiver", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.receiverPhone) > 11) {
			validResult.put("receiverPhone", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.receiverAddress) > 100) {
			validResult.put("receiverAddress", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.cityAreaId == null) {
			validResult.put("cityAreaId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.cityAreaId) > 13) {
				validResult.put("cityAreaId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.status == null) {
			validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
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

	private final static List<String> propertiesList = Arrays.asList("id","memberId","receiver","receiverPhone","receiverAddress","cityAreaId","defaulted","status","created","updated");

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
		if(map.containsKey("defaulted")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("defaulted",String.class))) {
				validResult.put("defaulted", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("created")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("created",String.class))) {
				validResult.put("created", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("updated")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("updated",String.class))) {
				validResult.put("updated", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("receiver")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("receiver",String.class)) > 10) {
				validResult.put("receiver", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("receiverPhone")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("receiverPhone",String.class)) > 11) {
				validResult.put("receiverPhone", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("receiverAddress")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("receiverAddress",String.class)) > 100) {
				validResult.put("receiverAddress", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("cityAreaId")) {
			if (map.getTypedValue("cityAreaId",String.class)  == null) {
				validResult.put("cityAreaId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("cityAreaId",String.class)) > 13) {
					validResult.put("cityAreaId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("status")) {
			if (map.get("status")  == null) {
				validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("created")) {
			if (map.get("created")  == null) {
				validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setMemberId(String memberId){
		this.memberId=memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setReceiver(String receiver){
		this.receiver=receiver;
	}

	public String getReceiver(){
		return receiver;
	}

	public void setReceiverPhone(String receiverPhone){
		this.receiverPhone=receiverPhone;
	}

	public String getReceiverPhone(){
		return receiverPhone;
	}

	public void setReceiverAddress(String receiverAddress){
		this.receiverAddress=receiverAddress;
	}

	public String getReceiverAddress(){
		return receiverAddress;
	}

	public void setCityAreaId(String cityAreaId){
		this.cityAreaId=cityAreaId;
	}

	public String getCityAreaId(){
		return cityAreaId;
	}

	public void setDefaulted(Boolean defaulted){
		this.defaulted=defaulted;
	}

	public Boolean getDefaulted(){
		return defaulted;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setUpdated(Date updated){
		this.updated=updated;
	}

	public Date getUpdated(){
		return updated;
	}
}
