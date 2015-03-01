package com.yeapoo.odaesan.sdk.model.message.event;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class LocationEventMessage extends EventMessage {

    private static final long serialVersionUID = -8250444793139051438L;

    private double latitude;
    private double longitude;
    private double precision;

    public LocationEventMessage() {
        this.setEvent(Constants.EventType.LOCATION);
    }

    public LocationEventMessage(Document document) {
        super(document);
        latitude = Double.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.LATITUDE));
        longitude = Double.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.LONGITUDE));
        precision = Double.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.PRECISION));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }
}
