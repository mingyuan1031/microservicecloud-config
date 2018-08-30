package com.lwxf.newstore.webapp.common.worker.activity.base;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.common.worker.SystemActivityWorker;
import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;

/**
 * 功能：
 *
 * @author：zhaozhenyi(zhenyi.zhao@ihydt.com)
 * @create：2017-05-15 10:21:25
 * @version：2017 Version：1.0
 * @company：创海科技 Created with IntelliJ IDEA
 */
public abstract class AbstractActivityBuilder implements IActivityBuilder {

    @Resource(name = "systemActivityWorker")
    protected SystemActivityWorker systemActivityWorker;

	/**
	 * 获取对象id属性的值
	 *
	 * @param object
	 * @return
	 */
	private static String getIdFieldValue(Object object) {
		Field[] fields = object.getClass().getFields();
		for (Field field : fields) {
			if ("id".equals(field.getName())) {
				try {
					return field.get(object).toString();
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}

    @PostConstruct
    protected void init() {
        this.registerToWorker();
    }

    /**
     * 子类必须重写
     */
    public abstract void registerToWorker();

    @Override
    public Object build(TSManagerEntity tsManagerEntity, SQLType sqlType) {
        SystemActivity orgActivity = newSystemActivityInstance();
        orgActivity.setEvent(getEvent(tsManagerEntity));
        orgActivity.setR1(getR1(tsManagerEntity));
        orgActivity.setR2(getR2(tsManagerEntity));
        orgActivity.setR3(getR3(tsManagerEntity));
        return orgActivity;
    }

    @Override
    public Object build(TSManualData tsManualData) {
        SystemActivity systemActivity = newSystemActivityInstance();
        systemActivity.setEvent(tsManualData.getEvent());
        systemActivity.setR1(getId(tsManualData.getParams()));
        systemActivity.setR2(null);
        systemActivity.setR3(JsonUtil.toJson(tsManualData.getParams()));
        return systemActivity;
    }

    /**
     * 生成事件名
     * @param tsManagerEntity
     * @return
     */
    protected abstract String getEvent(TSManagerEntity tsManagerEntity);

    /**
     * 获取操作主体标识
     * @param tsManagerEntity
     * @return
     */
    protected abstract String getR1(TSManagerEntity tsManagerEntity);

    /**
     * 获取额外参数
     * @param tsManagerEntity
     * @return
     */
    protected abstract String getR2(TSManagerEntity tsManagerEntity);

    /**
     * 获取补充数据
     * @param tsManagerEntity
     * @return
     */
    protected abstract String getR3(TSManagerEntity tsManagerEntity);

    /**
     * 实例化一个日志对象
     * @return
     */
    protected SystemActivity newSystemActivityInstance(){
		SystemActivity systemActivity = new SystemActivity();
        systemActivity.setCreator(WebUtils.getCurrUserId());
		systemActivity.setCreated(new Date());
        systemActivity.setCompanyId(WebUtils.getCurrCompanyId());
        return systemActivity;
    }

    /**
     *
     * 尝试获取主键标识
     * @param object
     * @return
     */
    protected String getId(Object object){
        if(object instanceof String){
            return (String) object;
        }

        if(object instanceof Map){
            Map map = (Map) object;
            return (String)map.get("id");
        }

        if(object instanceof IdEntity){
            return getIdFieldValue(object);
        }
        return null;
    }

}
