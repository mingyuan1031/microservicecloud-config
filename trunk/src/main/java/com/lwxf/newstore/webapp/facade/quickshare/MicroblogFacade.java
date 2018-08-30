package com.lwxf.newstore.webapp.facade.quickshare;

import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能：
 *
 * @author：panchenxiao
 * @create：2018/7/3 19:26
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MicroblogFacade extends BaseFacade {

	RequestResult addMicroblogAndImage(Microblog microblog, String[] ids);

//	RequestResult addMicroblog(Microblog microblog);

	RequestResult uploadMicroblogImage(List<MultipartFile> files);
	/**
	 * 查询普通类型的帖子
	 * @return
	 */
	RequestResult findByLimit(Integer pageNum, Integer pageSize,MapContext mapContext);

	/**
	 * 根据ID查询帖子详情
	 * @return
	 */
	RequestResult findById(String id);

	RequestResult deleteMicImage(String[] ids);

	RequestResult isNullByIds(String[] ids);

	RequestResult deleteMicroblog(String id);

//	RequestResult findByMemberId(Integer pageNum, Integer pageSize);

	RequestResult updateStatus(String id, MapContext mapContext);

	RequestResult findNameByCreator(String name);

}
