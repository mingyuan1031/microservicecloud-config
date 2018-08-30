package com.lwxf.newstore.webapp.common.utils.excel.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lwxf.newstore.webapp.common.utils.excel.ExcelParam;


/**
 * 功能：优惠码/优惠券导出excel的参数值
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 15:34:04
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CouponcodeExcelParam implements ExcelParam {


	/**
	 * 定义excel标题
	 *
	 * @return
	 */
	@Override
	public Map<String, String> getHeaderMap() {
		Map<String, String> headerMap = new LinkedHashMap<String, String>() {{
			put("id", "ID");
			put("group", "优惠组");
			put("type", "类型");
			put("mode", "优惠方式");
			put("amount", "优惠结果");
			put("startDate", "开始日期");
			put("endDate", "截止日期");
			put("state", "状态");
		}};
		return headerMap;
	}


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
						case "id":
						case "group":
						case "type":
						case "mode":
						case "amount":
						case "startDate":
						case "endDate":
						case "state":
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
