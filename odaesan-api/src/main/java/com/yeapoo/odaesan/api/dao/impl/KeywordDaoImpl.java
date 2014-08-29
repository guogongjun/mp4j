package com.yeapoo.odaesan.api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.KeywordDao;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class KeywordDaoImpl implements KeywordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(String groupId, List<Map<String, Object>> keywordList) {
        String sql = "INSERT INTO `keyword`(`id`,`group_id`,`content`,`keycode`,`fuzzy`) VALUES(?,?,?,?,?)";
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
                + " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, Constants.Keyword.SUBSCRIBE, Constants.Keyword.DEFAULT, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public List<Map<String, Object>> get(String infoId, String id) {
        String sql = "SELECT `id`,`content`,`fuzzy` FROM `keyword` WHERE `group_id` = ? AND `delete_time` IS NULL";
        return jdbcTemplate.queryForList(sql, id);
    }

    @Override
    public void delete(String id) {
        String sql = "UPDATE `keyword` SET `delete_time` = NOW() WHERE `group_id` = ?";
        jdbcTemplate.update(sql, id);
    }
}
