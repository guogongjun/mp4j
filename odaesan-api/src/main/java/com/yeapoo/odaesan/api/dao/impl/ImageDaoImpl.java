package com.yeapoo.odaesan.api.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.ImageDao;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class ImageDaoImpl implements ImageDao {

    @Value("${static.alias}")
    private String alias;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, String name, String relativePath) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_image`(`id`,`info_id`,`name`,`url`,`create_time`) VALUES(?,?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, name, relativePath);
        return id;
    }

    @Override
    public int count(String infoId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM material_image WHERE info_id = ? AND delete_time IS NULL", Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, Pagination pagination) {
        int offset = pagination.getOffset();
        int size = pagination.getSize();
        String sql ="SELECT id, name, CONCAT('%s', url) AS url FROM material_image"
                + "  WHERE info_id = ? AND delete_time IS NULL"
                + "  ORDER BY create_time DESC LIMIT ?, ?";
        return jdbcTemplate.queryForList(String.format(sql, alias), infoId, offset, size);
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        String sql = "SELECT `name`, `url` FROM `material_image` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    @Override
    public void update(String infoId, String id, String name) {
        String sql = "UPDATE material_image SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "DELETE FROM material_image WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
