package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.lang3.StringUtils;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.domain.entity.goods.SpecOption;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsExtendFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/27/027 15:22
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsExtendFacade")
public class GoodsExtendFacadeImpl extends BaseFacadeImpl implements GoodsExtendFacade {
	@Resource(name = "goodsExtendService")
	private GoodsExtendService goodsExtendService;
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name="uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "specOptionService")
	private SpecOptionService specOptionService;
	@Override
	public RequestResult findByGoodsId(String GoodsId) {
		return ResultFactory.generateRequestResult(goodsExtendService.findByGoodsId(GoodsId));
	}

	@Override
	@Transactional
	public RequestResult add(String goodsId,List<GoodsExtend> goodsExtendList) {
		//定义异常的map
		Map<String,String> validResult = new HashMap<String, String>();
		Goods goods= goodsService.findById(goodsExtendList.get(0).getGoodsId());
		if(goods==null) {
			return ResultFactory.generateResNotFoundResult();
		}
			List list= this.goodsExtendService.findByGoodsId(goodsId);
			if(list.size()!=0){//如果商品有报价 就不允许添加新的
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_PRICE_COMBINATION_NOT_UPDATE_10060,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_PRICE_COMBINATION_NOT_UPDATE_10060"));
			}
			//获取第一组的选项用来得到规格id
			String[] optionsForSpec= goodsExtendList.get(0).getOptions().split(",");
			String options = "";
			for (GoodsExtend goodsExtend:goodsExtendList) {
				String option= goodsExtend.getOptions();
				//判断规格选项数量是否一致
				if(option.split(",").length!=optionsForSpec.length){
					validResult.put("options",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
					return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
				}
				options+=option+",";
			}
			//通过set去重
			options = options.substring(0,options.length()-1);
			Set<String> optionsSet = new HashSet<String>(Arrays.asList(options.split(",")));
			//去重后再转换成数组
			String[] onlyOptions = optionsSet.toArray(new String[optionsSet.size()]);
//			//数组转换成字符串去数据库查询看看查到的条数和数组的条数是否一致 判断这些规格选项是否都存在
//			String strOptions= StringUtils.join(onlyOptions,",");
			int specOptionNum = this.specOptionService.findByIds(onlyOptions);
			if(specOptionNum!=onlyOptions.length){
				validResult.put("options",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			//通过字符串查询是否属于规格数量是否一致
			int specNum = this.specOptionService.findSpecByOptionids(onlyOptions);
			if(specNum!=optionsForSpec.length){
				validResult.put("options",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			//通过字符串查询是否属于同一分类下
			int typeNum = this.specOptionService.findTypeByOptions(onlyOptions);
			if(typeNum!=1){
				validResult.put("options",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			boolean flag=goodsExtendService.isExistByDefaults(goodsId);
			for (GoodsExtend goodsExtend:goodsExtendList) {
				if (!flag){
					goodsExtend.setDefaults(true);
					flag = true;
				}
				goodsExtend.setGoodsId(goodsId);
				goodsExtendService.add(goodsExtend);
			}
			//埋点
			TSManualData tsManualData = WebUtils.getTSManualData();
			tsManualData.setClazz(Goods.class);
			tsManualData.setEvent(SystemActivityEvent.GOODS_PRICES_CREATE.getValue());
			tsManualData.setParams(goodsExtendList);
			tsManualData.put(WebConstant.KEY_ENTITY_GOODS,goods);

			//更新商品的修改人和修改时间信息
			this.goodsService.updateUpdator(goods.getId());
			return ResultFactory.generateRequestResult(goodsExtendList);
		}

	@Override
	@Transactional
	public RequestResult updata(String id, MapContext mapContext,String goodsId) {
		GoodsExtend goodsExtend = this.goodsExtendService.findById(id);
		if (goodsExtend!=null) {
			if(!goodsExtend.getGoodsId().equals(goodsId)){
				Map<String,String> validResult = new HashMap<>();
				validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
				return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
			}
			Goods goods = goodsService.findById(goodsExtend.getGoodsId());
			if (!goods.getDisabled()) {
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058, AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
			}
			//如果是修改默认 就先更改原先的默认选项为false
			if(mapContext.get("defaults")!=null){
				if(mapContext.get("defaults").equals(true)){
					this.goodsExtendService.removeDefaults(goodsExtend.getGoodsId());
				}else{
					//当取消默认时 报出异常 必须有一个报价为默认报价
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NOT_ALLOW_OPERATION_10020,AppBeanInjector.i18nUtil.getMessage("BIZ_NOT_ALLOW_OPERATION_10020"));
				}
			}
			//埋点
			TSManualData tsManualData = WebUtils.getTSManualData();
			tsManualData.setClazz(Goods.class);
			tsManualData.setEvent(SystemActivityEvent.GOODS_PRICES_MD.getValue());
			tsManualData.setParams(mapContext);
			tsManualData.put(WebConstant.KEY_ENTITY_GOODS,goods);
			tsManualData.put("goodsExtend",goodsExtend);
			tsManualData.put(WebConstant.KEY_ENTITY_ID,id);

			mapContext.put(WebConstant.KEY_ENTITY_ID,id);
			this.goodsExtendService.updateByMapContext(mapContext);
			//更新商品的修改人和修改时间信息
			this.goodsService.updateUpdator(goods.getId());
			return ResultFactory.generateSuccessResult();
		}
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult uploadExtendImage(String id, MultipartFile file,String goodsId) {
		GoodsExtend goodsExtend = this.goodsExtendService.findById(id);
		if(goodsExtend==null){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!this.goodsService.isExist(goodsId)){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsExtend.getGoodsId().equals(goodsId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		Goods goods = this.goodsService.findById(goodsExtend.getGoodsId());
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		//赋值给uploadinfo 进行数据处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,file,UploadResourceType.GOODSSPEC,goods.getId());
		//从uploadfiles中查询是否已存在该报价的图片
		UploadFiles uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(goods.getId(),id,UploadResourceType.GOODSSPEC.getType());
		if(uploadFiles==null){
			uploadFiles= UploadFiles.create(id,goods.getId(),uploadInfo,UploadResourceType.GOODSSPEC,UploadType.FORMAL);
			this.uploadFilesService.add(uploadFiles);
		}else{
			//替换的话就删除以前的旧图
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(AppBeanInjector.configuration.getUploadFileRootDir().concat(uploadFiles.getPath()));
			MapContext update = MapContext.newOne();
			update.put(WebConstant.KEY_ENTITY_ID,uploadFiles.getId());
			update.put("path",uploadInfo.getRelativePath());
			update.put("name",uploadInfo.getFileName());
			update.put("originalMime",uploadInfo.getFileMimeType().getOriginalType());
			update.put("mime",uploadInfo.getFileMimeType().getRealType());
			update.put("creator",WebUtils.getCurrUserId());
			update.put("created",DateUtil.getSystemDate());
			this.uploadFilesService.updateByMapContext(update);
		}
		MapContext mapContext = new MapContext();
		mapContext.put("bigimage",uploadInfo.getRelativePath());
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);

		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Goods.class);
		tsManualData.setEvent(SystemActivityEvent.GOODS_PRICES_MD.getValue());
		tsManualData.setParams(mapContext);
		tsManualData.put(WebConstant.KEY_ENTITY_GOODS,goods);
		tsManualData.put("goodsExtend",goodsExtend);
		tsManualData.put(WebConstant.KEY_ENTITY_ID,id);



		this.goodsExtendService.updateByMapContext(mapContext);
		Map map = new HashMap();
		map.put("file",uploadInfo.getRelativePath());
		//更新商品的修改人和修改时间信息
		this.goodsService.updateUpdator(goods.getId());
		return ResultFactory.generateRequestResult(map);
	}
}
