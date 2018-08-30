package com.lwxf.newstore.webapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lwxf.newstore.webapp.common.utils.GenEntity;

/**
 * 功能：根据配置，数据库表生成java实体
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-26 10:07:46
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GenEntityTest {
	public static void main(String[] args) {
		//默认生成java文件路径
		String appointPath = "E:\\gencode\\newstore";
		//不允许修改的字段配置表　key 数据库表 value数据库字段列名
		Map<String, String> mapDisabledUpdate = new HashMap<>();
		mapDisabledUpdate.put("demo", "created");
		mapDisabledUpdate.put("brand", "id");
		mapDisabledUpdate.put("goods_comment","id,memberId,created");
		mapDisabledUpdate.put("goods","id,created,creator");
		mapDisabledUpdate.put("goods_extend","id");
		mapDisabledUpdate.put("goods_tag","id,goods,tag");
		mapDisabledUpdate.put("tag","id");
		mapDisabledUpdate.put("good_type","id");
		mapDisabledUpdate.put("spec_option","id");
		mapDisabledUpdate.put("goods_spec","id");
		mapDisabledUpdate.put("order_goods", "id");
		mapDisabledUpdate.put("goods_show","id");
		mapDisabledUpdate.put("reservation", "id,created");
		mapDisabledUpdate.put("company", "id");
		mapDisabledUpdate.put("cart", "id,created");
		mapDisabledUpdate.put("system_home_nav", "id");
		mapDisabledUpdate.put("system_config", "id");
		mapDisabledUpdate.put("store_config", "id");
		mapDisabledUpdate.put("video_file","id,created,creator");
		mapDisabledUpdate.put("video_type","id,created,creator");
		mapDisabledUpdate.put("advertising", "id");
		// 公共类的表处理
		mapDisabledUpdate.put("upload_files", "path,name,mime,originalMime,creator,created");
		// 活动日志
		mapDisabledUpdate.put("system_activity", "id,company_id,event,created,creator,r1,r2,r3,scope");
		// 用户通知
		mapDisabledUpdate.put("user_notify", "id,company_id,system_activity_id,user_id,grouping,created");

		/************************ demo ********************/
		//包
		String demoPackageName = "demo";
		//表名集合
		List<String> demoTableNameList = Arrays.asList("demo");
		demoTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, demoPackageName, appointPath, mapDisabledUpdate,"wangmingyuan(mingyuan1031@163.com)")
		);


		/************************ goods ********************/
		//包
		String goodsPackageName = "goods";
		//表名集合
		List<String> goodsTableNameList = Arrays.asList("video_type","video_file");
		goodsTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, goodsPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);

		/************************ goods ********************/
		//包
		String videoPackageName = "video";
		//表名集合
		List<String> videoTableNameList = Arrays.asList("video_file");
		videoTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, videoPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);

		/************************ demo ********************/
		//包
//		String orderPackageName = "order";
//		//表名集合
//		List<String> orderTableNameList = Arrays.asList("order_goods");
//		orderTableNameList.stream().forEach(
//				tableName -> new GenEntity(tableName, orderPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
//		);
		/************************ order wangmingyuan ********************/
		//订单管理 wangmingyuan
		mapDisabledUpdate.put("address", "member_id，created");
//		mapDisabledUpdate.put("city_area", "id");
		mapDisabledUpdate.put("logistics", "deliver_time，receipt_time，created");
		mapDisabledUpdate.put("order", "created");
		mapDisabledUpdate.put("paid_records","member_id,paid_num,paid_price,paid_time,type,created");
//		mapDisabledUpdate.put("order_goods", "id");

		//包
		String orderPackageName = "order";
		//表名集合
		List<String> orderTableNameList = Arrays.asList("paid_records","address","city_area","logistics","order","order_goods");
		orderTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, orderPackageName, appointPath, mapDisabledUpdate,"wangmingyuan(mingyuan1031@163.com)")
		);
		/************************ microblog wangmingyuan ********************/
		mapDisabledUpdate.put("microblog","created,creator");
		mapDisabledUpdate.put("microblog_comment","created,creator");
		mapDisabledUpdate.put("microblog_praise","created,member_id");
		//包
		String microblogPackageName = "quickshare";
		//表名集合
		List<String> microblogTableNameList = Arrays.asList("microblog","microblog_comment","microblog_praise");
		microblogTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, microblogPackageName, appointPath, mapDisabledUpdate,"wangmingyuan(mingyuan1031@163.com)")
		);

		/************************ 公共的数据表common ********************/
		//包
		String commonPackageName = "common";
		//表名集合
		Arrays.asList("upload_files").stream().forEach(
				tableName -> new GenEntity(tableName, commonPackageName, appointPath, mapDisabledUpdate,"renzhongshan(d3shan@126.com)")
		);

		/************************ storeconfig ********************/
		//包
		String storeConfigPackageName = "store_config";
		//表名集合
		List<String> storeConfigTableNameList = Arrays.asList("store_config");
		storeConfigTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, storeConfigPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);

		/************************ company ********************/
		//包
		String companyPackageName = "company";
		//表名集合
		List<String> companyTableNameList = Arrays.asList("company");
		companyTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, companyPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);

		/************************ company ********************/
		//包
		String cartPackageName = "cart";
		//表名集合
		List<String> cartTableNameList = Arrays.asList("cart");
		cartTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, cartPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);

		/************************ 活动日志和用户通知 ********************/
		//包
		String sysPackageName = "sys";
		//表名集合
		List<String> sysTableNameList = Arrays.asList("system_activity","user_notify");
		sysTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, sysPackageName, appointPath, mapDisabledUpdate,"renzhongshan(d3shan@126.com)")
		);



/************************ system_home_nav ********************/
		//包
		String navPackageName = "storehomenav";
		//表名集合
		List<String> navTableNameList = Arrays.asList("store_home_nav");
		navTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, navPackageName, appointPath, mapDisabledUpdate,"F_baisi(F_baisi@163.com)")
		);



		/************************ store_config ********************/
		//包
		String configPackageName = "config";
		//表名集合
		List<String> configTableNameList = Arrays.asList("system_config","store_config");
		configTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, configPackageName, appointPath, mapDisabledUpdate,"zhangjiale(zjl869319827@outlook.com)")
		);




		/************************ advertising********************/
		//包
		String advPackageName = "Advertising";
		//表名集合
		List<String> advTableNameList = Arrays.asList("advertising");
		advTableNameList.stream().forEach(
				tableName -> new GenEntity(tableName, advPackageName, appointPath, mapDisabledUpdate,"panchenxiao(Mister_pan@126.com)")
		);

	}
}
