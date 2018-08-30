package com.lwxf.newstore.webapp.domain.entity.advertising;
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
 * 功能：advertising 实体类
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-07-13 02:17
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "advertising",displayName = "advertising")
public class Advertising extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "adv_name",displayName = "广告名字")
	private String advName;
	@Column(type = Types.INTEGER,nullable = false,name = "place",displayName = "广告位置 ")
	private Integer place;
	@Column(type = Types.INTEGER,nullable = false,name = "link_type",displayName = "广告的类型")
	private Integer linkType;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "link",displayName = "广告链接")
	private String link;
	@Column(type = Types.INTEGER,name = "link_start",displayName = "链接是否显示    0表示禁用 1表示启用")
	private Integer linkStart;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "path",displayName = "图片路径")
	private String path;
	@Column(type = TypesExtend.DATETIME,nullable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "creator",displayName = "创建人")
	private String creator;

	public Advertising() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.advName == null) {
			validResult.put("advName", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.advName) > 50) {
				validResult.put("advName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.place == null) {
			validResult.put("place", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.linkType == null) {
			validResult.put("linkType", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.link == null) {
			validResult.put("link", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.link) > 50) {
				validResult.put("link", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.linkStart == null) {
			validResult.put("linkStart", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.path == null) {
			validResult.put("path", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.path) > 100) {
				validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("advName","place","linkType","link","linkStart","path","created","creator");

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
		if(map.containsKey("place")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("place",String.class))) {
				validResult.put("place", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("linkType")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("linkType",String.class))) {
				validResult.put("linkType", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("linkStart")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("linkStart",String.class))) {
				validResult.put("linkStart", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("created")) {
			if (!DataValidatorUtils.isDate(map.getTypedValue("created",String.class))) {
				validResult.put("created", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("advName")) {
			if (map.getTypedValue("advName",String.class)  == null) {
				validResult.put("advName", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("advName",String.class)) > 50) {
					validResult.put("advName", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("place")) {
			if (map.get("place")  == null) {
				validResult.put("place", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("linkType")) {
			if (map.get("linkType")  == null) {
				validResult.put("linkType", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("link")) {
			if (map.getTypedValue("link",String.class)  == null) {
				validResult.put("link", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("link",String.class)) > 50) {
					validResult.put("link", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("linkStart")) {
			if (map.get("linkStart")  == null) {
				validResult.put("linkStart", ErrorCodes.VALIDATE_NOTNULL);
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
		if(map.containsKey("created")) {
			if (map.get("created")  == null) {
				validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("creator")) {
			if (map.getTypedValue("creator",String.class)  == null) {
				validResult.put("creator", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("creator",String.class)) > 13) {
					validResult.put("creator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setAdvName(String advName){
		this.advName=advName;
	}

	public String getAdvName(){
		return advName;
	}

	public void setPlace(Integer place){
		this.place=place;
	}

	public Integer getPlace(){
		return place;
	}

	public void setLinkType(Integer linkType){
		this.linkType=linkType;
	}

	public Integer getLinkType(){
		return linkType;
	}

	public void setLink(String link){
		this.link=link;
	}

	public String getLink(){
		return link;
	}

	public void setLinkStart(Integer linkStart){
		this.linkStart=linkStart;
	}

	public Integer getLinkStart(){
		return linkStart;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
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
}
