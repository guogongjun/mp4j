package com.yeapoo.odaesan.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.service.VoiceService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/voice")
public class VoiceController {

    @Autowired
    private VoiceService service;

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
    public DataWrapper list(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> musicList = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("music", musicList);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":{"name":"$NAME", "url":"$URL"}}
     */
    @RequestMapping(value="url/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.get(infoId, id);
        return new DataWrapper(data);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public void download(@PathVariable String infoId, @PathVariable String id, HttpServletResponse response) throws IOException {
        Map<String, Object> data = service.getWithStream(infoId, id);
        InputStream in = MapUtil.get(data, "stream", InputStream.class);
        String filename = generateName(data);
        response.setContentType("application/mp3");
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));
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

    private String generateName(Map<String, Object> data) throws UnsupportedEncodingException {
        String name = MapUtil.get(data, "name");
        String url = MapUtil.get(data, "url");
        if (!name.toLowerCase().endsWith(".mp3") && !name.toLowerCase().endsWith(".amr")) {
            if (url.toLowerCase().endsWith(".amr")) {
                name = name + ".amr";
            } else {
                name = name + ".mp3";
            }
        }
        return URLEncoder.encode(name, "UTF-8");
    }
}
