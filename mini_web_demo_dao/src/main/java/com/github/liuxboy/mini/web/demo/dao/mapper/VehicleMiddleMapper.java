package com.github.liuxboy.mini.web.demo.dao.mapper;


import com.github.liuxboy.mini.web.demo.dao.entity.MiddleEntity;
import com.github.liuxboy.mini.web.demo.dao.entity.VehicleMiddleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment VehicleMapper
 */
public interface VehicleMiddleMapper {
    int insert(VehicleMiddleEntity vehicleMiddleEntity);
    MiddleEntity getAVG(@Param("segmentId")int segmentId, @Param("simTime")int simTime, @Param("endTime")int endTime);
    Integer getMaxTime();
    List<VehicleMiddleEntity> getVehicleMiddleList();
    int update(VehicleMiddleEntity vehicleMiddleEntity);
}
