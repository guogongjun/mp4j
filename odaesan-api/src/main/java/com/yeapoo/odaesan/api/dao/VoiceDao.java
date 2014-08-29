package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface VoiceDao {

    String insert(String infoId, String name, String relativeUrl);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    void update(String infoId, String id, String name);

    void delete(String infoId, String id);

}
