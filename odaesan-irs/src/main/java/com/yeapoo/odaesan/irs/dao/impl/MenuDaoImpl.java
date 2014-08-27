package com.yeapoo.odaesan.irs.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.MenuDao;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> getByKeycode(String infoId, String keycode) {
        String sql = "SELECT `reply_id`, `reply_type` FROM `menu` WHERE `info_id` = ? AND `keycode` = ?";
        try {
            return jdbcTemplate.queryForMap(sql, infoId, keycode);
        } catch (DataAccessException e) {
            return null;
        }
    }

}
