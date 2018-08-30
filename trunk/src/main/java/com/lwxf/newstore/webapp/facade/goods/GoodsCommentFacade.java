package com.lwxf.newstore.webapp.facade.goods;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.entity.goods.GoodsComment;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/7/9/009 16:07
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsCommentFacade extends BaseFacade {
	RequestResult findByMapper(Integer pageNum,Integer pageSize,MapContext mapContext);
	RequestResult addComment(GoodsComment goodsComment,String[] imgList,String orderGoodsId);
	RequestResult uploadCommentImage(String goodsId,MultipartFile[] files);
	RequestResult deleteById(String commentId,String goodsId);
	RequestResult updataDisabled(String commentId,String goodsId);
}
