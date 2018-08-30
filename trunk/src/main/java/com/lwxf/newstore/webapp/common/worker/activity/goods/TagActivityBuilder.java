package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.TagService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.goods.TagStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsResEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.TagResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/19/019 16:09
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class TagActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "tagService")
	private TagService tagService;
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Tag.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		Object params= tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		SystemActivity systemActivity= newSystemActivityInstance();
		TagResEntity tagResEntity = new TagResEntity();
		Map<String, Object> attrMap;
		Tag tag;
		String id;
		switch (sqlType) {
			case INSERT:
				tag = (Tag) params;
				systemActivity.setEvent(SystemActivityEvent.TAG_CREATE.getValue());
				systemActivity.setR1(tag.getId());
				tagResEntity.setName(tag.getName());
				tagResEntity.setColor(tag.getColor());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}
				tag = this.tagService.findById(id);
				systemActivity.setR1(id);
				tagResEntity.setName(tag.getName());
				tagResEntity.setColor(tag.getColor());
				Object disabled = paramsMap.get("disabled");
				if (disabled != null) {
					String falg = "0";
					if((Boolean)disabled==true){
						falg="1";
						systemActivity.setEvent(SystemActivityEvent.TAG_DISABLED.getValue());
					}else{
						systemActivity.setEvent(SystemActivityEvent.TAG_ENABLED.getValue());
					}
					Byte statsValue = Byte.valueOf(falg);
					attrMap = new HashMap<>();
					attrMap.put(WebConstant.KEY_ENTITY_DISABLED,statsValue);
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,TagStatus.getByValue(statsValue).getName());
					activityInfoEntity.setAttr(attrMap);
				} else {
					systemActivity.setEvent(SystemActivityEvent.TAG_MD.getValue());
					attrMap = new HashMap<>();
					paramsMap.forEach((key,value) ->{
						if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_DISABLED)){
							return;
						}
						attrMap.put(key,value);
					});
					activityInfoEntity.setAttr(attrMap);
				}
				break;
			case DELETE:
				//删除的时候 传的是id  所以只会有一个参数 特殊情况除外
				id = (String)tsManagerEntity.getMapperParams();

				tag = this.tagService.findById(id);
				//给R1赋值
				systemActivity.setR1(id);
				//给事件赋值
				systemActivity.setEvent(SystemActivityEvent.TAG_DELETE.getValue());
				tagResEntity.setName(tag.getName());
				tagResEntity.setColor(tag.getColor());
				break;
			default:
				return null;
		}
		activityInfoEntity.setRes(tagResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {//标签不存在埋点的情况
		return null;
	}
}
