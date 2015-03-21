package liu.chun.dong.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 11:45
 * @comment SegmentInfoEntity
 */
public class SegmentInfoEntity implements Serializable {
    private static final long serialVersionUID = 2576373444997795999L;
    private int id;
    private int segmentId;
    private int interserctionId;
    private String travelDirection;
    private String startLink;
    private String endLink;
    private String nextLink;

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

    public String getStartLink() {
        return startLink;
    }

    public void setStartLink(String startLink) {
        this.startLink = startLink;
    }

    public String getEndLink() {
        return endLink;
    }

    public void setEndLink(String endLink) {
        this.endLink = endLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    @Override
    public String toString() {
        return "SegmentInfoEntity{" +
                "id=" + id +
                ", segmentId=" + segmentId +
                ", interserctionId=" + interserctionId +
                ", travelDirection='" + travelDirection + '\'' +
                ", startLink='" + startLink + '\'' +
                ", endLink='" + endLink + '\'' +
                ", nextLink='" + nextLink + '\'' +
                '}';
    }
}
