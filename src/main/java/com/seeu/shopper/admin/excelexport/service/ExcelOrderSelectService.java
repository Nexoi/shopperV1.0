package com.seeu.shopper.admin.excelexport.service;

import com.seeu.shopper.admin.dao.ExportMapper;
import com.seeu.shopper.admin.excelexport.model.ExportOrderModel;
import com.seeu.shopper.order.iservice.OrderStatusService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 17/10/2017.
 */
@Service
public class ExcelOrderSelectService {

    @Autowired
    ExcelCreaterService excelCreaterService;
    @Resource
    ExportMapper exportMapper;
    @Resource
    OrderStatusService orderStatusService;

    public HSSFWorkbook createBook(String startTime, String endTime, Integer status) {
        // order_basic.create_time
        String timeCondition = " AND DATEDIFF(order_basic.create_time,'" + startTime + "')>=0";
        timeCondition += " AND DATEDIFF(order_basic.create_time,'" + endTime + "')<=0";

        String condition = "";
//        if (status == 1){
//            condition += "AND (status = 1 or status = 2 or status = 201 or status = 211 or status = 212 or status = 213)";
//        }
        if (status == 1) { // 支付成功
            condition += " AND (status = 3)";
        }
        if (status == 2) { // 已经发货
            condition += " AND (status = 4 or status = 400 or status = 421)";
        }
        if (status == 3) { // 交易完成
            condition += " AND (status = 5 or status = 0)";
        }
        if (status == 4) { // 交易完成
            condition += "";
        }
        List<ExportOrderModel> list = exportMapper.selectAllNames(condition + timeCondition);
        return excelCreaterService.createBook(list);
    }
}
