package com.lwxf.newstore.webapp.common.enums.activity;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-07-02 19:43
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum SystemActivityScope {
	COMPANY(0),
	CONFIG_SYS(1),
	CONFIG_STORE(2),
	GOODS(3),
	GOODS_COMMENT(4),
	ORDER(5),
	QUICKSHARE(6),
	QUICKSHARE_COMMENT(7),
	QUICKSHARE_PRAISE(8),
	RESERVATION(9),
	MEMBER(10),
	USER(11),
	BRAND(12),
	TAG(13),
	GOODSTYPE(14),
	CART(15),
	STORE_NAV(16),
	ADVERTISING(17),
	CLERK(18),
	LOGISTICS(19),
	ADDRESS(20),
	ORDERGOODS(21)
	;

	private int value;

	SystemActivityScope(int value){
		this.value = value;
	}

	public static final boolean validValue(int value){
		SystemActivityScope[] sas = SystemActivityScope.values();
		for(int m=0,len=sas.length;m<len;m++){
			if(sas[m].getValue()==value){
				return true;
			}
		}
		return false;
	}

	public int getValue() {
		return value;
	}
}
