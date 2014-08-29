package com.yeapoo.odaesan.irs.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.irs.dao.UserGroupMappingDao;

@Repository
public class UserGroupMappingDaoImpl implements UserGroupMappingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String openid, String groupId) {
        String sql = "INSERT IGNORE INTO `user_group_mapping`(`info_id`,`openid`,`group_id`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, infoId, openid, groupId);
    }

    @Override
    public void removeByOpenid(String infoId, String openid) {
        String sql = "DELETE FROM `user_group_mapping` WHERE `openid` = ?";
        jdbcTemplate.update(sql, openid);
    }

}
