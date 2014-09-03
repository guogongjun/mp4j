package com.yeapoo.odaesan.api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.yeapoo.odaesan.api.dao.MessageDao;
import com.yeapoo.odaesan.common.model.Pagination;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count(String infoId, String startDate, String endDate, boolean filterivrmsg, String filter) {
        String basicSql = "SELECT COUNT(`id`)"
                + " FROM `message`"
                + " WHERE `info_id` = ?";
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
                + " WHERE `m`.`info_id` = ?";
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

}
