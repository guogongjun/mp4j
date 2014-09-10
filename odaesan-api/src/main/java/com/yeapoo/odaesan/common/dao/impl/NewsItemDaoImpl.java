package com.yeapoo.odaesan.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.dao.NewsItemDao;

@Repository
public class NewsItemDaoImpl implements NewsItemDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String newsId, Map<String, Object> itemMap) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_news_item`(`id`,`news_id`,`info_id`,"
                + " `title`,`author`,`image_id`,`digest`,`content`,`content_source_url`)"
                + " VALUES(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, id, newsId, infoId,
                itemMap.get("title"),
                itemMap.get("author"),
                itemMap.get("image_id"),
                itemMap.get("digest"),
                itemMap.get("content"),
                itemMap.get("content_source_url"));
    }

    @Override
    public void batchInsert(String infoId, String newsId, List<Map<String, Object>> itemMapList) {
        String sql = "INSERT INTO `material_news_item`(`id`,`news_id`,`info_id`,"
                + " `title`,`author`,`image_id`,`digest`,`content`,`content_source_url`,`sequence`)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Map<String, Object> itemMap : itemMapList) {
            batchArgs.add(new Object[] {
                    IDGenerator.generate(Object.class),
                    newsId,
                    infoId,
                    itemMap.get("title"),
                    itemMap.get("author"),
                    itemMap.get("image_id"),
                    itemMap.get("digest"),
                    itemMap.get("content"),
                    itemMap.get("content_source_url"),
                    batchArgs.size() + 1
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public void deleteByNewsId(String infoId, String id) {
        String sql = "DELETE FROM material_news_item WHERE news_id = ?";
        jdbcTemplate.update(sql, id);
    }

}
