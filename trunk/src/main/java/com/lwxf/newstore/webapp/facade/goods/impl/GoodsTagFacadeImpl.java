package com.lwxf.newstore.webapp.facade.goods.impl;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTagService;
import com.lwxf.newstore.webapp.bizservice.goods.TagService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.exceptions.ErrorCodes;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsTag;
import com.lwxf.newstore.webapp.domain.entity.goods.Tag;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.goods.GoodsTagFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/13/013 16:13
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("goodsTagFacade")
public class GoodsTagFacadeImpl extends BaseFacadeImpl implements GoodsTagFacade {
	@Resource(name = "goodsTagService")
	private GoodsTagService goodsTagService;
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	@Resource(name = "tagService")
	private TagService tagService;

	@Override
	@Transactional
	public RequestResult addGoodsTag(String goodsId, String[] tags) {
		if (!this.goodsService.isExist(goodsId)){
			return ResultFactory.generateResNotFoundResult();
		}
		if(tags==null||tags.length==0){
			this.goodsTagService.deleteByGoodsId(goodsId);
			return ResultFactory.generateSuccessResult();
		}
		for(int i=0;i<tags.length;i++){
			Tag tag=this.tagService.findById(tags[i]);
			if(null==tag){
				return ResultFactory.generateResNotFoundResult();
			}else if(tag.getDisabled()){
				return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_DISABLED_10011,AppBeanInjector.i18nUtil.getMessage("BIZ_RES_DISABLED_10011"));
			}
		}
		this.goodsTagService.deleteByGoodsId(goodsId);
		for (int i = 0; i <tags.length ; i++) {
			GoodsTag goodsTag = new GoodsTag();
			goodsTag.setGoodsId(goodsId);
			goodsTag.setTagId(tags[i]);
			this.goodsTagService.add(goodsTag);
		}
		return ResultFactory.generateSuccessResult();
	}

	@Override
	@Transactional
	public RequestResult deleteGoodsTag(String goodsId, String tagId) {
		this.goodsTagService.deleteByGoodsIdAndTagId(goodsId,tagId);
		return ResultFactory.generateSuccessResult();
	}

	@Override
	public RequestResult findByGoodsId(String goodsId) {
		return ResultFactory.generateRequestResult(this.goodsTagService.findByGoodsId(goodsId));
	}

}
