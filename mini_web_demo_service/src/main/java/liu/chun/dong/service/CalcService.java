package liu.chun.dong.service;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:46
 * @comment CalcService
 */
public interface CalcService {
    boolean calcVehicleTravelTimeAndDelay();
    boolean calcSegmentTravelTimeAndDelay();
    boolean updateVehicle();
}
