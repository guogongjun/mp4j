package com.yeapoo.odaesan.irs.dao;

import java.util.Map;

public interface MenuDao {

    Map<String, Object> getByKeycode(String infoId, String keycode);

}