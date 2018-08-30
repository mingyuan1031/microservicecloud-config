package com.lwxf.newstore.webapp.common.utils.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 功能：excel的参数值的接口
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:04
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public interface ExcelParam {


	/**
	 * 定义excel标题
	 *
	 * @return
	 */
	Map<String, String> getHeaderMap();


	/**
	 * 创建excel内容
	 * 解析数据
	 *
	 * @param workbook
	 * @param sheet
	 * @param bodyStyle
	 * @param mapList
	 */
	void createBody(HSSFWorkbook workbook, HSSFSheet sheet, HSSFCellStyle bodyStyle, List<Map<String, Object>> mapList);

}
