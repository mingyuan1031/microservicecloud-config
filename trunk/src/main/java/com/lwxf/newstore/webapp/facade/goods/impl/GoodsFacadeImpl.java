package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;


import java.util.*;

import com.lwxf.newstore.webapp.common.mobile.weixin.template.AddGoodsMsg;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.goods.*;
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
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsDefault;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsTagsInfo;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.*;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/20/020 18:16
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsFacade")
public class GoodsFacadeImpl extends BaseFacadeImpl implements GoodsFacade {
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name="brandService")
	private BrandService brandService;
	@Resource(name = "goodsTypeService")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "goodsShowService")
	private GoodsShowService goodsShowService;
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name="goodsTagService")
	private GoodsTagService goodsTagService;
	@Resource(name="goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Override
	public RequestResult findGoodsDefaultByParamsForPaging(Integer pageNum,Integer pageSize, MapContext mapContext) {
		PaginatedFilter filter = PaginatedFilter.newOne();
		filter.setFilters(mapContext);
		Pagination pagination = Pagination.newOne();
		pagination.setPageNum(pageNum);
		pagination.setPageSize(pageSize);
		filter.setPagination(pagination);
		PaginatedList<GoodsDefault> goodsDefaultPaginatedList= this.goodsService.findGoodsDefaultByParamsForPaging(filter);
		List tags = new ArrayList<Object>();
		for (int i = 0; i <goodsDefaultPaginatedList.getRows().size() ; i++) {
			List<GoodsTagsInfo> goodsTagsList = new ArrayList<GoodsTagsInfo>();
			goodsTagsList = this.goodsTagService.findByGoodsId(goodsDefaultPaginatedList.getRows().get(i).getGoodsId());
			if(goodsTagsList.size()==0){
				tags.add("null");
			}else{
				tags.add(goodsTagsList);
			}
		}
		Map map = new HashMap();
		map.put("goods",goodsDefaultPaginatedList);
		map.put("tags",tags);
		return ResultFactory.generateRequestResult(map);
	}

	@Override
	@Transactional
	public RequestResult addGoods(Goods goods) {
		if(goods.getName().trim().equals("")){
			Map<String ,String> valiResult = new HashMap<>();
			valiResult.put("name",ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,valiResult);
		}
		if(isTypeRight(goods)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsTypeId", com.lwxf.commons.exception.ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR,validResult);
		}
		goodsService.add(goods);
		//给管理员发送微信消息
		List<UserThirdInfo> clerks =AppBeanInjector.userThirdInfoService.findAllClerks();
		if (clerks.size()!=0){
			for (UserThirdInfo thirdInfo:clerks) {
				AddGoodsMsg msg = new AddGoodsMsg();
				Map<String,String> map = new HashMap<>();
				map.put("goods_name",goods.getName());
				map.put("goods_count","0");
				map.put("creat_time",DateUtil.dateTimeToString(goods.getCreated()));
				msg.setContentMsg(map);
				msg.setTouser(thirdInfo.getWxOpenId());
				AppBeanInjector.weixinTemplateMsgService.pushMsg(msg);
			}
		}

		return ResultFactory.generateRequestResult(goods);
	}

	@Override
	public RequestResult findForHomePage() {
		return ResultFactory.generateRequestResult(this.goodsService.findForHomePage());
	}

	@Override
	@Transactional
	public RequestResult updata(String id, MapContext mapContext) {
		Goods goods= goodsService.findById(id);
		//判断商品是否存在
		if(goods==null){
			return ResultFactory.generateResNotFoundResult();
		}
		//是否是修改商品的上下架情况
		if(mapContext.get("disabled")==null){
			//是否是修改置顶状态
			if(mapContext.get("tops")!=null){
				mapContext.put(WebConstant.KEY_ENTITY_ID,id);
				this.goodsService.updateByMapContext(mapContext);
				return ResultFactory.generateSuccessResult();
			}
			//判断商品是否下架 不修改上下架走if
			if(!goods.getDisabled()){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
			}
		}else if(mapContext.get("disabled").equals(false)){
			if(goods.getFreight()==null){
				Map<String,String> validResult = new HashMap<String, String>();
				validResult.put("freight",ErrorCodes.VALIDATE_NOTNULL);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			List<GoodsExtend> goodsExtendList=this.goodsExtendService.findByGoodsId(id);
			if(goodsExtendList.size()==0){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_A_COMMODITY_MUST_HAVA_A_PRICE_10062,AppBeanInjector.i18nUtil.getMessage("BIZ_A_COMMODITY_MUST_HAVA_A_PRICE_10062"));
			}
		}
		//是否是修改商品分类 如果存在报价则不允许修改
		String goodsTypeId = (String) mapContext.get("goodsTypeId");
		if(goodsTypeId!=null){
			if(this.goodsExtendService.findByGoodsId(id).size()!=0){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NOT_ALLOW_OPERATION_10020,AppBeanInjector.i18nUtil.getMessage("BIZ_NOT_ALLOW_OPERATION_10020"));
			}else{//判断分类是否存在,是否禁用
				GoodsType goodsType= this.goodsTypeService.findById(goodsTypeId);
				if(goodsType==null||goodsType.getDisabled()){
					Map<String,String> validResult = new HashMap<String, String>();
					validResult.put("goodsTypeId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
					return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
				}
			}
		}
		//是否是修改商品名称 判断名称是否为空
		String name=(String)mapContext.get("name");
		if(name!=null&&name.trim().equals("")){
			Map<String ,String> valiResult = new HashMap<>();
			valiResult.put("name",ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,valiResult);
		}
		//是否是修改商品品牌 判断品牌是否存在
		if(mapContext.get("brandId")!=null){
			Brand brand = this.brandService.findById((String) mapContext.get("brandId"));
			if(brand==null||brand.getDisabled()){
				Map<String,String> validResult = new HashMap<>();
				validResult.put("brandId", com.lwxf.commons.exception.ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.VALIDATE_ERROR,validResult);
			}
		}
		//修改商品信息 顺道更新更新人及更新时间
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		mapContext.put("updated",DateUtil.getSystemDate());
		mapContext.put("updator",WebUtils.getCurrUserId());
		this.goodsService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult findById(String id) {
		Goods goods=goodsService.findById(id);
		if(null==goods){
			return ResultFactory.generateResNotFoundResult();
		}
		MapContext mapContext = MapContext.newOne();
		mapContext.put("goods",goods);
		mapContext.put("goodsExtendList",this.goodsExtendService.findByGoodsId(id));
		mapContext.put("tags",this.goodsTagService.findByGoodsId(id));
	//	mapContext.put("goodsInfo",this.goodsService.findGoodsInfo(id));
		List<GoodsExtend> goodsExtendList = this.goodsExtendService.findByGoodsId(id);
		List specOptions = new ArrayList();
		if(goodsExtendList.size()!=0){
			String[] optionsSize= goodsExtendList.get(0).getOptions().split(",");
			for (int i = 0;i<optionsSize.length;i++) {
				Map<String,Object> specOptionMap = new HashMap<String, Object>();
				SpecOption specOption=this.specOptionService.findById(optionsSize[i]);
				specOptionMap.put("spec",specOption.getGoodsSpecId());
				Set set = new HashSet<String>();
				for (GoodsExtend goodsExtend:goodsExtendList) {
					String options= goodsExtend.getOptions();
					String[] opt = options.split(",");
					set.add(opt[i]);
				}
				specOptionMap.put("options",set);
				specOptions.add(specOptionMap);
			}
		}
	//	mapContext.put("goodsExtendsList",goodsExtendList);
		mapContext.put("goodsSpecsOptionsList",specOptions);
		return ResultFactory.generateRequestResult(mapContext);
	}

	@Override
	@Transactional
	public RequestResult uploadGoodsImage(String goodsId, MultipartFile file) {
		Goods goods = this.goodsService.findById(goodsId);
		if (goods == null ) {
			return ResultFactory.generateErrorResult(com.lwxf.commons.exception.ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
		}
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		//赋值给uploadinfo,数据进行处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP,file,UploadResourceType.GOODS,goodsId);
		UploadFiles uploadFiles = UploadFiles.create(goodsId,goodsId ,uploadInfo,UploadResourceType.GOODS,UploadType.TEMP);
		this.uploadFilesService.add(uploadFiles);
		MapContext mapContext = MapContext.newOne();
		mapContext.put("id",uploadFiles.getId());
		mapContext.put("path",uploadFiles.getPath());

		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Goods.class);
		tsManualData.setEvent("null");

		return ResultFactory.generateRequestResult(mapContext);
	}

	@Override
	@Transactional
	public RequestResult updataContent(String goodsId,List<Map<String,Object>> uploadFilesList,String content) {
		Goods goods=this.goodsService.findById(goodsId);
		if(goods==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		MapContext mapContext= MapContext.newOne();
		if(uploadFilesList!=null&&uploadFilesList.size()!=0){
			for (int i = 0; i <uploadFilesList.size() ; i++) {
				if(!uploadFilesService.isExist((String) uploadFilesList.get(i).get("id"))){
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
				}
			}
			for (int i = 0; i <uploadFilesList.size() ; i++) {
				mapContext.put(WebConstant.KEY_ENTITY_ID,(String) uploadFilesList.get(i).get("id"));
				mapContext.put("status",(Integer) uploadFilesList.get(i).get("status"));
				uploadFilesService.updateByMapContext(mapContext);
			}
		}
		//更新详情顺道更新更新人和更新时间字段
		mapContext.put("updator",WebUtils.getCurrUserId());
		mapContext.put("updated",DateUtil.getSystemDate());
		mapContext.put(WebConstant.KEY_ENTITY_ID,goodsId);
		mapContext.put("content",content);
		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Goods.class);
		tsManualData.setEvent(SystemActivityEvent.GOODS_MD.getValue());
		tsManualData.setParams(mapContext);
		tsManualData.put(WebConstant.KEY_ENTITY_GOODS,goods);
		tsManualData.put(WebConstant.KEY_ENTITY_ID,goods.getId());
		this.goodsService.updateByMapContext(mapContext);
		return ResultFactory.generateSuccessResult();
	}


	private boolean isTypeRight(Goods goods){
		GoodsType goodsType= goodsTypeService.findById(goods.getGoodsTypeId());
		//判断分类是否存在 是否被禁用 父级是否禁用
		if(null==goodsType||goodsType.getDisabled()){
			return true;
		}
		//如果有父类 父类是否为null 父类是否被禁用
		if(null!=goodsType.getParentId()){
			GoodsType parentType = this.goodsTypeService.findById(goodsType.getParentId());
			if (null==parentType||parentType.getDisabled()){
				return true;
			}
		}
		return false;
	}
}
