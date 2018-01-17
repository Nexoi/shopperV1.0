package com.seeu.filesystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.seeu.configurer.DOMAIN;
import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.filesystem.service.FileUploadService;
import com.seeu.oauth.OAuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by neo on 16/01/2017.
 */
@RestController
@RequestMapping("/api/upload")
public class TypeUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    DOMAIN domain;
    /**
     *
     * 上传至UID对应的文件夹，文件存储读取操作时不受 type 影响，只与 UID/filename 有关，文件名不带后缀
     *
     * @param file
     * @param type
     * @param uid  参数为系统自动注入，用户不可干预
     * @return
     */
    @PostMapping("/{type}")
    public Result upload(@RequestParam(value = "file") MultipartFile file, @PathVariable("type") String type, @RequestAttribute("uid") String uid, @RequestAttribute("type") String typeOauth) {
        if (!OAuthType.isAvailable(typeOauth)) {
            return ResultGenerator.genNoAuthResult("无权操作");
        }
        if (file == null || file.isEmpty()) {
            return ResultGenerator.genNoContentResult("上传文件为空");
        }

        // handle path
        if (uid == null || uid.trim().length() == 0) uid = "000";
        String path = uid;

        // handle type (.png?)
        if (type == null)
            type = "";
        if (type.contains(".")) {
            type = type.replace(".","");
        }

        String filename = fileUploadService.upload(path, file);// 纯名字，带路径，如：4216882/8hxgu-ewbd-dwejdd9.png
        if (filename == null) {
            return ResultGenerator.genFailResult("上传失败");
        } else {
            JSONObject jo = new JSONObject();
            jo.put("filename", domain.getDomain()+"store1/" + type + "/" + filename);// 加域名即可访问
            // >>> /seeu/png/001/8hxgu-ewbd-dwejdd9.png
//            jo.put("filepath", path);
            return ResultGenerator.genSuccessResult(jo);
        }
    }
}
