package com.yeapoo.odaesan.api.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.api.service.MenuService;
import com.yeapoo.odaesan.common.model.DataWrapper;

@Controller
@RequestMapping("{infoId}/menu")
public class MenuController {
    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService service;

    /**
     * 
     * @return success: {"code":200, "message":"OK"} <br/>
     *         error: {"code":400, "message":"$ERROR_MESSAGE"} 如果已经拉取过，且没有传递force参数，则不能进行再次拉取，否则会覆盖菜单信息
     */
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper fetchInfo(@PathVariable String infoId, @RequestParam(required=false) String force) {
        if (StringUtils.hasText(force)) { // XXX 此处应加强验证，让用户再次输入密码等，避免恶意删除
            service.reset(infoId);
            logger.warn("Clear menu info for app {}", infoId);
        }
        if (service.hasFetched(infoId)) {
            return new DataWrapper(400, "menu info has already been fetched");
        }
        service.fetchInfo(infoId);
        return new DataWrapper();
    }

    /**
     * 
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper publish(@PathVariable String infoId) {
        service.publish(infoId);
        return new DataWrapper();
    }

    /**
     * 
     * @param itemMap {
     *   "parent_id":"$PARENT_MENU_ID",
     *   "name":"$NAME",
     *   "sequence":$SEQUENCE}
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
     * @return {"code":200, "message":"OK", "data":{"status":$FLAG, "menu":[{...}, {...}]}}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper list(@PathVariable String infoId) {
        List<Map<String, Object>> list = service.list(infoId); //TODO add is-published flag
        return new DataWrapper(list);
    }

    /**
     * 
     * @param params {"name":"$NAME","sequence":$SEQUENCE}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public DataWrapper update(@PathVariable String infoId, @PathVariable String id, @RequestBody Map<String, Object> itemMap) {
        service.update(infoId, id, itemMap);
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

    /**
     * 
     * @param itemMap {"menu_id":"$MENU_ID","reply_id":"$REPLY_ID","reply_type":"$REPLY_TYPE"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "bind", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper bindReply(@PathVariable String infoId, @RequestBody Map<String, String> itemMap) {
        String menuId = itemMap.get("menu_id");
        String replyId = itemMap.get("reply_id");
        String replyType = itemMap.get("reply_type");
        service.bindReply(infoId, menuId, replyId, replyType);
        return new DataWrapper();
    }

    /**
     * 
     * @param itemMap {"menu_id":"$MENU_ID"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "unbind", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper unbindReply(@PathVariable String infoId, @RequestBody Map<String, String> itemMap) {
        String menuId = itemMap.get("menu_id");
        service.unbindReply(infoId, menuId);
        return new DataWrapper();
    }
}
