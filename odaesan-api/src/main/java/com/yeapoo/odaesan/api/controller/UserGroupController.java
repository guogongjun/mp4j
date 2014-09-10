package com.yeapoo.odaesan.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.service.UserGroupService;

@Controller
@RequestMapping("v1/{infoId}/group")
public class UserGroupController {

    @Autowired
    UserGroupService service;

    /**
     * 
     * @param params {"name":"$NAME"}
     * @return {"code":200, "message":"OK", "data":"$ID"}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper create(@PathVariable String infoId, @RequestBody Map<String, String> params) {
        String name = params.get("name");
        String id = service.save(infoId, name);
        return new DataWrapper(id);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":[{
     *      "id":"$ID",
     *      "name":"$NAME"
     *      }, ...
     *   ]}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId) {
        List<Map<String, Object>> list = service.list(infoId);
        return new DataWrapper(list);
    }

    /**
     * 
     * @param params {"name":"$NAME"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper update(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, String> params) {
        String name = params.get("name");
        service.update(infoId, id, name);
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
