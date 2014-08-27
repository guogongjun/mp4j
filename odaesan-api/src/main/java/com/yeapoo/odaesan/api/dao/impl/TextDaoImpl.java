package com.yeapoo.odaesan.api.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.TextDao;

@Repository
public class TextDaoImpl implements TextDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String save(String infoId, String content) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_text`(`id`,`info_id`,`content`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, content);
        return id;
    }

    @Override
    public Map<String, Object> get(String infoId, String id) {
        String sql = "SELECT `id`, `content` FROM `material_text` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }
}
