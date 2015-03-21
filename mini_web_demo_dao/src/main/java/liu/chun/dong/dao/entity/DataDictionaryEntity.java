package liu.chun.dong.dao.entity;

import java.io.Serializable;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 11:43
 * @comment DataDictionaryEntity
 */
public class DataDictionaryEntity implements Serializable{

    private static final long serialVersionUID = 4699983117655293263L;

    private int id;
    private int num;
    private String crossStreetName;
    private String directionMovement;
    private String explanation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCrossStreetName() {
        return crossStreetName;
    }

    public void setCrossStreetName(String crossStreetName) {
        this.crossStreetName = crossStreetName;
    }

    public String getDirectionMovement() {
        return directionMovement;
    }

    public void setDirectionMovement(String directionMovement) {
        this.directionMovement = directionMovement;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "DataDictionaryEntity{" +
                "id=" + id +
                ", num=" + num +
                ", crossStreetName='" + crossStreetName + '\'' +
                ", directionMovment='" + directionMovement + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
