package com.lwxf.newstore.webapp.facade.user.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.util.SavedRequest;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.security.attacklocker.IAttackLocker;
import com.lwxf.commons.security.attacklocker.impl.AttackLockerInfo;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.commons.utils.ValidateUtils;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.goods.BrandService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsSpecService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTagService;
import com.lwxf.newstore.webapp.bizservice.goods.GoodsTypeService;
import com.lwxf.newstore.webapp.bizservice.goods.SpecOptionService;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.bizservice.user.UserResetpassService;
import com.lwxf.newstore.webapp.bizservice.user.UserService;
import com.lwxf.newstore.webapp.bizservice.user.UserThirdInfoService;
import com.lwxf.newstore.webapp.common.component.BaseFileUploadComponent;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.constant.BizConstant;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.permission.PermissionIndex;
import com.lwxf.newstore.webapp.common.enums.user.UserRole;
import com.lwxf.newstore.webapp.common.enums.user.UserState;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.model.PaginatedFilter;
import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.shiro.LwxfShiroRealm;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.UserExtraUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;
import com.lwxf.newstore.webapp.domain.entity.user.UserThirdInfo;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.user.UserFacade;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.i18nUtil;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:58:17
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("userFacade")
public class UserFacadeImpl extends BaseFacadeImpl implements UserFacade {
    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);
    @Resource
    private UserService userService;
    @Resource(name = "userExtraService")
    private UserExtraService userExtraService;
    @Resource(name = "userResetpassService")
    transient private UserResetpassService userResetpassService;
    @Resource
	private BaseFileUploadComponent baseFileUploadComponent;
    @Resource(name = "rememberMeManager")
    private CookieRememberMeManager rememberMeManager;
    @Resource(name = "userThirdInfoService")
    private UserThirdInfoService userThirdInfoService;
    //增加锁ip登录用户
    @Autowired
    @Qualifier("loginAttackLocker")
    protected IAttackLocker loginAttackLocker;

    @Resource(name = "ipAttackLocker")
    protected IAttackLocker ipAttackLocker;

    @Resource(name = "goodsTagService")
    private GoodsTagService goodsTagService;
    @Resource(name = "goodsTypeService")
    private GoodsTypeService goodsTypeService;
    @Resource(name = "goodsSpecService")
    private GoodsSpecService goodsSpecService;
    @Resource(name = "specOptionService")
    private SpecOptionService specOptionService;
    @Resource(name = "brandService")
    private BrandService brandService;
    @Resource(name = "systemConfigService")
    private SystemConfigService systemConfigService;
    @Resource(name = "storeConfigService")
    private StoreConfigService storeConfigService;
    /*@Resource
    private RabbitMQService rabbitMQService;*/

    public static void main(String[] args) {
        String newPassword = "dddddd";
        String md5Password = new Md5Hash(newPassword).toString();
        System.out.println("md5Password:" + md5Password);
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		String salt = rng.nextBytes().toBase64();
		String hashedPasswordBase64 = new SimpleHash(LwxfShiroRealm.HASH_ALGORITHM, newPassword, salt, LwxfShiroRealm.HASH_INTERATIONS)
                .toBase64();
        System.out.println(salt);
        System.out.println(hashedPasswordBase64);
    }

    @Override
    public User findUserById(String id) {
        return this.userService.findById(id);
    }

    @Override
    public RequestResult findUserByFilter(Integer pageNum,Integer pageSize,MapContext params) {

        PaginatedFilter filter = PaginatedFilter.newOne();
        filter.setFilters(params);
        Pagination pagination = Pagination.newOne();
        pagination.setPageNum(pageNum);
        pagination.setPageSize(pageSize);
        filter.setPagination(pagination);
		PaginatedList<User> list = this.userService.findByFilter(filter);
		return ResultFactory.generateRequestResult(list);
	}

    /**
     * 邮箱注册：todo：其他注册方式调用该方法需要相应处理
     * @param user
     * @param codeMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult addUser(User user,MapContext codeMap) {
        Map<String, Object> errMap = new HashMap<>();
        //埋数据发送邮件使用
        TSManualData tsManualData = WebUtils.getTSManualData();
        tsManualData.setClazz(User.class);
        tsManualData.setParams(user);
        //激活账号事件：直接与发邮件一一对应
        tsManualData.setEvent(JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL);
        //如果该邮箱未注册用户，则新建未激活用户
        User userOld = userService.findByEmail(user.getEmail());
        if (userOld != null) {
            //判断是否激活:如果已激活，返回前台；如果未激活，返回账户未激活。
            byte state = userOld.getState();
            //邮箱已激活
            if (state == UserState.ENABLED.getValue()) {
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_HAVE_EXISTED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            } else if (state == UserState.DISABLED.getValue()) {
                //邮箱已被：禁用
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_IS_DISABLED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            }
        } else {
            user = UserExtraUtil.userNewOne(user.getEmail());
            user.setState(UserState.ENABLED.getValue());
            userService.add(user);
            //埋了一个用户对象
            tsManualData.put("user", user);
        }
        UserResetpass userResetpass = userResetpassService.findByEmail(user.getEmail());
        if (userResetpass == null) {
            userResetpass = new UserResetpass();
            userResetpass.setCreated(DateUtil.getSystemDate());
            userResetpass.setEmail(user.getEmail());
            //埋了一个对象:为了发送注册激活邮件
            tsManualData.put("userResetpass", userResetpass);
            tsManualData.put("codeMap",codeMap);
            userResetpassService.add(userResetpass);
        } else {
            userResetpass.setCreated(DateUtil.getSystemDate());
            //埋了一个对象:为了发送注册激活邮件
            tsManualData.put("userResetpass", userResetpass);
            tsManualData.put("codeMap",codeMap);
            userResetpassService.updateUserResetpass(userResetpass);
        }

        return ResultFactory.generateRequestResult(user.getId()==null?userOld.getId():user.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult updateUser(MapContext user) {
        String mobile = (String)user.get("mobile");
        String username = (String)user.get("username");
        String currUserId = WebUtils.getCurrUserId();
        if (null != mobile && mobile != "") {
            String s = "(^(\\d{3,4}-)?\\d{7,8})$|(1[3|5|7|8|9]\\d{9})";
            Pattern p=Pattern.compile(s);
            Matcher matcher=p.matcher(mobile);
            if(!matcher.matches()){
                return ResultFactory.generateResNotFoundResult();
            }
            User existUser = this.userService.findByMobile(mobile);
            if(null != existUser && !existUser.getId().equals(currUserId)){
                return ResultFactory.generateResNotFoundResult();
            }
        }
        if(username != null) {
            // 当前用户的那条信息
            User existUserCurr = WebUtils.getCurrUser();
            // 修改的用户名的那条信息(不一定存在)
            User existUserName = this.userService.findByUserName(username);
            // 修改的那条信息在userThirdInfo中的数据
            UserThirdInfo userThirdInfo = this.userThirdInfoService.findByUserId(currUserId);
            // 1. 查看当前用户是否是第一次修改用户名
            if(null != userThirdInfo && !userThirdInfo.getWxOpenId().equals(existUserCurr.getUsername())){
                return ResultFactory.generateResNotFoundResult();
            }
            // 2. 这个username是否存在并且是否是这个人的
            if(null != existUserName && !existUserName.getId().equals(currUserId)){
                return ResultFactory.generateResNotFoundResult();
            }
            // 3. 验证权限范围
            if(existUserCurr.getRole().intValue() == UserRole.MEMBER.getValue() || existUserCurr.getRole().intValue() == UserRole.CLERK.getValue()){
                if(!existUserCurr.getId().equals(currUserId)){
                    return ResultFactory.generateErrorResult(com.lwxf.newstore.webapp.common.exceptions.ErrorCodes.BIZ_NO_PERMISSION_10003,AppBeanInjector.i18nUtil.getMessage("BIZ_NO_PERMISSION_10003"));
                }
            }
            user.put("username", username);
        }
        user.put(WebConstant.KEY_ENTITY_ID,currUserId);
        this.userService.updateByMapContext(user);
        return ResultFactory.generateSuccessResult();
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult updateUserAvatar(String userId, MapContext userMap, MultipartFile file) {
        User user = userService.findById(userId);
        if (user==null) {
            String errMsg = i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001");
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, errMsg);
		}
        UploadInfo uploadInfo = baseFileUploadComponent.doUploadByModule(file, UploadResourceType.AVATAR, userId);
        userMap.put("avatar", uploadInfo.getRelativePath());
        try {
            //判断是系统新的头像地址进行删除
            if(user.getAvatar().startsWith(BizConstant.EASPYM4_FILE_DOMAIN) && !user.getAvatar().equals(AppBeanInjector.configuration.getUserDefaultAvatar())){
                baseFileUploadComponent.deleteFile(user.getAvatar(), UploadResourceType.AVATAR,0);
            }
            this.userService.updateByMapContext(userMap);
        } catch (RuntimeException ex) {
            logger.error("更新头像失败", ex);
            baseFileUploadComponent.deleteFile(uploadInfo.getRelativePath(), UploadResourceType.AVATAR,0);
			throw ex;
        }
        userMap.remove("id");
        return ResultFactory.generateRequestResult(userMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RequestResult updateUserBackground(String userId, MultipartFile file) {
        User user = userService.findById(userId);
        if (user == null) {
            String errMsg = i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001");
            return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, errMsg);
        }
        UploadInfo uploadInfo = baseFileUploadComponent.doUploadByModule(file, UploadResourceType.BACKGROUND, userId);
        MapContext mapContext = MapContext.newOne();
        mapContext.put("id", userId);
        mapContext.put("background", uploadInfo.getRelativePath());
        userService.updateByMapContext(mapContext);
        if (!LwxfStringUtils.isEmpty(user.getBackground())) {
			baseFileUploadComponent.deleteFile(user.getBackground(), UploadResourceType.BACKGROUND,0);
		}
        return ResultFactory.generateRequestResult(mapContext);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RequestResult deleteUserBackground(String userId) {
        User user = userService.findById(userId);
        MapContext mapContext = new MapContext();
        mapContext.put("id", userId);
        mapContext.put("background", "");
        userService.updateByMapContext(mapContext);
        baseFileUploadComponent.deleteFile(user.getBackground(), UploadResourceType.BACKGROUND,0);
        return ResultFactory.generateSuccessResult();
    }

    @Override
    public boolean deleteUserById(String id) {
        return this.userService.deleteById(id) > 0;
    }

    @Override
    public User findByEmail(String email) {
        return this.userService.findByEmail(email);
    }

    @Override
    public User findByMobile(String mobile) {
        return this.userService.findByMobile(mobile);
    }

    @Override
    public Boolean isPasswordCorrect(String email, String inputPassword) {
        User user = this.findByEmail(email);
        if(user == null){
            return false;
        }
        return this.userExtraService.isPasswordCorrect(user.getId(),inputPassword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult toLogin(MapContext userMap, HttpServletRequest request) {
        String loginName = userMap.getTypedValue("loginName", String.class);
        boolean isEmail = false;
        boolean isMobile = false;
        if(ValidateUtils.isEmail(loginName)){
            isEmail = true;
        }
        if(ValidateUtils.isChinaPhoneNumber(loginName)){
            isMobile = true;
        }
        String password = userMap.getTypedValue("password", String.class);
        Boolean rememberMe = userMap.getTypedValue("rememberMe",Boolean.class);
        // 默认为记住我
        if(null == rememberMe){
            rememberMe = true;
        }
        //判断IP有没有被锁定
        AttackLockerInfo ipAttack = (AttackLockerInfo)this.ipAttackLocker.getLockerInfo(request.getServletPath());
        if(ipAttack.isLocked()){
            return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018, i18nUtil.getMessage("SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018",new Object[]{ipAttack.getLockMinutes()}));
        }
        User user = null;
        if(isEmail){
            user = this.userService.findByEmail(loginName);
        }else if(isMobile){
            user = this.userService.findByMobile(loginName);
        }else{
            user = this.userService.findByUserName(loginName);
        }
        if (user == null){
            //如果被锁定，返回IP锁定信息
            if(ipAttack.tryLock()){
                return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018, i18nUtil.getMessage("SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018",new Object[]{ipAttack.getLockMinutes()}));
            }
            //用户名或密码错误
            return ResultFactory.generateErrorResult(ErrorCodes.LOGIN_FAIL_90000, i18nUtil.getMessage("LOGIN_FAIL_90000"));
        }
        //判断用户被锁定
        AttackLockerInfo attackLockerInfo = (AttackLockerInfo)this.loginAttackLocker.getLockerInfo(user.getId());
        if (attackLockerInfo.isLocked()) {
            //保存用户锁定日志 TODO:
           // UserExtraUtil.saveUserlog(user.getId(),UserActivityEvent.login_locked);
            return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018, i18nUtil.getMessage("SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018",new Object[]{attackLockerInfo.getLockMinutes()}));
        }
        //2.判断用户是否禁用 //  TODO:
        if (user.getState().equals(UserState.DISABLED.getValue())) {
            //UserExtraUtil.saveUserlog(user.getId(),UserActivityEvent.login_invalid_password);
            //用户被禁用
            return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_USER_ISDISABLED_00019, i18nUtil.getMessage("SYS_ERROR_USER_ISDISABLED_00019"));
        }
        //3.判断用户名或密码是否正确
        if (!this.doLogin(user, password,rememberMe)) {
            //锁用户：
            if (attackLockerInfo.isLocked() || attackLockerInfo.tryLock()) {
                ipAttack.tryLock();
                //保存用户锁定日志  TODO:
               // UserExtraUtil.saveUserlog(user.getId(),UserActivityEvent.login_locked);
                //返回用户锁定信息
                return ResultFactory.generateErrorResult(ErrorCodes.SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018, i18nUtil.getMessage("SYS_ERROR_LOGIN_MORETHAN_LIMIT_00018",new Object[]{attackLockerInfo.getLockMinutes()}));
            }
            //  TODO:
            //UserExtraUtil.saveUserlog(user.getId(),UserActivityEvent.login_invalid_password);
            return ResultFactory.generateErrorResult(ErrorCodes.LOGIN_FAIL_90000, i18nUtil.getMessage("LOGIN_FAIL_90000"));
        }
        //  TODO:
        //UserExtraUtil.saveUserlog(UserActivityEvent.login_normal);
        RequestResult result = ResultFactory.generateSuccessResult();
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        String redirectPath;
        if (savedRequest == null) {
            if(user.getRole().intValue() == UserRole.MEMBER.getValue()){
                redirectPath = WebConstant.REDIRECT_PATH_MALL;
            }else{
                redirectPath = WebConstant.REDIRECT_PATH_ADMIN;
            }
        } else {
            redirectPath = savedRequest.getRequestUrl();
        }
        result.put("go", redirectPath);
        return result;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean doLogin(User user, String inputPassword,boolean remberMe) {
        //  User user = this.findByEmail(email);
        logger.debug("doLogin:userExtraService.isPasswordCorrect");
        if (!this.userExtraService.isPasswordCorrect(user.getId(), inputPassword)) {
            return false;
        }
        //更新用户最后登录时间
        MapContext userMap = MapContext.newOne();
        userMap.put("id", user.getId());
        userMap.put("lastLogin", DateUtil.getSystemDate());
        userService.updateByMapContext(userMap);
        logger.debug("doLogin:userService.updateByMapContext");
        Subject userSubject = SecurityUtils.getSubject();

        UserExtra userExtra = userExtraService.findById(user.getId());
        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getId(), userExtra.getToken());
        token.setRememberMe(remberMe);
        userSubject.login(token);
        logger.debug("doLogin:userSubject.login");
        token.clear();
        return true;
    }

    @Override
    public UserExtra findUserExtraByUserId(String userId) {
        return this.userExtraService.findById(userId);
    }

    /**
     * 获取当前用户的权限信息
     * @param id
     * @return
     */
    @Override
    public Collection<String> getUserHasPermissionsById(String id) {
        Set<String> perms = new HashSet<>();
        User currUser = WebUtils.getCurrUser();
        int role = currUser.getRole().intValue();
        switch (role){
            case 0: // 普通会员
                perms.add(PermissionIndex.PERMISSION_RES_MEMBER);
                perms.add(PermissionIndex.PERMISSION_GOODS_COMMENT_MEMBER);
                perms.add(PermissionIndex.PERMISSION_ORDER_MEMBER);
                perms.add(PermissionIndex.PERMISSION_ADDRESS_MEMBER);
                perms.add(PermissionIndex.PERMISSION_ORDERGOODS_MEMBER);
                perms.add(PermissionIndex.PERMISSION_LOGISTICS_MEMBER);
                break;
            case 1: // 店员
                perms.add(PermissionIndex.PERMISSION_RES_CLERK);
                perms.add(PermissionIndex.PERMISSION_GOODS_DICT_CLERK); // 商品字典
                perms.add(PermissionIndex.PERMISSION_GOODS_CLERK);
                perms.add(PermissionIndex.PERMISSION_GOODS_COMMENT_CLERK);
                perms.add(PermissionIndex.PERMISSION_ORDER_CLERK);
                perms.add(PermissionIndex.PERMISSION_ADDRESS_CLERK);
                perms.add(PermissionIndex.PERMISSION_ORDERGOODS_CLERK);
                perms.add(PermissionIndex.PERMISSION_LOGISTICS_CLERK);
                break;
            case 2: // 店长
                perms.add(PermissionIndex.PERMISSION_CFG_MANAGER);
                perms.add(PermissionIndex.PERMISSION_RES_MANAGER);
                perms.add(PermissionIndex.PERMISSION_GOODS_DICT_MANAGER); // 商品字典
                perms.add(PermissionIndex.PERMISSION_GOODS_MANAGER);
                perms.add(PermissionIndex.PERMISSION_GOODS_COMMENT_MANAGER);
                perms.add(PermissionIndex.PERMISSION_ORDER_MANAGER);
                perms.add(PermissionIndex.PERMISSION_ADDRESS_MANAGER);
                perms.add(PermissionIndex.PERMISSION_ORDERGOODS_MANAGER);
                perms.add(PermissionIndex.PERMISSION_LOGISTICS_MANAGER);
                break;
            case 3: // 店主
                perms.add(PermissionIndex.PERMISSION_CFG_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_RES_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_GOODS_DICT_SHOPKEEPER); // 商品字典
                perms.add(PermissionIndex.PERMISSION_GOODS_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_GOODS_COMMENT_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_ORDER_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_ADDRESS_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_ORDERGOODS_SHOPKEEPER);
                perms.add(PermissionIndex.PERMISSION_LOGISTICS_SHOPKEEPER);
                break;
        }
        // 快享权限
        perms.add(PermissionIndex.PERMISSION_QUICKSHARE);
        return perms;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult updateUserPassword(String userId, String password) {
        /*if(!ShiroUtil.isAuthenticationCodeValidated(ShiroUtil.getCurrUserId(), AuthenticationScene.USER)){
            return ResultFactory.generateErrorResult(ErrorCodes.BIZ_NOT_AUTHENTICATED_10055,i18nUtil.getMessage("BIZ_NOT_AUTHENTICATED_10055"));
        }*/
        UserExtra userExtra = userExtraService.findById(userId);
        //如果用户的原密码不为空，验证用户的原密码
        /*if(!LwxfStringUtils.isBlank(userExtra.getPassword())){
            // 验证oldPassword
            if (!this.userExtraService.isPasswordCorrect(userId, oldPassword)) {
                Map<String, String> errMap = new HashMap<>();
                errMap.put("password", ErrorCodes.VALIDATE_PASSWORD_INCORRECT);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            }
        }*/

        UserExtraUtil.saltingPassword(userExtra, password);
		MapContext mapContext = new MapContext();
		mapContext.put("salt",userExtra.getSalt());
		mapContext.put("userId",userExtra.getUserId());
		mapContext.put("password",userExtra.getPassword());
        mapContext.put("token", userExtra.getToken());
		this.userExtraService.updateByMapContext(mapContext);
		this.resetRememberMeCookie(password);

        return ResultFactory.generateSuccessResult();
    }

    /**
     {
     "csrfToken":String,//防跨站请求伪造令牌
     "user":Object,
     "orgs":Array, // 仅当前用户能看到的组织列表
     "orginfo":{// 当前组织信息
     "orgId":123, // 组织标识
     "batchJoinCode":3k3k339df, // 组织的批量邀请码（不存在时，没有该属性）
     },
     "projects":Array, // 仅用户能看到的项目（归档项目单独api查询）
     "users":Array, // 用户的基本信息列表(user)
     "favorites":Array, // 用户的收藏（仅当前组织中）后端按position正序排列
     "appfilters":Array, //系统级过滤器
     "userfilters":Array, //用户级过滤器
     "members":Array, // 组织成员信息列表（职位、角色等org_user）
     "tasktypes":Array,//任务类型
     "taskfields":Array,//任务字段
     "taskfieldDatasources":Array, // 所有自定义字段的数据源
     "taskTagPools":Array, // 任务标签定义列表
     "docTagPools":Array // 文档标签定义列表
     }
     *
     * @param userId
     * @return
     */
    @Override
    public RequestResult findUserPreloadData(String userId) {
        Map<String, Object> map = new HashMap<>();
        // 1. 当前用户信息
        Map<String, Object> userMap = this.userService.findByUserId(userId);
        map.put(WebConstant.KEY_PRELOAD_USER, userMap);

        // 2. 用户权限
        Collection<String> userPermissions = this.getUserHasPermissionsById(userId);
        Map<String,String> permissionsMap = new HashMap<>();
        userPermissions.stream().forEach(p ->{
            String parr[]=p.split(ShiroUtil.PERMISSION_PART_DIVIDER_TOKEN);
            permissionsMap.put(parr[0],parr[1]);
        });
        map.put(WebConstant.KEY_PRELOAD_PERMISSIONS,permissionsMap);

        // 3. 公司及配置信息
        List<Company> companies = AppBeanInjector.companyService.findAll();
        Company company = companies.get(0);
        map.put(WebConstant.KEY_PRELOAD_COMPANY,company);
        map.put(WebUtils.REQUEST_HEADER_X_C_ID,company.getId());
        map.put(WebConstant.KEY_PRELOAD_SYSCFG,AppBeanInjector.systemConfigService.findOneByLimit());
        map.put(WebConstant.KEY_PRELOAD_STORECFG,AppBeanInjector.storeConfigService.findOneByLimit());

        // 3. 字典数据
        // 商品相关字典数据
       // Map<String,Object> goodsPreload = new HashMap<>();
       // goodsPreload.put("brands",this.brandService.findAll()); // 品牌
       // goodsPreload.put("tags",this.goodsTagService.findAll()); // 商品标签
        //goodsPreload.put("specs",this.goodsSpecService.findAll()); // 商品规格
       // goodsPreload.put("specOptions",this.specOptionService.findAll()); // 商品规格选项
        //map.put("goodsPreload",goodsPreload);

        return ResultFactory.generateRequestResult(map);
    }

    @Override
    public List<User> findAll() {
        //批量给用户发邮件时需要　TODO:2017年11月24日　未实现，等业务需要时候确定是否要分页
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult resendEmail(User user) {
        //同注册逻辑，但是 未注册：邮箱未被注册，返回验证类的错误信息，告诉用户该邮箱未被注册
        //未注册：邮箱未被注册，返回验证类的错误信息，告诉用户该邮箱未被注册
        //已激活：返回验证类的错误信息，告诉用户该邮箱账号已被激活
        Map<String, Object> errMap = new HashMap<>();
        //埋数据发送邮件使用
        TSManualData tsManualData = WebUtils.getTSManualData();
        tsManualData.setClazz(UserResetpass.class);

        //激活账号事件：直接与发邮件一一对应
        tsManualData.setEvent(JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL);
        //如果该邮箱未注册用户，则新建未激活用户
        User userOld = userService.findByEmail(user.getEmail());
        if (userOld != null) {
            //判断是否激活:如果已激活，返回前台；如果未激活，返回账户未激活。
            byte state = userOld.getState();
            //邮箱已激活
            if (state == UserState.ENABLED.getValue()) {
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_HAVE_EXISTED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            } else if (state == UserState.DISABLED.getValue()) {
                //邮箱已被：禁用
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_IS_DISABLED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            }
        } else {
            errMap.put("email", ErrorCodes.VALIDATE_EMAIL_NOT_REGISTERED);
            return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
        }
        UserResetpass userResetpass = userResetpassService.findByEmail(user.getEmail());
        if (userResetpass == null) {
            userResetpass = new UserResetpass();
            userResetpass.setCreated(DateUtil.getSystemDate());
            userResetpass.setEmail(user.getEmail());
            tsManualData.setParams(userResetpass);
            userResetpassService.add(userResetpass);
        } else {
            userResetpass.setCreated(DateUtil.getSystemDate());
            tsManualData.setParams(userResetpass);
            userResetpassService.updateUserResetpass(userResetpass);
        }

        //埋了一个对象:为了发送注册激活邮件
        tsManualData.put("userResetpass", userResetpass);
        return ResultFactory.generateSuccessResult();
    }

    @Override
    public RequestResult passwordAccredit(String password) {
        if(userExtraService.isPasswordCorrect(WebUtils.getCurrUserId(), password)){
            return ResultFactory.generateSuccessResult();
        }

        Map<String, Object> errormap = new HashMap<>();
        errormap.put("password", ErrorCodes.VALIDATE_INVALID_PASSWORD);
        return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errormap);
    }

    private void resetRememberMeCookie(String password){
        if(null != ShiroUtil.getCurrUserId()){
            UsernamePasswordToken token = new UsernamePasswordToken(
                    ShiroUtil.getCurrUserId(), password);
            token.setRememberMe(true);
            SecurityUtils.getSubject().login(token);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult userMobileRegister(User user, MapContext registerMapContext) {
        Map<String, Object> errMap = new HashMap<>();
        //如果该邮箱未注册用户，则新建未激活用户
        User userOld = userService.findByMobile(user.getMobile());
        if (userOld != null) {
            //判断是否激活:如果已激活，返回前台；如果未激活，返回账户未激活。
            byte state = userOld.getState();
            //邮箱已激活
            if (state == UserState.ENABLED.getValue()) {
                errMap.put("mobile", ErrorCodes.VALIDATE_MOBILE_HAVE_EXISTED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            } else if (state == UserState.DISABLED.getValue()) {
                //账号禁用
                errMap.put("mobile", ErrorCodes.VALIDATE_MOBILE_IS_DISABLED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            }
        } else {
            //设置主账号类型为：手机
            //用户激活状态为:已激活
            user.setState(UserState.ENABLED.getValue());
            //用户默认头相，语言等设置
            UserExtraUtil.setOtherInfo(user);
            //1.保存用户主表信息
            userService.add(user);
            //2.保存用户密码表信息
            UserExtra userExtra = new UserExtra();
            userExtra.setUserId(user.getId());
            UserExtraUtil.saltingPassword(userExtra,registerMapContext.getTypedValue("password",String.class));
            userExtra.setUpdated(DateUtil.getSystemDate());
            userExtraService.add(userExtra);
            //3.保存用户第三方信息:手机号
            userThirdInfoService.add(UserExtraUtil.userThirdInfoInitFromMobileRegister(user.getId()));
        }
        return ResultFactory.generateSuccessResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestResult userEmailRegister(User user, MapContext registerMapContext, MapContext codeMap) {
        Map<String, Object> errMap = new HashMap<>();
        //埋数据发送邮件使用
        TSManualData tsManualData = WebUtils.getTSManualData();
        tsManualData.setClazz(User.class);
        tsManualData.setParams(user);
        //激活账号事件：直接与发邮件一一对应
        tsManualData.setEvent(JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL);
        //如果该邮箱未注册用户，则新建未激活用户
        User userOld = userService.findByEmail(user.getEmail());
        if (userOld != null) {
            //判断是否激活:如果已激活，返回前台；如果未激活，返回账户未激活。
            byte state = userOld.getState();
            //邮箱已激活
            if (state == UserState.ENABLED.getValue()) {
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_HAVE_EXISTED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            } else if (state == UserState.DISABLED.getValue()) {
                //邮箱已被：禁用
                errMap.put("email", ErrorCodes.VALIDATE_EMAIL_IS_DISABLED);
                return ResultFactory.generateErrorResult(ErrorCodes.VALIDATE_ERROR, errMap);
            }
        } else {
            //设置主账号类型为：邮箱
            //用户激活状态为:未激活
            user.setState(UserState.ENABLED.getValue());
            //用户默认头相，语言等设置
            UserExtraUtil.setOtherInfo(user);
            //1.保存用户主表信息
            userService.add(user);
            //埋了一个用户对象
            tsManualData.put("user", user);
            //2.保存用户密码表信息
            UserExtra userExtra = new UserExtra();
            userExtra.setUserId(user.getId());
            UserExtraUtil.saltingPassword(userExtra,registerMapContext.getTypedValue("password",String.class));
            userExtra.setUpdated(DateUtil.getSystemDate());
            userExtraService.add(userExtra);
            //3.保存用户第三方信息:邮箱
            userThirdInfoService.add(UserExtraUtil.userThirdInfoInitFromEmailRegister(user.getId()));
        }
        //发送激活邮件
        UserResetpass userResetpass = userResetpassService.findByEmail(user.getEmail());
        //原激活邮件不存在
        if (userResetpass == null) {
            userResetpass = new UserResetpass();
            userResetpass.setCreated(DateUtil.getSystemDate());
            userResetpass.setEmail(user.getEmail());
            //埋了一个对象:为了发送注册激活邮件
            tsManualData.put("userResetpass", userResetpass);
            tsManualData.put("codeMap",codeMap);
            userResetpassService.add(userResetpass);
        }
        //原激活邮件已经存在，则重新发送
        else {
            userResetpass.setCreated(DateUtil.getSystemDate());
            //埋了一个对象:为了发送注册激活邮件
            tsManualData.put("userResetpass", userResetpass);
            tsManualData.put("codeMap",codeMap);
            userResetpassService.updateUserResetpass(userResetpass);
        }

        return ResultFactory.generateSuccessResult();
    }

    @Override
    public RequestResult getPasswordState() {
        UserExtra userExtra = userExtraService.findById(WebUtils.getCurrUserId());
        Map result = new HashMap(1);
        result.put("pwdBlank", LwxfStringUtils.isBlank(userExtra.getPassword()) ? Boolean.TRUE : Boolean.FALSE);
        return ResultFactory.generateRequestResult(result);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RequestResult updateUserState(String id, MapContext update) {
		User user = this.userService.findById(id);
		if (user==null)
		{
			return ResultFactory.generateResNotFoundResult();
		}
		update.put(WebConstant.KEY_ENTITY_ID,id);
		this.userService.updateByMapContext(update);
		return  ResultFactory.generateRequestResult(update);
	}

    @Override
    public RequestResult findUserInfoByUserIds(List<String> id) {
        return ResultFactory.generateRequestResult(this.userService.findUserInfoByUserIds(id));
    }
}
