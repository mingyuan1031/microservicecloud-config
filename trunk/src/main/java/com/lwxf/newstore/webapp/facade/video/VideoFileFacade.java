package com.lwxf.newstore.webapp.facade.video;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.video.VideoFile;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 9:43
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface VideoFileFacade extends BaseFacade {
	RequestResult findAll(String disabled);
	RequestResult uploadVideoImage(String id,MultipartFile file);
	RequestResult updata(String id, MapContext mapContext);
	RequestResult uploadVideoFile(String id,MultipartFile file);
	RequestResult addVideoFile(VideoFile videoFile,String coverId,String pathId);
	RequestResult deleteById(String id);
}
