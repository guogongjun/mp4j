package com.yeapoo.odaesan.sdk.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.RandomEngine;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Media;
import com.yeapoo.odaesan.sdk.model.MimeType;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendNews;

@Component
public class MediaClient extends BaseClient {
    private static Logger logger = LoggerFactory.getLogger(MediaClient.class);

    private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");
    private static Map<String, String> mediaType;
    static {
        mediaType = new HashMap<String, String>();
        mediaType.put("image/jpeg", ".jpg");
        mediaType.put("image/jpg", ".jpg");
        mediaType.put("image/png", ".png");
        mediaType.put("image/gif", ".gif");
        mediaType.put("image/bmp", ".bmp");
        mediaType.put("audio/mp3", ".mp3");
        mediaType.put("audio/mpeg", ".mp3");
        mediaType.put("audio/amr", ".amr");
        mediaType.put("video/mpeg4", ".mp4");
        mediaType.put("video/mp4", ".mp4");
    }

    @Value("${wx.media.upload}")
    private String mediaUploadURL;
    @Value("${wx.media.download}")
    private String mediaDownloadURL;
    @Value("${wx.messsend.news.create}")
    private String newsUploadURL;

    /**
     * 上传的多媒体文件，有格式和大小限制: <br/>
     * image: 128K JPF <br/>
     * voice: 256K AMR length < 60s <br/>
     * video: 1MB MP4 <br/>
     * thumb: 64K JPG <br/>
     * 
     * 注意：多媒体文件在微信后台保存的时间为3天，即3天后media_id失效。
     * 
     * @throws WeixinSDKException
     */
    public Media upload(Authorization authorization, String filePath, MimeType mimeType) throws WeixinSDKException {
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
        FileSystemResource media = new FileSystemResource(new File(filePath));
        request.add("media", media);

        String response = template.postForObject(mediaUploadURL, request, String.class, authorization.getAccessToken(), mimeType.toString());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, Media.class);
        } catch (IOException e) {
            throw new WeixinSDKException(e);
        }
    }

    /**
     * 上传图文消息，供群发使用
     */
    public Media uploadNews(Authorization authorization, MasssendNews news) {
        HttpEntity<String> request = new HttpEntity<String>(news.toJSON(), headers);
        String response = template.postForObject(newsUploadURL, request, String.class, authorization.getAccessToken());
        logger.debug(String.format("Weixin Response => %s", response));
        try {
            return mapper.readValue(response, Media.class);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
    }

    /**
     * 下载多媒体文件，不支持视频文件
     * 
     * @return {@link java.util.Map}, 包含{"filename":"$NAME","stream":"$FILE"}
     */
    public Map<String, Object> download(Authorization authorization, String mediaId) {
        String urlStr = String.format(mediaDownloadURL, authorization.getAccessToken(), mediaId);
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            String contentType = connection.getContentType();
            String contentDisposition = connection.getHeaderField("Content-Disposition");
            String filename = extractFilename(contentDisposition, contentType);
            InputStream in = connection.getInputStream();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("filename", filename);
            data.put("stream", in);
            return data;
        } catch (IOException e) {
            throw new WeixinSDKException(e);
        }
    }

    private static String extractFilename(String contentDisposition, String contentType) {
        String filename = null;
        if (StringUtils.hasText(contentDisposition)) {
            filename = parseContentDisposition(contentDisposition);
            if (filename != null) {
                int index = filename.lastIndexOf('/') + 1;
                if (index > 0) {
                    filename = filename.substring(index);
                }
            }
        }
        filename = filename.replaceAll("[^a-zA-Z0-9\\.\\-_]+", "_");
        if (!StringUtils.hasText(filename) && StringUtils.hasText(contentType)) {
            filename = String.format("%s_%s%s", new Date().getTime(), RandomEngine.random(4), mediaType.get(contentType));
        }
        return filename;
    }

    private static String parseContentDisposition(String disposition) {
        try {
            Matcher m = CONTENT_DISPOSITION_PATTERN.matcher(disposition);
            if (m.find()) {
                return m.group(1);
            }
        } catch (IllegalStateException ex) {
        }
        return null;
    }
}
