package com.lwxf.newstore.webapp.baseservice.tsmanager.util;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.mybatis.annotation.Table;
import com.lwxf.mybatis.utils.OSUtil;

/**
 * 功能：
 * 扫描指定的包，根据@Table注解的实体类和表名
 * <p>
 * AuditPackageScan packageScan = new AuditPackageScan("com.lwxf.newstore.webapp.domain.entity", IdEntity.class);
 * packageScan.execute();
 * packageScan.getTableMappings();//映射结果
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 14:01:51
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class AuditPackageScan {
	private static Logger logger = LoggerFactory.getLogger(AuditPackageScan.class);

	private String packageName;//要扫描的包名
	private Class<? extends Annotation> annotation = Table.class;//类上带的注解
	private boolean recursive = true;//默认递归
	private Map<String, Class> tableMappings = new HashMap<String, Class>();//所有表名和对应实体class

	public AuditPackageScan(String packageName) {
		this.packageName = packageName;
	}

	public static void main(String[] args){
		generateTableMapping();
	}

	/**
	 * 生成数据库表名和对应实体class映射表
	 */
	public static void generateTableMapping() {
		String packageName = "com.lwxf.newstore.webapp.domain.entity";
		AuditPackageScan auditPackageScan = new AuditPackageScan(packageName);
		auditPackageScan.execute();//扫描实体类包
		Map<String, Class> map = auditPackageScan.getTableMappings();//设置参数

		Properties properties = new Properties();
		for (Map.Entry<String, Class> me : map.entrySet()) {
			properties.setProperty(me.getKey(), me.getValue().getName());
		}

		//文件生成在桌面
		String outputDir = OSUtil.getUserHome() + OSUtil.getFileSeparator() + "Desktop" + OSUtil.getFileSeparator();
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(new File(outputDir + "tableMapping.properties"));
			properties.store(oFile, "数据库表名和对应实体class映射表");
			oFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oFile != null) {
				try {
					oFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("create success");
	}

	//获取结果
	public Map getTableMappings() {
		return this.tableMappings;
	}

	/**
	 * 执行操作
	 */
	public void execute() {
		if (packageName == null) {
			logger.error("packageName is null");
			return;
		}
		getPackageAnnotation();
	}

	/**
	 * 获取当前包路径下指定的注解类型的文件
	 *
	 * @return 文件
	 */
	private void getPackageAnnotation() {
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs = null;

		//获取当前目录下面的所有的子目录的url
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (dirs != null) {
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				//得到url的类型
				String protocol = url.getProtocol();

				//如果当前类型是文件类型
				if ("file".equals(protocol)) {
					//获取包的物理路径
					String filePath = null;
					try {
						filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage());
					}
					if (filePath != null && (filePath.length() > 0)) {
						//dangdong 2017年6月20日处理filePath可能为null的情况
						filePath = filePath.substring(1);
						getFilePathClasses(packageName, filePath);
					}
				}
			}
		}
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 *
	 * @param packageName 包名
	 * @param packagePath 路径
	 */
	private void getFilePathClasses(String packageName, String packagePath) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			logger.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			@Override
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		if (dirfiles != null && dirfiles.length > 0) {
			// 循环所有文件
			for (File file : dirfiles) {
				// 如果是目录 则继续扫描
				if (file.isDirectory()) {
					getFilePathClasses(packageName + "."
							+ file.getName(), file.getAbsolutePath());
				} else {
					// 如果是java类文件 去掉后面的.class 只留下类名
					String className = file.getName().substring(0,
							file.getName().length() - 6);

					Class<?> clazz = null;
					try {
						clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
					} catch (ClassNotFoundException e) {
						// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
						logger.error(e.getMessage());
					}

					if (null != clazz && null != clazz.getAnnotation(annotation)) {
						Table table = (Table) clazz.getAnnotation(Table.class);
						tableMappings.put(table.name().toUpperCase(), clazz);
					}
				}
			}
		}
	}
}
