package com.yeapoo.odaesan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.service.TextService;

@Controller
@RequestMapping("v1/{infoId}/text")
public class TextController {

    @Autowired
    private TextService service;

    /**
     * 
     * @param itemMap {"content":"$CONTENT"}
     * @return {"code":200, "message":"OK", "data":"$ID"}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper saveText(@PathVariable String infoId, @RequestBody Map<String, String> itemMap) {
        String content = itemMap.get("content");
        String id = service.save(infoId, content);
        return new DataWrapper(id);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":{"id":"$ID","content":"$CONTENT"}}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.get(infoId, id);
        return new DataWrapper(data);
    }
}
