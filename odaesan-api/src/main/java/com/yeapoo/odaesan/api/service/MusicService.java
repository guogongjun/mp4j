package com.yeapoo.odaesan.api.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yeapoo.odaesan.common.model.Pagination;

public interface MusicService {

    Map<String, Object> save(String infoId, MultipartFile file);

    List<Map<String, Object>> list(String infoId, Pagination pagination);

    InputStream get(String infoId, String id);

    void rename(String infoId, String id, String name);

    void delete(String infoId, String id);

}
