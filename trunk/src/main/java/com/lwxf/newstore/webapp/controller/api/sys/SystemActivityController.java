package com.lwxf.newstore.webapp.controller.api.sys;

import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.facade.sys.SystemActivityFacade;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/7/24 10:12
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class SystemActivityController {
	@Resource(name = "systemActivityFacade")
	private SystemActivityFacade systemActivityFacade;

	/**
	 *  分页查询所有日志
	 * @return
	 */
	@GetMapping(value = "/activitys")
	public RequestResult findActivity(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
									  @RequestParam(required = false,defaultValue = "30") Integer pageSize){

		return this.systemActivityFacade.findActivity(pageNum,pageSize);
	}
	@DeleteMapping(value = "/activitys")
	public  RequestResult deleteActivityByIds (@RequestParam String... ids){
		return this.systemActivityFacade.deleteActivityByIds(ids);
	}

}
