package com.lwxf.newstore.webapp.common.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.Assert;

import org.apache.commons.lang3.ArrayUtils;

import com.lwxf.commons.exception.AssertIllegalArgumentException;


/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:22
 * @Copyright：2018 Version：1.0
 * @Company：老屋新房 Created with IntelliJ IDEA
 */
public class BucketService {

	Set<Bucket> buckets;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// 站点图片
	/**
	 * /api/(user|project|team)/(\d+)/pics/upload/(help|blog)
	 * /api/blog/blogImageUpload
	 */
	// 防盗链图片

	/**
	 * /api/(user|project|team)/(\d+)/imageUpload
	 * /icon/upload
	 * /api/(user|project|team)/(\d+)/doc/upload
	 * /api/team/(\d+)/project/(\d+)/icon_upload
	 * /api/user/(\d+)/upload_avatar
	 * /api/backlog/(\d+)/files
	 */
	public void setCfgs(List<String> cfgs) {
		this.buckets = new HashSet<>();
		Assert.notEmpty(cfgs, "还没配置七牛的bucket(easypm.properties的qn_bucket)");
		for (String cfg : cfgs) {
			String[] bucketArr = cfg.split("=");
			if (ArrayUtils.isEmpty(bucketArr) || bucketArr.length != 3) {
				this.logger.error("还没配置七牛的bucket配置有问题：cfg = {}",cfg);
				continue;
			}
			buckets.add(new Bucket(bucketArr[0], bucketArr[1], bucketArr[2]));
		}
	}

	public Bucket getBucket(String requestUrl) {
		for (Bucket bucket : this.buckets) {
			if (bucket.matcher(requestUrl)) {
				return bucket;
			}
		}
		logger.error("非法的上传文件api路径，请检查请求路径是否配置正确 : " + requestUrl);
		throw new AssertIllegalArgumentException("非法的上传文件api路径，请检查请求路径是否配置正确");
	}

}
