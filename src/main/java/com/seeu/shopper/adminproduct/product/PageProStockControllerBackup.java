//package com.seeu.shopper.adminproduct.product;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.seeu.shopper.adminproduct.model.FullModel;
//import com.seeu.shopper.adminproduct.model.ProductStockModel;
//import com.seeu.shopper.adminproduct.service.PageProductService;
//import com.seeu.shopper.product.model.ProductStock;
//import com.seeu.shopper.product.model.ProductStockLog;
//import com.seeu.shopper.product.service.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by neo on 25/08/2017.
// */
//@Controller
//@RequestMapping("/adminx")
//public class PageProStockControllerBackup {
//
//    @Resource
//    ProductStockService productStockService;
//    @Resource
//    ProductService productService;
//    @Resource
//    PageProductService pageProductService;
//    @Resource
//    ProductCategoryService productCategoryService;
//    @Resource
//    ProductNormService productNormService;
//
//    @Resource
//    ProductStockLogService productStockLogService;
//
//    @RequestMapping("/product-stock.html")
//    public String userlist(
//            Model model,
//            @RequestParam(value = "wd", required = false) String wd,
//            @RequestParam("page") Integer page,
//            @RequestParam("size") Integer size,
//            @RequestParam(value = "isUning", required = false) Boolean isUning) {
//
//        if (isUning == null) {
//            isUning = false; // 默认显示已经启用的，不显示所有信息
//        }
//
//        // 处理筛选条件
//        // 查询
//        PageInfo pageInfo = null;
//        if (wd != null && wd.trim().length() != 0) {
//            wd = wd.trim();
//            // 根据 wd 搜索商品库，再查询对应库存数据
//            PageHelper.startPage(page, size);
//            List<ProductStockModel> productStockModels = new ArrayList<>();
//            List<Integer> pids = searchByWord(wd);
//            for (Integer pid : pids) {
//                Condition condition = new Condition(ProductStock.class);
//                if (isUning) { // 显示所有信息
//                    condition.createCriteria().andCondition("pid = " + pid);
//                } else {
//                    condition.createCriteria().andCondition("pid = " + pid + " AND is_ing = true");
//                }
//                List<ProductStock> stocks = productStockService.findByCondition(condition);
//                if (stocks.size() != 0) {
//                    // 获得库存信息，形成最终数据
//                    productStockModels.addAll(formList(stocks));
//                }
//            }
//            pageInfo = new PageInfo(productStockModels);
//        } else {
//            // 查询所有库存
//            PageHelper.startPage(page, size);
//            List<ProductStock> stocks = null;
//            if (isUning) { // 显示所有信息
//                stocks = productStockService.findAll();
//            } else { // 显示已经启用的信息
//                Condition condition = new Condition(ProductStock.class);
//                condition.createCriteria().andCondition(" is_ing = true ");
//                stocks = productStockService.findByCondition(condition);
//            }
//            pageInfo = new PageInfo(formList(stocks));
//        }
//        if (wd == null) {
//            wd = "";
//        }
//
//        model.addAttribute("isUning", isUning);
//        model.addAttribute("word", wd);
//        model.addAttribute("pagelist", pageInfo.getList());
//        model.addAttribute("page", pageInfo);
//        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码
//        return "adminx/product-stock";
//    }
//
//    @RequestMapping("/product-stock-modal.html")
//    public String showDetail(
//            Model model,
//            @RequestParam("sid") Integer sid) {
//        if (sid == null) {
//            return "404";// 无此库存
//        }
//        // 注入数据
//        PageHelper.startPage(1, 10);
//        Condition condition = new Condition(ProductStockLog.class);
//        condition.createCriteria().andCondition("sid = " + sid);
//        condition.setOrderByClause("create_time desc");
//        condition.orderBy("createTime").desc();
//        List<ProductStockLog> stockLogs = productStockLogService.findByCondition(condition);
//        PageInfo pageInfo = new PageInfo(stockLogs);
//        pageInfo.setOrderBy("createTime desc");
//
//        model.addAttribute("sid",sid);
//        model.addAttribute("pagelist", pageInfo.getList());
//
//        return "adminx/product-stock-modal";
//    }
//
//
//    /**
//     * 查找所有的商品信息，换取商品名
//     * 查找规格信息，换取规格名
//     *
//     * @param stocks
//     */
//    private List<ProductStockModel> formList(List<ProductStock> stocks) {
//        HashMap<Integer, String> productNames = getProductNameList();
//        HashMap<String, String> productNorms = getProductNorms();
//
//        List<ProductStockModel> stockModels = new ArrayList<ProductStockModel>();
//        for (ProductStock stock : stocks) {
//            // 数据转存
//            ProductStockModel stockModel = new ProductStockModel();
//            stockModel.setSid(stock.getSid());
//            stockModel.setPid(stock.getPid());
//            stockModel.setCurrentStock(stock.getCurrentStock());
//            stockModel.setNormIds(stock.getNormIds());
//            stockModel.setOrginPrice(stock.getOrginPrice());
//            stockModel.setPrice(stock.getPrice());
//            stockModel.setUpdateDate(stock.getUpdateDate());
//            if (stock.getIsIng().equals(true)) {
//                stockModel.setIsIng(true);
//            } else {
//                stockModel.setIsIng(false);
//            }
//            // 加载新信息
//            stockModel.setProductName(productNames.get(stock.getPid()));
//            String normValues = "";
//            if (stock.getNormIds() != null) {
//                String[] ids = stock.getNormIds().split("_");
//                for (String normID : ids) {
//                    normValues += " [" + productNorms.get(normID) + "] ";
//                }
//            }
//            stockModel.setNormValues(normValues);
//            stockModels.add(stockModel);
//        }
//        return stockModels;
//    }
//
//    /**
//     * 根据搜索字搜索出对应商品信息（商品 id 列表）
//     */
//    private List<Integer> searchByWord(String wd) {
//        HashMap<Integer, String> productNames = getProductNameList();
//        List<Integer> pids = new ArrayList<>();
//        for (Map.Entry<Integer, String> entry : productNames.entrySet()) {
//            if (entry.getValue().contains(wd)) {
//                pids.add(entry.getKey());
//            }
//        }
//        return pids;
//    }
//
//    private HashMap<Integer, String> getProductNameList() {
//        // 全局只调用了一次该刷新方法？？？？
//        FullModel.flushProductNames();
//        return FullModel.getProductNames(pageProductService);
//    }
//
//    private HashMap<String, String> getProductNorms() {
//        return FullModel.getProductNorms(productNormService);
//    }
//}
