package com.yeapoo.odaesan.irs.service;


public interface FollowerService {

    void subscribe(String infoId, String openid);

    void unsubscribe(String infoId, String openid);

}