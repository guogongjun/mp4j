package com.yeapoo.odaesan.api.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;
import com.yeapoo.odaesan.api.dao.MenuDao;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insert(String infoId, String parentId, String name, int sequence) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `menu`(`id`,`info_id`,`parent_id`,`name`,`sequence`,`create_time`) VALUES(?,?,?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, parentId, name, sequence);
        return id;
    }

    @Override
    public void insert(String infoId, String parentId, String name, String type, String keycode, String url, int sequence) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `menu`(`id`,`info_id`,`parent_id`,`name`,`type`,`keycode`,`url`,`sequence`,`create_time`) VALUES(?,?,?,?,?,?,?,?,NOW())";
        jdbcTemplate.update(sql, id, infoId, parentId, name, type, keycode, url, sequence);
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT COUNT(`id`) FROM `menu` WHERE `info_id` = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId);
    }

    @Override
    public List<Map<String, Object>> findAllByParentId(String infoId, String parentId) {
        String sql = "SELECT `id`,`name`,`type`,`keycode`,`url` FROM `menu` WHERE `info_id` = ? AND `parent_id` = ? ORDER BY `sequence`";
        return jdbcTemplate.queryForList(sql, infoId, parentId);
    }

    @Override
    public List<Map<String, Object>> listByParentId(String infoId, String parentId) {
        String sql = "SELECT `id`,`name`,`type`,`keycode`,`url`,`reply_id`,`reply_type`,`sequence` FROM `menu` WHERE `info_id` = ? AND `parent_id` = ? ORDER BY `sequence`";
        return jdbcTemplate.queryForList(sql, infoId, parentId);
    }

    @Override
    public Map<String, Object> getParentIdAndSequenceById(String infoId, String id) {
        String sql = "SELECT `parent_id`, `sequence` FROM `menu` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    @Override
    public void update(String infoId, String id, String name, int sequence) {
        String sql = "UPDATE `menu` SET `name` = ?, `sequence` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, name, sequence, id);
    }

    @Override
    public void updateByParentIdAndSequence(String infoId, String parentId, int originalSequence, int targetSequence) {
        String sql = "UPDATE `menu` SET `sequence` = ? WHERE `info_id` = ? AND `parent_id` = ? AND `sequence` = ?";
        jdbcTemplate.update(sql, originalSequence, infoId, parentId, targetSequence);
    }

    @Override
    public void bindClickReply(String infoId, String menuId, String keycode, String replyId, String replyType) {
        String sql = "UPDATE `menu` SET `type` = 'click', `keycode` = ?, `reply_id` = ?, `reply_type` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, keycode, replyId, replyType, menuId);
    }

    @Override
    public void bindViewReply(String infoId, String menuId, String url) {
        String sql = "UPDATE `menu` SET `type` = 'view`, `url` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, url, menuId);
    }

    @Override
    public void unbindReply(int infoId, String menuId) {
        String sql = "UPDATE `menu` SET `type` = NULL, `keycode` = NULL, `url` = NULL, `reply_id` = ?, `reply_type` = ? WHERE `id` = ?";
        jdbcTemplate.update(sql, menuId);
    }

    @Override
    public void delete(String infoId, String id) {
        String sql = "DELETE FROM `menu` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void truncate(String infoId) {
        String sql = "DELETE FROM `menu` WHERE `info_id` = ?";
        jdbcTemplate.update(sql, infoId);
    }
}
