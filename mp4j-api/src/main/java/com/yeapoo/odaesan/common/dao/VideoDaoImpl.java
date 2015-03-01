package com.yeapoo.odaesan.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class VideoDaoImpl implements VideoDao {

    @Value("${static.alias}")
    private String alias;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insertUrl(String infoId, String relativeUrl) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_video`(`id`,`info_id`,`url`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, relativeUrl);
        return id;
    }

    @Override
    public void update(String infoId, String id, String title, String description) {
        String sql = "UPDATE `material_video` SET `title`=?,`description`=? WHERE `id` = ?";
        jdbcTemplate.update(sql, title, description, id);
    }

    @Override
    public void updateUrl(String infoId, String id, String relativeUrl) {
        String sql = "UPDATE `material_video` SET `url`=? WHERE `id` = ?";
        jdbcTemplate.update(sql, relativeUrl, id);
    }

    @Override
    public int count(String infoId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM material_video WHERE info_id = ? AND delete_time IS NULL", Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, Pagination pagination) {
        int offset = pagination.getOffset();
        int size = pagination.getSize();
        String sql ="SELECT id, title, description, CONCAT('%s', url) AS url, create_time FROM material_video"
                + "  WHERE info_id = ? AND delete_time IS NULL"
                + "  ORDER BY create_time DESC LIMIT ?, ?";
        return jdbcTemplate.queryForList(String.format(sql, alias), infoId, offset, size);
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        String sql = "SELECT `title`, `description`, `url`, `create_time` FROM `material_video` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "UPDATE `material_video` SET `delete_time` = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
