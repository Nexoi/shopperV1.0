package com.seeu.filesystem.controller;


import com.seeu.core.ServiceException;
import com.seeu.filesystem.service.FileDownloadServiceII;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by neo on 19/01/2017.
 * <p>
 * mappping 的 /seeu 表示不同的物理存储仓库，此处为本地 API 服务器，将来扩展仓库的时候，对应 upload 接口 里的仓库名字也应该更改
 */
@RestController
@RequestMapping("/store1")
public class TypeDownloadControllerII {
    @Autowired
    FileDownloadServiceII fileDownloadService;

    /**
     * type 将决定文件的格式，注入至 Header 中表示
     *
     * @param response
     * @param filename
     * @param type
     * @param UID
     * @throws IOException
     */
    @GetMapping({"/{type}/{UID}/{filename:.+}", "/{type}/{UID}/{filename}.png"})
    public void loadFile(HttpServletResponse response, @PathVariable(value = "filename") String filename, @PathVariable(value = "type") String type, @PathVariable(value = "UID") String UID) throws IOException {
        InputStream in = fileDownloadService.getFile(UID, filename);// 取文件只与 UID/filename 有关，与 type 无关
        if (in == null) {
            throw new ServiceException("404#无此文件：/" + UID + "/" + filename);
        }
        switch (type) {
            case "code":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            default:
                response.setContentType(MediaType.ALL_VALUE);
                break;

        }
        IOUtils.copy(in, response.getOutputStream());
    }

    /**
     * 获取对应缩略图，只需要在 uri 后面加 /thumb 即可
     *
     * @param response
     * @param filename
     * @param type
     * @param UID
     * @throws IOException
     */
    @GetMapping("/{type}/{UID}/{filename:.+}/thumb")
    public void loadThumbFile(HttpServletResponse response, @PathVariable(value = "filename") String filename, @PathVariable(value = "type") String type, @PathVariable(value = "UID") String UID) throws IOException {
        InputStream in = fileDownloadService.getThumbFile(UID, filename);// 取文件只与 UID/filename 有关，与 type 无关
        if (in == null) {
            throw new ServiceException("404#无此文件：/" + UID + "/" + filename);
        }
        switch (type) {
            case "code":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
            default:
                response.setContentType(MediaType.ALL_VALUE);
                break;

        }
        IOUtils.copy(in, response.getOutputStream());
    }
}
