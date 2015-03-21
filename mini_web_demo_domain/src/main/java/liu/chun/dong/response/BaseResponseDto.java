package liu.chun.dong.response;

/**
 * Created by liuchundong on 2015-03-08 15:38:48
 */
public class BaseResponseDto<T> implements java.io.Serializable {

    private static final long serialVersionUID = -7127995087107993254L;
    private int resultCode;
    private String resultMsg;
    private T resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "BaseResponseDto{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultData=" + resultData +
                '}';
    }
}
