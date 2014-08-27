package com.yeapoo.odaesan.api.service;

import java.util.Map;

public interface TextService {

    String save(String infoId, String content);

    Map<String, Object> get(String infoId, String id);

}
