package com.yeapoo.odaesan.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private VideoService service;

    /**
     * 
     * @param file
     * @return {"code":200, "message":"OK", "data":{"id":"$ID", "url":"$URL"}}
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper upload(@PathVariable String infoId, @RequestParam MultipartFile file) {
        Map<String, Object> data = service.save(infoId, file);
        return new DataWrapper(data);
    }

    /**
     * 
     * @param itemMap {
     *   "video_id": "$VIDEO_ID",
     *   "title":"$TITLE",
     *   "description":"$DESCRIPTION"}
     * @return {"code":200, "message":"OK", "data":"$ID"}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper create(@PathVariable String infoId, @RequestBody Map<String, Object> itemMap) {
        String id = service.save(infoId, itemMap);
        return new DataWrapper(id);
    }

    /**
     * 
     * @param index
     * @param size
     * @return {"code":200, "message":"OK", "data":{
     *      "pagination": {
     *          "index": $INDEX,
     *          "size": $SIZE,
     *          "count": $COUNT
     *      },
     *      "video": [{...}, {...}]
     *   }}
     */
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

    /**
     * 
     * @return {"code":200, "message":"OK", "data":{...}}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.get(infoId, id);
        return new DataWrapper(data);
    }

    /**
     * 
     * @param infoId
     * @param id
     * @param updatedItemMap {
     *   "video_id":"$VIDEO_ID",
     *   "title":"$TITLE",
     *   "description":"$DESCRIPTION"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper edit(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, Object> updatedItemMap) {
        service.update(infoId, id, updatedItemMap);
        return new DataWrapper();
    }

    /**
     * 
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public DataWrapper delete(@PathVariable String infoId, @PathVariable String id) {
        service.delete(infoId, id);
        return new DataWrapper();
    }
}
