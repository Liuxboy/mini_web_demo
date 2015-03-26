package liu.chun.dong.service.impl;

import liu.chun.dong.common.cp.Logger;
import liu.chun.dong.common.cp.LoggerFactory;
import liu.chun.dong.dao.entity.MiddleEntity;
import liu.chun.dong.dao.entity.SegmentInfoEntity;
import liu.chun.dong.dao.entity.VehicleEntity;
import liu.chun.dong.dao.mapper.SegmentInfoMapper;
import liu.chun.dong.dao.mapper.VehicleMapper;
import liu.chun.dong.service.CalcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:46
 * @comment CalcServiceImpl
 */
@Service
public class CalcServiceImpl implements CalcService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private VehicleMapper vehicleMapper;
    @Resource
    private SegmentInfoMapper segmentInfoMapper;

    @Override
    public boolean calcVehicleTravelTimeAndDelay(){
        final int initCarId = 6;
        try {
            int maxId = vehicleMapper.getMaxVehicleId();
            logger.info("最大车id：",maxId);
            List<VehicleEntity> vehicleEntityListByCardId;
            //所有的segment信息
            List<SegmentInfoEntity> segmentInfoEntityList = segmentInfoMapper.getSegmentInfo();
            //循环所有的车辆
            for (int i = 15363; i <= maxId; i++) {
                //某一辆车，所有时刻的状态，按simtime升序排列
                vehicleEntityListByCardId = vehicleMapper.getVehicleById(i);
                VehicleEntity objectVehicle, moveVehicle = null, beforeMoveVehicle;
                SegmentInfoEntity inSegment = null;
                if (vehicleEntityListByCardId != null && vehicleEntityListByCardId.size() > 0) {
                    int recordNum = vehicleEntityListByCardId.size();
                    int step = 1, timeIn = 0,timeOut = 1;
                    for (int j = 0; j < recordNum; j += step) {
                        //记下该时刻的车辆信息
                        objectVehicle = vehicleEntityListByCardId.get(j);
                        //通过startLink确定车辆可能存在的segment集
                        List<SegmentInfoEntity> maybeInSegmentListByStartLink = maybeInSegmentList(objectVehicle.getLinkid(), segmentInfoEntityList);
                        //如果可能存在的segment集大于1,则需从后面的时刻判断其具体所在的segment
                        if (maybeInSegmentListByStartLink.size() > 1){
                            timeIn = j;
                            //遍历其后时间的，找到终点
                            for (int k = j + 1; k < recordNum; k++) {
                                beforeMoveVehicle = vehicleEntityListByCardId.get(k - 1);
                                moveVehicle = vehicleEntityListByCardId.get(k);
                                int beforeLinkId = beforeMoveVehicle.getLinkid();
                                int movelinkId = moveVehicle.getLinkid();
                                inSegment = inSegment(beforeLinkId, maybeInSegmentListByStartLink);
                                //查询到segment且 当moveVehicle的linkId与segment的nextlink相同，则说明已经刚好驶出该segment，跳出循环计算
                                if(inSegment != null && movelinkId == inSegment.getNextLink()){
                                    timeOut = k;
                                    break;
                                }
                                if (k == recordNum-1)
                                    timeOut = k;
                            }
                        } else {
                            step = 1;
                        }

                        //在如果找到某个segment,那么segment起止中的时刻，其traveltime与delay是一样的
                        if (inSegment != null) {
                            VehicleEntity vehicleEntity = new VehicleEntity();
                            Result result = calcVehicleTravelTimeAndDelay(objectVehicle, moveVehicle);
                            int startTime = objectVehicle.getSimTime();
                            int endTime = moveVehicle.getSimTime();
                            int id = moveVehicle.getId();
                            vehicleEntity.setId(id);
                            vehicleEntity.setStartTime(startTime);
                            vehicleEntity.setEndTime(endTime);
                            vehicleEntity.setTravelTimet(result.getTravelTime());
                            vehicleEntity.setDelayT(result.getDelay());
                            vehicleEntity.setSegmentId(inSegment.getSegmentId());
                            vehicleMapper.update(vehicleEntity);
                            inSegment = null;
                            step = timeOut - timeIn;
                            logger.info("-----input the CardId: ",id,"-------");
                        }
                    }
                }
            }
        } catch (Exception e){
            logger.error(e.toString());
            return false;
        }
        return true;
    }
    @Override
    public boolean calcSegmentTravelTimeAndDelay(){
        try {
            int maxTime = vehicleMapper.getMaxTime();
            logger.info("the maxTime: ",maxTime);
            //所有的segment信息
            List<VehicleEntity> list;
            Map<String,Integer> map = new HashMap<String,Integer>();
            VehicleEntity vehicleEntity = new VehicleEntity();
            double travelTime = 0.0, delay = 0.0;
            double oldTravelTime = 0.0, oldDelay = 0.0;
            MiddleEntity middleEntity = new MiddleEntity();
            for (int i = 1; i <= 136; i++) {
                for (int j = 1; j <= maxTime; j++) {
                    map.put("segment",i);
                    map.put("simTime",j);
                    middleEntity =  vehicleMapper.getAVG(i, j);
                    //middleEntity =  vehicleMapper.getAVGMap(map);
                    /*
                    list = vehicleMapper.getVehicleListByTimeAndSegment(map);
                    //如果某时刻车辆不为0，则算个平均
                    if (list != null && list.size() > 0) {
                        int size = list.size();
                        for (VehicleEntity entity : list) {
                            travelTime += entity.getTravelTimet();
                            delay += entity.getDelayT();
                        }
                        oldTravelTime = travelTime = travelTime / size;
                        oldDelay = delay = delay / size;
                        vehicleEntity.setTravelTimet(travelTime);
                        vehicleEntity.setDelayT(delay);
                    }
                    //如果当前时间某segment没有车辆，则将上一次结果存入
                    else {
                        vehicleEntity.setTravelTimet(oldTravelTime);
                        vehicleEntity.setDelayT(oldDelay);
                    }*/
                    if (middleEntity != null){
                        oldTravelTime = travelTime = middleEntity.getTravel_time_ba();
                        oldDelay = delay = middleEntity.getDelay_ba();
                        vehicleEntity.setTravelTimet(travelTime);
                        vehicleEntity.setDelayT(delay);
                    } else {
                        vehicleEntity.setTravelTimet(oldTravelTime);
                        vehicleEntity.setDelayT(oldDelay);
                    }
                    vehicleEntity.setSegmentId(i);
                    vehicleEntity.setSimTime(j);
                    vehicleMapper.insert(vehicleEntity);
                    logger.info("----insert the SegmentId: "+i+" and the simtime: "+j+" second----");
                }
            }
        } catch (Exception e){
            logger.error(e.toString());
            return false;
        }
        logger.info("-----------Congratulations------It's Over-----------");
        return true;
    }

    /**
     * 根据linkId匹配segment中的startLink匹配出可能所在的segment集
     * @param startLink
     * @param segmentInfoEntityList
     * @return
     */
    private List<SegmentInfoEntity> maybeInSegmentList(int startLink, List<SegmentInfoEntity> segmentInfoEntityList){
        List<SegmentInfoEntity> resultSegmentList = new ArrayList<SegmentInfoEntity>();
        for (SegmentInfoEntity segmentInfoEntity : segmentInfoEntityList){
            if (segmentInfoEntity.getStartLink() == startLink){
                resultSegmentList.add(segmentInfoEntity);
            }
        }
        return resultSegmentList;
    }
    /**
     * 根据linkId匹配segment中的endLink匹配真正所在的segment
     */
    private SegmentInfoEntity inSegment(int endLink, List<SegmentInfoEntity> maybeInSegmentList){
        SegmentInfoEntity resultSegment = null;
        for (SegmentInfoEntity entity : maybeInSegmentList){
            if (entity.getEndLink() == endLink)
                resultSegment = entity;
        }
        return resultSegment;
    }


    /**
     * 计算车辆某时的travelTime和delay
     */
    private Result calcVehicleTravelTimeAndDelay(VehicleEntity objectVehicle, VehicleEntity moveVehicle){
        Result result = new Result();
        double travelTime;
        int timeIn = objectVehicle.getSimTime();
        double d1 = objectVehicle.getLinkpos();
        double v1 = objectVehicle.getSpeed();
        double delayTm1 = objectVehicle.getDelaytm();

        int timeOut = moveVehicle.getSimTime();
        double d2 = moveVehicle.getLinkpos();
        double v2 = moveVehicle.getSpeed();
        double delayTm2 = moveVehicle.getDelaytm();
        double deltaT = timeOut - timeIn;
        double dtime;
        if (Math.abs(v1 - 0.0D) > 0.000001 && Math.abs(v2-0.0D) > 0.000001) {
            dtime = d1 / v1 - d2 / v2;
        } else {
            dtime = 0.0D;
        }
        travelTime = deltaT + dtime;
        result.setTravelTime(travelTime);
        result.setDelay(delayTm2 - delayTm1);
        return result;
    }
}

class Result{
    private double travelTime;
    private double delay;

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "Result{" +
                "travelTime=" + travelTime +
                ", delay=" + delay +
                '}';
    }
}
