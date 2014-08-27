package com.yeapoo.odaesan.irs.dao;

import com.yeapoo.odaesan.sdk.model.Follower;

public interface UserDao {

    void insert(String infoId, Follower follower);

    void updateUnsubscribeTime(String infoId, String openid);

}