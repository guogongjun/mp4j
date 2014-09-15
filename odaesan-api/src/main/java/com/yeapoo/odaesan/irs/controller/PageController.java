package com.yeapoo.odaesan.irs.controller;

import java.sql.Timestamp;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.service.NewsService;
import com.yeapoo.odaesan.common.service.WeixinService;

@Controller
@RequestMapping("page/{infoId}")
public class PageController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private WeixinService weixinService;

    @RequestMapping(value="{itemId}", method=RequestMethod.GET)
    public String viewNewsItemPage(Model model, @PathVariable String infoId, @PathVariable String itemId) {
        Map<String, Object> itemInfo = newsService.getOneItem(infoId, itemId);
        model.addAllAttributes(itemInfo);

        Timestamp ts = MapUtil.get(itemInfo, "create_time", Timestamp.class);
        String  createTime = new DateTime(ts).toString("yyyy-MM-dd");
        model.addAttribute("create_time", createTime);

        Map<String, Object> accountInfo = weixinService.getName(infoId);
        model.addAllAttributes(accountInfo);

        return "newsitem";
    }
}
