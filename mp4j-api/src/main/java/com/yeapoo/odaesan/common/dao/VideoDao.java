package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface VideoDao {

    String insertUrl(String infoId, String relativeUrl);

    void update(String infoId, String id, String title, String description);

    void updateUrl(String infoId, String id, String relativeUrl);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    void delete(String infoId, String id);

}
