package com.lwxf.newstore.webapp.domain.dao.quickshare;


import java.util.List;
import java.util.Map;
import java.util.Set;


import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.quickshare.Microblog;


/**
 * 功能：
 * 
 * @author：wangmingyuan(wangmingyuan@126.com)
 * @created：2018-07-02 14:59:24
 * @version：2018 V1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@IBatisSqlTarget
public interface MicroblogDao extends BaseDao<Microblog, String> {

	PaginatedList<Microblog> selectByFilter(PaginatedFilter paginatedFilter);

	/**
	 * 获取普通快享帖子列表
	 * @param paginatedFilter
	 * @return
	 */
	PaginatedList<Microblog> findByType(PaginatedFilter paginatedFilter);

	/**
	 * 根据ID查找帖子信息
	 * @param id
	 * @return
	 */
	Microblog findById(String id);

	/**
	 * 删除快享帖子
	 * @param map
	 * @return
	 */
	int deleteByIdAndUserId(Map<String,String> map);

	/**
	 * 计算状态为启用的快享帖子总数
	 * @return
	 */
	Integer findCountByStatus();

	/**
	 * 查询当前用户的快享帖子
	 * @param paginatedFilter
	 * @return
	 */
	PaginatedList<Microblog> findMicroblogByMemberId(PaginatedFilter paginatedFilter);


}