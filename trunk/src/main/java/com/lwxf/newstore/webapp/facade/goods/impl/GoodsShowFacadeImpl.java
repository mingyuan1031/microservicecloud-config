package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsShowService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsShow;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsShowFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/4/004 14:43
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsShowFacade")
public class GoodsShowFacadeImpl extends BaseFacadeImpl implements GoodsShowFacade {
	@Resource(name="goodsShowService")
	private GoodsShowService goodsShowService;
	@Resource(name="goodsService")
	private GoodsService goodsService;
	@Resource(name="uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Override
	@Transactional
	public RequestResult uploadGoodsShowFile(String id, MultipartFile file) {
		Goods goods = this.goodsService.findById(id);
		if(null==goods){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		//赋值给uploadinfo,进行数据处理
		UploadInfo uploadInfo= AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL,file,UploadResourceType.GOODSSHOW,id);
		//查询商品展示表中该商品的图片是否大于6张
		List<GoodsShow> goodsShowList= goodsShowService.findByGoodsId(id);
		if(goodsShowList.size()>=6){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_IMAGE_NUM_LIMIT_10057,AppBeanInjector.i18nUtil.getMessage("BIZ_IMAGE_NUM_LIMIT_10057"));
		}
		//生成商品展示表数据
		GoodsShow goodsShow= new GoodsShow();
		if(goodsShowList.size()==0){
			goodsShow.setDefaults(true);
		}else{
			goodsShow.setDefaults(false);
		}
		goodsShow.setGoodsId(id);
		goodsShow.setPath(uploadInfo.getRelativePath());
		goodsShowService.add(goodsShow);
		//新增uploadfiles数据
		UploadFiles uploadFiles = UploadFiles.create(goodsShow.getId(),id ,uploadInfo,UploadResourceType.GOODSSHOW,UploadType.FORMAL);
		this.uploadFilesService.add(uploadFiles);
		MapContext mapContext = MapContext.newOne();
		//更新商品的修改人和修改时间信息
		this.goodsService.updateUpdator(id);
		return ResultFactory.generateRequestResult(uploadFiles);
	}

	@Override
	public RequestResult findAll(String goodsId) {
		return ResultFactory.generateRequestResult(this.goodsShowService.findByGoodsId(goodsId));
	}

	@Override
	@Transactional
	public RequestResult setDefault(String id,String goodsId) {
		GoodsShow goodsShow = goodsShowService.findById(id);
		if(null==goodsShow){
			return ResultFactory.generateResNotFoundResult();
		}
		if(!goodsShow.getGoodsId().equals(goodsId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		Goods goods = goodsService.findById(goodsShow.getGoodsId());
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		goodsShowService.removeDefaults(goods.getId());
		goodsShowService.setDefault(id);
		//更新商品的修改人和修改时间信息
		this.goodsService.updateUpdator(goods.getId());
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id,String goodsId) {
		GoodsShow goodsShow = this.goodsShowService.findById(id);
		if(goodsShow==null){
			return ResultFactory.generateSuccessResult();
		}
		if(!goodsShow.getGoodsId().equals(goodsId)){
			Map<String,String> validResult = new HashMap<>();
			validResult.put("goodsId",ErrorCodes.VALIDATE_ILLEGAL_ARGUMENT);
			return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR,validResult);
		}
		Goods goods = this.goodsService.findById(goodsId);
		if(!goods.getDisabled()){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_GOODS_NOT_DISABLED_UPDATA_10058,AppBeanInjector.i18nUtil.getMessage("BIZ_GOODS_NOT_DISABLED_UPDATA_10058"));
		}
		this.goodsShowService.deleteById(id);
		//如果删除的默认的那一条 就删除后把查到的列表第一条设置为默认(如果还有的话)
		if(goodsShow.getDefaults()==true){
			List<GoodsShow> goodsShows= this.goodsShowService.findByGoodsId(goodsShow.getGoodsId());
			if(goodsShows!=null){
				String oneId=goodsShows.get(0).getId();
				this.goodsShowService.setDefault(oneId);
			}
		}
		//把图片资源表中的该条数据删除
		uploadFilesService.deleteByResourceId(id);
		//删除本地的资源
		AppBeanInjector.baseFileUploadComponent.deleteFileByDir(AppBeanInjector.configuration.getUploadFileRootDir().concat(goodsShow.getPath()));
		//更新商品的修改人和修改时间信息
		this.goodsService.updateUpdator(goods.getId());
		return ResultFactory.generateSuccessResult();
	}
}
