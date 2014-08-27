package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.common.model.Pagination;

public interface VideoService {

    Map<String, Object> save(String infoId, MultipartFile file);

    String save(String infoId, Map<String, Object> itemMap);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    void update(String infoId, String id, Map<String, Object> updatedItemMap);

    void delete(String infoId, String id);

}
