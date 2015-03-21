package liu.chun.dong.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 11:48
 * @comment VehicleEntity
 */
public class VehicleEntity implements Serializable {

    private static final long serialVersionUID = 6742139941804090924L;
    private Long simTime;
    private Long id;
    private Integer carType;
    private Double speed;
    private Double desspeed;
    private Long linkid;
    private Long laneid;
    private Long nextlink;
    private Integer lnqueue;
    private Integer stops;
    private Double delaytm;
    private Double x;
    private Double y;

    public Long getSimTime() {
        return simTime;
    }

    public void setSimTime(Long simTime) {
        this.simTime = simTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getLinkid() {
        return linkid;
    }

    public void setLinkid(Long linkid) {
        this.linkid = linkid;
    }

    public Long getLaneid() {
        return laneid;
    }

    public void setLaneid(Long laneid) {
        this.laneid = laneid;
    }

    public Long getNextlink() {
        return nextlink;
    }

    public void setNextlink(Long nextlink) {
        this.nextlink = nextlink;
    }

    public Integer getLnqueue() {
        return lnqueue;
    }

    public void setLnqueue(Integer lnqueue) {
        this.lnqueue = lnqueue;
    }

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public Double getDelaytm() {
        return delaytm;
    }

    public void setDelaytm(Double delaytm) {
        this.delaytm = delaytm;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
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
                ", nextlink=" + nextlink +
                ", lnqueue=" + lnqueue +
                ", stops=" + stops +
                ", delaytm=" + delaytm +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
