package com.lwxf.newstore.webapp.bizservice.quickshare.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.stereotype.Component;


import com.lwxf.newstore.webapp.bizservice.base.BaseNoIdServiceImpl;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dao.quickshare.MicroblogPraiseDao;
import com.lwxf.newstore.webapp.bizservice.quickshare.MicroblogPraiseService;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.domain.entity.user.User;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:25
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("microblogPraiseService")
public class MicroblogPraiseServiceImpl extends BaseNoIdServiceImpl<MicroblogPraise, MicroblogPraiseDao> implements MicroblogPraiseService {
	@Resource

	@Override	public void setDao( MicroblogPraiseDao microblogPraiseDao) {
		this.dao = microblogPraiseDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList<MicroblogPraise> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.dao.selectByFilter(paginatedFilter) ;
	}

	@Override
	public Integer findCountByMicroblogId(String microblogId) {
		return this.dao.findCountByMicroblogId(microblogId);
	}


	@Override
	public int deleteByBlogId(String blogId) {
		return  this.dao.deleteByBlogId(blogId);
	}

	@Override
	public List<MicroblogPraise> findMemberId(String microblogId) {
		MapContext mapContext = MapContext.newOne();
		mapContext.put("microblogId", microblogId);
		mapContext.put("memberId", WebUtils.getCurrUserId());
		return this.dao.findMemberId(mapContext);
	}

	@Override
	public int deleteByMicroblog(String microblogId) {
		MapContext mapContext = MapContext.newOne();
		mapContext.put("microblogId", microblogId);
		mapContext.put("memberId", WebUtils.getCurrUserId());
		return this.dao.deleteByMapContext(mapContext);
	}

	@Override
	public List<MicroblogPraise> findByBlogIds(Set<String> blogIds) {
		return this.dao.findByBlogIds(blogIds);
	}

	@Override
	public List<MicroblogPraise> findByMemberId(String memberId) {
		return this.dao.findByMemberId(memberId);
	}

	@Override
	public MicroblogPraise selectByMemberIdAndMicroblogId(String microblogId, String memberId) {
		return this.dao.selectByMemberIdAndMicroblogId(memberId, microblogId);
	}

	@Override
	public List<MicroblogPraise> findByBlogId(String blogId) {
		Set<String> blogIds = new HashSet<>();
		blogIds.add(blogId);
		return this.dao.findByBlogIds(blogIds);
	}
}