package com.lwxf.newstore.webapp.domain.entity.cart;
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
 * 功能：cart 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-06-27 04:40 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "cart",displayName = "cart")
public class Cart extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "member_id",displayName = "会员的ID")
	private String memberId;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "goods_id",displayName = "商品的ID，通过ID找到商品信息")
	private String goodsId;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "extend_id",displayName = "")
	private String extendId;
	@Column(type = Types.INTEGER,nullable = false,name = "count",displayName = "商品的数量")
	private Integer count;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;

    public Cart() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
//		if (this.memberId == null) {
//			validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
//		}else{
// 			if (LwxfStringUtils.getStringLength(this.memberId) > 13) {
//				validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
//			}
//		}
		if (this.goodsId == null) {
			validResult.put("goodsId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.goodsId) > 13) {
				validResult.put("goodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.extendId == null) {
			validResult.put("extendId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.extendId) > 13) {
				validResult.put("extendId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.count == null) {
			validResult.put("count", ErrorCodes.VALIDATE_NOTNULL);
		}
//		if (this.created == null) {
//			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
//		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("memberId","goodsId","extendId","count");

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
		if(map.containsKey("count")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("count",String.class))) {
				validResult.put("count", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("goodsId")) {
			if (map.getTypedValue("goodsId",String.class)  == null) {
				validResult.put("goodsId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("goodsId",String.class)) > 13) {
					validResult.put("goodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("extendId")) {
			if (map.getTypedValue("extendId",String.class)  == null) {
				validResult.put("extendId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("extendId",String.class)) > 13) {
					validResult.put("extendId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("count")) {
			if (map.get("count")  == null) {
				validResult.put("count", ErrorCodes.VALIDATE_NOTNULL);
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

	public void setGoodsId(String goodsId){
		this.goodsId=goodsId;
	}

	public String getGoodsId(){
		return goodsId;
	}

	public void setExtendId(String extendId){
		this.extendId=extendId;
	}

	public String getExtendId(){
		return extendId;
	}

	public void setCount(Integer count){
		this.count=count;
	}

	public Integer getCount(){
		return count;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}
}
