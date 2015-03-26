package liu.chun.dong.dao.mapper;


import liu.chun.dong.dao.entity.MiddleEntity;
import liu.chun.dong.dao.entity.VehicleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment VehicleMapper
 */
public interface VehicleMapper {
    public List<VehicleEntity> getVehicleList();

    /**
     * 根据车辆id选出该车所通的link
     * @param id
     * @return
     */
    public List<VehicleEntity> getVehicleById(Integer id);
    /**
     * 根据时刻获取车辆所通过的link
     */
    public List<VehicleEntity> getVehicleByTime(Integer time);

    /**
     * 某车某时刻的记录
     * @param map
     * @return
     */
    public List<VehicleEntity> getVehicleByIdAndTime(Map<String, Integer> map);

    /**
     * 获取最大的车id
     * @return
     */
    public Integer getMaxVehicleId();
    /**
     * 获取最大的时刻
     */
    public Integer getMaxTime();

    /**
     * 某一时刻，某link上面的车辆信息
     */
    public List<VehicleEntity> getVehicleByTimeAndLink(Map<String, Integer> map);

    /**
     * 更新某时刻，某辆车的travel_time_t,delay,segmentId
     */
    public int update(VehicleEntity vehicleEntity);

    /**
     * 某时刻，某个segment上的车辆信息
     */
    public List<VehicleEntity> getVehicleListByTimeAndSegment(Map<String, Integer> map);

    public MiddleEntity getAVG(@Param("segmentId")int segmentId, @Param("simTime")int simTime);

    public MiddleEntity getAVGMap(Map map);

    public int insert(VehicleEntity vehicleEntity);
}
