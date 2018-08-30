package com.lwxf.newstore.webapp.facade.goods;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.domain.dto.goods.GoodsDefault;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.goods.Goods;
import com.lwxf.newstore.webapp.facade.base.BaseFacade;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/20/020 18:15
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface GoodsFacade extends BaseFacade {

	RequestResult findGoodsDefaultByParamsForPaging(Integer pageNum,Integer pageSize, MapContext mapContext);
	RequestResult addGoods(Goods goods);
	RequestResult findForHomePage();
	RequestResult updata(String id,MapContext mapContext);
	RequestResult findById(String id);
	RequestResult uploadGoodsImage(String goodsId,MultipartFile file);
	RequestResult updataContent(String goodsId, List<Map<String,Object>> uploadFilesList, String content);
}
