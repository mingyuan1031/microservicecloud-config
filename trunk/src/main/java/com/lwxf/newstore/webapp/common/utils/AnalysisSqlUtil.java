package com.lwxf.newstore.webapp.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.lwxf.newstore.webapp.common.enums.SQLType;

/**
 * 功能：解析SQL语句
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:25:12
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AnalysisSqlUtil {

	private static Logger logger = LoggerFactory.getLogger(AnalysisSqlUtil.class);

	public static class SQLInfo {
		private String sql; //执行的预编译sql
		private SQLType sqlType;//执行的类型: insert update delete
		private String currentTable; //操作表名
		private Map<String, SQLType> tableNames;//语句中包含的表名

		public SQLInfo(String sql, SQLType sqlType, String currentTable, Map tableNames) {
			this.sql = sql;
			this.sqlType = sqlType;
			this.currentTable = currentTable;
			this.tableNames = tableNames;
		}

		public String getSql(){
			return sql;
		}

		public SQLType getSqlType() {
			return sqlType;
		}

		public Map<String, SQLType> getTableNames() {
			return tableNames;
		}

		public String getCurrentTable() {
			return currentTable;
		}

		public void setCurrentTable(String currentTable) {
			this.currentTable = currentTable;
		}
	}

	public static SQLInfo getSqlType(String sql) {
		String dbType = JdbcConstants.MYSQL;
		//格式化输出
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
		if(stmtList.size() > 1){
			logger.debug("拦截sql为多条，只解析第一条语句");
		}

		SQLStatement stmt = stmtList.get(0);
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		stmt.accept(visitor);

		String currentTable = null;
		if(visitor.getCurrentTable() != null){
			currentTable = visitor.getCurrentTable().toUpperCase();
			if(currentTable != null && currentTable.indexOf("`")>-1){
				currentTable = currentTable.replaceAll("`","");
			}
		}

		Map<String, SQLType> tableList = null;
		SQLType type = null;
		Map<TableStat.Name, TableStat> tables = visitor.getTables();
		if(tables != null){
			tableList = new HashMap(tables.size());
			for (Map.Entry<TableStat.Name, TableStat> table : tables.entrySet()){
				String tbName = table.getKey().toString().toUpperCase();
				SQLType sqlType = SQLType.valueOf(table.getValue().toString().toUpperCase());
				tableList.put(tbName, sqlType);
			}
			type = tableList.get(currentTable);
			if(type == null )
			 logger.error("无法正确解析SQL中的表名，请检查SQL语句:sql = {}",sql);
		}
		return new SQLInfo(sql, type, currentTable,tableList);
	}

	public static void main(String[] args){
		String sql = "delete t from user t, p t2 where t.id = 1";
		List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, "mysql");
		SQLStatement sqlStatement = stmtList.get(0);

		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		sqlStatement.accept(visitor);
		Map<TableStat.Name, TableStat> tables = visitor.getTables();
		System.out.println("CurrentTable: " + visitor.getCurrentTable());
		for (Map.Entry<TableStat.Name, TableStat> table : tables.entrySet()){
			System.out.println(table.getKey() + "=>" + table.getValue());
		}
	}

}
