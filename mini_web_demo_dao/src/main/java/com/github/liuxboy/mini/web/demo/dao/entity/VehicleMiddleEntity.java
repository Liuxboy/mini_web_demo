package com.github.liuxboy.mini.web.demo.dao.entity;

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

    private int carType;
    private Double speed;
    private Double desspeed;
    private int linkid;
    private double linkpos;
    private int laneid;
    private String nextlink;
    private int inqueue;
    private int stops;
    private double delaytm;
    private double x;
    private double y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSimTime() {
        return simTime;
    }

    public void setSimTime(int simTime) {
        this.simTime = simTime;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
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

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDesspeed() {
        return desspeed;
    }

    public void setDesspeed(Double desspeed) {
        this.desspeed = desspeed;
    }

    public int getLinkid() {
        return linkid;
    }

    public void setLinkid(int linkid) {
        this.linkid = linkid;
    }

    public double getLinkpos() {
        return linkpos;
    }

    public void setLinkpos(double linkpos) {
        this.linkpos = linkpos;
    }

    public int getLaneid() {
        return laneid;
    }

    public void setLaneid(int laneid) {
        this.laneid = laneid;
    }

    public String getNextlink() {
        return nextlink;
    }

    public void setNextlink(String nextlink) {
        this.nextlink = nextlink;
    }

    public Integer getInqueue() {
        return inqueue;
    }

    public void setInqueue(Integer inqueue) {
        this.inqueue = inqueue;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public double getDelaytm() {
        return delaytm;
    }

    public void setDelaytm(double delaytm) {
        this.delaytm = delaytm;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
