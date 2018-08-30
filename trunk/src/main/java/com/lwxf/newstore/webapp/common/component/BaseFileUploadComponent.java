package com.lwxf.newstore.webapp.common.component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.newstore.webapp.common.enums.UploadResourceType;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:44:36
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class BaseFileUploadComponent {
	private static final String PREFIX = "";
	protected String rootPath = "";
	protected String tempRootPath = "";
	/**
	 * 记录当前上传的附件属于哪个模块
	 */
	protected ThreadLocal<UploadResourceType> uploadResourceTypeThreadLocal = new ThreadLocal<>();
	private Map<String, String> modulePathMapping = new HashMap<>();
	private FileNameGenerator fileNameGenerator;

	protected FileName buildFileName(MultipartFile file) {
		String name = fileNameGenerator.generate();
		String suffix = getSuffix(file);
		return new FileName(name, suffix, name + suffix);
	}

	private String getSuffix(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (!StringUtils.isEmpty(file.getOriginalFilename()) && file.getOriginalFilename().lastIndexOf(".") != -1) {
			int index = file.getOriginalFilename().lastIndexOf(".");
			return fileName.substring(index);
		}
		String contentType = file.getContentType();
		String[] mime = contentType.split("/");
		if (mime.length == 2) {
			return "." + mime[1];
		} else {
			return "";
		}
	}

	private String parseDir(UploadResourceType uploadResourceType, String... args) {
		Assert.notNull(uploadResourceType, "uploadResourceType不能为空");
		uploadResourceTypeThreadLocal.set(uploadResourceType);
		String path = modulePathMapping.get(uploadResourceType.getModule());
		Assert.notNull(path, "此模块没有配置路径");
		if (args.length == 0) {
			return path;
		}
		return MessageFormat.format(path, args);
	}

	public UploadInfo doUploadByModule(UploadType uploadType, MultipartFile multipartFile, UploadResourceType uploadResourceType, String... args) {
		String dir = parseDir(uploadResourceType, args);
		UploadInfo uploadInfo = doUpload(uploadType, dir, multipartFile);
		uploadResourceTypeThreadLocal.remove();
		return uploadInfo;
	}

	public UploadInfo doUploadByModule(MultipartFile multipartFile, UploadResourceType uploadResourceType, String... args) {
		return doUploadByModule(UploadType.FORMAL, multipartFile, uploadResourceType, args);
	}

	protected UploadInfo doUpload(UploadType uploadType, String dir, MultipartFile multipartFile){
		UploadInfo uploadInfo = this.executeUpload(uploadType, dir, multipartFile);
		return uploadInfo;
	}

	protected abstract UploadInfo executeUpload(UploadType uploadType, String dir, MultipartFile multipartFile);


	public int deleteFile(String path, UploadResourceType uploadResourceType,long fileSize){
		int i = this.executeDeleteFile(path,uploadResourceType);
		return i;
	}

	/**
	 * 批量删除文件
	 * @param files
	 * @param totalSize
	 * @return
	 */
	public int deleteFiles(Map<String,UploadResourceType> files,long totalSize){
		int i = 0;
		for(Map.Entry<String,UploadResourceType> entry:files.entrySet()){
			i+=this.executeDeleteFile(entry.getKey(),entry.getValue());
		}
		return i;
	}

	protected abstract int executeDeleteFile(String path, UploadResourceType uploadResourceType);

	/**
	 * 仅仅删除组织时调
	 * @param uploadResourceType
	 * @param args
	 * @return
	 */
	public boolean deleteFileByModule(UploadResourceType uploadResourceType, String... args) {
		String dir = parseDir(uploadResourceType, args);
		boolean result = deleteFileByDir(dir);
		uploadResourceTypeThreadLocal.remove();
		return result;
	}

	public abstract boolean deleteFileByDir(String dir);

	public abstract String transfer(String src, String dist);

	public Map<String, String> getModulePathMapping() {
		return modulePathMapping;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getTempRootPath() {
		return tempRootPath;
	}

	public void setTempRootPath(String tempRootPath) {
		this.tempRootPath = tempRootPath;
	}

	public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}
}
