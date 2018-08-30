package com.lwxf.newstore.webapp.domain.dao.user.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lwxf.mybatis.utils.MapContext;
import org.springframework.stereotype.Component;

import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDaoImpl;
import com.lwxf.newstore.webapp.domain.dao.user.UserDao;
import com.lwxf.newstore.webapp.domain.dto.user.UserDto;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 功能：缓存示例实现
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:03:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
	@Override
    public User selectByEmail(String email) {
        String sqlId = this.getNamedSqlId("selectByEmail");
        return this.getSqlSession().selectOne(sqlId, email);
    }

	@Override
	public User findByMobile(String mobile) {
		String sqlId = this.getNamedSqlId("findByMobile");
		return this.getSqlSession().selectOne(sqlId, mobile);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public PaginatedList<User> selectByFilter(PaginatedFilter paginatedFilter) {
        String sqlId = this.getNamedSqlId("selectByFilter");
        PageBounds pageBounds = this.toPageBounds(paginatedFilter.getPagination(), paginatedFilter.getSorts());
        PageList<User> pageList = (PageList) this.getSqlSession().selectList(sqlId, paginatedFilter.getFilters(), pageBounds);
        return this.toPaginatedList(pageList);
    }

    @Override
	public List<User> selectByUserIdList(List<String> userIdList) {
		String sqlId = this.getNamedSqlId("selectByUserIdList");
        return this.getSqlSession().selectList(sqlId, userIdList);
    }

    @Override
	public List<UserDto> selectUserDtoListByUserIds(String[] userIds) {
		String sqlId = this.getNamedSqlId("selectUserDtoListByUserIds");
        return this.getSqlSession().selectList(sqlId, userIds);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        String sqlId = this.getNamedSqlId("isExistsByEmail");
        return this.getSqlSession().selectOne(sqlId, email);
    }

    @Override
	public Map<String, Object> selectByUserId(String userId) {
		String sqlId = this.getNamedSqlId("selectByUserId");
        return this.getSqlSession().selectOne(sqlId, userId);
    }

    @Override
	public List<String> selectAllUserId() {
		String sqlId = this.getNamedSqlId("selectAllUserId");
		return this.getSqlSession().selectList(sqlId);
	}

	@Override
	public User findByUserName(String username) {
		String sqlId = this.getNamedSqlId("findByUserName");
		return this.getSqlSession().selectOne(sqlId,username);
	}
    @Override
    public List<User> findClerkListByStateAndRole(MapContext mapContext) {
        String sqlId = this.getNamedSqlId("findClerkListByStateAndRole");
        return this.getSqlSession().selectList(sqlId,mapContext);
    }

	@Override
	public List<User> findUserInfoByUserIds(List<String> id) {
		String sqlId = this.getNamedSqlId("findUserInfoByUserIds");
		return this.getSqlSession().selectList(sqlId,id);
	}

	@Override
	public int updateUserName(MapContext mapContext) {
		String sqlId = this.getNamedSqlId("updateUserName");
		return this.getSqlSession().update(sqlId, mapContext);
	}

	@Override
	public Map<String, Object> findUserById(String id) {
		String sqlId = this.getNamedSqlId("findUserById");
		return this.getSqlSession().selectOne(sqlId,id);
	}

	@Override
	public List<String> findIdByName(String name) {
		String sqlId = this.getNamedSqlId("findIdByName");
		return this.getSqlSession().selectList(sqlId, name);
	}
}
