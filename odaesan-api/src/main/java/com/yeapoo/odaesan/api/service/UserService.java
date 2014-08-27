package com.yeapoo.odaesan.api.service;

import java.util.List;
import java.util.Map;

import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.Follower;

public interface UserService {

    boolean hasFetched(String infoId);

    void fetchInfo(String infoId, String nextOpenId);

    void save(String infoId, List<Follower> followerList);

    void reset(String infoId);

    List<Map<String, Object>> listByGroup(String infoId, String groupId, Pagination pagination);

    Map<String, Object> get(String infoId, String openid);

    void fakeMoveUserToGroup(String infoId, String openid, String current, String target);

    void fakeCopyUserToGroup(String infoId, String openid, String current, String target);

    void fakeBatchMoveUserToGroup(String infoId, List<String> openidList, String current, String target);

    void fakeBatchCopyUserToGroup(String infoId, List<String> openidList, String current, String target);

    void save(List<Object[]> groupMappingList);

}
