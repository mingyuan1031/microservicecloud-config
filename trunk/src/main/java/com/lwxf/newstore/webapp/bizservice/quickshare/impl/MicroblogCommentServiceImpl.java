package com.lwxf.newstore.webapp.bizservice.quickshare.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogCommentDao;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogCommentService;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:25
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("microblogCommentService")
public class MicroblogCommentServiceImpl extends BaseServiceImpl<MicroblogComment, String, MicroblogCommentDao> implements MicroblogCommentService {


	@Resource

	@Override	public void setDao( MicroblogCommentDao microblogCommentDao) {
		this.dao = microblogCommentDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<MicroblogComment> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public Integer findCountByMicroblogId(String microblogId) {

		return this.dao.findCountByMicroblogId(microblogId);
	}

	/**
	 * 通过博客ID查询快享评论
	 * @param blogId
	 * @return
	 */
	@Override
	public List<MicroblogComment> selectMicroblogCommentByBlogId(String blogId) {
		return this.dao.selectMicroblogCommentByBlogId(blogId);
	}

	public List<MicroblogComment> selectMicroblogCommentByBlogIds(Set<String> blogIds) {
		return this.dao.selectMicroblogCommentByBlogIds(blogIds);
	}

	@Override
	public int deleteMicroblogCommentByBlogId(String blogId) {
		return this.dao.deleteMicroblogCommentByBlogId(blogId);
	}

	@Override
	public List<MicroblogComment> findByParentIds(List<String> parentIds) {
		return this.dao.findByParentIds(parentIds);
	}

	@Override
	public List<MicroblogComment> findByParentId(String parentId) {
		return this.dao.findByParentId(parentId);
	}

	@Override
	public int updateByParentId(String parentId) {
		return this.dao.updateByParentId(parentId);
	}
}