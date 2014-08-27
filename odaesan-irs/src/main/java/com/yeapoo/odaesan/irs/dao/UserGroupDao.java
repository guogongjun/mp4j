package com.yeapoo.odaesan.irs.dao;

import java.util.List;
import java.util.Map;

public interface UserGroupDao {

    List<Map<String, Object>> listWxGroupId(String infoId);

}