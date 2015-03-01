package com.yeapoo.odaesan.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.common.dao.UserGroupDao;
import com.yeapoo.odaesan.sdk.model.Group;

@Repository
public class UserGroupDaoImpl implements UserGroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, String name) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `user_group`(`id`,`info_id`,`name`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, name);
        return id;
    }

    @Override
    public void insert(String infoId, String id, String name) {
        String sql = "INSERT INTO `user_group`(`id`,`info_id`,`name`,`create_time`) VALUES(?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, name);
    }

    @Override
    public void batchInsert(String infoId, List<Group> groups) {
        String sql = "INSERT INTO `user_group`(`id`,`info_id`,`wx_group_id`,`name`,`create_time`) VALUES(?,?,?,?,NOW())";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Group group : groups) {
            batchArgs.add(new Object[] {
                    group.getId(),
                    infoId,
                    group.getId(),
                    group.getName()
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT count(`id`) FROM `user_group` WHERE `info_id` = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId) {
        String sql = "SELECT `id`, `name` FROM `user_group` WHERE `info_id` = ? AND `delete_time` IS NULL ORDER BY CAST(`id` as SIGNED)";
        return jdbcTemplate.queryForList(sql, infoId);
    }

    @Override
    public void update(String infoId, String id, String name) {
        String sql = "UPDATE `user_group` SET `name` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, name, id);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "UPDATE `user_group` SET `delete_time` = NOW() WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void truncate(String infoId) {
        String sql = "DELETE FROM `user_group` WHERE `info_id` = ?";
        jdbcTemplate.update(sql, infoId);
    }

}
