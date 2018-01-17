package com.seeu.shopper.adminproduct.product;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import com.seeu.shopper.product.model.ProductComment;
import com.seeu.shopper.product.service.ProductCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 28/08/2017.
 * <p>
 * 配置信息修改
 */
@RestController
@RequestMapping("/api/admin/v1/product/comment")
public class ApiPageProCommentController {

    @Resource
    ConfigService configService;

    @Resource
    ProductCommentService productCommentService;
    /**
     * 测试
     */

    /**
     * 管理员添加回复
     *
     * @param commentText
     * @return
     */

    @PostMapping
    public Result reply(@RequestParam("commentID") Integer commentID, @RequestParam("commentText") String commentText, @RequestAttribute("aid") Integer aid) {
        if (commentText == null || commentID == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // 查询该条回复信息
        ProductComment productComment = productCommentService.findById(commentID);
        if (productComment == null) {
            return ResultGenerator.genFailResult("无此回复信息，请确认后再操作");
        }
        // 更新该信息，表示已经被回复了
        ProductComment pp = new ProductComment();
        pp.setId(productComment.getId());
        pp.setIsReply(true);
        productCommentService.update(pp);


        // 添加一条管理员的记录
        ProductComment comment = new ProductComment();
        comment.setPid(productComment.getPid());
        comment.setFatherId(commentID);// 回复的记录
        comment.setContentHtml(commentText);
        comment.setUid(aid);
        // 调取管理员设定的公司姓名信息
        Config config = configService.findBy("attributeName", "company_reply_name");
        if (config == null) {
            comment.setName("Admin");// 默认为此名称
        } else {
            comment.setName(config.getAttributeValue());// 从配置文件调取
        }
        productCommentService.save(comment);// 新增记录
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改用户回复内容
     *
     * @param comment
     * @return
     */
    @PostMapping("/edit")
    public Result update(ProductComment comment) {
        // 只包含三个信息：star、name、isVisible
        if (comment.getId() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        ProductComment newComment = new ProductComment();
        newComment.setId(comment.getId());
        newComment.setStar(comment.getStar());
        newComment.setName(comment.getName());
        newComment.setIsVisible(comment.getIsVisible());
        productCommentService.update(newComment);
        return ResultGenerator.genSuccessResult();
    }

}
