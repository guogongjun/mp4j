package com.yeapoo.odaesan.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.api.service.VideoService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/video")
public class VideoController {

    @Value("${wx.video.max.size}")
    private long maxSize;

    @Autowired
    private VideoService service;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper upload(@PathVariable String infoId, @RequestParam MultipartFile file) {
        long size = file.getSize();
        if (size > maxSize) {
            return new DataWrapper(414, "file size exceeded limit");
        }
        String filename = file.getOriginalFilename();
        if (null == filename || !filename.toLowerCase().contains(".mp4")) {
            return new DataWrapper(406, "only MP4 formated videos are supported");
        }

        Map<String, Object> data = service.save(infoId, file);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper fillInInfo(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, String> itemMap) {
        String title = itemMap.get("title");
        String description = itemMap.get("description");
        service.update(infoId, id, title, description);
        return new DataWrapper();
    }

    @RequestMapping(value = "upload/{id}", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper updateMedia(@PathVariable String infoId, @PathVariable String id, @RequestParam MultipartFile file) {
        Map<String, Object> data = service.updateMedia(infoId, id, file);
        return new DataWrapper(data);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> list = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("video", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.get(infoId, id);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public DataWrapper delete(@PathVariable String infoId, @PathVariable String id) {
        service.delete(infoId, id);
        return new DataWrapper();
    }
}
