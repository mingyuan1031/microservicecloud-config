package com.lwxf.newstore.webapp.controller.api.scheme;

import com.google.javascript.rhino.head.RhinoSecurityManager;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.scheme.SchemePraise;
import com.lwxf.newstore.webapp.facade.scheme.SchemePraiseFacade;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 14:50
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class SchemePraiseController {
	@Resource(name = "schemePraiseFacade")
	private SchemePraiseFacade schemePraiseFacade;

	/**
	 * 点赞
	 * @param schemeId
	 * @return
	 */
	@PostMapping(value = "/schemes/{schemeId}/praise")
	public RequestResult add(@PathVariable String schemeId){
		return this.schemePraiseFacade.add(schemeId);
	}

	/**
	 * 取消点赞
	 * @param schemeId
	 * @return
	 */
	@DeleteMapping(value = "/schemes/{schemeId}/praise")
	public RequestResult deleteBySchemeIdAndCreator(@PathVariable String schemeId){
		return this.schemePraiseFacade.deleteBySchemeIdAndCreator(schemeId);
	}

}
