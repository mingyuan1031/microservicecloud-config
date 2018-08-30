package com.lwxf.newstore.webapp.controller.api.goods;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.FileMimeTypeUtil;
import com.lwxf.newstore.webapp.common.utils.JsonUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.controller.path.LoadBaseCfgController;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.goods.GoodsCommentFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/9/009 15:23
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@RestController
@RequestMapping(value = "/api/", produces = WebConstant.RESPONSE_CONTENT_TYPE_JSON_CHARTSET)
public class GoodsCommentController {
	@Resource(name="goodsCommentFacade")
	private GoodsCommentFacade goodsCommentFacade;

	@GetMapping("goods/comments")
	private RequestResult findByMapper(@RequestParam(required = false,defaultValue = "1")Integer pageNum,@RequestParam(required = false,defaultValue = "10")Integer pageSize, @RequestParam(required = false) String goodsId,@RequestParam(required = false) String score,@RequestParam(required = false) Boolean disabled,@RequestParam(required = false) String goodsName){
		MapContext mapContext = this.createMapContext(goodsId,score,disabled,goodsName);
		return goodsCommentFacade.findByMapper(pageNum,pageSize,mapContext);
	}
	@GetMapping("goods/{goodsId}/comments")
	private RequestResult findByGoodsId(@RequestParam(required = false,defaultValue = "1")Integer pageNum,@RequestParam(required = false,defaultValue = "10")Integer pageSize, @PathVariable String goodsId,@RequestParam(required = false) String score){
		MapContext mapContext = this.createMapContext(goodsId,score,false,null);
		return goodsCommentFacade.findByMapper(pageNum,pageSize,mapContext);
	}
	@PostMapping("goods/{goodsId}/comments")
	private RequestResult addComment(@PathVariable String goodsId,@RequestBody MapContext mapContext){
		String orderGoodsId = (String) mapContext.get("orderGoodsId");
		List imgList= (List) mapContext.get("imgList");

		Object comment= mapContext.get("goodsComment");
//		JSONObject jsonObject=JSONObject.fromObject(c);
		GoodsComment goodsComment = JsonUtil.from(comment, GoodsComment.class);
		goodsComment.setGoodsId(goodsId);
		goodsComment.setMemberId(WebUtils.getCurrUserId());
		goodsComment.setCreated(DateUtil.getSystemDate());
		goodsComment.setDisabled(false);
		RequestResult result= goodsComment.validateFields();
		if(result==null){
			if(imgList!=null){
				String [] imgs = new String[imgList.size()];
				imgList.toArray(imgs);
				return goodsCommentFacade.addComment(goodsComment,imgs,orderGoodsId);
			}
			return goodsCommentFacade.addComment(goodsComment,null,orderGoodsId);
		}
		return result;
	}
	@PutMapping("goods/{goodsId}/comments/{commentsId}")//更新状态 修改时 只准修改显示 隐藏
	private RequestResult updataDisabled(@PathVariable String commentsId,@PathVariable String goodsId){
		return goodsCommentFacade.updataDisabled(commentsId,goodsId);
	}
	@DeleteMapping("goods/{goodsId}/comments/{commentsId}")
	private RequestResult deleteById(@PathVariable String commentsId,@PathVariable String goodsId){
		return goodsCommentFacade.deleteById(commentsId,goodsId);
	}
	@PostMapping("goods/{goodsId}/comments/files")
	public RequestResult uploadFiles(@PathVariable String goodsId, @RequestBody MultipartFile[] fileList){
		Map<String, Object> errInfo = new HashMap<>();
		for(int i=0;i<fileList.length;i++){
			MultipartFile file =fileList[i];
			if (file == null) {
				errInfo.put("file", ErrorCodes.VALIDATE_NOTNULL);
			} else if (!FileMimeTypeUtil.isLegalImageType(file)) {
				errInfo.put("file", ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			} else if (file.getSize() > 1024L * 1024L * AppBeanInjector.configuration.getUploadBackgroundMaxsize()) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_FILE_SIZE_LIMIT_10031, LwxfStringUtils.format(AppBeanInjector.i18nUtil.getMessage("BIZ_FILE_SIZE_LIMIT_10031"),AppBeanInjector.configuration.getUploadBackgroundMaxsize()));
			}
			if (errInfo.size() > 0) {
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errInfo);
			}
		}
		return this.goodsCommentFacade.uploadCommentImage(goodsId,fileList);
	}
	private MapContext createMapContext(String goodsId,String score,Boolean disabled,String goodsName){
		MapContext mapContext = MapContext.newOne();
		if(null!=goodsId){
			mapContext.put("goodsId",goodsId);
		}
		if(null!=score){
			mapContext.put("score",score);
		}
		if(null!=disabled){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,disabled);
		}
		if(null!=goodsName){
			mapContext.put("goodsName",goodsName);
		}
		return mapContext;
	}
}

