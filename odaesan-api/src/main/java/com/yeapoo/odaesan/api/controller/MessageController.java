package com.yeapoo.odaesan.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.api.service.MessageService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/msg")
public class MessageController {

    @Autowired
    private MessageService service;

    /**
     * 
     * @param start_date date(2014-01-01) optional
     * @param end_date date(2014-01-31) optional
     * @param filterivrmsg 0/1, optional
     * @param filter string, optional
     * @param index
     * @param size
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
    public DataWrapper list(@PathVariable String infoId,
            @RequestParam(value="start_date", required=false) String startDate,
            @RequestParam(value="end_date", required=false) String endDate,
            @RequestParam(defaultValue = "0") int filterivrmsg,
            @RequestParam(required=false) String filter,
            @RequestParam(defaultValue = "1") int index,
            @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> list = service.list(infoId, startDate, endDate, 1 == filterivrmsg, filter, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }
}
