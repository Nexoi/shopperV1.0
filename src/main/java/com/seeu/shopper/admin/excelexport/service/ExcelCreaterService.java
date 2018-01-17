package com.seeu.shopper.admin.excelexport.service;

import com.seeu.shopper.admin.excelexport.model.ExportOrderModel;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.product.model.ProductNorm;
import com.seeu.shopper.product.service.ProductNormService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by neo on 17/10/2017.
 */
@Service
public class ExcelCreaterService {

    @Resource
    OrderStatusService orderStatusService;

    public HSSFWorkbook createBook(List<ExportOrderModel> lists) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("学生表一");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // 添加表头内容
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("订单编号");
        headCell = hssfRow.createCell(1);
        headCell.setCellValue("创建时间");
        headCell = hssfRow.createCell(2);
        headCell.setCellValue("订单状态");
        headCell = hssfRow.createCell(3);
        headCell.setCellValue("收货人");
        headCell = hssfRow.createCell(4);
        headCell.setCellValue("邮箱");
        headCell = hssfRow.createCell(5);
        headCell.setCellValue("电话");
        headCell = hssfRow.createCell(6);
        headCell.setCellValue("country");
        headCell = hssfRow.createCell(7);
        headCell.setCellValue("city");
        headCell = hssfRow.createCell(8);
        headCell.setCellValue("详细");
        headCell = hssfRow.createCell(9);
        headCell.setCellValue("邮编");
        headCell = hssfRow.createCell(10);
        headCell.setCellValue("商品编号 PID");
        headCell = hssfRow.createCell(11);
        headCell.setCellValue("商品名");
        headCell = hssfRow.createCell(12);
        headCell.setCellValue("商品规格");
        headCell = hssfRow.createCell(13);
        headCell.setCellValue("商品数量");
        headCell = hssfRow.createCell(14);
        headCell.setCellValue("支付方式");
        headCell = hssfRow.createCell(15);
        headCell.setCellValue("计价单位");
        headCell = hssfRow.createCell(16);
        headCell.setCellValue("物流方式");
        headCell = hssfRow.createCell(17);
        headCell.setCellValue("物流价格");
        headCell = hssfRow.createCell(18);
        headCell.setCellValue("商品价格");
        headCell = hssfRow.createCell(19);
        headCell.setCellValue("订单总价格");
        // 添加数据内容
        String lastOid = "";
        preStore();
        for (int i = 0; i < lists.size(); i++) {
            hssfRow = sheet.createRow((int) i + 1);
            ExportOrderModel model = lists.get(i);
            if (!lastOid.equals(model.getOid())) {
                // 创建独立不重复的行
                HSSFCell cell = hssfRow.createCell(0);
                cell.setCellValue(model.getOid());
                cell = hssfRow.createCell(1);
                cell.setCellValue(model.getCreate_time().toString());
                cell = hssfRow.createCell(2);
                cell.setCellValue(orderStatusService.getStatusNameEnglish(model.getStatus()));
                cell = hssfRow.createCell(3);
                cell.setCellValue(model.getName());
                cell = hssfRow.createCell(4);
                cell.setCellValue(model.getEmail());
                cell = hssfRow.createCell(5);
                cell.setCellValue(model.getPhone());
                cell = hssfRow.createCell(6);
                cell.setCellValue(model.getCountry());
                cell = hssfRow.createCell(7);
                cell.setCellValue(model.getCity());
                cell = hssfRow.createCell(8);
                cell.setCellValue(model.getDetail());
                cell = hssfRow.createCell(9);
                cell.setCellValue(model.getPostcode());
            }
            // 商品信息
            HSSFCell cell = hssfRow.createCell(10);
            cell.setCellValue(model.getPid());
            cell = hssfRow.createCell(11);
            cell.setCellValue(model.getProduct_name());
            cell = hssfRow.createCell(12);
            cell.setCellValue(getNormName(model.getNorms()));
            cell = hssfRow.createCell(13);
            cell.setCellValue(model.getAmount());

            if (!lastOid.equals(model.getOid())) {
                cell = hssfRow.createCell(14);
                cell.setCellValue(model.getPay_method() == 1 ? "PayPal" : "Bank Transfer");
                cell = hssfRow.createCell(15);
                cell.setCellValue(model.getUnit());
                cell = hssfRow.createCell(16);
                cell.setCellValue(model.getShip_name());
                cell = hssfRow.createCell(17);
                cell.setCellValue(model.getShip_price().doubleValue());
                cell = hssfRow.createCell(18);
                cell.setCellValue(model.getPrice().doubleValue());
                cell = hssfRow.createCell(19);
                cell.setCellValue(model.getPrice().doubleValue() + model.getShip_price().doubleValue());
            }
            lastOid = model.getOid();
        }
        postStore();
        // 保存Excel文件
        return workbook;

    }

    private static HashMap<Integer, String> normMap;

    @Resource
    ProductNormService productNormService;

    private void preStore() {
        List<ProductNorm> normList = productNormService.findAll();// 缓存所有
        HashMap<Integer, String> map = new HashMap<>();
        for (ProductNorm norm : normList) {
            map.put(norm.getId(), norm.getNormName());
        }
        normMap = map;
    }

    private void postStore() {
        normMap = null;// 释放所有
    }

    /* 规整数据（ normId -> normName ） */
    private String getNormName(String normIDs) {
        if (normMap == null) {
            preStore();
        }
        String[] normIds = normIDs.split("_");

        String finalName = "";
        for (String normId : normIds) {
            String name = normMap.get(Integer.parseInt(normId));
            finalName += "[" + name + "] ";
        }
        return finalName;
    }
}
