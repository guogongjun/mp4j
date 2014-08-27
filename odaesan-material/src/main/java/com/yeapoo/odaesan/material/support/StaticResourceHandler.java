package com.yeapoo.odaesan.material.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.exception.APIException;

@Component
public class StaticResourceHandler {
    private static Logger logger = LoggerFactory.getLogger(StaticResourceHandler.class);

    @Value("${static.folder}")
    private String folder;
    @Value("${static.alias}")
    private String alias;

    public String handleDownloading(String infoId, String type, Map<String, Object> fileInfo) {
        String relativePath = String.format("%s/download/%s", infoId, type);
        String absolutePath = folder + relativePath;
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filename = MapUtil.get(fileInfo, "filename");
        InputStream stream = MapUtil.get(fileInfo, "stream", InputStream.class);
        try {
            FileCopyUtils.copy(stream, new FileOutputStream(absolutePath + filename));
        } catch (IOException e) {
            logger.error("Error occured when download file for {}", infoId, e);
        }

        String relativeUrl = relativePath + filename;
        return relativeUrl;
    }

    public String handleUploading(String infoId, String type, MultipartFile file) {
        // ensure dir exists
        String relativePath = String.format("%s/upload/%s/", infoId, type);
        String absolutePath = folder + relativePath;
        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // copy file
        String name = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(absolutePath + name));
        } catch (IOException e) {
            logger.error("Error occured when upload image {} for {}", name, infoId, e);
            throw new APIException(e);
        }

        // construct relative url
        String relativeUrl = relativePath + name;
        return relativeUrl;
    }

    public InputStream handleDownloading(String relativeUrl) {
        File file = new File(folder + relativeUrl);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("Error occured when download file");
            throw new APIException(e);
        }
    }

    public String getAbsolutePath(String relativePath) {
        return folder + relativePath;
    }

    public String getAbsoluteURL(String relativeUrl) {
        return alias + relativeUrl;
    }
}
