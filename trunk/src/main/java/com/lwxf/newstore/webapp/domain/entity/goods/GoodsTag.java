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
 * 功能：goods_tag 实体类
 *
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-07-30 06:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods_tag",displayName = "goods_tag")
public class GoodsTag extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "goods_id",displayName = "商品的id 外键关联商品表 ")
	private String goodsId;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "tag_id",displayName = "标签id  外键关联标签表")
	private String tagId;

	public GoodsTag() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.goodsId == null) {
			validResult.put("goodsId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.goodsId) > 13) {
				validResult.put("goodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.tagId == null) {
			validResult.put("tagId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.tagId) > 13) {
				validResult.put("tagId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList();

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
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setGoodsId(String goodsId){
		this.goodsId=goodsId;
	}

	public String getGoodsId(){
		return goodsId;
	}

	public void setTagId(String tagId){
		this.tagId=tagId;
	}

	public String getTagId(){
		return tagId;
	}
}
