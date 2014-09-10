package com.yeapoo.odaesan.irs.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.KeywordDao;

@Repository
public class KeywordDaoImpl implements KeywordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> find(String infoId, String content) {
        String sql = "SELECT `g`.`reply_id`, `g`.`reply_type`"
                + " FROM `keyword_group` `g`"
                + " JOIN `keyword` `k` ON `g`.`id` = `k`.`group_id`"
                + " WHERE `g`.`info_id` = ?"
                + "   AND `k`.`delete_time` IS NULL AND `g`.`delete_time` IS NULL"
                + "   AND `k`.`content` = ? AND `k`.`fuzzy` = ?"
                + " LIMIT 1";
        try {
            return jdbcTemplate.queryForMap(sql, infoId, content, false);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> findFuzzy(String infoId, String content) {
        String sql = "SELECT `g`.`reply_id`, `g`.`reply_type`"
                + " FROM `keyword_group` `g`"
                + " JOIN `keyword` `k` ON `g`.`id` = `k`.`group_id`"
                + " WHERE `g`.`info_id` = ?"
                + "   AND `k`.`delete_time` IS NULL AND `g`.`delete_time` IS NULL"
                + "   AND `k`.`content` LIKE '%"+content+"%' AND `k`.`fuzzy` = ?"
                + " LIMIT 1";
        try {
            return jdbcTemplate.queryForMap(sql, infoId, true);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Map<String, Object> findByName(String infoId, String ruleName) {
        String sql = "SELECT `reply_id`, `reply_type`"
                + " FROM `keyword_group`"
                + " WHERE `info_id` = ? AND `name` = ? AND `delete_time` IS NULL"
                + " ORDER BY `create_time` DESC"
                + " LIMIT 1";
        return jdbcTemplate.queryForMap(sql, infoId, ruleName);
    }
}
