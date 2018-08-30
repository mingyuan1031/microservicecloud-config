package com.lwxf.newstore.webapp.common.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.Assert;

import com.lwxf.commons.exception.*;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

/**
 * 功能：异常生成器（除非特殊情况，异常均有此生成器生成）
 * i18n错误消息处理
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:10:33
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class ExceptionGenerateFactory {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionGenerateFactory.class);
	private static final Map<String,String> errorCodeMapping;
	private static final Set<String> validateErrorCodes = new HashSet<String>() {{
		add(ErrorCodes.VALIDATE_DOC_FOLDER_NOT_EXISTED);
		add(ErrorCodes.VALIDATE_EMAIL_HAVE_EXISTED);
		add(ErrorCodes.VALIDATE_ILLEGAL_CHARACTER);
		add(ErrorCodes.VALIDATE_INCORRECT_DATA_FORMAT);
		add(ErrorCodes.VALIDATE_INVALID_EMAIL);
		add(ErrorCodes.VALIDATE_INVALID_MOBILE_NO);
		add(ErrorCodes.VALIDATE_INVALID_PHONE_NO);
		add(ErrorCodes.VALIDATE_LENGTH_TOO_LONG);
		add(ErrorCodes.VALIDATE_LOGINNAME_HAVE_EXISTED);
		add(ErrorCodes.VALIDATE_NOT_ALLOWED_REPEAT);
		add(ErrorCodes.VALIDATE_NOTNULL);
		add(ErrorCodes.VALIDATE_PASSWORD_INCORRECT);
		add(ErrorCodes.VALIDATE_PASSWORD_IS_NOT_CONSISTENT);
		add(ErrorCodes.SYS_INVALID_CSRF_TOKEN_00013);
		add(ErrorCodes.VALIDATE_CIRCULAR_REFERENCE_ERROR_20022);
		add(ErrorCodes.VALIDATE_NOT_LESS_THAN_ZERO_20025);
	}};

	private static final Map<String, Constructor<LwxfException>> constructorMap = new HashMap<String, Constructor<LwxfException>>();

	static {
		Map<String, Class<? extends LwxfException>> exceptionMap = new HashMap<String, Class<? extends LwxfException>>() {{
			put("LOGIN_FAIL_90000", LoginFailException.class);
			put("SYS_UNKNOW_00000", UnknowException.class);
			put("SYS_EXECUTE_FAIL_00001", ExecuteFailException.class);
			put("SYS_ILLEGAL_DATA_00002", IllegalDataException.class);
			put("SYS_UNSUPPORT_ENCODING_00003", UnsupportEncodingException.class);
			put("SYS_GENERATOR_ID_EXCEPTION_00004", GeneratorIdException.class);
			put("SYS_ILLEGAL_ARGUMENT_00005", LwxfIllegalArgumentException.class);
			put("SYS_UPLOAD_ERROR_00006", UploadFileException.class);
			put("SYS_IO_EXCEPTION_00007", LwxfIOException.class);
			put("SYS_INVALID_EMAIL_ADDRESS_00009", InvalidEmailAddressException.class);
			put("SYS_UNINITIALIZED_EXCEPTION_00010", UninitializedException.class);
			put("SYS_INVALID_CSRF_TOKEN_00013", InvalidCsrfTokenException.class);
			put("SYS_INVALID_REFER_00014", InvalidReferException.class);
			put("BIZ_REQUEST_PARAM_ERROR_10000", RequestParamError.class);
			put("BIZ_RES_NOT_FOUND_10001", ResNotFoundException.class);
			put("BIZ_USER_NOT_FOUND_10002", UserNotFoundException.class);
			put("BIZ_NO_PERMISSION_10003", NoPermissionExcpetion.class);
			put("BIZ_RES_BE_IN_USE_10006", ResBeInUseException.class);
			put("BIZ_SYS_RES_NOT_ALLOW_DELETE_10007", ResNotAllowDeleteException.class);
			put("BIZ_RES_ARCHIVED_10008", ResArchivedException.class);
			put("BIZ_RES_DISABLED_10011", ResDisabledException.class);
			put("SYS_INVALID_CSRF_TOKEN_00013", CircularReferenceException.class);
			put("VALIDATE_CIRCULAR_REFERENCE_ERROR_20022", CircularReferenceException.class);
			put("SYS_SESSION_TIMEOUT_00015", SessionTimeOutException.class);
			put("BIZ_LOAD_USER_WEIXIN_INFO_10021", LoadUserWeixinInfoError.class);
			put("BIZ_MB_GET_AUTHORIZATION_FAIL_10022", GetAuthorizationFail.class);
			put("BIZ_MB_BIND_WX_FAIL_10023", BindWeixinFail.class);
			put("BIZ_MB_CREATE_WX_MENU_FAIL_10024", CreateWeixinMenuFail.class);
			put("BIZ_MB_DELETE_WX_MENU_FAIL_10025", DeleteMenuFail.class);
			put("BIZ_MB_WEIXIN_BOUND_10026", WeixinHasBeenBound.class);
			put("BIZ_MB_CREATE_QRCODE_FAIL_10027", CreateQRCodeFail.class);
			put("BIZ_MB_ONLY_UNBIND_SELF_WX_10028", OnlyUnbindSelfWeixin.class);
			put("BIZ_MB_NOT_BIND_WX_10029", NotBindWeixin.class);
			put("BIZ_MB_INVALID_AUTH_CODE_10030", InvalidAuthorizationCode.class);
			put("BIZ_FILE_SIZE_LIMIT_10031", FileSizeLimitException.class);
			put("BIZ_FILE_SPACE_SIZE_LIMIT_10040", FileSpaceSizeLimitException.class);
			put("BIZ_IP_WHITELIST_CHECK_FAILED_10049", IpWhitelistCheckFailed.class);
			put("SYS_ERROR_REQUEST_METHOD_NOT_SUPPORT_00020", RequestMethodNotSupport.class);
		}};
		errorCodeMapping = new HashMap<>();
		int idx;
		String errorCode;
		for (String key : exceptionMap.keySet()) {
			try {
				idx = key.lastIndexOf("_");
				errorCode = key.substring(idx + 1);
				if (LwxfStringUtils.isBlank(errorCode) || !LwxfStringUtils.isNumeric(errorCode)) {
					Assert.isTrue(false, "不合规范的错误码定义：" + key);
				}
				Constructor<LwxfException> constructor = (Constructor<LwxfException>) exceptionMap.get(key).getConstructor(String.class, Throwable.class);
				constructorMap.put(errorCode, constructor);
				errorCodeMapping.put(errorCode, key);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	private ExceptionGenerateFactory() {

	}

	public static LwxfException createException(String errorCode) {
		return createException(errorCode, null);
	}

	public static LwxfException createException(String errorCode, Throwable cause) {
		String errorCodeKey = errorCodeMapping.get(errorCode);
		Constructor<LwxfException> constructor = constructorMap.get(errorCode);
		Assert.notNull(constructor, "未定义异常类的错误码：" + errorCode);
		LwxfException chException = null;
		try {
			String i18nMessage = AppBeanInjector.i18nUtil.getMessage(errorCodeKey);
			chException = constructor.newInstance(i18nMessage, cause);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chException;
	}

	/**
	 * 创建验证类异常
	 *
	 * @param validateErrorCode：具体的验证错误码（不能传20000进来）
	 * @return
	 */
	public static LwxfException createValidateException(String validateErrorCode) {
		if (ErrorCodes.VALIDATE_ERROR.equals(validateErrorCode)) {
			Assert.isTrue(false, ErrorCodes.VALIDATE_ERROR + "是统称的验证类错误码，请传入具体的验证错误码");
		}
		if (!validateErrorCodes.contains(validateErrorCode)) {
			Assert.isTrue(false, "未知的验证错误码，请检查错误码定义：" + validateErrorCode);
		}
		return new ValidateException(validateErrorCode);
	}
}
