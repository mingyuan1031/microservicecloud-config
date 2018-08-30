package com.lwxf.newstore.webapp.bizservice.quickshare;


import java.util.List;
import java.util.Set;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.base.BaseService;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogComment;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:25
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface MicroblogCommentService extends BaseService <MicroblogComment, String> {

	PaginatedList<MicroblogComment> selectByFilter(PaginatedFilter paginatedFilter);

	Integer findCountByMicroblogId(String microblogId);

	List<MicroblogComment> selectMicroblogCommentByBlogId(String blogId);

	List<MicroblogComment> selectMicroblogCommentByBlogIds(Set<String> blogIds);

	int deleteMicroblogCommentByBlogId(String blogId);

	List<MicroblogComment> findByParentIds(List<String> parentIds);

	List<MicroblogComment> findByParentId(String parentId);

	int updateByParentId(String parentId);

}