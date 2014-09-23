# Odaesan(微信API)V1版接口文档 

<!-- MarkdownTOC depth=3 autolink=true bracket=round -->

- [文档说明](#文档说明)
- [用户信息](#用户信息)
    - [从微信拉取用户信息](#从微信拉取用户信息)
    - [创建用户分组](#创建用户分组)
    - [获取用户分组](#获取用户分组)
    - [更新分组名称](#更新分组名称)
    - [删除分组](#删除分组)
    - [获取用户列表](#获取用户列表)
    - [获取单个用户信息](#获取单个用户信息)
    - [获取用户所属分组](#获取用户所属分组)
    - [添加用户到特定分组](#添加用户到特定分组)
    - [批量添加用户到特定分组](#批量添加用户到特定分组)
    - [将用户从特定分组中删除](#将用户从特定分组中删除)
    - [批量将用户从特定分组中删除](#批量将用户从特定分组中删除)
- [素材 - 文本](#素材---文本)
    - [保存文本](#保存文本)
    - [获取文本](#获取文本)
- [素材 - 图片](#素材---图片)
    - [上传图片](#上传图片)
    - [获取图片列表, 支持分页](#获取图片列表-支持分页)
    - [获取图片](#获取图片)
    - [下载图片](#下载图片)
    - [重命名图片](#重命名图片)
    - [删除图片](#删除图片)
- [素材 - 语音](#素材---语音)
    - [上传语音](#上传语音)
    - [获取语音列表, 支持分页](#获取语音列表-支持分页)
    - [获取语音](#获取语音)
    - [下载语音](#下载语音)
    - [重命名语音](#重命名语音)
    - [删除语音](#删除语音)
- [素材 - 视频](#素材---视频)
    - [上传视频](#上传视频)
    - [为视频消息添加或更新标题和描述信息](#为视频消息添加或更新标题和描述信息)
    - [更新视频消息的视频](#更新视频消息的视频)
    - [获取视频消息列表](#获取视频消息列表)
    - [获取单个视频消息信息](#获取单个视频消息信息)
    - [删除单个视频消息信息](#删除单个视频消息信息)
- [素材 - 图文](#素材---图文)
    - [上传图文的图片](#上传图文的图片)
    - [保存单图文消息](#保存单图文消息)
    - [保存多图文消息](#保存多图文消息)
    - [获取图文列表, 支持分页](#获取图文列表-支持分页)
    - [获取单个图文信息](#获取单个图文信息)
    - [修改单图文消息](#修改单图文消息)
    - [修改多图文消息](#修改多图文消息)
    - [删除图文消息](#删除图文消息)
- [关键字回复](#关键字回复)
    - [保存关键字消息回复规则](#保存关键字消息回复规则)
    - [获取关注回复信息](#获取关注回复信息)
    - [获取消息回复信息](#获取消息回复信息)
    - [获取规则列表, 支持分页](#获取规则列表-支持分页)
    - [获取单个规则信息](#获取单个规则信息)
    - [更新消息回复](#更新消息回复)
    - [删除规则信息](#删除规则信息)
- [自定义菜单](#自定义菜单)
    - [从微信拉取菜单信息](#从微信拉取菜单信息)
    - [发布菜单](#发布菜单)
    - [列出菜单信息](#列出菜单信息)
    - [创建菜单按钮](#创建菜单按钮)
    - [更新菜单名称与顺序](#更新菜单名称与顺序)
    - [删除菜单按钮](#删除菜单按钮)
    - [绑定菜单回复内容](#绑定菜单回复内容)
    - [解绑菜单回复内容](#解绑菜单回复内容)
- [消息管理](#消息管理)
    - [列出接收到的消息, 支持分页](#列出接收到的消息-支持分页)
- [群发消息](#群发消息)
    - [发送群发消息](#发送群发消息)
    - [列出发出的群发消息, 支持分页](#列出发出的群发消息-支持分页)
    - [获取群发消息的统计状态](#获取群发消息的统计状态)

<!-- /MarkdownTOC -->


## 文档说明
- 以$开头, 后接大写字母的, 为需要替换的变量, 如$BASE_URL, $INFO_ID
- 接口返回数据格式统一为
        {"code":$CODE,"message":"$MESSAGE","data":$DATA}
    每个接口返回的数据在$DATA中
- code含义 // TODO 后续会增加, 目前统一有如下规范
    - 2XX 请求成功
    - 4XX 客户端错误
    - 5XX 服务器错误

## 用户信息

### 从微信拉取用户信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/fetch
- Params:
    - force, string, 可选, 建议第一次调用不传，如果客户确认覆盖拉取，则传1
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```
- 当已经拉取过后返回示例:
```
{"code":400,"message":"follower info has already been fetched","data":null}
```

### 创建用户分组
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/group
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - name, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 获取用户分组
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/group
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"total":$NUM,"groups":[{"id":"$ID1","name":"$NAME1","num":$NUM1},{"id":"$ID2","name":"$NAME2","num":$NUM2}]}}
```

### 更新分组名称
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/group/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - name, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除分组
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/group/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 获取用户列表
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user
- Params:
    - group, string, 必须, 分组的ID
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"user":[{"openid":"$OPENID1","nickname":"$NICK1","avatar":"$URL1"},{"openid":"$OPENID2","nickname":"$NICK2","avatar":"$URL2"}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取单个用户信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"openid":"$OPENID","nickname":"$NICK","country":"$","province":"$","city":"$","gender":1,"avatar":"$URL","language":"zh_CN","subscribed":true,"subscribe_time":1403163520000}}
```

### 获取用户所属分组
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/groups/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":["$GROUP1","$GROUP2"]}
```

### 添加用户到特定分组
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/add
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - openid, string, 必须
    - group_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 批量添加用户到特定分组
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/add/batch
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - openid, json array, 必须, 其中每个item都是一个openid
    - group_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 将用户从特定分组中删除
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/rm
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - openid, string, 必须
    - group_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 批量将用户从特定分组中删除
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/user/rm/batch
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - openid, json array, 必须, 其中每个item都是一个openid
    - group_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 素材 - 文本

### 保存文本
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/text
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - content, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 获取文本
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/text/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID","content":"$CONTENT"}}
```

## 素材 - 图片

### 上传图片
- HTTP Method: POST/FORM
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image
- Params:
    - file, jpg格式文件, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID","name":"$NAME","url":"$URL"}}
```

### 获取图片列表, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"image":[{"id":"$ID","name":"$NAME","url":"$URL"}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取图片
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image/url/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"name":"$name","url":"$URL"}}
```

### 下载图片
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image/$ID
- Params: 无
- 返回示例: 返回为流, 直接下载

### 重命名图片
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - name, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除图片
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/image/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 素材 - 语音

### 上传语音
- HTTP Method: POST/FORM
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice
- Params:
    - file, mp3格式文件, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID","name":"$NAME","url":"$URL"}}
```

### 获取语音列表, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"image":[{"id":"$ID","name":"$NAME","url":"$URL"}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取语音
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice/url/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"name":"$name","url":"$URL"}}
```

### 下载语音
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice/$ID
- Params: 无
- 返回示例: 返回为流, 直接下载

### 重命名语音
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - name, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除语音
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/voice/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 素材 - 视频

### 上传视频
- HTTP Method: POST/FORM
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video/upload
- Params:
    - file, mp4格式文件, 必须
- 返回示例: 注意，此处返回的ID是整个视频消息的ID
```
{"code":200,"message":"OK","data":{"id":"$ID","url":"$URL"}}
```

### 为视频消息添加或更新标题和描述信息
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video/$ID
- Params:
    - title, string, 必须
    - description, string, 可选
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 更新视频消息的视频
- HTTP Method: POST/FROM
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video/upload/$ID
- Params:
    - file, mp4格式文件, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 获取视频消息列表
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"video":[{"id":"$ID","title":"$TITLE","description":"$DESCRIPTION","url":"$URL","create_time":$},{"id":"$ID","title":"$TITLE","description":"$DESCRIPTION","url":"$URL","create_time":$}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取单个视频消息信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"title":"$TITLE","description":"$DESCRIPTION","url":"$URL","create_time":$}}
```

### 删除单个视频消息信息
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/video/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 素材 - 图文

### 上传图文的图片
- HTTP Method: POST/FORM
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/image
- Params:
    - file, jpg格式文件, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID","url":"$URL"}}
```

### 保存单图文消息
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/single
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - title, string, 必须
    - author, string, 可选
    - image_id, string, 必须
    - digest, string, 可选
    - content, string, 必须
    - content_source_url, string, 可选
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 保存多图文消息
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/multiple
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Array, 每个item的key-value格式如下
    - title, string, 必须
    - author, string, 可选
    - image_id, string, 必须
    - digest, string, 可选
    - content, string, 必须
    - content_source_url, string, 可选
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 获取图文列表, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"news":{"$ID1":[{"update_time":$TIMESTAMP1,"title":"$TITLE1","url":"$URL1","digest":"$DIGEST1"}],"$ID2":[{"update_time":$TIMESTAMP2,"title":"$TITLE2","url":"$URL2","digest":"$DIGEST2"},{"update_time":$TIMESTAMP3,"title":"$TITLE3","url":"$URL3","digest":"$DIGEST3"}]},"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取单个图文信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/$ID
- Params: 无
- 返回示例: 当为单图文时data数组里只有一个item
```
{"code":200,"message":"OK","data":[{"update_time":$TIMESTAMP2,"title":"$TITLE2","author":"$AUTHOR2","image_id":"$IMAGEID2","url":"$URL2","digest":"$DIGEST2","content":"$CONTENT2","content_source_url":"$SRC2"},{"update_time":$TIMESTAMP3,"title":"$TITLE3","author":"$AUTHOR3","image_id":"$IMAGEID3",url":"$URL3","digest":"$DIGEST3","content":"$CONTENT3","content_source_url":"$SRC3"}]}
```

### 修改单图文消息
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/single/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - title, string, 必须
    - author, string, 可选
    - image_id, string, 必须
    - digest, string, 可选
    - content, string, 必须
    - content_source_url, string, 可选
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 修改多图文消息
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/multiple/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Array, 每个item的key-value格式如下
    - title, string, 必须
    - author, string, 可选
    - image_id, string, 必须
    - digest, string, 可选
    - content, string, 必须
    - content_source_url, string, 可选
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除图文消息
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/news/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 关键字回复

### 保存关键字消息回复规则
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - rule_name, string, 必须, 如果是关注回复, 此项固定为__subscribe, 如果是消息回复, 此项固定为__default
    - keyword, json array, 必须, 其中每个item的key-value格式如下, 注意: *如果是关注回复/消息回复, 此项不传*
        - content, string, 必须
        - fuzzy, string, 必须, "0"表示完全匹配, "1"表示模糊匹配
    - reply_id, string, 必须
    - reply_type, string, 必须, 值为(text,image,news,video,music)中的一个
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 获取关注回复信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword/subscribe
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID",reply_id":"$REPLY_ID","reply_type":"$REPLY_TYPE"}}
```

### 获取消息回复信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword/default
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"id":"$ID",reply_id":"$REPLY_ID","reply_type":"$REPLY_TYPE"}}
```

### 获取规则列表, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"keyword":[{"id":"$ID","name":"$NAME","keywords":"$KEYWORD1,$KEYWORD2"}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取单个规则信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"name":"$NAME","reply_id":"$REPLY_ID","reply_type":"$REPLY_TYPE","keyword":[{"id":"$ID1","content":"$CONTENT1","fuzzy":$BOOLEAN},{"id":"$ID2","content":"$CONTENT2","fuzzy":$BOOLEAN}]}}
```

### 更新消息回复
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword/$ID
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - rule_name, string, 必须, 如果是关注回复, 此项固定为__subscribe, 如果是消息回复, 此项固定为__default
    - keyword, json array, 必须, 其中每个item的key-value格式如下, 注意: *如果是关注回复/消息回复, 此项不传*
        - content, string, 必须
        - fuzzy, string, 必须, "0"表示完全匹配, "1"表示模糊匹配
    - reply_id, string, 必须
    - reply_type, string, 必须, 值为(text,image,news,video,music)中的一个
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除规则信息
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/keyword/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 自定义菜单

### 从微信拉取菜单信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/fetch
- Params:
    - force, string, 可选, 建议第一次调用不传，如果客户确认覆盖拉取，则传1
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```
- 当已经拉取过后返回示例:
```
{"code":400,"message":"menu info has already been fetched","data":null}
```

### 发布菜单
- HTTP Method: POST
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/publish
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 列出菜单信息
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":[{"id":"7fbd286b-d4eb-46de-9a80-9e601f76c861","name":"移动网站","type":null,"keycode":null,"url":null,"reply_id":null,"reply_type":null,"sequence":1,"sub_button":[{"id":"2a759423-ac8d-48c1-8fab-d12c24936702","name":"Yeapoo","type":"view","keycode":null,"url":"http://m.yeapoo.com","reply_id":null,"reply_type":null,"sequence":1},{"id":"626aad83-25d8-4f15-b4af-f05459baea1f","name":"POP","type":"click","keycode":"V20131226171926_pop_199","url":null,"reply_id":null,"reply_type":null,"sequence":2},{"id":"96356d85-5def-4bdf-aaa7-780d23508672","name":"km","type":"click","keycode":"V20131226171935_km_199","url":null,"reply_id":null,"reply_type":null,"sequence":3},{"id":"c658e569-354a-4c54-9dd2-e57b8bc7ae2b","name":"伊顿","type":"click","keycode":"V20131226172002_YD_199","url":null,"reply_id":null,"reply_type":null,"sequence":4},{"id":"95c5a828-e8b6-42ba-83ab-9e39c50c2cd8","name":"qwe","type":"click","keycode":"V20131226172022_qwe_199","url":null,"reply_id":null,"reply_type":null,"sequence":5}]},{"id":"8d43c613-29f2-48ac-b49f-5e2e9159d9bb","name":"案例","type":null,"keycode":null,"url":null,"reply_id":null,"reply_type":null,"sequence":2,"sub_button":[{"id":"40d7ae84-134a-41d9-802a-3f56c8d35d16","name":"果园","type":"click","keycode":"V20140603155931_GY_199","url":null,"reply_id":null,"reply_type":null,"sequence":1}]},{"id":"438a912a-de24-40cf-9c7a-39cd01848b40","name":"新闻","type":null,"keycode":null,"url":null,"reply_id":null,"reply_type":null,"sequence":3,"sub_button":[{"id":"d26bf1d4-76df-44b2-82cd-b88e02a1c831","name":"社会","type":"click","keycode":"V20131226172719_SH_199","url":null,"reply_id":null,"reply_type":null,"sequence":1},{"id":"fee127ae-b36f-4f02-868e-9a7ea7220d79","name":"科技","type":"click","keycode":"V20131226172726_KJ_199","url":null,"reply_id":null,"reply_type":null,"sequence":2}]}]}
```

### 创建菜单按钮
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu
- Params: JSON Object, key-value格式如下
    - parent_id, string, 必须, 如果是顶级菜单, 则传"0"
    - name, string, 必须
    - sequence, int, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":"$ID"}
```

### 更新菜单名称与顺序
- HTTP Method: PUT/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/$ID
- Params: JSON Object, key-value格式如下
    - name, string, 必须
    - sequence, int, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 删除菜单按钮
- HTTP Method: DELETE
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 绑定菜单回复内容
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/bind
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - menu_id, string, 必须
    - reply_id, string, 必须, 当值为link时, 直接传入URL
    - reply_type, string, 必须, 值为(text,image,news,video,music,link)中的一个
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 解绑菜单回复内容
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/menu/unbind
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - menu_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

## 消息管理

### 列出接收到的消息, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/msg
- Params:
    - start_date, date, 可选, 包含
    - end_date, date, 可选, 不包含, 示例: 设今天为2014－08-27, 若想获得今天的消息, 则start_date=2014-08-27, end_date=2014-08-28; 若想获得昨天的消息, 则start_date=2014-08-26, end_date=2014-08-27; 若想获得更多消息, 则end_date=2014-08-25
    - filterivrmsg, 0/1, 可选, 是否过滤关键字
    - filter, string, 可选, 模糊搜索内容
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
        {
            "code": 200,
            "message": "OK",
            "data": {
                "pagination": {
                    "index": 1,
                    "size": 10,
                    "count": 6
                },
                "message": [
                    {
                        "id": "1",
                        "type": "text",
                        "content": "这里是内容1",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    },
                    {
                        "id": "2",
                        "type": "image",
                        "media_id": "",
                        "pic_url": "",
                        "url": "",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    },
                    {
                        "id": "3",
                        "type": "voice",
                        "media_id": "",
                        "url": "",
                        "format": "",
                        "recognition": "",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    },
                    {
                        "id": "4",
                        "type": "video",
                        "media_id": "",
                        "url": "",
                        "thumb_media_id": "",
                        "thumb_url": "",
                        "title": "",
                        "description": "",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    },
                    {
                        "id": "5",
                        "type": "location",
                        "latitude": "",
                        "longitude": "",
                        "scale": "",
                        "label": "",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    },
                    {
                        "id": "6",
                        "type": "link",
                        "url": "",
                        "title": "",
                        "description": "",
                        "sender_id": "oYxv8t0KxWPTN-hY_CQbqzgBUcZk",
                        "nickname": "",
                        "avatar": "",
                        "create_time": 1406822400000
                    }
                ]
            }
        }
```

## 群发消息

### 发送群发消息
- HTTP Method: POST/Raw
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/masssend/send
- HTTP Header:
    - Content-Type: application/json
- Params: JSON Object, key-value格式如下
    - group_id, string, 必须
    - gender, string, 可选, 值为0(未知),1(男),2(女)中的一个
    - msg_type, string, 必须, 值为(text,image,mpnews,mpvideo,voice)中的一个
    - msg_id, string, 必须
- 返回示例:
```
{"code":200,"message":"OK","data":null}
```

### 列出发出的群发消息, 支持分页
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/masssend
- Params:
    - index, int, 可选, 默认1
    - size, int, 可选, 默认10
- 返回示例:
```
{"code":200,"message":"OK","data":{"message":[{"id":"$ID","msg_type":"image","msg_id":"$MSG_ID","create_time":"$TIMESTAMP","name":"$NAME","url":"$URL"},{"id":"$ID","msg_type":"news","msg_id":"$MSG_ID","create_time":"$TIMESTAMP","news":[{"update_time":"$TIMESTAMP","title":"$TITLE","author":"$AUTHOR","image_id":"$","url":"$URL","digest":"$","content":"$","content_source_url":"$"},{}]},{"id":"$ID","msg_type":"video","msg_id":"$MSG_ID","create_time":"$TIMESTAMP","title":"$","description":"$","url":"$"},{"id":"$ID","msg_type":"voice","msg_id":"$MSG_ID","create_time":"$TIMESTAMP","name":"$","url":"$"},{"id":"$ID","msg_type":"text","msg_id":"$MSG_ID","create_time":"$TIMESTAMP","content":"$"}],"pagination":{"index":$INDEX,"size":$SIZE,"count":$COUNT}}}
```

### 获取群发消息的统计状态
- HTTP Method: GET
- HTTP URL: http://$BASE_URL/v1/$INFO_ID/masssend/$ID
- Params: 无
- 返回示例:
```
{"code":200,"message":"OK","data":{"status":"$STATUS","total_count":$,"filter_count":$,"sent_count":$,"error_count":$,}}
```