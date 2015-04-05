package liu.chun.dong.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/4/5 20:31
 * @comment VehicleMiddleEntity
 */
public class VehicleMiddleEntity implements Serializable{

    private static final long serialVersionUID = -7662768554307435819L;

    private int id;
    private int simTime;
    private int segmentId;
    private int vehicleId;
    private double travelTimet;
    private double delayT;
    private int timeIn;
    private int timeOut;

    public void setId(int id) {
        this.id = id;
    }

    public void setSimTime(int simTime) {
        this.simTime = simTime;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getSimTime() {
        return simTime;
    }

    public void setSimTime(Integer simTime) {
        this.simTime = simTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTravelTimet() {
        return travelTimet;
    }

    public void setTravelTimet(double travelTimet) {
        this.travelTimet = travelTimet;
    }

    public double getDelayT() {
        return delayT;
    }

    public void setDelayT(double delayT) {
        this.delayT = delayT;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(int timeIn) {
        this.timeIn = timeIn;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
