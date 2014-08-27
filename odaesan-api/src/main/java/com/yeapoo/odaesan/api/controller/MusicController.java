package com.yeapoo.odaesan.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.api.service.MusicService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/music")
public class MusicController {

    @Autowired
    private MusicService service;

    /**
     * 
     * @param file
     * @return {"code":200, "message":"OK", "data":{"id":"$ID", "name":"$NAME", "url":"$URL"}}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper upload(@PathVariable String infoId, @RequestParam MultipartFile file) {
        Map<String, Object> data = service.save(infoId, file);
        return new DataWrapper(data);
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
     *      "music": [{...}, {...}]
     *   }}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper listNews(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> musicList = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("music", musicList);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public void download(@PathVariable String infoId, @PathVariable String id, HttpServletResponse response) throws IOException {
        InputStream in = service.get(infoId, id);
        response.setContentType("application/mp3");
        FileCopyUtils.copy(in, response.getOutputStream());
        response.flushBuffer();
    }

    /**
     * @param params {"name":"$NAME"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper rename(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, String> params) {
        String name = params.get("name");
        service.rename(infoId, id, name);
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
