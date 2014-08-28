package com.yeapoo.odaesan.api.controller;

import java.util.HashMap;
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

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.service.UserGroupService;
import com.yeapoo.odaesan.api.service.UserService;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

@Controller
@RequestMapping("{infoId}/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    @Autowired
    private UserGroupService groupService;

    /**
     * 
     * @return success: {"code":200, "message":"OK"} <br/>
     *         error: {"code":400, "message":"$ERROR_MESSAGE"} 如果已经拉取过，且没有传递force参数，则不能进行再次拉取，否则会覆盖分组信息
     */
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper fetchFollowerInfo(@PathVariable String infoId, @RequestParam(required=false) String force) {
        if (StringUtils.hasText(force)) { // XXX 此处应加强验证，让用户再次输入密码等，避免恶意删除
            service.reset(infoId);
            logger.warn("Clear follower info for app {}", infoId);
        }
        if (service.hasFetched(infoId)) {
            return new DataWrapper(400, "follower info has already been fetched");
        }
        groupService.init(infoId);
        service.fetchInfo(infoId, null);
        return new DataWrapper();
    }

    /**
     * @param group
     * @param index
     * @param size
     * @return {"code":200, "message":"OK", "data":{
     *      "pagination": {
     *          "index": $INDEX,
     *          "size": $SIZE,
     *          "count": $COUNT
     *      },
     *      "user": [{...}, {...}]
     *   }}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper listByGroup(@PathVariable String infoId, @RequestParam String group, @RequestParam(defaultValue = "1") int index, @RequestParam(defaultValue = "10") int size) {
        Pagination pagination = new Pagination(index, size);
        List<Map<String, Object>> list = service.listByGroup(infoId, group, pagination);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user", list);
        data.put("pagination", pagination);
        return new DataWrapper(data);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":{...}}
     */
    @RequestMapping(value = "{openid}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper get(@PathVariable String infoId, @PathVariable String openid) {
        Map<String, Object> data = service.get(infoId, openid);
        return new DataWrapper(data);
    }

    /**
     * 
     * @return {"code":200, "message":"OK", "data":["$GROUP1","$GROUP2"]}
     */
    @RequestMapping(value = "groups/{openid}", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper listGroupsByOpenid(@PathVariable String infoId, @PathVariable String openid) {
        List<Map<String, Object>> data = service.listGroups(infoId, openid);
        return new DataWrapper(data);
    }

    /**
     * @param params {"openid":"$OPENID", "group_id":"$GROUP1"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "rm", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper removeUserFromGroup(@PathVariable String infoId, @RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String groupId = params.get("group_id");
        service.fakeRemoveUserFromGroup(infoId, openid, groupId);
        return new DataWrapper();
    }

    /**
     * @param params {"openid":["$OPENID1","$OPENID2"], "group_id":"$GROUP1"}
     * @return {"code":200, "message":"OK"}
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "rm/batch", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper batchRemoveUserFromGroup(@PathVariable String infoId, @RequestBody Map<String, Object> params) {
        List<String> openidList = MapUtil.get(params, "openid", List.class);
        String groupId = MapUtil.get(params, "group_id");
        service.fakeBatchRemoveUserFromGroup(infoId, openidList, groupId);
        return new DataWrapper();
    }

    /**
     * 
     * @param params {"openid":"$OPENID", "group_id":"$GROUPID"}
     * @return {"code":200, "message":"OK"}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper addUserToGroup(@PathVariable String infoId, @RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String groupId = params.get("group_id");
        service.fakeAddUserToGroup(infoId, openid, groupId);
        return new DataWrapper();
    }

    /**
     * @param params {"openid":["$OPENID1","$OPENID2"], "group_id":"$GROUP"}
     * @return {"code":200, "message":"OK"}
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "add/batch", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper batchAddUserFromGroup(@PathVariable String infoId, @RequestBody Map<String, Object> params) {
        List<String> openidList = MapUtil.get(params, "openid", List.class);
        String groupId = MapUtil.get(params, "group_id");
        service.fakeBatchAddUserToGroup(infoId, openidList, groupId);
        return new DataWrapper();
    }

    /**
     * 
     * @param params {"openid":"$OPENID", "current":"$GROUP1", "target":"$GROUP2"}
     * @return {"code":200, "message":"OK"}
     */
    @Deprecated
    @RequestMapping(value = "mv", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper moveUserToGroup(@PathVariable String infoId, @RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String current = params.get("current");
        String target = params.get("target");
        service.fakeMoveUserToGroup(infoId, openid, current, target);
        return new DataWrapper();
    }

    /**
     * 
     * @param params {"openid":"$USER_ID", "current":"$GROUP1", "target":"$GROUP2"}
     * @return {"code":200, "message":"OK"}
     */
    @Deprecated
    @RequestMapping(value = "cp", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper copyUserToGroup(@PathVariable String infoId, @RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        String current = params.get("current");
        String target = params.get("target");
        service.fakeCopyUserToGroup(infoId, openid, current, target);
        return new DataWrapper();
    }

    /**
     * 
     * @param params {"openid":["$USER_ID"], "current":"$GROUP1", "target":"$GROUP2"}
     * @return {"code":200, "message":"OK"}
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "mv/batch", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper batchMoveUserToGroup(@PathVariable String infoId, @RequestBody Map<String, Object> params) {
        List<String> openidList = MapUtil.get(params, "openid", List.class);
        String current = MapUtil.get(params, "current");
        String target = MapUtil.get(params, "target");
        service.fakeBatchMoveUserToGroup(infoId, openidList, current, target);
        return new DataWrapper();
    }

    /**
     * 
     * @param params {"openid":["$USER_ID"], "current":"$GROUP1", "target":"$GROUP2"}
     * @return {"code":200, "message":"OK"}
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "cp/batch", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper batchCopyUserToGroup(@PathVariable String infoId, @RequestBody Map<String, Object> params) {
        List<String> openidList = MapUtil.get(params, "openid", List.class);
        String current = MapUtil.get(params, "current");
        String target = MapUtil.get(params, "target");
        service.fakeBatchCopyUserToGroup(infoId, openidList, current, target);
        return new DataWrapper();
    }
}