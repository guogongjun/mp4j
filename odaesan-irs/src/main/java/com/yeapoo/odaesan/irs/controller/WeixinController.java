package com.yeapoo.odaesan.irs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeapoo.odaesan.framework.processor.Processor;
import com.yeapoo.odaesan.irs.service.WeixinService;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.util.MessageConverter;

@Controller
@RequestMapping("{infoId}")
public class WeixinController {
    private static Logger logger = LoggerFactory.getLogger(WeixinController.class);

    @Resource(name = "processors")
    private Map<String, Processor<? extends Message>> processors;

    @Autowired
    private WeixinService wxService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String validate(@PathVariable String infoId, @RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        boolean valid = wxService.validate(infoId, signature, timestamp, nonce, echostr);
        return valid ? echostr : "This is an attack";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String handle(@PathVariable String infoId, @RequestBody String xml) {
        Assert.notNull(xml);
        logger.info(xml);
        Message input = MessageConverter.xml2Message(xml);

        String msgType = input.getMessageType();
        Processor processor = processors.get(msgType);

        if (null == processor) {
            logger.error("No Processor specified for this MsgType => {}", msgType);
            return null;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("info_id", infoId);

        Message output = processor.process(input, params);
        return null == output ? null : output.toXML();
    }
}
