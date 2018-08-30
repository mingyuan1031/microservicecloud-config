package com.lwxf.newstore.webapp.domain.entity.news;
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
 * 功能：news_img 实体类
 *
 * @author：dongshibo(F_baisi@163.com)
 * @created：2018-08-22 02:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "news_img",displayName = "news_img")
public class NewsImg extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,updatable = false,name = "path",displayName = "路径 图片的访问路径")
	private String path;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,updatable = false,name = "name",displayName = "名称 图片的名称 用来区分图片,不可重复")
	private String name;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "news_id",displayName = "所属的企业动态的id")
	private String newsId;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "news_type_id",displayName = "动态资源分类的id")
	private String newsTypeId;

	public NewsImg() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
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
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.name) > 100) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.newsId == null) {
			validResult.put("newsId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.newsId) > 13) {
				validResult.put("newsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.newsTypeId == null) {
			validResult.put("newsTypeId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.newsTypeId) > 13) {
				validResult.put("newsTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setNewsId(String newsId){
		this.newsId=newsId;
	}

	public String getNewsId(){
		return newsId;
	}

	public void setNewsTypeId(String newsTypeId){
		this.newsTypeId=newsTypeId;
	}

	public String getNewsTypeId(){
		return newsTypeId;
	}

	public NewsImg(String path, Date created, String name, String newsId, String newsTypeId) {
		this.path = path;
		this.created = created;
		this.name = name;
		this.newsId = newsId;
		this.newsTypeId = newsTypeId;
	}
}
