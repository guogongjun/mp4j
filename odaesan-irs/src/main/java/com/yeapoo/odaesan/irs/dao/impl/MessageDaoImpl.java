package com.yeapoo.odaesan.irs.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.MessageDao;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, Message msg) {
        String sql = "INSERT INTO `message`(`id`,`info_id`,`type`,`sender_id`,`create_time`) VALUES(?,?,?,?,?)";
        String id = msg.getMessageId();
        jdbcTemplate.update(sql, id, infoId, msg.getMessageType(), msg.getFromUserName(), msg.getCreateTime());
        return id;
    }

    @Override
    public String insert(String infoId, TextMessage msg) {
        String sql = "INSERT INTO `message`(`id`,`info_id`,`type`,`content`,`sender_id`,`create_time`) VALUES(?,?,?,?,?,?)";
        String id = msg.getMessageId();
        jdbcTemplate.update(sql, id, infoId, msg.getMessageType(), msg.getContent(), msg.getFromUserName(), msg.getCreateTime());
        return id;
    }

    @Override
    public void update(String infoId, String id, boolean ivrmsg) {
        String sql = "UPDATE `message` SET `ivrmsg` = ? WHERE `id` = ? AND `info_id` = ?";
        jdbcTemplate.update(sql, ivrmsg, id, infoId);
    }

    @Override
    public void insertAdditionalInfo(List<Object[]> batchArgs) {
        String sql = "INSERT INTO `message_content`(`msg_id`,`info_id`,`meta_key`,`meta_value`) VALUES(?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

}
