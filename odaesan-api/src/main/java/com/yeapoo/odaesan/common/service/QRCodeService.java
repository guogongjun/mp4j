package com.yeapoo.odaesan.common.service;

import java.util.Date;

public interface QRCodeService {

    void saveScanInfo(String infoId, String sceneId, String openid, String ticket, boolean isNewFollower, Date createTime);

}
