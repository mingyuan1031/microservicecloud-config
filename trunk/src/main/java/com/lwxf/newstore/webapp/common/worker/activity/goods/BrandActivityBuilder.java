package com.lwxf.newstore.webapp.common.worker.activity.goods;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lwxf.commons.json.JsonMapper;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.BrandService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.goods.BrandStatus;
import com.lwxf.newstore.webapp.common.worker.activity.BaseActivityBuilder;
import com.lwxf.newstore.webapp.common.worker.activity.base.ActivityInfoEntity;
import com.lwxf.newstore.webapp.common.worker.activity.base.goods.BrandResEntity;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/17/017 10:34
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class BrandActivityBuilder extends BaseActivityBuilder {
	private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
	@Resource(name = "brandService")
	private BrandService brandService;
	@Override
	public void registerToWorker() {
		this.systemActivityWorker.register(Brand.class,this);
	}


	@Override
	public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {//埋点的情况
		SystemActivity systemActivity = newSystemActivityInstance();
		Object params= tsManagerEntity.getMapperParams();
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		BrandResEntity brandResEntity = new BrandResEntity();
		Map<String,Object> attrMap;
		Brand brand;
		String id;

		switch (sqlType){
			case INSERT:
				brand = (Brand) params;
				systemActivity.setEvent(SystemActivityEvent.BRAND_CREATE.getValue());
				//日志设置R1当前操作的资源Id
				systemActivity.setR1(brand.getId());
				//给R3中res赋值
				brandResEntity.setName(brand.getName());
				brandResEntity.setContent(brand.getContent());
				brandResEntity.setDisabled(brand.getDisabled());
				break;
			case UPDATE:
				Map<String, Object> paramsMap = (Map) params;
				id = (String)paramsMap.get(WebConstant.KEY_ENTITY_ID);
				if(null == id){
					return null;
				}

				brand = this.brandService.findById(id);
				//日志设置R1当前操作的资源Id
				systemActivity.setR1(id);
				//给品牌的res实体类赋值 然后在最后赋值 给R3
				brandResEntity.setName(brand.getName());
				brandResEntity.setContent(brand.getContent());
				brandResEntity.setDisabled(brand.getDisabled());
				Object disabled = paramsMap.get("disabled");
				//如果进行的状态修改的操作 是单独执行的 点击修改状态按钮直接马上就会进行操作的 所以是单独的 不同于基础属性的修改
				if (disabled != null) {
					String flag = "0";
					//等于0代表是进行启用操作
					if((Boolean) disabled==true){
						flag = "1";
						systemActivity.setEvent(SystemActivityEvent.BRAND_DISABLED.getValue());
					}else{//不然就是禁用操作
						systemActivity.setEvent(SystemActivityEvent.BRAND_ENABLED.getValue());
					}

					//获取到disabled这个参数的值
					Byte statsValue = Byte.valueOf(flag);

					attrMap = new HashMap<>();
					//进行的是什么操作
					attrMap.put(WebConstant.KEY_ENTITY_DISABLED,statsValue);
					//对操作进行补充说明
					attrMap.put(WebConstant.KEY_ACTIVITY_STATUS_NAME,BrandStatus.getByValue(statsValue).getName());
					//把attr放进attrMap中
					activityInfoEntity.setAttr(attrMap);
				} else {
					//修改基础属性
					systemActivity.setEvent(SystemActivityEvent.BRAND_MD.getValue());
					attrMap = new HashMap<>();
					//便利参数的map对象
					paramsMap.forEach((key,value) ->{
						//如果参数是 id  或者 状态 则不允许修改
						if(key.equals(WebConstant.KEY_ENTITY_ID) || key.equals(WebConstant.KEY_ENTITY_DISABLED)){
							return;
						}
						//把修改的属性值名称及对应的值放进attr中
						attrMap.put(key,value);
					});
					//把attr放入R3的实体类类型中
					activityInfoEntity.setAttr(attrMap);
				}
				break;
			case DELETE:
				//删除的时候 传的是id  所以只会有一个参数 特殊情况除外
				id = (String)tsManagerEntity.getMapperParams();

				brand = this.brandService.findById(id);
				//给R1赋值
				systemActivity.setR1(id);
				//给事件赋值
				systemActivity.setEvent(SystemActivityEvent.BRAND_DELETE.getValue());
				brandResEntity.setName(brand.getName());
				brandResEntity.setContent(brand.getContent());
				brandResEntity.setDisabled(brand.getDisabled());
				break;
			default:
				return null;
		}
		//把当前操作的数据赋值给res 并赋值给R3  因为没有父类 所以不赋值R2
		activityInfoEntity.setRes(brandResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}

	@Override
	public Object build(TSManualData tsManualData) {
		SystemActivity systemActivity = newSystemActivityInstance();
		Map map= (Map) tsManualData.getParams();
		BrandResEntity brandResEntity = new BrandResEntity();
		Brand brand  = (Brand) tsManualData.get("brand");
		ActivityInfoEntity activityInfoEntity = new ActivityInfoEntity();
		if(tsManualData.getEvent().equals(SystemActivityEvent.BRAND_MD.getValue())){
			systemActivity.setEvent(SystemActivityEvent.BRAND_MD.getValue());
			systemActivity.setR1((String) map.get(WebConstant.KEY_ENTITY_ID));
			brandResEntity.setContent(brand.getContent());
			brandResEntity.setName(brand.getName());
			brandResEntity.setDisabled(brand.getDisabled());
			Map attr = new HashMap<String,Object>();
			attr.put("image",map.get("image"));
			activityInfoEntity.setAttr(attr);
		}else{
			return null;
		}
		activityInfoEntity.setRes(brandResEntity);
		systemActivity.setR3(this.jsonMapper.toJson(activityInfoEntity));
		return systemActivity;
	}
}
