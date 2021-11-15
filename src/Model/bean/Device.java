package Model.bean;

import java.io.Serializable;

/**
 *
 * @author will
 */
public class Device implements Serializable {
    private static String deviceID;

    public static String getDeviceID() {
        return deviceID;
    }

    public static void setDeviceID(String deviceID) {
        Device.deviceID = deviceID;
    }

}
