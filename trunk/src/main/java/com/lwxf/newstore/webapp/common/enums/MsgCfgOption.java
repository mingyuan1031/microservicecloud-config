package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 16:58:21
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum MsgCfgOption {
	TASK_ASSIGNEE("taskAssignee"),
	TASK_DUEDATE_MD("taskDuedateMD"),
	TASK_DONE_MD("taskDoneMD"),
	TASK_COMMENT("taskComment"),
	TASK_KBC_MD("taskKbcMD"),
	TASK_DELETE("taskDelete"),
	DOC_COMMENT("docComment"),
	DOC_DELETE("docDelete");

	private String value;

	MsgCfgOption(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}


	public static String getDefaultCfg(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0 , len = MsgCfgOption.values().length ; i < len ; i++){
			sb.append("1");
		}
		return sb.toString();
	}
}
