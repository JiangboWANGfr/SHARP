package zzdr.domain;

public class NeedUser {
    String username;
    int usbType;
    double latitude;
    double longitude;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int  getUsbType() {
        return usbType;
    }

    public void setUsbType(int  usbType) {
        this.usbType = usbType;
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
}
