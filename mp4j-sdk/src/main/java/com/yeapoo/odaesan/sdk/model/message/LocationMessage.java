package com.yeapoo.odaesan.sdk.model.message;

import org.w3c.dom.Document;

import com.yeapoo.common.util.XmlUtil;
import com.yeapoo.odaesan.sdk.constants.Constants;

public class LocationMessage extends Message {

    private static final long serialVersionUID = 2196040554682426103L;

    private Double latitude;
    private Double longitude;
    private int scale;
    private String label;

    public LocationMessage() {
        this.setMessageType(Constants.MessageType.LOCATION);
    }

    public LocationMessage(Document document) {
        super(document);
        latitude = Double.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.LOCATION_X));
        longitude = Double.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.LOCATION_Y));
        scale = Integer.valueOf(XmlUtil.getNodeContent(document, Constants.XmlTag.SCALE));
        label = XmlUtil.getNodeContent(document, Constants.XmlTag.LABEL);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
