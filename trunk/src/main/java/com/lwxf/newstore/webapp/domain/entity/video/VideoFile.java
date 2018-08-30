package com.lwxf.newstore.webapp.domain.entity.video;
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
 * 功能：video_file 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-12 07:37
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "video_file",displayName = "video_file")
public class VideoFile extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "video_Type_Id",displayName = "类型列如工厂还是仓库 关联类型表")
	private String videoTypeId;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "上传时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "上传人")
	private String creator;
	@Column(type = Types.VARCHAR,length = 100,name = "cover",displayName = "封面 视频封面 可取视频里某一幕 也可和视频毫无关联  <2M")
	private String cover;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否禁用 0 false 启用 1 true 禁用")
	private Boolean disabled;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,name = "path",displayName = "视频路径")
	private String path;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,name = "name",displayName = "标题名称")
	private String name;
	@Column(type = Types.INTEGER,name = "sequence",displayName = "排序 ")
	private Integer sequence;

	public VideoFile() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.videoTypeId == null) {
			validResult.put("videoTypeId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.videoTypeId) > 13) {
				validResult.put("videoTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
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
		if (LwxfStringUtils.getStringLength(this.cover) > 100) {
			validResult.put("cover", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.path == null) {
			validResult.put("path", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.path) > 100) {
				validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.name == null) {
			validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.name) > 50) {
				validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("videoTypeId","cover","disabled","path","name","sequence");

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
		if(map.containsKey("sequence")) {
			if (!DataValidatorUtils.isInteger1(map.getTypedValue("sequence",String.class))) {
				validResult.put("sequence", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("videoTypeId")) {
			if (map.getTypedValue("videoTypeId",String.class)  == null) {
				validResult.put("videoTypeId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("videoTypeId",String.class)) > 13) {
					validResult.put("videoTypeId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("cover")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("cover",String.class)) > 100) {
				validResult.put("cover", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("disabled")) {
			if (map.get("disabled")  == null) {
				validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
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
		if(map.containsKey("name")) {
			if (map.getTypedValue("name",String.class)  == null) {
				validResult.put("name", ErrorCodes.VALIDATE_NOTNULL);
			}else{
				if (LwxfStringUtils.getStringLength(map.getTypedValue("name",String.class)) > 50) {
					validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setVideoTypeId(String videoTypeId){
		this.videoTypeId=videoTypeId;
	}

	public String getVideoTypeId(){
		return videoTypeId;
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

	public void setCover(String cover){
		this.cover=cover;
	}

	public String getCover(){
		return cover;
	}

	public void setDisabled(Boolean disabled){
		this.disabled=disabled;
	}

	public Boolean getDisabled(){
		return disabled;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}

	public Integer getSequence(){
		return sequence;
	}
}
