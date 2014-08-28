package com.yeapoo.odaesan.api.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.api.dao.UserGroupMappingDao;

@Repository
public class UserGroupMappingDaoImpl implements UserGroupMappingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, String openid, String target) {
        String sql = "INSERT IGNORE INTO `user_group_mapping`(`info_id`,`openid`,`group_id`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, infoId, openid, target);
    }

    @Override
    public void batchInsert(List<Object[]> groupMappingList) {
        String sql = "INSERT INTO `user_group_mapping`(`info_id`,`openid`,`group_id`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.batchUpdate(sql, groupMappingList);
    }

    @Override
    public void batchInsert(String infoId, List<String> openidList, String target) {
        String sql = "INSERT IGNORE INTO `user_group_mapping`(`info_id`,`openid`,`group_id`,`create_time`) VALUES(?,?,?,NOW())";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String openid : openidList) {
            batchArgs.add(new Object[] {
                    infoId,
                    openid,
                    target
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public List<Map<String, Object>> findByOpenid(String infoId, String openid) {
        String sql = "SELECT `group_id` FROM `user_group_mapping` WHERE `info_id` = ? AND `openid` = ?";
        return jdbcTemplate.queryForList(sql, infoId, openid);
    }

    @Override
    public void delete(String infoId, String openid, String current) {
        String sql = "DELETE FROM `user_group_mapping` WHERE `info_id` = ? AND `openid` = ? AND `group_id` = ?";
        jdbcTemplate.update(sql, infoId, openid, current);
    }

    @Override
    public void batchDelete(String infoId, List<String> openidList, String current) {
        String sql = "DELETE FROM `user_group_mapping` WHERE `info_id` = ? AND `openid` = ? AND `group_id` = ?";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String openid : openidList) {
            batchArgs.add(new Object[] {
                    infoId,
                    openid,
                    current
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public void deleteByGroupId(String infoId, String groupId) {
        String sql = "DELETE FROM `user_group_mapping` WHERE `info_id` = ? AND `group_id` = ?";
        jdbcTemplate.update(sql, infoId, groupId);
    }

    @Override
    public void truncate(String infoId) {
        String sql = "DELETE FROM `user_group_mapping` WHERE `info_id` = ?";
        jdbcTemplate.update(sql, infoId);
    }

}
