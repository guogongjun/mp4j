<!DOCTYPE html><%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript">
var navigationStart = false;
if (typeof performance != "undefined" && performance.timing) {
    navigationStart = performance.timing.navigationStart;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="dns-prefetch" href="http://mmbiz.qpic.cn">
<link rel="dns-prefetch" href="http://res.wx.qq.com">
<title>${ title }</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/page_mp_article2059d7.css">
<!--[if lt IE 9]><link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/page/page_mp_article_pc1ffb00.css"/><![endif]-->
<style>
ol,ul{list-style-position: inside;}
</style>
</head>
<body id="activity-detail">
  <div class="rich_media">
    <div class="rich_media_inner">
      <h2 class="rich_media_title" id="activity-name">${ title }</h2>
      <div class="rich_media_meta_list">
        <em id="post-date" class="rich_media_meta text">${ create_time }</em>
        <em class="rich_media_meta text">${ author }</em>
        <a class="rich_media_meta link nickname" href="javascript:viewProfile();" id="post-user">${ name }</a>
      </div>
      <div id="page-content">
        <div id="img-content">
          <c:if test="${ show_cover_pic }">
          <div class="rich_media_thumb" id="media">
            <img onerror="this.parentNode.removeChild(this)" src="${ url }">
          </div>
          </c:if>
          <div class="rich_media_content" id="js_content">
            ${ content }
          </div>
          <div class="rich_media_tool" id="js_toobar">
            <c:if test="${ !empty(content_source_url) }">
            <!-- <a class="media_tool_meta meta_primary" href="javascript:viewSource();">阅读原文</a> -->
            <a class="media_tool_meta meta_primary" href="${ content_source_url }">阅读原文</a>
            </c:if>
            <!-- <a class="media_tool_meta link_primary meta_extra" href="javascript:report_article();">举报</a> -->
          </div>
        </div>
      </div>
      <div id="js_pc_qr_code" class="qr_code_pc_outer">
        <div class="qr_code_pc_inner">
          <div class="qr_code_pc">
            <img id="js_pc_qr_code_img" class="qr_code_pc_img">
            <p>微信扫一扫<br>获得更多内容</p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
            var tid = "";
            var aid = "";
            var uin = "";
            var key = "";
            var biz = "";
            var mid = "";
            var idx = "";
            var appuin = "";

            //下边这两个值为什么调转过来呢？是因为要跟推广页的字段保持一致。。坑爹
            var source = "";
            var scene = 75;

            var itemidx = "";
            var nickname = "${name}";
            var user_name = "${weixin_id}";
            var fakeid = "";
            var version = "";
            var is_limit_user = "0";
            var msg_title = "${title}";
            var msg_desc = "";//"{digest}";
            var msg_cdn_url = document.URL;
            var msg_link = document.URL;
            var msg_source_url = '${content_source_url}';
            var networkType;
            var appmsgid = '';
            var abtest = 0;
            var readNum = 1;

            var likeNum = '赞';
        </script>
  <script type="text/javascript" src="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/js/appmsg205462.js"></script>
</body>
</html>