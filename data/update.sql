ALTER TABLE `material_news_item` 
ADD COLUMN `show_cover_pic` TINYINT(1) NOT NULL DEFAULT 1 AFTER `image_id`;