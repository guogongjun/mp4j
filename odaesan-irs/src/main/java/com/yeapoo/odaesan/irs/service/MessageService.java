package com.yeapoo.odaesan.irs.service;

import java.util.Map;
import java.util.concurrent.Future;

import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

public interface MessageService {

    Future<String> save(TextMessage message, Map<String, Object> params);

    void updateKeywordFlag(String infoId, String id, boolean ivrmsg);

    Future<String> save(ImageMessage message, Map<String, Object> params);

    Future<String> save(VoiceMessage message, Map<String, Object> params);

    Future<String> save(VideoMessage message, Map<String, Object> params);

    Future<String> save(LocationMessage message, Map<String, Object> params);

    Future<String> save(LinkMessage message, Map<String, Object> params);

}