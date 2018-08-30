package com.lwxf.newstore.webapp.domain.entity.quickshare;
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
 * 功能：microblog_comment 实体类
 *
 * @author：wangmingyuan(mingyuan1031@163.com)
 * @created：2018-07-02 02:57 
 * @version：2018 Version：1.0 
 * @company：老屋新房 Created with IntelliJ IDEA 
 */ 
@Table(name = "microblog_comment",displayName = "microblog_comment")
public class MicroblogComment extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,name = "microblog_id",displayName = "帖子id，关联消息帖子表")
	private String microblogId;
	@Column(type = Types.CHAR,length = 13,name = "parent_id",displayName = "父评论ID：被回复的评论id")
	private String parentId;
	@Column(type = Types.CHAR,length = 13,name = "parent_creator",displayName = "被回复的评论人id")
	private String parentCreator;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "creator",displayName = "创建人（会员id），关联会员表")
	private String creator;
	@Column(type = Types.VARCHAR,length = 2000,nullable = false,name = "content",displayName = "评论内容")
	private String content;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否禁用（0：禁用，1启用）")
	private Boolean disabled;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "创建时间")
	private Date created;

    public MicroblogComment() {  
     } 

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.microblogId == null) {
			validResult.put("microblogId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.microblogId) > 13) {
				validResult.put("microblogId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}


		if (LwxfStringUtils.getStringLength(this.parentId) > 13) {
				validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}

		if (LwxfStringUtils.getStringLength(this.parentCreator) > 13) {
			validResult.put("parentCreator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.creator == null) {
			validResult.put("creator", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.creator) > 13) {
				validResult.put("creator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.content == null) {
			validResult.put("content", ErrorCodes.VALIDATE_NOTNULL);
		}else{
 			if (LwxfStringUtils.getStringLength(this.content) > 2000) {
				validResult.put("content", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("id","microblogId","parentId","parentCreator","content","disabled");

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
		if(map.containsKey("microblogId")) {
			if (map.getTypedValue("microblogId",String.class)  == null) {
				validResult.put("microblogId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("microblogId",String.class)) > 13) {
					validResult.put("microblogId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("parentId")) {
			if (map.getTypedValue("parentId",String.class)  == null) {
				validResult.put("parentId", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("parentId",String.class)) > 13) {
					validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
			}
		}
		if(map.containsKey("parentCreator")) {
			if (LwxfStringUtils.getStringLength(map.getTypedValue("parentCreator",String.class)) > 13) {
				validResult.put("parentCreator", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if(map.containsKey("content")) {
			if (map.getTypedValue("content",String.class)  == null) {
				validResult.put("content", ErrorCodes.VALIDATE_NOTNULL);
			}else{
 				if (LwxfStringUtils.getStringLength(map.getTypedValue("content",String.class)) > 2000) {
					validResult.put("content", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
				}
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


	public void setMicroblogId(String microblogId){
		this.microblogId=microblogId;
	}

	public String getMicroblogId(){
		return microblogId;
	}

	public void setParentId(String parentId){
		this.parentId=parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setParentCreator(String parentCreator){
		this.parentCreator=parentCreator;
	}

	public String getParentCreator(){
		return parentCreator;
	}

	public void setCreator(String creator){
		this.creator=creator;
	}

	public String getCreator(){
		return creator;
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

	public void setCreated(Date created){
		this.created=created;
	}

	public Date getCreated(){
		return created;
	}
}
