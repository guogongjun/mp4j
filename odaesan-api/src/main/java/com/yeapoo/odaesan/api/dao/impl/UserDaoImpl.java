package com.yeapoo.odaesan.api.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.MapUtil;
import com.yeapoo.odaesan.api.dao.UserDao;
import com.yeapoo.odaesan.common.adapter.FollowerWrapper;
import com.yeapoo.odaesan.common.constants.Constants;
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.common.util.StringUtil;
import com.yeapoo.odaesan.sdk.model.Follower;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(String infoId, List<FollowerWrapper> followerList) {
        String sql = "INSERT INTO `user`(`openid`,`info_id`,`nickname`,`country`,`province`,`city`,`gender`,`avatar`,`language`,`unionid`,`remark`,`subscribed`,`subscribe_time`,`ungrouped`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        Follower follower = null;
        for (FollowerWrapper wrapper : followerList) {
            follower = wrapper.getFollower();
            batchArgs.add(new Object[] {
                    follower.getOpenid(),
                    infoId,
                    StringUtil.filterUTF8MB4(follower.getNickname()),
                    follower.getCountry(),
                    follower.getProvince(),
                    follower.getCity(),
                    follower.getGender(),
                    follower.getHeadImageURL(),
                    follower.getLanguage(),
                    follower.getUnionid(),
                    follower.getRemark(),
                    follower.getSubscribe(),
                    new Date(follower.getSubscribeTime() * 1000),
                    wrapper.isUngrouped()
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int count(String infoId) {
        String sql = "SELECT COUNT(`u`.`openid`)"
                + " FROM `user` `u`"
                + " LEFT JOIN `user_group_mapping` `m` ON `u`.`openid` = `m`.`openid`"
                + " WHERE `u`.`info_id` = ? AND `u`.`subscribed` = 1 AND `m`.`group_id` != ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId, Constants.UserGroup.BLACKLIST_ID);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, Pagination pagination) {
        String sql = "SELECT `u`.`openid`,`nickname`,`avatar`"
                + " FROM `user` `u`"
                + " LEFT JOIN `user_group_mapping` `m` ON `u`.`openid` = `m`.`openid`"
                + " WHERE `u`.`info_id` = ? AND `u`.`subscribed` = 1 AND `m`.`group_id` != ?"
                + " ORDER BY `u`.`subscribe_time` DESC"
                + " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, Constants.UserGroup.BLACKLIST_ID, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public int count(String infoId, String groupId) {
        if (Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            String sql = "SELECT COUNT(`openid`) FROM `user` WHERE `info_id` = ? AND `subscribed` = 1 AND `ungrouped` = 1";
            return jdbcTemplate.queryForObject(sql, Integer.class, infoId);
        } else {
            String sql = "SELECT COUNT(`openid`) FROM `user_group_mapping` WHERE `info_id` = ? AND `group_id` = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, infoId, groupId);
        }
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, String groupId, Pagination pagination) {
        if (Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            String sql = "SELECT `openid`,`nickname`,`avatar`"
                    + " FROM `user`"
                    + " WHERE `info_id` = ? AND `subscribed` = 1 AND `ungrouped` = 1"
                    + " ORDER BY `subscribe_time` DESC"
                    + " LIMIT ?,?";
            return jdbcTemplate.queryForList(sql, infoId, pagination.getOffset(), pagination.getSize());
        } else {
            String sql = "SELECT `u`.`openid`,`nickname`,`avatar`" +
                " FROM `user` `u`" +
                " JOIN `user_group_mapping` `m` ON `u`.`openid` = `m`.`openid`" +
                " WHERE `u`.`info_id` = ? AND `m`.`group_id` = ? AND `u`.`subscribed` = 1" +
                " ORDER BY `m`.`create_time` DESC" +
                " LIMIT ?,?";
            return jdbcTemplate.queryForList(sql, infoId, groupId, pagination.getOffset(), pagination.getSize());
        }
    }

    @Override
    public Map<String, Object> get(String infoId, String openid) {
        String sql = "SELECT * FROM `user` WHERE `openid` = ?";
        return jdbcTemplate.queryForMap(sql, openid);
    }

    @Override
    public List<String> findByGroup(String infoId, String groupId) {
        String sql = null;
        List<Map<String, Object>> list = null;
        if (Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            sql = "SELECT `openid` FROM `user` WHERE `info_id` = ? AND `subscribed` = 1 AND `ungrouped` = 1";
            list = jdbcTemplate.queryForList(sql, infoId);
        } else {
            sql = "SELECT `openid` FROM `user_group_mapping` WHERE `info_id` = ? AND `group_id` = ?";
            list = jdbcTemplate.queryForList(sql, infoId, groupId);
        }
        return flat(list);
    }

    @Override
    public List<String> findByGroupAndGender(String infoId, String groupId, String gender) {
        String sql = null;
        List<Map<String, Object>> list = null;
        if (Constants.UserGroup.UNGROUPED_ID.equals(groupId)) {
            sql = "SELECT `openid` FROM `user` WHERE `info_id` = ? AND `gender` = ? AND `subscribed` = 1 AND `ungrouped` = 1";
            list = jdbcTemplate.queryForList(sql, infoId, gender);
        } else {
            sql = "SELECT `u`.`openid`"
                    + " FROM `user_group_mapping` `m`"
                    + " JOIN `user` `u` ON `m`.`openid` = `u`.`openid`"
                    + " WHERE `u`.`info_id` = ? AND `m`.`group_id` = ? AND `u`.`gender` = ?";
            list = jdbcTemplate.queryForList(sql, infoId, groupId, gender);
        }
        return flat(list);
    }

    @Override
    public void truncate(String infoId) {
        String sql = "DELETE FROM `user` WHERE `info_id` = ?";
        jdbcTemplate.update(sql, infoId);
    }

    private List<String> flat(List<Map<String, Object>> list) {
        List<String> openidList = new ArrayList<String>();
        for (Map<String, Object> map : list) {
            openidList.add(MapUtil.get(map, "openid"));
        }
        return openidList;
    }

    @Override
    public void updateUngrouped(String infoId, String openid, boolean ungrouped) {
        String sql = "UPDATE `user` SET `ungrouped` = ? WHERE `openid` = ?";
        jdbcTemplate.update(sql, infoId, openid);
    }

    @Override
    public void batchUpdateUngrouped(String infoId, List<String> openidList, boolean ungrouped) {
        String sql = "UPDATE `user` SET `ungrouped` = ? WHERE `openid` = ?";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String openid : openidList) {
            batchArgs.add(new Object[] {ungrouped, openid});
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
