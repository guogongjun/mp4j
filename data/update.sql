ALTER TABLE `material_news_item` 
ADD COLUMN `show_cover_pic` TINYINT(1) NOT NULL DEFAULT 1 AFTER `image_id`;

ALTER TABLE `app_info` 
ADD COLUMN `name` VARCHAR(100) NOT NULL AFTER `weixin_id`;

ALTER TABLE `material_news_item` 
ADD COLUMN `delete_time` DATETIME NULL AFTER `sequence`;

ALTER TABLE `user` 
ADD COLUMN `blocked` TINYINT(1) NOT NULL DEFAULT 0 AFTER `ungrouped`;