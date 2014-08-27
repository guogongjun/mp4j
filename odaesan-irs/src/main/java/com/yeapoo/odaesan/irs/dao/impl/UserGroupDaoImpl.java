package com.yeapoo.odaesan.irs.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.UserGroupDao;

@Repository
public class UserGroupDaoImpl implements UserGroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listWxGroupId(String infoId) {
        String sql = "SELECT `id`, `wx_group_id` FROM `user_group` WHERE `info_id` = ? AND `delete_time` IS NULL";
        return jdbcTemplate.queryForList(sql, infoId);
    }
}
