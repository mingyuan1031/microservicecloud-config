package com.lwxf.newstore.webapp.common.component;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.lwxf.commons.uniquekey.IdGererateFactory;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:40:51
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component
public class IdFileNameGenerator implements FileNameGenerator {
	@Resource
	private IdGererateFactory idGererateFactory;

	public void setIdGererateFactory(IdGererateFactory idGererateFactory) {
		this.idGererateFactory = idGererateFactory;
	}

	@Override
	public String generate() {
		return idGererateFactory.nextStringId();
	}
}
