package com.yeapoo.odaesan.api.dao;

import java.util.List;

public interface UserGroupMappingDao {

    void insert(String infoId, String openid, String target);

    void batchInsert(List<Object[]> groupMappingList);

    void batchInsert(String infoId, List<String> openidList, String target);

    void batchDelete(String infoId, List<String> openidList, String current);

    void delete(String infoId, String openid, String current);

    void deleteByGroupId(String infoId, String groupId);

    void truncate(String infoId);

}