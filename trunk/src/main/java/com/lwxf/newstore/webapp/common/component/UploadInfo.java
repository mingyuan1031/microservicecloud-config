package com.lwxf.newstore.webapp.common.component;

import com.lwxf.newstore.webapp.common.result.FileMimeType;

public class UploadInfo {
	private String fileId;
	private FileMimeType fileMimeType;
	private String realPath;
	private String relativePath;
	private String fileName;

	public UploadInfo() {

	}

	public UploadInfo(FileMimeType fileMimeType, String realPath, String relativePath, String fileId, String fileName) {
		this.fileMimeType = fileMimeType;
		this.realPath = realPath;
		this.relativePath = relativePath;
		this.fileId = fileId;
		this.fileName = fileName;
	}

	public FileMimeType getFileMimeType() {
		return fileMimeType;
	}

	public void setFileMimeType(FileMimeType fileMimeType) {
		this.fileMimeType = fileMimeType;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}