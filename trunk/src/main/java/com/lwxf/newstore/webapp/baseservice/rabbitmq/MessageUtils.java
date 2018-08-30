package com.lwxf.newstore.webapp.baseservice.rabbitmq;

import com.lwxf.commons.utils.LwxfStringUtils;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:20:55
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public abstract class MessageUtils {
	private static final String ORG_ROUTING_KEY_TPL="O.{0}";
	private static final String ORGUSER_ROUTING_KEY_TPL="OU.{0}";
	private static final String USER_ROUTING_KEY_TPL="U.{0}";
	private static final String FOLDER_ROUTING_KEY_TPL="F.{0}";
	private static final String DOC_ROUTING_KEY_TPL="D.{0}";

	public static String getOrgRoutingKey(String orgId){
		return LwxfStringUtils.format(ORG_ROUTING_KEY_TPL,orgId);
	}

	public static String getOrgUserRoutingKey(String orgUserId){
		return LwxfStringUtils.format(ORGUSER_ROUTING_KEY_TPL,orgUserId);
	}

	public static String getUserRoutingKey(String userId){
		return LwxfStringUtils.format(USER_ROUTING_KEY_TPL,userId);
	}

	public static String getFolderRoutingKey(String folderId){
		return LwxfStringUtils.format(FOLDER_ROUTING_KEY_TPL,folderId);
	}

	public static String getDocRoutingKey(String docId){
		return LwxfStringUtils.format(DOC_ROUTING_KEY_TPL,docId);
	}
}
