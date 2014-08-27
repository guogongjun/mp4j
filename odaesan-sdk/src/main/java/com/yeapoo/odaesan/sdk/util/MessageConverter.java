package com.yeapoo.odaesan.sdk.util;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;
import com.yeapoo.odaesan.sdk.exception.WeixinSDKException;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ClickEventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.EventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.LocationEventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.MassSendJobFinishEventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ScanEventMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ViewEventMessage;

public class MessageConverter {

    private static Map<String, Class<? extends Message>> msgTypeModelMap = new HashMap<String, Class<? extends Message>>();
    static {
        msgTypeModelMap.put(Constants.MessageType.TEXT, TextMessage.class);
        msgTypeModelMap.put(Constants.MessageType.IMAGE, ImageMessage.class);
        msgTypeModelMap.put(Constants.MessageType.VOICE, VoiceMessage.class);
        msgTypeModelMap.put(Constants.MessageType.VIDEO, VideoMessage.class);
        msgTypeModelMap.put(Constants.MessageType.LOCATION, LocationMessage.class);
        msgTypeModelMap.put(Constants.MessageType.LINK, LinkMessage.class);
        msgTypeModelMap.put(Constants.EventType.SUBSCRIBE, EventMessage.class);
        msgTypeModelMap.put(Constants.EventType.UNSUBSCRIBE, EventMessage.class);
        msgTypeModelMap.put(Constants.EventType.SCAN, ScanEventMessage.class);
        msgTypeModelMap.put(Constants.EventType.LOCATION, LocationEventMessage.class);
        msgTypeModelMap.put(Constants.EventType.CLICK, ClickEventMessage.class);
        msgTypeModelMap.put(Constants.EventType.VIEW, ViewEventMessage.class);
        msgTypeModelMap.put(Constants.EventType.MASS_SEND_JOB_FINISH, MassSendJobFinishEventMessage.class);
    }

    public static Message xml2Message(InputStream xml) {
        Document document = XmlUtil.getDocument(xml);
        return innerXml2Message(document);
    }

    public static Message xml2Message(String xml) {
        Document document = XmlUtil.getDocument(xml);
        return innerXml2Message(document);
    }

    public static Message innerXml2Message(Document document) {
        Node node = document.getElementsByTagName(Constants.XmlTag.MSG_TYPE).item(0);
        String messageType = node.getTextContent();
        Class<? extends Message> clazz = null;
        if (messageType.equals(Constants.MessageType.EVENT)) { // 事件推送消息，需细化到是何种事件
            node = document.getElementsByTagName(Constants.XmlTag.EVENT).item(0);
            String eventType = node.getTextContent();
            if (Constants.EventType.SUBSCRIBE.equals(eventType)) { // 由于带参数二维码的存在，当事件是subscribe时，需判断是否是SCAN事件
                NodeList ticket = document.getElementsByTagName(Constants.XmlTag.TICKET);
                if (ticket.getLength() != 0) {
                    clazz =  msgTypeModelMap.get(Constants.EventType.SCAN);
                } else {
                    clazz =  msgTypeModelMap.get(eventType);
                }
            } else {
                clazz =  msgTypeModelMap.get(eventType);
            }
        } else { // 普通消息
            clazz =  msgTypeModelMap.get(messageType);
        }
        Message message = null;
        try {
            Constructor<? extends Message> constructor = clazz.getConstructor(Document.class);
            message = constructor.newInstance(document);
        } catch (Exception e) {
            throw new WeixinSDKException(e);
        }
        return message;
    }
}
