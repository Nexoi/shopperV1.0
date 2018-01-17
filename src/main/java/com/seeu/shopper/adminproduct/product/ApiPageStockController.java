package com.seeu.shopper.adminproduct.product;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.model.ProductStockLog;
import com.seeu.shopper.product.service.ProductStockLogService;
import com.seeu.shopper.product.service.ProductStockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 28/08/2017.
 * <p>
 * 库存信息修改
 */
@RestController
@RequestMapping("/api/admin/v1/stock")
public class ApiPageStockController {

    @Resource
    ProductStockService productStockService;
    @Resource
    ProductStockLogService productStockLogService;

    /**
     * 库存： 出／入库操作
     *
     * @param stockLog
     * @param aid
     * @return
     */
    @PostMapping
    public Result opStock(ProductStockLog stockLog, @RequestAttribute(value = "aid", required = false) Integer aid) {
        if (stockLog.getSid() == null || stockLog.getType() == null || stockLog.getAmount() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        if (stockLog.getAmount() == 0) {
            return ResultGenerator.genSuccessResult("出／入库数量为 0 ，库存未发生修改");
        }
        stockLog.setOpAdminId(aid);
        // 更新库存
        ProductStock stock = productStockService.findById(stockLog.getSid());// 查找记录
        if (stock == null) {
            return ResultGenerator.genFailResult("无此库存信息");
        }
        // 执行更新预处理
        ProductStock newStock = new ProductStock();
        // 库存
        if (stockLog.getType()) {
            // 若为真，表示入库
            Integer currentStock = stock.getCurrentStock();
            currentStock += stockLog.getAmount();
            newStock.setCurrentStock(currentStock);
            stock.setCurrentStock(currentStock);
        } else {
            // 为假，出库
            Integer currentStock = stock.getCurrentStock();
            currentStock -= stockLog.getAmount();
            if (currentStock < 0) { // 不为负值
                currentStock = 0;
                stockLog.setAmount(stock.getCurrentStock()); // 只能减去现有的库存量
            }
            newStock.setCurrentStock(currentStock);
            stock.setCurrentStock(currentStock);
        }
        newStock.setSid(stock.getSid());
        // update
        int i = productStockService.update(newStock);
        if (i == 0) {
            // 插入新库存记录
            productStockService.save(stock);
        }

        // 插入操作记录
        stockLog.setId(null);
        productStockLogService.save(stockLog);
        return ResultGenerator.genSuccessResult();
    }
}
