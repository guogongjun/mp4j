package com.yeapoo.odaesan.irs.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.irs.dao.QRCodeStatsDao;

@Repository
public class QRCodeStatsDaoImpl implements QRCodeStatsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String sceneId, String openid, String ticket, boolean isNewFollower, Date createTime) {
        String sql = "INSERT INTO `qrcode_stats`(`id`,`info_id`,`scene_id`,`openid`,`ticket`,`new`,`create_time`) VALUES(?,?,?,?,?,?,?)";
        String id = IDGenerator.generate(Object.class);
        jdbcTemplate.update(sql, id, infoId, sceneId, openid, ticket, isNewFollower, createTime);
    }

}
