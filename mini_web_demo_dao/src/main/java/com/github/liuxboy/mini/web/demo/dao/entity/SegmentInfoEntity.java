package com.github.liuxboy.mini.web.demo.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:48
 * @comment SegmentInfoEntity
 */
public class SegmentInfoEntity implements Serializable {
    private static final long serialVersionUID = 2576373444997795999L;
    private int id;
    private int segmentId;
    private int interserctionId;
    private String travelDirection;
    private int startLink;
    private int endLink;
    private int nextLink;
    private double travel_time_ba;
    private double delay_ba;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getInterserctionId() {
        return interserctionId;
    }

    public void setInterserctionId(int interserctionId) {
        this.interserctionId = interserctionId;
    }

    public String getTravelDirection() {
        return travelDirection;
    }

    public void setTravelDirection(String travelDirection) {
        this.travelDirection = travelDirection;
    }

    public int getStartLink() {
        return startLink;
    }

    public void setStartLink(int startLink) {
        this.startLink = startLink;
    }

    public int getEndLink() {
        return endLink;
    }

    public void setEndLink(int endLink) {
        this.endLink = endLink;
    }

    public int getNextLink() {
        return nextLink;
    }

    public void setNextLink(int nextLink) {
        this.nextLink = nextLink;
    }

    public double getTravel_time_ba() {
        return travel_time_ba;
    }

    public void setTravel_time_ba(double travel_time_ba) {
        this.travel_time_ba = travel_time_ba;
    }

    public double getDelay_ba() {
        return delay_ba;
    }

    public void setDelay_ba(double delay_ba) {
        this.delay_ba = delay_ba;
    }

    @Override
    public String toString() {
        return "SegmentInfoEntity{" +
                "id=" + id +
                ", segmentId=" + segmentId +
                ", interserctionId=" + interserctionId +
                ", travelDirection='" + travelDirection + '\'' +
                ", startLink=" + startLink +
                ", endLink=" + endLink +
                ", nextLink=" + nextLink +
                ", travel_time_ba=" + travel_time_ba +
                ", delay_ba=" + delay_ba +
                '}';
    }
}
