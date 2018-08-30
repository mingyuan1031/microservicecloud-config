package com.lwxf.newstore.webapp.bizservice.user;


import java.util.List;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;


public interface UserThirdInfoService{
	PaginatedList<UserThirdInfo> selectByFilter(PaginatedFilter paginatedFilter);
	void bindingWeixin(String orgUserId, UserThirdInfo weixinInfo);

	void unbindWeixinByUserId(String userId);
	List<UserThirdInfo> findByWxOpenId(String wxOpenId);
	List<UserThirdInfo> findByWxUnionId(String wxUnionId);
	UserThirdInfo findByUserId(String userId);

	List<UserThirdInfo> findAllWithNotBlankWxOpenId();

	List<UserThirdInfo> findByUserIds(List<String> userIdList);

	void unSubscribeByWxOpenId(String wxOpenId);

	void add(UserThirdInfo userThirdInfo);

	int updateByMapContext(MapContext saveMap);

	void updateByWxOpenId(MapContext mapContext);

	List<UserThirdInfo> findClerks(List<String> strList);

	List<UserThirdInfo> findAllClerks();

	UserThirdInfo findByOpenId(String wxOpenId);

	void deleteByOppenId(String wxOppenId);

}