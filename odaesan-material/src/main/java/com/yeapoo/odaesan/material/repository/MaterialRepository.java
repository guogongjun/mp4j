package com.yeapoo.odaesan.material.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yeapoo.common.util.IDGenerator;

@Repository
public class MaterialRepository {

    @Value("${static.alias}")
    private String alias;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getText(String infoId, String id) {
        String sql = "SELECT `content` FROM `material_text` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public String getTextContent(String infoId, String id) {
        String sql = "SELECT `content` FROM `material_text` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Map<String, Object> getImage(String infoId, String id) {
        String sql = "SELECT `name`, `url` FROM `material_image` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public String getImageUrl(String infoId, String id) {
        String sql = "SELECT `url` FROM `material_image` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public List<Map<String, Object>> getNews(String infoId, String id) {
        String sql = "SELECT news.update_time, item.title, item.author, item.image_id, CONCAT('%s', image.url) AS url, item.digest, item.content, item.content_source_url"
                + " FROM material_news news"
                + " JOIN material_news_item item ON news.id = item.news_id"
                + " JOIN material_news_image image ON item.image_id = image.id"
                + " WHERE news.id = ? AND news.delete_time IS NULL"
                + " ORDER BY item.sequence";
        return jdbcTemplate.queryForList(String.format(sql, alias), id);
    }

    public List<Map<String, Object>> getNewsForMasssend(String infoId, String id) {
        String sql = "SELECT item.title, item.author, image.url, item.digest, item.content, item.content_source_url"
                + " FROM material_news news"
                + " JOIN material_news_item item ON news.id = item.news_id"
                + " JOIN material_news_image image ON item.image_id = image.id"
                + " WHERE news.id = ? AND news.delete_time IS NULL"
                + " ORDER BY item.sequence";
        return jdbcTemplate.queryForList(sql, id);
    }

    public List<Map<String, Object>> getNewsForReply(String infoId, String id) {
        String sql = "SELECT item.title, item.digest, CONCAT('%s', image.url) AS pic_url, item.content_source_url AS url"
                + " FROM material_news news"
                + " JOIN material_news_item item ON news.id = item.news_id"
                + " JOIN material_news_image image ON item.image_id = image.id"
                + " WHERE news.id = ? AND news.delete_time IS NULL"
                + " ORDER BY item.sequence";
        return jdbcTemplate.queryForList(sql, id);
    }

    public Map<String, Object> getMedia(String infoId, String materialType, String materialId) {
        String sql = "SELECT `media_id`, `create_time` FROM `material_media` WHERE `info_id` = ? AND `material_type` = ? AND `material_id` = ? ORDER BY `create_time` DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForMap(sql, infoId, materialType, materialId);
        } catch (Exception e) {
            return null;
        }
    }

    public String insertMedia(String infoId, String materialType, String materialId, String mediaId, int createTime) {
        String id = IDGenerator.generate(Object.class);
        String sql = "INSERT INTO `material_media`(`id`,`info_id`,`material_type`,`material_id`,`media_id`,`create_time`) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, id, infoId, materialType, materialId, mediaId, createTime);
        return id;
    }

    public String getVoiceUrl(String infoId, String id) {
        String sql = "SELECT `url` FROM `material_voice` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Map<String, Object> getVoiceInfo(String infoId, String id) {
        String sql = "SELECT `name`, `url` FROM `material_voice` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public Map<String, Object> getVideo(String infoId, String id) {
        String sql = "SELECT `title`, `description` FROM `material_video` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }

    public String getVideoUrl(String infoId, String id) {
        String sql = "SELECT `url` FROM `material_video` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Map<String, Object> getVideoAllInfo(String infoId, String id) {
        String sql = "SELECT `title`, `description`, `url` FROM `material_video` WHERE `id` = ?";
        return jdbcTemplate.queryForMap(sql, id);
    }
}
