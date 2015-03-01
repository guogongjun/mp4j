package com.yeapoo.odaesan.common.dao;

import java.util.Date;

public interface QRCodeStatsDao {

    void insert(String infoId, String sceneId, String openid, String ticket, boolean isNewFollower, Date createTime);

}
