package com.lwxf.newstore.webapp.common.worker;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManagerEntity;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AbstractAutowireWorker;
import com.lwxf.newstore.webapp.baseservice.tsmanager.base.AfterCommitEventListener;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.jmail.JMailService;
import com.lwxf.newstore.webapp.common.jmail.ResetPasswordMailSend;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.user.User;
import com.lwxf.newstore.webapp.domain.entity.user.UserResetpass;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.jMailService;

/**
 * 功能：向云端发送信息或邮件
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:28:29
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("sendCloudWorker")
public class SendCloudWorker extends AbstractAutowireWorker implements AfterCommitEventListener {
	@Resource
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Override
	public void afterCommit(List<TSManagerEntity> tsManagerEntitys) {
		TSManualData tsManualData = (TSManualData) WebUtils.getDataFromRequestMap(WebConstant.TSMANAGER_MANUAL_ACTION_FLAG);
		if (tsManualData != null) {
			//重置密码事件
			if (JMailService.MAILSEND_CLASSNAME_RESETPASSWORDMAIL.equals(tsManualData.getEvent())) {
//				threadPoolTaskExecutor.execute();
				UserResetpass resetPass = (UserResetpass) tsManualData.get("userResetpass");
				// 2.新邮件发送
				ResetPasswordMailSend mail;
				Assert.notNull(resetPass.getEmail(), "缺少发送邮件的标识");
				mail = (ResetPasswordMailSend) jMailService.createMailSend(JMailService.MAILSEND_CLASSNAME_RESETPASSWORDMAIL);
				mail.setResetPass(resetPass);
				Set<String> tos = new HashSet<>();
				tos.add(resetPass.getEmail());
				mail.setTo(tos);
				mail.setUser(AppBeanInjector.userService.findByEmail(resetPass.getEmail()));
				jMailService.send(mail);
			} else if (JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL.equals(tsManualData.getEvent())) {
				// 发送激活账号的邮件
				UserResetpass resetPass = (UserResetpass) tsManualData.get("userResetpass");
				// 2.新邮件发送
				ResetPasswordMailSend mail;
				Assert.notNull(resetPass.getEmail(), "缺少发送邮件的标识");
				mail = (ResetPasswordMailSend) jMailService.createMailSend(JMailService.MAILSEND_CLASSNAME_SENDACTIVATIONMAIL);
				mail.setResetPass(resetPass);
				mail.setCodeMap((MapContext) tsManualData.get("codeMap"));
				Set<String> tos = new HashSet<>();
				tos.add(resetPass.getEmail());
				mail.setTo(tos);
				User user = (User) tsManualData.get("user");
				mail.setUser(user);
				jMailService.send(mail);
			} else if (JMailService.MAILSEND_CLASSNAME_INVITATIONMAIL.equals(tsManualData.getEvent())) {
				/*OrgInvitation orgInvitation = (OrgInvitation) tsManualData.get("orgInvitation");
				InvitationMailSend mail = (InvitationMailSend) jMailService.createMailSend(JMailService.MAILSEND_CLASSNAME_INVITATIONMAIL);
				mail.setOrgInvitation(orgInvitation);
				//组织名称如果需要再加,暂时赋"EasyPM"
				Org org = orgService.findById(orgInvitation.getOrgId());
				String orgName = "EasyPM";
				if (org != null) {
					orgName = org.getName();
				}
				mail.setTeamName(orgName);
				Set<String> tos = new HashSet<>();
				tos.add(orgInvitation.getEmail());
				mail.setTo(tos);
				mail.setInviteCode(orgInvitation.getCode());
				jMailService.send(mail);*/
			}
		}

	}
}
