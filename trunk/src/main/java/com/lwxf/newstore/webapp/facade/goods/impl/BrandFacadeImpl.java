package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.goods.BrandService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.BrandFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/15/015 14:55
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("brandFacade")
public class BrandFacadeImpl extends BaseFacadeImpl implements BrandFacade {

	@Resource(name="brandService")
	private BrandService brandService;
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name="uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Override
	public RequestResult findAll() {
		return ResultFactory.generateRequestResult(this.brandService.findAll());
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		Brand brand= brandService.findById(id);
		if (brand!=null){
			if(goodsService.isExistByBrandId(id)){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_BE_IN_USE_10006,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_BE_IN_USE_10006"));
			}
			if(brand.getImage()!=null){
				this.uploadFilesService.deleteByResourceId(id);
				AppBeanInjector.baseFileUploadComponent.deleteFileByDir(AppBeanInjector.configuration.getUploadFileRootDir().concat(brand.getImage().substring(0,brand.getImage().lastIndexOf("/"))));
			}
			brandService.deleteById(id);
			uploadFilesService.deleteByResourceId(id);
			return ResultFactory.generateSuccessResult();
		}
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult addBrand(Brand brand) {
		if(brand.getName().trim().equals("")){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		if (this.brandService.findByBrandName(brand.getName())!=null){
			Map<String,String> validResult = new HashMap<>();
			validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		brandService.add(brand);
		return ResultFactory.generateRequestResult(brand);
	}

	@Override
	public RequestResult findById(String id) {
		Brand brand=this.brandService.findById(id);
		if(null!=brand){
			//存在就查询出来返回
			return ResultFactory.generateRequestResult(this.brandService.findById(id));
		}
		//不存在就返回空
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult updataBrand(String id,MapContext updata) {
		//判断id是否存在
		Brand brand = this.brandService.findById(id);
		if(null!=brand){
			//如果是修改禁用启用的操作,则不用判断是否禁用
			if(updata.get("disabled")==null){
				//如果不是修改启用禁用操作 就判断操作的当条数据 是否是禁用
				if(true==brand.getDisabled()){
					return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
				}
			}
			//是否是修改品牌名操作
			if(updata.get("name")!=null){
				if(((String)updata.get("name")).trim().equals("")){
					Map<String,String> validResult = new HashMap<>();
					validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOTNULL);
					return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
				}
				Brand brand1 = this.brandService.findByBrandName((String)updata.get("name"));
				//所要修改的品牌名存在且id和当前修改的数据id不同 则返回不可重复异常(防止用户品牌名未修改却受到不可重复异常)
				if(brand1!=null&&!brand1.getId().equals(id)){
					Map<String,String> validResult = new HashMap<>();
					validResult.put(WebConstant.KEY_ENTITY_NAME,ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
					return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
				}
			}
			updata.put(WebConstant.KEY_ENTITY_ID,id);
			return ResultFactory.generateRequestResult(this.brandService.updateByMapContext(updata));
		}
		return ResultFactory.generateResNotFoundResult();
	}

	@Override
	@Transactional
	public RequestResult uploadBrandImage(String brandId, MultipartFile file) {
		Brand brand = this.brandService.findById(brandId);
		if (brand == null || brand.getDisabled()) {
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
		}
		//赋值给uploadinfo,数据进行处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,file,UploadResourceType.BRAND,brandId);
		// TODO： 1.先从UploadFiles中查询是否已存在该品牌的图片
		UploadFiles uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(brandId,brandId,UploadResourceType.BRAND.getType());
		if(uploadFiles == null){
			uploadFiles = UploadFiles.create(brandId,brandId ,uploadInfo,UploadResourceType.BRAND,UploadType.FORMAL);
			this.uploadFilesService.add(uploadFiles);
		}else{
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
		mapContext.put("image",uploadInfo.getRelativePath());
		mapContext.put(WebConstant.KEY_ENTITY_ID,brandId);

		//埋点
		TSManualData tsManualData = WebUtils.getTSManualData();
		tsManualData.setClazz(Brand.class);
		tsManualData.setEvent(SystemActivityEvent.BRAND_MD.getValue());
		tsManualData.setParams(mapContext);
		tsManualData.put("brand",brand);
		tsManualData.put(WebConstant.KEY_ENTITY_ID,brand.getId());


		this.brandService.updateByMapContext(mapContext);
		Map map = new HashMap();
		map.put("file",uploadInfo.getRelativePath());
		return ResultFactory.generateRequestResult(map);
	}

	@Override
	public RequestResult findListByParams(MapContext mapContext) {
		return ResultFactory.generateRequestResult(this.brandService.findListByParams(mapContext));
	}
}
