package com.seeu.filesystem.service;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.file.storage.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by neo on 18/01/2017.
 */
@Service
public class FileDownloadService {

    private final Path mypath;

    public FileDownloadService(StorageProperties properties) {
        this.mypath = Paths.get(properties.getLocation());
    }

    public Object loadFile(String UID, String filename) {
        return loadFile(mypath.resolve(UID), filename);
    }

    public void downloadFile2(HttpServletResponse response, String UID, String filename) {
        download(response, mypath.resolve(UID), filename);
    }


    private Object loadFile(Path path, String filename) {
        if (checkName(filename)) {
            try {
                return loadAsResource(path, filename);
            } catch (StorageFileNotFoundException e) {
                return ResultGenerator.genNoContentResult("找不到文件");
            }
        } else {
            return ResultGenerator.genFailResult("文件名不合法");
        }
    }

    /**
     * @param filename like: file-abc or xxx/yy.png
     * @return
     * @throws StorageFileNotFoundException
     */
    public Resource loadAsResource(Path path, String filename) throws StorageFileNotFoundException {
        try {
            Path file = path.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                if (file.toFile().isDirectory()) {
                    throw new StorageFileNotFoundException("File is a directory : " + filename);
                }
                if (!resource.isReadable()) {
                    throw new StorageFileNotFoundException("Could not read file : " + filename);
                }
                return resource;
            } else {
                throw new StorageFileNotFoundException("File not exist : " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file - MalformedURLException : " + filename);
        }
    }


    //    @RequestMapping(value = "check")
    private boolean checkName(String filename) {
//        LOGGER.warn(filename);
        if (filename == null || filename.trim().equals("")) {
            return false;
        }
        String pattern = "^(?!_)(?!.*?_$)[a-zA-Z0-9-._\u4e00-\u9fa5]+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(filename);
        return m.matches();
    }


    /**
     * success
     *
     * @param resp
     * @param path
     * @param filename
     */
    public void download(HttpServletResponse resp, Path path, String filename) {
        File file = path.resolve(filename).toFile();
        if (file != null && file.exists()) {
            resp.setHeader("Content-type", "application/octet-stream");
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = resp.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            byte[] buff = new byte[1024];
            OutputStream os = null;
            try {
                Result result = ResultGenerator.genFailResult("无此文件");
                os = resp.getOutputStream();
                os.write(result.toString().getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    /**
     *
     * @param UID
     * @param filename
     * @return
     */
    public InputStream getFile(String UID, String filename) {
        File f = mypath.resolve(UID).resolve(filename).toFile();
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(f));
            return bufferedInputStream;
        } catch (IOException e) {
            return null;
        }
    }
}
