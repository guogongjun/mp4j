package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

public interface UserGroupMappingDao {

    void insert(String infoId, String openid, String groupId);

    void batchInsert(List<Object[]> groupMappingList);

    void batchInsert(String infoId, List<String> openidList, String target);

    List<Map<String, Object>> findByOpenid(String infoId, String openid);

    List<String> findOpenidByGroupId(String infoId, String groupId);

    void batchDelete(String infoId, List<String> openidList, String current);

    void delete(String infoId, String openid, String current);

    void deleteByGroupId(String infoId, String groupId);

    void deleteByOpenid(String infoId, String openid);

    void batchDeleteByOpenid(String infoId, List<String> openidList);

    void truncate(String infoId);
}