package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

public interface MenuDao {

    String insert(String infoId, String parentId, String name, int sequence);

    void insert(String infoId, String parentId, String name, String type, String keycode, String url, int sequence);

    int count(String infoId);

    List<Map<String, Object>> findAllByParentId(String infoId, String parentId);

    List<Map<String, Object>> listByParentId(String infoId, String parentId);

    Map<String, Object> getParentIdAndSequenceById(String infoId, String id);

    void update(String infoId, String id, String name, int sequence);

    void updateByParentIdAndSequence(String infoId, String parentId, int originalSequence, int targetSequence);

    void bindClickReply(String infoId, String menuId, String keycode, String replyId, String replyType);

    void bindViewReply(String infoId, String menuId, String url);

    void unbindReply(int infoId, String menuId);

    void delete(String infoId, String id);

    void truncate(String infoId);

}