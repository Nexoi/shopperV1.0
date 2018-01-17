package com.seeu.file;


import com.seeu.file.storage.StorageFileNotFoundException;
import com.seeu.file.storage.StorageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by neo on 19/01/2017.
 * <p>
 * mappping 的 /seeu 表示不同的物理存储仓库，此处为本地 API 服务器，将来扩展仓库的时候，对应 upload 接口 里的仓库名字也应该更改
 */
@RestController
public class FileController {
    private final StorageService storageService;

    @Autowired
    FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/data/**")
    public void loadFile(HttpServletRequest request, HttpServletResponse response) {
        String filePath = request.getRequestURI().substring(1);// 去掉 '/' 这 1 个字符
        try {
            filePath = URLDecoder.decode(filePath, "UTF-8");
            System.out.println(">>" + filePath);
            Resource resource = storageService.loadAsResource(filePath);
            IOUtils.copy(resource.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            response.setStatus(404);
        } catch (StorageFileNotFoundException e) {
            response.setStatus(404);
        }
//        return ResponseEntity.ok().body(resource);
    }
}