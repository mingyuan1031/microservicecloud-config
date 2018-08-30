package com.lwxf.newstore.webapp.domain.entity.order;
import java.math.BigDecimal;
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
 * 功能：paid_records 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-08-06 04:12 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "paid_records",displayName = "paid_records")
public class PaidRecords extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "member_id",displayName = "会员ID")
	private String memberId;
	@Column(type = Types.VARCHAR,length = 20,nullable = false,updatable = false,name = "paid_num",displayName = "支付单号")
	private String paidNum;
	@Column(type = Types.DECIMAL,precision = 10,scale=2,nullable = false,updatable = false,name = "paid_price",displayName = "支付金额")
	private BigDecimal paidPrice;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "paid_time",displayName = "支付时间")
	private Date paidTime;
	@Column(type = Types.TINYINT,nullable = false,updatable = false,name = "type",displayName = "0-订单支付;1-订单退款")
	private Byte type;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "")
	private Date created;
	@Column(type = Types.CHAR,length = 13,name = "order_id",displayName = "")
	private String orderId;

    public PaidRecords() {  
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
		if (this.paidNum == null) {
			validResult.put("paidNum", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.paidNum) > 20) {
				validResult.put("paidNum", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.paidTime == null) {
			validResult.put("paidTime", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.type == null) {
			validResult.put("type", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.orderId) > 13) {
			validResult.put("orderId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","orderId");

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
		if(map.containsKey("orderId")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("orderId",String.class)) > 13) {
				validResult.put("orderId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	public void setPaidNum(String paidNum){
		this.paidNum=paidNum;
	}

	public String getPaidNum(){
		return paidNum;
	}

	public void setPaidPrice(BigDecimal paidPrice){
		this.paidPrice=paidPrice;
	}

	public BigDecimal getPaidPrice(){
		return paidPrice;
	}

	public void setPaidTime(Date paidTime){
		this.paidTime=paidTime;
	}

	public Date getPaidTime(){
		return paidTime;
	}

	public void setType(Byte type){
		this.type=type;
	}

	public Byte getType(){
		return type;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setOrderId(String orderId){
		this.orderId=orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}
