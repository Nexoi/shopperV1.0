package com.seeu.shopper.adminproduct.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.adminproduct.model.FullModel;
import com.seeu.shopper.adminproduct.model.ProductCommentModel;
import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.product.model.*;
import com.seeu.shopper.product.service.ProductCommentService;
import com.seeu.shopper.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 25/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageProCommentController {
    @Resource
    ProductService productService;
    @Resource
    PageProductService pageProductService;
    @Resource
    ProductCommentService productCommentService;

    @RequestMapping("/product-comment.html")
    public String userlist(
            Model model,
            @RequestParam(value = "wd", required = false) String wd,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {

        // 查询
        PageInfo pageInfo = null;
        if (wd != null && wd.trim().length() != 0) {
            // 根据 wd 搜索商品库，再查询对应库存数据
            PageHelper.startPage(page, size);
            List<ProductCommentModel> productCommentModels = new ArrayList<>();
            List<Integer> pids = searchByWord(wd);
            for (Integer pid : pids) {
                Condition condition = new Condition(ProductComment.class);
                condition.createCriteria().andCondition("pid = " + pid +" AND father_id = -1"); // 只查看新起的回复，子回复过滤掉
                List<ProductComment> productComments = productCommentService.findByCondition(condition);
                if (productComments.size() != 0) {
                    // 获得库存信息，形成最终数据
                    productCommentModels.addAll(formList(productComments));
                }
            }
            pageInfo = new PageInfo(productCommentModels);
        } else {
            // 查询所有库存
            PageHelper.startPage(page, size);
            Condition condition = new Condition(ProductComment.class);
            condition.createCriteria().andCondition("father_id = -1"); // 只查看新起的回复，子回复过滤掉
            List<ProductComment> productComments = productCommentService.findByCondition(condition);
            pageInfo = new PageInfo(formList(productComments));
        }
        if (wd == null) {
            wd = "";
        }

        model.addAttribute("word", wd);
        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码

        model.addAttribute("currentPage", page);
        return "adminx/product-comment";
    }

    @RequestMapping("/product-comment-modal.html")
    public String showDetail(
            Model model,
            @RequestParam("commentid") Integer commentID) {
        if (commentID == null) {
            return "404";// 无此评论
        }
        // 查询数据
        ProductComment comment = productCommentService.findById(commentID);
        if (comment == null) {
            return "404";
        }
        // 注入数据
        model.addAttribute("comment", comment);
        return "adminx/product-comment-modal";
    }


    private List<ProductCommentModel> formList(List<ProductComment> productComments) {
        HashMap<Integer, String> productNames = getProductNameList();
        List<ProductCommentModel> productCommentModels = new ArrayList<>();
        for (ProductComment product : productComments) {
            Integer pid = product.getPid();
            String name = productNames.get(pid);

            // 转存数据
            ProductCommentModel model = new ProductCommentModel();
            model.setPid(pid);
            model.setProductName(name);
            model.setName(product.getName());
            model.setContentHtml(product.getContentHtml());
            model.setCreateDate(product.getCreateDate());
            model.setFatherId(product.getFatherId());
            model.setId(product.getId());
            model.setIsReply(product.getIsReply());
            model.setStar(product.getStar());
            model.setUid(product.getUid());
            model.setIsVisible(product.getIsVisible());
            productCommentModels.add(model);
        }
        return productCommentModels;
    }


    /**
     * 根据搜索字搜索出对应商品信息（商品 id 列表）
     */
    private List<Integer> searchByWord(String wd) {
        HashMap<Integer, String> productNames = getProductNameList();
        List<Integer> pids = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : productNames.entrySet()) {
            if (entry.getValue().contains(wd)) {
                pids.add(entry.getKey());
            }
        }
        return pids;
    }

    private HashMap<Integer, String> getProductNameList() {
        return FullModel.getProductNames(pageProductService);
    }
}
