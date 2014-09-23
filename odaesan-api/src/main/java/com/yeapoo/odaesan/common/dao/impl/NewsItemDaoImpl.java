package com.yeapoo.odaesan.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.dao.NewsItemDao;

@Repository
public class NewsItemDaoImpl implements NewsItemDao {

    @Value("${static.alias}")
    private String alias;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String newsId, Map<String, Object> itemMap) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_news_item`(`id`,`news_id`,`info_id`,"
                + " `title`,`author`,`image_id`,`show_cover_pic`,`digest`,`content`,`content_source_url`)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, id, newsId, infoId,
                itemMap.get("title"),
                itemMap.get("author"),
                itemMap.get("image_id"),
                itemMap.get("show_cover_pic"),
                itemMap.get("digest"),
                itemMap.get("content"),
                itemMap.get("content_source_url"));
    }

    @Override
    public void batchInsert(String infoId, String newsId, List<Map<String, Object>> itemMapList) {
        String sql = "INSERT INTO `material_news_item`(`id`,`news_id`,`info_id`,"
                + " `title`,`author`,`image_id`,`show_cover_pic`,`digest`,`content`,`content_source_url`,`sequence`)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Map<String, Object> itemMap : itemMapList) {
            batchArgs.add(new Object[] {
                    IDGenerator.generate(Object.class),
                    newsId,
                    infoId,
                    itemMap.get("title"),
                    itemMap.get("author"),
                    itemMap.get("image_id"),
                    itemMap.get("show_cover_pic"),
                    itemMap.get("digest"),
                    itemMap.get("content"),
                    itemMap.get("content_source_url"),
                    batchArgs.size() + 1
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public Map<String, Object> get(String infoId, String itemId) {
        String sql = "SELECT `item`.*, CONCAT('%s', `image`.`url`) AS `url`, `image`.`create_time`"
                + " FROM `material_news_item` `item`"
                + " JOIN `material_news_image` `image` ON `item`.`image_id` = `image`.`id`"
                + " WHERE `item`.`info_id` = ? AND `item`.`id` = ?";
        return jdbcTemplate.queryForMap(String.format(sql, alias), infoId, itemId);
    }

    @Override
    public void deleteByNewsId(String infoId, String id) {
        String sql = "UPDATE `material_news_item` SET `delete_time` = NOW() WHERE news_id = ?";
        jdbcTemplate.update(sql, id);
    }

}
