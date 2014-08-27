package com.yeapoo.odaesan.irs.dao;

import java.util.Map;

public interface AppInfoDao {

    Map<String, Object> get(String infoId);

    String getToken(String weixinId);
}
