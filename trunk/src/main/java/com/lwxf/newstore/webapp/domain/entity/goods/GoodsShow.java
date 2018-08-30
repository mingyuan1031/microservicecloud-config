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
 * 功能：goods_show 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-11 05:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods_show",displayName = "goods_show")
public class GoodsShow extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "goods_id",displayName = "关联的商品id  是哪件商品的")
	private String goodsId;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "path",displayName = "图片的路径")
	private String path;
	@Column(type = Types.BIT,nullable = false,name = "is_defaults",displayName = "是否是默认图片 用作商品列表显示 暂时无用 备用")
	private Boolean defaults;
	@Column(type = Types.INTEGER,name = "sequence",displayName = "排序 图片的排序方式 暂时备用")
	private Integer sequence;

	public GoodsShow() {
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
		if (this.path == null) {
			validResult.put("path", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.path) > 100) {
				validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.defaults == null) {
			validResult.put("defaults", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("goodsId","path","defaults","sequence");

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
		if(map.containsKey("defaults")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("defaults",String.class))) {
				validResult.put("defaults", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("sequence")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("sequence",String.class))) {
				validResult.put("sequence", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("path")) {
			if (map.getTypedValue("path",String.class)  == null) {
				validResult.put("path", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("path",String.class)) > 100) {
					validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("defaults")) {
			if (map.get("defaults")  == null) {
				validResult.put("defaults", ErrorCodes.VALIDATE_NOTNULL);
			}
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

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setDefaults(Boolean defaults){
		this.defaults=defaults;
	}

	public Boolean getDefaults(){
		return defaults;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}

	public Integer getSequence(){
		return sequence;
	}
}
