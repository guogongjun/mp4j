package com.yeapoo.odaesan.api.dao;

import java.util.Map;

public interface TextDao {

    String save(String infoId, String content);

    Map<String, Object> get(String infoId, String id);

}