package com.lwxf.newstore.webapp.bizservice.user.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Service;

import com.lwxf.newstore.webapp.bizservice.base.BaseServiceImpl;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.dao.user.UserDao;
import com.lwxf.newstore.webapp.domain.dto.user.UserDto;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：user的service实现
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:45:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String, UserDao> implements UserService {
	@Override
	@Resource(name = "userDao")
	public void setDao(UserDao userDao) {
		this.dao = userDao;
	}

    @Override
    public User findByEmail(String email) {
		return this.dao.selectByEmail(email);
	}

	@Override
	public User findByMobile(String mobile) {
		return this.dao.findByMobile(mobile);
	}

	@Override
    public PaginatedList<User> findByFilter(PaginatedFilter paginatedFilter) {
		return this.dao.selectByFilter(paginatedFilter);
	}

	@Override
	public Map<String, Object> findByUserId(String userId) {
		return this.dao.selectByUserId(userId);
	}

	@Override
	public List<UserDto> findUserDtoListByUserIdList(String[] userIdList) {
		return this.dao.selectUserDtoListByUserIds(userIdList);
	}

	@Override
	public boolean isExistsByEmail(String email) {
		return this.dao.isExistsByEmail(email);
	}

	@Override
	public List<String> findAllUserId() {
		return this.dao.selectAllUserId();
	}

	@Override
	public User findByUserName(String username) {
		return this.dao.findByUserName(username);
	}
	@Override
	public List<User> findClerkListByStateAndRole(MapContext mapContext){
		return this.dao.findClerkListByStateAndRole(mapContext);
	}

	@Override
	public List<User> findUserInfoByUserIds(List<String> id) {
		return this.dao.findUserInfoByUserIds(id);
	}

	@Override
	public int updateUserName(String username) {
		MapContext mapContext = MapContext.newOne();
		mapContext.put("id", WebUtils.getCurrUserId());
		mapContext.put("username", username);
		return this.dao.updateUserName(mapContext);
	}

	@Override
	public Map<String, Object> findUserById(String id) {
		return this.dao.findUserById(id);
	}

	@Override
	public List<String> findIdByName(String name) {
		return this.dao.findIdByName(name);
	}

	@Override
	public int add(User entity) {
		if(LwxfStringUtils.isBlank(entity.getName())){
			entity.setName("我是谁？");
		}
		return super.add(entity);
	}
}
