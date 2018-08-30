package com.lwxf.newstore.webapp.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;

/**
 * 功能：自定义根据表名生成实体工具
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-18 11:26:30
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GenEntity {
	/**
	 * 数据库连接
	 */
	private static final String URL = "jdbc:mysql://192.168.1.4:3306/newstore?useUnicode=true&characterEncoding=UTF-8";
	private static final String NAME = "root";
	private static final String PASS = "lwxf@123";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	/**
	 * 不允许修改的列
	 */
	Map<String, String> mapDisabledUpdate = new HashMap<>();
	/**
	 * 不允许修改列属性
	 */
	List<String> disableUpdateCol = new ArrayList<>();
	/**
	 * 外键注解
	 */
	Map fkMap = new HashMap<String, String>();
	/**
	 * 约束注解
	 */
	Map uqMap = new HashMap<String, String>();
	/**
	 * 约束注解串
	 */
	String uqStr = "";
	/**
	 * 用户名
	 */
	private String username = "easypm4_schema";
	/**
	 * 包的路径
	 */
	private String packageUrl = "com.lwxf.newstore.webapp.domain.entity.";
	/**
	 * 类输出路径
	 */
	private String outputPath = "E:\\workspace\\newstore\\codes\\lwxf_newstore\\trunk\\src\\main\\java\\com\\chuanghai\\easypm\\webapp\\domain\\entity\\";
	/**
	 * 表名
	 */
	private String tablename = "";
	/**
	 * 列名数组
	 */
	private String[] colnames;
	/**
	 * 数据库列名数组
	 */
	private String[] dbColNames;
	/**
	 * 列名类型数组
	 */
	private String[] colTypes;
	/**
	 * 列名描述数组
	 */
	private String[] colDescs;
	/**
	 * 列名大小数组
	 */
	private int[] colSizes;
	/**
	 * 是否允许为空
	 */
	private String[] colIsNullAble;
	/**
	 * 默认值
	 */
	private String[] colDefaultValue;
	/**
	 * 表的主键信息注解
	 */
	private String primaryConstraints = "";
	/**
	 * 是否需要导入包java.util.*
	 */
	private boolean importUtil = true;
	/**
	 * 是否需要导入包java.sql.*
	 */
	private boolean importSql = true;
	private String author;

	/**
	 * 构造函数
	 */
	public GenEntity(final String tName, final String packageName, final String appointPath, final Map<String, String> mapDisabledUpdate) {
		this(tName,packageName,appointPath,mapDisabledUpdate,"renzhongshan(d3shan@126.com)");
	}

	/**
	 * 构造函数
	 */
	public GenEntity(final String tName, final String packageName, final String appointPath, final Map<String, String> mapDisabledUpdate, String author) {
		//表名转化为小写
		this.tablename = LwxfStringUtils.lowerCase(tName);
		this.packageUrl = this.packageUrl + packageName;
		this.outputPath = this.outputPath + packageName;
		this.mapDisabledUpdate = mapDisabledUpdate;
		this.author = author;
		this.disableUpdateCol = getDisableUpdateCol();
		if (appointPath != null) {
			this.outputPath = appointPath + "\\" + packageName;
		}
		//创建连接
		Connection con = null;
		Statement statement = null;
		StringBuilder fkSqlSb = new StringBuilder();
		fkSqlSb.append("select t.CONSTRAINT_NAME,t.COLUMN_NAME,t.REFERENCED_TABLE_NAME,t.REFERENCED_COLUMN_NAME from INFORMATION_SCHEMA.KEY_COLUMN_USAGE t where t.TABLE_SCHEMA='")
				.append(username)
				.append("' and t.TABLE_NAME='")
				.append(tablename)
				.append("' and t.REFERENCED_TABLE_NAME is not null");
		try {
			try {
				Class.forName(DRIVER);
				con = DriverManager.getConnection(URL, NAME, PASS);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			/*************************取外键信息 start *********************/
			String key;
			String className;
			String referencedColumnName;
			String fkAnnotation;
			if (con != null) {
				statement = con.createStatement();
			}
			String uqSql;
			ResultSet uqRs = null;
			String uKey;
			String uValue;
			StringBuffer uqTempSb;
			Iterator iterator;
			String uqTemp;
			DatabaseMetaData databaseMetaData = null;
			ResultSet pkRs;
			List<String> pkList = new ArrayList<>();
			ResultSet rs = null;
			int size;
			int i;
			String content;
			String outPutFileName;
			ResultSet fkRs = null;
			if (statement != null) {
				try {
					fkRs = statement.executeQuery(fkSqlSb.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			try {
				if (fkRs != null) {
					while (fkRs.next()) {
						key = initcapCol(fkRs.getString("COLUMN_NAME"));
						className = initcap(fkRs.getString("REFERENCED_TABLE_NAME"));
						referencedColumnName = initcapCol(fkRs.getString("REFERENCED_COLUMN_NAME"));
						fkAnnotation = "@ForeignKey(refEntityClass = " + className + ".class,refFieldName = \"" + referencedColumnName + "\")";
						fkMap.put(key, fkAnnotation);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fkRs != null) {
					fkRs.close();
				}

			}
			/*************************取外键信息 end   *********************/
			/*************************取约束信息 start**********************/
			uqSql = "select t.CONSTRAINT_NAME,t.COLUMN_NAME  from INFORMATION_SCHEMA.KEY_COLUMN_USAGE t where lower(t.CONSTRAINT_NAME) like 'u%'  and  t.TABLE_SCHEMA='" + username + "' and t.TABLE_NAME='" + tName + "'";
			if (statement != null) {
				uqRs = statement.executeQuery(uqSql);
			}
			if (uqRs != null) {
				while (uqRs.next()) {
					uKey = uqRs.getString("CONSTRAINT_NAME");
					String uTempValue = "\"" + initcapCol(uqRs.getString("COLUMN_NAME")) + "\"";
					uValue = (String) uqMap.get(uKey);
					if (uValue == null) {
						uqMap.put(uKey, uTempValue);
					} else {
						uValue = uValue .concat( ",").concat(uTempValue);
						uqMap.put(uKey, uValue);
					}
				}
				uqRs.close();
			}
			uqTempSb = new StringBuffer();
			iterator = uqMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				Object val = entry.getValue();
				uqTempSb.append("@UniqueConstraint(fieldNames = {")
						.append(String.valueOf(val))
						.append("}),");
			}
			uqTemp = uqTempSb.toString();
			if (uqTemp.length() > 0) {
				uqTemp = uqTemp.substring(0, uqTemp.length() - 1);
				uqStr = "uniqueConstraints = {";
				uqStr = uqStr + uqTemp;
				uqStr = uqStr + "},";
			}
			/*************************取约束信息 end  **********************/
			if (con != null) {
				databaseMetaData = con.getMetaData();
			}
			if (databaseMetaData != null) {
				pkRs = databaseMetaData.getPrimaryKeys(null, "%", tablename);
				while (pkRs.next()) {
					pkList.add(initcapCol(pkRs.getString("COLUMN_NAME")));
					System.out.println("主键：" + pkRs.getString("COLUMN_NAME"));
				}
				if (pkList.size() > 0) {
					if (pkList.size() > 1) {
						primaryConstraints = " primaryConstraints = @PrimaryConstraint(fieldNames = {";
						for (String col : pkList) {
							primaryConstraints = primaryConstraints .concat("\"") .concat(col).concat("\",");
						}
						primaryConstraints = primaryConstraints.substring(0, primaryConstraints.length() - 1);
						primaryConstraints = primaryConstraints + "}), ";
					}
				}
				rs = databaseMetaData.getColumns(null, "%", tablename, "%");
			}
			//直接取表字段
			System.out.println("表名：" + tablename + "\t\n表字段信息：");
			if (rs != null) {
				rs.last();
				size = rs.getRow();
				rs.beforeFirst();
				dbColNames = new String[size];
				colnames = new String[size];
				colTypes = new String[size];
				colSizes = new int[size];
				colDescs = new String[size];
				colIsNullAble = new String[size];
				colDefaultValue = new String[size];
				i = 0;
				while (rs.next()) {
					System.out.println(rs.getString("COLUMN_NAME") + "----" + rs.getString("REMARKS") + "----" + rs.getString("TYPE_NAME") + "--COLUMN_SIZE:" + rs.getInt("COLUMN_SIZE"));
					dbColNames[i] = rs.getString("COLUMN_NAME");
					colnames[i] = initcapCol(rs.getString("COLUMN_NAME"));
					colTypes[i] = rs.getString("TYPE_NAME");
					colDescs[i] = rs.getString("REMARKS");
					colSizes[i] = rs.getInt("COLUMN_SIZE");
					colIsNullAble[i] = rs.getString("IS_NULLABLE");
					colDefaultValue[i] = rs.getString(13);
					if (CommonConstant.STRING_DATETIME.equalsIgnoreCase(colTypes[i])) {
						importUtil = true;
					}
					if (CommonConstant.STRING_IMAGE.equalsIgnoreCase(colTypes[i]) || CommonConstant.STRING_TEXT.equalsIgnoreCase(colTypes[i])) {
						importSql = true;
					}
					i++;
				}
			}
			content = parse(colnames, colTypes, colSizes);
			try {
				File directory = new File(outputPath);
				try {
					directory.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
				}
				outPutFileName = outputPath + "\\" + initcap(tablename) + ".java";
				System.out.println("输出路径：" + outPutFileName);
				File file = new File(outPutFileName);
				try (FileOutputStream fop = new FileOutputStream(file)) {
					// if file doesn't exists, then create it
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
					// get the content in bytes
					byte[] contentInBytes = content.getBytes(Charset.defaultCharset());
					fop.write(contentInBytes);
					fop.flush();
					fop.close();
					System.out.println("写文件内容完成");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {


			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 功能：生成实体类主体代码
	 *
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		//判断是否导入工具包
		sb.append("package ").append(packageUrl).append(";\r\n");
		if (importUtil) {
			sb.append("import java.util.*;\r\n");
		}
		if (importSql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("import java.util.Date;\r\n");
		sb.append("import java.util.Arrays;\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("\r\n");
		sb.append("import com.lwxf.mybatis.utils.TypesExtend;\r\n");
		sb.append("import com.lwxf.commons.exception.ErrorCodes;\r\n");
		sb.append("import com.lwxf.commons.utils.DataValidatorUtils;\r\n");
		sb.append("import com.lwxf.commons.utils.LwxfStringUtils;\r\n");
		sb.append("import com.lwxf.mybatis.annotation.Table;\r\n");
		sb.append("import com.lwxf.mybatis.annotation.Column;\r\n");
		if (uqStr.length() > 0) {
			sb.append("import com.lwxf.mybatis.annotation.UniqueConstraint;\r\n");
		}
		if (fkMap.size() > 0) {
			sb.append("import com.lwxf.mybatis.annotation.ForeignKey;\r\n");
		}
		sb.append("\r\n");
		if (createExtendsIdEntity().length() > 0) {
			sb.append("import com.lwxf.newstore.webapp.domain.entity.base.IdEntity;\r\n");

		}
		sb.append("import com.lwxf.mybatis.utils.MapContext;\r\n");
		sb.append("import com.lwxf.newstore.webapp.common.result.RequestResult;\r\n");
		sb.append("import com.lwxf.newstore.webapp.common.result.ResultFactory;\r\n");

		//注释部分
		sb.append("/**\r\n");
		sb.append(" * 功能：").append(tablename).append(" 实体类\r\n");
		sb.append(" *\r\n");
		sb.append(" * @author：").append(this.author).append("\r\n");
		sb.append(" * @created：" + DateUtil.dateTimeToString(new Date(), "yyyy-MM-dd hh:mm") + " \r\n");
		sb.append(" * @version：2018 Version：1.0 \r\n");
		sb.append(" * @company：老屋新房 Created with IntelliJ IDEA" + " \r\n");
		sb.append(" */ \r\n");
		//实体表注解
		sb.append("@Table(name = \"" + tablename + "\"," + primaryConstraints + uqStr + "displayName = \"" + tablename + "\")");
		//实体部分
		sb.append("\r\npublic class " + initcap(tablename) + createExtendsIdEntity() + " {\r\n");
		if (createExtendsIdEntity().length() > 0) {
			sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");

		}
		//属性
		processAllAttrs(sb);
		sb.append("\r\n");
		sb.append("    public " + initcap(tablename) + "() {  \r\n");
		sb.append("     } \r\n");
		sb.append("\r\n");
		//验证方法生成
		processValidate(sb);
		processValidateMap(sb);
		sb.append("\r\n");
		//get set方法
		processAllMethod(sb);
		sb.append("}\r\n");

		return sb.toString();
	}

	private void processValidate(StringBuffer sb) {
		//验证头
		sb.append("\tpublic RequestResult validateFields() {\r\n");
		sb.append("\t\tMap<String, String> validResult = new HashMap<>();\r\n");
		for (int i = 0; i < colnames.length; i++) {
			//id不生成
			if ("id".equalsIgnoreCase(colnames[i])) {
				continue;
			}
			//验证体开始
			if ("bigint".equalsIgnoreCase(colTypes[i]) || "INT UNSIGNED".equalsIgnoreCase(colTypes[i]) || "INT".equalsIgnoreCase(colTypes[i]) ||
					"tinyint".equalsIgnoreCase(colTypes[i]) || "TINYINT UNSIGNED".equalsIgnoreCase(colTypes[i]) || "BIGINT UNSIGNED".equalsIgnoreCase(colTypes[i]) ||
					"bit".equalsIgnoreCase(colTypes[i]) || "datetime".equalsIgnoreCase(colTypes[i])) {
				//是否允许为空
				if ("NO".equalsIgnoreCase(colIsNullAble[i])) {
					sb.append("\t\tif (this." + colnames[i] + " == null) {\r\n");
					sb.append("\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_NOTNULL);\r\n");
					sb.append("\t\t}\r\n");
				}
			}
			//验证体开始
			if ("varchar".equalsIgnoreCase(colTypes[i]) || "char".equalsIgnoreCase(colTypes[i])) {
				//是否允许为空
				if ("NO".equalsIgnoreCase(colIsNullAble[i])) {
					sb.append("\t\tif (this." + colnames[i] + " == null) {\r\n");
					sb.append("\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_NOTNULL);\r\n");
					sb.append("\t\t}");
					//长度校验
					sb.append("else{\r\n " +
							"\t\t\tif (LwxfStringUtils.getStringLength(this." + colnames[i] + ") > " + colSizes[i] + ") {\r\n");
					sb.append("\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);\r\n");
					sb.append("\t\t\t}\r\n");
					//特殊验证生成
					sb.append(generateSpecialValidator(colnames[i], true));
					sb.append("\t\t}\r\n");
				} else {
					//长度校验
					sb.append("\t\tif (LwxfStringUtils.getStringLength(this." + colnames[i] + ") > " + colSizes[i] + ") {\r\n");
					sb.append("\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);\r\n");
					sb.append("\t\t}\r\n");
					//特殊验证生成
					sb.append(generateSpecialValidator(colnames[i], true));
				}
			}
		}
		sb.append("\t\tif(validResult.size()>0){\n")
				.append("\t\t\treturn ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);\n")
				.append("\t\t}else {\n")
				.append("\t\t\treturn null;\n")
				.append("\t\t}\r\n");
		sb.append("\t}\r\n");
	}

	/**
	 * 生成特殊情况验证
	 *
	 * @param key  验证的字段名
	 * @param flag 不带else
	 * @return
	 */
	private String generateSpecialValidator(String key, boolean flag) {
		StringBuffer sb = new StringBuffer();
		if ("email".equalsIgnoreCase(key)) {
			if (flag) {
				//email校验
				sb.append("\t\t\telse if(!ValidateUtils.isEmail(this." + key + ")){\r\n")
						.append("\t\t\t\tvalidResult.put(\"" + key + "\", ErrorCodes.VALIDATE_INVALID_EMAIL);\r\n")
						.append("\t\t\t}\r\n");
			} else {
				//email校验
				sb.append("\t\t\telse if(!ValidateUtils.isEmail(map.getTypedValue(\"" + key + "\",String.class))){\r\n")
						.append("\t\t\t\tvalidResult.put(\"" + key + "\", ErrorCodes.VALIDATE_INVALID_EMAIL);\r\n")
						.append("\t\t\t}\r\n");
			}
		}
		if ("mobile".equalsIgnoreCase(key)) {
			if (flag) {
				//mobile校验
				sb.append("\t\tif(this." + key + "!=null){\r\n")
						.append("\t\t\tif(!ValidateUtils.isMobile(this." + key + ")){\r\n")
						.append("\t\t\t\tvalidResult.put(\"" + key + "\", ErrorCodes.VALIDATE_INVALID_MOBILE_NO);\r\n")
						.append("\t\t\t}\r\n")
						.append("\t\t}\r\n");

			} else {
				//mobile校验 if(map.get("mobile")!=null) {}再执行
				sb.append("\t\t\telse if(!ValidateUtils.isMobile(map.getTypedValue(\"" + key + "\",String.class))){\r\n")
						.append("\t\t\t\tvalidResult.put(\"" + key + "\", ErrorCodes.VALIDATE_INVALID_MOBILE_NO);\r\n")
						.append("\t\t\t}\r\n");
			}
		}
		return sb.toString();
	}

	private List<String> getDisableUpdateCol() {
		List<String> disableUpdateColTmp;
		List<String> disableUpdateCol = new ArrayList<>();
		String s = mapDisabledUpdate.get(tablename);
		if (s != null) {
			disableUpdateColTmp = Arrays.asList(s.split(","));
			int count = disableUpdateColTmp.size();
			for (int i = 0; i < count; i++) {
				disableUpdateCol.add(i, initcapCol(disableUpdateColTmp.get(i)));
			}
		}
		return disableUpdateCol;
	}

	/**
	 * 生成MapContext验证方法
	 *
	 * @param sb
	 */
	private void processValidateMap(StringBuffer sb) {

		//1.2. 处理不需要更新的字段
		List<String> disableUpdateCol = getDisableUpdateCol();
		//2. 验证有没有多余的参数
		int colLen = colnames.length;
		StringBuffer psSb = new StringBuffer();
		for (int j = 0; j < colLen; j++) {
			//如果包含在不更新字段里面，就不再生成
			if (disableUpdateCol.contains(colnames[j])) {
				continue;
			}
			psSb.append("\"")
					.append(colnames[j])
					.append("\",");
		}
		String tmpStr = psSb.toString();
		int len = tmpStr.length();
		if (len > 1) {
			tmpStr = tmpStr.substring(0, len - 1);
		}
		sb.append("\r\n")
				.append("\tprivate final static List<String> propertiesList = Arrays.asList(" + tmpStr + ");\r\n")
				.append("\r\n")
				.append("\tpublic static RequestResult validateFields(MapContext map) {\r\n")
				.append("\t\tMap<String, String> validResult = new HashMap<>();\r\n")
				.append("\t\tif(map.size()==0){\r\n")
				.append("\t\t\treturn ResultFactory.generateErrorResult(\"error\",ErrorCodes.VALIDATE_NOTNULL);\r\n")
				.append("\t\t}\r\n")
				.append("\t\tboolean flag;\r\n")
				.append("\t\tSet<String> mapSet = map.keySet();\r\n")
				.append("\t\tflag = propertiesList.containsAll(mapSet);\r\n")
				.append("\t\tif(!flag){\r\n")
				.append("\t\t\treturn ResultFactory.generateErrorResult(\"error\",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);\r\n")
				.append("\t\t}\r\n");

		for (int i = 0; i < colnames.length; i++) {
			//id不生成
			if ("id".equalsIgnoreCase(colnames[i])) {
				continue;
			}
			//不需要更新的列跳过
			if (disableUpdateCol != null && disableUpdateCol.size() > 0) {
				if (disableUpdateCol.contains(colnames[i])) {
					continue;
				}
			}
			String validatorMethodName = "";

			//开始生成数据格式校验
			if ("BigDecimal".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isDecmal4(";
			}
			if ("Long".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isLong(";
			}
			if ("Float".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isFloat(";
			}
			if ("Integer".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isInteger1(";
			}
			if ("Short".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isShort1(";
			}
			if ("Byte".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isByte1(";
			}
			if ("Boolean".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isBoolean(";
			}
			if ("Date".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				validatorMethodName = "isDate(";
			}
			if (validatorMethodName.length() > 0) {
				sb.append("\t\tif(map.containsKey(\"" + colnames[i] + "\")) {\r\n");
				sb.append("\t\t\tif (!DataValidatorUtils." + validatorMethodName + "map.getTypedValue(\"" + colnames[i] + "\",String.class))) {\r\n");
				sb.append("\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);\r\n");
				sb.append("\t\t\t}\r\n");
				sb.append("\t\t}\r\n");
			}
		}
		for (int i = 0; i < colnames.length; i++) {
			//id不生成
			if ("id".equalsIgnoreCase(colnames[i])) {
				continue;
			}
			if (disableUpdateCol != null) {
				if (disableUpdateCol.contains(colnames[i])) {
					continue;
				}
			}
			//验证体开始
				if ("bigint".equalsIgnoreCase(colTypes[i]) || "INT UNSIGNED".equalsIgnoreCase(colTypes[i]) || "INT".equalsIgnoreCase(colTypes[i]) ||
						"tinyint".equalsIgnoreCase(colTypes[i]) || "TINYINT UNSIGNED".equalsIgnoreCase(colTypes[i]) || "BIGINT UNSIGNED".equalsIgnoreCase(colTypes[i]) ||
						"bit".equalsIgnoreCase(colTypes[i]) || "datetime".equalsIgnoreCase(colTypes[i])) {
				//是否允许为空
				if ("NO".equalsIgnoreCase(colIsNullAble[i])) {
					sb.append("\t\tif(map.containsKey(\"" + colnames[i] + "\")) {\r\n");
					sb.append("\t\t\tif (map.get(\"" + colnames[i] + "\")  == null) {\r\n");
					sb.append("\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_NOTNULL);\r\n");
					sb.append("\t\t\t}\r\n");
					sb.append("\t\t}\r\n");
				}
			}
			//验证体开始
			if ("varchar".equalsIgnoreCase(colTypes[i]) || "char".equalsIgnoreCase(colTypes[i])) {
				//是否允许为空
				if ("NO".equalsIgnoreCase(colIsNullAble[i])) {
					sb.append("\t\tif(map.containsKey(\"" + colnames[i] + "\")) {\r\n");
					sb.append("\t\t\tif (map.getTypedValue(\"" + colnames[i] + "\",String.class)  == null) {\r\n");
					sb.append("\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_NOTNULL);\r\n");
					sb.append("\t\t\t}");
					//长度校验
					sb.append("else{\r\n" +
							" \t\t\t\tif (LwxfStringUtils.getStringLength(map.getTypedValue(\"" + colnames[i] + "\",String.class)) > " + colSizes[i] + ") {\r\n");
					sb.append("\t\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);\r\n");
					sb.append("\t\t\t\t}\r\n");
					//特殊验证生成
					sb.append(generateSpecialValidator(colnames[i], false));
					sb.append("\t\t\t}\r\n");
					sb.append("\t\t}\r\n");

				} else {
					sb.append("\t\tif(map.containsKey(\"" + colnames[i] + "\")) {\r\n");
					//长度校验
					sb.append("\t\t\tif (LwxfStringUtils.getStringLength(map.getTypedValue(\"" + colnames[i] + "\",String.class)) > " + colSizes[i] + ") {\r\n");
					sb.append("\t\t\t\tvalidResult.put(\"" + colnames[i] + "\", ErrorCodes.VALIDATE_LENGTH_TOO_LONG);\r\n");
					sb.append("\t\t\t}\r\n");
					//特殊验证生成
					sb.append(generateSpecialValidator(colnames[i], false));
					sb.append("\t\t}\r\n");

				}
			}
		}
		sb.append("\t\tif(validResult.size()>0){\n")
				.append("\t\t\treturn ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);\n")
				.append("\t\t}else {\n")
				.append("\t\t\treturn null;\n")
				.append("\t\t}\r\n");
		sb.append("\t}\r\n");
	}

	/**
	 * 只有id为Long的才继承 IdEntity
	 * @return
	 */
	private String createExtendsIdEntity() {
		String r = "";
		for (int i = 0; i < colnames.length; i++) {
			//id不生成
			if ("id".equalsIgnoreCase(colnames[i]) && "String".equals(sqlTypeToJavaType(colTypes[i], colSizes[i]))) {
				r = " extends IdEntity ";
				break;
			}
		}
		return r;
	}

	private String getDisableUpdateColStr(String name) {
		if (this.disableUpdateCol != null) {
			if (disableUpdateCol.indexOf(name)>-1) {
				return "updatable = false,";
			}
		}
		return "";
	}

	/**
	 * 功能：生成所有属性
	 *
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			//id不生成
			if ("id".equalsIgnoreCase(colnames[i])) {
				continue;
			}
			sb.append("\t@Column(" + getJavaSqlType(colTypes[i]) + "," + getDbColLength(colTypes[i], colSizes[i]) + getDefaultValue(colDefaultValue[i]) + getNullAble(colIsNullAble[i]) + getDisableUpdateColStr(colnames[i]) + "name = \"" + dbColNames[i] + "\",displayName = \"" + colDescs[i] + "\")\r\n");
			//生成外键注解
			String fkString = (String) fkMap.get(colnames[i]);
			if (fkString != null) {
				sb.append("\t" + fkString + "\r\n");
			}
			sb.append("\tprivate " + sqlTypeToJavaType(colTypes[i], colSizes[i]) + " " + colnames[i] + ";\r\n");
		}

	}

	/**
	 * varchar设置长度
	 * @param sqlType
	 * @param length
	 * @return
	 */
	private String getDbColLength(String sqlType, int length) {
		String r = "";
		if ("varchar".equalsIgnoreCase(sqlType)) {
			r = "length = " + length + ",";
		}
		if ("char".equalsIgnoreCase(sqlType)) {
			r = "length = " + length + ",";
		} else if ("numeric".equalsIgnoreCase(sqlType)) {
			r = "precision = " + length + ",scale=2,";
		} else if ("decimal".equalsIgnoreCase(sqlType)) {
			r = "precision = " + length + ",scale=2,";
		}
		return r;
	}

	private String getDefaultValue(String s) {
		String r = "";
		if (s != null) {
			s = s.replaceAll("b'", "");
			s = s.replaceAll("'", "");
			r = "defaultValue = \"" + s + "\",";
		}
		return r;
	}

	private String getNullAble(String s) {
		String r = "";
		boolean b = true;
		if (s == null) {
			b = true;
		} else if ("NO".equalsIgnoreCase(s)) {
			b = false;
		} else if ("YES".equalsIgnoreCase(s)) {
			b = true;
		}
		if (!b) {
			r = "nullable = false,";
		}
		return r;
	}

	private String getJavaSqlType(String colType) {
		String r;
		if ("DATETIME".equalsIgnoreCase(colType)) {
			r = "type = TypesExtend.DATETIME";
		} else if ("DATE".equalsIgnoreCase(colType)) {
			r = "type = Types.DATE";
		} else if ("TINYINT UNSIGNED".equalsIgnoreCase(colType)) {
			r = "type = Types.INTEGER";
		} else if ("BIGINT UNSIGNED".equalsIgnoreCase(colType)) {
			r = "type = Types.BIGINT";
		} else if ("INT UNSIGNED".equalsIgnoreCase(colType)) {
			r = "type = Types.INTEGER";
		} else if ("INT".equalsIgnoreCase(colType)) {
			r = "type = Types.INTEGER";
		} else if ("LONGTEXT".equalsIgnoreCase(colType)) {
			r = "type = Types.LONGVARCHAR";
		} else if ("TEXT".equalsIgnoreCase(colType)) {
			r = "type = Types.CLOB";
		} else {
			r = "type = Types." + colType;
		}
		return r;
	}

	/**
	 * 功能：生成所有方法
	 *
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			if ("id".equalsIgnoreCase(colnames[i])) {
				continue;
			}

			sb.append("\r\n\tpublic void set" + initcap(colnames[i]) + "(" + sqlTypeToJavaType(colTypes[i], colSizes[i]) + " " +
					colnames[i] + "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\r\n\tpublic " + sqlTypeToJavaType(colTypes[i], colSizes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}

	}

	/**
	 * 功能：将输入字符串的首字母及下划线后的字母改成大写
	 *
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
		String[] arr = str.split("_");
		StringBuffer tempSb = new StringBuffer();
		if (arr.length > 0) {
			for (String st : arr) {
				char[] c = st.toCharArray();
				if (c[0] >= 'a' && c[0] <= 'z') {
					c[0] = (char) (c[0] - 32);
				}
				tempSb.append(new String(c));
			}
		}
		return tempSb.toString();
	}

	/**
	 * 功能：将输入字符串的下划线后的字母改成大写
	 *
	 * @param str
	 * @return
	 */
	private String initcapCol(String str) {
		if (str.startsWith("is_")) {
			str = str.replaceFirst("is_", "");
		}
		String[] arr = str.split("_");
		StringBuffer tempSb = new StringBuffer();
		if (arr.length > 1) {
			int i = 0;
			for (String st : arr) {
				if (i > 0) {
					char[] c = st.toCharArray();
					if (c[0] >= 'a' && c[0] <= 'z') {
						c[0] = (char) (c[0] - 32);
					}
					tempSb.append(new String(c));
				} else {
					tempSb.append(st);
				}
				i++;
			}
		} else {
			tempSb.append(str);
		}

		return tempSb.toString();
	}

	/**
	 * 功能：获得列的数据类型
	 *
	 * @param sqlType
	 * @return
	 */
	private String sqlTypeToJavaType(String sqlType, int typeSize) {

		if ("bit".equalsIgnoreCase(sqlType)) {
			return "Boolean";
		} else if ("tinyint".equalsIgnoreCase(sqlType)) {
			return "Byte";
		} else if ("TINYINT UNSIGNED".equalsIgnoreCase(sqlType)) {
			return "Byte";
		} else if ("smallint".equalsIgnoreCase(sqlType)) {
			return "Short";
		} else if ("INT".equalsIgnoreCase(sqlType)) {
			return "Integer";
		} else if ("int".equalsIgnoreCase(sqlType)) {
//			if(typeSize>=10){
//				return "Long";
//			}else{
			return "Integer";
//			}
		} else if ("INT UNSIGNED".equalsIgnoreCase(sqlType)) {
			return "Integer";
		} else if ("bigint".equalsIgnoreCase(sqlType)) {
			return "Long";
		} else if ("BIGINT UNSIGNED".equalsIgnoreCase(sqlType)) {
			return "Long";
		} else if ("float".equalsIgnoreCase(sqlType)) {
			return "Float";
		} else if ("real".equalsIgnoreCase(sqlType) || "money".equalsIgnoreCase(sqlType)
				|| "smallmoney".equalsIgnoreCase(sqlType) || "double".equalsIgnoreCase(sqlType)) {
			return "Double";
		} else if ("varchar".equalsIgnoreCase(sqlType) || "char".equalsIgnoreCase(sqlType)
				|| "nvarchar".equalsIgnoreCase(sqlType) || "nchar".equalsIgnoreCase(sqlType)
				|| "text".equalsIgnoreCase(sqlType)) {
			return "String";
		} else if ("datetime".equalsIgnoreCase(sqlType) || "date".equalsIgnoreCase(sqlType) || "timestamp".equalsIgnoreCase(sqlType)) {
			return "Date";
		} else if ("image".equalsIgnoreCase(sqlType)) {
			return "Blod";
		} else if ("longtext".equalsIgnoreCase(sqlType)) {
			return "String";
		} else if ("decimal".equalsIgnoreCase(sqlType)
				|| "numeric".equalsIgnoreCase(sqlType)) {
			return "BigDecimal";
		}
		return null;
	}
}