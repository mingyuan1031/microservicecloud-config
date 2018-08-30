package com.lwxf.newstore.webapp.domain.dao.user;


import java.util.List;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;


@IBatisSqlTarget
public interface UserThirdInfoDao extends BaseDao<UserThirdInfo, String> {

	List<UserThirdInfo> selectByWxOpenId(String wxOpenId);

	List<String> selectUserIdsBysWxOpenId(String wxOpenId);

	List<UserThirdInfo> selectByWxUnionId(String wxUnionId);

	PaginatedList<UserThirdInfo> selectByFilter(PaginatedFilter paginatedFilter);

	void deleteByOpenId(String wxOpenId);

	void deleteByUserId(String userId);

	List<UserThirdInfo> findAllWithNotBlankWxOpenId();

	List<UserThirdInfo> findByUserIds(List<String> userIdList);

	UserThirdInfo selectByUserId(String userId);

	void updateByWxOpenId(MapContext mapContext);

	List <UserThirdInfo> findAllClerks();

	UserThirdInfo selectByOpenId(String wxOpenId);

}