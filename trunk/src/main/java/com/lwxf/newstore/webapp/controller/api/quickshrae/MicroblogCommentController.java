package com.lwxf.newstore.webapp.controller.api.quickshrae;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogCommentFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/7/9 9:42
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MicroblogCommentController {
	@Resource(name = "microblogCommentFacade")
	private MicroblogCommentFacade microblogCommentFacade;


	/**
	 * 添加帖子的评论
	 * @param microblogComment
	 * @param blogId
	 * @return
	 */
	@PostMapping(value = "/microblogs/{blogId}/comments")
	public RequestResult addMicroblogComment(@PathVariable String blogId, @RequestBody MicroblogComment microblogComment){
		return this.microblogCommentFacade.addMicroblogComment(microblogComment,blogId);
	}

	/**
	 * 删除快享帖子评论
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/microblogs/{id}/comments/{commentId}")
	public RequestResult delMicroblogComment(@PathVariable String id,@PathVariable String commentId){
		return this.microblogCommentFacade.delMicroblogCommentById(id, commentId);
	}

}
