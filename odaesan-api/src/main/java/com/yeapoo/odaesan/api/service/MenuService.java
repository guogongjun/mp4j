package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

public interface MenuService {

    boolean hasFetched(String infoId);

    void fetchInfo(String infoId);

    void reset(String infoId);

    void publish(String infoId);

    String save(String infoId, Map<String, Object> itemMap);

    List<Map<String, Object>> list(String infoId);

    void update(String infoId, String id, Map<String, Object> itemMap);

    void delete(String infoId, String id);

    void bindReply(String infoId, String menuId, String replyId, String replyType);

    void unbindReply(String infoId, String menuId);

}
