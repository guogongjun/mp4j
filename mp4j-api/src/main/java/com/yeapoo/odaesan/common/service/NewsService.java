package com.yeapoo.odaesan.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.common.model.Pagination;

public interface NewsService {

    Map<String, Object> saveImage(String infoId, MultipartFile file);

    String save(String infoId, Map<String, Object> itemMap);

    String save(String infoId, List<Map<String, Object>> itemMapList);

    Map<String, List<Map<String, Object>>> list(String infoId, Pagination pagination);

    List<Map<String, Object>> get(String infoId, String id);

    Map<String, Object> getOneItem(String infoId, String itemId);

    void update(String infoId, String id, Map<String, Object> updatedItemMap);

    void update(String infoId, String id, List<Map<String, Object>> updatedItemMapList);

    void delete(String infoId, String id);

}
