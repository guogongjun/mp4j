package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

public interface NewsItemDao {

    void insert(String infoId, String newsId, Map<String, Object> itemMap);

    void batchInsert(String infoId, String newsId, List<Map<String, Object>> itemMapList);

    Map<String, Object> get(String infoId, String itemId);

    void deleteByNewsId(String infoId, String id);

}