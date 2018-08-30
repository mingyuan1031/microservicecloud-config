package com.lwxf.newstore.webapp.domain.entity.goods;
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
 * 功能：goods 实体类
 *
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-03 03:04
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods",displayName = "goods")
public class Goods extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "商品名称  模糊查询会用到")
	private String name;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间 商品的上架时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "创建人 ")
	private String creator;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "goods_type_id",displayName = "该商品属于哪个分类下面 哪个终极分类(不可再分的类别)")
	private String goodsTypeId;
	@Column(type = Types.LONGVARCHAR,name = "content",displayName = "文本描述 该商品的详情介绍 可以很长 ")
	private String content;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否下架 并非删除 是由于供货等原因 暂时下架 有以后重新上架的可能")
	private Boolean disabled;
	@Column(type = TypesExtend.DATETIME,name = "updated",displayName = "商品的修改时间 可以为空 意思为商品上架后就没人动 如果有人修改该商品扩展表的规格内容 则更新时间")
	private Date updated;
	@Column(type = Types.CHAR,length = 13,name = "updator",displayName = "更新的人是谁,是谁修改的数据,便于管理 删除也属于更新 所以取消删除人字段")
	private String updator;
	@Column(type = Types.CHAR,length = 13,name = "brand_id",displayName = "关联品牌表 代表该产品是什么牌子的  我认为产品应该 都有品牌 不然就是三无产品嘛 ")
	private String brandId;
	@Column(type = Types.BIT,name = "is_tops",displayName = "是否置顶 true(1) 置顶 false(0) 不置顶")
	private Boolean tops;
	@Column(type = Types.INTEGER,name = "views",displayName = "")
	private Integer views;
	@Column(type = Types.DECIMAL,precision = 10,scale=2,defaultValue = "0.00",name = "freight",displayName = "运费 不可为空 必填")
	private BigDecimal freight;

	public Goods() {
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
		if (this.goodsTypeId == null) {
			validResult.put("goodsTypeId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.goodsTypeId) > 13) {
				validResult.put("goodsTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.updator) > 13) {
			validResult.put("updator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.brandId) > 13) {
			validResult.put("brandId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("name","goodsTypeId","content","disabled","updated","updator","brandId","tops","views","freight");

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
		if(map.containsKey("disabled")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("disabled",String.class))) {
				validResult.put("disabled", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("updated")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("updated",String.class))) {
				validResult.put("updated", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("tops")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("tops",String.class))) {
				validResult.put("tops", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("views")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("views",String.class))) {
				validResult.put("views", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("freight")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("freight",String.class))) {
				validResult.put("freight", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("goodsTypeId")) {
			if (map.getTypedValue("goodsTypeId",String.class)  == null) {
				validResult.put("goodsTypeId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("goodsTypeId",String.class)) > 13) {
					validResult.put("goodsTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("disabled")) {
			if (map.get("disabled")  == null) {
				validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("updator")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("updator",String.class)) > 13) {
				validResult.put("updator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("brandId")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("brandId",String.class)) > 13) {
				validResult.put("brandId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	public void setGoodsTypeId(String goodsTypeId){
		this.goodsTypeId=goodsTypeId;
	}

	public String getGoodsTypeId(){
		return goodsTypeId;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setDisabled(Boolean disabled){
		this.disabled=disabled;
	}

	public Boolean getDisabled(){
		return disabled;
	}

	public void setUpdated(Date updated){
		this.updated=updated;
	}

	public Date getUpdated(){
		return updated;
	}

	public void setUpdator(String updator){
		this.updator=updator;
	}

	public String getUpdator(){
		return updator;
	}

	public void setBrandId(String brandId){
		this.brandId=brandId;
	}

	public String getBrandId(){
		return brandId;
	}

	public void setTops(Boolean tops){
		this.tops=tops;
	}

	public Boolean getTops(){
		return tops;
	}

	public void setViews(Integer views){
		this.views=views;
	}

	public Integer getViews(){
		return views;
	}

	public void setFreight(BigDecimal freight){
		this.freight=freight;
	}

	public BigDecimal getFreight(){
		return freight;
	}
}
