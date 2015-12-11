package com.github.liuxboy.mini.web.demo.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:48
 * @comment VehicleEntity
 */
public class VehicleEntity implements Serializable {

    private static final long serialVersionUID = 6742139941804090924L;
    private Integer simTime;
    private Integer id;
    private Integer carType;
    private Double speed;
    private Double desspeed;
    private Integer linkid;
    private Integer laneid;
    private String nextlink;
    private double linkpos;
    private Integer inqueue;
    private Integer stops;
    private double delaytm;
    private double x;
    private double y;

    private double travelTimet;
    private double delayT;
    private int segmentId;

    private int startTime;
    private int endTime;

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

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
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

    public Integer getLinkid() {
        return linkid;
    }

    public void setLinkid(Integer linkid) {
        this.linkid = linkid;
    }

    public Integer getLaneid() {
        return laneid;
    }

    public void setLaneid(Integer laneid) {
        this.laneid = laneid;
    }

    public String getNextlink() {
        return nextlink;
    }

    public void setNextlink(String nextlink) {
        this.nextlink = nextlink;
    }

    public double getLinkpos() {
        return linkpos;
    }

    public void setLinkpos(double linkpos) {
        this.linkpos = linkpos;
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


    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "simTime=" + simTime +
                ", id=" + id +
                ", carType=" + carType +
                ", speed=" + speed +
                ", desspeed=" + desspeed +
                ", linkid=" + linkid +
                ", laneid=" + laneid +
                ", nextlink='" + nextlink + '\'' +
                ", linkpos=" + linkpos +
                ", inqueue=" + inqueue +
                ", stops=" + stops +
                ", delaytm=" + delaytm +
                ", x=" + x +
                ", y=" + y +
                ", travelTimet=" + travelTimet +
                ", delayT=" + delayT +
                ", segmentId=" + segmentId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
