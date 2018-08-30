package com.lwxf.newstore.webapp.domain.entity.common;
import java.util.*;
import java.sql.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DataValidatorUtils;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.annotation.Column;

import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
/**
 * 功能：upload_files 实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 06:07 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "upload_files",displayName = "upload_files")
public class UploadFiles extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,name = "resource_id",displayName = "所属资源id，比如商品、商品评论、")
	private String resourceId;
	@Column(type = Types.TINYINT,nullable = false,name = "resource_type",displayName = "文件的所属资源类型：0-系统配置;1-商城配置;2-商品;3-商品评论;4-快享;5-商品规格;6-品牌;7-用户相关（头像、桌面背景等）;")
	private Integer resourceType;
	@Column(type = Types.VARCHAR,length = 100,nullable = false,updatable = false,name = "path",displayName = "文件的访问路径，带域名的全路径，如：http://localhost:8080/resources/goods/3232233323.png")
	private String path;
	@Column(type = Types.VARCHAR,length = 50,updatable = false,name = "name",displayName = "文件的名称：被上传时的原始文件名")
	private String name;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,updatable = false,name = "mime",displayName = "文件的mime类型（上传时根据业务逻辑而设定的）")
	private String mime;
	@Column(type = Types.VARCHAR,length = 50,nullable = false,updatable = false,name = "original_mime",displayName = "文件的mime类型（原始mime）")
	private String originalMime;
	@Column(type = Types.TINYINT,nullable = false,name = "status",displayName = "文件状态：0 - 临时文件；1：正式文件")
	private Integer status;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "创建人id（上传人id）")
	private String creator;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "上传时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,name = "belong_id",displayName = "该文件对应的资源属于哪个资源的子资源：比如商品评论的图片，那么belong_id填对应的商品id，没有则为null")
	private String belongId;

    public UploadFiles() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (LwxfStringUtils.getStringLength(this.resourceId) > 13) {
			validResult.put("resourceId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.resourceType == null) {
			validResult.put("resourceType", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.path == null) {
			validResult.put("path", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.path) > 100) {
				validResult.put("path", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.name) > 50) {
			validResult.put("name", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.mime == null) {
			validResult.put("mime", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.mime) > 50) {
				validResult.put("mime", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.originalMime == null) {
			validResult.put("originalMime", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.originalMime) > 50) {
				validResult.put("originalMime", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.status == null) {
			validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
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
		if (LwxfStringUtils.getStringLength(this.belongId) > 13) {
			validResult.put("belongId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","resourceType","resourceId","status","belongId");

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
		if(map.containsKey("resourceType")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("resourceType",String.class))) {
				validResult.put("resourceType", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("resourceId")){
			if(!DataValidatorUtils.isByte1(map.getTypedValue("resourceId",String.class))){
				validResult.put("resourceId",ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("status")) {
			if (!DataValidatorUtils.isByte1(map.getTypedValue("status",String.class))) {
				validResult.put("status", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("resourceType")) {
			if (map.get("resourceType")  == null) {
				validResult.put("resourceType", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("status")) {
			if (map.get("status")  == null) {
				validResult.put("status", ErrorCodes.VALIDATE_NOTNULL);
			}
		}
		if(map.containsKey("belongId")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("belongId",String.class)) > 13) {
				validResult.put("belongId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}


	public void setResourceId(String resourceId){
		this.resourceId=resourceId;
	}

	public String getResourceId(){
		return resourceId;
	}

	public void setResourceType(Integer resourceType){
		this.resourceType=resourceType;
	}

	public Integer getResourceType(){
		return resourceType;
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

	public void setMime(String mime){
		this.mime=mime;
	}

	public String getMime(){
		return mime;
	}

	public void setOriginalMime(String originalMime){
		this.originalMime=originalMime;
	}

	public String getOriginalMime(){
		return originalMime;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
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

	public void setBelongId(String belongId){
		this.belongId=belongId;
	}

	public String getBelongId(){
		return belongId;
	}



	public static UploadFiles create(String resId, String belongId, UploadInfo uploadInfo, UploadResourceType uploadResourceType, UploadType uploadType){
		UploadFiles file = new UploadFiles();
		file.setBelongId(belongId);
		file.setCreated(DateUtil.getSystemDate());
		file.setCreator(WebUtils.getCurrUserId());
		file.setMime(uploadInfo.getFileMimeType().getRealType());
		file.setOriginalMime(uploadInfo.getFileMimeType().getOriginalType());
		file.setName(uploadInfo.getFileName());
		file.setPath(uploadInfo.getRelativePath());
		file.setResourceId(resId);
		file.setResourceType(uploadResourceType.getType());
		file.setStatus(uploadType.getValue());
		return file;
	}
}
