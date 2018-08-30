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
 * 功能：logistics 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-02 11:27 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "logistics",displayName = "logistics")
public class Logistics extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "order_id",displayName = "订单id，关联订单表")
	private String orderId;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "logistics_name",displayName = "物流名称")
	private String logisticsName;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "logistics_number",displayName = "快递单号")
	private String logisticsNumber;
	@Column(type = TypesExtend.DATETIME,name = "deliver_time",displayName = "发货时间")
	private Date deliverTime;
	@Column(type = TypesExtend.DATETIME,name = "receipt_time",displayName = "收货时间")
	private Date receiptTime;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "receipt_id",displayName = "收获地址ID，管理收货地址表")
	private String receiptId;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "order_goods_id",displayName = "已发货商品ID")
	private String orderGoodsId;
	@Column(type = Types.VARCHAR,length = 200,name = "remarks",displayName = "备注")
	private String remarks;
	@Column(type = TypesExtend.DATETIME,nullable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = TypesExtend.DATETIME,name = "updated",displayName = "修改时间")
	private Date updated;

    public Logistics() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.orderId == null) {
			validResult.put("orderId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.orderId) > 13) {
				validResult.put("orderId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.logisticsName == null) {
			validResult.put("logisticsName", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.logisticsName) > 50) {
				validResult.put("logisticsName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.logisticsNumber == null) {
			validResult.put("logisticsNumber", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.logisticsNumber) > 50) {
				validResult.put("logisticsNumber", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.receiptId == null) {
			validResult.put("receiptId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.receiptId) > 13) {
				validResult.put("receiptId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.orderGoodsId == null) {
			validResult.put("orderGoodsId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.orderGoodsId) > 50) {
				validResult.put("orderGoodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.remarks) > 200) {
			validResult.put("remarks", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	private final static List<String> propertiesList = Arrays.asList("id","orderId","logisticsName","logisticsNumber","deliverTime","receiptTime","receiptId","orderGoodsId","remarks","created","updated");

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
		if(map.containsKey("deliverTime")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("deliverTime",String.class))) {
				validResult.put("deliverTime", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("receiptTime")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("receiptTime",String.class))) {
				validResult.put("receiptTime", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("orderId")) {
			if (map.getTypedValue("orderId",String.class)  == null) {
				validResult.put("orderId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("orderId",String.class)) > 13) {
					validResult.put("orderId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("logisticsName")) {
			if (map.getTypedValue("logisticsName",String.class)  == null) {
				validResult.put("logisticsName", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("logisticsName",String.class)) > 50) {
					validResult.put("logisticsName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("logisticsNumber")) {
			if (map.getTypedValue("logisticsNumber",String.class)  == null) {
				validResult.put("logisticsNumber", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("logisticsNumber",String.class)) > 50) {
					validResult.put("logisticsNumber", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("receiptId")) {
			if (map.getTypedValue("receiptId",String.class)  == null) {
				validResult.put("receiptId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("receiptId",String.class)) > 13) {
					validResult.put("receiptId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("orderGoodsId")) {
			if (map.getTypedValue("orderGoodsId",String.class)  == null) {
				validResult.put("orderGoodsId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("orderGoodsId",String.class)) > 50) {
					validResult.put("orderGoodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("remarks")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("remarks",String.class)) > 200) {
				validResult.put("remarks", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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


	public void setOrderId(String orderId){
		this.orderId=orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setLogisticsName(String logisticsName){
		this.logisticsName=logisticsName;
	}

	public String getLogisticsName(){
		return logisticsName;
	}

	public void setLogisticsNumber(String logisticsNumber){
		this.logisticsNumber=logisticsNumber;
	}

	public String getLogisticsNumber(){
		return logisticsNumber;
	}

	public void setDeliverTime(Date deliverTime){
		this.deliverTime=deliverTime;
	}

	public Date getDeliverTime(){
		return deliverTime;
	}

	public void setReceiptTime(Date receiptTime){
		this.receiptTime=receiptTime;
	}

	public Date getReceiptTime(){
		return receiptTime;
	}

	public void setReceiptId(String receiptId){
		this.receiptId=receiptId;
	}

	public String getReceiptId(){
		return receiptId;
	}

	public void setOrderGoodsId(String orderGoodsId){
		this.orderGoodsId=orderGoodsId;
	}

	public String getOrderGoodsId(){
		return orderGoodsId;
	}

	public void setRemarks(String remarks){
		this.remarks=remarks;
	}

	public String getRemarks(){
		return remarks;
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
	public Logistics clone()
	{
		Logistics logistics=new Logistics();
		logistics.setId(this.getId());
		logistics.setOrderGoodsId(this.getOrderGoodsId());
		logistics.setReceiptId(this.getReceiptId());
		logistics.setOrderId(this.getOrderId());
		logistics.setDeliverTime(this.getDeliverTime());
		logistics.setCreated(this.getCreated());
		logistics.setLogisticsName(this.getLogisticsName());
		logistics.setLogisticsNumber(this.getLogisticsNumber());
		logistics.setReceiptTime(this.getReceiptTime());
		logistics.setRemarks(this.getRemarks());
		logistics.setUpdated(this.getUpdated());
		return  logistics;
	}
}
