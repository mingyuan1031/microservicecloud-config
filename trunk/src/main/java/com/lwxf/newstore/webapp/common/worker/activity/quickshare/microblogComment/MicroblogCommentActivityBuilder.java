package com.lwxf.newstore.webapp.common.worker.activity.quickshare.microblogComment;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogCommentService;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogService;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/19 11:42
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class MicroblogCommentActivityBuilder extends BaseActivityBuilder {
	//jsonmapper是非空
	JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "microblogCommentService")
	private MicroblogCommentService microblogCommentService;
	@Resource(name = "microblogService")
	private MicroblogService microblogService;
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(MicroblogComment.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		//实例化一个日志对象
		SystemActivity systemActivity = newSystemActivityInstance();
		//得到mapperParams（对象或者map）
		Object params = tsManagerEntity.getMapperParams();
		//活动日志r3实体类
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		MicroblogCommentResEntity microblogCommentResEntity = new MicroblogCommentResEntity();
		//MicroblogCommentResEntity parentMicroblogCommentResEntity = new MicroblogCommentResEntity();
		Map<String,Object> dataMap = new HashMap<>();
		Map<String, Object> attrMap;
		MicroblogComment microblogComment;
		String id;

		switch (sqlType){
			case INSERT:
				microblogComment=(MicroblogComment) params;
				systemActivity.setR1(microblogComment.getId());
				systemActivity.setR2(microblogComment.getMicroblogId());
				systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_COMMENT_CREATE.getValue());
				if(microblogComment.getParentId()!=null) {
					dataMap.put("parentId",microblogComment.getParentId());
					dataMap.put("parentContent",this.microblogCommentService.findById(microblogComment.getParentId()).getContent());
				}else {
					Microblog microblog = this.microblogService.findById(microblogComment.getMicroblogId());
					dataMap.put("MicroblogContent",microblog.getContent());
					dataMap.put("MicroblogCreator",microblog.getCreator());
				}
				if (microblogComment.getParentCreator()!=null){
					dataMap.put("parentCreator",microblogComment.getParentCreator());
				}

				microblogCommentResEntity.setContent(microblogComment.getContent());
				activityInfoEntity.setData(dataMap);
				break;
			case UPDATE:
				id = (String) params;
//				id = (String) paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				microblogComment = this.microblogCommentService.findById(id);
				systemActivity.setR1(id);
				microblogCommentResEntity.setName(microblogComment.getCreator());
				systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_COMMENT_UPDATE.getValue());
				attrMap = new HashMap<>();
//				paramsMap.forEach((key,value) ->{
//					if(key.equals(WebConstant.KEY_ENTITY_ID)){
//						return;
//					}
					attrMap.put("id",id);
//				});
				activityInfoEntity.setAttr(attrMap);
				break;
			case DELETE:
				id = (String) tsManagerEntity.getMapperParams();
				microblogComment = this.microblogCommentService.findById(id);
				systemActivity.setR1(microblogComment.getId());
				systemActivity.setR2(microblogComment.getMicroblogId());
				systemActivity.setEvent(SystemActivityEvent.QUICKSHARE_COMMENT_DELETE.getValue());
				if(microblogComment.getParentId()!=null) {
					dataMap.put("parentId",microblogComment.getParentId());
					dataMap.put("parentContent",this.microblogCommentService.findById(microblogComment.getParentId()).getContent());
				}else {
					Microblog microblog = this.microblogService.findById(microblogComment.getMicroblogId());
					dataMap.put("MicroblogContent",microblog.getContent());
					dataMap.put("MicroblogCreator",microblog.getCreator());
				}
				if (microblogComment.getParentCreator()!=null){
					dataMap.put("parentCreator",microblogComment.getParentCreator());
				}
				microblogCommentResEntity.setContent(microblogComment.getContent());
				activityInfoEntity.setData(dataMap);
				break;
		}

		activityInfoEntity.setRes(microblogCommentResEntity);
		systemActivity.setCompanyId(WebUtils.getCurrCompanyId());
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
	@Override
	public Object build(TSManualData tsManualData) {//不存在埋点的情况
		return null;
	}
}
