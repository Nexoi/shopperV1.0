package com.seeu.shopper.admin.dao;

import com.seeu.shopper.admin.excelexport.model.ExportOrderModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 17/10/2017.
 */
public interface ExportMapper {

    @Select("SELECT order_basic.create_time as create_time,order_basic.oid,order_receiver.name,email,phone,country,city,detail,postcode,pid,order_product.name as product_name,order_product.norms,amount,pay_method,order_basic.unit,order_ship.ship_name,order_basic.ship_price,order_basic.price,status FROM (order_basic,order_receiver,order_product,order_ship) where order_basic.oid = order_receiver.oid AND order_basic.oid = order_product.oid AND order_basic.oid = order_ship.oid ${condition}")
    List<ExportOrderModel> selectAllNames(@Param("condition") String condition);
}
