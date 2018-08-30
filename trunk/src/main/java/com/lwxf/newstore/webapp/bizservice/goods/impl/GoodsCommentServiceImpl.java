package com.lwxf.newstore.webapp.bizservice.goods.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.goods.GoodsCommentDao;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsCommentService;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsComments;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;


/**
 * 功能：
 * 
 * @author：renzhongshan(d3shan@126.com)
 * @created：2018-06-15 20:58:43
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("goodsCommentService")
public class GoodsCommentServiceImpl extends BaseServiceImpl<GoodsComment, String, GoodsCommentDao> implements GoodsCommentService {


	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;

	@Resource
	@Override
	public void setDao( GoodsCommentDao goodsCommentDao) {
		this.dao = goodsCommentDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<GoodsComment> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}



	@Override
	public PaginatedList<GoodsComments> findByMapper(PaginatedFilter paginatedFilter) {
		return this.dao.findByMapper(paginatedFilter);
	}

	@Override
	public int deleteById(GoodsComment goodsComment) {
		if(goodsComment.getAnswered()!=null&&goodsComment.getAnswered()==true){
			this.dao.deleteByParentId(goodsComment.getId());
		}
		return this.dao.deleteById(goodsComment.getId());
	}

	@Override
	public Map<String, Object> findCountById(String id) {
		return this.dao.findCountById(id);
	}

}