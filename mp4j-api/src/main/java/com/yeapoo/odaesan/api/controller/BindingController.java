package com.yeapoo.odaesan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.Authorizer;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.service.AppInfoService;
import com.yeapoo.odaesan.common.service.MenuService;
import com.yeapoo.odaesan.common.service.UserGroupService;
import com.yeapoo.odaesan.common.service.UserService;
import com.yeapoo.odaesan.common.support.AppInfoProvider;

@Controller
@RequestMapping("v1/bind")
public class BindingController {

    @Value("${app.baseURL}")
    private String baseURL;

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService groupService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private Authorizer authorizer;
    @Autowired
    private AppInfoProvider provider;

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper bind(@RequestBody Map<String, String> params) {
        String weixinId = params.get("weixin_id");
        Assert.notNull(weixinId);

        Map<String, Object> info = appInfoService.save(weixinId);

        info.put("url", baseURL + "/wx/" + info.get("id"));

        return new DataWrapper(info);
    }

    @RequestMapping(value="fillin", method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper fillIn(@RequestBody Map<String, Object> params) {
        String id = MapUtil.get(params, "id");
        Assert.notNull(id);
        String appId = MapUtil.get(params, "app_id");
        String appSecret = MapUtil.get(params, "app_secret");

        appInfoService.updateById(id, appId, appSecret);

        try {
            authorizer.refresh(provider.refresh(id));
            groupService.init(id);
            userService.fetchInfo(id, null);
            menuService.fetchInfo(id);
        } catch (Exception e) {
            return new DataWrapper(400, e.getMessage());
        }

        return new DataWrapper();
    }

    @RequestMapping(value="refresh/{infoId}", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper refreshAuth(@PathVariable String infoId) {
        authorizer.refresh(provider.refresh(infoId));
        return new DataWrapper();
    }
}
