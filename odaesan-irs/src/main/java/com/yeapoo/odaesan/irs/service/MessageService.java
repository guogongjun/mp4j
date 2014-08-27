package com.yeapoo.odaesan.irs.service;

import java.util.Map;

import org.springframework.scheduling.annotation.AsyncResult;

import com.yeapoo.odaesan.sdk.model.message.ImageMessage;
import com.yeapoo.odaesan.sdk.model.message.LinkMessage;
import com.yeapoo.odaesan.sdk.model.message.LocationMessage;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;
import com.yeapoo.odaesan.sdk.model.message.VideoMessage;
import com.yeapoo.odaesan.sdk.model.message.VoiceMessage;

public interface MessageService {

    AsyncResult<String> save(TextMessage message, Map<String, Object> params);

    void updateKeywordFlag(String infoId, String id, boolean ivrmsg);

    AsyncResult<String> save(ImageMessage message, Map<String, Object> params);

    AsyncResult<String> save(VoiceMessage message, Map<String, Object> params);

    AsyncResult<String> save(VideoMessage message, Map<String, Object> params);

    AsyncResult<String> save(LocationMessage message, Map<String, Object> params);

    AsyncResult<String> save(LinkMessage message, Map<String, Object> params);

}