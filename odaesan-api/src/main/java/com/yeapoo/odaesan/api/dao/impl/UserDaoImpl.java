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
import com.yeapoo.odaesan.common.model.Pagination;
import com.yeapoo.odaesan.sdk.model.Follower;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(String infoId, List<Follower> followerList) {
        String sql = "INSERT INTO `user`(`openid`,`info_id`,`nickname`,`country`,`province`,`city`,`gender`,`avatar`,`language`,`unionid`,`remark`,`subscribed`,`subscribe_time`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Follower follower : followerList) {
            batchArgs.add(new Object[] {
                    follower.getOpenid(),
                    infoId,
                    follower.getNickname(),
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
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public int count(String infoId, String groupId) {
        String sql = "SELECT COUNT(`openid`) FROM `user_group_mapping` WHERE `info_id` = ? AND `group_id` = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, infoId, groupId);
    }

    @Override
    public List<Map<String, Object>> findAll(String infoId, String groupId, Pagination pagination) {
        String sql = "SELECT `u`.`openid`,`nickname`,`avatar`" +
                " FROM `user` `u`" +
                " JOIN `user_group_mapping` `m` ON `u`.`openid` = `m`.`openid`" +
                " WHERE `u`.`info_id` = ? AND `m`.`group_id` = ?" +
                " ORDER BY `m`.`create_time` DESC" +
                " LIMIT ?,?";
        return jdbcTemplate.queryForList(sql, infoId, groupId, pagination.getOffset(), pagination.getSize());
    }

    @Override
    public Map<String, Object> get(String infoId, String openid) {
        String sql = "SELECT * FROM `user` WHERE `openid` = ?";
        return jdbcTemplate.queryForMap(sql, openid);
    }

    @Override
    public List<String> findByGroup(String infoId, String groupId) {
        String sql = "SELECT `openid` FROM `user_group_mapping` WHERE `info_id` = ? AND `group_id` = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, infoId, groupId);
        return flat(list);
    }

    @Override
    public List<String> findByGroupAndGender(String infoId, String groupId, String gender) {
        String sql = "SELECT `u`.`openid`"
                + " FROM `user_group_mapping` `m`"
                + " JOIN `user` `u` ON `m`.`openid` = `u`.`openid`"
                + " WHERE `m`.`group_id` = ? AND `u`.`gender` = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, groupId, gender);
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
}
