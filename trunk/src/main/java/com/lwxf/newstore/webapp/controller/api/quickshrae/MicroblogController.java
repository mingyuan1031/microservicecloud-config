package com.lwxf.newstore.webapp.controller.api.quickshrae;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.quickshare.MicroblogFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Mult;

/**
 * 功能：
 *
 * @author：panchenxiao
 * @create：2018/7/4 16:01
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class MicroblogController {
	@Resource(name = "microblogFacade")
	private MicroblogFacade microblogFacade;

	/**
	 * 添加帖子并修改图片为正式图片()
	 * @param
	 * @param imageId
	 * @return
	 */
	@PostMapping(value = "/microblogs")
	public RequestResult addMicroblogs(@RequestBody Microblog microblog, @RequestParam(required = false) String... imageId) {
		return this.microblogFacade.addMicroblogAndImage(microblog,imageId);
	}



	/**
	 * 上传帖子的临时图片（）
	 * @param fileList
	 * @return
	 */
	@PostMapping(value = "/microblogs/files")
	public RequestResult uploadMicTempImage(@RequestBody MultipartFile... fileList){
		List<MultipartFile> list = new ArrayList<>();
		for(MultipartFile multipartFile : fileList){
			Map<String,Object> errorInfo = new HashMap<String, Object>();
			if(multipartFile==null){
				errorInfo.put("files",ErrorCodes.VALIDATE_NOTNULL);
			}else if (!FileMimeTypeUtil.isLegalImageType(multipartFile)) {
				errorInfo.put("files", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			} else if (multipartFile.getSize() > 1024L * 1024L* AppBeanInjector.configuration.getUploadBackgroundMaxsize()) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031").concat(":").concat(AppBeanInjector.configuration.getUploadBackgroundMaxsize()+"").concat("M"));
			}
			if (errorInfo.size() > 0) {
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errorInfo);
			}
			list.add(multipartFile);
		}
		return this.microblogFacade.uploadMicroblogImage(list);
	}

	/**
	 * 删除快享帖子的图片（批量删除）
	 * @param
	 * @return
	 */
//	@DeleteMapping(value = "/microblogs/files/{fileId}")
//	public RequestResult deleteMicoImage(@PathVariable String...fileId){
//		return  this.microblogFacade.deleteMicImage(fileId);
//	}

	/**
	 * 删除快享帖子以及关联的图片和评论、点赞
	 * @param
	 * @return
	 */
	@DeleteMapping(value = "/microblogs/{blogId}")
	public RequestResult delectMicroblog (@PathVariable String blogId){
		return this.microblogFacade.deleteMicroblog(blogId);
	}


	/**
	 *  返回普通帖子的列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/microblogs")
	public RequestResult findMicroblgs(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
									   @RequestParam(required = false,defaultValue = "30") Integer pageSize,
									   @RequestParam(required = false) String name,
									   @RequestParam(required = false) String content,
									   @RequestParam(required = false) String startTime,
									   @RequestParam(required = false) String endTime){
		MapContext mapContext = MapContext.newOne();
		if(LwxfStringUtils.isNotBlank(name)){
			mapContext.put(WebConstant.KEY_ENTITY_NAME, name);
		}
		if(LwxfStringUtils.isNotBlank(content)){
		    mapContext.put(WebConstant.KEY_ENTITY_CONTENT, content);
		}
		if(LwxfStringUtils.isNotBlank(startTime)){
		    mapContext.put(WebConstant.KEY_ENTITY_STARTTIME, startTime);
		}
		if(LwxfStringUtils.isNotBlank(endTime)){
			mapContext.put(WebConstant.KEY_ENTITY_ENDTIME, endTime);
		}
		return this.microblogFacade.findByLimit(pageNum,pageSize,mapContext);
	}

	/**
	 * 查看普通帖子信息
	 * @param blogId
	 * @return
	 */
	@GetMapping(value = "/microblogs/{blogId}")
	public RequestResult findMicroblogsByblogId(@PathVariable String blogId){
		return this.microblogFacade.findById(blogId);
	}

	/**
	 * 获取当前登陆用户的帖子列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping(value = "/users/0/microblogs")
	public RequestResult findByMemberId(@RequestParam(required = false,defaultValue = "1") Integer pageNum, @RequestParam(required = false,defaultValue = "30") Integer pageSize){
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_CREATOR, WebUtils.getCurrUserId());
		return this.microblogFacade.findByLimit(pageNum, pageSize,mapContext);
	}

	/**
	 * 修改帖子的状态
	 * @param mapContext
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/microblogs/{id}")
	public RequestResult updateStatus(@PathVariable String id, @RequestBody MapContext mapContext){
		return this.microblogFacade.updateStatus(id,mapContext);
	}



}
