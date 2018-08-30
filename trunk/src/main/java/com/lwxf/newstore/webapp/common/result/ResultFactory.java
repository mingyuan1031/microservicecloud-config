package com.lwxf.newstore.webapp.common.result;

import java.util.Map;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.newstore.webapp.common.model.Pagination;

import static com.lwxf.newstore.webapp.facade.AppBeanInjector.i18nUtil;

/**
 * 功能：后端返回前端的结果数据生成器
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 10:28:01
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public final class ResultFactory {
	private ResultFactory(){

	}

	/**
	 * 生成错误信息对象
	 * @param code：错误码，如：400、404等，还有自定义错误码
	 * @param error：错误对象，可以是String、Map、Object
	 * @return
	 */
	public static RequestResult generateErrorResult(String code,Object error){
		return RequestResult.newInstance(code,error);
	}

	/**
	 * 生成正常请求结果对象
	 * @param result：
	 *    分页对象：com.lwxf.newstore.webapp.common.model.PaginatedList
	 *    其他对象：可以为任意对象（包括String）
	 * @return
	 */
	public static RequestResult generateRequestResult(Object result){
		return RequestResult.newInstance(result);
	}

	/**
	 * 分页查询返回多个数据集的情况
	 * @param datas
	 * @param pagination
	 * @return
	 */
	public static RequestResult generateRequestResult(Map<String,Object> datas, Pagination pagination){
		return RequestResult.newInstance(datas,pagination);
	}

	/**
	 * 生成操作成功的请求结果对象
	 * @return
	 */
	public static RequestResult generateSuccessResult(){
		return RequestResult.newInstance();
	}

	public static RequestResult generateResNotFoundResult(){
		return generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
	}

	public static RequestResult generateResHavedArchivedResult(){
		return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_ARCHIVED_10008,i18nUtil.getMessage("BIZ_RES_ARCHIVED_10008"));
	}

	/*public static void main(String args[]){
		JsonMapper json = JsonMapper.nonEmptyMapper();
		System.out.println(json.toJson(generateErrorResult("aaa","错误测试")));

		PaginatedList list = new PaginatedList();
		list.setRows(new ArrayList(){{
			add("aaa");
			add("bbb");
		}});
		com.lwxf.newstore.webapp.common.model.Pagination pagination = new Pagination();
		pagination.setPageNum(1);
		pagination.setPageSize(2);
		pagination.setTotalCount(50);
		list.setPagination(pagination);

		System.out.println(json.toJson(generateRequestResult(list)));
	}*/
}
