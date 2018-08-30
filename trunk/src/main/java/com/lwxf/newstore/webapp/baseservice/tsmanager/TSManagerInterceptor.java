package com.lwxf.newstore.webapp.baseservice.tsmanager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.SQLType;
import com.lwxf.newstore.webapp.common.utils.AnalysisSqlUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：拦截器
 * update:insert/update/delete
 * commit:commit
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:31:33
 * @version：2018 Version 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class TSManagerInterceptor extends TransactionSynchronizationAdapter implements Interceptor {
	private static final String THREAD_KEY = "TSManagerInterceptor";//存放操作信息key
	//事件管理上下文
	private static final TSManagerContext tsManagerContext = TSManagerContext.instance;
	private static Logger logger = LoggerFactory.getLogger(TSManagerInterceptor.class);
	//实体表名(大写)和对应class的映射关系
	private static Map<String,Class> tableMappings = new HashMap<String,Class>();//数据库表名和对应实体class映射表
	//mapper中方法对应的sql解析
	private ConcurrentHashMap<String, AnalysisSqlUtil.SQLInfo> sqlInfoCache = new ConcurrentHashMap<String, AnalysisSqlUtil.SQLInfo>();

	/**
	 * 实现拦截逻辑的地方，内部要通过invocation.proceed()显式地推进责任链前进，也就是调用下一个拦截器拦截目标方法
	 * @param invocation
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//更新操作,注册回调
		if(TransactionSynchronizationManager.isSynchronizationActive() && !TransactionSynchronizationManager.getSynchronizations().contains(this)){
			TransactionSynchronizationManager.registerSynchronization(this);
		}
		return onUpdate(invocation);
	}

	/**
	 * 更新事件
	 * @param invocation
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Object onUpdate(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
		TSManagerEntity tsManagerEntity = null;
		if(WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG) == null){
			//获取执行sql封装实体信息
			tsManagerEntity = getUpdateEntity(invocation);
		}

		SQLType sqlType = null;
		boolean isDoTsManagerContext = Boolean.TRUE.booleanValue();
		if(tsManagerEntity != null){
			Object params = tsManagerEntity.getMapperParams();
			if(params instanceof MapContext){
				isDoTsManagerContext = ((MapContext)params).isDoTSManagerContext();
			}
			sqlType = tsManagerEntity.getSqlType();
		}
		if(isDoTsManagerContext){
			tsManagerContext.beforeUpdate(tsManagerEntity, sqlType);
		}
		Object result = invocation.proceed();//执行操作

		//更新后
		Assert.isTrue(result instanceof Integer, "reslut must be Integer");
		if(tsManagerEntity != null){//判断非空
			int updateRows = (Integer) result;
			tsManagerEntity.setAffectedRows(updateRows);
			//线程数据，有更新结果放入线程中
			if(updateRows > 0 && isDoTsManagerContext){
				List list = (List) WebUtils.getDataFromRequestMap(TSManagerInterceptor.THREAD_KEY);
				if (list == null) {
					list = new ArrayList<TSManagerEntity>();
					WebUtils.putDataToRequestMap(TSManagerInterceptor.THREAD_KEY,list);
				}
				list.add(tsManagerEntity);
			}
		}
		if(isDoTsManagerContext) {
			tsManagerContext.afterUpdate(tsManagerEntity, sqlType);
		}

		return result;
	}

	/**
	 * 事务提交前，此方法只会调用一次
	 * @param readOnly
	 */
	@Override
	public void beforeCommit(boolean readOnly) {
		//封装参数,触发事件
		ArrayList<TSManagerEntity> list = (ArrayList) WebUtils.getDataFromRequestMap(TSManagerInterceptor.THREAD_KEY);
		if(list != null){
			tsManagerContext.beforeCommit((List)list.clone());
		}else {
			tsManagerContext.beforeCommit(null);
		}
	}

	/**
	 * 事务提交后的回调
	 */
	@Override
	public void afterCommit() {
		//封装参数,触发事件
		ArrayList<TSManagerEntity> list = (ArrayList) WebUtils.getDataFromRequestMap(TSManagerInterceptor.THREAD_KEY);
		if(list != null){
			tsManagerContext.afterCommit((List)list.clone());
		}else {
			tsManagerContext.afterCommit(null);
		}
	}

	/**
	 * 用当前这个拦截器生成对目标target的代理，实际是通过Plugin.wrap(target,this)来完成的，把目标target和拦截器this传给了包装函数
	 * @param target
	 * @return
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}


	/**
	 * 设置额外的参数，参数配置在拦截器的Properties节点里
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
		logger.debug("Load tableMapping.properties file ...");
		//当前类加载器
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream("tableMapping.properties");
		if(in == null){
			throw new RuntimeException("TableMapping.properties is not found");
		}

		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Set<Map.Entry<Object,Object>> entrys = prop.entrySet();//返回的属性键值对实体
		for(Map.Entry<Object, Object> entry : entrys){
			Class clazz = null;
			try {
				clazz = classLoader.loadClass(entry.getValue().toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			tableMappings.put(entry.getKey().toString(),clazz);
		}
	}


	/**
	 * 封装参数
	 *
	 * @param invocation
	 * @return
	 */
	private TSManagerEntity getUpdateEntity(Invocation invocation) {
		Object[] args = invocation.getArgs();
//		String event = invocation.getMethod().getName();

		MappedStatement mappedStatement = (MappedStatement) args[0];
		BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
		String preparedSql = boundSql.getSql();//预编译SQL
		String mappings = mappedStatement.getId();//调用mapper的包名.类名.方法名
		//从缓存中获取解析结果
		AnalysisSqlUtil.SQLInfo sqlInfo =  getSQLInfo(mappings, preparedSql);
		String tbName = sqlInfo.getCurrentTable();//操作表名

		//update delete insert操作必须传入参数
		if(args[1] == null){
			logger.error("该操作{}没有参数条件", mappings);
			throw new IllegalArgumentException("该操作必须有参数");
		}

		TSManagerEntity tsManagerEntity = new TSManagerEntity();
		tsManagerEntity.setSqlType(sqlInfo.getSqlType());
		tsManagerEntity.setCurrentTable(tbName);
		tsManagerEntity.setClazz(tableMappings.get(tbName));//操作的class
		tsManagerEntity.setMappings(mappings);
		tsManagerEntity.setMapperParams(args[1]);
		tsManagerEntity.setSqlInfo(sqlInfo);

		return tsManagerEntity;
	}

	/**
	 * 获取解析sql结果
	 * @param id	key(执行方法的包类.类名.方法名)
	 * @param sql	sql(预编译sql)
	 * @return
	 */
	private AnalysisSqlUtil.SQLInfo getSQLInfo(String id, String sql){
		AnalysisSqlUtil.SQLInfo sqlInfo = sqlInfoCache.get(id);//从缓存中查找
		if(sqlInfo == null){
			synchronized (this){ // TODO：同步锁需要Redis实现
				if(sqlInfo == null){
					sqlInfo = AnalysisSqlUtil.getSqlType(sql);//解析sql
					sqlInfoCache.put(id, sqlInfo);
				}
			}
			sqlInfo = sqlInfoCache.get(id);
		}
		if(logger.isDebugEnabled()){
			Map<String, SQLType> map = sqlInfo.getTableNames();
			if(map != null && map.size() > 1){
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, SQLType> me : map.entrySet()){
					sb.append("表名 : " + me.getKey());
					sb.append("\n");
					sb.append("操作类型：" + me.getValue());
					sb.append("\n");
				}
				logger.debug("此处拦截到多表操作，请检查埋点:\n{}", sb.toString());
			}
		}
		return sqlInfo;
	}
}
