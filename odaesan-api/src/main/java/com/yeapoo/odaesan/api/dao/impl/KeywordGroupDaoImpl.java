package com.yeapoo.odaesan.api.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.KeywordGroupDao;

@Repository
public class KeywordGroupDaoImpl implements KeywordGroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, String ruleName, String replyId, String replyType) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `keyword_group`(`id`,`info_id`,`name`,`reply_id`,`reply_type`,`create_time`) VALUES(?,?,?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, ruleName, replyId, replyType);
        return id;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        String sql = "SELECT `name`, `reply_id`, `reply_type` FROM `keyword_group` WHERE `id` = ? AND `delete_time` IS NULL";
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> getInfo(String infoId, String ruleName) {
        String sql = "SELECT `id`, `reply_id`, `reply_type` FROM `keyword_group` WHERE `info_id` = ? AND `name` = ? AND `delete_time` IS NULL ORDER BY `update_time` DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForMap(sql, infoId, ruleName);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(String infoId, String id, String ruleName, String replyId, String replyType) {
        String sql = "UPDATE `keyword_group` SET `name` = ?, `reply_id` = ?, `reply_type` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, ruleName, replyId, replyType, id);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "UPDATE `keyword_group` SET `delete_time` = NOW() WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }

}
