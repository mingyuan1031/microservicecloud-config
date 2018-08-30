package com.lwxf.newstore.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lwxf.commons.security.attacklocker.IAttackLocker;
import com.lwxf.commons.security.attacklocker.IKeyGetter;
import com.lwxf.commons.security.attacklocker.impl.AtatckLocker;
import com.lwxf.newstore.webapp.common.security.attacklocker.IpKeyGetter;

/**
 * 功能：攻击防护锁定义
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 12:07:46
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class AttackLockerConfig {
	@Bean
	IAttackLocker ipAttackLocker(){
		IAttackLocker locker = new AtatckLocker();
		IKeyGetter keyGetter = new IpKeyGetter();
		locker.setKeyGetter(keyGetter);
		return locker;
	}
	@Bean
	IAttackLocker loginAttackLocker(){
		return new AtatckLocker();
	}
	@Bean
	IAttackLocker errorOutApiLocker(){
		IAttackLocker locker = new AtatckLocker();
		locker.setLiveSeconds(10);
		locker.setLockMinutes(30);
		IKeyGetter keyGetter = new IpKeyGetter();
		locker.setKeyGetter(keyGetter);
		return locker;
	}
}
