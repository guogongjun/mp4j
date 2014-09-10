package com.yeapoo.odaesan.common.dao;

import java.util.Map;

public interface TextDao {

    String save(String infoId, String content);

    Map<String, Object> get(String infoId, String id);

}