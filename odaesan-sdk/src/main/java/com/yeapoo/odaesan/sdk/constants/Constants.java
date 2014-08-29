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

    interface MaterialType {
        String TEXT = "text";
        String IMAGE = "image";
        String NEWS = "news";
        String MP_NEWS = "mpnews";
        String VIDEO = "video";
        String MP_VIDEO = "mpvideo";
        String MUSIC = "music";
        String VOICE = "voice";
    }
}
