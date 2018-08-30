package com.lwxf.newstore.webapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.lwxf.newstore.webapp.domain.entity.advertising.Advertising;
import org.junit.Test;

import com.lwxf.mybatis.tool.MySqlTool;
import com.lwxf.mybatis.tool.MySqlToolParams;
import com.lwxf.newstore.webapp.domain.entity.cart.Cart;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;
import com.lwxf.newstore.webapp.domain.entity.demo.Demo;
import com.lwxf.newstore.webapp.domain.entity.goods.*;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.domain.entity.order.*;
import com.lwxf.newstore.webapp.domain.entity.config.SystemConfig;
import com.lwxf.newstore.webapp.domain.entity.sys.SystemActivity;
import com.lwxf.newstore.webapp.domain.entity.sys.UserNotify;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;


/**
 * 功能：根据数据库自动生成实体和mapper
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-26 10:07:46
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class DaoMapper {
    @Test
    public void test_createOrAlterDb() {
        MySqlTool.initDbObjects();
    }

	/**
	 * 自动生成User及User相关的实体和mapper
	 * @throws IOException
	 */
	@Test
    public void test_batchGenerateDemoDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
        // 生成的java和xml文件的输出目录（默认为桌面）
        String outputDir = "E:\\gencode\\newstore";
        //生成的包前缀
        String packageName  = "demo";
        // 是否使用带分页的 selectByFilter
        boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("renzhongshan(d3shan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
        List<Class> entityList = Arrays.asList(Demo.class);
        if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
    }
	/**
	 * wangmingyuan
	 * 自动生成microblog及microblog相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateMicroblogDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String microblogPackageName  = "quickshare";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("wangmingyuan(wangmingyuan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(Microblog.class,MicroblogComment.class,MicroblogPraise.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, microblogPackageName,params);
			}
		}

	}
	/**
	 * wangmingyuan
	 * 自动生成Order及Oder相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateOrderDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String orderPackageName  = "order";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("wangmingyuan(wangmingyuan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(PaidRecords.class,Address.class,CityArea.class,Logistics.class,Order.class,OrderGoods.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, orderPackageName,params);
			}
		}

	}

	/**
	 * 自动生成User及User相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateCommonDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName  = "common";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("renzhongshan(d3shan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(UploadFiles.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}
	/**
	 * 自动生成Gooods及Gooods相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateGoodsDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName  = "goods";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("F_baisi(F_baisi@163.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList();
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}

	/**
	 * 自动生成Video及Video相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateVideoDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName  = "video";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("F_baisi(F_baisi@163.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(VideoFile.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}

	/**
	 * 自动生成Company及Company相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGeneratecompanyDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "company";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("zhangjiale(zjl869319827@outlook.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(Company.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}


	/**
	 * 自动生成cart及Cart相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGeneratecartDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "cart";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("zhangjiale(zjl869319827@outlook.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(Cart.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}


	/**
	 * 自动生成系统活动日志及用户通知相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateSysDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "sys";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("renzhongshan(d3shan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
//		List<Class> entityList = Arrays.asList(SystemActivity.class,UserNotify.class);
//		if (entityList.size() > 0) {
//			for (Class c : entityList) {
//				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
//			}
//		}
	}

	/**
	 * 自动生成Company及Company相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGeneratesystemHomeNavDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "storehomenav";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("zhangjiale(zjl869319827@outlook.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(StoreHomeNav.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}

	/**
	 * 自动生成Company及Company相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateconfigDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "config";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("zhangjiale(zjl869319827@outlook.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(StoreConfig.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}
	/**
	 * 自动生成adv及adv相关的实体和mapper
	 * @throws IOException
	 */
	@Test
	public void test_batchGenerateAdvDaoClassAndServiceClassAndDaoXmlFiles() throws IOException {
		// 生成的java和xml文件的输出目录（默认为桌面）
		String outputDir = "E:\\gencode\\newstore";
		//生成的包前缀
		String packageName = "advertising";
		// 是否使用带分页的 selectByFilter
		boolean usePagedFilter = true;
		MySqlToolParams params = new MySqlToolParams("panchenxiao(Mister_pan@126.com)","2018 V1.0","老屋新房","com.lwxf.newstore");
		List<Class> entityList = Arrays.asList(Advertising.class);
		if (entityList.size() > 0) {
			for (Class c : entityList) {
				MySqlTool.generateDaoClassAndServiceClassAndXmlFiles(c, usePagedFilter, outputDir, packageName,params);
			}
		}
	}

}
