package com.seeu.shopper.adminproduct.product;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.core.ServiceException;
import com.seeu.filesystem.service.FileUploadService;
import com.seeu.shopper.adminproduct.model.ProductBasicModel;
import com.seeu.shopper.adminproduct.model.ProductNormModel;
import com.seeu.shopper.adminproduct.model.ProductPictureModel;
import com.seeu.shopper.adminproduct.service.PageProductNormService;
import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.product.model.*;
import com.seeu.shopper.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 26/08/2017.
 * <p>
 * 提供给 Add 和 Update 页面使用，用于增添／修改商品数据
 */
@RestController
@RequestMapping("/api/admin/v1/product")
public class ApiPageProAddController {
    @Autowired
    FileUploadService fileUploadService;
    @Resource
    ProductService productService;
    @Resource
    PageProductService pageProductService;
    @Resource
    ProductIntroService productIntroService;
    @Resource
    ProductImgService productImgService;
    @Resource
    ProductAttributeService productAttributeService;
    @Resource
    ProductNormService productNormService;
    @Resource
    PageProductNormService pageProductNormService;
    @Resource
    ProductStockService productStockService;

    @GetMapping("/{pid}")
    public Result get(@PathVariable("pid") Integer pid) {
        if (pid == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        Product product = productService.findById(pid);
        if (product == null) {
            return ResultGenerator.genNoContentResult("无此商品信息");
        }
        // 规整化
        ProductBasicModel productBasic = new ProductBasicModel();
        productBasic.setPid(product.getPid());
        productBasic.setStatus(product.getStatus());
        productBasic.setName(product.getName());
        productBasic.setSubname(product.getSubname());
        productBasic.setCategory(product.getCategoryId());
        productBasic.setPrice(product.getCurrentPrice());
        productBasic.setOrginPrice(product.getOriginPrice());
        productBasic.setKeyword(product.getKeyword());
        productBasic.setWeight(product.getWeight());
        productBasic.setTag(product.getTag());
//        productBasic.setPicture(null);
        return ResultGenerator.genSuccessResult(productBasic);
    }

    @PostMapping
    public Result add(ProductBasicModel productBasic) {
        Product product = new Product();
        product.setStatus(productBasic.getStatus());
        product.setName(productBasic.getName());
        product.setSubname(productBasic.getSubname());
        product.setCategoryId(productBasic.getCategory());
        product.setCurrentPrice(productBasic.getPrice());
        product.setOriginPrice(productBasic.getOrginPrice());
        product.setKeyword(productBasic.getKeyword());
        product.setWeight(productBasic.getWeight());
        product.setTag(productBasic.getTag());
        // 存储文件
        if (productBasic.getPicture() != null) {
            try {
                String picPath = fileUploadService.uploadWithDomain("image", "product", productBasic.getPicture());
                product.setImg(picPath);
                pageProductService.insertProductWithImg(product);
            } catch (ServiceException e) {
                return ResultGenerator.genExceptionResult(e.getMessage());
            }
        } else {
            pageProductService.insertProduct(product);
        }
        // 初始化 intro 表
        ProductIntro intro = new ProductIntro();
        intro.setPid(product.getPid());
        intro.setHtml("No Content Display");
        productIntroService.save(intro);
        return ResultGenerator.genSuccessResult(product);
    }

    @PostMapping("/edit")
    public Result put(ProductBasicModel productBasic) {
        if (productBasic.getPid() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        Product product = new Product();
        product.setPid(productBasic.getPid());
        product.setStatus(productBasic.getStatus());
        product.setName(productBasic.getName());
        product.setSubname(productBasic.getSubname());
        product.setCategoryId(productBasic.getCategory());
        product.setCurrentPrice(productBasic.getPrice());
        product.setOriginPrice(productBasic.getOrginPrice());
        product.setKeyword(productBasic.getKeyword());
        product.setWeight(productBasic.getWeight());
        product.setTag(productBasic.getTag());
        // 存储文件
        if (productBasic.getPicture() != null) {
            try {
                String picPath = fileUploadService.uploadWithDomain("image", "product", productBasic.getPicture());
                product.setImg(picPath);
                productService.update(product);
            } catch (ServiceException e) {
                return ResultGenerator.genExceptionResult(e.getMessage());
            }
        } else {
            productService.update(product);
        }
        return ResultGenerator.genSuccessResult(product);
    }


    @DeleteMapping("/{pid}")
    public Result delete(@PathVariable("pid") Integer pid) {
        if (pid == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        productService.deleteById(pid);

        Condition condition = new Condition(ProductStock.class);
        condition.createCriteria().andCondition("pid = " + pid);

        // 删除介绍信息
        productIntroService.deleteById(pid);
        // 删除图片信息
        productImgService.deleteByCondition(condition);
        // 删除属性信息
        productAttributeService.deleteByCondition(condition);
        // 删除规格信息
        productNormService.deleteByCondition(condition);
        // 删除库存信息
        productStockService.deleteByCondition(condition);
        // 删除评论信息
        // TODO
        return ResultGenerator.genSuccessResult();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/intro/{pid}")
    public Result getIntro(@PathVariable("pid") Integer pid) {
        if (pid == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        ProductIntro productIntro = productIntroService.findById(pid);// 主键就是 pid
//        暂时注释掉该操作，若无此信息，可以正常通过 PUT 进行添加记录
//        if (productIntro == null)
//            return ResultGenerator.genNoContentResult("无该商品详情信息，请确认参数后再操作");
        return ResultGenerator.genSuccessResult(productIntro);
    }

    @PutMapping("/intro")
    public Result updateIntro(ProductIntro intro) {
        if (intro.getPid() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }

        int i = productIntroService.update(intro);
        if (i == 0) {
            // 增添记录
            productIntroService.save(intro);
        }
        return ResultGenerator.genSuccessResult();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/picture/{pid}")
    public Result getPicture(@PathVariable("pid") Integer pid) {
        Condition condition = new Condition(ProductImg.class);
        condition.createCriteria().andCondition("pid = " + pid);
        List<ProductImg> productImgs = productImgService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(productImgs);
    }

    @PostMapping("/picture")
    public Result addPicture(ProductPictureModel productPictureModel) {
        if (productPictureModel.getPid() == null || productPictureModel.getPicture() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }

        ProductImg productImg = new ProductImg();
        productImg.setPid(productPictureModel.getPid());
        productImg.setImgOrder(productPictureModel.getImg_order());
        // 存储文件
        try {
            String picPath = fileUploadService.uploadWithDomain("image", "product", productPictureModel.getPicture());
            productImg.setImgUrl(picPath);
            // save
            productImgService.save(productImg);
        } catch (ServiceException e) {
            return ResultGenerator.genExceptionResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/picture")
    public Result updatePicture(ProductPictureModel productPictureModel) {
        if (productPictureModel.getPid() == null || productPictureModel.getPicture() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        ProductImg productImg = new ProductImg();
        productImg.setPid(productPictureModel.getPid());
        productImg.setImgOrder(productPictureModel.getImg_order());
        // 存储文件
        try {
            String picPath = fileUploadService.uploadWithDomain("image", "product", productPictureModel.getPicture());
            productImg.setImgUrl(picPath);
            // update
            productImgService.update(productImg);
        } catch (ServiceException e) {
            return ResultGenerator.genExceptionResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/picture/{id}")
    public Result delPicture(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        productImgService.deleteById(id);
        // 文件删除？暂定不删除文件 TODO

        return ResultGenerator.genSuccessResult();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/attribute/{pid}")
    public Result getAttr(@PathVariable("pid") Integer pid) {
        if (pid == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        Condition condition = new Condition(ProductAttribute.class);
        condition.createCriteria().andCondition("pid = " + pid);
        List<ProductAttribute> productAttributes = productAttributeService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(productAttributes);
    }

    @PostMapping("/attribute")
    public Result addAttr(ProductAttribute attribute) {
        if (attribute.getPid() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        productAttributeService.save(attribute);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/attribute/{id}")
    public Result deleteAttr(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        productAttributeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/norm/{pid}")
    public Result getNorm(@PathVariable("pid") Integer pid) {
        if (pid == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // 规整化数据
        // 选取规格名（ team_id = -1 ）
        Condition condition = new Condition(ProductNorm.class);
        condition.createCriteria().andCondition("team_id = -1 AND pid = " + pid);
        List<ProductNorm> productNorms = productNormService.findByCondition(condition);
        if (productNorms.size() == 0) {
            return ResultGenerator.genNoContentResult("无此商品规格信息");
        }
        JSONArray norms = new JSONArray();
        //添加子类（规格值）
        for (ProductNorm productNorm : productNorms) {
            Condition cond = new Condition(ProductNorm.class);
            cond.createCriteria().andCondition("team_id = " + productNorm.getId());
            List<ProductNorm> normValues = productNormService.findByCondition(cond);
            JSONObject jo = new JSONObject();
            jo.put("normId", productNorm.getId());
            jo.put("normName", productNorm.getNormName());
            jo.put("normValues", normValues);
            norms.add(jo);
        }
        return ResultGenerator.genSuccessResult(norms);
    }

    @PostMapping("/norm")
    public Result addNorm(ProductNormModel normModel) {
        if (normModel.getPid() == null || normModel.getNormValues() == null || normModel.getNormName() == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // 添加 normName 记录
        ProductNorm normName = new ProductNorm();
        normName.setPid(normModel.getPid());
        normName.setNormName(normModel.getNormName());
        normName.setTeamId(-1);// 表示为 team_id
        pageProductNormService.insertProductNorm(normName);
        // 添加 normValues 记录
        for (String nValue : normModel.getNormValues()) {
            if (nValue == null || nValue.trim().length() == 0) {
                continue;
            }
            ProductNorm normValue = new ProductNorm();
            normValue.setPid(normModel.getPid());
            normValue.setNormName(nValue);
            normValue.setTeamId(normName.getId());
            pageProductNormService.insertProductNorm(normValue);
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/norm/{id}")
    public Result deleteNorm(@PathVariable("id") Integer id) {
        if (id == null) {
            return ResultGenerator.genFailResult("传入参数不完整");
        }
        // 把子类都删光
        // 删除 normName
        productNormService.deleteById(id);
        // 删除 normValues
        Condition condition = new Condition(ProductNorm.class);
        condition.createCriteria().andCondition("team_id = " + id);
        productNormService.deleteByCondition(condition);

        return ResultGenerator.genSuccessResult();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/stock/{pid}")
    public Result getStock(@PathVariable("pid") Integer pid) {
        Condition condition = new Condition(ProductStock.class);
        condition.createCriteria().andCondition("pid = " + pid);
        List<ProductStock> productStocks = productStockService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(productStocks);
    }

    @PutMapping("/stock")
    public Result addOrUpdateStock(ProductStock stock) {
        // 查看mysql，如果没有就增加，否则更新
        if (stock.getNormIds() == null
                || stock.getPid() == null
                || stock.getCurrentStock() == null
                || stock.getPrice() == null
                || stock.getOrginPrice() == null) {
            return ResultGenerator.genNoContentResult("传入参数不完整");// 204
        }

        // 更新
        Condition condition = new Condition(ProductStock.class);
        condition.createCriteria().andCondition("norm_ids = '" + stock.getNormIds() + "'");
        int i = productStockService.updateCondition(stock, condition);
        if (i == 0) { // 如果未更新（无可更新的记录）
            // 则添加
            productStockService.save(stock);
        }
        // 添加日志记录
        // 暂时不添加，更新库存的时候再添加日志
        return ResultGenerator.genSuccessResult();
    }
}
