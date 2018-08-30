package com.lwxf.newstore.webapp.common.mobile.weixin.template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dazhen（1299501979@qq.com）
 * @create 2018-07-10 16:38
 * @desc
 **/

public class ImgJsonMsg extends BaseJsonMsg{
    public static final String KEY_MEDIAID="media_id";
    private Map<String,String> image;

    public ImgJsonMsg(){
        image = new HashMap<>();
        this.setMsgtype("image");
    }


    @Override
    public void setContentInfo(String info) {

    }

    public void setMediaId(String mediaId) {
        image.put(KEY_MEDIAID,mediaId);
    }

    public Map<String, String> getImage() {
        return image;
    }
}

