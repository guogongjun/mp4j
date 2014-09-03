package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.adapter.FollowerWrapper;
import com.yeapoo.odaesan.common.model.Pagination;

public interface UserService {

    boolean hasFetched(String infoId);

    void fetchInfo(String infoId, String nextOpenId);

    void save(String infoId, List<FollowerWrapper> followerList);

    void save(List<Object[]> groupMappingList);

    void reset(String infoId);

    List<Map<String, Object>> listByGroup(String infoId, String groupId, Pagination pagination);

    Map<String, Object> get(String infoId, String openid);

    List<Map<String, Object>> listGroups(String infoId, String openid);

    void fakeMoveUserToGroup(String infoId, String openid, String current, String target);

    void fakeCopyUserToGroup(String infoId, String openid, String current, String target);

    void fakeBatchMoveUserToGroup(String infoId, List<String> openidList, String current, String target);

    void fakeBatchCopyUserToGroup(String infoId, List<String> openidList, String current, String target);

    void fakeRemoveUserFromGroup(String infoId, String openid, String groupId);

    void fakeBatchRemoveUserFromGroup(String infoId, List<String> openidList, String groupId);

    void fakeAddUserToGroup(String infoId, String openid, String groupId);

    void fakeBatchAddUserToGroup(String infoId, List<String> openidList, String groupId);
}
