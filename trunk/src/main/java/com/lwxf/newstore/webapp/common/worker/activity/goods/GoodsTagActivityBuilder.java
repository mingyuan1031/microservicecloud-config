package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTagService;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.GoodsTagResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsTag;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/19/019 18:40
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class GoodsTagActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

	@Resource(name = "goodsTagService")
	private GoodsTagService goodsTagService;
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Override
	public void registerToWorker() {
		systemActivityWorker.register(GoodsTag.class,this);
	}

	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params= tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		//todo 新建一个实体类然后new对象
		GoodsTagResEntity goodsTagResEntity = new GoodsTagResEntity();
		Map<String,Object> attrMap;
		GoodsTag goodsTag;
		String id;

		switch (sqlType){
			case INSERT:
				goodsTag = (GoodsTag) params;
				systemActivity.setEvent(SystemActivityEvent.GOODS_TAG_CREATE.getValue());
				//日志设置R1当前操作的资源Id
				systemActivity.setR1(goodsTag.getGoodsId());
				//给R3中res赋值
				goodsTagResEntity.setTagId(goodsTag.getTagId());
				break;
			case DELETE:
				//删除的时候 传的是id  所以只会有一个参数 特殊情况除外
				params = (String) params;
				Goods goods=this.goodsService.findById((String) params);
				//给R1赋值
				systemActivity.setR1(goods.getId());
				//给事件赋值
				systemActivity.setEvent(SystemActivityEvent.GOODS_TAG_DELETE.getValue());
				break;
			default:
				return null;
		}
		//把当前操作的数据赋值给res 并赋值给R3  因为没有父类 所以不赋值R2
		activityInfoEntity.setRes(goodsTagResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {//不存在埋点的情况
		return null;
	}
}
