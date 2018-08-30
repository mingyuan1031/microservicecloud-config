package com.lwxf.newstore.webapp.domain.entity.goods;
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
 * 功能：goods_type 实体类
 *
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-07-30 04:37
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods_type",displayName = "goods_type")
public class GoodsType extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "分类的名称  如 家具→床→婴儿床  像这样的每种都是一行数据                       →双人床")
	private String name;
	@Column(type = Types.INTEGER,nullable = false,name = "sort",displayName = "排序 用在显示或者商品展示的时候的显示顺序")
	private Integer sort;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否隐藏 有时候 原来卖这种分类的产品 但由于店铺转型 或者供货商改变 导致该种分类不再上新 或者不再售卖 就禁用 false 显示 true  隐藏")
	private Boolean disabled;
	@Column(type = Types.CHAR,length = 13,updatable = false,name = "parent_id",displayName = "父类的id 自关联在字段name中有介绍 分类的父类 没有就写0")
	private String parentId;

	public GoodsType() {
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
		if (this.sort == null) {
			validResult.put("sort", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (LwxfStringUtils.getStringLength(this.parentId) > 13) {
			validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("name","sort","disabled");

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
		if(map.containsKey("sort")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("sort",String.class))) {
				validResult.put("sort", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("disabled")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("disabled",String.class))) {
				validResult.put("disabled", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("sort")) {
			if (map.get("sort")  == null) {
				validResult.put("sort", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("disabled")) {
			if (map.get("disabled")  == null) {
				validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
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

	public void setSort(Integer sort){
		this.sort=sort;
	}

	public Integer getSort(){
		return sort;
	}

	public void setDisabled(Boolean disabled){
		this.disabled=disabled;
	}

	public Boolean getDisabled(){
		return disabled;
	}

	public void setParentId(String parentId){
		this.parentId=parentId;
	}

	public String getParentId(){
		return parentId;
	}
}
