package com.yeapoo.odaesan.common.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.common.dao.AppInfoDao;

@Repository
public class AppInfoDaoImpl implements AppInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> get(String infoId) {
        String sql = "SELECT `id`, `app_id`, `app_secret` FROM `app_info` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, infoId);
    }

    @Override
    public String getToken(String infoId) {
        String sql = "SELECT `token` FROM `app_info` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, String.class, infoId);
    }

    @Override
    public Map<String, Object> getName(String infoId) {
        String sql = "SELECT `weixin_id`,`name` FROM `app_info` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, infoId);
    }

}
