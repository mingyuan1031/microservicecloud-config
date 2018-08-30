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
 * 功能：goods_comment 实体类
 *
 * @author：F_baisi(F_baisi@163.com)
 * @created：2018-07-30 10:05
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Table(name = "goods_comment",displayName = "goods_comment")
public class GoodsComment extends IdEntity  {
	private static final long serialVersionUID = 1L;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "member_id",displayName = "会员id 评论该商品的人是谁")
	private String memberId;
	@Column(type = Types.CHAR,length = 13,updatable = false,name = "parent_id",displayName = "父类是谁 如果是买家的评论 那就为 0  是店家的回复 这里就填 回复的那条评论的id")
	private String parentId;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "goods_id",displayName = "商品的id  这些评论 还有回复 是针对哪个商品的")
	private String goodsId;
	@Column(type = Types.VARCHAR,length = 200,updatable = false,name = "content",displayName = "评论的内容是什么  可以带图 内容数字也有上线")
	private String content;
	@Column(type = Types.BIT,name = "is_answered",displayName = "是否已回复 买家的评论 店家是否回复  false 未回复 true 已回复")
	private Boolean answered;
	@Column(type = Types.BIT,name = "is_auditd",displayName = "是否审核 true 审核过关  false 未审核 评论需审核过后才可以显示")
	private Boolean auditd;
	@Column(type = Types.BIT,nullable = false,name = "is_disabled",displayName = "是否隐藏该评论 false 显示 true 隐藏")
	private Boolean disabled;
	@Column(type = TypesExtend.DATETIME,nullable = false,updatable = false,name = "created",displayName = "该评论的创建时间")
	private Date created;
	@Column(type = Types.CHAR,length = 13,nullable = false,updatable = false,name = "goods_extend_id",displayName = "关联报价表 获取所购买的报价信息 ")
	private String goodsExtendId;
	@Column(type = Types.CHAR,length = 1,updatable = false,name = "score",displayName = "商品品论的打分状态  分三等 优(0) 良(1) 差(2) 前端显示名称可换  ")
	private String score;

	public GoodsComment() {
	}

	public RequestResult validateFields() {
		Map<String, String> validResult = new HashMap<>();
		if (this.memberId == null) {
			validResult.put("memberId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.memberId) > 13) {
				validResult.put("memberId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.parentId) > 13) {
			validResult.put("parentId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.goodsId == null) {
			validResult.put("goodsId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.goodsId) > 13) {
				validResult.put("goodsId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.content) > 200) {
			validResult.put("content", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if (this.disabled == null) {
			validResult.put("disabled", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.created == null) {
			validResult.put("created", ErrorCodes.VALIDATE_NOTNULL);
		}
		if (this.goodsExtendId == null) {
			validResult.put("goodsExtendId", ErrorCodes.VALIDATE_NOTNULL);
		}else{
			if (LwxfStringUtils.getStringLength(this.goodsExtendId) > 13) {
				validResult.put("goodsExtendId", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
			}
		}
		if (LwxfStringUtils.getStringLength(this.score) > 1) {
			validResult.put("score", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		}
		if(validResult.size()>0){
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}else {
			return null;
		}
	}

	private final static List<String> propertiesList = Arrays.asList("answered","auditd","disabled");

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
		if(map.containsKey("answered")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("answered",String.class))) {
				validResult.put("answered", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("auditd")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("auditd",String.class))) {
				validResult.put("auditd", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
			}
		}
		if(map.containsKey("disabled")) {
			if (!DataValidatorUtils.isBoolean(map.getTypedValue("disabled",String.class))) {
				validResult.put("disabled", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
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


	public void setMemberId(String memberId){
		this.memberId=memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setParentId(String parentId){
		this.parentId=parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setGoodsId(String goodsId){
		this.goodsId=goodsId;
	}

	public String getGoodsId(){
		return goodsId;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setAnswered(Boolean answered){
		this.answered=answered;
	}

	public Boolean getAnswered(){
		return answered;
	}

	public void setAuditd(Boolean auditd){
		this.auditd=auditd;
	}

	public Boolean getAuditd(){
		return auditd;
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

	public void setGoodsExtendId(String goodsExtendId){
		this.goodsExtendId=goodsExtendId;
	}

	public String getGoodsExtendId(){
		return goodsExtendId;
	}

	public void setScore(String score){
		this.score=score;
	}

	public String getScore(){
		return score;
	}
}
