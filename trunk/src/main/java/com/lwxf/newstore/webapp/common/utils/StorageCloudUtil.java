package com.lwxf.newstore.webapp.common.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.lwxf.commons.exception.UploadFileException;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

/**
 * 功能：
 *
 * @User：sunchangji()
 * @create：2018-05-24 11:40
 * @Copyright：2018 Version：1.0
 * @Company：老屋新房 Created with IntelliJ IDEA
 */
public class StorageCloudUtil {
	private static final Logger logger = LoggerFactory.getLogger(StorageCloudUtil.class);
	private static final String c_bucket_default = "default";
	public static Bucket defaultBucket;
	private static com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.autoZone());
	private static UploadManager uploadManager = new UploadManager(cfg);
	private static BucketService bucketService;
	private static String ACCESS_KEY;
	private static String SECRET_KEY;

	//用于静态注入，禁止删除此函数
	public static void setKeyInfo(String info) {
		String[] arr = info.split("=");
		ACCESS_KEY = arr[0];
		SECRET_KEY = arr[1];
	}

	public static void setBucketService(BucketService service) {
		bucketService = service;
		defaultBucket = bucketService.getBucket(c_bucket_default);
	}

	private static String getUploadToken(Bucket bucket) {
		Assert.notNull(bucket);
		Auth auth = getAuth();
		return auth.uploadToken(bucket.getBucketName());
	}

	private static Auth getAuth() {
		return Auth.create(ACCESS_KEY, SECRET_KEY);
	}

	private static BucketManager getBucketManager() {
		return new BucketManager(getAuth(), cfg);
	}

	public static String uploadFile(String key, String localFile) {
		return uploadFile(key, localFile, defaultBucket);
	}

	public static String uploadFile(String key, File localFile) {
		return uploadFile(key, localFile, defaultBucket);
	}

	public static String uploadFile(String key, File localFile, String bucketKey) {
		Bucket bucket = bucketService.getBucket(bucketKey);
		return uploadFile(key, localFile, bucket == null ? defaultBucket : bucket);
	}

	public static String uploadStream(String key, InputStream input) {
		return uploadStream(key, input, defaultBucket);
	}

	public static String uploadFile(String key, String localFile, Bucket bucket) {
		return uploadFile(key, new File(localFile), bucket);
	}

	public static String uploadFile(String key, File localFile, Bucket bucket) {
		Assert.notNull(bucket);
		Response response = null;
		try {
			response = uploadManager.put(localFile, key, getUploadToken(bucket));
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			logger.info(putRet.key);
			logger.info(putRet.hash);
		} catch (QiniuException e) {
			Response r = e.response;
			logger.error(r.toString());
			throw new UploadFileException(e);
		}
		return bucket.getBucketDomain() + key;
	}

	public static String uploadStream(String key, InputStream input, Bucket bucket) {
		Assert.notNull(bucket);
		try {
			Response response = uploadManager.put(input, key, getUploadToken(bucket), null, null);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			logger.info(putRet.key);
			logger.info(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.error(r.toString());
			throw new UploadFileException(ex);
		}
		return bucket.getBucketDomain() + key;
	}

	public static void delete(String key) {
		try {
			getBucketManager().delete(defaultBucket.getBucketName(), key);
		} catch (QiniuException e) {
			throw new UploadFileException(e);
		}
	}

	public static void delete(String url, String bucketKey) {
		try {
			Bucket bucket = bucketService.getBucket(bucketKey);
			getBucketManager().delete(bucket.getBucketName(), url.replace(bucket.getBucketDomain(), ""));
		} catch (QiniuException e) {
			throw new UploadFileException(e);
		}
	}

	public static boolean exists(String key) {
		try {
			FileInfo fileInfo = getBucketManager().stat(defaultBucket.getBucketName(), key);
			logger.info(fileInfo.hash);
			logger.info(String.valueOf(fileInfo.fsize));
			logger.info(fileInfo.mimeType);
			logger.info(String.valueOf(fileInfo.putTime));
			if (fileInfo.hash == null) {
				return false;
			}
		} catch (QiniuException ex) {
			logger.error(ex.response.toString());
			throw new UploadFileException(ex);
		}
		return true;
	}

	public static String removeFile(String sourceKey, String destKey) {
//		if (!exists(sourceKey)) {
//			return AppBeanInjector.configuration.getProjectDefaultAvatar();
//		}
		try {
			String bucketName = defaultBucket.getBucketName();
			getBucketManager().move(bucketName, sourceKey, bucketName, destKey);
		} catch (QiniuException e) {
			throw new UploadFileException(e);
		}
		return StorageCloudUtil.defaultBucket.getBucketDomain() + destKey;
	}

	//七牛复制文件
	public static String copyFile(String sourceKey, String destKey) {
		if (!exists(sourceKey)) {
			return null;
		}
		String bucketName = defaultBucket.getBucketName();
		try {
			getBucketManager().copy(bucketName, sourceKey, bucketName, destKey);
		} catch (QiniuException e) {
			throw new UploadFileException(e);
		}
		return StorageCloudUtil.defaultBucket.getBucketDomain() + destKey;
	}

	//七牛移动文件
	public static String moveFile(String sourceKey, String destKey) {
		if (!exists(sourceKey)) {
			return null;
		}
		String bucketName = defaultBucket.getBucketName();
		try {
			getBucketManager().move(bucketName, sourceKey, bucketName, destKey);
		} catch (QiniuException e) {
			throw new UploadFileException(e);
		}
		return StorageCloudUtil.defaultBucket.getBucketDomain() + destKey;
	}

	public static boolean deleteFileByDirOnAllBuckets(String dir) {
		try {
			for (Bucket bucket : bucketService.buckets) {
				deleteFileByDir(dir, bucket);
			}
		} catch (Exception e) {
			logger.error("删除文件异常", e);
			return false;
		}
		return true;
	}

	public static boolean deleteFileByDir(String dir, Bucket bucket) throws QiniuException {
		BucketManager.FileListIterator fileListIterator = getBucketManager().createFileListIterator(bucket.getBucketName(), dir, 1000, null);
		List<String> keyList = new ArrayList<>();
		BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		while (fileListIterator.hasNext()) {
			FileInfo[] items = fileListIterator.next();
			if (items.length == 0) {
				continue;
			}
			for (FileInfo item : items) {
				keyList.add(item.key);
			}
			batchOperations.addDeleteOp(bucket.getBucketName(), keyList.toArray(new String[keyList.size()]));
			getBucketManager().batch(batchOperations);
		}
		return true;
	}
}
