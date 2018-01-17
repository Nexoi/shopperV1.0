package com.seeu.shopper.admin.srcmanager;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.filesystem.service.FileUploadService;
import com.seeu.shopper.admin.model.ResourceModel;
import com.seeu.shopper.store.model.DbStore;
import com.seeu.shopper.store.service.DbStoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 23/09/2017.
 */

@RestController
@RequestMapping("/api/admin/v1/resource")
public class ApiResourceController {

    @Resource
    FileUploadService fileUploadService;
    @Resource
    DbStoreService dbStoreService;

    @PostMapping
    public Result addResource(ResourceModel resourceModel) {
        if (resourceModel == null) return ResultGenerator.genNoContentResult("上传内容不能为空");

        String path;
        if (1 == resourceModel.getType()) {
            path = fileUploadService.uploadWithDomain("file", "image", resourceModel.getResource());
        } else {
            path = fileUploadService.uploadWithDomain("file", "resource", resourceModel.getResource());
        }
        DbStore dbStore = new DbStore();
        dbStore.setName(resourceModel.getName());
        dbStore.setType(resourceModel.getType());
        dbStore.setUrl(path);
        dbStoreService.save(dbStore);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable("id") Integer id) {
        dbStoreService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
