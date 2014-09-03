package com.yeapoo.odaesan.api.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.MasssendMessageDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.masssend.MasssendResponse;

@Repository
public class MasssendMessageDaoImpl implements MasssendMessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String msgType, String msgId, MasssendResponse response) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `masssend_message`(`id`,`info_id`,`msg_type`,`msg_id`,`wx_msg_id`,`status`,`errcode`,`errmsg`,`create_time`) VALUES(?,?,?,?,?,?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, msgType, msgId, response.getMsgId(), "sending", response.getErrorCode(), response.getErrorMsg());
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT COUNT(`id`) FROM `masssend_message` WHERE `info_id` = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, Pagination pagination) {
        String sql = "SELECT `id`,`msg_type`,`msg_id`,`create_time`"
                + " FROM `masssend_message`"
                + " WHERE `info_id` = ?"
                + " ORDER BY `create_time` DESC"
                + " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public Map<String, Object> getStatistics(String infoId, String id) {
        String sql = "SELECT `status`,`total_count`,`filter_count`,`sent_count`,`error_count` FROM `masssend_message` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

}
