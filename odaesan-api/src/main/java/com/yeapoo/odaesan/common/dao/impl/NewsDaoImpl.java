package com.yeapoo.odaesan.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.dao.NewsDao;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class NewsDaoImpl implements NewsDao {

    @Value("${static.alias}")
    private String alias;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_news`(`id`,`info_id`,`create_time`) VALUES(?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId);
        return id;
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT COUNT(`id`) FROM `material_news` WHERE `info_id` = ? AND `delete_time` IS NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, Pagination pagination) {
        String sql = "SELECT id"
                + " FROM material_news"
                + " WHERE info_id = ? AND delete_time IS NULL"
                + " ORDER BY update_time DESC"
                + " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public List<Map<String, Object>> getBasic(String infoId, String id) {
        String sql = "SELECT news.update_time, item.title, CONCAT('%s', image.url) AS url, item.show_cover_pic, item.digest"
                + " FROM material_news news"
                + " JOIN material_news_item item ON news.id = item.news_id"
                + " JOIN material_news_image image ON item.image_id = image.id"
                + " WHERE news.id = ? AND news.delete_time IS NULL AND item.delete_time IS NULL"
                + " ORDER BY item.sequence";
        return jdbcTemplate.queryForList(String.format(sql, alias), id);
    }

    @Override
    public List<Map<String, Object>> get(String infoId, String id) {
        String sql = "SELECT news.update_time, item.title, item.author, item.image_id, CONCAT('%s', image.url) AS url, item.show_cover_pic, item.digest, item.content, item.content_source_url"
                + " FROM material_news news"
                + " JOIN material_news_item item ON news.id = item.news_id"
                + " JOIN material_news_image image ON item.image_id = image.id"
                + " WHERE news.id = ? AND news.delete_time IS NULL AND item.delete_time IS NULL"
                + " ORDER BY item.sequence";
        return jdbcTemplate.queryForList(String.format(sql, alias), id);
    }

    @Override
    public void update(String infoId, String id) {
        String sql = "UPDATE material_news SET update_time = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "UPDATE material_news SET delete_time = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
