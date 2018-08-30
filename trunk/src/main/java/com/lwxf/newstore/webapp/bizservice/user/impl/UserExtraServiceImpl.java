package com.lwxf.newstore.webapp.bizservice.user.impl;


import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.shiro.LwxfShiroRealm;
import com.lwxf.newstore.webapp.domain.dao.user.UserExtraDao;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;


@Service("userExtraService")
public class UserExtraServiceImpl implements UserExtraService {
	@Resource(name = "userExtraDao")
	private UserExtraDao userExtraDao;

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public PaginatedList<UserExtra> selectByFilter(PaginatedFilter paginatedFilter) {
		//
		return this.userExtraDao.selectByFilter(paginatedFilter);
	}

	@Override
	public int add(UserExtra userExtra) {
		return this.userExtraDao.insert(userExtra);
	}

	@Override
	public UserExtra findById(String userId) {
		return this.userExtraDao.selectById(userId);
	}

	@Override
	public int updateByMapContext(MapContext mapContext) {
		return this.userExtraDao.updateByMapContext(mapContext);
	}

	@Override
	public int updateUserExtra(UserExtra userExtra) {
		return this.userExtraDao.updateUserExtra(userExtra);
	}

	@Override
	public Boolean isPasswordCorrect(String userId, String inputPassword) {
		if(LwxfStringUtils.isBlank(inputPassword)){
			return false;
		}
		UserExtra userExtra = this.userExtraDao.selectById(userId);
		if(null == userExtra){
			return false;
		}
		String salt = userExtra.getSalt();
		String oldPassword = userExtra.getPassword();
		if(oldPassword== null)
			return  false;
		String newPassword = new SimpleHash(LwxfShiroRealm.HASH_ALGORITHM, inputPassword, salt, LwxfShiroRealm.HASH_INTERATIONS).toBase64();
		return newPassword.equals(oldPassword);
	}

}