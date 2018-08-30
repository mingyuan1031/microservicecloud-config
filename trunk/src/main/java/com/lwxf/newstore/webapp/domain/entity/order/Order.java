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

import com.lwxf.newstore.webapp.domain.dto.order.OrderDto;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
/**
 * 功能：order 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-06 03:34 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "order",displayName = "order")
public class Order extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "order_number",displayName = "订单编号")
	private String orderNumber;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "member_id",displayName = "会员ID")
	private String memberId;
	@Column(type = Types.DECIMAL,precision = 20,scale=2,name = "paid_price",displayName = "支付金额")
	private BigDecimal paidPrice;
	@Column(type = TypesExtend.DATETIME,name = "paid_time",displayName = "支付时间")
	private Date paidTime;
	@Column(type = Types.DECIMAL,precision = 20,scale=2,nullable = false,name = "freight",displayName = "运费")
	private BigDecimal freight;
	@Column(type = Types.TINYINT,nullable = false,name = "paid_method",displayName = "支付类型")
	private Integer paidMethod;
	@Column(type = Types.VARCHAR,length = 200,name = "descr",displayName = "订单备注")
	private String descr;
	@Column(type = Types.TINYINT,nullable = false,name = "status",displayName = "订单状态(0未付款1已付款2部分发货3已发货4已收货5已撤销6已退款)")
	private Integer status;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "receipt_id",displayName = "用户地址ID")
	private String receiptId;
	@Column(type = Types.CHAR,length = 13,name = "trade_no",displayName = "商户系统内部订单号")
	private String tradeNo;
	@Column(type = TypesExtend.DATETIME,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = TypesExtend.DATETIME,name = "updated",displayName = "")
	private Date updated;

	public Order() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.orderNumber == null) {
			validResult.put("orderNumber", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.orderNumber) > 50) {
				validResult.put("orderNumber", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.memberId == null) {
			validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.memberId) > 13) {
				validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.paidMethod == null) {
			validResult.put("paidMethod", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.descr) > 200) {
			validResult.put("descr", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.status == null) {
			validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.receiptId == null) {
			validResult.put("receiptId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.receiptId) > 13) {
				validResult.put("receiptId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.tradeNo) > 13) {
			validResult.put("tradeNo", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","orderNumber","memberId","paidPrice","paidTime","freight","paidMethod","descr","status","receiptId","tradeNo","updated");

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
		if(map.containsKey("paidPrice")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("paidPrice",String.class))) {
				validResult.put("paidPrice", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("paidTime")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("paidTime",String.class))) {
				validResult.put("paidTime", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("freight")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("freight",String.class))) {
				validResult.put("freight", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("paidMethod")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("paidMethod",String.class))) {
				validResult.put("paidMethod", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("updated")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("updated",String.class))) {
				validResult.put("updated", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("orderNumber")) {
			if (map.getTypedValue("orderNumber",String.class)  == null) {
				validResult.put("orderNumber", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("orderNumber",String.class)) > 50) {
					validResult.put("orderNumber", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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
		if(map.containsKey("paidMethod")) {
			if (map.get("paidMethod")  == null) {
				validResult.put("paidMethod", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("descr")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("descr",String.class)) > 200) {
				validResult.put("descr", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("status")) {
			if (map.get("status")  == null) {
				validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
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
		if(map.containsKey("tradeNo")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("tradeNo",String.class)) > 13) {
				validResult.put("tradeNo", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setOrderNumber(String orderNumber){
		this.orderNumber=orderNumber;
	}

	public String getOrderNumber(){
		return orderNumber;
	}

	public void setMemberId(String memberId){
		this.memberId=memberId;
	}

	public String getMemberId(){
		return memberId;
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

	public void setFreight(BigDecimal freight){
		this.freight=freight;
	}

	public BigDecimal getFreight(){
		return freight;
	}

	public void setPaidMethod(Integer paidMethod){
		this.paidMethod=paidMethod;
	}

	public Integer getPaidMethod(){
		return paidMethod;
	}

	public void setDescr(String descr){
		this.descr=descr;
	}

	public String getDescr(){
		return descr;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}
	public String getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public void setTradeNo(String tradeNo){
		this.tradeNo=tradeNo;
	}

	public String getTradeNo(){
		return tradeNo;
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
	public OrderDto clone(){
		OrderDto dto = new OrderDto();
		dto.setId(this.getId());
		dto.setMemberId(this.getMemberId());
		dto.setOrderNumber(this.getOrderNumber());
		dto.setPaidPrice(this.getPaidPrice());
		dto.setStatus(this.getStatus());
		dto.setPaidTime(this.getPaidTime());
		dto.setCreated(this.getCreated());
		dto.setUpdated(this.getUpdated());
		dto.setFreight(this.getFreight());
		dto.setPaidMethod(this.getPaidMethod());
		dto.setDescr(this.getDescr());
		dto.setReceiptId(this.getReceiptId());
		dto.setTradeNo(this.getTradeNo());
		return dto;
	}
}
