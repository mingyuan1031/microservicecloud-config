package com.lwxf.newstore.webapp.web.filter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.lwxf.commons.utils.LwxfStringUtils;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:41:54
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class SecureHeadersFilter implements Filter {

	/** Configuration member to specify if web app use web fonts */
	private static final boolean APP_USE_WEBFONTS = false;

	/** Configuration member to specify if web app use videos or audios */
	private static final boolean APP_USE_AUDIOS_OR_VIDEOS = false;

	/** Configuration member to specify if filter must add CSP directive used by Mozilla (Firefox) */
	private static final boolean INCLUDE_MOZILLA_CSP_DIRECTIVES = true;

	private static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
	private static final String X_CONTENT_SECURITY_POLICY = "X-Content-Security-Policy";
	private static final String X_WEBKIT_CSP = "X-WebKit-CSP";

	private static final String POLICIES_DEFAULT_SRC = "default-src * data: blob: 'self'";
	private static final StringBuilder POLICIES_SCRIPT_SRC;
	private static final StringBuilder POLICIES_OBJECT_SRC;
	private static final StringBuilder POLICIES_STYLE_SRC;
	private static final StringBuilder POLICIES_IMG_SRC;
	private static final StringBuilder POLICIES_OPTIONS;
	private static final StringBuilder POLICIES_PLUGIN_TYPES;
	private static final StringBuilder POLICIES_REFLECTED_XSS;
	private static final StringBuilder POLICIES_COMMONS;

	private static final List<String> CSP_POLICIES = new ArrayList<>();

	/** List CSP HTTP Headers */
	private static final List<String> CSP_HEADERS = new ArrayList<>();


	/** Filter configuration */
	private FilterConfig filterConfig = null;

	/** Collection of CSP polcies that will be applied */
	private String policies;

	/** Used for Script Nonce */
	private SecureRandom prng = null;

	static {
		POLICIES_COMMONS = new StringBuilder();
		POLICIES_COMMONS.append("*.easypm.cn ");
		POLICIES_COMMONS.append("hm.baidui.com ");
		POLICIES_COMMONS.append("at.alicdn.com ");
		POLICIES_COMMONS.append("*.weixin.qq.com ");
		String commonsStr = POLICIES_COMMONS.toString();

		POLICIES_SCRIPT_SRC = new StringBuilder("script-src ");
		POLICIES_SCRIPT_SRC.append("'unsafe-inline' ");
		POLICIES_SCRIPT_SRC.append("'unsafe-eval' ");
		POLICIES_SCRIPT_SRC.append(commonsStr);

		POLICIES_OBJECT_SRC = new StringBuilder("object-src ");
		POLICIES_OBJECT_SRC.append("*.easypm.cn ");

		POLICIES_STYLE_SRC = new StringBuilder("style-src ");
		POLICIES_STYLE_SRC.append(commonsStr);

		POLICIES_IMG_SRC = new StringBuilder("img-src ");
		POLICIES_IMG_SRC.append(commonsStr);

		POLICIES_OPTIONS = new StringBuilder("options ");
		POLICIES_OPTIONS.append("inline-script ");
		POLICIES_OPTIONS.append("eval-script ");

		POLICIES_PLUGIN_TYPES = new StringBuilder("plugin-types ");
		POLICIES_PLUGIN_TYPES.append("application/pdf ");
		POLICIES_PLUGIN_TYPES.append("application/x-shockwave-flash ");

		POLICIES_REFLECTED_XSS = new StringBuilder("reflected-xss ");
		POLICIES_REFLECTED_XSS.append("block ");

		/*if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
			CSP_POLICIES.add(POLICIES_OPTIONS.toString());
		}*/

		CSP_HEADERS.add(CONTENT_SECURITY_POLICY);
		CSP_HEADERS.add(X_CONTENT_SECURITY_POLICY);
		CSP_HEADERS.add(X_WEBKIT_CSP);
	}

	/**
	 * Used to prepare (one time for all) set of CSP policies that will be applied on each HTTP response.
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// Get filter configuration
		this.filterConfig = fConfig;
		if(this.policies == null){
			//String domain =AppBeanInjector.configuration.getDomainUrl().split(":")[1].concat(" ");
			String domain ="epm4.nat123.net ";
			//if(AppBeanInjector.configuration.isOnDev()){
				POLICIES_SCRIPT_SRC.append("localhost ");
				POLICIES_SCRIPT_SRC.append(domain);
				POLICIES_SCRIPT_SRC.append("127.0.0.1 ");
				POLICIES_SCRIPT_SRC.append("192.168.0.112 ");
				POLICIES_SCRIPT_SRC.append("localhost:3000 ");
				POLICIES_SCRIPT_SRC.append("github.com ");
				POLICIES_OBJECT_SRC.append(domain);
				POLICIES_IMG_SRC.append(domain);
				POLICIES_IMG_SRC.append("localhost ");
				POLICIES_IMG_SRC.append("127.0.0.1 ");
				POLICIES_IMG_SRC.append("192.168.0.112 ");
				POLICIES_IMG_SRC.append("localhost:3000 ");
				POLICIES_IMG_SRC.append("github.com ");


			POLICIES_STYLE_SRC.append(domain);
			POLICIES_STYLE_SRC.append("localhost ");
			POLICIES_STYLE_SRC.append("127.0.0.1 ");
			POLICIES_STYLE_SRC.append("192.168.0.112 ");
			POLICIES_STYLE_SRC.append("localhost:3000 ");
			POLICIES_STYLE_SRC.append("github.com ");
			//}

			CSP_POLICIES.add(POLICIES_DEFAULT_SRC);
			CSP_POLICIES.add(POLICIES_SCRIPT_SRC.toString());
			CSP_POLICIES.add(POLICIES_OBJECT_SRC.toString());
			CSP_POLICIES.add(POLICIES_STYLE_SRC.toString());
			CSP_POLICIES.add(POLICIES_IMG_SRC.toString());
			CSP_POLICIES.add(POLICIES_PLUGIN_TYPES.toString());
			CSP_POLICIES.add(POLICIES_REFLECTED_XSS.toString());
			policies = LwxfStringUtils.collectionJoin(CSP_POLICIES,";");
		}
		// Init secure random
		try {
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}

		// Target formating
	}

	/**
	 * Add CSP policies on each HTTP response.
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fchain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);

		/* Step 1 : Detect if target resource is a Frame */
		// Customize here according to your context...
		boolean isFrame = true;

		/* Step 2 : Add CSP policies to HTTP response */
		StringBuilder policiesBuffer = new StringBuilder(policies);

		// If resource is a frame add Frame/Sandbox CSP policy
		/*if (isFrame) {
			// Frame + Sandbox : Here sandbox allow nothing, customize sandbox options depending on your app....
			policiesBuffer.append(";").append("frame-src 'self';sandbox");
			if (INCLUDE_MOZILLA_CSP_DIRECTIVES) {
				policiesBuffer.append(";").append("frame-ancestors 'self'");
			}
		}*/

		// Add Script Nonce CSP Policy
		// --Generate a random number
		String randomNum = new Integer(this.prng.nextInt()).toString();
		// --Get its digest
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
		byte[] digest = sha.digest(randomNum.getBytes());
		// --Encode it into HEXA
		String scriptNonce = Hex.encodeHexString(digest);
		policiesBuffer.append(";").append("script-nonce ").append(scriptNonce);
		// --Made available script nonce in view app layer
		httpRequest.setAttribute("CSP_SCRIPT_NONCE", scriptNonce);

		// Add policies to all HTTP headers
		for (String header : CSP_HEADERS) {
			httpResponse.setHeader(header, policiesBuffer.toString());
		}

		/* Step 3 : Let request continue chain filter */
		fchain.doFilter(request, response);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Not used
	}
}
