package com.lwxf.newstore.webapp.controller.api.order;

import javax.annotation.Resource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.dto.order.LogisticsPostDto;
import com.lwxf.newstore.webapp.domain.entity.order.Logistics;
import com.lwxf.newstore.webapp.facade.order.LogisticsFacade;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/30 16:42
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/logistics", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class LogisticsController {
	private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);
	@Resource(name = "logisticsFacade")
	private LogisticsFacade logisticsFacade;

	/**
	 * 创建发货单
	 * @param logisticsPostDto
	 * @return
	 */
	@PostMapping
	public RequestResult createLogistics(@RequestBody(required = true)LogisticsPostDto logisticsPostDto)
	{

		Logistics logistics=logisticsPostDto.getLogistics();
		String[] ids =logisticsPostDto.getIds();
		String orderId =logisticsPostDto.getOrderId();
		if (logistics==null||ids.length==0||StringUtils.isBlank(orderId))
		{
			return ResultFactory.generateResNotFoundResult();
		}
		return  this.logisticsFacade.createLogistics(logistics,ids,orderId);
	}

	/**
	 * 删除物流信息
	 * @param id
	 * @return
	 */
	@DeleteMapping(value ="/{id}")
	public RequestResult deleteLogistics(@PathVariable("id") String id)
	{
		return  this.logisticsFacade.deleteLogistics(id);
	}

	/**
	 * 查询单个物流信息
	 * @param id
	 * @return
	 */
	@GetMapping(value ="/{id}")
	public RequestResult findLogisticsById(@PathVariable("id") String id)
	{
		return  this.logisticsFacade.findLogisticsById(id);
	}

	/**
	 * 修改物流信息
	 * @param id
	 * @param update
	 * @return
	 */
	@PutMapping(value ="/{id}")
	public  RequestResult updateLogistics(@PathVariable("id") String id , @RequestBody MapContext update)
	{
		RequestResult validResult = Logistics.validateFields(update);
		if(null != validResult){
			return validResult;
		}
		update.put(WebConstant.KEY_ENTITY_UPDATED,DateUtil.getSystemDate());
		return  this.logisticsFacade.updateLogistics(id,update);
	}
}
