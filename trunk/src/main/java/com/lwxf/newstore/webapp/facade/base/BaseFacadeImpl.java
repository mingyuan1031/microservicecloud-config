package com.lwxf.newstore.webapp.facade.base;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 13:43:56
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Service("baseFacade")
@Transactional(readOnly = true)
public abstract class BaseFacadeImpl implements BaseFacade {

}
