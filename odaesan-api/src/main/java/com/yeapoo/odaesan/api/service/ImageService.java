package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.common.model.Pagination;

public interface ImageService {

    Map<String, Object> save(String infoId, MultipartFile file);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    Map<String, Object> get(String infoId, String id);

    Map<String, Object> getWithStream(String infoId, String id);

    void rename(String infoId, String id, String name);

    void delete(String infoId, String id);

}
