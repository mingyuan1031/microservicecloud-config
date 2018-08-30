package com.lwxf.newstore.webapp.domain.entity.scheme;
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
 * 功能：scheme 实体类
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @created：2018-08-01 09:50 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "scheme",displayName = "scheme")
public class Scheme extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "标题")
	private String name;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "cover",displayName = "封面，保存图片的路径")
	private String cover;
	@Column(type = Types.LONGVARCHAR,nullable = false,name = "content",displayName = "内容，为html格式的内容")
	private String content;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "panorama",displayName = "全景图图片的路径")
	private String panorama;
	@Column(type = Types.VARCHAR,length = 50,name = "vr_path",displayName = "VR视频的路径")
	private String vrPath;
	@Column(type = Types.INTEGER,name = "page_view",displayName = "浏览量")
	private Integer pageView;
	@Column(type = Types.INTEGER,name = "praise",displayName = "点赞数")
	private Integer praise;
	@Column(type = Types.VARCHAR,length = 200,name = "district",displayName = "地址区域")
	private String district;
	@Column(type = Types.VARCHAR,length = 200,name = "style",displayName = "装修的风格")
	private String style;
	@Column(type = Types.VARCHAR,length = 50,name = "home_type",displayName = "房屋类型")
	private String homeType;
	@Column(type = Types.VARCHAR,length = 50,name = "area",displayName = "房屋面积")
	private String area;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "创建人")
	private String creator;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = Types.INTEGER,defaultValue = "1",nullable = false,name = "status",displayName = "案例的状态 1 启用 0 禁用")
	private Integer status;

    public Scheme() {  
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
		if (this.cover == null) {
			validResult.put("cover", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.cover) > 50) {
				validResult.put("cover", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.panorama == null) {
			validResult.put("panorama", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.panorama) > 50) {
				validResult.put("panorama", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.vrPath) > 50) {
			validResult.put("vrPath", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.district) > 200) {
			validResult.put("district", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.style) > 200) {
			validResult.put("style", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.homeType) > 50) {
			validResult.put("homeType", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (LwxfStringUtils.getStringLength(this.area) > 50) {
			validResult.put("area", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.creator == null) {
			validResult.put("creator", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.creator) > 13) {
				validResult.put("creator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
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

	private final static List<String> propertiesList = Arrays.asList("name","cover","content","panorama","vrPath","pageView","praise","district","style","homeType","area","status");

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
		if(map.containsKey("pageView")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("pageView",String.class))) {
				validResult.put("pageView", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("praise")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("praise",String.class))) {
				validResult.put("praise", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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
		if(map.containsKey("cover")) {
			if (map.getTypedValue("cover",String.class)  == null) {
				validResult.put("cover", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("cover",String.class)) > 50) {
					validResult.put("cover", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("panorama")) {
			if (map.getTypedValue("panorama",String.class)  == null) {
				validResult.put("panorama", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("panorama",String.class)) > 50) {
					validResult.put("panorama", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("vrPath")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("vrPath",String.class)) > 50) {
				validResult.put("vrPath", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("district")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("district",String.class)) > 200) {
				validResult.put("district", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("style")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("style",String.class)) > 200) {
				validResult.put("style", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("homeType")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("homeType",String.class)) > 50) {
				validResult.put("homeType", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("area")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("area",String.class)) > 50) {
				validResult.put("area", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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


	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setCover(String cover){
		this.cover=cover;
	}

	public String getCover(){
		return cover;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setPanorama(String panorama){
		this.panorama=panorama;
	}

	public String getPanorama(){
		return panorama;
	}

	public void setVrPath(String vrPath){
		this.vrPath=vrPath;
	}

	public String getVrPath(){
		return vrPath;
	}

	public void setPageView(Integer pageView){
		this.pageView=pageView;
	}

	public Integer getPageView(){
		return pageView;
	}

	public void setPraise(Integer praise){
		this.praise=praise;
	}

	public Integer getPraise(){
		return praise;
	}

	public void setDistrict(String district){
		this.district=district;
	}

	public String getDistrict(){
		return district;
	}

	public void setStyle(String style){
		this.style=style;
	}

	public String getStyle(){
		return style;
	}

	public void setHomeType(String homeType){
		this.homeType=homeType;
	}

	public String getHomeType(){
		return homeType;
	}

	public void setArea(String area){
		this.area=area;
	}

	public String getArea(){
		return area;
	}

	public void setCreator(String creator){
		this.creator=creator;
	}

	public String getCreator(){
		return creator;
	}

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}
}
