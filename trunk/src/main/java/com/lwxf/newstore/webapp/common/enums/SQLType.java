package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：SQL类型
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:49:10
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum SQLType {
	SELECT,//查询
	INSERT,//插入
	DELETE,//删除
	UPDATE,//更新
	SELECTDELETE//delete中嵌套select
}
