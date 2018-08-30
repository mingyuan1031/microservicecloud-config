package com.lwxf.newstore.webapp.common.utils.excel.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lwxf.newstore.webapp.common.utils.excel.ExcelParam;

/**
 * 功能：任务导出excel的参数值
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:04
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class TaskExcelParam implements ExcelParam {


	/**
	 * 定义excel标题
	 *
	 * @return
	 */
	@Override
	public Map<String, String> getHeaderMap() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>() {{
			put("no", "编号");
			put("type", "类型");
			put("done", "状态");
			put("name", "标题");
			put("assignee", "负责人");
			put("followers", "参与人");
			put("startDate", "开始时间");
			put("dueDate", "截止时间");
			put("tag", "标签");
			put("label", "标记");
			put("creator", "创建人");
			put("created", "创建时间");
			put("descr", "描述");
		}};
		return headerMap;
	}


	/**
	 * 创建excel内容
	 * 解析数据
	 *
	 * @param workbook
	 * @param sheet
	 * @param bodyStyle
	 * @param mapList
	 */
	@Override
	public void createBody(HSSFWorkbook workbook, HSSFSheet sheet, HSSFCellStyle bodyStyle, List<Map<String, Object>> mapList) {
		//遍历集合数据,产生数据行
		HSSFRow row;
		int colIdx;
		HSSFCellStyle rightAlignStyle = workbook.createCellStyle();
		rightAlignStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		rightAlignStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		rightAlignStyle.setFont(bodyStyle.getFont(workbook));

		HSSFCellStyle leftAlignStyle = workbook.createCellStyle();
		leftAlignStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		leftAlignStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		leftAlignStyle.setFont(bodyStyle.getFont(workbook));
		//leftAlignStyle.setWrapText(true);
		//leftAlignStyle.setHidden(true);

		HSSFCellStyle noStyle = workbook.createCellStyle();
		noStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		noStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font = noStyle.getFont(workbook);
		font.setColor(HSSFColor.BLUE.index);
		font.setFontHeightInPoints((short) 12);
		noStyle.setFont(font);

		for (int m = 0, len = mapList.size(); m < len; m++) {
			Map<String, Object> backlog = mapList.get(m);
			row = sheet.createRow(m + 1);
			Iterator<String> keys = getHeaderMap().keySet().iterator();
			colIdx = 0;
			while (keys.hasNext()) {
				String key = keys.next();
				Object value = backlog.get(key);
				HSSFCell cell = row.createCell(colIdx++);
				cell.setCellStyle(bodyStyle);
				if (null != value) {
					switch (key) {
						case "no":
							String[] nolink = value.toString().split(",");
							cell.setCellValue(nolink[0]);
							HSSFHyperlink hyperlink = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
							hyperlink.setAddress(nolink[1]);
							cell.setHyperlink(hyperlink);
							cell.setCellStyle(noStyle);
							break;
						case "type":
						case "done":
						case "name":
						case "assignee":
						case "followers":
						case "startDate":
						case "dueDate":
						case "tag":
						case "label":
						case "creator":
						case "created":
						case "descr":
							cell.setCellStyle(leftAlignStyle);
							cell.setCellValue(value.toString());
							break;
						default:
							cell.setCellValue(value.toString());
					}
				}
			}
		}
	}


}
