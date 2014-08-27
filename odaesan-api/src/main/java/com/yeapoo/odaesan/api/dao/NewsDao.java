package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;

public interface NewsDao {

    String insert(String infoId);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId, Pagination pagination);

    List<Map<String, Object>> get(String infoId, String id);

    List<Map<String, Object>> getForMasssend(String infoId, String id);

    void update(String infoId, String id);

    void delete(String infoId, String id);

}