package com.yeapoo.odaesan.common.dao;

import java.util.Map;

public interface AppInfoDao {

    Map<String, Object> get(String infoId);

    String getToken(String infoId);

    Map<String, Object> getName(String infoId);
}
