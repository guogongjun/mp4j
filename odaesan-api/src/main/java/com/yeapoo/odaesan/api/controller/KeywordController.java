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

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.service.KeywordService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/keyword")
public class KeywordController {

    @Autowired
    private KeywordService service;

    /**
     * 
     * @param itemMap {
     *      "rule_name":"$RULE_NAME",
     *      "keyword":[
     *          {
     *              "content":"$KEYWORD1",
     *              "fuzzy":"1"
     *          }, {
     *              "content":"$KEYWORD2",
     *              "fuzzy":"0"
     *          }
     *      ],
     *      "reply_id":"$REPLY_ID",
     *      "reply_type":"$REPLY_TYPE"
     *      }
     * @return {"code":200, "message":"OK", "data":"$ID"}
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper create(@PathVariable String infoId, @RequestBody Map<String, Object> itemMap) {
        String ruleName = MapUtil.get(itemMap, "rule_name");
        List<Map<String, Object>> keywordList = MapUtil.get(itemMap, "keyword", List.class);
        String replyId = MapUtil.get(itemMap, "reply_id");
        String replyType = MapUtil.get(itemMap, "reply_type");
        String id = service.save(infoId, ruleName, keywordList, replyId, replyType);
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
     *      "keyword": [{...}, {...}]
     *   }}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> list = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("keyword", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    /**
     * @param id
     * @return {"code":200, "message":"OK", "data":{...}}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.get(infoId, id);
        return new DataWrapper(data);
    }

    /**
     * @return {"code":200, "message":"OK", "data":{...}}
     */
    @RequestMapping(value = "subscribe", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper getSubscribeInfo(@PathVariable String infoId) {
        Map<String, Object> data = service.getSubscribeInfo(infoId);
        return new DataWrapper(data);
    }

    /**
     * @return {"code":200, "message":"OK", "data":{...}}
     */
    @RequestMapping(value = "default", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper getDefaultInfo(@PathVariable String infoId) {
        Map<String, Object> data = service.getDefaultInfo(infoId);
        return new DataWrapper(data);
    }

    /**
     * 
     * @param id
     * @param updatedItemMap {
     *      "rule_name":"$RULE_NAME",
     *      "keyword":[
     *          {
     *              "content":"$KEYWORD1",
     *              "fuzzy":"1"
     *          }, {
     *              "name":"$KEYWORD2",
     *              "fuzzy":"0"
     *          }
     *      ],
     *      "reply_id":"$REPLY_ID",
     *      "reply_type":"$REPLY_TYPE"
     *      }
     * @return {"code":200, "message":"OK"}
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper update(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, Object> updatedItemMap) {
        String ruleName = MapUtil.get(updatedItemMap, "rule_name");
        List<Map<String, Object>> keywordList = MapUtil.get(updatedItemMap, "keyword", List.class);
        String replyId = MapUtil.get(updatedItemMap, "reply_id");
        String replyType = MapUtil.get(updatedItemMap, "reply_type");
        service.update(infoId, id, ruleName, keywordList, replyId, replyType);
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
