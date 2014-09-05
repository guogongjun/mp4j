package com.yeapoo.odaesan.irs.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.odaesan.common.util.StringUtil;
import com.yeapoo.odaesan.irs.dao.UserDao;
import com.yeapoo.odaesan.sdk.model.Follower;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String infoId, Follower follower) {
        String sql = "INSERT INTO `user`(`openid`,`info_id`,`nickname`,`country`,`province`,`city`,`gender`,`avatar`,`language`,`unionid`,`remark`,`subscribed`,`subscribe_time`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql, 
                follower.getOpenid(),
                infoId,
                StringUtil.filterUnusualChar(follower.getNickname()),
                follower.getCountry(),
                follower.getProvince(),
                follower.getCity(),
                follower.getGender(),
                follower.getHeadImageURL(),
                follower.getLanguage(),
                follower.getUnionid(),
                follower.getRemark(),
                follower.getSubscribe(),
                new Date(follower.getSubscribeTime() * 1000));
        } catch (Exception e) {
            sql = "UPDATE `user` SET `info_id`=?,`nickname`=?,`country`=?,`province`=?,`city`=?,`gender`=?,`avatar`=?,`language`=?,`unionid`=?,`remark`=?,`subscribed`=?,`subscribe_time`=?,`unsubscribe_time`=NULL,`ungrouped`=1 WHERE `openid` = ?";
            jdbcTemplate.update(sql, 
                    infoId,
                    StringUtil.filterUnusualChar(follower.getNickname()),
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
                    follower.getOpenid());
        }
    }

    @Override
    public void updateUnsubscribeTime(String infoId, String openid) {
        String sql = "UPDATE `user` SET `subscribed` = 0, `unsubscribe_time` = NOW() WHERE `openid` = ?";
        jdbcTemplate.update(sql, openid);
    }

}
