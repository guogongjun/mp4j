package com.yeapoo.odaesan.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.dao.MessageDao;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.message.Message;
import com.yeapoo.odaesan.sdk.model.message.TextMessage;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter) {
        String basicSql = "SELECT COUNT(`id`)"
                + " FROM `message`"
                + " WHERE `info_id` = ? AND `type` != 'event'";
        StringBuilder sqlBuilder = new StringBuilder(basicSql);
        List<Object> args = new ArrayList<Object>();
        args.add(infoId);
        if (StringUtils.hasText(startDate)) {
            sqlBuilder.append(" AND `create_time` > ?");
            args.add(startDate);
        }
        if (StringUtils.hasText(endDate)) {
            sqlBuilder.append(" AND `create_time` < ?");
            args.add(endDate);
        }
        if (filterivrmsg) {
            sqlBuilder.append(" AND `ivrmsg` = 0");
        }
        if (null != filter) {
            sqlBuilder.append(" AND `content` LIKE '%" + filter +"%'");
        }
        return jdbcTemplate.queryForObject(sqlBuilder.toString(), Integer.class, args.toArray());
    }

    @Override
    public List<Map<String, Object>> list(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter, Pagination pagination) {
        String basicSql = "SELECT `m`.`id`, `m`.`type`, `m`.`content`, `m`.`sender_id`, `u`.`nickname`,`u`.`avatar`, `m`.`create_time`"
                + " FROM `message` `m`"
                + " JOIN `user` `u` ON `m`.`sender_id` = `u`.`openid`"
                + " WHERE `m`.`info_id` = ? AND `m`.`type` != 'event'";
        StringBuilder sqlBuilder = new StringBuilder(basicSql);
        List<Object> args = new ArrayList<Object>();
        args.add(infoId);
        if (StringUtils.hasText(startDate)) {
            sqlBuilder.append(" AND `create_time` > ?");
            args.add(startDate);
        }
        if (StringUtils.hasText(endDate)) {
            sqlBuilder.append(" AND `create_time` < ?");
            args.add(endDate);
        }
        if (filterivrmsg) {
            sqlBuilder.append(" AND `ivrmsg` = 0");
        }
        if (null != filter) {
            sqlBuilder.append(" AND `content` LIKE '%" + filter + "%'");
        }
        sqlBuilder.append(" ORDER BY `create_time` DESC LIMIT ?,?");
        args.add(pagination.getOffset());
        args.add(pagination.getSize());
        return jdbcTemplate.queryForList(sqlBuilder.toString(), args.toArray());
    }

    @Override
    public List<Map<String, Object>> getAdditionalInfo(String infoId, String msgId) {
        String sql = "SELECT `meta_key`, `meta_value` FROM `message_content` WHERE `info_id` = ? AND `msg_id` = ?";
        return jdbcTemplate.queryForList(sql, infoId, msgId);
    }


    @Override
    public String insert(String infoId, Message msg) {
        String sql = "INSERT INTO `message`(`id`,`info_id`,`type`,`sender_id`,`create_time`) VALUES(?,?,?,?,?)";

        String id = msg.getMessageId();
        if (!StringUtils.hasText(id)) {
            id = IDGenerator.generate(Object.class);
        }

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
