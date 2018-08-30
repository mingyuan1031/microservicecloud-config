package com.lwxf.newstore.webapp.common.utils.excel;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lwxf.newstore.webapp.common.utils.WebUtils;

/**
 * 功能：导出excel基本公共方法
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:04
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class BaseExportExcelUtil {

	private static Logger logger = LoggerFactory.getLogger(BaseExportExcelUtil.class);

	private HSSFWorkbook workbook;

	public BaseExportExcelUtil() {
		workbook = new HSSFWorkbook();
	}



	/**
	 * 创建excel头部
	 *
	 * @param sheet
	 * @param headerStyle
	 * @param excelParam
	 */
	public void createHeader(HSSFSheet sheet, HSSFCellStyle headerStyle, ExcelParam excelParam) {
		HSSFRow row = sheet.createRow(0);
		Iterator<String> headers = excelParam.getHeaderMap().values().iterator();
		int idx = 0;
		while (headers.hasNext()) {
			HSSFCell cell = row.createCell(idx++);
			cell.setCellStyle(headerStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers.next());
			cell.setCellValue(text);
		}
	}


	/**
	 * 创建excel文件
	 *
	 * @param title
	 * @param mapList
	 * @param excelParam
	 * @return
	 */
	public HSSFWorkbook createExcel(String title, List<Map<String, Object>> mapList, ExcelParam excelParam) {
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		// 设置这些样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont headerFont = workbook.createFont();
		//headerFont.setColor(HSSFColor.VIOLET.index);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		headerStyle.setFont(headerFont);

		HSSFCellStyle bodyStyle = workbook.createCellStyle();
		// 设置这些样式
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont bodyFont = workbook.createFont();
		//bodyFont.setColor(HSSFColor.VIOLET.index);
		bodyFont.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		bodyStyle.setFont(bodyFont);

		this.createHeader(sheet, headerStyle, excelParam);//创建excel头部
		excelParam.createBody(workbook, sheet, bodyStyle, mapList);//创建excel内容
		return workbook;
	}



	/**
	 * 下载excel文件
	 *
	 * @param title
	 * @param mapList
	 * @param excelParam
	 * @return
	 */
	public void download(String title, List<Map<String, Object>> mapList, ExcelParam excelParam) {
		this.createExcel(title, mapList, excelParam);
		HttpServletResponse response = WebUtils.getHttpServletResponse();
		OutputStream os;
		try {
			os = response.getOutputStream();
			this.getWorkbook().write(os);
			os.close();
		} catch (Exception e) {
			logger.error("下载Excel时异常",e);
		}
	}


	protected HSSFWorkbook getWorkbook() {
		return this.workbook;
	}

}
