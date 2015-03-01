package com.yeapoo.odaesan.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.dao.KeywordDao;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class KeywordDaoImpl implements KeywordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(String groupId, List<Map<String, Object>> keywordList) {
        String sql = "INSERT INTO `keyword`(`id`,`group_id`,`content`,`keycode`,`fuzzy`,`create_time`) VALUES(?,?,?,?,?,NOW())";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Map<String, Object> map : keywordList) {
            String keycode = MapUtil.get(map, "keycode");
            if (!StringUtils.hasText(keycode)) {
                keycode = UUID.randomUUID().toString();
            }
            batchArgs.add(new Object[] {
                    IDGenerator.generate(Object.class),
                    groupId,
                    MapUtil.get(map, "content"),
                    keycode,
                    MapUtil.get(map, "fuzzy")
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT count(`id`) FROM `keyword_group` WHERE `info_id` = ? AND `delete_time` IS NULL AND `name` != ? AND `name` != ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId, Constants.Keyword.SUBSCRIBE, Constants.Keyword.DEFAULT);
    }

    @Override
    public List<Map<String, Object>> list(String infoId, Pagination pagination) {
        String sql = "SELECT `g`.`id`, `g`.`name`, GROUP_CONCAT(`k`.`content` SEPARATOR ',') AS `keywords` "
                + " FROM `keyword_group` `g`"
                + " JOIN `keyword` `k` ON `g`.`id` = `k`.`group_id`"
                + " WHERE `g`.`info_id` = ? AND `g`.`delete_time` IS NULL AND `k`.`delete_time` IS NULL AND `g`.`name` != ? AND `g`.`name` != ?"
                + " GROUP BY `g`.`id`"
                + " ORDER BY `g`.`create_time`, `k`.`create_time`"
                + " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, Constants.Keyword.SUBSCRIBE, Constants.Keyword.DEFAULT, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public List<Map<String, Object>> get(String infoId, String id) {
        String sql = "SELECT `id`,`content`,`fuzzy` FROM `keyword` WHERE `group_id` = ? AND `delete_time` IS NULL ORDER BY `create_time`";
        return jdbcTemplate.queryForList(sql, id);
    }

    @Override
    public void delete(String id) {
        String sql = "UPDATE `keyword` SET `delete_time` = NOW() WHERE `group_id` = ?";
        jdbcTemplate.update(sql, id);
    }

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
        try {
            return jdbcTemplate.queryForMap(sql, infoId, ruleName);
        } catch (Exception e) {
            return null;
        }
    }
}
