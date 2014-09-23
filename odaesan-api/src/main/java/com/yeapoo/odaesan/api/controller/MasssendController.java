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

import com.yeapoo.odaesan.common.exception.APIException;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.service.MasssendService;

@Controller
@RequestMapping("v1/{infoId}/masssend")
public class MasssendController {

    @Autowired
    private MasssendService service;

    /**
     * 
     * @param itemMap {
     *      "group_id": "$GROUP_ID",
     *      "gender": "1"(male)|"2"(female)|"0"(unknown),
     *      "msg_type": "$MSG_TYPE",
     *      "msg_id": "$MSG_ID"
     *  }
     * @return  {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper send(@PathVariable String infoId, @RequestBody Map<String, String> itemMap) {
        String groupId = itemMap.get("group_id");
        String gender = itemMap.get("gender");
        String msgType = itemMap.get("msg_type");
        String msgId = itemMap.get("msg_id");
        try {
            service.submitMasssendTask(infoId, groupId, gender, msgType, msgId);
            return new DataWrapper();
        } catch (APIException e) {
            return new DataWrapper(400, e.getMessage());
        }
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
     *      "message": [{...}, {...}]
     *   }}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> list = service.list(infoId, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    /**
     * @param id
     * @return {"code":200, "message":"OK", "data":{
     *          "total_count":$TOTAL_COUNT,
     *          "filter_count":$FILTER_COUNT,
     *          "sent_count":$SENT_COUNT,
     *          "error_count":$ERROR_COUNT
     *      }
     * }
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper getSendStatus(@PathVariable String infoId, @PathVariable String id) {
        Map<String, Object> data = service.getStatistics(infoId, id);
        return new DataWrapper(data);
    }
}
