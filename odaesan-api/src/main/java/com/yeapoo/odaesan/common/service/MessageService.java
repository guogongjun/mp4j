package com.yeapoo.odaesan.common.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;
import com.yeapoo.odaesan.sdk.model.message.event.ClickEventMessage;

public interface MessageService {

    List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination);

    //================== following is for IRS ============================

    String save(TextMessage message, Map<String, Object> params);

    void save(ClickEventMessage message, Map<String, Object> params);

    void updateKeywordFlag(String infoId, String id, boolean ivrmsg);

    Future<String> save(ImageMessage message, Map<String, Object> params);

    Future<String> save(VoiceMessage message, Map<String, Object> params);

    Future<String> save(VideoMessage message, Map<String, Object> params);

    Future<String> save(LocationMessage message, Map<String, Object> params);

    Future<String> save(LinkMessage message, Map<String, Object> params);
}
