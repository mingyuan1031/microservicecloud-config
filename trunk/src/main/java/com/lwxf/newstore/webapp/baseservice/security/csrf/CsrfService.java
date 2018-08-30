package com.lwxf.newstore.webapp.baseservice.security.csrf;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.csrfguard.CsrfGuard;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.json.JsonMapper;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.common.constant.BizConstant;
import com.lwxf.newstore.webapp.common.shiro.ShiroUtil;
import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:05:18
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CsrfService {
	protected JsonMapper json = new JsonMapper();

	public CsrfService() {
	}

	public Map<String, Object> getAppCfg(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CsrfGuard csrfGuard = CsrfGuard.getInstance();
		Map<String, Object> cfg = new HashMap<>();
		String _userId= ShiroUtil.getCurrUserId();
		String userId =(_userId==null?"":_userId)  + "";
		String token = (String) session.getAttribute(csrfGuard.getSessionKey());
		cfg.put(csrfGuard.getTokenName(), token);
		String sesionMd5 = md5Hash(session.getId(), userId);
		cfg.put(CommonConstant.REQUEST_HEADER_KEY_PHPSESSIONID, session.getId());
		cfg.put(CommonConstant.REQUEST_HEADER_KEY_X_SID, createSecretCode(userId, token, sesionMd5) + "_" + sesionMd5);
		cfg.put(CommonConstant.SITE_BASE_URL, WebUtils.getDomainUrl());
		return cfg;
	}


	private String md5Hash(String s1, String s2) {
		String ret = s1 + "_" + s2;
		try {
			MessageDigest md= (MessageDigest) WebUtils.getDataFromRequestMap("CsrfService_MD5");
			if (md == null) {
				md = MessageDigest.getInstance("MD5");
				WebUtils.putDataToRequestMap("CsrfService_MD5",md);
			}
			md.update(ret.getBytes("UTF-8"));
			BigInteger bigInt = new BigInteger(1, md.digest());
			ret = bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("", e);
		}
		return ret;
	}

	private String createSecretCode(String userId, String sessionId, String token) {
		String ret = md5Hash(userId, sessionId);
		ret = md5Hash(ret, token);
		return ret;
	}

	public boolean validateToken(HttpServletRequest request, HttpServletResponse response, String xSId, String token) {
		if (LwxfStringUtils.isEmpty(xSId) || LwxfStringUtils.isEmpty(token)) {
			return false;
		}
		String[] values = xSId.split("_");
		if (values.length != 2) {
			return false;
		}

		String sCode = values[0];
		String sesionMd5 = values[1];
		String _userId=ShiroUtil.getCurrUserId();
		String userId =(_userId==null?"":_userId)  + "";
		String _newSCode = createSecretCode(userId, token, sesionMd5);
		boolean ret = _newSCode.equals(sCode);
		if (ret) {
			HttpSession session = request.getSession();
			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			sesionMd5 = md5Hash(session.getId(), userId);
			token = (String) session.getAttribute(csrfGuard.getSessionKey());
			xSId = createSecretCode(userId, token, sesionMd5) + "_" + sesionMd5;
			response.setHeader(CommonConstant.REQUEST_HEADER_KEY_X_SID, xSId);
			response.setHeader(csrfGuard.getTokenName(), token);
		}
		return ret;
	}
}
