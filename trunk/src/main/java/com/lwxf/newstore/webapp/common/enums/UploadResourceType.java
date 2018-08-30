package com.lwxf.newstore.webapp.common.enums;

/**
 * 功能：上传文件的资源类型
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-15 17:48
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public enum UploadResourceType {
	SYSCFG(0,"syscfg"),
	STORECFG(1,"storecfg"),
	STORECARD(4,"storecard"),
	GOODS(2,"goods"),
	GOODSCOMMENT(3,"goodscomment"),
	QUICKSHARE(4,"quickshare"),
	GOODSSPEC(5,"goodsspec"),
	BRAND(6,"brand"),
	AVATAR(7,"avatar"),
	BACKGROUND(8,"background"),
	GOODSSHOW(9,"goodsshow"),
	STOREHOMENAV(10,"storehomenav"),
	VIDEOFILE(11,"videofile"),
	COVER(12,"cover"),
	ADVERTISING(13,"advertising"),
	SCHEME_COVER(14,"scheme_cover"),
	SCHEME_PANORAMA(15,"scheme_panorama");

	/*LOGO(11,"logo"),
	HEADER(12,"header"),
	POSTER(13,"poster"),
	QRCODE(14,"qrcode")*/
	private int type;
	private String module; // 存数文件的路径

	UploadResourceType(int type,String module) {
		this.type = type;
		this.module = module;
	}

	public int getType() {
		return this.type;
	}

	public String getModule() {
		return module;
	}

	public static boolean validType(int type){
		UploadResourceType[] arr = values();
		for (int m=0,len = arr.length;m<len;m++){
			if(arr[m].type == type){
				return true;
			}
		}
		return false;
	}
}
