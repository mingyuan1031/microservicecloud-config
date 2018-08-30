package com.lwxf.newstore.webapp.common.shiro;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.shiro.io.ClassResolvingObjectInputStream;
import org.apache.shiro.io.SerializationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.commons.security.attacklocker.IAttackLocker;
import com.lwxf.commons.security.attacklocker.IAttackLockerInfo;
import com.lwxf.newstore.webapp.bizservice.user.UserExtraService;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.UserExtra;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:37:45
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfRememberMeManager extends org.apache.shiro.web.mgt.CookieRememberMeManager {
	private final static Logger logger = LoggerFactory.getLogger(LwxfRememberMeManager.class);
	@Resource(name = "ipAttackLocker")
	protected IAttackLocker ipAttackLocker;
	@Resource(name = "userExtraService")
	UserExtraService userExtraService;
	private Pattern principalPattern = Pattern.compile("(\\w*?)=(\\w*)");

	private String md5Hash(UserExtra user) {
		String hash = user.getSalt();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(hash.getBytes("UTF-8"));
			BigInteger bigInt = new BigInteger(1, md.digest());
			hash = bigInt.toString(16);

		} catch (Exception e) {
			logger.error("", e);
		}
		return hash;
	}

	private byte[] serializeStr(String o) throws SerializationException {
		if (o == null) {
			String msg = "argument cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			String msg = "Unable to serialize object [" + o + "].  " +
					"In order for the DefaultSerializer to serialize this object, the [" + o.getClass().getName() + "] " +
					"class must implement java.io.Serializable.";
			throw new SerializationException(msg, e);
		}
	}

	@Override
	protected byte[] convertPrincipalsToBytes(PrincipalCollection principals) {
		//todo:  发送Spring Event，其它地方接收事件进行处理，例如： 发送微信登录消息
		String value = (String) principals.getPrimaryPrincipal();
		UserExtra user = this.userExtraService.findById(value);
		String remember_key = md5Hash(user);
		String tag = value + "=" + remember_key;
		byte[] bytes = this.serializeStr(tag);
		if (getCipherService() != null) {
			bytes = encrypt(bytes);
		}
		return bytes;
	}

	private PrincipalCollection doConvertBytesToPrincipals(byte[] bytes, SubjectContext subjectContext, IAttackLockerInfo attackLockerInfo) {
		if (getCipherService() != null) {
			bytes = decrypt(bytes);
		}

		String value = deserializeStr(bytes);// new String(bytes);
		Matcher matcher = principalPattern.matcher(value);

		if (matcher.matches()) {
			String userId = String.valueOf(matcher.group(1));
			UserExtra user = this.userExtraService.findById(userId);
			if (user != null) {
				String reqKey = matcher.group(2);
				String rememberKey = md5Hash(user);
				if (reqKey.equals(rememberKey)) {
					//微信端不走remeberme
					if(WebUtils.getUserAgent().isWeixin()){
						return null;
					}
					return ShiroUtil.createPrincipalCollection(userId);
				}
			}
		}
		// attackLockerInfo.lockIt(); //TODO：暂时屏蔽
		return null;
	}

	private String deserializeStr(byte[] serialized) throws SerializationException {
		if (serialized == null) {
			String msg = "argument cannot be null.";
			throw new IllegalArgumentException(msg);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
		BufferedInputStream bis = new BufferedInputStream(bais);
		try {
			ObjectInputStream ois = new ClassResolvingObjectInputStream(bis);
			String deserialized = (String) ois.readObject();
			ois.close();
			return deserialized;
		} catch (Exception e) {
			String msg = "Unable to deserialze argument byte array.";
			throw new SerializationException(msg, e);
		}
	}

	@Override
	protected PrincipalCollection convertBytesToPrincipals(byte[] bytes, SubjectContext subjectContext) {
		/*IAttackLockerInfo attackLockerInfo = this.ipAttackLocker.getLockerInfo("remberme");
		if (!attackLockerInfo.isLocked()) {
			PrincipalCollection pc = doConvertBytesToPrincipals(bytes, subjectContext, attackLockerInfo);
			if (pc != null)
				return pc;

		}
		if (attackLockerInfo.isLocked() || attackLockerInfo.tryLock()) {
			WebUtils.getHttpServletRequest().setAttribute("isCookieAttack", true);
		}

		forgetIdentity(subjectContext);
		return null;*/

		PrincipalCollection pc = doConvertBytesToPrincipals(bytes, subjectContext, null);
		if (pc != null) {
			return pc;
		}
		forgetIdentity(subjectContext);
		return null;
	}
}
