package com.yeapoo.odaesan.irs.dao;

import java.util.List;

import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

public interface MessageDao {

    String insert(String infoId, Message msg);

    String insert(String infoId, TextMessage msg);

    void update(String infoId, String id, boolean ivrmsg);

    void insertAdditionalInfo(List<Object[]> batchArgs);

}