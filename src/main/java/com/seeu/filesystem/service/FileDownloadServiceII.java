package com.seeu.filesystem.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by neo on 18/01/2017.
 */
@Service
public class FileDownloadServiceII {

    private final Path mypath;

    public FileDownloadServiceII(StorageProperties properties) {
        this.mypath = Paths.get(properties.getLocation());
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

    /**
     * 暂时做法与上相同，需要修改，提供缩略图
     * @param UID
     * @param filename
     * @return
     */
    public InputStream getThumbFile(String UID, String filename) {
        // TODO
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
