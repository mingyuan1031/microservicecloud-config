package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.*;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.mobile.weixin.template.OrderReplyMsg;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.regexp.internal.RE;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsCommentService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.order.OrderGoodsService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsComments;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsCommentFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/9/009 16:08
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsCommentFacade")
public class
GoodsCommentFacadeImpl extends BaseFacadeImpl implements GoodsCommentFacade {
	@Resource(name = "goodsCommentService")
	private GoodsCommentService goodsCommentService;

	@Resource(name="goodsService")
	private GoodsService goodsService;

	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;

	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;

	@Resource(name = "orderGoodsService")
	private OrderGoodsService orderGoodsService;

	@Resource(name="userThirdInfoService")
	private UserThirdInfoService userThirdInfoService;

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Override
	public RequestResult findByMapper(Integer pageNum,Integer pageSize,MapContext mapContext) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(mapContext);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		List imgList = new ArrayList();
		PaginatedList<GoodsComments> goodsCommentsPaginatedList = this.goodsCommentService.findByMapper(filter);
		for (int i=0;i<goodsCommentsPaginatedList.getRows().size();i++) {
			GoodsComments goodsComments = goodsCommentsPaginatedList.getRows().get(i);
			List<UploadFiles> uploadFilesList = this.uploadFilesService.findByResourceIdAndStatusAndTypeId(goodsComments.getId(), true, UploadResourceType.GOODSCOMMENT.getType());
			List<UploadFiles> list = new ArrayList<UploadFiles>();
			for (UploadFiles uploadFile : uploadFilesList) {
				list.add(uploadFile);
			}
			imgList.add(list);
		}
		MapContext mapContext2 = MapContext.newOne();
		mapContext2.put("goodsComments",goodsCommentsPaginatedList.getRows());
		mapContext2.put("files",imgList);
		if(mapContext.get("goodsId")!=null){
			mapContext2.put("count",this.goodsCommentService.findCountById((String) mapContext.get("goodsId")));
		}
		return ResultFactory.generateRequestResult(mapContext2, goodsCommentsPaginatedList.getPagination());
	}

	@Override
	@Transactional
	public RequestResult addComment(GoodsComment goodsComment,String[] imgList,String orderGoodsId) {
		//定义异常返回的map
		Map<String,String> validResult = new HashMap<>();
		if(goodsComment.getParentId()==null){//买家评论
			if(orderGoodsId==null){//订单id不可为null
				validResult.put("orderGoodsId",ErrorCodes.VALIDATE_NOTNULL);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			OrderGoods orderGoods = this.orderGoodsService.findById(orderGoodsId);
			if(orderGoods==null){//订单必须真实存在
				validResult.put("orderGoodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(orderGoods.getStatus()!=2){//订单必须处于收货状态
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_RECEIVED_NOT_COMMENTABLE_10059,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_RECEIVED_NOT_COMMENTABLE_10059"));
			}else if(!orderGoods.getGoodsExtendId().equals(goodsComment.getGoodsExtendId())){//商品订单的商品信息和评论的商品信息不符合
				validResult.put("goodsExtendId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			Goods goods = this.goodsService.findById(goodsComment.getGoodsId());
			//判断商品不存在
			if(goods==null){
				validResult.put("goodsId", com.lwxf.commons.exception.ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR,validResult);
			}
			//判断报价是否是该商品下的
			if(!this.goodsExtendService.isExistByGoodsIdAndExtendId(goods.getId(),goodsComment.getGoodsExtendId())){
				validResult.put("goodsExtendId", com.lwxf.commons.exception.ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR,validResult);
			}
			//判断图片数量是否超过限制值
			if(null!=imgList&&imgList.length>6){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_IMAGE_NUM_LIMIT_10057,AppBeanInjector.i18nUtil.getMessage("BIZ_IMAGE_NUM_LIMIT_10057"));
			}
			//判断用户评论时 是否评分
			if(goodsComment.getScore()==null){
				validResult.put("score",ErrorCodes.VALIDATE_NOTNULL);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(!Arrays.asList("0","1","2").contains(goodsComment.getScore())){//判断评分字符是否是规定的字符
				validResult.put("score",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(goodsComment.getContent()==null||goodsComment.getContent().equals("")){//当用户只打分未评论时 填充信息
				goodsComment.setContent("该用户未评论");
			}else if(goodsComment.getAnswered()==null){
				goodsComment.setAnswered(false);
			}
			//埋点
			TSManualData tsManualData = WebUtils.getTSManualData();
			tsManualData.setEvent(SystemActivityEvent.GOODSCOMMENT_USER_CREATE.getValue());
			tsManualData.setParams(goodsComment);
			tsManualData.setClazz(GoodsComment.class);
			tsManualData.put("orderGoodsId",orderGoodsId);
			this.goodsCommentService.add(goodsComment);
			if(null!=imgList){
				for (int i = 0; i < imgList.length; i++) {
					MapContext mapContext = MapContext.newOne();
					mapContext.put(WebConstant.KEY_ENTITY_ID,imgList[i]);
					mapContext.put("resourceId",goodsComment.getId());
					mapContext.put("status",true);
					this.uploadFilesService.updateByMapContext(mapContext);
				}
			}
			MapContext mapContext1 = MapContext.newOne();
			mapContext1.put(WebConstant.KEY_ENTITY_ID,orderGoodsId);
			mapContext1.put("status","3");
			this.orderGoodsService.updateByMapContext(mapContext1);
			//发送微信模板消息
			String evaluate_score="";
			if(goodsComment.getScore().equals("0")){
				evaluate_score="非常好";
			}else if(goodsComment.getScore().equals("1")){
				evaluate_score="一般";
			}else{
				evaluate_score="差";
			}
			Map<String,String> map = new HashMap<>();
			map.put("goods_name",goods.getName());
			map.put("customer_name",WebUtils.getCurrUser().getName());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("evaluate_time",format.format(goodsComment.getCreated()));
			map.put("evaluate_score",evaluate_score);
			map.put("evaluate_content",goodsComment.getContent());
			OrderReplyMsg msg = new OrderReplyMsg();
			List<UserThirdInfo> clerks = userThirdInfoService.findAllClerks();
			msg.setContentMsg(map);
			if (clerks.size()!=0){
				for (UserThirdInfo thirdInfo :clerks) {
					msg.setTouser(thirdInfo.getWxOpenId());
					AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
				}
			}
		}else{
			//商家的回复时判断goodsId 和extendId 和 买家的评论是否一致
			GoodsComment parentComment = this.goodsCommentService.findById(goodsComment.getParentId());
			if(parentComment==null){//回复的那条Id 不存在
				validResult.put("parentId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(!parentComment.getGoodsId().equals(goodsComment.getGoodsId())){//商品Id 和回复的商品Id 不一样
				validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(!parentComment.getGoodsExtendId().equals(goodsComment.getGoodsExtendId())){//报价Id 和回复的报价Id 不一样
				validResult.put("goodsExtendId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(goodsComment.getScore()!=null){//卖家回复时 评分字段应为null
				validResult.put("score",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(goodsComment.getContent()==null||goodsComment.getContent().equals("")){//卖家回复时 内容不可以为空
				validResult.put("content",ErrorCodes.VALIDATE_NOTNULL);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}else if(parentComment.getAnswered()!=null&&parentComment.getAnswered()){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_REPLIES_ARE_ALLOWED_ONLY_ONCE_10061,AppBeanInjector.i18nUtil.getMessage("BIZ_REPLIES_ARE_ALLOWED_ONLY_ONCE_10061"));
			}
			//埋点
			TSManualData tsManualData = WebUtils.getTSManualData();
			tsManualData.setClazz(GoodsComment.class);
			tsManualData.setEvent(SystemActivityEvent.GOODSCOMMENT_ADMIN_CREATE.getValue());
			tsManualData.setParams(goodsComment);
			tsManualData.put("orderGoodsId",orderGoodsId);
			this.goodsCommentService.add(goodsComment);
			MapContext mapContext1 = MapContext.newOne();
			mapContext1.put("answered",true);
			mapContext1.put(WebConstant.KEY_ENTITY_ID,goodsComment.getParentId());
			this.goodsCommentService.updateByMapContext(mapContext1);
		}
		MapContext result = MapContext.newOne();
		result.put("context",goodsComment.getContent());
		result.put("created",goodsComment.getCreated());
		return ResultFactory.generateRequestResult(result);
	}

	@Override
	@Transactional
	public RequestResult uploadCommentImage(String goodsId, MultipartFile[] FileList) {
		List list = new ArrayList();
		if(!goodsService.isExist(goodsId)){
			return ResultFactory.generateResNotFoundResult();
		}
		for (MultipartFile file : FileList){
			//赋值给uploadinfo,数据进行处理
			UploadInfo uploadInfo=AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,file,UploadResourceType.GOODSCOMMENT,goodsId);
			//创建uploadFiles
			UploadFiles uploadFiles = UploadFiles.create(null,goodsId,uploadInfo,UploadResourceType.GOODSCOMMENT,UploadType.TEMP);
			this.uploadFilesService.add(uploadFiles);
			Map map = new HashMap();
			map.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
			map.put("file",uploadInfo.getRelativePath());
			list.add(map);
		}
		return ResultFactory.generateRequestResult(list);
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id,String goodsId) {
		GoodsComment goodsComment = this.goodsCommentService.findById(id);
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(GoodsComment.class);
		tsManualData.setParams(id);
		tsManualData.setEvent(SystemActivityEvent.GOODSCOMMENT_DELETE.getValue());
		if(goodsComment==null){
			return ResultFactory.generateSuccessResult();
		}
		if(!goodsComment.getGoodsId().equals(goodsId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(goodsComment.getMemberId().equals(WebUtils.getCurrUserId())||!WebUtils.getCurrUser().getRole().equals("0")){
			this.goodsCommentService.deleteById(goodsComment);
			//修改回复的那条评论状态为未回复
			if(goodsComment.getParentId()!=null){
				MapContext mapContext = MapContext.newOne();
				mapContext.put(WebConstant.KEY_ENTITY_ID,goodsComment.getParentId());
				mapContext.put("answered",false);
				this.goodsCommentService.updateByMapContext(mapContext);
			}
		}else{
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
		}
		List<UploadFiles> uploadFilesList= this.uploadFilesService.findByResourceId(id);
		for (UploadFiles uploadFiles :uploadFilesList) {
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(AppBeanInjector.configuration.getUploadFileRootDir().concat(uploadFiles.getPath()));
		}
		this.uploadFilesService.deleteByResourceId(id);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult updataDisabled(String id,String goodsId) {
		GoodsComment goodsComment = this.goodsCommentService.findById(id);
		MapContext mapContext = MapContext.newOne();
		if(goodsComment==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsComment.getGoodsId().equals(goodsId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if(goodsComment.getDisabled()){
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,false);
		}else{
			mapContext.put(WebConstant.KEY_ENTITY_DISABLED,true);
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		this.goodsCommentService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

}
