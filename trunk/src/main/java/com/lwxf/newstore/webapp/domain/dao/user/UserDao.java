package com.lwxf.newstore.webapp.domain.dao.user;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lwxf.mybatis.annotation.IBatisSqlTarget;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.domain.dao.base.BaseDao;
import com.lwxf.newstore.webapp.domain.dto.user.UserDto;
import com.lwxf.newstore.webapp.domain.entity.quickshare.MicroblogPraise;
import com.lwxf.newstore.webapp.domain.entity.user.User;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:43:14
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
//@Mapper
@IBatisSqlTarget
public interface UserDao extends BaseDao<User, String> {
	/**
     * 根据用户邮箱返回用户信息
     *
     * @param email
     * @return
     */
    User selectByEmail(String email);

	/**
	 * 根据用户手机号查用户对象
	 *
	 * @param mobile
	 * @return
	 */
	User findByMobile(String mobile);

    PaginatedList<User> selectByFilter(PaginatedFilter paginatedFilter);

    /**
     * 根据一组用户标识返回用户列表(id,name)
     *
     * @param userIds
     * @return
     */
	List<UserDto> selectUserDtoListByUserIds(String[] userIds);

    /**
     * 根据一组用户标识返回用户列表(user)
     *
     * @param userIdList
     * @return
     */
	List<User> selectByUserIdList(List<String> userIdList);

    /**
     * 检查邮箱存不在不存
     *
     * @param email
     * @return
     */
    boolean isExistsByEmail(String email);

    /**
     * 根据用户标识返回当前用户信息(结构为map)
     *
     * @param userId
     * @return
     */
	Map<String, Object> selectByUserId(String userId);

	/**
	 * 查询所有用户id
	 *
	 * @return
	 */
	List<String> selectAllUserId();

	/**
	 * 根据用户名返回当前用户信息
	 *
	 * @param username
	 * @return
	 */
	User findByUserName(String username);

	/**
	 * 根据用户的启用状态和角色查询用户集合
	 * @param mapContext
	 * @return
	 */
	List<User> findClerkListByStateAndRole(MapContext mapContext);

	/**
	 * 根据用户的Id查询用户的信息
	 * @param id 多个用户
	 * @return
	 */
	List<User> findUserInfoByUserIds(List<String> id);

	int updateUserName(MapContext mapContext);

	Map<String, Object> findUserById(String id);

	List<String> findIdByName(String name);

}
