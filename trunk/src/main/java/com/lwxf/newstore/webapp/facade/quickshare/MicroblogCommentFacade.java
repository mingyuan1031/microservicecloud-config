package com.lwxf.newstore.webapp.facade.quickshare;

import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：panchenxiao
 * @create：2018/7/9 9:45
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MicroblogCommentFacade extends BaseFacade {

	RequestResult addMicroblogComment(MicroblogComment microblogComment,String blogId);

	/**
	 * 删除帖子评论
	 * @return
	 */
	RequestResult delMicroblogCommentById(String id, String commentId);

}
