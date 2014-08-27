package com.yeapoo.odaesan.irs.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.MasssendMessageDao;

@Repository
public class MasssendMessageDaoImpl implements MasssendMessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateStatistics(String infoId, String wxMsgId, String status, int totalCount, int filterCount, int sentCount, int errorCount) {
        String sql = "UPDATE `masssend_message` SET `status` = ?, `total_count` = ?, `filter_count` = ?, `sent_count` = ?, `error_count` = ? WHERE `info_id` = ? AND `wx_msg_id` = ?";
        jdbcTemplate.update(sql, status, totalCount, filterCount, sentCount, errorCount, infoId, wxMsgId);
    }

}
