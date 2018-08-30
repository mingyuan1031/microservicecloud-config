package com.lwxf.newstore.webapp.facade.scheme.impl;

import com.lwxf.commons.utils.DateUtil;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemePraiseService;
import com.lwxf.newstore.webapp.bizservice.scheme.SchemeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.scheme.SchemePraise;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.scheme.SchemePraiseFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 功能：
 *
 * @author：panchenxiao(Mister_pan@126.com)
 * @create：2018/8/1 14:57
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("schemePraiseFacade")
public class SchemePraiseFacadeImpl extends BaseFacadeImpl implements SchemePraiseFacade  {

	@Resource(name = "schemePraiseService")
	private SchemePraiseService schemePraiseService;
	@Resource(name = "schemeService")
	private SchemeService schemeService;


	/**
	 * 点赞
	 * @param schemeId
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult add(String schemeId) {
		SchemePraise praise = new SchemePraise();
		praise.setSchemeId(schemeId);
		praise.setCreated(DateUtil.getSystemDate());
		praise.setMemberId(WebUtils.getCurrUserId());
		this.schemePraiseService.add(praise);
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,schemeId);
		this.schemeService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(this.schemeService.findById(schemeId));
	}

	/**
	 * 取消点赞
	 * @param schemeId
	 * @return
	 */
	@Override
	@Transactional
	public RequestResult deleteBySchemeIdAndCreator(String schemeId) {
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_SCHEME_SCHEMEID,schemeId);
		mapContext.put(WebConstant.KEY_ENTITY_MEMBER_ID,WebUtils.getCurrUserId());
		this.schemePraiseService.deleteBySchemeIdAndCreator(mapContext);
		MapContext mapContext1 = MapContext.newOne();
		mapContext1.put(WebConstant.KEY_ENTITY_ID,schemeId);
		this.schemeService.updateByMapContext(mapContext1);
		return ResultFactory.generateRequestResult(this.schemeService.findById(schemeId));
	}
}
