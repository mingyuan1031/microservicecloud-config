package com.lwxf.newstore.webapp.controller.api.quickshrae;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogPraiseFacade;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/6 14:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MicroblogPraiseController {

	@Resource(name = "microblogPraiseFacade")
	private MicroblogPraiseFacade microblogPraiseFacade;

	@PostMapping(value = "/microblogs/{blogId}/praises")
	public RequestResult addMicroblogPraises(@PathVariable String blogId){
		return this.microblogPraiseFacade.addMicroblogPraise(blogId);
	}

	@DeleteMapping(value = "/microblogs/{blogId}/praises")
	public RequestResult delByblogId(@PathVariable String blogId){
		return this.microblogPraiseFacade.deleteMicroblogPraises(blogId);
	}


}
