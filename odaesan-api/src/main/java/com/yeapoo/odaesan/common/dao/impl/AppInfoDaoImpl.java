package com.yeapoo.odaesan.common.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.common.util.RandomEngine;
import com.yeapoo.odaesan.common.dao.AppInfoDao;

@Repository
public class AppInfoDaoImpl implements AppInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> insert(String weixinId) {
        String select = "SELECT `id`, `token` FROM `app_info` WHERE `weixin_id` = ? FOR UPDATE";
        try {
            return jdbcTemplate.queryForMap(select, weixinId);
        } catch (DataAccessException e) {
            String id = IDGenerator.generate(Object.class);
            String token = RandomEngine.random(8);
            String sql = "INSERT INTO `app_info`(`id`, `weixin_id`, `token`, `type`, `create_time`) VALUES(?,?,?,1,NOW())";
            jdbcTemplate.update(sql, id, weixinId, token);

            Map<String, Object> info = new HashMap<String, Object>();
            info.put("token", token);
            info.put("id", id);
            return info;
        }
    }

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

    @Override
    public void updateById(String id, String appId, String appSecret) {
        String sql = "UPDATE `app_info` SET `app_id` = ?, `app_secret` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, appId, appSecret, id);
    }

}
