package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.adapter.FollowerWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

public interface UserDao {

    void batchInsert(String infoId, List<FollowerWrapper> followerList);

    int count(String infoId);

    List<Map<String, Object>> findAll(String infoId, Pagination pagination);

    int count(String infoId, String groupId);

    List<Map<String, Object>> findAll(String infoId, String groupId, Pagination pagination);

    Map<String, Object> get(String infoId, String openid);

    List<String> findByGroup(String infoId, String groupId);

    List<String> findByGroupAndGender(String infoId, String groupId, String gender);

    void truncate(String infoId);

    void updateUngrouped(String infoId, String openid, boolean ungrouped);

    void batchUpdateUngrouped(String infoId, List<String> openidList, boolean ungrouped);

}