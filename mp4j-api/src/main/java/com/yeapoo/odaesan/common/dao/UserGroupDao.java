package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.sdk.model.Group;

public interface UserGroupDao {

    String insert(String infoId, String name);

    void insert(String infoId, String id, String all);

    void batchInsert(String infoId, List<Group> groups);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId);

    void update(String infoId, String id, String name);

    void delete(String infoId, String id);

    void truncate(String infoId);

}