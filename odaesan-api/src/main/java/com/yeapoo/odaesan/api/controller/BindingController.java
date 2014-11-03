package com.yeapoo.odaesan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.service.AppInfoService;

@Controller
@RequestMapping("v1/bind")
public class BindingController {

    @Value("${app.baseURL}")
    private String baseURL;

    @Autowired
    private AppInfoService appInfoService;

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
    public DataWrapper fillIn(@RequestBody Map<String, String> params) {
        String weixinId = params.get("weixin_id");
        Assert.notNull(weixinId);
        String appId = params.get("app_id");
        String appSecret = params.get("app_secret");

        appInfoService.updateByWeixinID(weixinId, appId, appSecret);

        return new DataWrapper();
    }
}
