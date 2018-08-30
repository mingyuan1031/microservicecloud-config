package com.lwxf.newstore.webapp.bizservice.goods.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.commons.utils.DateUtil;
import com.lwxf.newstore.webapp.bizservice.goods.BrandService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsExtendService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsService;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsDefault;
import com.lwxf.newstore.webapp.domain.entity.goods.Brand;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsExtend;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsType;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsService")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, String, GoodsDao> implements GoodsService {


	@Resource
	@Override	public void setDao( GoodsDao goodsDao) {
		this.dao = goodsDao;
	}

	@Resource(name = "goodsTypeService")
	private GoodsTypeService goodsTypeService;

	@Resource(name="goodsExtendService")
	private GoodsExtendService goodsExtendService;

	@Resource(name = "brandService")
	private BrandService brandService;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<Goods> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public PaginatedList<GoodsDefault> findGoodsDefaultByParamsForPaging(PaginatedFilter paginatedFilter) {
		String goodsTypeId = (String) paginatedFilter.getFilters().get("goodsTypeId");
		String goodsTypeName = (String) paginatedFilter.getFilters().get("goodsTypeName");
		String brandName = (String) paginatedFilter.getFilters().get("brandName");
		List goodsTypes = new ArrayList();
		if(goodsTypeId!=null){
			goodsTypes.add(goodsTypeId);
			GoodsType goodsType  = this.goodsTypeService.findById(goodsTypeId);
			if(goodsType!=null&&goodsType.getParentId()==null){
				List<GoodsType> goodsTypeList= this.goodsTypeService.findByParentId(goodsTypeId);
				for (int i = 0; i <goodsTypeList.size() ; i++) {
					goodsTypes.add(goodsTypeList.get(i).getId());
				}
			}
			paginatedFilter.getFilters().put("goodsTypeId",goodsTypes);
		}
		if(goodsTypeName!=null){
			GoodsType goodsType= this.goodsTypeService.findByName(goodsTypeName);
			goodsTypes.add(goodsType.getId());
			if(goodsType!=null&&goodsType.getParentId()==null){
				List<GoodsType> goodsTypeList= this.goodsTypeService.findByParentId(goodsTypeId);
				for (int i = 0; i <goodsTypeList.size() ; i++) {
					goodsTypes.add(goodsTypeList.get(i).getId());
				}
			}
			paginatedFilter.getFilters().put("goodsTypeId",goodsTypes);
		}
		if(brandName!=null){
			List<Brand> brandList = this.brandService.findLikeName(brandName);
			paginatedFilter.getFilters().put("brandNameIds",brandList);
		}
		return this.dao.findGoodsDefaultByParamsForPaging(paginatedFilter);
	}

	@Override
	public Boolean isExistByBrandId(String brandId) {
		return this.dao.isExistByBrandId(brandId);
	}

	@Override
	public Boolean isExistByGoodsTypeId(String goodsTypeId) {
		return this.dao.isExistByGoodsTypeId(goodsTypeId);
	}

	@Override
	public List<GoodsDefault> findForHomePage() {
		return this.dao.findForHomePage();
	}

	@Override
	public GoodsDefault findGoodsInfo(String id) {
		GoodsDefault goodsDefault= this.dao.findGoodsInfo(id);
		Goods goods= this.dao.selectById(id);
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,id);
		mapContext.put("views",goods.getViews()+1);
		this.dao.updateByMapContext(mapContext);
		return goodsDefault;
	}

	@Override
	public int updateUpdator(String goodsId) {
		MapContext mapContext = MapContext.newOne();
		mapContext.put(WebConstant.KEY_ENTITY_ID,goodsId);
		mapContext.put("updator",WebUtils.getCurrUserId());
		mapContext.put("updated",DateUtil.getSystemDate());
		return this.dao.updateUpdator(mapContext);
	}

	@Override
	public int updataStock(String extendsId) {
		GoodsExtend goodsExtend= this.goodsExtendService.findById(extendsId);
		if(goodsExtend.getStock()==0){//库存为0
			return 0;
		}
		MapContext mapContext = new MapContext();
		mapContext.put("stock",goodsExtend.getStock()-1);
		mapContext.put("sales",goodsExtend.getSales()+1);
		mapContext.put(WebConstant.KEY_ENTITY_ID,extendsId);
		return this.goodsExtendService.updateByMapContext(mapContext);
	}


}