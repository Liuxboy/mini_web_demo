package com.github.liuxboy.mini.web.demo.dao.mapper;


import com.github.liuxboy.mini.web.demo.dao.entity.VehicleEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment VehicleMapper
 */
public interface VehicleMapper {
    /**
     * 根据车辆id选出该车所通的link
     *
     * @param id
     *
     * @return
     */
    List<VehicleEntity> getVehicleById(Integer id);

    /**
     * 某车某时刻某segment的记录
     *
     * @param map
     *
     * @return
     */
    VehicleEntity getVehicleByIdAndTimeAndSegment(Map<String, Integer> map);

    /**
     * 获取最大的车id
     *
     * @return
     */
    Integer getMaxVehicleId();

    /**
     * 插入segment上车的统计travelTime和delay
     * @param vehicleEntity
     * @return
     */
    int insert(VehicleEntity vehicleEntity);
}
