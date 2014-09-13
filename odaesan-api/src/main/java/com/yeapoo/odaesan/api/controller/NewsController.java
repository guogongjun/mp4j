package com.yeapoo.odaesan.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.NewsService;

@Controller
@RequestMapping("v1/{infoId}/news")
public class NewsController {

    @Value("${wx.image.max.size}")
    private long maxSize;

    @Autowired
    private NewsService service;

    /**
     * 
     * @param file
     * @return {"code":200, "message":"OK", "data":{"id":"$ID", "url":"$URL"}}
     */
    @RequestMapping(value = "image", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper uploadImage(@PathVariable String infoId, @RequestParam MultipartFile file) {
        long size = file.getSize();
        if (size > maxSize) {
            return new DataWrapper(414, "file size exceeded limit");
        }
        String filename = file.getOriginalFilename();
        if (null == filename || !filename.toLowerCase().contains(".jpg")) {
            return new DataWrapper(406, "only JPG formated images are supported");
        }

        Map<String, Object> data = service.saveImage(infoId, file);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "single", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper createSingle(@PathVariable String infoId, @RequestBody Map<String, Object> itemMap) {
        String id = service.save(infoId, itemMap);
        return new DataWrapper(id);
    }

    @RequestMapping(value = "multiple", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper createMultiple(@PathVariable String infoId, @RequestBody List<Map<String, Object>> itemMapList) {
        String id = service.save(infoId, itemMapList);
        return new DataWrapper(id);
    }

    /**
     * 
     * @param index optional, default 1
     * @param size optional, default 10
     * @return {"code":200, "message":"OK", "data":{
     *      "pagination": {
     *          "index": $INDEX,
     *          "size": $SIZE,
     *          "count": $COUNT
     *      },
     *      "news": {"$ID1":[{...}], "$ID2":[{...},{...}]}
     *   }}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        MultiValueMap<String, Map<String, Object>> list = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("news", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":[{...}]}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        List<Map<String, Object>> data = service.get(infoId, id);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "single/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper editSingle(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, Object> updatedItemMap) {
        service.update(infoId, id, updatedItemMap);
        return new DataWrapper();
    }

    @RequestMapping(value = "multiple/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper editMultiple(@PathVariable String infoId, @PathVariable String id, @RequestBody List<Map<String, Object>> updatedItemMapList) {
        service.update(infoId, id, updatedItemMapList);
        return new DataWrapper();
    }

    /**
     * @param id
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public DataWrapper delete(@PathVariable String infoId, @PathVariable String id) {
        service.delete(infoId, id);
        return new DataWrapper();
    }
}
