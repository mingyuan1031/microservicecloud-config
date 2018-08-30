package com.lwxf.newstore.webapp.common.component;

import java.io.File;
import java.io.IOException;

import javax.annotation.CheckReturnValue;
import javax.annotation.meta.When;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.UploadFileException;
import com.lwxf.commons.utils.file.FileUtil;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.result.FileMimeType;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.StorageCloudUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 19:11:45
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@CheckReturnValue(when = When.NEVER)
public class FileUploadToQiNiuComponent extends BaseFileUploadComponent {
	private Logger logger = LoggerFactory.getLogger(FileUploadToQiNiuComponent.class);

	public FileUploadToQiNiuComponent() {
	}

	@Override
	protected UploadInfo executeUpload(UploadType uploadType, String dir, MultipartFile multipartFile) {
		return upload(dir, multipartFile);
	}

	@Override
	public int executeDeleteFile(String path, UploadResourceType uploadResourceType) {
		try {
			StorageCloudUtil.delete(path, uploadResourceType.getModule());
			return 1;
		} catch (Exception e) {
			logger.error("删除文件失败", e);
			return 0;
		}
	}

	@Override
	public boolean deleteFileByDir(String dir) {
		if (dir.startsWith("/")) {
			dir = dir.substring(1);
		}
		return StorageCloudUtil.deleteFileByDirOnAllBuckets(dir);
	}

	@Override
	public String transfer(String src, String dist) {
		String resultPath;
		try {
			File file = new File(super.tempRootPath + src);
			dist = dist.startsWith("/") ? dist.substring(1) : dist;
			resultPath = StorageCloudUtil.uploadFile(dist, file);
			FileUtils.forceDelete(file);
			return resultPath;
		} catch (Exception e) {
			throw new UploadFileException(e);
		}
	}

	private UploadInfo upload(String dir, MultipartFile multipartFile) {
		String baseDir = super.tempRootPath + dir;
		FileUtil.createDir(baseDir);
		FileName fileName = buildFileName(multipartFile);
		String tempPath = baseDir + fileName.getFullName();
		String serverPath = (dir.startsWith("/") ? dir.substring(1) : dir) + fileName.getFullName();
		FileMimeType fileMimeType = FileMimeTypeUtil.doFileMimeType(multipartFile);
		String resultPath;
		try {
			File file = new File(tempPath);
			multipartFile.transferTo(file);
			resultPath = StorageCloudUtil.uploadFile(serverPath, file, super.uploadResourceTypeThreadLocal.get().getModule());
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
		return new UploadInfo(fileMimeType, resultPath, resultPath, fileName.getName(), fileName.getFullName());

	}
}
