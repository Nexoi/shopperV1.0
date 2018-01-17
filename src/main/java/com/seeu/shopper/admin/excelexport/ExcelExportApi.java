package com.seeu.shopper.admin.excelexport;

import com.seeu.shopper.admin.excelexport.service.ExcelOrderSelectService;
import com.seeu.shopper.order.iservice.OrderStatusService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by neo on 17/10/2017.
 */
@Controller
@RequestMapping("/api/admin/v1/export")
public class ExcelExportApi {

    @Autowired
    ExcelOrderSelectService excelOrderSelectService;

    /**
     * 订单状态分三种：支付成功、已经发货、交易完毕、所有订单
     *
     * @param startTime   2017-09-10
     * @param endTime     2017-09-10
     * @param orderStatus 1\2\3
     * @return
     */
    @GetMapping("/order*.xls")
    public void exportOrder(@RequestParam String startTime,
                            @RequestParam String endTime,
                            @RequestParam Integer orderStatus,
                            HttpServletResponse response) throws IOException {
        if (startTime.length() != 10 || endTime.length() != 10) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=export_order_info.xls");
            response.getWriter().write("参数错误，请输入正确的时间表达式[如：2017-01-01]");
            return;
        }
        HSSFWorkbook workbook = excelOrderSelectService.createBook(startTime, endTime, orderStatus);
        workbook.write(response.getOutputStream());
    }
}
