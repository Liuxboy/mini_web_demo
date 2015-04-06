package liu.chun.dong.dao.mapper;


import liu.chun.dong.dao.entity.MiddleEntity;
import liu.chun.dong.dao.entity.VehicleMiddleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment VehicleMapper
 */
public interface VehicleMiddleMapper {
    int insert(VehicleMiddleEntity vehicleMiddleEntity);
    MiddleEntity getAVG(@Param("segmentId")int segmentId, @Param("simTime")int simTime, @Param("endTime")int endTime);
    MiddleEntity getAVGMap(Map map);
    List<VehicleMiddleEntity> getVehicleMiddleList();
    int update(VehicleMiddleEntity vehicleMiddleEntity);
}
