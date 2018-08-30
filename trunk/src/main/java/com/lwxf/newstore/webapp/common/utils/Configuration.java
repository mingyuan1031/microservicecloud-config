package com.lwxf.newstore.webapp.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 功能：系统配置信息
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:54:32
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class Configuration {
	@Value("${spring.profiles.active}")
	private String profile;
	@Value("${lwxf.base.domain.url:#{null}}")
	private String domainUrl;
	@Value("${lwxf.user.default.avatar:https://cdn.easypm.cn/sys/man.png}")
	private String userDefaultAvatar;

	@Value("${lwxf.company.id}")
	private String companyId;
//	@Value("${server.session.timeout:1800}")
//	private int sessionTimeOut;
	@Value("${ch.sys.task.copy.limit: 500}")
	private Integer taskCopyLimit;

	@Value("${lwxf.shiro.permission.cache.expire:4}")
	private int shiroPermissionCacheExpire;

	/**
	 * 文件上传的跟路径
	 */
//	@Value("${upload.path:/resources/uploads}")
//	private String uploadPath;

	/**
	 * 临时文件的存放跟路径
	 */
	@Value("${upload.temp:/resources/temp}")
	private String uploadTempPath;

	/**
	 * 上传头像的根目录
	 */
	@Value("${upload.avatar.root.dir:/resources/uploads/avatar}")
	private String uploadAvatarRootDir;
	/**
	 * 上传文件的根目录
	 */
	@Value("${upload.file.upload.component.rootPath:/resources/uploads}")
	private String uploadFileRootDir;
	/**
	 * 上传头像的文件范围
	 */
	@Value("${upload.avatar.type:image/png,image/gif,image/jpg,image/jpeg,image/pjpeg,image/x-ms-bmp,image/bmp}")
	private String uploadAvatarRange;
	/**
	 * 上传图片的文件范围
	 */
	@Value("${upload.img.mime.range:image/png,image/gif,image/jpg,image/jpeg,image/pjpeg,image/x-ms-bmp,image/bmp}")
	private String uploadImgMimeRange;
	/**
	 * 上传视频的文件范围
	 */
	@Value("${upload.video.mime.range:video/mp4,video/x-msvideo,video/3gpp}")
	private String uploadVideoMimeRange;
	/**
	 * 上传网页的文件范围
	 */
	@Value("${upload.html.mime.range:text/html,application/octet-stream}")
	private String uploadHtmlMimeRange;
	/**
	 * 上传图片的文件范围
	 */
	@Value("${upload.file.mime.range:all}")
	private String uploadFileMimeRange;

	/**
	 * 上传文件的限制大小，单位M
	 */
	@Value("${upload.file.maxsize:20}")
	private int uploadFileMaxsize;
	/**
	 * 上传视频的限制大小，单位M
	 */
	@Value("${upload.video.maxsize:300}")
	private int uploadVideoMaxsize;
	/**
	 * 上传头像的限制大小，单位M
	 */
	@Value("${upload.avatar.maxsize:2}")
	private int uploadAvatarMaxsize;
	/**
	 * 上传页面的限制大小,单位M
	 */
	@Value("${upload.avatar.maxsize:2}")
	private int uploadHtmlMaxsize;
	/**
	 * 上传页面路径
	 */
 	@Value("${upload.file.news}")
	private String uploadNewsFile;

	@Value("${upload.background.maxsize:5}")
	private int uploadBackgroundMaxsize;

	@Value("${page.size.limit:50}")
	private int pageSizeLimit;
	@Value("${system.reserved.keys:all|lwxf|goods}")
	private String systemReservedKeys;

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getUserDefaultAvatar() {
		return userDefaultAvatar;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	/*public int getSessionTimeOut() {
		return sessionTimeOut;
	}*/

	public int getShiroPermissionCacheExpire() {
		return shiroPermissionCacheExpire;
	}

/*
	public String getUploadPath() {
		return uploadPath;
	}
*/

	public String getUploadTempPath() {
		return uploadTempPath;
	}

	public String getUploadAvatarRootDir() {
		return uploadAvatarRootDir;
	}

	public String getUploadFileRootDir() {
		return uploadFileRootDir;
	}

	public String getUploadAvatarRange() {
		return uploadAvatarRange;
	}

	public String getUploadImgMimeRange() {
		return uploadImgMimeRange;
	}
//
	public String getUploadFileMimeRange() {
		return uploadFileMimeRange;
	}

	public int getUploadFileMaxsize() {
		return this.uploadFileMaxsize;
	}

	public int getUploadAvatarMaxsize() {
		return uploadAvatarMaxsize;
	}

	public int getUploadBackgroundMaxsize() {
		return uploadBackgroundMaxsize;
	}

	public void setUploadBackgroundMaxsize(int uploadBackgroundMaxsize) {
		this.uploadBackgroundMaxsize = uploadBackgroundMaxsize;
	}

	public String getUploadNewsFile() {
		return uploadNewsFile;
	}

	public int getUploadHtmlMaxsize() {
		return uploadHtmlMaxsize;
	}

	public int getPageSizeLimit() {
		return pageSizeLimit;
	}

	public String getUploadVideoMimeRange() {
		return uploadVideoMimeRange;
	}

	public String getUploadHtmlMimeRange() {
		return uploadHtmlMimeRange;
	}

	public int getUploadVideoMaxsize() {
		return uploadVideoMaxsize;
	}

	/**
	 * 是否在生产环境（即正式上线）
	 * @return
	 */
	public boolean isOnProd(){
		return "prod".equals(this.profile);
	}

	/**
	 * 是否在测试环境（即阿里的dev服务器上）
	 * @return
	 */
	public boolean isOnTest(){
		return "test".equals(this.profile);
	}

	/**
	 * 是否在本地环境运行（含在本地测试服务器运行）
	 * @return
	 */
	public boolean isOnDev(){
		return isOnLocalDev() || isOnLocalTest();
	}

	/**
	 * 是否在本地测试环境（公司内部）
	 * @return
	 */
	public boolean isOnLocalTest(){
		return "localtest".equals(this.profile);
	}

	public boolean isOnFEPublic(){
		return  isOnProd();
	}



	/**
	 * 是否在本地开发环境（公司内部）
	 * @return
	 */
	private boolean isOnLocalDev(){
		return "dev".equals(this.profile);
	}

	public Map<String, Object> getSystemConfigJson(Map<String, Object> cfg) {
		if(cfg == null){
			cfg = new HashMap<>();
		}
		cfg.put("pageSizeLimit", this.pageSizeLimit);
//		cfg.put("uploadPath",this.uploadPath);
		cfg.put("uploadFileRootDir", this.uploadFileRootDir);
		cfg.put("uploadAvatarMaxsize", this.uploadAvatarMaxsize);
		cfg.put("uploadAvatarRange", this.uploadAvatarRange);
		cfg.put("uploadBackgroundMaxsize", this.uploadBackgroundMaxsize);
//		cfg.put("uploadImgMimeRange", this.uploadImgMimeRange);
//		cfg.put("uploadFileMimeRange", this.uploadFileMimeRange);
		cfg.put("reservedWords", this.systemReservedKeys);
		/*cfg.put("isOnDev", this.isOnDev());
		cfg.put("isOnProd", this.isOnProd());
		cfg.put("isOnTest", this.isOnTest());*/
		return cfg;
	}
}
