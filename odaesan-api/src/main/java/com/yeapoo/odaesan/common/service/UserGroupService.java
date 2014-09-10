package com.yeapoo.odaesan.common.service;

import java.util.List;
import java.util.Map;

public interface UserGroupService {

    void init(String infoId);

    String save(String infoId, String name);

    List<Map<String, Object>> list(String infoId);

    void update(String infoId, String id, String name);

    void delete(String infoId, String id);

}
