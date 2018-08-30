package com.lwxf.newstore.webapp.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 功能：请求编码处理
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 11:04:59
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class LwxfCharacterEncodingFilter extends CharacterEncodingFilter {
	private Pattern disableForceEncodingPattern;

	public void setDisableForceEncoding(String disableForceEncoding) {
		this.disableForceEncodingPattern = Pattern.compile(disableForceEncoding);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (disableForceEncodingPattern != null && disableForceEncodingPattern.matcher(request.getServletPath()).matches()) {
			filterChain.doFilter(request, response);
		} else {
			super.doFilterInternal(request, response, filterChain);
		}
	}
}
