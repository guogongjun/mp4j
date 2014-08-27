package com.yeapoo.odaesan.sdk.constants;

public interface Constants {

    public static final String TEMPLATE_REEPLACE_STR = "<_flag_>";

    interface XmlTag {
        String FROM_USER_NAME = "FromUserName";
        String TO_USER_NAME = "ToUserName";
        String CREATE_TIME = "CreateTime";
        String MSG_TYPE = "MsgType";
        String MSG_ID = "MsgId";
        String CONTENT = "Content";
        String PIC_URL = "PicUrl";
        String MEDIA_ID = "MediaId";
        String FORMAT = "Format";
        String RECOGNITION = "Recognition";
        String THUMB_MEDIA_ID = "ThumbMediaId";
        String LOCATION_X = "Location_X";
        String LOCATION_Y = "Location_Y";
        String SCALE = "Scale";
        String LABEL = "Label";
        String TITLE = "Title";
        String DESCRIPTION = "Description";
        String URL = "Url";
        String EVENT = "Event";
        String EVENT_KEY = "EventKey";
        String TICKET = "Ticket";
        String LATITUDE = "Latitude";
        String LONGITUDE = "Longitude";
        String PRECISION = "Precision";
        String STATUS = "Status";
        String TOTAL_COUNT = "TotalCount";
        String FILTE_RCOUNT = "FilterCount";
        String SENT_COUNT = "SentCount";
        String ERROR_COUNT = "ErrorCount";
    }

    interface MessageType {
        String TEXT = "text";
        String IMAGE = "image";
        String LOCATION = "location";
        String LINK = "link";
        String MUSIC = "music";
        String VOICE = "voice";
        String VIDEO = "video";
        String NEWS = "news";
        String EVENT = "event";
    }

    interface EventType {
        String SUBSCRIBE = "subscribe";
        String UNSUBSCRIBE = "unsubscribe";
        String SCAN = "SCAN";
        String CLICK = "CLICK";
        String VIEW = "VIEW";
        String LOCATION = "LOCATION";
        String MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
        
    }

    public static final String ARG_ACCESS_TOKEN = "access_token";
    public static final String ARG_APP_ID = "appid";
    public static final String ARG_APP_SECRET = "secret";
    public static final String ARG_EXPIRES_IN = "expires_in";
    public static final String ARG_MENU_BUTTON = "button";
    public static final String ARG_TICKET = "ticket";
    public static final String ARG_EXPIRE_SECONDS = "expire_seconds";
    public static final String ARG_NEXT_OPENID = "next_openid";
    public static final String ARG_OPENID = "openid";
    public static final String ARG_TO_GROUP_ID = "to_groupid";
    public static final String ARG_SUBSCRIBE = "subscribe";
    public static final String ARG_TOTAL = "total";
    public static final String ARG_MEDIA_TYPE = "type";
    public static final String ARG_MEDIA_ID = "media_id";
    public static final String ARG_GROUP = "group";

    public static final String RESPONSE_MESSAGE_ERRCODE = "errcode";
    public static final String RESPONSE_MESSAGE_ERRCODE_SUCCESS = "ok";

    public static final String WECHAT_APP_ID = "app.id";
    public static final String WECHAT_APP_SECRET = "app.secret";
    public static final String API_ACCESS_TOKEN = "wechat.access.token";
    public static final String API_CUSTOMER_SERVICE = "wechat.customer.service";
    public static final String API_MENU_CREATE = "wechat.menu.create";
    public static final String API_MENU_GET = "wechat.menu.get";
    public static final String API_MENU_DELETE = "wechat.menu.delete";
    public static final String API_QRCODE_TICKET_CREATE = "wechat.qrcocde.ticket.create";
    public static final String API_QRCODE_TICKET_REDEEM = "wechat.qrcocde.ticket.redeem";
    public static final String API_FOLOOLWER_LIST = "wechat.follower.list";
    public static final String API_FOLOOLWER_INFO = "wechat.follower.info";
    public static final String API_GROUP_GET = "wechat.group.get";
    public static final String API_GROUP_CREATE = "wechat.group.create";
    public static final String API_GROUP_UPDATE = "wechat.group.update";
    public static final String API_GROUP_MOVE_USER = "wechat.group.mvuser";
}
