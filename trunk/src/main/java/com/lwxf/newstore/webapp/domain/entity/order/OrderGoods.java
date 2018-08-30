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
 * 功能：order_goods 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-02 11:27 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "order_goods",displayName = "order_goods")
public class OrderGoods extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "order_id",displayName = "订单ID")
	private String orderId;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "goods_extend_id",displayName = "商品扩展Id")
	private String goodsExtendId;
	@Column(type = Types.INTEGER,nullable = false,name = "goods_amount",displayName = "商品数量")
	private Integer goodsAmount;
	@Column(type = Types.DECIMAL,precision = 20,scale=2,nullable = false,name = "paid_price",displayName = "商品单价")
	private BigDecimal paidPrice;
	@Column(type = Types.VARCHAR,length = 200,name = "descr",displayName = "描述")
	private String descr;
	@Column(type = Types.TINYINT,nullable = false,name = "status",displayName = "0：未发货，1：已发货，2：已收货，3：已评论：4已加评")
	private Integer status;

    public OrderGoods() {  
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
		if (this.goodsExtendId == null) {
			validResult.put("goodsExtendId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.goodsExtendId) > 13) {
				validResult.put("goodsExtendId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.goodsAmount == null) {
			validResult.put("goodsAmount", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.descr) > 200) {
			validResult.put("descr", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.status == null) {
			validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("orderId","goodsExtendId","goodsAmount","paidPrice","descr","status");

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
		if(map.containsKey("goodsAmount")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("goodsAmount",String.class))) {
				validResult.put("goodsAmount", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("paidPrice")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("paidPrice",String.class))) {
				validResult.put("paidPrice", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("goodsExtendId")) {
			if (map.getTypedValue("goodsExtendId",String.class)  == null) {
				validResult.put("goodsExtendId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("goodsExtendId",String.class)) > 13) {
					validResult.put("goodsExtendId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("goodsAmount")) {
			if (map.get("goodsAmount")  == null) {
				validResult.put("goodsAmount", ErrorCodes.VALIDATE_NOTNULL);
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

	public void setGoodsExtendId(String goodsExtendId){
		this.goodsExtendId=goodsExtendId;
	}

	public String getGoodsExtendId(){
		return goodsExtendId;
	}

	public void setGoodsAmount(Integer goodsAmount){
		this.goodsAmount=goodsAmount;
	}

	public Integer getGoodsAmount(){
		return goodsAmount;
	}

	public void setPaidPrice(BigDecimal paidPrice){
		this.paidPrice=paidPrice;
	}

	public BigDecimal getPaidPrice(){
		return paidPrice;
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
}
