package com.yeapoo.odaesan.api.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.adapter.WeixinSDKAdapter;
import com.yeapoo.odaesan.common.model.DataWrapper;
import com.yeapoo.odaesan.common.support.AppInfoProvider;
import com.yeapoo.odaesan.material.support.StaticResourceHandler;
import com.yeapoo.odaesan.sdk.client.QRCodeClient;
import com.yeapoo.odaesan.sdk.model.Authorization;
import com.yeapoo.odaesan.sdk.model.Ticket;
import com.yeapoo.odaesan.sdk.model.TicketArgs;

@Controller
@RequestMapping("v1/{infoId}/qr")
public class QRCodeController {

    @Autowired
    private WeixinSDKAdapter adapter;
    @Autowired
    private QRCodeClient client;
    @Autowired
    private StaticResourceHandler handler;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AppInfoProvider infoProvider;

    @RequestMapping(value="test/{start}/{end}",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper test(@PathVariable String infoId, @PathVariable Integer start, @PathVariable Integer end) {
        Map<String, Object> appInfo = infoProvider.provide(infoId);
        Method method = ReflectionUtils.findMethod(QRCodeClient.class, "requestTicket", new Class<?>[] {Authorization.class, TicketArgs.class});
        TicketArgs args = null;
        Ticket ticket = null;
        for (int i = start; i < end; i++) {
            args = new TicketArgs(i);
            ticket = Ticket.class.cast(adapter.invoke(client, method, new Object[] {null, args}, appInfo));
            jdbcTemplate.update("INSERT INTO `qrcode_scene`(`id`,`scene_id`,`info_id`,`data`,`ticket`) VALUES(?,?,?,?,?)",
                    IDGenerator.generate(Object.class),
                    i,
                    infoId,
                    "product" + i,
                    ticket.getTicket());
            System.out.println("－－－－－－－－－－－－－－－－－－－－－－");
            System.out.println("scene" + i);
            System.out.println(ticket.getTicket());
            System.out.println(client.redeemTicket(ticket));
        }
        return new DataWrapper();
    }

    @RequestMapping(value="test2",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper test2(@PathVariable String infoId) throws FileNotFoundException, IOException {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT scene_id, ticket FROM qrcode_scene WHERE info_id = ?", infoId);
        for (Map<String, Object> map : list) {
            Ticket ticket = new Ticket(MapUtil.get(map, "ticket"), 1800);
            URL url = new URL(client.redeemTicket(ticket));
            int sceneId = MapUtil.get(map, "scene_id", Integer.class) - 5;
            String path = handler.getAbsolutePath(infoId + "/download/qrcode/" + sceneId + ".jpg");
            FileCopyUtils.copy(url.openStream(), new FileOutputStream(new File(path)));
        }
        return new DataWrapper();
    }
}
