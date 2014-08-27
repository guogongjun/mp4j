package com.yeapoo.odaesan.api.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.NewsImageDao;

@Repository
public class NewsImageDaoImpl implements NewsImageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, String relativeUrl) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_news_image`(`id`,`info_id`,`url`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, relativeUrl);
        return id;
    }

}
