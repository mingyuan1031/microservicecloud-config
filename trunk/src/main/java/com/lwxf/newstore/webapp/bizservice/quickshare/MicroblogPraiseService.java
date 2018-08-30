package com.lwxf.newstore.webapp.bizservice.quickshare;


import java.util.List;
import java.util.Set;

import com.lwxf.newstore.webapp.bizservice.base.BaseNoIdService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
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
public interface MicroblogPraiseService extends BaseNoIdService<MicroblogPraise> {

	PaginatedList<MicroblogPraise> selectByFilter(PaginatedFilter paginatedFilter);

	Integer findCountByMicroblogId(String microblogId);

	List<MicroblogPraise> findMemberId(String microblogId);

	int deleteByMicroblog(String microblogId);

	int deleteByBlogId(String blogId);

	List<MicroblogPraise> findByBlogIds(Set<String> blogIds);

	List<MicroblogPraise> findByBlogId(String blogId);

	List<MicroblogPraise> findByMemberId(String memberId);

	MicroblogPraise selectByMemberIdAndMicroblogId(String microblogId, String memberId);

}