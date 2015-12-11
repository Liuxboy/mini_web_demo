package com.github.liuxboy.mini.web.demo.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/25 20:51
 * @comment MiddleEntity
 */
public class MiddleEntity implements Serializable {
    private static final long serialVersionUID = -5835294610894629958L;

    /**
     * 平均TravelTime
     */
    public double travel_time_ba;
    /**
     * 平均Delay
     */
    public double delay_ba;

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
        return "MiddleEntity{" +
                "travel_time_ba=" + travel_time_ba +
                ", delay_ba=" + delay_ba +
                '}';
    }
}
