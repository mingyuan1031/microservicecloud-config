package com.lwxf.newstore.webapp.facade.user;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:57:38
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface UserFacade extends BaseFacade {

	User findUserById(String id);

    RequestResult findUserByFilter(Integer pageNum,Integer pageSize,MapContext params);

    RequestResult addUser(User user,MapContext codeMap);

    RequestResult updateUser(MapContext user);

	/**
	 * 修改state
	 * @param id
	 * @param update
	 * @return
	 */
	RequestResult updateUserState(String id,MapContext update);
    /**
     * 激活用户，设置密码，删除原邮件。
     *
     * @return
     */

	RequestResult updateUserAvatar(String userId, MapContext userMap, MultipartFile file);

	RequestResult updateUserBackground(String userId, MultipartFile file);

	RequestResult deleteUserBackground(String userId);

	boolean deleteUserById(String id);

    User findByEmail(String email);

	/**
	 * 根据手机号查用户信息
	 * @param mobile
	 * @return
	 */
	User findByMobile(String mobile);

    Boolean isPasswordCorrect(String loginName, String inputPassword);

	Collection<String> getUserHasPermissionsById(String id);

    RequestResult toLogin(MapContext userMap, HttpServletRequest request);

    Boolean doLogin(User user, String inputPassword,boolean remberMe);

	UserExtra findUserExtraByUserId(String userId);

	RequestResult updateUserPassword(String userId, String password);

    /**
     * 获取用户预加载数据
     *
     * @param userId
     * @return
     */
	RequestResult findUserPreloadData(String userId);

    /**
     * 查询返回系统所有用户
     *
     * @return
     */
    List<User> findAll();

    /**
     * 重发激活邮件
     *
     * @param user
     * @return
     */
    RequestResult resendEmail(User user);

	/**
	 * 用户密码授权
	 * @param password
	 * @return
	 */
	RequestResult passwordAccredit(String password);

	/**
	 * 用户手机注册
	 * @param user
	 * @param registerMapContext
	 * @return
	 */
	RequestResult userMobileRegister(User user,MapContext registerMapContext);

	/**
	 * 用户邮箱注册
	 * @param user
	 * @param registerMapContext
	 * @param codeMap
	 * @return
	 */
	RequestResult userEmailRegister(User user,MapContext registerMapContext,MapContext codeMap);

	/**
	 * 查询用户密码是否为空
	 * @return
	 */
	RequestResult getPasswordState();

	RequestResult findUserInfoByUserIds(List<String> id);
}
