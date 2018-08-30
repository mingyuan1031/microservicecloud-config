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
 * 功能：goods_extend 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-27 03:03
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods_extend",displayName = "goods_extend")
public class GoodsExtend extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "goods_id",displayName = "商品id 外键关联商品表的id")
	private String goodsId;
	@Column(type = Types.INTEGER,nullable = false,name = "sales",displayName = "销售量  该商品卖出去多少件 可把销售量与浏览量的比值作为热度 进行排名 以确定热销产品的先后顺序")
	private Integer sales;
	@Column(type = Types.DECIMAL,precision = 10,scale=2,nullable = false,name = "originalprice",displayName = "开始时的价格(原价) 用来促销打折活动的时候 用户直观的看到降价")
	private BigDecimal originalprice;
	@Column(type = Types.DECIMAL,precision = 10,scale=2,nullable = false,name = "price",displayName = "现在的价格 (现价) 和原价搭配使用 如果和原价一样 那就普通显示")
	private BigDecimal price;
	@Column(type = Types.BIT,nullable = false,name = "is_defaults",displayName = "该规格是不是默认规格,意思为查询商品列表的时候 显示的图片和价格是哪个")
	private Boolean defaults;
	@Column(type = Types.INTEGER,nullable = false,name = "stock",displayName = "库存 定制产品的话 库存固定写死 普通商品有库存 库存的真正使用地方时买规格的时候 选的规格是否显示")
	private Integer stock;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,updatable = false,name = "options",displayName = "不可为null 即使只有一个 通码通号 也要有一个通码通号选项")
	private String options;
	@Column(type = Types.VARCHAR,length = 100,name = "bigimage",displayName = "该种规格的大图")
	private String bigimage;
	@Column(type = Types.VARCHAR,length = 100,name = "smallimage",displayName = "该种规格的小图")
	private String smallimage;
	@Column(type = Types.CHAR,length = 1,name = "state",displayName = "0 正常 1 删除 2 禁用")
	private String state;

	public GoodsExtend() {
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
		if (this.sales == null) {
			validResult.put("sales", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.defaults == null) {
			validResult.put("defaults", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.stock == null) {
			validResult.put("stock", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.options == null) {
			validResult.put("options", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.options) > 50) {
				validResult.put("options", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.bigimage) > 100) {
			validResult.put("bigimage", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.smallimage) > 100) {
			validResult.put("smallimage", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.state) > 1) {
			validResult.put("state", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("sales","originalprice","price","defaults","stock","bigimage","smallimage","state");

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
		if(map.containsKey("sales")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("sales",String.class))) {
				validResult.put("sales", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("originalprice")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("originalprice",String.class))) {
				validResult.put("originalprice", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("price")) {
			if (!DataValidatorUtils.isDecmal4(map.getTypedValue("price",String.class))) {
				validResult.put("price", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("defaults")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("defaults",String.class))) {
				validResult.put("defaults", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("stock")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("stock",String.class))) {
				validResult.put("stock", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("sales")) {
			if (map.get("sales")  == null) {
				validResult.put("sales", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("defaults")) {
			if (map.get("defaults")  == null) {
				validResult.put("defaults", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("stock")) {
			if (map.get("stock")  == null) {
				validResult.put("stock", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("bigimage")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("bigimage",String.class)) > 100) {
				validResult.put("bigimage", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("smallimage")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("smallimage",String.class)) > 100) {
				validResult.put("smallimage", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("state")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("state",String.class)) > 1) {
				validResult.put("state", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	public void setSales(Integer sales){
		this.sales=sales;
	}

	public Integer getSales(){
		return sales;
	}

	public void setOriginalprice(BigDecimal originalprice){
		this.originalprice=originalprice;
	}

	public BigDecimal getOriginalprice(){
		return originalprice;
	}

	public void setPrice(BigDecimal price){
		this.price=price;
	}

	public BigDecimal getPrice(){
		return price;
	}

	public void setDefaults(Boolean defaults){
		this.defaults=defaults;
	}

	public Boolean getDefaults(){
		return defaults;
	}

	public void setStock(Integer stock){
		this.stock=stock;
	}

	public Integer getStock(){
		return stock;
	}

	public void setOptions(String options){
		this.options=options;
	}

	public String getOptions(){
		return options;
	}

	public void setBigimage(String bigimage){
		this.bigimage=bigimage;
	}

	public String getBigimage(){
		return bigimage;
	}

	public void setSmallimage(String smallimage){
		this.smallimage=smallimage;
	}

	public String getSmallimage(){
		return smallimage;
	}

	public void setState(String state){
		this.state=state;
	}

	public String getState(){
		return state;
	}
}
