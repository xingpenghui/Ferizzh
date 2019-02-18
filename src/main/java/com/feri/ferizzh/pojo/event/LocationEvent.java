package com.feri.ferizzh.pojo.event;

/**
 *@Author feri
 *@Date Created in 2019/2/18 12:01
 */
public class LocationEvent extends BaseEvent {
    /*
    * Latitude	地理位置纬度
Longitude	地理位置经度
Precision	地理位置精度*/
    private String Latitude;
    private String Longitude;
    private String Precision;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }
}
