package com.lwxf.newstore.webapp.common.component;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.UploadFileException;
import com.lwxf.commons.utils.file.FileUtil;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.result.FileMimeType;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:59:44
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class FileUploadToDiskComponent extends BaseFileUploadComponent {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadToDiskComponent.class.getName());
	@Override
	protected UploadInfo executeUpload(UploadType uploadType, String dir, MultipartFile multipartFile) {
		String baseDir = super.rootPath + dir;
		FileUtil.createDir(baseDir);
		FileName fileName = buildFileName(multipartFile);
		String path = baseDir + fileName.getFullName();
		FileMimeType fileMimeType = FileMimeTypeUtil.doFileMimeType(multipartFile);
		try {
			multipartFile.transferTo(new File(path));
			/*try {
				MagicMatch magicMatch= Magic.getMagicMatch(new File(path),true,false);
				String tempMimeType = magicMatch.getMimeType().toLowerCase();
				if(!fileMimeType.getOriginalType().equals(tempMimeType)){
					logger.error("**************** 文件的mime type 出现差异 ");
					logger.error("**************** eu.medsea.mimeutil MimeType = "+fileMimeType.getOriginalType());
					logger.error("**************** net.sf.jmimemagic = "+tempMimeType);
				}
			} catch (MagicParseException e) {
				e.printStackTrace();
			} catch (MagicMatchNotFoundException e) {
				e.printStackTrace();
			} catch (MagicException e) {
				e.printStackTrace();
			}*/
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
		// TODO：相对路径需要处理
		return new UploadInfo(fileMimeType, path,
				(dir.startsWith("/") ? dir : ("/" + dir)) + fileName.getFullName(),
				fileName.getName(),
				fileName.getFullName());
	}

	@Override
	public int executeDeleteFile(String relativePath, UploadResourceType uploadResourceType) {
		return FileUtil.delete(rootPath + relativePath);
	}

	@Override
	public boolean deleteFileByDir(String dir) {
		FileUtil.deleteDirectory(dir);
		return true;
	}

	@Override
	public String transfer(String src, String dist) {
		try {
			FileUtils.moveFile(new File(super.tempRootPath + src), new File(super.rootPath + dist));
			return dist;
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
	}

}
