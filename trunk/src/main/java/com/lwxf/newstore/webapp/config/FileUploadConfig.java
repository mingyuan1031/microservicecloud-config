package com.lwxf.newstore.webapp.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.component.FileUploadToDiskComponent;
import com.lwxf.newstore.webapp.common.component.FileUploadToQiNiuComponent;
import com.lwxf.newstore.webapp.common.component.IdFileNameGenerator;
import com.lwxf.newstore.webapp.common.utils.BucketService;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.StorageCloudUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:09:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class FileUploadConfig {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadConfig.class);
	@Value("${qn}")
	private String keyInfo;
	@Value("${qn_bucket_high_frequency}")
	private String QN_BUCKET_HIGH_FREQUENCY;
	@Value("${qn_bucket_low_frequency}")
	private String QN_BUCKET_LOW_FREQUENCY;

	@ConfigurationProperties("upload.file.upload.component")
	@Bean(name = "baseFileUploadComponent")
	public BaseFileUploadComponent fileUploadComponent(IdFileNameGenerator idFileNameGenerator, com.lwxf.newstore.webapp.common.utils.Configuration configuration) {
		BaseFileUploadComponent baseFileUploadComponent;
		baseFileUploadComponent = new FileUploadToDiskComponent();
		baseFileUploadComponent.setFileNameGenerator(idFileNameGenerator);
		return baseFileUploadComponent;
	}
	/*@Bean(name = "bucketService")
	public BucketService bucketService() {
		BucketService bucketService = new BucketService();
		bucketService.setCfgs(Arrays.asList(QN_BUCKET_HIGH_FREQUENCY, QN_BUCKET_LOW_FREQUENCY));
		StorageCloudUtil.setBucketService(bucketService);
		StorageCloudUtil.setKeyInfo(keyInfo);
		return bucketService;
	}*/
}
