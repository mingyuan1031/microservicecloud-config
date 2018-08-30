package com.lwxf.newstore.webapp.controller.api.demo;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.demo.DemoFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-05 14:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/demos", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class DemoController {
	@Resource(name = "demoFacade")
	private DemoFacade demoFacade;

	@GetMapping
	public RequestResult getDemos(){
		return this.demoFacade.findDemos();
	}

	@GetMapping("/createUniquneCode")
	public RequestResult createUniquneCode(){
		return this.demoFacade.generateUniquneCodeTest();
	}
}
