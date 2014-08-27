package com.yeapoo.odaesan.api.dao;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.Follower;

public interface UserDao {

    void batchInsert(String infoId, List<Follower> followerList);

    int count(String infoId, String groupId);

    List<Map<String, Object>> findAll(String infoId, String groupId, Pagination pagination);

    Map<String, Object> get(String infoId, String openid);

    List<String> findByGroup(String infoId, String groupId);

    List<String> findByGroupAndGender(String infoId, String groupId, String gender);

    void truncate(String infoId);

}