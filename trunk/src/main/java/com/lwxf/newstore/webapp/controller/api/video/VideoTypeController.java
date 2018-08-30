package com.lwxf.newstore.webapp.controller.api.video;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.video.VideoType;
import com.lwxf.newstore.webapp.facade.video.VideoTypeFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/12/012 10:29
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/videotypes", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class VideoTypeController {
	@Resource(name = "videoTypeFacade")
	private VideoTypeFacade videoTypeFacade;

	@GetMapping
	private RequestResult getByFilter(@RequestParam(required = false) String disabled){
		return videoTypeFacade.findByFilter(disabled);
	}

	@PostMapping
	private RequestResult addVideoType(@RequestBody VideoType videoType){
		videoType.setCreated(DateUtil.getSystemDate());
		videoType.setCreator(WebUtils.getCurrUserId());
		videoType.setDisabled(false);
		RequestResult result = videoType.validateFields();
		if(result!=null){
			return result;
		}
		return videoTypeFacade.add(videoType);
	}
	@DeleteMapping("{id}")
	private RequestResult deleteById(@PathVariable String id){
		return videoTypeFacade.deleteById(id);
	}

	@PutMapping("{id}")
	private RequestResult updata(@PathVariable String id, @RequestBody MapContext mapContext){
		RequestResult result = VideoType.validateFields(mapContext);
		if(result!=null){
			return result;
		}
		return videoTypeFacade.updata(id,mapContext);
	}
}
