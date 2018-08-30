package com.lwxf.newstore.webapp.baseservice.tsmanager;

import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.AnalysisSqlUtil;

/**
 * 功能：解析拦截器封装的实体类
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:52:48
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class TSManagerEntity {
	private SQLType sqlType; //sql类型
	private String currentTable; //操作表名
	private Class clazz;//操作数据库表对应的class
	private String mappings;// 调用mapper的包名.类名.方法名
	private Object mapperParams;// Mapper传入的参数（对象或Map）
	private Integer affectedRows;//语句影响数据库行数,beforeUpdate时不存在
	private AnalysisSqlUtil.SQLInfo sqlInfo;//sql信息

	public TSManagerEntity() {

	}

	public String getMappings() {
		return mappings;
	}

	public void setMappings(String mappings) {
		this.mappings = mappings;
	}

	public Object getMapperParams() {
		return mapperParams;
	}

	public void setMapperParams(Object mapperParams) {
		this.mapperParams = mapperParams;
	}

	public AnalysisSqlUtil.SQLInfo getSqlInfo() {
		return sqlInfo;
	}

	public Integer getAffectedRows() {
		return affectedRows;
	}

	public void setAffectedRows(Integer affectedRows) {
		this.affectedRows = affectedRows;
	}

	public void setSqlInfo(AnalysisSqlUtil.SQLInfo sqlInfo) {
		this.sqlInfo = sqlInfo;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public SQLType getSqlType() {
		return sqlType;
	}

	public void setSqlType(SQLType sqlType) {
		this.sqlType = sqlType;
	}

	public String getCurrentTable() {
		return currentTable;
	}

	public void setCurrentTable(String currentTable) {
		this.currentTable = currentTable;
	}
}
